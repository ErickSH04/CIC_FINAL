
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import static javax.swing.JOptionPane.showMessageDialog;

public class citasCompletadasMedPanel extends JPanel {
    private static String usuarioId;
    private static Statement stmt;
    public static Connection con;
    private DefaultTableModel m;

    // Componentes (renómbralos si usas otro IDE)
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblAtras;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCita;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JTextField txtBuscar;
    private Reloj1 reloj11;

    public citasCompletadasMedPanel(String usuarioId) {
        citasCompletadasMedPanel.usuarioId = usuarioId;
        initComponents();
        con = ConexionSQL.ConexionSQLServer();
        m = (DefaultTableModel) tblCita.getModel();
        lblFecha.setText(obtenerFecha());
        llenarCitas();
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        lblAtras = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCita = new javax.swing.JTable();
        cmbMes = new javax.swing.JComboBox<>();
        cmbDia = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        reloj11 = new Reloj1();

        // PANEL PRINCIPAL
        setLayout(new java.awt.BorderLayout());
        add(jPanel1, java.awt.BorderLayout.CENTER);

        // CONFIGURAR jPanel1 y jPanel2 (copia tu layout aquí)
        // ...
        // (Pega aquí todo tu initComponents() de NetBeans, **sin** setDefaultCloseOperation ni pack())

        // --- LISTENERS ---

        // "Atrás" vuelve al listado de citas (tarjeta "CITAS")
        lblAtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firePropertyChange("navigate", null, "CITAS");
            }
        });

        // "Salir" delega al padre (opcional: o quítalo si ya tienes botón en ventanaMedico)
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firePropertyChange("navigate", null, "LOGOUT");
            }
        });

        // Filtrar al teclear Enter en txtBuscar
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == evt.VK_ENTER) {
                    buscarPaciente(txtBuscar.getText());
                    if (txtBuscar.getText().isEmpty()) {
                        llenarCitas();
                    }
                }
            }
        });

        // Filtrar por mes/día
        cmbMes.addItemListener(evt -> buscarPorFecha());
        cmbDia.addItemListener(evt -> buscarPorFecha());

        // (Opcional) clic en fila: nada o lo que ya tenías
        tblCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // no-op
            }
        });
    }

    private String obtenerFecha() {
        LocalDateTime hoy = LocalDateTime.now();
        return hoy.getMonthValue() + "/" + hoy.getDayOfMonth() + "/" + hoy.getYear();
    }

    private void llenarCitas() {
        try {
            con = ConexionSQL.ConexionSQLServer();
            m.setRowCount(0);
            stmt = con.createStatement();
            String idMed = obtenerIdMed(usuarioId);
            String query = String.join("\n",
                "SELECT cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro",
                "FROM citasCompletadas cit",
                "INNER JOIN paciente pac ON pac.numeroSeguro = cit.numeroSeguro",
                "WHERE cit.idMedico = '" + idMed + "';"
            );
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Object[] row = {
                    rs.getObject("fecha"),
                    rs.getObject("hora"),
                    rs.getObject("nombrePac"),
                    rs.getObject("apellido1"),
                    rs.getObject("apellido2"),
                    rs.getObject("numeroSeguro")
                };
                m.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(citasCompletadasMedPanel.class.getName())
                  .log(Level.SEVERE, null, ex);
            showMessageDialog(null, "Error al cargar citas completadas");
        }
    }

    private void buscarPorFecha() {
        int nDia = cmbDia.getSelectedIndex();
        int nMes = cmbMes.getSelectedIndex();
        String idMed = obtenerIdMed(usuarioId);
        StringBuilder sb = new StringBuilder(
            "SELECT cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro\n" +
            "FROM citasCompletadas cit\n" +
            "INNER JOIN paciente pac ON pac.numeroSeguro = cit.numeroSeguro\n" +
            "WHERE cit.idMedico = '" + idMed + "'\n"
        );
        if (nMes > 0) sb.append("  AND MONTH(cit.fecha) = ").append(nMes).append("\n");
        if (nDia > 0) sb.append("  AND DAY(cit.fecha) = ").append(nDia).append("\n");
        sb.append(";");
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());
            m.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                    rs.getObject("fecha"),
                    rs.getObject("hora"),
                    rs.getObject("nombrePac"),
                    rs.getObject("apellido1"),
                    rs.getObject("apellido2"),
                    rs.getObject("numeroSeguro")
                };
                m.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(citasCompletadasMedPanel.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    private void buscarPaciente(String nombre) {
        String idMed = obtenerIdMed(usuarioId);
        String q = String.join("\n",
            "SELECT cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro",
            "FROM citasCompletadas cit",
            "INNER JOIN paciente pac ON pac.numeroSeguro = cit.numeroSeguro",
            "WHERE cit.idMedico = '" + idMed + "'",
            "  AND pac.nombrePac LIKE '%" + nombre + "%';"
        );
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(q);
            m.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                    rs.getObject("fecha"),
                    rs.getObject("hora"),
                    rs.getObject("nombrePac"),
                    rs.getObject("apellido1"),
                    rs.getObject("apellido2"),
                    rs.getObject("numeroSeguro")
                };
                m.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(citasCompletadasMedPanel.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    public static String obtenerIdMed(String correo) {
        try {
            con = ConexionSQL.ConexionSQLServer();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT med.idMedico FROM medico med " +
                "INNER JOIN usuario u ON med.Correo = u.correo " +
                "WHERE med.Correo = '" + correo + "';"
            );
            if (rs.next()) return rs.getString("idMedico");
        } catch (SQLException ex) {
            Logger.getLogger(citasCompletadasMedPanel.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
