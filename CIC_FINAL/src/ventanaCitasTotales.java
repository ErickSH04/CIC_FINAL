
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ventanaCitasTotales extends javax.swing.JFrame {
    
    private medico medico;
    private static String usuarioId;
    private static Statement stmt;
    public static Connection con;

    public ventanaCitasTotales(medico medico) {//String usuarioId
        //this.usuarioId = usuarioId;
        this.medico=ventanaMedico.getMedico();
        initComponents();
        this.setLocationRelativeTo(this);
        con = ConexionSQL.ConexionSQLServer();
        m = (DefaultTableModel) tblCita.getModel();
        lblFecha.setText(obtenerFecha());
        llenarCitas();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        lblAtras = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCita = new javax.swing.JTable();
        cmbMes = new javax.swing.JComboBox<>();
        cmbDia = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        reloj11 = new Reloj1();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        lblSalir.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblSalir.setText("Salir");
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
        });

        lblAtras.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flecha-izquierda (3).png"))); // NOI18N
        lblAtras.setText("Atrás");
        lblAtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAtrasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAtras)
                    .addComponent(lblSalir))
                .addContainerGap(170, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(173, 173, 173)
                .addComponent(lblAtras)
                .addGap(187, 187, 187)
                .addComponent(lblSalir)
                .addContainerGap(217, Short.MAX_VALUE))
        );

        tblCita.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblCita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Hora", "Nombre", "Apellido1", "Apellido2", "numeroSeguro", "Estatus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCitaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCita);

        cmbMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mes...", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cmbMes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMesItemStateChanged(evt);
            }
        });

        cmbDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dia...", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        cmbDia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDiaItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Citas totales");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cita-medica.png"))); // NOI18N

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");

        lblBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar (1).png"))); // NOI18N
        lblBuscar.setText("Buscar paciente");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(406, 406, 406)
                .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFecha)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(223, 223, 223)
                                        .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(265, 265, 265)
                                        .addComponent(jLabel2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(138, 138, 138)))
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(102, 102, 102)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(210, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        loginHospitalNuevo hl = new loginHospitalNuevo();
        hl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        char c = (char) evt.getKeyCode();
        if (c == evt.VK_ENTER){
            buscarPaciente(txtBuscar.getText());
            if(txtBuscar.getText().equals("")){
                llenarCitas();
            }
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void cmbMesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMesItemStateChanged
        buscarPorFecha();
    }//GEN-LAST:event_cmbMesItemStateChanged
   
    private void lblAtrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAtrasMouseClicked
        ventanaCitasMed vm = new ventanaCitasMed(this.medico);
        vm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblAtrasMouseClicked
    
    private void buscarPorFecha(){
        int nDia=cmbDia.getSelectedIndex();
        int nMes=cmbMes.getSelectedIndex();
        
        if(nDia==0 && nMes==0){llenarCitas();}
        
        if((nDia>0) && (nMes>0)){
           String buscar="SELECT cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro, cit.estatus\n" +
            "FROM cita cit\n" +
            "INNER JOIN paciente pac ON pac.numeroSeguro = cit.numeroSeguro\n" +
            "WHERE cit.idMedico = '"+this.medico.getIdMedico()+"' \n" +
            "  AND MONTH(cit.fecha) = '"+nMes+"' \n" +
            "  AND DAY(cit.fecha) = '"+nDia+"';";
           
         try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(buscar);
                Object R[] = new Object[7];
                m.setRowCount(0);
                while (rs.next()) {
                    R[0] = rs.getObject("fecha");
                    R[1] = rs.getObject("hora");
                    R[2] = rs.getObject("nombrePac");
                    R[3] = rs.getObject("apellido1");
                    R[4] = rs.getObject("apellido2");
                    R[5] = rs.getObject("numeroSeguro");
                    R[6] = rs.getObject("estatus");
                    m.addRow(R);
                }
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }  
        }
          if((nDia==0) && (nMes>0)){
           String buscar="select cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro, cit.estatus\n" +
            "from cita cit\n" +
            "inner join paciente pac on (pac.numeroSeguro = cit.numeroSeguro)\n" +
            "WHERE cit.idMedico = '"+this.medico.getIdMedico()+"' \n" +
            "  AND MONTH(cit.fecha) = "+nMes;
         try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(buscar);
                Object R[] = new Object[7];
                m.setRowCount(0);
                while (rs.next()) {
                    R[0] = rs.getObject("fecha");
                    R[1] = rs.getObject("hora");
                    R[2] = rs.getObject("nombrePac");
                    R[3] = rs.getObject("apellido1");
                    R[4] = rs.getObject("apellido2");
                    R[5] = rs.getObject("numeroSeguro");
                    R[6] = rs.getObject("estatus");
                    m.addRow(R);
                }
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }  
        }
           if((nDia>0) && (nMes==0)){
           String buscar="select cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro, cit.estatus\n" +
            "from cita cit\n" +
            "inner join paciente pac on (pac.numeroSeguro = cit.numeroSeguro)\n" +
            "WHERE cit.idMedico = '"+this.medico.getIdMedico()+"' \n" +
            "  AND DAY(cit.fecha) = "+nDia+";";
         try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(buscar);
                Object R[] = new Object[7];
                m.setRowCount(0);
                while (rs.next()) {
                    R[0] = rs.getObject("fecha");
                    R[1] = rs.getObject("hora");
                    R[2] = rs.getObject("nombrePac");
                    R[3] = rs.getObject("apellido1");
                    R[4] = rs.getObject("apellido2");
                    R[5] = rs.getObject("numeroSeguro");
                    R[6] = rs.getObject("estatus");
                    m.addRow(R);
                }
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }  
        }
    }
   
    private void cmbDiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDiaItemStateChanged
        buscarPorFecha();
    }//GEN-LAST:event_cmbDiaItemStateChanged
    
    private void tblCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitaMouseClicked
        
    }//GEN-LAST:event_tblCitaMouseClicked
    
    private String obtenerFecha(){
        String fecha="";
        LocalDateTime hoy = LocalDateTime.now();  
        fecha = hoy.getMonthValue()+"/"+hoy.getDayOfMonth()+"/"+hoy.getYear();
        return fecha;
    }
    
    private void buscarPaciente(String nombre){
            String busqueda = nombre; 
            String query;
            con = ConexionSQL.ConexionSQLServer();
            ResultSet rs;
            m.setRowCount(0);
            
            query = "select cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro, cit.estatus\n" +
                "from cita cit\n" +
                "inner join paciente pac on (pac.numeroSeguro = cit.numeroSeguro)\n" +
                "where pac.nombrePac ="+ "'"+busqueda+"';";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                Object R[] = new Object[7];
            
                while (rs.next()) {
                    R[0] = rs.getObject("fecha");
                    R[1] = rs.getObject("hora");
                    R[2] = rs.getObject("nombrePac");
                    R[3] = rs.getObject("apellido1");
                    R[4] = rs.getObject("apellido2");
                    R[5] = rs.getObject("numeroSeguro");
                    R[6] = rs.getObject("estatus");
                    m.addRow(R);
                }
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } 
    }
    public void llenarCitas() {
        con = ConexionSQL.ConexionSQLServer();
        m.setRowCount(0);
        Statement stmt2;
        ResultSet resultadoMed;
       
        try {
            stmt = con.createStatement();
            String query = "select cit.fecha, cit.hora, pac.nombrePac, "+
                            "pac.apellido1, pac.apellido2, pac.numeroSeguro, cit.estatus\n" +
                            "from cita cit\n" +
                            "inner join paciente pac on (pac.numeroSeguro = cit.numeroSeguro)\n" +
                            "where cit.idMedico="+"'"+this.medico.getIdMedico()+"'"+";";
            ResultSet result = stmt.executeQuery(query);
            
            Object R[] = new Object[7];
            while (result.next()) {
                R[0] = result.getObject("fecha");
                R[1] = result.getObject("hora");
                R[2] = result.getObject("nombrePac");
                R[3] = result.getObject("apellido1");
                R[4] = result.getObject("apellido2");
                R[5] = result.getObject("numeroSeguro");
                R[6] = result.getObject("estatus");
                m.addRow(R);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ventanaCitasMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                ventanaCitasMed vcm = new ventanaCitasMed(ventanaMedico.getMedico());
                vcm.setVisible(true);
            }
        });
    }
    private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAtras;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private Reloj1 reloj11;
    private javax.swing.JTable tblCita;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
