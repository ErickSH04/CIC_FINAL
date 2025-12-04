
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.showMessageDialog;


public class registroCitaMed extends javax.swing.JFrame {
    
    private medico medico;
    private static Statement stmt;
    public static Connection con;
    

    public registroCitaMed(medico medico) {
        initComponents();
        this.setLocationRelativeTo(this);
        this.medico=ventanaMedico.getMedico();
        con = ConexionSQL.ConexionSQLServer();
        txtIdCita.setEditable(false);
        obtenerCita();
    }
    
    
    private void obtenerCita() {
        con = ConexionSQL.ConexionSQLServer();
        Statement stmt3;
        ResultSet resultadoNSS, resultado;
        String idCita = "";
        
        
        try {
        con = ConexionSQL.ConexionSQLServer();
        stmt3 = con.createStatement();

        // CONSULTA CORREGIDA - Orden numérico, no alfabético
        String cita = "SELECT MAX(CAST(SUBSTRING(idCita, 2, LEN(idCita)) AS INT)) as max_num FROM Cita WHERE idCita LIKE 'C%'";
        System.out.println("Ejecutando: " + cita);

        resultado = stmt3.executeQuery(cita);

        String idFinal = "C1"; // Valor por defecto
        
        if (resultado.next()) {
            int maxNum = resultado.getInt("max_num");
            System.out.println("Último número encontrado: " + maxNum);
            
            if (!resultado.wasNull()) { // Verificar que no sea NULL
                int nuevoNumero = maxNum + 1;
                idFinal = "C" + nuevoNumero;
                System.out.println("Nuevo ID generado: " + idFinal);
            } else {
                System.out.println("No hay citas, empezando con C1");
            }
        } else {
            System.out.println("No se encontraron resultados, usando C1");
        }
        
        // Asignar el ID al campo de texto
        txtIdCita.setText(idFinal);
        System.out.println("ID final asignado: " + idFinal);

    } catch (SQLException ex) {
        System.err.println("Error SQL: " + ex.getMessage());
        ex.printStackTrace();
        txtIdCita.setText("C1");
    } catch (Exception ex) {
        System.err.println("Error inesperado: " + ex.getMessage());
        ex.printStackTrace();
        txtIdCita.setText("C1");
    } 
   }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timePicker1 = new com.raven.swing.TimePicker();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        txtIdCita = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        btnAtras = new javax.swing.JButton();
        dcFecha = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        txtApellidoM = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNss = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtApellidoP = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        escogerHora = new com.raven.swing.TimePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(136, 212, 234));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Roboto", 2, 18)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favicon.png"))); // NOI18N
        jLabel6.setText("Registra Cita");

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel7.setText("idCita:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel8.setText("Fecha:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel9.setText("Hora:");

        btnAceptar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnAceptar.setText("Confirmar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        txtIdCita.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtHora.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N

        btnAtras.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnAtras.setText("Atrás");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        dcFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel10.setText("Nombre:");

        txtApellidoM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel11.setText("Apellido M:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel5.setText("NSS:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel13.setText("Apellido P:");

        txtApellidoP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5)
                        .addComponent(jLabel7))
                    .addComponent(jLabel11)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApellidoP, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidoM, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNss, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 86, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jLabel6)))
                .addGap(29, 29, 29))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtApellidoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtApellidoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dcFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnAtras))
                .addGap(30, 30, 30))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 390, 420));

        escogerHora.set24hourMode(true);
        escogerHora.setDisplayText(txtHora);
        jPanel1.add(escogerHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
    // 1. Verificar fecha
    if (dcFecha.getDate() == null) {
        showMessageDialog(null, "Error: Debe seleccionar una fecha");
        return;
    }
    
    if (txtHora.getText().trim().isEmpty()) {
        showMessageDialog(null, "Error: Debe seleccionar una hora");
        return;
    }
 
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String fechaFormato = sdf.format(dcFecha.getDate());
    String hora = txtHora.getText();
    
    if (!verificarDisponibilidad(this.medico.getIdMedico(), fechaFormato, hora)) {
        showMessageDialog(null, 
            "Horario no disponible\n\n" +
            "El medico ya tiene una cita programada para:\n" +
            "" + fechaFormato + "\n" +
            "" + hora + "\n\n" +
            "Por favor, seleccione otro horario."
        );
        return;
    }
    
    // Si pasa todas las validaciones, registrar cita
    registrarCita(this.medico.getCorreo());
    }//GEN-LAST:event_btnAceptarActionPerformed

    private boolean verificarDisponibilidad(String idMedico, String fecha, String hora){
           Connection con = null;
           PreparedStatement pstmt = null;
           ResultSet rs = null;
    
    try {
        con = ConexionSQL.ConexionSQLServer();
        
        String query = "SELECT COUNT(*) as citas_existentes " +
                      "FROM Cita " +
                      "WHERE idMedico = ? AND fecha = ? AND hora = ?";
        
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, idMedico);
        pstmt.setString(2, fecha);
        pstmt.setString(3, hora);
        
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            int citasExistentes = rs.getInt("citas_existentes");
            System.out.println("Citas existentes en ese horario: " + citasExistentes);
            return citasExistentes == 0; // true = disponible, false = ocupado
        }
        
    } catch (SQLException ex) {
        System.err.println("Error verificando disponibilidad: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        // Cerrar recursos
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            System.err.println("Error cerrando recursos: " + ex.getMessage());
        }
    }
    return false;
  }

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        txtIdCita.setText("");
        txtHora.setText("");
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        char c = (char) evt.getKeyCode();
        if (c == evt.VK_ENTER) {
            System.out.println("Numero de seguro: "+obtenerNss(txtNombre.getText(), txtApellidoP.getText(), txtApellidoM.getText()));
            txtNss.setText(obtenerNss(txtNombre.getText(), txtApellidoP.getText(), txtApellidoM.getText()));   
        }
    }//GEN-LAST:event_txtNombreKeyPressed
    
    private String obtenerNss(String nombre, String apellido1, String apellido2){
        ResultSet rs;
        Statement s;
        String ns = "";
        
        String query = "select numeroSeguro\n" +
                        "from paciente \n" +
                        "where nombrePac like '"+nombre+"%' and apellido1 like '"+apellido1+"%'and apellido2 like '"+apellido2+"%'";
        try {
            
            s = con.createStatement();
            rs = s.executeQuery(query);
            while(rs.next()){
                ns = rs.getString("numeroSeguro");
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroCitaMed.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return ns;
    }
    
    private void registrarCita(String correo) {
        String nss, hora, idCita;
        con = ConexionSQL.ConexionSQLServer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = dcFecha.getDate();
        String fechaFormato = sdf.format(fecha);
      
       LocalDate hoy = LocalDate.now();
       LocalDate fechaCita = dcFecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        nss = txtNss.getText();
        idCita = txtIdCita.getText();
        LocalTime horaCita = LocalTime.parse(txtHora.getText());
        
        
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime cita = LocalDateTime.of(fechaCita, horaCita);

    // 1. No permitir fechas en el pasado
        if (fechaCita.isBefore(LocalDate.now())) {
            showMessageDialog(null,"No se permite agendar citas en fechas pasadas");
            return;
        }

    // 2. Si la cita es hoy, validar que la hora no sea pasada
        if (fechaCita.isEqual(LocalDate.now()) && horaCita.isBefore(LocalTime.now())) {
            showMessageDialog(null,"No se permite agendar citas horas pasadas");
            return;
        }

    // 3. Validar al menos 3 horas de anticipación
        if (cita.isBefore(ahora.plusHours(3))) {
            showMessageDialog(null,"Agende en otra fecha y hora con mínimo 3 horas de anticipación.");
            return ;
        }
        
        /*
        LocalTime compara;
        LocalTime actual = LocalTime.now();
        compara = LocalTime.parse(hora);

        
        if (fechaCita.isBefore(hoy)) {
            showMessageDialog(null, "No puede agendar citas en una fecha pasada.");
        return;
        }
        
        if (fechaCita.isEqual(hoy)) {
            if (compara.isBefore(actual)) {
                showMessageDialog(null, "No puede agendar citas antes de la hora actual.");
                return;
            }
        }*/

        String query = "INSERT INTO Cita (idCita, fecha, hora, numeroSeguro, idMedico, estatus)\n"
          +"VALUES ('"+idCita+"','"+fechaFormato+"','"+txtHora.getText()+"','"+nss+"',"+Integer.parseInt(this.medico.getIdMedico())+",'Activa');";
        Statement stmt2;
        String queryD = "SELECT Correo\n"
                + "FROM Paciente\n"
                + "WHERE numeroSeguro = '" + nss + "';";
        
        //String correoPac = "";
        /*try {
            stmt2 = con.createStatement();
            ResultSet rs = stmt.executeQuery(queryD);
            while (rs.next()) {                
                 correoPac = rs.getString("correo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroCitaPac.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        String mensaje = "Programaste una cita para el " + fechaFormato + " a las " + txtHora.getText() + ".\n";
        String mensajePac = "Tienes una cita para el " + fechaFormato + " a las " + txtHora.getText() + ".\n";

        //NotificacionCorreo noti = new NotificacionCorreo();
        //noti.enviarCorreo(correo, "Recordatorio de cita médica", mensaje);
        //noti.enviarCorreo(correoPac, "Recordatorio de cita médica", mensajePac);
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            showMessageDialog(null, "Cita agregada con exito");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            showMessageDialog(null, "Error al insertar cita");
            return;
        }

        this.dispose();

    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAtras;
    private com.toedter.calendar.JDateChooser dcFecha;
    private com.raven.swing.TimePicker escogerHora;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.raven.swing.TimePicker timePicker1;
    private javax.swing.JTextField txtApellidoM;
    private javax.swing.JTextField txtApellidoP;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtIdCita;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNss;
    // End of variables declaration//GEN-END:variables
}
