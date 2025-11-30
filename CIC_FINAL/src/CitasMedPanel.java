
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CitasMedPanel extends JPanel {
    private final String usuarioId;
    private Connection con;
    private DefaultTableModel m;
	private String prueba;

    // Componentes
    private JTable           tblCita;
    private JComboBox<String> cmbMes, cmbDia;
    private JLabel           lblFecha, lblBuscar;
    private JTextField       txtBuscar;
    private JLabel           btnAgregarCita, btnCitasCompletadas;
    private Reloj1           reloj11;

    public CitasMedPanel(String usuarioId) {
        this.usuarioId = usuarioId;
        this.con       = ConexionSQL.ConexionSQLServer();
        initComponents();
        m = (DefaultTableModel) tblCita.getModel();
        lblFecha.setText(obtenerFecha());
        llenarCitas();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ── Panel superior: buscador + fecha ──────────────────────
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblBuscar = new JLabel("Buscar paciente", new ImageIcon(getClass().getResource("/imagenes/buscar(1).png")), SwingConstants.LEFT);
        txtBuscar = new JTextField(15);
        lblFecha  = new JLabel();
        top.add(lblBuscar);
        top.add(txtBuscar);
        top.add(Box.createHorizontalStrut(20));
        top.add(lblFecha);
        add(top, BorderLayout.NORTH);

        // ── Panel central: tabla de citas ────────────────────────
        tblCita = new JTable(new DefaultTableModel(
            new Object[]{"Fecha","Hora","Nombre","Apellido1","Apellido2","numeroSeguro"}, 0
        ));
        add(new JScrollPane(tblCita), BorderLayout.CENTER);

        // ── Panel inferior: filtros mes/día ──────────────────────
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cmbMes = new JComboBox<>(new String[]{"Mes…","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"});
        cmbDia = new JComboBox<>(new String[]{"Día…","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
        bottom.add(cmbMes);
        bottom.add(cmbDia);
        add(bottom, BorderLayout.SOUTH);

        // ── Panel izquierdo: acciones ─────────────────────────────
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        btnAgregarCita       = new JLabel("Agregar Cita",       new ImageIcon(getClass().getResource("/imagenes/agregar-usuario.png")), SwingConstants.LEFT);
        btnCitasCompletadas  = new JLabel("Citas completadas",  new ImageIcon(getClass().getResource("/imagenes/citaCompletada.png")), SwingConstants.LEFT);
        left.add(Box.createVerticalStrut(20));
        left.add(btnAgregarCita);
        left.add(Box.createVerticalStrut(20));
        left.add(btnCitasCompletadas);
        add(left, BorderLayout.WEST);

        // ── Reloj ───────────────────────────────────────────────────
        reloj11 = new Reloj1();
        add(reloj11, BorderLayout.EAST);

        // ── Listeners ──────────────────────────────────────────────
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarPaciente(txtBuscar.getText());
                    if (txtBuscar.getText().isEmpty()) llenarCitas();
                }
            }
        });
        cmbMes.addItemListener(e -> buscarPorFecha());
        cmbDia.addItemListener(e -> buscarPorFecha());
        tblCita.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { manejarClickTabla(); }
        });

        // Disparar evento "navigate" para que ventanaMedico cambie de tarjeta
        btnAgregarCita.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAgregarCita.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                firePropertyChange("navigate", null, "REGISTRAR");
            }
        });
        btnCitasCompletadas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCitasCompletadas.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                firePropertyChange("navigate", null, "COMPLETADAS");
            }
        });
    }

    // ── Lógica de datos (idéntica a tu versión original) ────────

    private String obtenerFecha() {
        LocalDateTime hoy = LocalDateTime.now();
        return hoy.getMonthValue() + "/" + hoy.getDayOfMonth() + "/" + hoy.getYear();
    }

    public void llenarCitas() {
        m.setRowCount(0);
        try {
            Statement stmt2 = con.createStatement();
            ResultSet rsMed = stmt2.executeQuery(
                "SELECT idMedico FROM medico WHERE correo = '"+usuarioId+"'"
            );
            String idMed = rsMed.next() ? rsMed.getString("idMedico") : "";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
              "SELECT cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro "
            + "FROM cita cit INNER JOIN paciente pac ON pac.numeroSeguro = cit.numeroSeguro "
            + "WHERE cit.idMedico = '"+idMed+"' ORDER BY cit.fecha;"
            );
            while (rs.next()) {
                m.addRow(new Object[]{
                  rs.getObject("fecha"),
                  rs.getObject("hora"),
                  rs.getObject("nombrePac"),
                  rs.getObject("apellido1"),
                  rs.getObject("apellido2"),
                  rs.getObject("numeroSeguro")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void buscarPorFecha() {
        int nDia = cmbDia.getSelectedIndex(), nMes = cmbMes.getSelectedIndex();
        if (nDia==0 && nMes==0) { llenarCitas(); return; }
        StringBuilder sql = new StringBuilder(
          "SELECT cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro "
        + "FROM cita cit INNER JOIN paciente pac ON pac.numeroSeguro = cit.numeroSeguro "
        + "WHERE cit.idMedico = '"+ obtenerIdMed(usuarioId) +"' "
        );
        if (nMes>0) sql.append("AND MONTH(cit.fecha) = ").append(nMes).append(" ");
        if (nDia>0) sql.append("AND DAY(cit.fecha)  = ").append(nDia).append(" ");
        sql.append("ORDER BY cit.fecha;");
        try {
            m.setRowCount(0);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            while (rs.next()) {
                m.addRow(new Object[]{
                  rs.getObject("fecha"),
                  rs.getObject("hora"),
                  rs.getObject("nombrePac"),
                  rs.getObject("apellido1"),
                  rs.getObject("apellido2"),
                  rs.getObject("numeroSeguro")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void buscarPaciente(String nombre) {
        String sql = 
          "SELECT cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro "
        + "FROM cita cit INNER JOIN paciente pac ON pac.numeroSeguro = cit.numeroSeguro "
        + "WHERE pac.nombrePac LIKE '%"+nombre+"%';";
        try {
            m.setRowCount(0);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                m.addRow(new Object[]{
                  rs.getObject("fecha"),
                  rs.getObject("hora"),
                  rs.getObject("nombrePac"),
                  rs.getObject("apellido1"),
                  rs.getObject("apellido2"),
                  rs.getObject("numeroSeguro")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarClickTabla() {
        // copia aquí tu lógica de JOptionPane (1:completar, 2:modificar, 3:cancelar)
        // y al final invoca llenarCitas() para refrescar.
    }

    private String obtenerIdMed(String correo) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs   = stmt.executeQuery(
              "SELECT idMedico FROM medico WHERE correo = '"+correo+"'"
            );
            if (rs.next()) return rs.getString("idMedico");
        } catch (SQLException ex) { ex.printStackTrace(); }
        return "";
    }
}
