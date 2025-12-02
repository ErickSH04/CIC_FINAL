
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.PreparedStatement;


public class VentanaRecepcion extends javax.swing.JFrame {

    private static String usuarioId;
    //private static Statement stmt;
    //public static Connection con;

    public VentanaRecepcion() {
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
    }

    public VentanaRecepcion(String usuarioId) throws ClassNotFoundException, SQLException {//String usuarioId
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        String nombre = obtenerNombreRecepcion();
        //lblBienvenida.setText(nombre);
    }
    
    

    public String obtenerNombreRecepcion() throws ClassNotFoundException {
    Connection conn = null;
    Statement stmt2 = null;
    ResultSet resultado = null;
    String nombreRecepcion = "";

    try {
        conn = ConexionSQL.ConexionSQLServer();
        //VERIFICAR si la conexión es null
        if (conn == null) {
            System.err.println("Error: No se pudo establecer conexión a la BD");
            return "Recepcionista";
        }
        
        stmt2 = conn.createStatement();
        String query = "SELECT nombre, apellido1, apellido2 FROM RECEPCIONISTA WHERE correo = '" + usuarioId + "'";
        System.out.println(query);
        
        resultado = stmt2.executeQuery(query);

        while (resultado.next()) {
            nombreRecepcion = resultado.getString("nombre");
        }

    } catch (SQLException ex) {
        System.err.println("Error obteniendo datos del recepcionista: " + ex.getMessage()); 
    } finally {
        try {
            if (resultado != null) resultado.close();
        } catch (SQLException e) {
            System.err.println("Error cerrando ResultSet: " + e.getMessage());
        }
        try {
            if (stmt2 != null) stmt2.close();
        } catch (SQLException e) {
            System.err.println("Error cerrando Statement: " + e.getMessage());
        }
        try {
            //if (conn != null) ConexionSQL.cerrarConexion(conn);
        } catch (Exception e) {
            System.err.println("Error cerrando Connection: " + e.getMessage());
        }
    }
    return nombreRecepcion;
}

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reloj11 = new Reloj1();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblGestionCitas = new javax.swing.JLabel();
        lblPacientes = new javax.swing.JLabel();
        lblCerrarSesion = new javax.swing.JLabel();
        lblMedicos = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelPagos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        lblGestionCitas.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblGestionCitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/citaM.png"))); // NOI18N
        lblGestionCitas.setText("Gestion Citas");
        lblGestionCitas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblGestionCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGestionCitasMouseClicked(evt);
            }
        });

        lblPacientes.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblPacientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paciente.png"))); // NOI18N
        lblPacientes.setText("Pacientes");
        lblPacientes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPacientesMouseClicked(evt);
            }
        });

        lblCerrarSesion.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblCerrarSesion.setText("Cerrar sesión");
        lblCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarSesionMouseClicked(evt);
            }
        });

        lblMedicos.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblMedicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/estetoscopio.png"))); // NOI18N
        lblMedicos.setText("Medicos");
        lblMedicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMedicosMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        jLabelPagos.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabelPagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dar-dinero.png"))); // NOI18N
        jLabelPagos.setText("Pagos");
        jLabelPagos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelPagosMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMedicos)
                        .addGap(59, 59, 59))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCerrarSesion)
                        .addContainerGap())))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1))
                    .addComponent(lblPacientes)
                    .addComponent(lblGestionCitas)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addGap(72, 72, 72)
                .addComponent(jLabelPagos)
                .addGap(18, 18, 18)
                .addComponent(lblGestionCitas)
                .addGap(18, 18, 18)
                .addComponent(lblPacientes)
                .addGap(18, 18, 18)
                .addComponent(lblMedicos)
                .addGap(33, 33, 33)
                .addComponent(lblCerrarSesion)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(660, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblGestionCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGestionCitasMouseClicked
        ventanaCitasPacRecepcionista vc = null;
        try {
            vc = new ventanaCitasPacRecepcionista(this.usuarioId);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaRecepcion.class.getName()).log(Level.SEVERE, null, ex);
        }
        vc.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_lblGestionCitasMouseClicked

    private void lblPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPacientesMouseClicked
        // TODO add your handling code here:
        ventanaPacientesRecp rp = null;
        try {
            rp = new ventanaPacientesRecp(usuarioId);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaRecepcion.class.getName()).log(Level.SEVERE, null, ex);
        }
        rp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblPacientesMouseClicked

    private void lblMedicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMedicosMouseClicked
        // TODO add your handling code here:
        VentanaInfoMedRecp InfMe = null;
        try {
            InfMe = new VentanaInfoMedRecp(usuarioId);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaRecepcion.class.getName()).log(Level.SEVERE, null, ex);
        }
        InfMe.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblMedicosMouseClicked

    private void lblCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarSesionMouseClicked
        // TODO add your handling code here:
        loginHospitalNuevo hl = null;
        hl = new loginHospitalNuevo();
        hl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblCerrarSesionMouseClicked

    private void jLabelPagosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPagosMouseClicked
         // Abrir la ventana de pagos
          ventaPagos vp = null;
          vp = new ventaPagos(usuarioId); // si tu clase ventaPagos requiere usuarioId
          if (vp != null) {
          vp.setVisible(true);   // Mostrar ventana de pagos
          this.dispose();        // Cerrar la ventana actual
      }
    }//GEN-LAST:event_jLabelPagosMouseClicked

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelPagos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblGestionCitas;
    private javax.swing.JLabel lblMedicos;
    private javax.swing.JLabel lblPacientes;
    private Reloj1 reloj11;
    // End of variables declaration//GEN-END:variables
}
