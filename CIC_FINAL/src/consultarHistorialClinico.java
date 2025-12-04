import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class consultarHistorialClinico extends javax.swing.JFrame {
    private String idExp; 
    private Connection con;
    
    public consultarHistorialClinico(String idExpP) {
        this.setTitle("Consultar de Historial Clinico");
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
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        lblAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pagina-de-inicio.png"))); // NOI18N
        lblAtras.setText("Inicio");
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(lblSalir))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(lblAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario1)
                .addGap(143, 143, 143)
                .addComponent(lblAtras)
                .addGap(67, 67, 67)
                .addComponent(lblSalir)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 771));

        noName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        noName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/documentacionPaciente.png"))); // NOI18N
        noName.setText("Consultar Historial Clinico");
        getContentPane().add(noName, new org.netbeans.lib.awtextra.AbsoluteConstraints(511, 30, 311, -1));

        reloj11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        getContentPane().add(reloj11, new org.netbeans.lib.awtextra.AbsoluteConstraints(828, 16, -1, -1));

        lblEspecialidad.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblEspecialidad.setText("jLabel7");
        getContentPane().add(lblEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 350, 223, -1));

        lblMotivoAtencion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblMotivoAtencion.setText("jLabel8");
        getContentPane().add(lblMotivoAtencion, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 390, 213, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel5.setText("Datos del paciente");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel1.setText("Id Exp:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 190, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel2.setText("Fecha atencion:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 246, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel3.setText("Especialidad:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 350, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel4.setText("Motivo Atencion");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 390, -1, -1));

        lblidExp.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblidExp.setText("jLabel5");
        getContentPane().add(lblidExp, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 190, 223, -1));

        lblFechaAtencion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblFechaAtencion.setText("jLabel6");
        getContentPane().add(lblFechaAtencion, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 246, 223, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel6.setText("Temperatura:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 447, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel8.setText("Frecuencia Respiratoria:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 507, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel9.setText("Presion Arterial:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 561, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel10.setText("Frecuencia Cardiaca:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 609, -1, -1));

        lblTemperatura.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTemperatura.setText("jLabel12");
        getContentPane().add(lblTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 447, 213, -1));

        lblFrecuenciaRespiratoria.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblFrecuenciaRespiratoria.setText("jLabel12");
        getContentPane().add(lblFrecuenciaRespiratoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 507, 213, -1));

        lblPresionArterial.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblPresionArterial.setText("jLabel12");
        getContentPane().add(lblPresionArterial, new org.netbeans.lib.awtextra.AbsoluteConstraints(719, 561, 213, -1));

        lblFrecuenciaCardiaca.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblFrecuenciaCardiaca.setText("jLabel12");
        getContentPane().add(lblFrecuenciaCardiaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(719, 609, 213, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel7.setText("Nombre Medico:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(507, 310, -1, -1));

        lblNombreMedico1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblNombreMedico1.setText("jLabel7");
        getContentPane().add(lblNombreMedico1, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 310, 223, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 0, -1, -1));

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
    
private void rellenarEtiquetas(String idExpP) {

    // Variables para los datos
    String idExp = "", fecha = "", medico = "", especialidad = "", motivo = "";
    String temperatura = "", frecResp = "", presion = "", frecCard = "";

    try {
        // ================================
        //  PRIMERA BÚSQUEDA: expediente_clinico
        // ================================
        String sql1 =
            "SELECT HC.idExp, HC.fecha_atencion, " +
            "       M.nombreMed + ' ' + M.apellido1 + ' ' + M.apellido2 AS nombreMedico, " +
            "       M.Especialidad, HC.motivo_atencion, " +
            "       HC.temperatura, HC.frecuencia_respiratoria, " +
            "       HC.presion_arterial, HC.frecuencia_cardiaca " +
            "FROM expediente_clinico HC " +
            "JOIN MEDICO M ON HC.id_medico = M.idMedico " +
            "WHERE HC.idExp = ? ";

        PreparedStatement ps1 = con.prepareStatement(sql1);
        ps1.setString(1, idExpP);

        ResultSet rs1 = ps1.executeQuery();

        boolean encontrado = false;

        if (rs1.next()) {
            encontrado = true;
            idExp = rs1.getString("idExp");
            fecha = rs1.getString("fecha_atencion");
            medico = rs1.getString("nombreMedico");
            especialidad = rs1.getString("Especialidad");
            motivo = rs1.getString("motivo_atencion");
            temperatura = rs1.getString("temperatura");
            frecResp = rs1.getString("frecuencia_respiratoria");
            presion = rs1.getString("presion_arterial");
            frecCard = rs1.getString("frecuencia_cardiaca");
        }

        rs1.close();
        ps1.close();

        // ================================
        //  SI NO ESTÁ → BUSCAR EN eliminados
        // ================================
        if (!encontrado) {

            String sql2 =
                "SELECT EE.idExp, EE.fecha_atencion, " +
                "       M.nombreMed + ' ' + M.apellido1 + ' ' + M.apellido2 AS nombreMedico, " +
                "       M.Especialidad, EE.motivo_atencion, " +
                "       EE.temperatura, EE.frecuencia_respiratoria, " +
                "       EE.presion_arterial, EE.frecuencia_cardiaca " +
                "FROM expedientes_eliminados EE " +
                "JOIN MEDICO M ON EE.id_medico = M.idMedico " +
                "WHERE EE.idExp = ? ";

            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, idExpP);

            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                idExp = rs2.getString("idExp");
                fecha = rs2.getString("fecha_atencion");
                medico = rs2.getString("nombreMedico");
                especialidad = rs2.getString("Especialidad");
                motivo = rs2.getString("motivo_atencion");
                temperatura = rs2.getString("temperatura");
                frecResp = rs2.getString("frecuencia_respiratoria");
                presion = rs2.getString("presion_arterial");
                frecCard = rs2.getString("frecuencia_cardiaca");
            }

            rs2.close();
            ps2.close();
        }

    } catch (SQLException e) {
        System.out.println("Error en rellenarEtiquetas: " + e.getMessage());
    }

    // ================================
    //  ACTUALIZAR ETIQUETAS
    // ================================
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
    private javax.swing.JPanel jPanel1;
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
