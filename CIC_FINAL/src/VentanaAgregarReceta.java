
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;



public class VentanaAgregarReceta extends javax.swing.JFrame {
    private medico medico;
    private String usuarioId;
    private static Connection con;
    private static Statement stmt;
    private static String fecha;
    private String medicoIdActual;
    private Paciente paciente;

    public VentanaAgregarReceta(Paciente paciente, medico medico) {
        con = ConexionSQL.ConexionSQLServer();
        this.medico = medico;
        this.paciente = paciente;
        this.setSize(1200,800);
        initComponents();
        this.setLocationRelativeTo(null);
        lblNombreMed.setText(medico.getNombreMed()+" "
        +medico.getApellido1()+" "+medico.getApellido2());
        lblCedula.setText(medico.getNumCedula());
        LocalDate hoy = LocalDate.now();
        lblDate.setText(hoy.toString());
        lblnombrePac.setText(paciente.getNombrePac()+" "+paciente.getApellido1()+" "+paciente.getApellido2());
        lblNSS.setText(paciente.getNss());
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblCerrarSesion = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
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
        txtIndicaciones = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMedicamentos = new javax.swing.JTextArea();
        lblNombreMed = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblMedico1 = new javax.swing.JLabel();
        reloj11 = new Reloj1();
        lblCedula = new javax.swing.JLabel();
        lblFecha1 = new javax.swing.JLabel();
        lblNSS = new javax.swing.JLabel();
        lblMedico2 = new javax.swing.JLabel();
        lblnombrePac = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        lblCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblInicio.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flecha-izquierda (3).png"))); // NOI18N
        lblInicio.setText("Atrás");
        lblInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInicioMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        jLabelGuardar.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabelGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/disco-flexibleM.png"))); // NOI18N
        jLabelGuardar.setText("Guardar");
        jLabelGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelGuardarMouseClicked(evt);
            }
        });

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
                            .addComponent(jLabel2)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(lblCerrarSesion)))
                .addGap(0, 63, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(142, 142, 142)
                .addComponent(lblInicio)
                .addGap(157, 157, 157)
                .addComponent(jLabelGuardar)
                .addGap(120, 120, 120)
                .addComponent(lblCerrarSesion)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        lblInformacion.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        lblInformacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/receta(1).png"))); // NOI18N
        lblInformacion.setText(" Recetas");

        lblFecha.setFont(new java.awt.Font("Roboto", 2, 20)); // NOI18N
        lblFecha.setText("Fecha de Emision: ");

        lblMedicamento.setFont(new java.awt.Font("Roboto", 2, 20)); // NOI18N
        lblMedicamento.setText("Medicamentos:");

        lblMedicoR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblIDR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblIndicaciones.setFont(new java.awt.Font("Roboto", 2, 20)); // NOI18N
        lblIndicaciones.setText("Indicaciones: ");

        lblFechaR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblMedicamentoR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblMedico.setFont(new java.awt.Font("Tahoma", 2, 20)); // NOI18N
        lblMedico.setText("Medico Correspondiente: ");

        txtIndicaciones.setColumns(20);
        txtIndicaciones.setRows(5);
        jScrollPane1.setViewportView(txtIndicaciones);

        txtMedicamentos.setColumns(20);
        txtMedicamentos.setRows(5);
        jScrollPane2.setViewportView(txtMedicamentos);

        lblNombreMed.setFont(new java.awt.Font("Tahoma", 2, 20)); // NOI18N
        lblNombreMed.setText("jLabel1");

        lblDate.setFont(new java.awt.Font("Segoe UI", 2, 20)); // NOI18N
        lblDate.setText("jLabel1");

        lblMedico1.setFont(new java.awt.Font("Tahoma", 2, 20)); // NOI18N
        lblMedico1.setText("Num. Cedula:");

        lblCedula.setFont(new java.awt.Font("Tahoma", 2, 20)); // NOI18N
        lblCedula.setText("jLabel1");

        lblFecha1.setFont(new java.awt.Font("Roboto", 2, 20)); // NOI18N
        lblFecha1.setText("NSS:");

        lblNSS.setFont(new java.awt.Font("Segoe UI", 2, 20)); // NOI18N
        lblNSS.setText("jLabel1");

        lblMedico2.setFont(new java.awt.Font("Tahoma", 2, 20)); // NOI18N
        lblMedico2.setText("Nombre paciente:");

        lblnombrePac.setFont(new java.awt.Font("Segoe UI", 2, 20)); // NOI18N
        lblnombrePac.setText("jLabel1");

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
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblMedicoR, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblMedico1)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblMedicamentoR, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(302, 302, 302))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(61, 61, 61)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(lblFecha1)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lblNSS, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(lblMedico2)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(lblnombrePac, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblMedico)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblNombreMed, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblFecha)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblIDR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblFechaR, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(335, 335, 335)
                        .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblDate))
                            .addComponent(lblFechaR, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombreMed)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblIDR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMedico1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCedula))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMedicamentoR, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMedico2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblnombrePac))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNSS))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMedicoR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(421, 421, 421))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1192, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        atendMod m = new atendMod (this.medico, this.paciente);
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

    private void jLabelGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelGuardarMouseClicked
        String guardar = "INSERT INTO RecetaMedica "
        + "VALUES ('" + txtIndicaciones.getText() + "', "
        + "'" + lblDate.getText() + "', "
        + "'" + txtMedicamentos.getText() + "', "
        + this.medico.getIdMedico() + ", "
        + "'" + this.paciente.getNss() + "')";
        
        con = ConexionSQL.ConexionSQLServer();
        Statement st;
        
        try {
            st = con.createStatement();
            st.executeUpdate(guardar);
            showMessageDialog(null,"Receta agregada con éxito");
            txtMedicamentos.setText(" ");
            txtIndicaciones.setText(" ");
        } catch (SQLException ex) {
            Logger.getLogger(VentanaAgregarReceta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabelGuardarMouseClicked

    

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
                VentanaAgregarReceta vr = new VentanaAgregarReceta(ventanaCitasMed.getPaciente(), ventanaMedico.getMedico());
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
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFecha1;
    private javax.swing.JLabel lblFechaR;
    private javax.swing.JLabel lblIDR;
    private javax.swing.JLabel lblIndicaciones;
    private javax.swing.JLabel lblInformacion;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblMedicamento;
    private javax.swing.JLabel lblMedicamentoR;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblMedico1;
    private javax.swing.JLabel lblMedico2;
    private javax.swing.JLabel lblMedicoR;
    private javax.swing.JLabel lblNSS;
    private javax.swing.JLabel lblNombreMed;
    private javax.swing.JLabel lblnombrePac;
    private Reloj1 reloj11;
    private javax.swing.JTextArea txtIndicaciones;
    private javax.swing.JTextArea txtMedicamentos;
    // End of variables declaration//GEN-END:variables
}
