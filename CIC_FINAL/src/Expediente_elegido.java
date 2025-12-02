
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Erick
 */
public class Expediente_elegido extends javax.swing.JFrame {
    private static Connection con;
    private medico medico;
    public static Paciente paciente; 
    private static ObjetoExpediente oe;

    
    public Expediente_elegido(Paciente paciente, medico medico) {
        initComponents();
        this.paciente = paciente;
        this.medico = medico;
        con = ConexionSQL.ConexionSQLServer();
        this.setSize(1250, 750);
        this.setLocationRelativeTo(this);
        this.setVisible(true);
        lblHeader.setLocation(625, 100);
        ObjetoExpediente o = new ObjetoExpediente();
        m = (DefaultTableModel) tblExp1.getModel();
        llenarTabla();
        rellenarEtiquetas(paciente.getNss());
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblExp = new javax.swing.JTable();
        reloj11 = new Reloj1();
        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        lblAtras = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblExp1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        lblNss = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblEmergencia = new javax.swing.JLabel();
        lblCronica = new javax.swing.JLabel();
        lblFamilia = new javax.swing.JLabel();
        lblEdad = new javax.swing.JLabel();
        lblAlcohol = new javax.swing.JLabel();
        lblTabaco = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblApellidos = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        reloj12 = new Reloj1();
        lblHeader = new javax.swing.JLabel();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        lblAtras.setFont(new java.awt.Font("Roboto", 2, 22)); // NOI18N
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
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(177, 177, 177)
                .addComponent(lblAtras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSalir)
                .addGap(119, 119, 119))
        );

        tblExp1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblExp1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Identificador", "Fecha", "Motivo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblExp1.setPreferredSize(new java.awt.Dimension(800, 80));
        tblExp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblExp1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblExp1);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel8.setText("Enfermedad crónica:");

        lblNss.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblNss.setText("jLabel7");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel9.setText("Familiares con enfermedad:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel10.setText("Consumo de tabaco");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel11.setText("Consumo de alcohol:");

        lblEmergencia.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblEmergencia.setText("jLabel12");

        lblCronica.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblCronica.setText("jLabel12");

        lblFamilia.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblFamilia.setText("jLabel12");

        lblEdad.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblEdad.setText("jLabel8");

        lblAlcohol.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblAlcohol.setText("jLabel12");

        lblTabaco.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTabaco.setText("jLabel12");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel1.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel2.setText("Apellidos:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel3.setText("NSS:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel4.setText("Edad:");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblNombre.setText("jLabel5");

        lblApellidos.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblApellidos.setText("jLabel6");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        jLabel6.setText("Contacto de emergencia:");

        lblHeader.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/carpeta.png"))); // NOI18N
        lblHeader.setText("Expedientes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(reloj12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblHeader)
                                .addGap(381, 381, 381))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblNss, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(16, 16, 16)
                                                .addComponent(lblEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEmergencia))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblCronica))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(16, 16, 16)
                                        .addComponent(lblFamilia))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(66, 66, 66)
                                        .addComponent(lblTabaco))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(66, 66, 66)
                                        .addComponent(lblAlcohol))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 28, Short.MAX_VALUE)))))
                .addGap(74, 74, 74))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(reloj12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lblHeader)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNombre)
                        .addComponent(jLabel2)
                        .addComponent(lblApellidos))
                    .addComponent(jLabel1))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNss)
                    .addComponent(jLabel4)
                    .addComponent(lblEdad))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblEmergencia))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblCronica))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblFamilia))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblTabaco))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblAlcohol))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        loginHospitalNuevo hl = new loginHospitalNuevo();
        hl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblAtrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAtrasMouseClicked
        this.dispose();
        expMed em = new expMed(ventanaCitasMed.getPaciente(), ventanaMedico.getMedico());
        em.setVisible(true);
    }//GEN-LAST:event_lblAtrasMouseClicked

    private void tblExpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblExpMouseClicked

    }//GEN-LAST:event_tblExpMouseClicked

    
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
    
    
    public static ObjetoExpediente getOe() {
        return oe;
    }

    public void llenarTabla() {
        con = ConexionSQL.ConexionSQLServer();
        m.setRowCount(0);
        Statement stmt;
        
        try {

            stmt = con.createStatement();
            String query="select ec.idExp, ec.fecha_atencion, ec.motivo_atencion\n" +
            "from PACIENTE pac\n" +
            "inner join expediente_clinico ec on (ec.nss = pac.numeroSeguro)\n" +
            "where ec.nss = '"+this.paciente.getNss()+"'";
    
            ResultSet result = stmt.executeQuery(query);
            
            Object R[] = new Object[3];
            while (result.next()) {
                R[0] = result.getObject("idExp");
                R[1] = result.getObject("fecha_atencion");
                R[2] = result.getObject("motivo_atencion");
                m.addRow(R);    
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(act_eli_Expediente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    private void rellenarEtiquetas(String nss){
        ResultSet rs;
        Statement st;
    
        String q = "select pac.nombrePac, pac.apellido1, pac.apellido2,  pac.fechaNac,pac.numeroSeguro, \n" +
                    "pp.Contacto_Emergencia, pp.enfermedad_cronica, pp.tabaco, pp.alcohol, pp.Enfermedad_familiar\n" +
                    "from paciente pac\n" +
                    "inner join preguntas_preliminares pp on (pac.numeroSeguro = pp.nss)\n"+
                    "WHERE pac.numeroSeguro = '"+nss+"'";
        
        String nombre="", apellido1="", apellido2="", nss2="", edad="", emergencia="", cronica="", familia="", tabaco="", alcohol="";
        int ed=0,aa=0;
        
        LocalDateTime hoy = LocalDateTime.now();
        aa = hoy.getYear();
        
        try{
            st = con.createStatement();
            rs= st.executeQuery(q);
            while(rs.next()){
                nombre=rs.getString("nombrePac");
                apellido1=rs.getString("apellido1");
                apellido2=rs.getString("apellido2");
                edad=rs.getString("fechaNac");
                nss2=rs.getString("numeroSeguro");
                emergencia=rs.getString("contacto_Emergencia");
                cronica=rs.getString("enfermedad_Cronica");
                tabaco=rs.getString("tabaco");
                alcohol=rs.getString("alcohol");
                familia=rs.getString("enfermedad_Familiar");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }       
                ed = aa - (Integer.parseInt(edad.substring(0,4)));
                
                lblNombre.setText(nombre);
                lblNss.setText(nss2);
                lblApellidos.setText(apellido1 + " "+apellido2);
                lblEdad.setText(expMed.obtenerEdadReal(edad)+"");
                lblNss.setText(nss2);
                lblEmergencia.setText(emergencia);
                lblCronica.setText(cronica);
                lblFamilia.setText(familia);
                lblTabaco.setText(tabaco);
                lblAlcohol.setText(alcohol);
    }
    private void tblExp1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblExp1MouseClicked
        con = ConexionSQL.ConexionSQLServer();
        
        int confirmacion=0;
    
        JOptionPane.showConfirmDialog(this,"¿Estás seguro de querer consultar este expediente?");
            switch(confirmacion){
                    
                case 0:
                    Object arreglo[] = new Object[3];
                    int renglon = tblExp1.getSelectedRow();
                    String  motivo= "", fecha = "";
                    int identificador=0;
                    if (renglon == -1) {
                        JOptionPane.showMessageDialog(this, "Seleccione un registro primero.");
                    return; // Detiene la ejecución del case
}
                    /*for (int i = 0; i < arreglo.length; i++) {
                        arreglo[i] = tblExp1.getValueAt(renglon, i);
                        
                    }

                    identificador = Integer.parseInt(arreglo[0].toString());
                    fecha = arreglo[1].toString();
                    motivo = arreglo[2].toString();
            
                    String query="delete from expediente_clinico\n" +
                    "where idExp ="+identificador;
                    
                    Statement stmt;
                try {
                    stmt = con.createStatement();
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
        
        
    System.out.println("Respuesta: " + respuesta);
    if (respuesta == 1) {//actualiza
           Object arreglo[] = new Object[3];
           int renglon = tblExp1.getSelectedRow();
           String fecha="";
           
           int identificador=0;
           Statement stmt;
           
            for (int i = 0; i < arreglo.length; i++) {
                arreglo[i] = tblExp1.getValueAt(renglon, i);
            }
            
            identificador = Integer.parseInt(arreglo[0].toString());
            fecha = arreglo[1].toString();
            
            ObjetoExpediente o = new ObjetoExpediente();
            o = crearExp(fecha, identificador);
            modificarExpediente me = new modificarExpediente(o);
            me.setVisible(true);
            this.dispose();

        }
        if (respuesta == 2) {//elimina
            int confirmacion=0;
            
            JOptionPane.showConfirmDialog(this,"¿Estás seguro de querer borrar este expediente?");
            switch(confirmacion){
                    
                case 0:
                    Object arreglo[] = new Object[3];
                    int renglon = tblExp1.getSelectedRow();
                    String  motivo= "", fecha = "";
                    int identificador=0;
                    if (renglon == -1) {
                        JOptionPane.showMessageDialog(this, "Seleccione un registro primero.");
                    return; // Detiene la ejecución del case
}
                    for (int i = 0; i < arreglo.length; i++) {
                        arreglo[i] = tblExp1.getValueAt(renglon, i);
                        
                    }

                    identificador = Integer.parseInt(arreglo[0].toString());
                    fecha = arreglo[1].toString();
                    motivo = arreglo[2].toString();
            
                    String query="delete from expediente_clinico\n" +
                    "where idExp ="+identificador;
                    
                    Statement stmt;
                try {
                    stmt = con.createStatement();
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
           
        if (respuesta == 3) {
        //no hace nada
        }
    }
                  */}
    }//GEN-LAST:event_tblExp1MouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Expediente_elegido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Expediente_elegido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Expediente_elegido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Expediente_elegido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Expediente_elegido ee = new Expediente_elegido(ventanaCitasMed.getPaciente(), ventanaMedico.getMedico());
            }
        });
    }
private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAlcohol;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblAtras;
    private javax.swing.JLabel lblCronica;
    private javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblEmergencia;
    private javax.swing.JLabel lblFamilia;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNss;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblTabaco;
    private javax.swing.JLabel lblUsuario;
    private Reloj1 reloj11;
    private Reloj1 reloj12;
    private javax.swing.JTable tblExp;
    private javax.swing.JTable tblExp1;
    // End of variables declaration//GEN-END:variables
}
