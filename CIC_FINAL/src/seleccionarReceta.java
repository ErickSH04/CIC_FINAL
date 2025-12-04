

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


public class seleccionarReceta extends javax.swing.JFrame {

    private static String usuarioId;
    private static medico medico;
    private static Statement stmt;
    public static Connection con;
    private static objetoReceta or;

    public seleccionarReceta(medico medico) {//String usuarioId
       this.medico = medico;
       initComponents();
       setSize(1200,800);
       this.setLocationRelativeTo(this);
       ObjetoExpediente o = new ObjetoExpediente();
       con = ConexionSQL.ConexionSQLServer();
       m = (DefaultTableModel) tblRec.getModel();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRec = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
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
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(192, 192, 192)
                .addComponent(lblInicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSalir)
                .addGap(119, 119, 119))
        );

        tblRec.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblRec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Identificador", "Nombre", "Apellido paterno", "Apellido materno", "NSS", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRecMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRec);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/carpeta.png"))); // NOI18N
        jLabel3.setText("Recetas");

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");

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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(btnRefresh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 601, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(216, 216, 216)))
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 899, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFecha)
                        .addGap(88, 88, 88))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(28, 28, 28)
                .addComponent(lblFecha)
                .addGap(75, 75, 75)
                .addComponent(btnRefresh)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, Short.MAX_VALUE))
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

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        ventanaMedico vm = new ventanaMedico(ventanaMedico.getMedico().getCorreo());
        vm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_lblInicioMouseClicked


    public static objetoReceta crearRec(String fec, int id){
        Statement st;
        ResultSet rs;
        String indicaciones="", fecha = "", medicamentos="", nombre="", apellido1 = "", apellido2 = "", ns = "";
        int idRec=0;
        String llenarTabla="select rm.idReceta, rm.indicaciones, rm.fechaEmision, rm.medicamentos, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro\n" +
        "from RecetaMedica rm\n" +
        "inner join PACIENTE pac on (rm.numeroSeguro = pac.numeroSeguro)\n" +
        "where rm.idMedico ="+Integer.parseInt(ventanaMedico.getMedico().getIdMedico());
        try {
            st = con.createStatement();
            rs = st.executeQuery(llenarTabla);
            
            while(rs.next()){
                idRec = rs.getInt("idReceta");
                indicaciones = rs.getString("indicaciones");
                fecha = rs.getString("fechaEmision");
                medicamentos = rs.getString("medicamentos");
                nombre = rs.getString("nombrePac");
                apellido1 = rs.getString("apellido1");
                apellido2 = rs.getString("apellido2");
                ns = rs.getString("numeroSeguro");

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Expediente_elegido.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        objetoReceta o = new objetoReceta();
        o.setIdRec(idRec);
        o.setNumeroSeguro(ns);
        o.setFechaEmision(fecha);
        return o;
    }
    
    public static objetoReceta getReceta(){
        return or;
    }
    
    private void tblRecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRecMouseClicked
        con = ConexionSQL.ConexionSQLServer();
        int confirmacion=0;
        JOptionPane.showConfirmDialog(this,"¿Estás seguro de querer consultar detalles de esta receta?");
            switch(confirmacion){
                    
                case 0:
                    Object arreglo[] = new Object[6];
                    int renglon = tblRec.getSelectedRow();
                    String nombre="", apellido2="",apellido1="", nss= "", fecha = "";
                    int identificador=0;
                    if (renglon == -1) {
                        JOptionPane.showMessageDialog(this, "Seleccione un registro primero.");
                        return; // Detiene la ejecución del case
                    }
                    
                    for (int i = 0; i < arreglo.length; i++) {
                        arreglo[i] = tblRec.getValueAt(renglon, i);
                        
                    }

                    identificador = Integer.parseInt(arreglo[0].toString());
                    nombre = arreglo[1].toString();
                    apellido1 = arreglo[2].toString();
                    apellido2 = arreglo[3].toString();
                    nss = arreglo[4].toString();
                    fecha = arreglo[5].toString();
                    
                    System.out.println("identificador: "+identificador);
                    System.out.println("nombre: "+nombre);
                    System.out.println("apellido1: "+apellido1);
                    System.out.println("apellido2: "+apellido2);
                    System.out.println("Numero de seguro social: "+nss);
                    System.out.println("Fecha de emision: "+fecha);
               
                    
                    objetoReceta objRec = new objetoReceta();
                    objRec = crearRec(nss, identificador);
                    
                    ConsultarRecetas r = new ConsultarRecetas(nss,ventanaMedico.getMedico() ,identificador);
                    r.setVisible(true);
                    this.setVisible(false);
                    break;
                case 1:break; case 2: break;
            }
    }//GEN-LAST:event_tblRecMouseClicked

    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked
        llenarTabla();
    }//GEN-LAST:event_btnRefreshMouseClicked

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
            String query="select rm.idReceta, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro, rm.fechaEmision\n" +
            "from RecetaMedica rm\n" +
            "inner join PACIENTE pac on (rm.numeroSeguro = pac.numeroSeguro)\n" +
            "where rm.idMedico ="+Integer.parseInt(ventanaMedico.getMedico().getIdMedico());
    
            ResultSet result = stmt.executeQuery(query);

            Object R[] = new Object[6];
            while (result.next()) {
                R[0] =  result.getObject("idReceta");
                R[1] =  result.getObject("nombrePac");
                R[2] =  result.getObject("apellido1");
                R[3] =  result.getObject("apellido2");
                R[4] =  result.getObject("numeroSeguro");
                R[5] =  result.getObject("fechaEmision");
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
                seleccionarReceta sr = new seleccionarReceta(ventanaMedico.getMedico());
                sr.setVisible(true);
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
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private Reloj1 reloj11;
    private javax.swing.JTable tblRec;
    // End of variables declaration//GEN-END:variables
}
