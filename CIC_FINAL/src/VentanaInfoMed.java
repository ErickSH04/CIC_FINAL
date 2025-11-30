
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentanaInfoMed extends javax.swing.JFrame {

    private String usuarioId;
    private static Connection con;
    private static Statement stmt;
    private static String doctor;
    

    public VentanaInfoMed() {
        con = ConexionSQL.ConexionSQLServer();
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
    }

    public VentanaInfoMed(String usuarioId) {//String usuarioId
        con = ConexionSQL.ConexionSQLServer();
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        llenarDoctores();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblCerrarSesion = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblInformacion = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblMedicoId = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        lblNombreR = new javax.swing.JLabel();
        lblEspecialidad = new javax.swing.JLabel();
        lblMedicoIdR = new javax.swing.JLabel();
        cbmMedicos = new javax.swing.JComboBox<>();
        lblInformacion1 = new javax.swing.JLabel();
        lblEspecialidadR = new javax.swing.JLabel();
        lblCedulaR = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblTelefonoR = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        lblCorreoR = new javax.swing.JLabel();
        txtTelefonoR = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        lblCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblInicio.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pagina-de-inicio.png"))); // NOI18N
        lblInicio.setText("Inicio");
        lblInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInicioMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCerrarSesion)
                    .addComponent(lblInicio)
                    .addComponent(jLabel1))
                .addGap(0, 64, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInicio)
                .addGap(119, 119, 119)
                .addComponent(lblCerrarSesion)
                .addGap(111, 111, 111))
        );

        lblInformacion.setFont(new java.awt.Font("Roboto", 2, 24));
        lblInformacion.setText("Información Medicos");

        lblNombre.setFont(new java.awt.Font("Roboto", 2, 14));
        lblNombre.setText("Nombre:");

        lblMedicoId.setFont(new java.awt.Font("Roboto", 2, 14));
        lblMedicoId.setText("Id del Medico:");

        lblCedula.setFont(new java.awt.Font("Roboto", 2, 14));
        lblCedula.setText("Número de Cedula:");

        lblNombreR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblEspecialidad.setFont(new java.awt.Font("Roboto", 2, 14));
        lblEspecialidad.setText("Especialidad:");

        lblMedicoIdR.setFont(new java.awt.Font("Roboto", 2, 14));

        cbmMedicos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbmMedicosItemStateChanged(evt);
            }
        });

        lblInformacion1.setFont(new java.awt.Font("Roboto", 2, 14));
        lblInformacion1.setText("Busqueda:");

        lblEspecialidadR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblCedulaR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblTelefono.setFont(new java.awt.Font("Roboto", 2, 14));
        lblTelefono.setText("Telefono:");

        lblTelefonoR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblCorreo.setFont(new java.awt.Font("Roboto", 2, 14));
        lblCorreo.setText("Correo:");

        lblCorreoR.setFont(new java.awt.Font("Roboto", 2, 14));

        txtTelefonoR.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMedicoId, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(73, 73, 73)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCedulaR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMedicoIdR, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEspecialidadR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelefonoR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCorreoR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(txtTelefonoR, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNombreR, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblInformacion1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(cbmMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMedicoIdR, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNombreR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefonoR))
                        .addGap(21, 21, 21)
                        .addComponent(lblEspecialidadR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(lblCedulaR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(lblTelefonoR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblInformacion1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbmMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMedicoId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(lblCorreoR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(211, 211, 211))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbmMedicosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbmMedicosItemStateChanged
        // TODO add your handling code here:
        doctor = (String) cbmMedicos.getSelectedItem();
        if (!(doctor == "Selecciona")) {
            llenarDatos();
        }

    }//GEN-LAST:event_cbmMedicosItemStateChanged

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        VentanaPaciente vp = new VentanaPaciente(usuarioId);
        vp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

    public void llenarDoctores() {
        try {
            // Limpia los elementos existentes
            System.out.println("Entro a la funcion");
            cbmMedicos.removeAllItems();
            cbmMedicos.addItem("Selecciona");

            // Ejecuta la consulta para obtener todos los usuarios
            stmt = con.createStatement();

            String query = "SELECT nombreMed "
                    + "FROM MEDICO";
            
            System.out.println(query);
            ResultSet result = stmt.executeQuery(query);

            // Agrega los usuarios al JComboBox
            while (result.next()) {
                String doctor = result.getString("nombreMed");
                cbmMedicos.addItem(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error para depuración
        }
    }

    public void llenarDatos() {
        try {
            stmt = con.createStatement();
            String query = "SELECT M.idMedico, M.nombreMed, M.apellido1, M.apellido2, M.Especialidad, M.NumCedula, M.NumTelefonico, M.Correo\n"
                    + "FROM Medico M\n"
                    + "WHERE nombreMed = '" + doctor + "';";
                    
            ResultSet result = stmt.executeQuery(query);
            String idMedico = "", nombreCom = "", especialidad = "", numCedula = "", telefono = "", correo = "";
            while (result.next()) {
               
                idMedico = result.getString("idMedico");
                nombreCom = result.getString("nombreMed") + " " + result.getString("apellido1") + " " + result.getString("apellido2");
                especialidad = result.getString("Especialidad");
                numCedula = result.getString("NumCedula");
                telefono = result.getString("NumTelefonico");
                correo = result.getString("Correo");

                lblMedicoIdR.setText(idMedico);
                lblNombreR.setText(nombreCom);
                lblEspecialidadR.setText(especialidad);
                lblCedulaR.setText(numCedula);
                lblTelefonoR.setText(telefono);
                lblCorreoR.setText(correo);

            }
        } catch (SQLException ex) {
            Logger.getLogger(VentanaInfoPac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPaciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbmMedicos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblCedulaR;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblCorreoR;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblEspecialidadR;
    private javax.swing.JLabel lblInformacion;
    private javax.swing.JLabel lblInformacion1;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblMedicoId;
    private javax.swing.JLabel lblMedicoIdR;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreR;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTelefonoR;
    private javax.swing.JLabel txtTelefonoR;
    // End of variables declaration//GEN-END:variables
}