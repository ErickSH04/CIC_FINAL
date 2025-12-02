
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ventanaCitasPacRecepcionista extends javax.swing.JFrame {

    private static String usuarioId;
    private boolean mensajeModificacionMostrado = false;

    //private static Statement stmt;
    //public static Connection con;

    public ventanaCitasPacRecepcionista(String usuarioId) throws ClassNotFoundException {//String usuarioId
        this.setTitle("Citas Paciente");
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        //con = ConexionSQL.ConexionSQLServer();
        m = (DefaultTableModel) tblCita.getModel();
        lblFecha.setText(obtenerFecha());
        
        llenarCitas();
        configurarColoresTabla();

    }
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtIdCita = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        lblAgregarCita = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCita = new javax.swing.JTable();
        cmbMes = new javax.swing.JComboBox<>();
        cmbDia = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        reloj11 = new Reloj1();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextBuscarPac = new javax.swing.JTextArea();

        txtIdCita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

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

        lblAgregarCita.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblAgregarCita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar-usuario.png"))); // NOI18N
        lblAgregarCita.setText("Agregar Cita");
        lblAgregarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAgregarCitaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAgregarCita)
                            .addComponent(lblInicio)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(lblSalir))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(lblUsuario)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblUsuario)
                .addGap(110, 110, 110)
                .addComponent(lblInicio)
                .addGap(59, 59, 59)
                .addComponent(lblAgregarCita)
                .addGap(63, 63, 63)
                .addComponent(lblSalir)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        tblCita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblCita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Hora", "Paciente", "Medico", "Especialidad", "numeroSeguro", "Estatus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, true, true
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
        jLabel2.setText("Citas programadas");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/citaM.png"))); // NOI18N
        jLabel3.setText("Citas");

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");

        lblBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar (1).png"))); // NOI18N
        lblBuscar.setText("Buscar cita");

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jTextBuscarPac.setColumns(20);
        jTextBuscarPac.setRows(5);
        jTextBuscarPac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextBuscarPacKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTextBuscarPac);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(365, 365, 365)
                .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblFecha)
                        .addGap(60, 60, 60))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(468, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(327, 327, 327))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 905, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(379, 379, 379)
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpiar)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblSalirMouseClicked

    private void cmbMesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMesItemStateChanged
        // Obtener los valores de los combos
        Integer mes = cmbMes.getSelectedIndex() > 0 ? cmbMes.getSelectedIndex() : null;
        Integer dia = cmbDia.getSelectedIndex() > 0 ? cmbDia.getSelectedIndex() : null;
        String nombre = jTextBuscarPac.getText().trim();
        
        buscarPaciente(mes, dia, nombre.isEmpty() ? null : nombre);
    }//GEN-LAST:event_cmbMesItemStateChanged

    public String obtenerNSS(String correo) throws ClassNotFoundException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String identificadorPac = "";

    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            System.err.println("Error: No se pudo conectar a la BD");
            return "";
        }
        
        String query = "SELECT numeroSeguro FROM PACIENTE WHERE Correo = ?";
        
        stmt = conn.prepareStatement(query);
        stmt.setString(1, correo);
        
        System.out.println("Consulta NSS: " + query + " - Correo: " + correo);
        
        rs = stmt.executeQuery();

        if (rs.next()) {
            identificadorPac = rs.getString("numeroSeguro");
        }

    } catch (SQLException ex) {
        System.err.println("Error obteniendo NSS: " + ex.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("Error cerrando ResultSet: " + e.getMessage());
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error cerrando PreparedStatement: " + e.getMessage());
        }
        try {
            if (conn != null) {
                //ConexionSQL.cerrarConexion(conn);
                System.out.println("Conexión cerrada en obtenerNSS");
            }
        } catch (Exception e) {
            System.err.println("Error cerrando Connection: " + e.getMessage());
        }
    }
    return identificadorPac;
}
    
  

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
         VentanaRecepcion vp = null;
        try {
            vp = new VentanaRecepcion(usuarioId);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ventanaCitasPacRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ventanaCitasPacRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
        }
        vp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

    private void lblAgregarCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAgregarCitaMouseClicked
        registroCitaPac rc = null;
        try {
            rc = new registroCitaPac(this.usuarioId);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ventanaCitasPacRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
        }
        rc.setVisible(true);
    }//GEN-LAST:event_lblAgregarCitaMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        m.setRowCount(0);
        cmbMes.setSelectedIndex(0);
        cmbDia.setSelectedIndex(0);
        try {
            llenarCitas();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ventanaCitasPacRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void cmbDiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDiaItemStateChanged
        Integer mes = cmbMes.getSelectedIndex() > 0 ? cmbMes.getSelectedIndex() : null;
        Integer dia = cmbDia.getSelectedIndex() > 0 ? cmbDia.getSelectedIndex() : null;
        String nombre = jTextBuscarPac.getText().trim();

        buscarPaciente(mes, dia, nombre.isEmpty() ? null : nombre);
    }//GEN-LAST:event_cmbDiaItemStateChanged

    
    private void tblCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitaMouseClicked
            Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
        int respuesta = 0;
        respuesta = Integer.parseInt(JOptionPane.showInputDialog("¿Qué operación desea realizar?\n"
                + "1.- Modificar cita\n"
                + "2.- Reprogramar cita\n"
                + "3.- Cancelar cita\n"
                + "4.- Marcar como COMPLETADA\n"
                + "5.- Pagar cita\n"
                + "6.- Cancelar operación"));

        System.out.println("Respuesta: " + respuesta);
        
        if (respuesta == 1 || respuesta == 2 || respuesta == 3 || respuesta == 4 || respuesta == 5) {
            Object arreglo[] = new Object[7];
            int renglon = tblCita.getSelectedRow();
            
            if (renglon == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una cita primero.");
                return;
            }
            
            String nombrePaciente = "", fecha = "", hora = "", medico = "", especialidad = "", nss = "", estatusActual = "";

            // Obtener los 7 valores de la tabla
            for (int i = 0; i < 7; i++) {
                arreglo[i] = tblCita.getValueAt(renglon, i);
                System.out.println("Columna " + i + ": " + arreglo[i]);
            }

            fecha = arreglo[0] != null ? arreglo[0].toString() : "";
            hora = arreglo[1] != null ? arreglo[1].toString() : "";
            nombrePaciente = arreglo[2] != null ? arreglo[2].toString() : "";
            medico = arreglo[3] != null ? arreglo[3].toString() : "";
            especialidad = arreglo[4] != null ? arreglo[4].toString() : "";
            nss = arreglo[5] != null ? arreglo[5].toString() : "";
            estatusActual = arreglo[6] != null ? arreglo[6].toString() : "";
            
            System.out.println("Datos obtenidos - Paciente: " + nombrePaciente + ", Médico: " + medico + ", Especialidad: " + especialidad + ", Estatus: " + estatusActual);
            
            conn = ConexionSQL.ConexionSQLServer();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Error de conexión a la BD");
                return;
            }
            
            String buscarIdCita = "SELECT cit.idCita, cit.idMedico " +
                                 "FROM CITA cit " +  
                                 "WHERE cit.hora = '" + hora + "' AND cit.fecha = '" + fecha + "' AND cit.numeroSeguro = '" + nss + "'";
            
            System.out.println("Consulta SQL: " + buscarIdCita);
            String idC = "";
            String idMedico = "";
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(buscarIdCita);
            
            boolean citaEncontrada = false;
            while (rs.next()) {
                idC = rs.getString("idCita");
                idMedico = rs.getString("idMedico");
                citaEncontrada = true;
                System.out.println("Cita encontrada - ID: " + idC);
            }
            
            if (!citaEncontrada) {
                JOptionPane.showMessageDialog(this, "No se encontró la cita en la base de datos.");
                return;
            }
            
            // OPERACIONES
            if (respuesta == 1) {
                // Modificar cita
                modificarFecha mf = null;
                mf = new modificarFecha(idC);
                mf.setVisible(true);
                
            } else if (respuesta == 2) {
                // Reprogramar cita
                int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea reprogramar esta cita?\n\n" +
                    "Paciente: " + nombrePaciente + "\n" +  
                    "NSS: " + nss + "\n" +
                    "Médico: " + medico + " - " + especialidad + "\n" +  
                    "Fecha actual: " + fecha + "\n" +
                    "Hora actual: " + hora + "\n\n" +
                    "Se eliminará la cita actual y podrá crear una nueva.",
                    "Confirmar Reprogramación",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    String eliminacion = "DELETE FROM CITA WHERE idCita = '" + idC + "'";
                    int filasEliminadas = stmt.executeUpdate(eliminacion);
                    if (filasEliminadas > 0) {
                        JOptionPane.showMessageDialog(this, 
                                "Cita eliminada. Ahora puede crear una nueva cita.");
                        registroCitaPac rc = new registroCitaPac(this.usuarioId);
                        rc.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar la cita.");
                    }
                }
                
            } else if (respuesta == 3) {
                // Cancelar cita
                int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea cancelar esta cita?\n\n" +
                    "Paciente: " + nombrePaciente + "\n" +  
                    "NSS: " + nss + "\n" +
                    "Médico: " + medico + " - " + especialidad + "\n" +  
                    "Fecha: " + fecha + " " + hora,
                    "Confirmar Cancelación",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    String actualizacion = "UPDATE CITA SET estatus = 'CANCELADA' WHERE idCita = '" + idC + "'";
                    int filasAfectadas = stmt.executeUpdate(actualizacion);
                    
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Cita cancelada con éxito.");
                        try {
                            llenarCitas();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ventanaCitasPacRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al cancelar la cita.");
                    }
                }
                
            } else if (respuesta == 4) {
                // Marcar como COMPLETADA
                if ("COMPLETADA".equalsIgnoreCase(estatusActual)) {
                    JOptionPane.showMessageDialog(this, "Esta cita ya está marcada como COMPLETADA.");
                    return;
                }
                
                int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea marcar esta cita como COMPLETADA?\n\n" +
                    "Paciente: " + nombrePaciente + "\n" +  
                    "NSS: " + nss + "\n" +
                    "Médico: " + medico + " - " + especialidad + "\n" +  
                    "Fecha: " + fecha + " " + hora,
                    "Confirmar Cita Completada",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    String actualizacion = "UPDATE CITA SET estatus = 'COMPLETADA' WHERE idCita = '" + idC + "'";
                    int filasAfectadas = stmt.executeUpdate(actualizacion);
                    
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Cita marcada como COMPLETADA con éxito.");
                        try {
                            llenarCitas();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ventanaCitasPacRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al marcar la cita como COMPLETADA.");
                    }
                }

            } else if (respuesta == 5) {
                // Pagar cita
                VentanaPagosIn vp = new VentanaPagosIn(idC);
                vp.setVisible(true);
                this.dispose();
            }
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(ventanaCitasPacRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    } catch (NumberFormatException ex) {
        // Usuario canceló la operación
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(ventanaCitasPacRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException e) { }
        try { if (stmt != null) stmt.close(); } catch (SQLException e) { }
        try { if (conn != null) conn.close(); } catch (SQLException e) { }
     }
    }//GEN-LAST:event_tblCitaMouseClicked

    private void jTextBuscarPacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextBuscarPacKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        Integer mes = cmbMes.getSelectedIndex() > 0 ? cmbMes.getSelectedIndex() : null;
        Integer dia = cmbDia.getSelectedIndex() > 0 ? cmbDia.getSelectedIndex() : null;
        String nombre = jTextBuscarPac.getText().trim();
        
        buscarPaciente(mes, dia, nombre.isEmpty() ? null : nombre);
      }
    }//GEN-LAST:event_jTextBuscarPacKeyPressed

    private String obtenerFecha() {
        String fecha = "";
        LocalDateTime hoy = LocalDateTime.now();
        fecha = hoy.getMonthValue() + "/" + hoy.getDayOfMonth() + "/" + hoy.getYear();

        return fecha;
    }
    
    
    private void buscarPaciente(Integer mes, Integer dia, String nombrePaciente) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            System.err.println("Error: No se pudo conectar a la BD");
            return;
        }
        
        m.setRowCount(0);
        
        StringBuilder query = new StringBuilder(
                 "SELECT cit.fecha, cit.hora, " +
    "CONCAT(pac.nombrePac, ' ', pac.apellido1) as Paciente, " +
    "CONCAT(med.nombreMed, ' ', med.apellido1) as Medico, " +
    "med.Especialidad, pac.numeroSeguro, " +
    "CASE WHEN cit.estatus IS NULL THEN 'Activa' ELSE cit.estatus END as estatus " +
    "FROM CITA cit " +  
    "INNER JOIN PACIENTE pac ON pac.numeroSeguro = cit.numeroSeguro " +  
    "INNER JOIN MEDICO med ON CAST(med.idMedico AS VARCHAR) = cit.idMedico " +
    "WHERE 1=1"
        );

        int paramIndex = 1;
        
        if (mes != null) {
            query.append(" AND MONTH(cit.fecha) = ?");
        }
        
        if (dia != null) {
            query.append(" AND DAY(cit.fecha) = ?");
        }
        
        if (nombrePaciente != null && !nombrePaciente.trim().isEmpty()) {
            query.append(" AND (pac.nombrePac LIKE ? OR pac.apellido1 LIKE ?)");
        }
        
        query.append(" ORDER BY cit.fecha, cit.hora");

        stmt = conn.prepareStatement(query.toString());
        
        if (mes != null) {
            stmt.setInt(paramIndex++, mes);
        }
        
        if (dia != null) {
            stmt.setInt(paramIndex++, dia);
        }
        
        if (nombrePaciente != null && !nombrePaciente.trim().isEmpty()) {
            stmt.setString(paramIndex++, "%" + nombrePaciente + "%");
            stmt.setString(paramIndex++, "%" + nombrePaciente + "%");
        }

        rs = stmt.executeQuery();
        
        Object R[] = new Object[7];
        while (rs.next()) {
            R[0] = rs.getObject("fecha");
            R[1] = rs.getObject("hora");
            R[2] = rs.getObject("Paciente");
            R[3] = rs.getObject("Medico");
            R[4] = rs.getObject("Especialidad");
            R[5] = rs.getObject("numeroSeguro");
            R[6] = rs.getObject("estatus");
            m.addRow(R);
        }
        
    } catch (SQLException ex) {
        System.err.println("Error buscando paciente: " + ex.getMessage());
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al buscar citas: " + ex.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}  
    
   
    public void llenarCitas() throws ClassNotFoundException {
    
    Connection conn = null;
    Statement stmt = null;
    ResultSet result = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            System.err.println("Error: No se pudo conectar a la BD");
            return;
        }
        
        m.setRowCount(0);
        
        String query =  "SELECT cit.fecha, cit.hora, " +
      "CONCAT(pac.nombrePac, ' ', pac.apellido1) as Paciente, " +
      "CONCAT(med.nombreMed, ' ', med.apellido1) as Medico, " +
      "med.Especialidad, pac.numeroSeguro, " +
      "CASE WHEN cit.estatus IS NULL THEN 'Activa' ELSE cit.estatus END as estatus " + 
      "FROM CITA cit " +
      "INNER JOIN PACIENTE pac ON pac.numeroSeguro = cit.numeroSeguro " +
      "INNER JOIN MEDICO med ON CAST(med.idMedico AS VARCHAR) = cit.idMedico " +  
      "ORDER BY cit.fecha, cit.hora";

        System.out.println("Consulta recepcionista: " + query);
        
        stmt = conn.createStatement();
        result = stmt.executeQuery(query);

        Object R[] = new Object[7];
        while (result.next()) {
            R[0] = result.getObject("fecha");
            R[1] = result.getObject("hora");
            R[2] = result.getObject("Paciente");
            R[3] = result.getObject("Medico");
            R[4] = result.getObject("Especialidad");
            R[5] = result.getObject("numeroSeguro");
            R[6] = result.getObject("estatus");
            m.addRow(R);
        }
        
        verificarCitasModificadas();
    } catch (SQLException ex) {
        System.err.println("Error llenando citas recepcionista: " + ex.getMessage());
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar citas: " + ex.getMessage());
    } finally {
        try {
            if (result != null) result.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
    
     private void configurarColoresTabla() {
    // Renderer para TODA la tabla, usando el estatus de la columna 6
    tblCita.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            java.awt.Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            Object estatusObj = table.getValueAt(row, 6);
            String estatus = estatusObj != null ? estatusObj.toString().trim().toLowerCase() : "";

            switch (estatus) {
                case "activa":
                    cell.setBackground(new java.awt.Color(144, 238, 144)); // verde claro
                    cell.setForeground(java.awt.Color.BLACK);
                    break;

                case "cancelada":
                case "cancelado":
                    cell.setBackground(new java.awt.Color(255, 182, 193)); // rosa/rojo claro
                    cell.setForeground(java.awt.Color.BLACK);
                    break;

                case "modificado":
                case "modificada":
                    cell.setBackground(new java.awt.Color(173, 216, 230)); // azul claro
                    cell.setForeground(java.awt.Color.BLACK);
                    if (column == 6) {
                        setText("Activa (Modificada)");
                    }
                    break;

                case "completada":
                case "completado":
                    cell.setBackground(new java.awt.Color(224, 224, 224)); // gris claro
                    cell.setForeground(java.awt.Color.BLACK);
                    break;

                default:
                    cell.setBackground(java.awt.Color.WHITE);
                    cell.setForeground(java.awt.Color.BLACK);
                    break;
            }

            if (isSelected) {
                cell.setBackground(cell.getBackground().darker());
            }

            return cell;
        }
    });

    tblCita.setDefaultEditor(Object.class, null);

    javax.swing.table.JTableHeader header = tblCita.getTableHeader();
    header.setBackground(new java.awt.Color(224, 224, 224));
    header.setForeground(java.awt.Color.BLACK);
    header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
    header.setOpaque(false);

    tblCita.repaint();
}
     
    private void verificarCitasModificadas() {
    if (mensajeModificacionMostrado) return;

    int filas = tblCita.getRowCount();
    for (int i = 0; i < filas; i++) {
        Object v = tblCita.getValueAt(i, 6); // columna estatus
        if (v != null && "modificado".equalsIgnoreCase(v.toString().trim())) {
            mensajeModificacionMostrado = true;

            JOptionPane.showMessageDialog(
                    this,
                    "Una o más citas fueron modificadas.\n" +
                    "Revisa fecha, hora y médico asignado.",
                    "Citas modificadas",
                    JOptionPane.INFORMATION_MESSAGE
            );
            break;
        }
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
                try {
                    ventanaCitasPacRecepcionista vcm = null;
                    vcm = new ventanaCitasPacRecepcionista(usuarioId);
                    vcm.setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ventanaCitasPacRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextBuscarPac;
    private javax.swing.JLabel lblAgregarCita;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private Reloj1 reloj11;
    private javax.swing.JTable tblCita;
    private javax.swing.JTextField txtIdCita;
    // End of variables declaration//GEN-END:variables
}
