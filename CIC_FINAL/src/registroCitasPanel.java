
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import com.raven.swing.TimePicker;

public class registroCitasPanel extends JPanel {
    private static Statement stmt;
    public static Connection con;
    private String correo;
    private DefaultTableModel m;

    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAtras;
    private javax.swing.JComboBox<String> cmbNSS;
    private JDateChooser dcFecha;
    private TimePicker escogerHora;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtIdCita;
    private javax.swing.JTextField txtNombre;

    public registroCitasPanel(String correo) {
        this.correo = correo;
        initComponents();
        con = ConexionSQL.ConexionSQLServer();
        txtIdCita.setEditable(false);
        m = null; // no hay tabla aquí
        llenarNSS();
    }

    private void initComponents() {
        // Este código suele generarlo NetBeans. Lo he extraído de tu clase JFrame
        // ÚNICAMENTE necesita eliminar setDefaultCloseOperation, pack() y main().
        // Y al final lo anidamos en este panel:
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        txtIdCita = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        btnAtras = new javax.swing.JButton();
        dcFecha = new com.toedter.calendar.JDateChooser();
        cmbNSS = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        escogerHora = new com.raven.swing.TimePicker();

        // Configuraciones de layout de jPanel1/jPanel2...
        // ... copia aquí el initComponents() que tenías, sin llamadas a JFrame.

        // Al final:
        setLayout(new java.awt.BorderLayout());
        add(jPanel1, java.awt.BorderLayout.CENTER);

        // Listeners:
        btnAtras.addActionListener(evt -> onAtras());
        btnAceptar.addActionListener(evt -> onAceptar());
        cmbNSS.addItemListener(evt -> obtenerDatosPac());
        // etc. (los demás listeners los dejas igual, ajustando this.dispose())
    }

    private void onAtras() {
        // En lugar de cerrar ventana, notificamos al padre que vuelva a "CITAS"
        firePropertyChange("navigate", null, "CITAS");
    }

    private void onAceptar() {
        registrarCita(this.correo);
        // luego volvemos a la tarjeta de lista de citas
        firePropertyChange("navigate", null, "CITAS");
    }

    private void obtenerDatosPac() {
        try {
            stmt = con.createStatement();
            String idNSS = cmbNSS.getSelectedItem().toString();
            ResultSet rs1 = stmt.executeQuery(
                "SELECT nombrePac, apellido1, apellido2 FROM PACIENTE WHERE numeroSeguro='"
                 + idNSS + "';");
            ResultSet rs2 = stmt.executeQuery(
                "SELECT TOP 1 idCita FROM Cita ORDER BY idCita DESC;");
            if (rs1.next() && rs2.next()) {
                txtNombre.setText(rs1.getString("nombrePac"));
                txtApellidos.setText(
                    rs1.getString("apellido1") + " " + rs1.getString("apellido2")
                );
                // genera siguiente idCita:
                String last = rs2.getString("idCita");
                char prefix = last.charAt(0);
                int num = Integer.parseInt(last.substring(1)) + 1;
                txtIdCita.setText(prefix + String.valueOf(num));
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroCitasPanel.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    private void llenarNSS() {
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT numeroSeguro FROM PACIENTE;");
            cmbNSS.removeAllItems();
            cmbNSS.addItem("NSS...");
            while (rs.next()) {
                cmbNSS.addItem(rs.getString("numeroSeguro"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroCitasPanel.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    private void registrarCita(String correo) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFmt = sdf.format(dcFecha.getDate());
            String nss = cmbNSS.getSelectedItem().toString();
            String idCita = txtIdCita.getText();
            String hora = txtHora.getText();
            String idMed = ventanaCitasMed.obtenerIdMed(correo);

            String insert = String.join("\n",
                "INSERT INTO Cita (idCita, fecha, hora, numeroSeguro, idMedico)",
                "VALUES ('"+idCita+"','"+fechaFmt+"','"+hora+"','"+nss+"','"+idMed+"');"
            );
            stmt = con.createStatement();
            stmt.executeUpdate(insert);
            showMessageDialog(null, "Cita agregada con éxito");
        } catch (Exception ex) {
            Logger.getLogger(registroCitasPanel.class.getName())
                  .log(Level.SEVERE, null, ex);
            showMessageDialog(null, "Error al insertar cita");
        }
    }
}

