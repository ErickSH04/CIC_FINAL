

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


public class act_eli_Expediente extends javax.swing.JFrame {

    private static String usuarioId;
    private static medico medico;
    private static Statement stmt;
    public static Connection con;
    private static ObjetoExpediente oe;

    public act_eli_Expediente( medico medico) {//String usuarioId
       
       this.medico = medico;
       initComponents();
       setSize(1200,800);
       this.setLocationRelativeTo(this);
       ObjetoExpediente o = new ObjetoExpediente();
       con = ConexionSQL.ConexionSQLServer();
       m = (DefaultTableModel) tblExp.getModel();
       lblFecha.setText(obtenerFecha());
       llenarTabla();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        lblEliminados = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblExp = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        reloj11 = new Reloj1();
        btnRefresh = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        lblSalir.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblSalir.setText("Cerrar sesión");
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

        lblEliminados.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblEliminados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/borrar.png"))); // NOI18N
        lblEliminados.setText("Eliminados");
        lblEliminados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEliminadosMouseClicked(evt);
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
                    .addComponent(lblEliminados)
                    .addComponent(lblSalir)
                    .addComponent(lblInicio))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(123, 123, 123)
                .addComponent(lblInicio)
                .addGap(115, 115, 115)
                .addComponent(lblEliminados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(lblSalir)
                .addGap(119, 119, 119))
        );

        tblExp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblExp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Identificador", "Nombre", "Apellido paterno", "Apellido materno", "NSS", "Fecha", "Motivo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblExp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblExpMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblExp);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/carpeta.png"))); // NOI18N
        jLabel3.setText("Expedientes");

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");

        lblBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar (1).png"))); // NOI18N
        lblBuscar.setText("Buscar expediente");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        btnRefresh.setText("Refresh");
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(382, 382, 382)
                .addComponent(lblBuscar)
                .addGap(26, 26, 26)
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
                                .addGap(48, 48, 48)
                                .addComponent(btnRefresh))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(280, 280, 280)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 227, Short.MAX_VALUE)
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 899, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 111, 111)
                .addComponent(btnRefresh)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(256, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        loginHospitalNuevo hl = new loginHospitalNuevo();
        hl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        char c = (char) evt.getKeyCode();
        if (c == evt.VK_ENTER) {
            buscarPaciente(txtBuscar.getText());
            if (txtBuscar.getText().equals("")) {
                llenarTabla();
            }
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        ventanaMedico vm = new ventanaMedico(ventanaMedico.getMedico().getCorreo());
        vm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_lblInicioMouseClicked


    public static ObjetoExpediente crearExp(String fec, int id){
        Statement st;
        ResultSet rs;
        String idExp="",idMed="", ns = "", temp="", presionArt="", f = "", motivo = "";
        int frecResp = 0, frecCard = 0;
        
        String queryalv="select * from expediente_clinico\n" +
                            "where idExp = "+id+" and fecha_atencion= '"+fec+"'";
        try {
            st = con.createStatement();
            rs = st.executeQuery(queryalv);
            
            while(rs.next()){
                idExp = rs.getString("idExp");
                idMed = rs.getString("id_medico");
                ns = rs.getString("nss");
                temp = rs.getString("temperatura");
                frecResp = rs.getInt("frecuencia_respiratoria");
                presionArt = rs.getString("presion_arterial");
                frecCard = rs.getInt("frecuencia_cardiaca");
                motivo = rs.getString("motivo_atencion");
                f = rs.getString("fecha_atencion");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Expediente_elegido.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ObjetoExpediente o = new ObjetoExpediente();
        o.setIdExp(idExp);
        o.setIdMed(idMed);
        o.setNs(ns);
        o.setTemp(temp);
        o.setFrecResp(frecResp);
        o.setPresionArt(presionArt);
        o.setFrecCard(frecCard);
        o.setMotivo(motivo);
        o.setFec(f);
        
        return o;
    }
    
    
    private void tblExpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblExpMouseClicked
         con = ConexionSQL.ConexionSQLServer();
        
        int confirmacion=0;
        int respuesta = 0;
        
        respuesta = Integer.parseInt(JOptionPane.showInputDialog("¿Qué operación desea realizar?\n"
                + "1.- Eliminar\n"
                + "2.- Consulta detalles del expediente"));
        
        
    System.out.println("Respuesta: " + respuesta);
    if (respuesta == 1) {//elimina
        JOptionPane.showConfirmDialog(this,"¿Estás seguro de querer eliminar este expediente?");
            switch(confirmacion){
                    
                case 0:
                    Object arreglo[] = new Object[3];
                    int renglon = tblExp.getSelectedRow();
                    String  motivo= "", fecha = "";
                    int identificador=0;
                    if (renglon == -1) {
                        JOptionPane.showMessageDialog(this, "Seleccione un registro primero.");
                    return; // Detiene la ejecución del case
}
                    for (int i = 0; i < arreglo.length; i++) {
                        arreglo[i] = tblExp.getValueAt(renglon, i);
                        
                    }

                    identificador = Integer.parseInt(arreglo[0].toString());
                    fecha = arreglo[1].toString();
                    motivo = arreglo[2].toString();
                    String inserta = "INSERT INTO expedientes_eliminados\n" +
                    "SELECT * FROM expediente_clinico\n" +
                    "where idExp ="+identificador;
                    String query="delete from expediente_clinico\n" +
                    "where idExp ="+identificador;
                    
                    Statement stmt;
                try {
                    stmt = con.createStatement();
                    stmt.executeUpdate(inserta);
                    stmt.executeUpdate(query);
                    JOptionPane.showMessageDialog(this, "Eliminando...");
                    JOptionPane.showMessageDialog(null, "Expediente eliminado con éxito.");
                    llenarTabla();
                } catch (SQLException ex) {
                    Logger.getLogger(ventanaCitasMed.class.getName()).log(Level.SEVERE, null, ex);
                }
                    break;
                case 1:break; case 2: break;
            }   
        }
        if (respuesta == 2) {//consulta
            int co=0;
            
            JOptionPane.showConfirmDialog(this,"¿Estás seguro de querer consultar este expediente?");
            switch(co){
                    
                case 0:
                    Object arreglo[] = new Object[7];
                    int renglon = tblExp.getSelectedRow();
                    String nombre="",apellido1="",apellido2="", nss="", motivo= "", fecha = "";
                    
                    int identificador=0;
                    if (renglon == -1) {
                        JOptionPane.showMessageDialog(this, "Seleccione un registro primero.");
                    return; // Detiene la ejecución del case
}
                    for (int i = 0; i < arreglo.length; i++) {
                        arreglo[i] = tblExp.getValueAt(renglon, i);
                        
                    }

                    identificador = Integer.parseInt(arreglo[0].toString());
                    nombre = arreglo[1].toString();
                    apellido1 = arreglo[2].toString();
                    apellido2 = arreglo[3].toString();
                    nss = arreglo[4].toString();
                    fecha = arreglo[5].toString();
                    motivo = arreglo[6].toString();
                    
                    ConsultarExp x = new ConsultarExp(nss, this.medico,identificador);
                    x.setVisible(true);
                    this.setVisible(false);
                    break;
                case 1:break; case 2: break;
            }
           
        if (respuesta == 3) {
        //no hace nada
        }
    }
    }//GEN-LAST:event_tblExpMouseClicked

    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked
        llenarTabla();
    }//GEN-LAST:event_btnRefreshMouseClicked

    private void lblEliminadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEliminadosMouseClicked
        eliminadosExp e = new eliminadosExp(ventanaMedico.getMedico());
        e.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblEliminadosMouseClicked

    private String obtenerFecha() {
        String fecha = "";
        LocalDateTime hoy = LocalDateTime.now();
        fecha = hoy.getMonthValue() + "/" + hoy.getDayOfMonth() + "/" + hoy.getYear();
        return fecha;
    }

    private void buscarPaciente(String nombre) {
        String busqueda = nombre;
        String query;
        con = ConexionSQL.ConexionSQLServer();
        ResultSet rs;
        m.setRowCount(0);

        query = "select ec.idExp, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro, ec.fecha_atencion, ec.motivo_atencion\n" +
            "from PACIENTE pac\n" +
            "inner join expediente_clinico ec on (ec.nss = pac.numeroSeguro)\n" +
            "WHERE pac.nombrePac LIKE '%" + busqueda + "%'";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            Object R[] = new Object[8];

            while (rs.next()) {
                R[0] = rs.getObject("idExp");
                R[1] = rs.getObject("nombrePac");
                R[2] = rs.getObject("apellido1");
                R[3] = rs.getObject("apellido2");
                R[4] = rs.getObject("apellido2");
                R[5] = rs.getObject("numeroSeguro");
                R[6] = rs.getObject("fecha_atencion");
                R[7] = rs.getObject("motivo_atencion");
                m.addRow(R);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void llenarTabla() {
        con = ConexionSQL.ConexionSQLServer();
        m.setRowCount(0);
       
        try {

            stmt = con.createStatement();
            String query="select ec.idExp, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro, ec.fecha_atencion, ec.motivo_atencion\n" +
            "from PACIENTE pac\n" +
            "inner join expediente_clinico ec on (ec.nss = pac.numeroSeguro)\n" +
            "where ec.id_medico = '"+this.medico.getIdMedico()+"'";
    
            ResultSet result = stmt.executeQuery(query);

            Object R[] = new Object[7];
            while (result.next()) {
                R[0] = result.getObject("idExp");
                R[1] = result.getObject("nombrePac");
                R[2] = result.getObject("apellido1");
                R[3] = result.getObject("apellido2");
                R[4] = result.getObject("numeroSeguro");
                R[5] = result.getObject("fecha_atencion");
                R[6] = result.getObject("motivo_atencion");
                m.addRow(R);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(act_eli_Expediente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                act_eli_Expediente aee = new act_eli_Expediente(ventanaMedico.getMedico());
                aee.setVisible(true);
            }
        });
    }
    private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblEliminados;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private Reloj1 reloj11;
    private javax.swing.JTable tblExp;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
