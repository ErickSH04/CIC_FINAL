
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.SwingUtilities;

public class registroCitaPac extends javax.swing.JFrame {
    
    //HolaMundo123

    //private static Statement stmt;
    //public static Connection con;
    private String usuarioId;
    public static String medicoId;
    private static final int DURACION_CITA_MINUTOS = 60; // 1 hora de duración
    private String nssPacienteValidado; // error
    
    public registroCitaPac(String correo) throws ClassNotFoundException {
    initComponents();
    this.setLocationRelativeTo(this);
    this.usuarioId = usuarioId;
    txtIdCita.setEditable(false);
    String idCita = obtenerCita();
    txtIdCita.setText(idCita);
    llenarDoctor();
    fechaCita.setMinSelectableDate(new Date());
    this.nssPacienteValidado = null; //error
    
    configurarValidacionPaciente();
    
    //registrarCita(registroCitaPac.this.usuarioId);

}

private void configurarValidacionPaciente() {
    txtNombrePaciente.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyPressed(java.awt.event.KeyEvent evt) {
            if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                validarPaciente();
            }
        }
    });
}
    public String obtenerCita() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String nuevoId = "C1"; // valor por defecto
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn != null) {
            stmt = conn.createStatement();
            
            String query = "SELECT MAX(CAST(SUBSTRING(idCita, 2, LEN(idCita)-1) AS INT)) as max_num FROM CITA WHERE idCita LIKE 'C%'";
            
            System.out.println("Ejecutando: " + query);
            rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                int maxNum = rs.getInt("max_num");
                if(!rs.wasNull()){
                    nuevoId = "C" + (maxNum+1);
                }else{
                    System.out.println("No hay citas existentes, usando C1");
                }
            } 
        }
    } catch (SQLException ex) {
        System.err.println("Error SQL: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        //CERRAR TODOS LOS RECURSOS
        try {
            if (rs != null) rs.close();
        } catch (SQLException ex) { 
            System.err.println("Error cerrando ResultSet: " + ex.getMessage()); 
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException ex) { 
            System.err.println("Error cerrando Statement: " + ex.getMessage()); 
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) { 
            System.err.println("Error cerrando Connection: " + ex.getMessage()); 
        }
    }
    return nuevoId;
  }
    
    
  
    
    
    //error
    private void validarPaciente() {
    String nombrePaciente = txtNombrePaciente.getText().trim();
    
    if (nombrePaciente.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre del paciente");
        txtNombrePaciente.requestFocus();
        return;
    }
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn != null) {
            
            String query = "SELECT numeroSeguro, nombrePac, apellido1, apellido2 FROM PACIENTE " +
                          "WHERE CONCAT(nombrePac, ' ', apellido1, ' ', apellido2) LIKE ? " +  
                          "OR nombrePac LIKE ? " +  
                          "OR apellido1 LIKE ?";
            
            ps = conn.prepareStatement(query);
            String busqueda = "%" + nombrePaciente + "%";
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                // PACIENTE ENCONTRADO
                String nss = rs.getString("numeroSeguro");
                String nombreCompleto = rs.getString("nombrePac") + " " + 
                                      rs.getString("apellido1") + " " + 
                                      rs.getString("apellido2");
                
                // Puedes usar una variable de instancia para almacenar el NSS
                this.nssPacienteValidado = nss; // Agrega esta variable a tu clase
                
                // Mostrar confirmación
                int opcion = JOptionPane.showConfirmDialog(this,
                    "Paciente encontrado:\n" +
                    "Nombre: " + nombreCompleto + "\n" +
                    "NSS: " + nss + "\n\n" +
                    "¿Es este el paciente correcto?",
                    "Confirmar Paciente",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    txtNombrePaciente.setText(nombreCompleto);
                    txtNombrePaciente.setEditable(false); 
                    
                    // Guardar el NSS en una variable de instancia
                    this.nssPacienteValidado = nss;
                    
                    JOptionPane.showMessageDialog(this, 
                        "Paciente confirmado: " + nombreCompleto + "\n" +
                        "NSS: " + nss);
                } else {
                    this.nssPacienteValidado = null;
                }
            } else {
                // PACIENTE NO ENCONTRADO
                int opcion = JOptionPane.showConfirmDialog(this,
                    "No se encontró el paciente: " + nombrePaciente + "\n\n" +
                    "¿Desea registrar un nuevo paciente?",
                    "Paciente No Encontrado",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, 
                        "Por favor registre al paciente primero\n" +
                        "y luego vuelva a agendar la cita.");
                } else {
                    txtNombrePaciente.setText("");
                    txtNombrePaciente.requestFocus();
                }
                this.nssPacienteValidado = null;
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, 
            "Error al buscar paciente: " + ex.getMessage());
        this.nssPacienteValidado = null;
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
    
 
  
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        txtIdCita = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        btnAtras = new javax.swing.JButton();
        cmbMedico = new javax.swing.JComboBox<>();
        fechaCita = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        txtNombrePaciente = new javax.swing.JTextField();
        escogerHora = new com.raven.swing.TimePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(136, 212, 234));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Roboto", 2, 18)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favicon.png"))); // NOI18N
        jLabel6.setText("Registra Cita");

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel5.setText("Paciente:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel7.setText("Numero Cita:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel8.setText("Fecha:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel9.setText("Hora:");

        btnConfirmar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        txtIdCita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtHora.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N

        btnAtras.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnAtras.setText("Atrás");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        cmbMedico.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMedicoItemStateChanged(evt);
            }
        });
        cmbMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMedicoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel10.setText("Medico:");

        txtNombrePaciente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIdCita, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                    .addComponent(txtHora)
                                    .addComponent(cmbMedico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fechaCita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNombrePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel6)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel6)
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNombrePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(fechaCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConfirmar)
                            .addComponent(btnAtras))
                        .addGap(46, 46, 46))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 380, 400));

        escogerHora.set24hourMode(true);
        escogerHora.setDisplayText(txtHora);
        jPanel1.add(escogerHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
                  
    //Verificar que se haya validado un paciente
    if (txtNombrePaciente.getText().trim().isEmpty() || txtNombrePaciente.isEditable()) {
        showMessageDialog(null, "Error: Debe ingresar y validar un paciente primero");
        txtNombrePaciente.requestFocus();
        return;
    }
    
    //Verificar fecha
    if (fechaCita.getDate() == null) {
        showMessageDialog(null, "Error: Debe seleccionar una fecha");
        return;
    }
    
    if (medicoId == null || medicoId.equals("null") || medicoId.isEmpty() || 
        cmbMedico.getSelectedItem().equals("Selecciona")) {
        showMessageDialog(null, "Error: Debe seleccionar un médico válido");
        return;
    }
    
    if (txtHora.getText().trim().isEmpty()) {
        showMessageDialog(null, "Error: Debe seleccionar una hora");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String fechaFormato = sdf.format(fechaCita.getDate());
    String hora = txtHora.getText();
    
    if (!verificarDisponibilidad(medicoId, fechaFormato, hora)) {
        return;
    }
    
    if (!verificarDisponibilidadPaciente(fechaFormato, hora)) {
        return;
    }
    
    // VENTANA DE ESPERA CON JOptionPane
    JOptionPane optionPane = new JOptionPane(
        "Registrando cita, por favor espere...", 
        JOptionPane.INFORMATION_MESSAGE
    );
    
    JDialog dialog = optionPane.createDialog(this, "Procesando");
    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    
    new Thread(() -> {
        try {
            // Simular un pequeño delay para que se vea la ventana de espera
            Thread.sleep(500);
            
            // Registrar cita
            registrarCita(registroCitaPac.this.usuarioId);
            
        } catch (Exception ex) {
            // Mostrar error en el hilo de EDT
            SwingUtilities.invokeLater(() -> {
                showMessageDialog(null, "Error al registrar la cita: " + ex.getMessage());
            });
        } finally {
            // Cerrar el diálogo de espera en el hilo de EDT
            SwingUtilities.invokeLater(() -> {
                dialog.dispose();
            });
        }
    }).start();
    
    // Mostrar el diálogo de espera
    dialog.setVisible(true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void cmbMedicoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMedicoItemStateChanged
        Connection conn = null;
        Statement stmt = null; 
        ResultSet result = null; 
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn != null) {
            stmt = conn.createStatement();
            String query = "SELECT idMedico FROM MEDICO WHERE nombreMed = '" + cmbMedico.getSelectedItem() + "'";
            result = stmt.executeQuery(query);

            while (result.next()) {
                String doctor = result.getString("idMedico");
                medicoId = doctor;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        //Cerrar todos los recursos
        try {
            if (result != null) result.close();
        } catch (SQLException ex) { ex.printStackTrace(); }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException ex) { ex.printStackTrace(); }
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) { ex.printStackTrace(); }
      }
    }//GEN-LAST:event_cmbMedicoItemStateChanged

    private void cmbMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMedicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMedicoActionPerformed

    
    public void llenarDoctor() {
          Connection conn = null;
          Statement stmt = null; 
          ResultSet result = null; 
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn != null) {
            cmbMedico.removeAllItems();
            cmbMedico.addItem("Selecciona");

            stmt = conn.createStatement();
            String query = "SELECT nombreMed, idMedico FROM MEDICO";
            result = stmt.executeQuery(query);

            while (result.next()) {
                String doctor = result.getString("nombreMed");
                cmbMedico.addItem(doctor);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        //Cerrar todos los recursos
        try {
            if (result != null) result.close();
        } catch (SQLException ex) { ex.printStackTrace(); }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException ex) { ex.printStackTrace(); }
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
  }
    
    
    private boolean verificarDisponibilidad(String idMedico, String fecha, String hora) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            System.err.println("Error: No se pudo conectar a la BD");
            return false;
        }
        
        String query = "SELECT COUNT(*) as citas_existentes, " +
                      "(SELECT nombreMed FROM MEDICO WHERE idMedico = ?) as nombre_medico, " +
                      "(SELECT TOP 1 CONCAT(c.hora, ' - ', p.nombrePac, ' ', p.apellido1) " +
                      "FROM CITA c INNER JOIN PACIENTE p ON c.numeroSeguro = p.numeroSeguro " +
                      "WHERE c.idMedico = ? AND c.fecha = ? " +
                      "AND (c.estatus IS NULL OR c.estatus != 'CANCELADA') " +
                      "AND ( " +
                      "    (? BETWEEN c.hora AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", c.hora) " +
                      "    OR DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", ?) BETWEEN c.hora AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", c.hora) " +
                      "    OR c.hora BETWEEN ? AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", ?) " +
                      ")) " +
                      ") as cita_conflicto " +
                      "FROM CITA " +
                      "WHERE idMedico = ? " +
                      "AND fecha = ? " +
                      "AND (estatus IS NULL OR estatus != 'CANCELADA') " +
                      "AND ( " +
                      "    (? BETWEEN hora AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", hora) " +
                      "    OR DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", ?) BETWEEN hora AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", hora) " +
                      "    OR hora BETWEEN ? AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", ?) " +
                      "))";

        pstmt = conn.prepareStatement(query);
        
        int paramIndex = 1;
        
        // Parámetros para el médico (posición 1)
        pstmt.setString(paramIndex++, idMedico);
        
        // Parámetros para la subconsulta (posiciones 2-7)
        pstmt.setString(paramIndex++, idMedico);
        pstmt.setString(paramIndex++, fecha);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        
        // Parámetros para la consulta principal (posiciones 8-11)
        pstmt.setString(paramIndex++, idMedico);
        pstmt.setString(paramIndex++, fecha);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        
        System.out.println("Ejecutando verificación de disponibilidad...");
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            int citasExistentes = rs.getInt("citas_existentes");
            String nombreMedico = rs.getString("nombre_medico");
            String citaConflicto = rs.getString("cita_conflicto");
            
            System.out.println("Citas existentes en rango para " + nombreMedico + ": " + citasExistentes);
            
            if (citasExistentes > 0) {
                mostrarInformacionCitaExistenteMejorada(conn, idMedico, fecha, hora, nombreMedico, citaConflicto);
                return false;
            }
        }
        return true;
        
    } catch (SQLException ex) {
        System.err.println("Error verificando disponibilidad: " + ex.getMessage());
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Error al verificar disponibilidad: " + ex.getMessage(), 
            "Error de Base de Datos", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    } finally {
        try {
            if (rs != null) rs.close();
        } catch (SQLException ex) {
            System.err.println("Error cerrando ResultSet: " + ex.getMessage());
        }
        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException ex) {
            System.err.println("Error cerrando PreparedStatement: " + ex.getMessage());
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.err.println("Error cerrando Connection: " + ex.getMessage());
        }
    }
}
    
    
    
    private void mostrarInformacionCitaExistenteMejorada(Connection conn, String idMedico, String fecha, String hora, String nombreMedico, String citaConflicto) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        String query = "SELECT c.idCita, c.hora, " +
                      "CONCAT(p.nombrePac, ' ', p.apellido1) as paciente, " +
                      "c.estatus " +
                      "FROM CITA c " +
                      "INNER JOIN PACIENTE p ON c.numeroSeguro = p.numeroSeguro " +
                      "WHERE c.idMedico = ? " +
                      "AND c.fecha = ? " +
                      "AND (c.estatus IS NULL OR c.estatus != 'CANCELADA') " +
                      "AND ( " +
                      "    (? BETWEEN c.hora AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", c.hora) " +
                      "    OR DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", ?) BETWEEN c.hora AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", c.hora) " +
                      "    OR c.hora BETWEEN ? AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", ?) " +
                      ")) " +
                      "ORDER BY c.hora";
        
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, idMedico);
        pstmt.setString(2, fecha);
        pstmt.setString(3, hora);
        pstmt.setString(4, hora);
        pstmt.setString(5, hora);
        pstmt.setString(6, hora);
        
        rs = pstmt.executeQuery();
        
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("CONFLICTO DE HORARIO - DURACIÓN DE CITA\n\n");
        mensaje.append("El Dr. ").append(nombreMedico).append(" tiene citas que se solapan:\n\n");
        
        mensaje.append("Fecha: ").append(fecha).append("\n");
        mensaje.append("Hora solicitada: ").append(hora).append(" (Duración: ").append(DURACION_CITA_MINUTOS).append(" min)\n\n");
        mensaje.append("Citas existentes en conflicto:\n");
        
        boolean tieneConflictos = false;
        while (rs.next()) {
            tieneConflictos = true;
            String horaCita = rs.getString("hora");
            String paciente = rs.getString("paciente");
            String idCita = rs.getString("idCita");
            String estatus = rs.getString("estatus");
            
            mensaje.append("• ").append(horaCita).append(" - ").append(paciente)
                   .append(" (Cita: ").append(idCita).append(")\n");
        }
        
        if (tieneConflictos) {
            mensaje.append("\n Las citas tienen una duración de ").append(DURACION_CITA_MINUTOS)
                   .append(" minutos. Por favor seleccione otro horario.");
            
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                    mensaje.toString(),
                    "Conflicto de Duración de Cita",
                    JOptionPane.WARNING_MESSAGE
                );
            });
        }
        
    } catch (SQLException ex) {
        System.err.println("Error obteniendo información de citas en conflicto: " + ex.getMessage());
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
    
    private boolean verificarDisponibilidadPaciente(String fecha, String hora) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        String nss = obtenerNSSDelPacienteValidado();
        if (nss == null || nss.trim().isEmpty()) {
            return true;
        }
        
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            return true;
        }
        
        String query = "SELECT COUNT(*) as citas_existentes, " +
                      "(SELECT TOP 1 CONCAT(m.nombreMed, ' ', m.apellido1, ' - ', c.hora) " +
                      "FROM MEDICO m INNER JOIN CITA c ON m.idMedico = c.idMedico " +
                      "WHERE c.numeroSeguro = ? AND c.fecha = ? " +
                      "AND (c.estatus IS NULL OR c.estatus != 'CANCELADA') " +
                      "AND ( " +
                      "    (? BETWEEN c.hora AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", c.hora) " +
                      "    OR DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", ?) BETWEEN c.hora AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", c.hora) " +
                      "    OR c.hora BETWEEN ? AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", ?) " +
                      ")) " +
                      ") as medico_hora " +
                      "FROM CITA " +
                      "WHERE numeroSeguro = ? " +
                      "AND fecha = ? " +
                      "AND (estatus IS NULL OR estatus != 'CANCELADA') " +
                      "AND ( " +
                      "    (? BETWEEN hora AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", hora) " +
                      "    OR DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", ?) BETWEEN hora AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", hora) " +
                      "    OR hora BETWEEN ? AND DATEADD(MINUTE, " + DURACION_CITA_MINUTOS + ", ?) " +
                      "))";

        pstmt = conn.prepareStatement(query);
        
        
        int paramIndex = 1;
        
        // Subconsulta (parámetros 1-6)
        pstmt.setString(paramIndex++, nss);
        pstmt.setString(paramIndex++, fecha);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        
        // Consulta principal (parámetros 7-12)
        pstmt.setString(paramIndex++, nss);
        pstmt.setString(paramIndex++, fecha);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        pstmt.setString(paramIndex++, hora);
        
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            int citasExistentes = rs.getInt("citas_existentes");
            String medicoHora = rs.getString("medico_hora");
            
            if (citasExistentes > 0) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                        "EL PACIENTE YA TIENE UNA CITA EN ESE HORARIO\n\n" +
                        "El paciente ya tiene una cita que se solapa:\n" +
                        "Fecha: " + fecha + "\n" +
                        "Hora solicitada: " + hora + " (Duración: " + DURACION_CITA_MINUTOS + " min)\n" +
                        "Conflicto con: " + (medicoHora != null ? medicoHora : "Otra cita") + "\n\n" +
                        "Un paciente no puede tener dos citas en horarios que se solapan.",
                        "Conflicto de Horario del Paciente",
                        JOptionPane.WARNING_MESSAGE
                    );
                });
                return false;
            }
        }
        return true;
        
    } catch (SQLException ex) {
        System.err.println("Error verificando disponibilidad del paciente: " + ex.getMessage());
        ex.printStackTrace();
        return true; // En caso de error, permitir continuar
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
    
    
  
    

    private boolean verificarPacienteExiste(Connection conn, String nss) throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        String query = "SELECT COUNT(*) as count FROM PACIENTE WHERE numeroSeguro = ?";
        ps = conn.prepareStatement(query);
        ps.setString(1, nss);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("count") > 0;
        }
        return false;
        
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
    
    private void registrarCita(String correo) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs = null;

    try {
        System.out.println("=== INICIANDO REGISTRO CITA ===");
        
        // 1. CONEXIÓN
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            throw new Exception("No se pudo conectar a la base de datos");
        }

        // 2. OBTENER NSS CON VALIDACIÓN
        String nss = obtenerNSSDelPacienteValidado(conn);
        System.out.println("NSS obtenido: " + nss);
        
        if (nss == null || nss.trim().isEmpty()) {
            throw new Exception("No se pudo obtener el NSS del paciente. Por favor valide el paciente nuevamente.");
        }

        // 3. VALIDAR TODOS LOS CAMPOS
        String idCita = txtIdCita.getText();
        if (idCita == null || idCita.trim().isEmpty()) {
            throw new Exception("ID de cita no válido");
        }
        idCita = idCita.trim();

        if (fechaCita.getDate() == null) {
            throw new Exception("Fecha no seleccionada");
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormato = sdf.format(fechaCita.getDate());

        String hora = txtHora.getText();
        if (hora == null || hora.trim().isEmpty()) {
            throw new Exception("Hora no seleccionada");
        }
        hora = hora.trim();

        if (medicoId == null || medicoId.trim().isEmpty() || "null".equals(medicoId)) {
            throw new Exception("Médico no seleccionado");
        }
        String idMedico = medicoId.trim();
        String estatus = "Activa";

        // 4. IMPRIMIR VALORES PARA DEBUG
        System.out.println("Valores a insertar:");
        System.out.println("idCita: " + idCita);
        System.out.println("fecha: " + fechaFormato);
        System.out.println("hora: " + hora);
        System.out.println("nss: " + nss);
        System.out.println("idMedico: " + idMedico);

        // 5. INSERTAR CITA CON PREPAREDSTATEMENT
        String query = "INSERT INTO CITA (idCita, fecha, hora, numeroSeguro, idMedico,estatus) VALUES (?, ?, ?, ?, ?,?)";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, idCita);
        pstmt.setString(2, fechaFormato);
        pstmt.setString(3, hora);
        pstmt.setString(4, nss);
        pstmt.setString(5, idMedico);
        pstmt.setString(6, estatus);

        int filasAfectadas = pstmt.executeUpdate();
        System.out.println("Filas afectadas: " + filasAfectadas);

        if (filasAfectadas > 0) {
            // 6. OBTENER CORREO MÉDICO Y ENVIAR NOTIFICACIONES
            String correoDoc = "";
            String queryD = "SELECT Correo FROM MEDICO WHERE idMedico = ?";
            pstmt2 = conn.prepareStatement(queryD);
            pstmt2.setString(1, idMedico);
            rs = pstmt2.executeQuery();
            
            if (rs.next()) {                
                correoDoc = rs.getString("correo");
                System.out.println("Correo médico: " + correoDoc);
            }

            // 7. ENVIAR NOTIFICACIONES (OPCIONAL)
            try {
                String mensaje = "Programaste una cita para el " + fechaFormato + " a las " + hora + ".\n";
                String mensajeDoc = "Tienes una cita para el " + fechaFormato + " a las " + hora + ".\n";

                NotificacionCorreo noti = new NotificacionCorreo();
                if (correo != null && !correo.trim().isEmpty()) {
                    noti.enviarCorreo(correo, "Recordatorio de cita médica", mensaje);
                }
                if (correoDoc != null && !correoDoc.trim().isEmpty()) {
                    noti.enviarCorreo(correoDoc, "Recordatorio de cita médica", mensajeDoc);
                }
            } catch (Exception e) {
                System.err.println("Error enviando correos: " + e.getMessage());
                // NO LANZAR EXCEPCIÓN, SOLO LOG
            }

            // 8. MOSTRAR ÉXITO
            SwingUtilities.invokeLater(() -> {
                showMessageDialog(null, "Cita agregada con éxito para: " + txtNombrePaciente.getText());
                dispose();
            });
        } else {
            throw new Exception("No se pudo insertar la cita en la base de datos");
        }

    } catch (Exception ex) {
        System.err.println("ERROR AL REGISTRAR CITA: " + ex.getMessage());
        ex.printStackTrace();
        
        SwingUtilities.invokeLater(() -> {
            showMessageDialog(null, "Error al registrar la cita: " + ex.getMessage());
        });
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (pstmt2 != null) pstmt2.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
 
    //error
    private String obtenerNSSDelPacienteValidado(Connection conn) throws SQLException {
    if (this.nssPacienteValidado != null && !this.nssPacienteValidado.trim().isEmpty()) {
        System.out.println("Usando NSS previamente validado: " + this.nssPacienteValidado);
        return this.nssPacienteValidado;
    }
    
    String nombrePaciente = txtNombrePaciente.getText().trim();
    if (nombrePaciente.isEmpty()) {
        System.out.println("Campo de nombre del paciente está vacío");
        return null;
    }
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        String query = "SELECT numeroSeguro FROM PACIENTE " +
                      "WHERE CONCAT(nombrePac, ' ', apellido1, ' ', apellido2) LIKE ? " +
                      "OR CONCAT(apellido1, ' ', apellido2, ' ', nombrePac) LIKE ? " +
                      "OR nombrePac LIKE ? " +
                      "OR apellido1 LIKE ?";
        
        ps = conn.prepareStatement(query);
        
        String busqueda = "%" + nombrePaciente + "%";
        ps.setString(1, busqueda);
        ps.setString(2, busqueda);
        ps.setString(3, busqueda);
        ps.setString(4, busqueda);
        
        rs = ps.executeQuery();
        
        if (rs.next()) {
            String nss = rs.getString("numeroSeguro");
            System.out.println("NSS encontrado en BD: " + nss + " para: " + nombrePaciente);
            
            this.nssPacienteValidado = nss;
            return nss;
        } else {
            System.out.println("No se encontró NSS en BD para: " + nombrePaciente);
            System.out.println("Búsqueda realizada con: " + busqueda);
            return null;
        }
        
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}

    private String obtenerNSSDelPacienteValidado() {
    if (this.nssPacienteValidado != null && !this.nssPacienteValidado.trim().isEmpty()) {
        System.out.println("Usando NSS previamente validado: " + this.nssPacienteValidado);
        return this.nssPacienteValidado;
    }
    
    String nombrePaciente = txtNombrePaciente.getText().trim();
    if (nombrePaciente.isEmpty()) {
        return null;
    }
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            System.err.println("Error: No se pudo conectar a la BD");
            return null;
        }
        
        String query = "SELECT numeroSeguro FROM PACIENTE " +
                      "WHERE CONCAT(nombrePac, ' ', apellido1, ' ', apellido2) LIKE ? " +
                      "OR CONCAT(apellido1, ' ', apellido2, ' ', nombrePac) LIKE ? " +
                      "OR nombrePac LIKE ? " +
                      "OR apellido1 LIKE ?";
        
        ps = conn.prepareStatement(query);
        
        String busqueda = "%" + nombrePaciente + "%";
        ps.setString(1, busqueda);
        ps.setString(2, busqueda);
        ps.setString(3, busqueda);
        ps.setString(4, busqueda);
        
        rs = ps.executeQuery();
        
        if (rs.next()) {
            String nss = rs.getString("numeroSeguro");
            System.out.println("NSS encontrado: " + nss);
            
            this.nssPacienteValidado = nss;
            return nss;
        }
        return null;
        
    } catch (SQLException ex) {
        System.err.println("Error obteniendo NSS del paciente: " + ex.getMessage());
        ex.printStackTrace();
        return null;
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
    
    
    
    
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox<String> cmbMedico;
    private com.raven.swing.TimePicker escogerHora;
    private com.toedter.calendar.JDateChooser fechaCita;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtIdCita;
    private javax.swing.JTextField txtNombrePaciente;
    // End of variables declaration//GEN-END:variables
}
