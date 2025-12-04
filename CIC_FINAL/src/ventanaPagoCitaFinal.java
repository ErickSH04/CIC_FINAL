import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigDecimal;
import javax.swing.JOptionPane;

public class ventanaPagoCitaFinal extends javax.swing.JFrame {

    private String usuarioId;
    private String idCitaPago; 

    public ventanaPagoCitaFinal(String usuarioId) {
        initComponents();
        this.usuarioId = usuarioId;

        txtPaciente.setEditable(false);
        txtNumTarjeta.setEditable(false);
        txtEspecialidad.setEditable(false);
        txtNomMed.setEditable(false);
        txtHora.setEditable(false);
        txtCosto.setEditable(false);
        txtFechaCita.setEditable(false);

        btnPagar.setText("Realizar Pago");
          String numeroSeguro = obtenerNumeroSeguro(usuarioId);

        cargarDatosParaPago(numeroSeguro);
        configurarPlaceholderCVV();
    }
   private String obtenerNumeroSeguro(String correo) {
    String sql = "SELECT numeroSeguro FROM PACIENTE WHERE Correo = ?";

    try (Connection conn = ConexionSQL.ConexionSQLServer();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, correo);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("numeroSeguro");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error obteniendo numeroSeguro: " + e.getMessage());
    }
    return null;
}



    private void configurarPlaceholderCVV() {
        txtCVV.setText("Ingrese CVV");
        txtCVV.setForeground(Color.GRAY);

        txtCVV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtCVV.getText().equals("Ingrese CVV")) {
                    txtCVV.setText("");
                    txtCVV.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtCVV.getText().isEmpty()) {
                    txtCVV.setText("Ingrese CVV");
                    txtCVV.setForeground(Color.GRAY);
                }
            }
        });
    }

    public void cargarDatosParaPago(String numeroSeguro) {
        String sql =
            "SELECT TOP 1 " +
            "p.nombrePac + ' ' + p.apellido1 + ' ' + ISNULL(p.apellido2, '') AS pacienteCompleto, " +
            "c.fecha, c.hora, c.idCita, " +
            "m.nombreMed + ' ' + m.apellido1 + ' ' + ISNULL(m.apellido2,'') AS medicoCompleto, " +
            "m.Especialidad, te.precio " +
            "FROM PACIENTE p " +
            "JOIN CITA c ON p.numeroSeguro = c.numeroSeguro " +
            "JOIN MEDICO m ON c.idMedico = m.idMedico " +
            "LEFT JOIN tarifa_especialidad te ON m.Especialidad = te.especialidad " +
            "WHERE p.numeroSeguro = ? AND c.estatus = 'Completado' " +
            "ORDER BY c.fecha DESC, c.hora DESC";

        try (Connection conn = ConexionSQL.ConexionSQLServer();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroSeguro);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtPaciente.setText(rs.getString("pacienteCompleto"));

                Date fecha = rs.getDate("fecha");
                txtFechaCita.setText(new SimpleDateFormat("dd/MM/yyyy").format(fecha));

                txtHora.setText(rs.getString("hora"));
                idCitaPago = rs.getString("idCita");
                txtNomMed.setText(rs.getString("medicoCompleto"));
                txtEspecialidad.setText(rs.getString("Especialidad"));

                BigDecimal precio = rs.getBigDecimal("precio");
                txtCosto.setText(NumberFormat.getCurrencyInstance().format(precio));

                cargarDatosTarjeta();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }

    private void cargarDatosTarjeta() {
        String sql =
            "SELECT TOP 1 numTarjeta " +
            "FROM Pago WHERE numeroSeguro = ? ORDER BY fechaPago DESC";

        try (Connection conn = ConexionSQL.ConexionSQLServer();
             PreparedStatement ps = conn.prepareStatement(sql)) {

           ps.setString(1, obtenerNumeroSeguro(usuarioId));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String numTarjeta = rs.getString("numTarjeta");
                if (numTarjeta != null && numTarjeta.length() >= 4) {
                    String ult4 = numTarjeta.substring(numTarjeta.length() - 4);
                    txtNumTarjeta.setText("**** **** **** " + ult4);
                } else {
                    txtNumTarjeta.setText("Sin tarjeta");
                }
            } else {
                txtNumTarjeta.setText("No tiene tarjeta registrada");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tarjeta: " + e.getMessage());
        }
    }

    private boolean validarDatosPago() {
        String cvv = txtCVV.getText().trim();
        if (cvv.equals("Ingrese CVV")) {
            JOptionPane.showMessageDialog(this, "Ingrese el CVV.");
            return false;
        }
        if (!cvv.matches("\\d{3,4}")) {
            JOptionPane.showMessageDialog(this, "CVV inválido.");
            return false;
        }

        Date fechaVenc = fechaCita.getDate();
        if (fechaVenc == null || fechaVenc.before(new Date())) {
            JOptionPane.showMessageDialog(this, "Fecha de vencimiento inválida.");
            return false;
        }

        return true;
    }

    private String obtenerNumeroTarjetaCompleto() {
        String sql = "SELECT TOP 1 numTarjeta FROM Pago WHERE numeroSeguro=? ORDER BY fechaPago DESC";

        try (Connection conn = ConexionSQL.ConexionSQLServer();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("numTarjeta");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error leyendo tarjeta: " + e.getMessage());
        }
        return null;
    }

    private void procesarPago() {
        if (!validarDatosPago()) return;

        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;

        try {
            conn = ConexionSQL.ConexionSQLServer();
            conn.setAutoCommit(false);

            String idPago = "P" + System.currentTimeMillis();
            BigDecimal monto = new BigDecimal(txtCosto.getText().replaceAll("[^0-9.]", ""));

            String numTarjetaReal = obtenerNumeroTarjetaCompleto();

            String sql =
                "INSERT INTO Pago (idPago, monto, fechaPago, numTarjeta, cvv, FechVencimiento, numeroSeguro) " +
                "VALUES (?, ?, GETDATE(), ?, ?, ?, ?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, idPago);
            ps.setBigDecimal(2, monto);
            ps.setString(3, numTarjetaReal);
            ps.setString(4, txtCVV.getText().replaceAll("\\D+", ""));
            ps.setDate(5, new java.sql.Date(fechaCita.getDate().getTime()));
            ps.setString(6, usuarioId);

            ps.executeUpdate();

            ps2 = conn.prepareStatement("UPDATE CITA SET pagoRealizado='SI' WHERE idCita=?");
            ps2.setString(1, idCitaPago);
            ps2.executeUpdate();

            conn.commit();

            mostrarComprobantePago(idPago, monto);

            dispose();

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            JOptionPane.showMessageDialog(this, "Error al procesar pago: " + e.getMessage());
        }
    }

    private void mostrarComprobantePago(String idPago, BigDecimal monto) {
        String msg =
            "===== PAGO EXITOSO =====\n" +
            "ID Pago: " + idPago + "\n" +
            "Paciente: " + txtPaciente.getText() + "\n" +
            "Médico: " + txtNomMed.getText() + "\n" +
            "Monto: " + NumberFormat.getCurrencyInstance().format(monto) + "\n" +
            "Tarjeta: " + txtNumTarjeta.getText();

        JOptionPane.showMessageDialog(this, msg, "Comprobante", JOptionPane.INFORMATION_MESSAGE);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblEspecialidad = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblPaciente = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblCVV = new javax.swing.JLabel();
        btnPagar = new javax.swing.JButton();
        txtCVV = new javax.swing.JTextField();
        txtPaciente = new javax.swing.JTextField();
        txtNumTarjeta = new javax.swing.JTextField();
        lblEspe = new javax.swing.JLabel();
        lblMedico = new javax.swing.JLabel();
        lblFechaCita = new javax.swing.JLabel();
        lblHora = new javax.swing.JLabel();
        txtEspecialidad = new javax.swing.JTextField();
        txtFechaCita = new javax.swing.JTextField();
        txtNomMed = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        lblCostoCita = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        fechaCita = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(136, 212, 234));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblEspecialidad.setBackground(new java.awt.Color(255, 255, 255));
        lblEspecialidad.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setFont(new java.awt.Font("Roboto", 2, 18)); // NOI18N
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favicon.png"))); // NOI18N
        lblLogo.setText("Pago de cita ");
        lblEspecialidad.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, -1, -1));

        lblPaciente.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lblPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paciente.png"))); // NOI18N
        lblPaciente.setText("Paciente:");
        lblEspecialidad.add(lblPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 149, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Tarjeta.png"))); // NOI18N
        jLabel7.setText("Número Tarjeta:");
        lblEspecialidad.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 210, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Vencimiento.png"))); // NOI18N
        jLabel8.setText("Vencimiento (MM/AA):");
        lblEspecialidad.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 260, -1));

        lblCVV.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lblCVV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CVV.png"))); // NOI18N
        lblCVV.setText("CVV:");
        lblEspecialidad.add(lblCVV, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, 114, 40));

        btnPagar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnPagar.setText("Pagar");
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });
        lblEspecialidad.add(btnPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, 132, -1));

        txtCVV.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lblEspecialidad.add(txtCVV, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 220, 110, -1));

        txtPaciente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblEspecialidad.add(txtPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 150, 221, -1));

        txtNumTarjeta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblEspecialidad.add(txtNumTarjeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 219, -1));

        lblEspe.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lblEspe.setText("Especialidad:");
        lblEspecialidad.add(lblEspe, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 120, 40));

        lblMedico.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lblMedico.setText("Medico:");
        lblEspecialidad.add(lblMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 80, 40));

        lblFechaCita.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lblFechaCita.setText("Dia de la cita:");
        lblEspecialidad.add(lblFechaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 130, -1));

        lblHora.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lblHora.setText("Hora de la cita:");
        lblEspecialidad.add(lblHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 130, -1));
        lblEspecialidad.add(txtEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 230, -1));
        lblEspecialidad.add(txtFechaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 130, -1));
        lblEspecialidad.add(txtNomMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 230, -1));
        lblEspecialidad.add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 130, -1));

        lblCostoCita.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lblCostoCita.setText("Costo de la cita:");
        lblEspecialidad.add(lblCostoCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 300, 140, -1));
        lblEspecialidad.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 300, 140, -1));
        lblEspecialidad.add(fechaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, 170, -1));

        jPanel1.add(lblEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 930, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
    // Validar CVV y fecha
    if (!validarDatosPago()) {
        return;
    }

    // Confirmar con el usuario
    int confirmar = JOptionPane.showConfirmDialog(this,
        "=== CONFIRMAR PAGO ===\n\n" +
        "👤 Paciente: " + txtPaciente.getText() + "\n" +
        "🩺 Médico: " + txtNomMed.getText() + "\n" +
        "📊 Especialidad: " + txtEspecialidad.getText() + "\n" +
        "💰 Costo: " + txtCosto.getText() + "\n" +
        "📅 Fecha de cita: " + txtFechaCita.getText() + " " + txtHora.getText() + "\n" +
        "🔢 Tarjeta: " + txtNumTarjeta.getText() + "\n" +
        "📅 Vencimiento: " + new SimpleDateFormat("MM/yyyy").format(fechaCita.getDate()) + "\n\n" +
        "¿Desea proceder con el pago?",
        "Confirmar Pago",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);

    if (confirmar == JOptionPane.YES_OPTION) {
       procesarPago();
    }
    }//GEN-LAST:event_btnPagarActionPerformed
 
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPagar;
    private com.toedter.calendar.JDateChooser fechaCita;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCVV;
    private javax.swing.JLabel lblCostoCita;
    private javax.swing.JLabel lblEspe;
    private javax.swing.JPanel lblEspecialidad;
    private javax.swing.JLabel lblFechaCita;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblPaciente;
    private javax.swing.JTextField txtCVV;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtFechaCita;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtNomMed;
    private javax.swing.JTextField txtNumTarjeta;
    private javax.swing.JTextField txtPaciente;
    // End of variables declaration//GEN-END:variables
}