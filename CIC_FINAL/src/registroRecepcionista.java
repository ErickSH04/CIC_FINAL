
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;

public class registroRecepcionista extends javax.swing.JFrame {

    private static Statement stmt;
    public static Connection con;
    private String correo;

    public registroRecepcionista(String correo) {
        initComponents();
        this.setLocationRelativeTo(this);
        this.correo = correo;
        con = ConexionSQL.ConexionSQLServer();
        
        String nextId = obtenerSiguienteIdRecep();
        txtIdRecep.setText(nextId);
        txtIdRecep.setEditable(false);
        txtIdRecep.setEnabled(false);      // opcional: gris
        txtIdRecep.setFocusable(false);    // que ni el cursor llegue
        dcFec.setMaxSelectableDate(new java.util.Date());
    }
    
     private String obtenerSiguienteIdRecep() {
        String sql = "SELECT ISNULL(MAX(idRecepcionista),0) + 1 AS nextId FROM recepcionista";
        try (java.sql.Statement st = con.createStatement();
             java.sql.ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getString("nextId");
        } catch (SQLException ex) {
            System.out.println("Error obtenerSiguienteIdRecep: " + ex.getMessage());
        }
        return "1";
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Aceptar = new javax.swing.JButton();
        txtApellido1 = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido2 = new javax.swing.JTextField();
        txtIdRecep = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        dcFec = new com.toedter.calendar.JDateChooser();
        txtCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(136, 212, 234));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabel1.setText("Registrarse");

        jLabel6.setFont(new java.awt.Font("Roboto", 2, 18)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favicon.png"))); // NOI18N
        jLabel6.setText("Centro de Informacion y Consulta");

        jLabel5.setText("idRecepcionista:");

        jLabel7.setText("Nombre:");

        jLabel8.setText("Apellido1:");

        jLabel9.setText("Apellido2:");

        jLabel10.setText("Fecha contratacion:");

        Aceptar.setText("Confirmar");
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        jLabel13.setText("Teléfono:");

        txtCancelar.setText("Cancelar");
        txtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtApellido2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                        .addComponent(txtIdRecep)
                        .addComponent(txtNombre)
                        .addComponent(txtApellido1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtCancelar)
                                .addGap(18, 18, 18)
                                .addComponent(Aceptar))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(74, 74, 74)
                                        .addComponent(jLabel1))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel9)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(139, 139, 139))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(dcFec, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(3, 3, 3)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdRecep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(dcFec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptar)
                    .addComponent(txtCancelar))
                .addGap(27, 27, 27))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 340, 450));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed
        registrarRecepcionista(this.correo);
        ventanaAdmin login = new ventanaAdmin(null);
        login.setVisible(true);

    }//GEN-LAST:event_AceptarActionPerformed

    private void txtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCancelarActionPerformed
        String query = "delete from usuario \n"
                + "where(correo = '" + correo + "');";

        System.out.println(query);

        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            showMessageDialog(null, "Registro cancelado con exito");
        } catch (SQLException ex) {
            showMessageDialog(null, ex.getMessage());
        }
        this.dispose();
        loginHospitalNuevo login = new loginHospitalNuevo();
        login.setVisible(true);
    }//GEN-LAST:event_txtCancelarActionPerformed

    private void registrarRecepcionista(String correo) {
    con = ConexionSQL.ConexionSQLServer();

    if (dcFec.getCalendar() == null) {
        JOptionPane.showMessageDialog(this, "Selecciona la fecha de contratación.");
        return;
    }

    Calendar fec = dcFec.getCalendar();

    // ⬇⬇⬇ VALIDACIÓN ANTI FUTURO
    Calendar hoy = Calendar.getInstance();
    hoy.set(Calendar.HOUR_OF_DAY, 0);
    hoy.set(Calendar.MINUTE, 0);
    hoy.set(Calendar.SECOND, 0);
    hoy.set(Calendar.MILLISECOND, 0);

    Calendar soloFecha = (Calendar) fec.clone();
    soloFecha.set(Calendar.HOUR_OF_DAY, 0);
    soloFecha.set(Calendar.MINUTE, 0);
    soloFecha.set(Calendar.SECOND, 0);
    soloFecha.set(Calendar.MILLISECOND, 0);

    if (soloFecha.after(hoy)) {
        JOptionPane.showMessageDialog(this, "La fecha de contratación no puede ser futura.");
        return;
    }
    // ⬆⬆⬆ hasta aquí la validación

    String fechaCon = fec.get(Calendar.YEAR) + "-" + (fec.get(Calendar.MONTH)+1) + "-" + fec.get(Calendar.DAY_OF_MONTH);

    String idRecepcionista = txtIdRecep.getText().trim(); // viene bloqueado
    String nombre          = txtNombre.getText().trim();
    String apellido1       = txtApellido1.getText().trim();
    String apellido2       = txtApellido2.getText().trim();
    String telefono        = txtTelefono.getText().trim();

    String sql = "INSERT INTO Recepcionista (idRecepcionista, nombre, apellido1, apellido2, correo, telefono, fechaContratacion) " +
                 "VALUES (?,?,?,?,?,?,?)";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, idRecepcionista);
        ps.setString(2, nombre);
        ps.setString(3, apellido1);
        ps.setString(4, apellido2);
        ps.setString(5, correo);
        ps.setString(6, telefono);
        ps.setDate(7, java.sql.Date.valueOf(fechaCon));

        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Recepcionista agregado con éxito");
        this.dispose();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al insertar Recepcionista:\n" + ex.getMessage());
    }
}



    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptar;
    private com.toedter.calendar.JDateChooser dcFec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtApellido1;
    private javax.swing.JTextField txtApellido2;
    private javax.swing.JButton txtCancelar;
    private javax.swing.JTextField txtIdRecep;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
