
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ventaPagos extends javax.swing.JFrame {

    private static String usuarioId;
    private static Statement stmt;
    public static Connection con;

    public ventaPagos(String usuarioId) {//String usuarioId
        this.usuarioId = usuarioId;
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
        lblInicio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCita = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cmbMes = new javax.swing.JComboBox<>();
        cmbDia = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        lblPago = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();

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

        lblInicio.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pagina-de-inicio.png"))); // NOI18N
        lblInicio.setText("Inicio");
        lblInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInicioMouseClicked(evt);
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
                    .addComponent(lblSalir)
                    .addComponent(lblInicio))
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(155, 155, 155)
                .addComponent(lblInicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSalir)
                .addGap(119, 119, 119))
        );

        tblCita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblCita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Hora", "Nombre", "Apellido1", "Apellido2", "numeroSeguro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
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

        jLabel1.setText("Reloj ");

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
        jLabel2.setText("Registro de pagos");

        lblPago.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dar-dinero.png"))); // NOI18N
        lblPago.setText("Pago");

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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(92, 92, 92))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                                .addComponent(lblFecha)
                                .addGap(0, 89, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(95, 95, 95))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(201, 201, 201)
                                    .addComponent(jLabel2)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(216, 216, 216)
                                .addComponent(lblPago)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblPago)
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(231, Short.MAX_VALUE))
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
        System.exit(0);
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
      
        int nMes=cmbMes.getSelectedIndex();
        cmbMes.getItemAt(nMes);
        
        String buscar="select cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro\n" +
        "from cita cit\n" +
        "inner join paciente pac on (pac.numeroSeguro = cit.numeroSeguro)\n" +
        "where Month(cit.fecha) = "+nMes+";";
        
         try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(buscar);
                Object R[] = new Object[6];
                m.setRowCount(0);
                while (rs.next()) {
                    R[0] = rs.getObject("fecha");
                    R[1] = rs.getObject("hora");
                    R[2] = rs.getObject("nombrePac");
                    R[3] = rs.getObject("apellido1");
                    R[4] = rs.getObject("apellido2");
                    R[5] = rs.getObject("numeroSeguro");
                    m.addRow(R);
                }
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } 
    
    }//GEN-LAST:event_cmbMesItemStateChanged
   public static String obtenerIdMed(String correo){
       con = ConexionSQL.ConexionSQLServer();
        Statement stmt2;
        ResultSet resultadoMed;
        String identificadorMed="";
        
        try {
            stmt2 = con.createStatement();
            String idMed = "select med.idMedico \n" +
                            "from medico med\n" +
                            "inner join usuario u on (med.Correo = u.correo)\n" +
                            "where med.Correo = "+"'"+correo+"'";
            System.out.println(idMed);
            
            resultadoMed = stmt2.executeQuery(idMed);
            
            while(resultadoMed.next()){
                identificadorMed = resultadoMed.getString("idMedico");
            } 
           
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ventanaCitasMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return identificadorMed;
   }
    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        ventanaMedico vm = new ventanaMedico(this.usuarioId);
        vm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

    private void cmbDiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDiaItemStateChanged
        
        int nDia=cmbDia.getSelectedIndex();
        cmbMes.getItemAt(nDia);
        
        String buscar="select cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro\n" +
        "from cita cit\n" +
        "inner join paciente pac on (pac.numeroSeguro = cit.numeroSeguro)\n" +
        "where Day(cit.fecha) = "+nDia+";";
        
      
         try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(buscar);
                Object R[] = new Object[6];
                m.setRowCount(0);
                while (rs.next()) {
                    R[0] = rs.getObject("fecha");
                    R[1] = rs.getObject("hora");
                    R[2] = rs.getObject("nombrePac");
                    R[3] = rs.getObject("apellido1");
                    R[4] = rs.getObject("apellido2");
                    R[5] = rs.getObject("numeroSeguro");
                    m.addRow(R);
                }
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } 
    }//GEN-LAST:event_cmbDiaItemStateChanged
    
    private void tblCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitaMouseClicked
      Object arreglo []= new Object [6];
     
      for(int i=0;i<6;i++){
          arreglo [i]= m.getDataVector().subList(i, i);
          System.out.println(arreglo[i].toString());
      }
      
      
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
            
            query = "select cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro\n" +
                "from cita cit\n" +
                "inner join paciente pac on (pac.numeroSeguro = cit.numeroSeguro)\n" +
                "where pac.nombrePac ="+ "'"+busqueda+"';";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                Object R[] = new Object[6];
            
                while (rs.next()) {
                    R[0] = rs.getObject("fecha");
                    R[1] = rs.getObject("hora");
                    R[2] = rs.getObject("nombrePac");
                    R[3] = rs.getObject("apellido1");
                    R[4] = rs.getObject("apellido2");
                    R[5] = rs.getObject("numeroSeguro");
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
        String identificadorMed="";
        
        try {
            
            stmt2 = con.createStatement();
            String idMed = "select med.idMedico \n" +
                            "from medico med\n" +
                            "inner join usuario u on (med.Correo = u.correo)\n" +
                            "where med.Correo = "+"'"+this.usuarioId+"'";
            System.out.println(idMed);
            
            resultadoMed = stmt2.executeQuery(idMed);
            
            while(resultadoMed.next()){
                identificadorMed = resultadoMed.getString("idMedico");
            }
            System.out.println(identificadorMed);
            
            showMessageDialog(null,"Rellenando la tabla cawn..."+this.usuarioId+" "+identificadorMed);
            stmt = con.createStatement();
            String query = "select cit.fecha, cit.hora, pac.nombrePac, "+
                            "pac.apellido1, pac.apellido2, pac.numeroSeguro\n" +
                            "from cita cit\n" +
                            "inner join paciente pac on (pac.numeroSeguro = cit.numeroSeguro)\n" +
                            "where cit.idMedico="+"'"+identificadorMed+"'"+";";
            ResultSet result = stmt.executeQuery(query);
            
            Object R[] = new Object[6];
            while (result.next()) {
                R[0] = result.getObject("fecha");
                R[1] = result.getObject("hora");
                R[2] = result.getObject("nombrePac");
                R[3] = result.getObject("apellido1");
                R[4] = result.getObject("apellido2");
                R[5] = result.getObject("numeroSeguro");
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
                ventanaCitasMed vcm = new ventanaCitasMed(usuarioId);
                vcm.setVisible(true);
            }
        });
    }
    private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblPago;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblCita;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
