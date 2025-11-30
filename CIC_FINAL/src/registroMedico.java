
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;

public class registroMedico extends javax.swing.JFrame {

    private static Statement stmt;
    public static Connection con;
    private String correo;

    public registroMedico(String correo) {
    initComponents();
    this.correo = correo;

    con = ConexionSQL.ConexionSQLServer();   // 1) primero abrir conexión

    txtIdMed.setEditable(false);             // 2) id bloqueado
    txtIdMed.setEnabled(false);
    txtIdMed.setText(obtenerSiguienteIdMedico()); // 3) ahora sí consulta el siguiente id

    // =============================
    //  LIMITE DE FECHAS: NO FUTURO
    // =============================
    java.util.Date hoy = new java.util.Date();

    // Fecha de contratación (dcFec)
    dcFec.setMaxSelectableDate(hoy);
    dcFec.setDate(hoy);          // que inicie en hoy

    // Fecha de nacimiento (FeNac)
    FeNac.setMaxSelectableDate(hoy);
    FeNac.setDate(hoy);          // también inicia en hoy

    this.setLocationRelativeTo(null);
}


    
    private String obtenerSiguienteIdMedico() {
    try {
        if (con == null || con.isClosed()) {
            con = ConexionSQL.ConexionSQLServer();
        }
        String sql = "SELECT ISNULL(MAX(idMedico),0) + 1 AS nextId FROM medico";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getString("nextId") : "1";
        }
    } catch (SQLException ex) {
        System.out.println("Error obtenerSiguienteIdMedico: " + ex.getMessage());
        return "1";
    }
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
        jLabel11 = new javax.swing.JLabel();
        Aceptar = new javax.swing.JButton();
        txtApellido1 = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido2 = new javax.swing.JTextField();
        txtEspecialidad = new javax.swing.JTextField();
        txtNumCedula = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtIdMed = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtDomicilio = new javax.swing.JTextField();
        dcFec = new com.toedter.calendar.JDateChooser();
        txtCancelar = new javax.swing.JButton();
        FeNac = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(136, 212, 234));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabel1.setText("Registrarse");

        jLabel6.setFont(new java.awt.Font("Roboto", 2, 18)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favicon.png"))); // NOI18N
        jLabel6.setText("Centro de Informacion y Consulta");

        jLabel5.setText("idMedico:");

        jLabel7.setText("Nombre:");

        jLabel8.setText("Apellido1:");

        jLabel9.setText("Apellido2:");

        jLabel10.setText("Fecha Contratacion:");

        jLabel11.setText("Número de cédula:");

        Aceptar.setText("Confirmar");
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        jLabel12.setText("Especialidad:");

        jLabel13.setText("Teléfono:");

        jLabel14.setText("Domicilio:");

        txtCancelar.setText("Cancelar");
        txtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCancelarActionPerformed(evt);
            }
        });

        jLabel15.setText("Fecha Nacimiento:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(Aceptar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(jLabel1))
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel12)))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtDomicilio))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtApellido2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(txtIdMed)
                            .addComponent(txtNombre)
                            .addComponent(txtApellido1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(FeNac, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dcFec, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNumCedula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel6)
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtIdMed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dcFec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(FeNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtNumCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(txtDomicilio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
        registrarMedico(this.correo);
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
        ventanaAdmin login = new ventanaAdmin(null);
        login.setVisible(true);
    }//GEN-LAST:event_txtCancelarActionPerformed

    private void registrarMedico(String correo) {
    con = ConexionSQL.ConexionSQLServer();

    // Validar que haya fecha de contratación
    if (dcFec.getCalendar() == null) {
        JOptionPane.showMessageDialog(this, "Selecciona la fecha de contratación.");
        return;
    }

    // Validar que haya fecha de nacimiento
    if (FeNac.getCalendar() == null) {
        JOptionPane.showMessageDialog(this, "Selecciona la fecha de nacimiento.");
        return;
    }

    // =============================
    //  EXTRA: NO FUTURO POR SI LAS DUDAS
    // =============================
    java.util.Calendar hoy = java.util.Calendar.getInstance();

    java.util.Calendar calContra = dcFec.getCalendar();
    java.util.Calendar calNac    = FeNac.getCalendar();

    if (calContra.after(hoy)) {
        JOptionPane.showMessageDialog(this,
            "La fecha de contratación no puede ser mayor a hoy.");
        return;
    }

    if (calNac.after(hoy)) {
        JOptionPane.showMessageDialog(this,
            "La fecha de nacimiento no puede ser mayor a hoy.");
        return;
    }

    // Pasar a texto YYYY-M-D
    String fechaContra = calContra.get(java.util.Calendar.YEAR) + "-" +
                         (calContra.get(java.util.Calendar.MONTH) + 1) + "-" +
                         calContra.get(java.util.Calendar.DAY_OF_MONTH);

    String fechaNac = calNac.get(java.util.Calendar.YEAR) + "-" +
                      (calNac.get(java.util.Calendar.MONTH) + 1) + "-" +
                      calNac.get(java.util.Calendar.DAY_OF_MONTH);

    String idMed       = txtIdMed.getText().trim();
    String nombre      = txtNombre.getText().trim();
    String apellido1   = txtApellido1.getText().trim();
    String apellido2   = txtApellido2.getText().trim();
    String especialidad= txtEspecialidad.getText().trim();
    String numCedula   = txtNumCedula.getText().trim();
    String tel         = txtTelefono.getText().trim();
    String dom         = txtDomicilio.getText().trim();

    // Ajusta columnas a como esté tu tabla medico
    String sql = "INSERT INTO medico (" +
                 "idMedico, nombreMed, apellido1, apellido2, " +
                 "fechaContra, FechaNac, especialidad, NumCedula, " +
                 "NumTelefonico, Domicilio, Correo) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, idMed);
        ps.setString(2, nombre);
        ps.setString(3, apellido1);
        ps.setString(4, apellido2);
        ps.setString(5, fechaContra);
        ps.setString(6, fechaNac);
        ps.setString(7, especialidad);
        ps.setString(8, numCedula);
        ps.setString(9, tel);
        ps.setString(10, dom);
        ps.setString(11, correo);

        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Médico agregado con éxito.");
        this.dispose();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al insertar Médico: " + ex.getMessage());
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
    private com.toedter.calendar.JDateChooser FeNac;
    private com.toedter.calendar.JDateChooser dcFec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtIdMed;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumCedula;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
