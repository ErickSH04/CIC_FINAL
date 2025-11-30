
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VentanaPerfilMedico extends javax.swing.JFrame {

    private String usuarioId;
    private static Connection con;
    private static Statement stmt;
    private static String doctor;
    private String rutaFotoSeleccionada = null;
    
    public VentanaPerfilMedico(String usuarioId) {//String usuarioId
        con = ConexionSQL.ConexionSQLServer();
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        cargarDatosMedico();
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
        lblEspecialidadR = new javax.swing.JLabel();
        lblCedulaR = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblTelefonoR = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        lblCorreoR = new javax.swing.JLabel();
        jLabelFoto = new javax.swing.JLabel();
        jButtonCambiarFoto = new javax.swing.JButton();
        jButtonGuardarFot = new javax.swing.JButton();

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
                .addGap(0, 29, Short.MAX_VALUE))
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

        lblInformacion.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        lblInformacion.setText("Perfil Medico");

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

        lblEspecialidadR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblCedulaR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblTelefono.setFont(new java.awt.Font("Roboto", 2, 14));
        lblTelefono.setText("Telefono:");

        lblTelefonoR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblCorreo.setFont(new java.awt.Font("Roboto", 2, 14));
        lblCorreo.setText("Correo:");

        lblCorreoR.setFont(new java.awt.Font("Roboto", 2, 14));

        jLabelFoto.setText("Foto");
        jLabelFoto.setPreferredSize(new java.awt.Dimension(90, 90));

        jButtonCambiarFoto.setText("Cambiar Foto");
        jButtonCambiarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCambiarFotoActionPerformed(evt);
            }
        });

        jButtonGuardarFot.setText("Guardar Foto");
        jButtonGuardarFot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarFotActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMedicoId, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(73, 73, 73)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCedulaR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEspecialidadR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelefonoR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCorreoR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombreR, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMedicoIdR, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonCambiarFoto)
                        .addGap(36, 36, 36)
                        .addComponent(jButtonGuardarFot)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonCambiarFoto)
                            .addComponent(jButtonGuardarFot))
                        .addGap(73, 73, 73)
                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblNombreR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEspecialidadR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCedulaR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMedicoId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMedicoIdR, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCorreoR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblTelefonoR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(120, 120, 120))
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

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        ventanaMedico vm = new ventanaMedico(usuarioId);
        vm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

    private void jButtonCambiarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCambiarFotoActionPerformed
        // TODO add your handling code here:
        // 1) Configura un selector de archivos que sólo muestre imágenes
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(
        new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg","jpeg","png")
        );

        // 2) Abre el diálogo
        int opcion = chooser.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
        File archivo = chooser.getSelectedFile();
        // 3) Lee y escala la imagen al tamaño de jLabelFoto
        ImageIcon icono = new ImageIcon(archivo.getAbsolutePath());
        Image imgEscalada = icono.getImage().getScaledInstance(
            jLabelFoto.getWidth(),
            jLabelFoto.getHeight(),
            Image.SCALE_SMOOTH
        );
        // 4) Muestra la foto en el JLabel
        jLabelFoto.setIcon(new ImageIcon(imgEscalada));

        // Guardar la ruta en memoria 
        rutaFotoSeleccionada = archivo.getAbsolutePath();
        }
    }//GEN-LAST:event_jButtonCambiarFotoActionPerformed

    private void jButtonGuardarFotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarFotActionPerformed
        // TODO add your handling code here:
        if (rutaFotoSeleccionada == null) {
        JOptionPane.showMessageDialog(this,
            "Debes seleccionar una foto primero.",
            "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lee todo el archivo en memoria
    File archivo = new File(rutaFotoSeleccionada);
    byte[] fotoBytes;
     try {
        // lee todo el fichero en un byte[]
        fotoBytes = Files.readAllBytes(archivo.toPath());
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this,
            "No se pudo leer la imagen:\n" + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Actualiza el BLOB en la BD
    String sql = "UPDATE MEDICO SET foto_blob = ? WHERE correo = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setBytes(1, fotoBytes);
        ps.setString(2, usuarioId);
        int filas = ps.executeUpdate();
        if (filas > 0) {
            JOptionPane.showMessageDialog(this,
                "Foto guardada correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "No se encontró al médico para actualizar la foto.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error al guardar la foto:\n" + ex.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    }//GEN-LAST:event_jButtonGuardarFotActionPerformed

    private void cargarDatosMedico() {
         String sql = ""
      + "SELECT idMedico, nombreMed, apellido1, apellido2, "
      + "       Especialidad, NumCedula, NumTelefonico, Correo, foto_blob "
      + "  FROM MEDICO "
      + " WHERE correo = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, usuarioId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                lblMedicoIdR.setText(rs.getString("idMedico"));
                lblNombreR.setText(
                    rs.getString("nombreMed") + " "
                  + rs.getString("apellido1")  + " "
                  + rs.getString("apellido2")
                );
                lblEspecialidadR.setText(rs.getString("Especialidad"));
                lblCedulaR.setText(rs.getString("NumCedula"));
                lblTelefonoR.setText(rs.getString("NumTelefonico"));
                lblCorreoR.setText(rs.getString("Correo"));

                // --- Carga la foto desde el BLOB ---
                byte[] imgBytes = rs.getBytes("foto_blob");
                if (imgBytes != null && imgBytes.length > 0) {
                    ImageIcon iconBD = new ImageIcon(imgBytes);
                    Image imgBD = iconBD.getImage()
                      .getScaledInstance(
                        jLabelFoto.getWidth(),
                        jLabelFoto.getHeight(),
                        Image.SCALE_SMOOTH
                      );
                    jLabelFoto.setIcon(new ImageIcon(imgBD));
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error al cargar datos del médico:\n" + ex.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
    
    private void cargarImagen(String ruta){
        ImageIcon iconBD = new ImageIcon(ruta);
        Image imgBD = iconBD.getImage().getScaledInstance(jLabelFoto.getWidth(), jLabelFoto.getHeight(), Image.SCALE_SMOOTH);
        jLabelFoto.setIcon(new ImageIcon(imgBD));
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
    private javax.swing.JButton jButtonCambiarFoto;
    private javax.swing.JButton jButtonGuardarFot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelFoto;
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
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblMedicoId;
    private javax.swing.JLabel lblMedicoIdR;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreR;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTelefonoR;
    // End of variables declaration//GEN-END:variables
}
