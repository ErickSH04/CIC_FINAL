
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class VentanaAgregarReceta extends javax.swing.JFrame {
    private medico medico;
    private String usuarioId;
    private static Connection con;
    private static Statement stmt;
    private static String fecha;
    private String medicoIdActual;

    public VentanaAgregarReceta() {
        con = ConexionSQL.ConexionSQLServer();
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
    }

    public VentanaAgregarReceta(String usuarioId) {//String usuarioId
        con = ConexionSQL.ConexionSQLServer();
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);    
        
        configurarBotonGuardar();
        configurarFechaPlaceholder();
        cargarMedicoActual();
    }
    
    private void configurarBotonGuardar() {
    jLabelGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            guardarReceta();
        }
    });
  }
    
    // Método para cargar el médico actual en el ComboBox
    private void cargarMedicoActual() {
      try {
        String sql = "SELECT idMedico, nombreMed, apellido1 FROM MEDICO WHERE correo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String idMedico = rs.getString("idMedico");
                    String nombreCompleto = rs.getString("nombreMed") + " " + rs.getString("apellido1");
                    
                    // Limpiar y agregar solo el médico actual
                    //jComboMedico.removeAllItems();
                    //jComboMedico.addItem(nombreCompleto + " (" + idMedico + ")");
                    
                    // Guardar el ID del médico para usar después
                    medicoIdActual = idMedico;
                }
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(VentanaAgregarReceta.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error al cargar información del médico");
    }
  }
    
    // Método para guardar la receta en la base de datos
    private void guardarReceta() {
    if (validarCampos()) {
        try {
            String sql = "INSERT INTO RecetaMedica (indicaciones, fechaEmision, medicamentos, idMedico) " +
                        "VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                // Obtener valores de los campos
                String fecha = jTextFecha.getText();
                String indicaciones = jTextIndicaciones.getText();
                String medicamentos = jTextMedicamentos.getText();
               // String medicoSeleccionado = (String) jComboMedico.getSelectedItem();
                
                ps.setString(1, indicaciones);
                ps.setString(2, fecha);
                ps.setString(3, medicamentos);
                ps.setString(4, medicoIdActual); // ID del médico actual
                
                int filasAfectadas = ps.executeUpdate();
                
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Receta guardada exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    abrirVentanaReceMed(indicaciones, medicamentos, medicoSeleccionado);
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error al guardar la receta", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentanaAgregarReceta.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, 
                "Error de base de datos: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}

private void abrirVentanaReceMed(String indicaciones, String medicamentos, String medico) {
    VentanaReceMed ventanaRece = new VentanaReceMed(usuarioId);
    ventanaRece.mostrarRecetaReciente(indicaciones, medicamentos, medico);
    ventanaRece.setVisible(true);
    this.dispose();
}

// Validar campos obligatorios
private boolean validarCampos() {
    String fecha = jTextFecha.getText();
    String indicaciones = jTextIndicaciones.getText();
    String medicamentos = jTextMedicamentos.getText();
  
    if (indicaciones.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Por favor ingrese las indicaciones", 
            "Validación", 
            JOptionPane.WARNING_MESSAGE);
        jTextIndicaciones.requestFocus();
        return false;
    }
    
    if (medicamentos.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Por favor ingrese los medicamentos", 
            "Validación", 
            JOptionPane.WARNING_MESSAGE);
        jTextMedicamentos.requestFocus();
        return false;
    }
    
    if (!validarFormatoFecha(fecha)) {
        JOptionPane.showMessageDialog(this, 
            "Formato de fecha inválido. Use YYYY-MM-DD", 
            "Validación", 
            JOptionPane.WARNING_MESSAGE);
        jTextFecha.requestFocus();
        return false;
    }
    
    return true;
}

// Validar formato de fecha
private boolean validarFormatoFecha(String fecha) {
    return fecha.matches("\\d{4}-\\d{2}-\\d{2}");
}

private void configurarFechaPlaceholder() {
    // Agregar placeholder al campo de fecha
    jTextFecha.setText("aaaa-mm-dd"); // Texto de ejemplo
    jTextFecha.setForeground(Color.GRAY); // Color para indicar que es placeholder

    jTextFecha.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (jTextFecha.getText().equals("aaaa-mm-dd")) {
                jTextFecha.setText("");
                jTextFecha.setForeground(Color.BLACK);
            }
        }
        
        @Override
        public void focusLost(FocusEvent e) {
            if (jTextFecha.getText().isEmpty()) {
                jTextFecha.setText("aaaa-mm-dd");
                jTextFecha.setForeground(Color.GRAY);
            }
        }
    });
}


