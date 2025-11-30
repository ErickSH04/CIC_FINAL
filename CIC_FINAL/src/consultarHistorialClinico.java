import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class consultarHistorialClinico extends javax.swing.JFrame {
    private String idExp; 
    private Connection con;
    
    public consultarHistorialClinico(String idExpP) {
        this.setTitle("Consultar de Clinico");
        con = ConexionSQL.ConexionSQLServer();
        setSize(1200,800);
        this.setLocationRelativeTo(null);
        this.idExp = idExpP; 
        initComponents();
        this.setLocationRelativeTo(null);
        rellenarEtiquetas(idExp);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        lblAtras = new javax.swing.JLabel();
        lblUsuario1 = new javax.swing.JLabel();
        noName = new javax.swing.JLabel();
        reloj11 = new Reloj1();
        lblEspecialidad = new javax.swing.JLabel();
        lblMotivoAtencion = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblidExp = new javax.swing.JLabel();
        lblFechaAtencion = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTemperatura = new javax.swing.JLabel();
        lblFrecuenciaRespiratoria = new javax.swing.JLabel();
        lblPresionArterial = new javax.swing.JLabel();
        lblFrecuenciaCardiaca = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblNombreMedico1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        lblSalir.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblSalir.setText("Cerrar sesión");
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
        });

        lblAtras.setFont(new java.awt.Font("Roboto", 2, 22)); // NOI18N
        lblAtras.setIcon(new javax.swing.ImageIcon("C:\\CIC\\loginHospital\\src\\imagenes\\flecha-izquierda (3).png")); // NOI18N
        lblAtras.setText("Atrás");
        lblAtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAtrasMouseClicked(evt);
            }
        });

        lblUsuario1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSalir)
                    .addComponent(lblAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario1)
                .addGap(146, 146, 146)
                .addComponent(lblAtras)
                .addGap(64, 64, 64)
                .addComponent(lblSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        noName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        noName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/documentacionPaciente.png"))); // NOI18N
        noName.setText("Consultar Historial Clinico");

        reloj11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        lblEspecialidad.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblEspecialidad.setText("jLabel7");

        lblMotivoAtencion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblMotivoAtencion.setText("jLabel8");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel5.setText("Datos del paciente");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel1.setText("Id Exp:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel2.setText("Fecha atencion:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel3.setText("Especialidad:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel4.setText("Motivo Atencion");

        lblidExp.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblidExp.setText("jLabel5");

        lblFechaAtencion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblFechaAtencion.setText("jLabel6");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel6.setText("Temperatura:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel8.setText("Frecuencia Respiratoria:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel9.setText("Presion Arterial:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel10.setText("Frecuencia Cardiaca:");

        lblTemperatura.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTemperatura.setText("jLabel12");

        lblFrecuenciaRespiratoria.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblFrecuenciaRespiratoria.setText("jLabel12");

        lblPresionArterial.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblPresionArterial.setText("jLabel12");

        lblFrecuenciaCardiaca.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblFrecuenciaCardiaca.setText("jLabel12");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel7.setText("Nombre Medico:");

        lblNombreMedico1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblNombreMedico1.setText("jLabel7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jLabel5)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel1))
                                            .addGap(70, 70, 70)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(lblFechaAtencion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblidExp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblNombreMedico1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel8)
                                                .addComponent(jLabel9)
                                                .addComponent(jLabel10)
                                                .addComponent(jLabel4)
                                                .addComponent(jLabel3))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(26, 26, 26)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblPresionArterial, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblFrecuenciaCardiaca, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblMotivoAtencion, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblFrecuenciaRespiratoria, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(303, 303, 303)))
                                .addGap(99, 99, 99))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(noName, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(noName))
                .addGap(60, 60, 60)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblidExp)
                    .addComponent(jLabel1))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblFechaAtencion))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblNombreMedico1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblEspecialidad))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblMotivoAtencion))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblTemperatura))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblFrecuenciaRespiratoria))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblPresionArterial))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblFrecuenciaCardiaca))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        loginHospitalNuevo hl = new loginHospitalNuevo();
        hl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblAtrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAtrasMouseClicked
        ventanaHistorialClinico hc = new ventanaHistorialClinico(ventanaHistorialClinico.usuarioId);
        hc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblAtrasMouseClicked
    
private void rellenarEtiquetas(String idExpP){
    ResultSet rs;
    Statement st;

    String q = 
        "SELECT HC.idExp, HC.fecha_atencion, " +
        "       M.nombreMed + ' ' + M.apellido1 + ' ' + M.apellido2 AS nombreMedico, " +
        "       M.Especialidad, HC.motivo_atencion, " +
        "       HC.temperatura, HC.frecuencia_respiratoria, " +
        "       HC.presion_arterial, HC.frecuencia_cardiaca " +
        "FROM expediente_clinico HC " +
        "JOIN MEDICO M ON HC.id_medico = M.idMedico " +
        "WHERE  HC.idExp = '" + idExpP + "' " +
        "ORDER BY HC.fecha_atencion DESC";

    // Variables locales (igual que tu método original)
    String idExp = "", fecha = "", medico = "", especialidad = "", motivo = "";
    String temperatura = "", frecResp = "", presion = "", frecCard = "";

    try{
        st = con.createStatement();
        rs = st.executeQuery(q);

        if(rs.next()){  // solo tomamos el expediente más reciente

            idExp = rs.getString("idExp");
            fecha = rs.getString("fecha_atencion");
            medico = rs.getString("nombreMedico");
            especialidad = rs.getString("Especialidad");
            motivo = rs.getString("motivo_atencion");
            temperatura = rs.getString("temperatura");
            frecResp = rs.getString("frecuencia_respiratoria");
            presion = rs.getString("presion_arterial");
            frecCard = rs.getString("frecuencia_cardiaca");
        }

    }catch(SQLException e){
        System.out.println(e.getMessage());
    }

    // Asignación a etiquetas (misma estructura que tu método original)
    lblidExp.setText(idExp);
    lblFechaAtencion.setText(fecha);
    lblNombreMedico1.setText(medico);
    lblEspecialidad.setText(especialidad);
    lblMotivoAtencion.setText(motivo);
    lblTemperatura.setText(temperatura);
    lblFrecuenciaRespiratoria.setText(frecResp);
    lblPresionArterial.setText(presion);
    lblFrecuenciaCardiaca.setText(frecCard);
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
            java.util.logging.Logger.getLogger(expMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(expMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(expMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(expMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             consultarHistorialClinico c = new consultarHistorialClinico(ventanaHistorialClinico.usuarioId);
               c.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAtras;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblFechaAtencion;
    private javax.swing.JLabel lblFrecuenciaCardiaca;
    private javax.swing.JLabel lblFrecuenciaRespiratoria;
    private javax.swing.JLabel lblMotivoAtencion;
    private javax.swing.JLabel lblNombreMedico1;
    private javax.swing.JLabel lblPresionArterial;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblTemperatura;
    private javax.swing.JLabel lblUsuario1;
    private javax.swing.JLabel lblidExp;
    private javax.swing.JLabel noName;
    private Reloj1 reloj11;
    // End of variables declaration//GEN-END:variables
}
