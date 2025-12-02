/*
 * Dialogo para reasignar las citas de un médico a otros médicos disponibles
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ReasignarCitasDialog extends JDialog {

    private final Connection con;
    private final int idMedicoOriginal;

    private JTable tblCitas;
    private JTable tblMedicos;
    private DefaultTableModel mCitas;
    private DefaultTableModel mMedicosDisp;

    private boolean todoReasignado = false;

    public ReasignarCitasDialog(Frame owner, Connection con, int idMedicoOriginal) {
        super(owner, "Reasignar citas del médico " + idMedicoOriginal, true);
        this.con = con;
        this.idMedicoOriginal = idMedicoOriginal;

        initComponents();
        cargarCitas();

        // Centrar respecto a la ventana que lo llama (ventanaAdmin)
        setLocationRelativeTo(owner);
    }

    public boolean fueReasignadoTodo() {
        return todoReasignado;
    }

    private void initComponents() {
        // ====== TABLA DE CITAS ======
        mCitas = new DefaultTableModel(
                new Object[]{"IdCita", "Fecha", "Hora", "Paciente"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tblCitas = new JTable(mCitas);

        // ====== TABLA DE MÉDICOS DISPONIBLES ======
        mMedicosDisp = new DefaultTableModel(
                new Object[]{"IdMedico", "Nombre", "Apellido1", "Apellido2", "Especialidad"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tblMedicos = new JTable(mMedicosDisp);

        JButton btnCargarMedicos = new JButton("Ver médicos disponibles para esta cita");
        JButton btnReasignar     = new JButton("Reasignar cita");
        JButton btnCerrar        = new JButton("Cerrar");

        btnCargarMedicos.addActionListener(e -> cargarMedicosParaCitaSeleccionada());
        btnReasignar.addActionListener(e -> reasignarCitaSeleccionada());
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCentro.add(new JScrollPane(tblCitas));
        panelCentro.add(new JScrollPane(tblMedicos));

        JPanel panelAbajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAbajo.add(btnCargarMedicos);
        panelAbajo.add(btnReasignar);
        panelAbajo.add(btnCerrar);

        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(panelCentro, BorderLayout.CENTER);
        getContentPane().add(panelAbajo, BorderLayout.SOUTH);

        setSize(900, 400);
    }

    // =================== CARGAR CITAS DEL MÉDICO ORIGINAL ===================

    private void cargarCitas() {
        mCitas.setRowCount(0);
        String sql =
                "SELECT c.idCita, c.fecha, c.hora, p.nombrePac " +
                "FROM CITA c " +
                "LEFT JOIN PACIENTE p ON p.numeroSeguro = c.numeroSeguro " +
                "WHERE c.idMedico = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMedicoOriginal);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mCitas.addRow(new Object[]{
                            rs.getString("idCita"),
                            rs.getDate("fecha"),
                            rs.getString("hora"),
                            rs.getString("nombrePac")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando citas del médico:\n" + ex.getMessage());
        }
    }

    // =================== CARGAR MÉDICOS DISPONIBLES PARA UNA CITA ===================

    private void cargarMedicosParaCitaSeleccionada() {
        int row = tblCitas.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita primero.");
            return;
        }

        java.sql.Date fecha = (java.sql.Date) mCitas.getValueAt(row, 1);
        String hora = mCitas.getValueAt(row, 2).toString();

        mMedicosDisp.setRowCount(0);

        String sql =
                "SELECT m.idMedico, m.nombreMed, m.apellido1, m.apellido2, m.Especialidad " +
                "FROM MEDICO m " +
                "WHERE m.idMedico <> ? " +
                "AND NOT EXISTS (" +
                "    SELECT 1 FROM CITA c " +
                "    WHERE c.idMedico = m.idMedico " +
                "      AND c.fecha = ? " +
                "      AND c.hora  = ?" +
                ")";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMedicoOriginal);
            ps.setDate(2, fecha);
            ps.setString(3, hora);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mMedicosDisp.addRow(new Object[]{
                            rs.getInt("idMedico"),
                            rs.getString("nombreMed"),
                            rs.getString("apellido1"),
                            rs.getString("apellido2"),
                            rs.getString("Especialidad")
                    });
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando médicos disponibles:\n" + ex.getMessage());
        }
    }

    // =================== REASIGNAR UNA CITA ===================

  private void reasignarCitaSeleccionada() {
    int rowCita = tblCitas.getSelectedRow();
    int rowMed  = tblMedicos.getSelectedRow();

    if (rowCita < 0) {
        JOptionPane.showMessageDialog(this, "Selecciona una cita.");
        return;
    }
    if (rowMed < 0) {
        JOptionPane.showMessageDialog(this, "Selecciona un médico disponible.");
        return;
    }

    String idCita = mCitas.getValueAt(rowCita, 0).toString();
    int idMedicoNuevo = (int) mMedicosDisp.getValueAt(rowMed, 0);

    // Aquí se cambia el médico y se marca como Modificado
    String sql = "UPDATE CITA " +
                 "SET idMedico = ?, estatus = 'Modificado' " +
                 "WHERE idCita = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idMedicoNuevo);
        ps.setString(2, idCita);
        ps.executeUpdate();

        // La quitas de la tabla porque ya no pertenece al médico original
        mCitas.removeRow(rowCita);

        if (mCitas.getRowCount() == 0) {
            todoReasignado = true;
            JOptionPane.showMessageDialog(this,
                    "Todas las citas de este médico fueron reasignadas.");
        } else {
            JOptionPane.showMessageDialog(this, "Cita reasignada.");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error al reasignar cita:\n" + ex.getMessage());
    }
}


}
