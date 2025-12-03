import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;

public class CostosCitasDialog extends JDialog {
    private final Connection con;
    private final String usuario; // para audit trail
    private JTable tbl;
    private DefaultTableModel model;

    public CostosCitasDialog(Frame owner, Connection con, String usuario) {
        super(owner, "Costos por especialidad", true);
        this.con = con;
        this.usuario = usuario == null ? "" : usuario;

        initUI();
        setSize(700, 480);
        setLocationRelativeTo(owner);

        try {
            ensureTablaTarifa();
            cargarDatos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error inicializando tarifas:\n" + e.getMessage());
        }
    }

    private void initUI() {
        model = new DefaultTableModel(new Object[]{"Especialidad", "Precio", "Actualizado", "Por"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 1; } // solo precio
            @Override public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return String.class;
                return String.class;
            }
        };
        tbl = new JTable(model);
        tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbl.getColumnModel().getColumn(0).setPreferredWidth(250);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(120);
        tbl.getColumnModel().getColumn(2).setPreferredWidth(140);
        tbl.getColumnModel().getColumn(3).setPreferredWidth(120);
        tbl.setRowHeight(26);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRecargar = new JButton("Recargar");
        JButton btnGuardar  = new JButton("Guardar cambios");
        JButton btnAgregar  = new JButton("Agregar especialidad");
        JButton btnEliminar = new JButton("Eliminar precio");
        JButton btnCerrar   = new JButton("Cerrar");