// Limpiar campos después de guardar
private void limpiarCampos() {
    jTextFecha.setText("aaaa-mm-dd");
    jTextFecha.setForeground(Color.GRAY);
    jTextIndicaciones.setText("");
    jTextMedicamentos.setText("");
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblCerrarSesion = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        reloj11 = new Reloj1();
        jLabel2 = new javax.swing.JLabel();
        jLabelGuardar = new javax.swing.JLabel();
        lblInformacion = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblMedicamento = new javax.swing.JLabel();
        lblMedicoR = new javax.swing.JLabel();
        lblIDR = new javax.swing.JLabel();
        lblIndicaciones = new javax.swing.JLabel();
        lblFechaR = new javax.swing.JLabel();
        lblMedicamentoR = new javax.swing.JLabel();
        lblMedico = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextIndicaciones = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextMedicamentos = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextFecha = new javax.swing.JTextArea();
        lblIdMed = new javax.swing.JLabel();

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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        jLabelGuardar.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabelGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/disco-flexibleM.png"))); // NOI18N
        jLabelGuardar.setText("Guardar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelGuardar)
                            .addComponent(lblInicio)
                            .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(lblCerrarSesion)))
                .addGap(0, 61, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(52, 52, 52)
                .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122)
                .addComponent(lblInicio)
                .addGap(51, 51, 51)
                .addComponent(jLabelGuardar)
                .addGap(120, 120, 120)
                .addComponent(lblCerrarSesion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblInformacion.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        lblInformacion.setText(" Recetas");

        lblFecha.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblFecha.setText("Fecha de Emision: ");

        lblMedicamento.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblMedicamento.setText("Medicamentos:");

        lblMedicoR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblIDR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblIndicaciones.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblIndicaciones.setText("Indicaciones: ");

        lblFechaR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblMedicamentoR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblMedico.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        lblMedico.setText("Medico Correspondiente: ");

        jTextIndicaciones.setColumns(20);
        jTextIndicaciones.setRows(5);
        jScrollPane1.setViewportView(jTextIndicaciones);

        jTextMedicamentos.setColumns(20);
        jTextMedicamentos.setRows(5);
        jScrollPane2.setViewportView(jTextMedicamentos);

        jTextFecha.setColumns(20);
        jTextFecha.setRows(5);
        jScrollPane3.setViewportView(jTextFecha);

        lblIdMed.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        lblIdMed.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(472, 472, 472)
                                .addComponent(lblMedicoR, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblIdMed, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(162, 162, 162)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblIDR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblFechaR, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblMedicamentoR, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(302, 302, 302))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(294, 294, 294)
                        .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaR, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblIDR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblIdMed)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(411, 411, 411)
                        .addComponent(lblMedicamentoR, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(lblMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMedicoR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1104, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 861, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        ventanaMedico vm = new ventanaMedico(usuarioId);
        vm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

    

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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelGuardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextFecha;
    private javax.swing.JTextArea jTextIndicaciones;
    private javax.swing.JTextArea jTextMedicamentos;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFechaR;
    private javax.swing.JLabel lblIDR;
    private javax.swing.JLabel lblIdMed;
    private javax.swing.JLabel lblIndicaciones;
    private javax.swing.JLabel lblInformacion;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblMedicamento;
    private javax.swing.JLabel lblMedicamentoR;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblMedicoR;
    private Reloj1 reloj11;
    // End of variables declaration//GEN-END:variables
}