        btnRecargar.addActionListener(e -> {
            try { cargarDatos(); } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error recargando:\n" + ex.getMessage());
            }
        });

        btnGuardar.addActionListener(e -> {
            try { guardarCambios(); } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error guardando:\n" + ex.getMessage());
            }
        });

        btnAgregar.addActionListener(e -> agregarEspecialidad());
        btnEliminar.addActionListener(e -> eliminarPrecioSeleccionado());
        btnCerrar.addActionListener(e -> dispose());

        top.add(btnRecargar);
        top.add(btnGuardar);
        top.add(btnAgregar);
        top.add(btnEliminar);
        top.add(btnCerrar);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(tbl), BorderLayout.CENTER);
    }

    private void ensureTablaTarifa() throws SQLException {
        String ddl = ""
            + "IF NOT EXISTS (SELECT 1 FROM sys.objects "
            + "WHERE object_id = OBJECT_ID(N'[dbo].[tarifa_especialidad]') AND type = N'U') "
            + "BEGIN "
            + "  CREATE TABLE dbo.tarifa_especialidad( "
            + "    especialidad   VARCHAR(100) NOT NULL PRIMARY KEY, "
            + "    precio         DECIMAL(10,2) NOT NULL, "
            + "    actualizado_por VARCHAR(100) NULL, "
            + "    actualizado_en  DATETIME NOT NULL DEFAULT GETDATE() "
            + "  ); "
            + "END";
        try (Statement st = con.createStatement()) { st.executeUpdate(ddl); }
    }

    private void cargarDatos() throws SQLException {
        model.setRowCount(0);
        String sql = ""
            + "WITH esp AS ( "
            + "  SELECT DISTINCT LTRIM(RTRIM(Especialidad)) AS especialidad "
            + "  FROM medico "
            + "  WHERE Especialidad IS NOT NULL AND LTRIM(RTRIM(Especialidad)) <> '' "
            + ") "
            + "SELECT COALESCE(e.especialidad, t.especialidad) AS especialidad, "
            + "       CAST(ISNULL(t.precio, 0) AS DECIMAL(10,2)) AS precio, "
            + "       ISNULL(CONVERT(VARCHAR(19), t.actualizado_en, 120), '') AS actualizado_en, "
            + "       ISNULL(t.actualizado_por, '') AS actualizado_por "
            + "FROM esp e "
            + "FULL OUTER JOIN tarifa_especialidad t ON t.especialidad = e.especialidad "
            + "ORDER BY 1";

        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getString("especialidad"),
                    rs.getBigDecimal("precio") == null ? "0.00" : rs.getBigDecimal("precio").toPlainString(),
                    rs.getString("actualizado_en"),
                    rs.getString("actualizado_por")
                });
            }
        }
    }

    private void guardarCambios() throws SQLException {
    // cierra la edición activa de la celda, si la hay
    if (tbl.isEditing()) {
        tbl.getCellEditor().stopCellEditing();
    }

    con.setAutoCommit(false);
    try {
        String upd = "UPDATE dbo.tarifa_especialidad " +
                     "SET precio=?, actualizado_por=?, actualizado_en=GETDATE() " +
                     "WHERE especialidad=?";
        String ins = "INSERT INTO dbo.tarifa_especialidad(especialidad, precio, actualizado_por) " +
                     "VALUES (?,?,?)";

        int totalUpd = 0, totalIns = 0;

        try (PreparedStatement psUpd = con.prepareStatement(upd);
             PreparedStatement psIns = con.prepareStatement(ins)) {

            for (int r = 0; r < model.getRowCount(); r++) {
                String esp = safeStr(model.getValueAt(r, 0));
                String precioTxt = safeStr(model.getValueAt(r, 1));

                BigDecimal precio = parseMoney(precioTxt);

                // UPDATE
                psUpd.setBigDecimal(1, precio);
                psUpd.setString(2, usuario);
                psUpd.setString(3, esp);
                int n = psUpd.executeUpdate();
                totalUpd += n;

                if (n == 0) {
                    // INSERT
                    psIns.setString(1, esp);
                    psIns.setBigDecimal(2, precio);
                    psIns.setString(3, usuario);
                    psIns.executeUpdate();
                    totalIns++;
                }

                model.setValueAt(nowISO(), r, 2);
                model.setValueAt(usuario, r, 3);
            }
        }

        con.commit();

        // debug: muestra en qué servidor/BD estás escribiendo
        try (Statement s = con.createStatement()) {
            try (ResultSet rs = s.executeQuery("SELECT @@SERVERNAME, DB_NAME()")) {
                if (rs.next()) {
                    System.out.println("Guardado en: servidor=" + rs.getString(1) +
                                       " bd=" + rs.getString(2));
                }
            }
        }

        JOptionPane.showMessageDialog(this,
                "Tarifas guardadas.\nActualizadas: " + totalUpd + "  Insertadas: " + totalIns);

    } catch (SQLException ex) {
        try { con.rollback(); } catch (Exception ignored) {}
        throw ex;
    } finally {
        try { con.setAutoCommit(true); } catch (Exception ignored) {}
    }
}


    private void agregarEspecialidad() {
        String esp = JOptionPane.showInputDialog(this, "Nombre de la especialidad:");
        if (esp == null) return;
        esp = esp.trim();
        if (esp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Especialidad vacía.");
            return;
        }
        String precio = JOptionPane.showInputDialog(this, "Precio (ej. 500 o 500.00):", "0.00");
        if (precio == null) return;

        try {
            parseMoney(precio); // valida
            // si ya existe en la tabla visual, solo actualiza la fila
            int row = findRowByEspecialidad(esp);
            if (row >= 0) {
                model.setValueAt(precio, row, 1);
            } else {
                model.addRow(new Object[]{esp, new BigDecimal(precio).toPlainString(), "", ""});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Precio inválido.");
        }
    }

    private void eliminarPrecioSeleccionado() {
        int row = tbl.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila.");
            return;
        }
        String esp = safeStr(model.getValueAt(row, 0));
        int ok = JOptionPane.showConfirmDialog(this,
                "Eliminar el precio de \"" + esp + "\"?\n"
              + "No borra la especialidad de los médicos, solo la tarifa.", "Confirmar",
              JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;

        try {
            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM tarifa_especialidad WHERE especialidad=?")) {
                ps.setString(1, esp);
                ps.executeUpdate();
            }
            model.setValueAt("0.00", row, 1);
            model.setValueAt("", row, 2);
            model.setValueAt("", row, 3);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error eliminando precio:\n" + e.getMessage());
        }
    }

    private int findRowByEspecialidad(String esp) {
        for (int r = 0; r < model.getRowCount(); r++) {
            if (esp.equalsIgnoreCase(safeStr(model.getValueAt(r, 0)))) return r;
        }
        return -1;
    }

    private static String safeStr(Object o) {
        return o == null ? "" : o.toString().trim();
    }

    private static BigDecimal parseMoney(String s) {
        s = s == null ? "" : s.trim().replace(",", "");
        if (s.isEmpty()) return new BigDecimal("0");
        return new BigDecimal(s);
    }

    private static String nowISO() {
        // no depende de la BD, es solo para mostrar inmediato
        java.time.LocalDateTime t = java.time.LocalDateTime.now();
        return t.toString().replace('T', ' ').substring(0, 19);
    }
}
