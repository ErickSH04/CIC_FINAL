import java.awt.Color;
import java.sql.Statement;
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
    private String numeroSeguroActual;
    private String tarjeta;

public ventanaPagoCitaFinal(String usuarioId) {
    initComponents();
    this.usuarioId = usuarioId;

    // Solo estos DOS se editan:
    txtCVV.setEditable(true);
    fechaCita.setEnabled(true);

    // Todo lo demás bloqueado
    txtPaciente.setEditable(false);
    txtEspecialidad.setEditable(false);
    txtNomMed.setEditable(false);
    txtHora.setEditable(false);
    txtCosto.setEditable(false);
    txtFechaCita.setEditable(false);

    btnPagar.setText("Realizar Pago");

    // Resolver NSS solo una vez
    this.numeroSeguroActual = resolverNumeroSeguro(usuarioId);

    // Cargar todo usando SIEMPRE numeroSeguroActual
    cargarDatosParaPago(numeroSeguroActual);
    configurarPlaceholderCVV();
}

private String resolverNumeroSeguro(String id) {
    if (id == null || id.trim().isEmpty()) return null;
    if (id.contains("@")) {
        // id es correo -> buscar NSS
        return obtenerNumeroSeguro(id);
    }
    // id ya es NSS
    return id.trim();
}

 private void agregarPlaceholders() {
        // Número de Tarjeta
        txtNumTarjeta.setText("Ingrese número de tarjeta");
        txtNumTarjeta.setForeground(Color.GRAY);
        txtNumTarjeta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtNumTarjeta.getText().equals("Ingrese número de tarjeta")) {
                    txtNumTarjeta.setText("");
                    txtNumTarjeta.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtNumTarjeta.getText().isEmpty()) {
                    txtNumTarjeta.setForeground(Color.GRAY);
                    txtNumTarjeta.setText("Ingrese número de tarjeta");
                }
            }
        });
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
          // Número de Tarjeta
        txtNumTarjeta.setText("Ingrese número de tarjeta");
        txtNumTarjeta.setForeground(Color.GRAY);
        txtNumTarjeta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtNumTarjeta.getText().equals("Ingrese número de tarjeta")) {
                    txtNumTarjeta.setText("");
                    txtNumTarjeta.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtNumTarjeta.getText().isEmpty()) {
                    txtNumTarjeta.setForeground(Color.GRAY);
                    txtNumTarjeta.setText("Ingrese número de tarjeta");
                }
            }
        });
        
        
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
// agrega arriba en la clase:
private BigDecimal tarifa = BigDecimal.ZERO;
private BigDecimal yaPagado = BigDecimal.ZERO;
private BigDecimal importeAPagar = BigDecimal.ZERO;
    
  public void cargarDatosParaPago(String numeroSeguro) {
    this.numeroSeguroActual = numeroSeguro; // guarda el NSS definitivo

    String sql =
        "WITH tot AS ( " +
        "  SELECT pc.idCita, SUM(p.monto) AS total_pagado " +
        "  FROM Pago p " +
        "  JOIN PagoCita pc ON pc.idPago = p.idPago " +
        "  GROUP BY pc.idCita " +
        ") " +
        "SELECT TOP 1 " +
        "  p.nombrePac + ' ' + p.apellido1 + ' ' + ISNULL(p.apellido2,'') AS pacienteCompleto, " +
        "  c.fecha, c.hora, c.idCita, " +
        "  m.nombreMed + ' ' + m.apellido1 + ' ' + ISNULL(m.apellido2,'') AS medicoCompleto, " +
        "  m.Especialidad, te.precio AS tarifa, ISNULL(tot.total_pagado,0) AS total_pagado " +
        "FROM CITA c " +
        "JOIN PACIENTE p ON p.numeroSeguro = c.numeroSeguro " +
        "JOIN MEDICO m   ON m.idMedico = c.idMedico " +
        "LEFT JOIN tarifa_especialidad te ON te.especialidad = m.Especialidad " +
        "LEFT JOIN tot ON tot.idCita = c.idCita " +
        "WHERE p.numeroSeguro = ? AND c.estatus IN ('Activa','Completado') " +
        "ORDER BY c.fecha DESC, c.hora DESC";

    try (Connection conn = ConexionSQL.ConexionSQLServer();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, this.numeroSeguroActual);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                txtPaciente.setText(rs.getString("pacienteCompleto"));
                Date fecha = rs.getDate("fecha");
                txtFechaCita.setText(new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha));
                txtHora.setText(rs.getString("hora"));
                idCitaPago = rs.getString("idCita");
                txtNomMed.setText(rs.getString("medicoCompleto"));
                txtEspecialidad.setText(rs.getString("Especialidad"));

                tarifa = rs.getBigDecimal("tarifa");       if (tarifa == null) tarifa = BigDecimal.ZERO;
                yaPagado = rs.getBigDecimal("total_pagado");if (yaPagado == null) yaPagado = BigDecimal.ZERO;
                importeAPagar = tarifa.subtract(yaPagado);
                if (importeAPagar.compareTo(BigDecimal.ZERO) < 0) importeAPagar = BigDecimal.ZERO;

                txtCosto.setText(java.text.NumberFormat.getCurrencyInstance().format(importeAPagar));

                // AHORA sí: usa el MISMO NSS ya resuelto
                //cargarDatosTarjeta(this.numeroSeguroActual);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró una cita 'Completado' para pagar.");
                dispose();
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
    }
}

private void cargarDatosTarjeta(String numeroSeguro) {
    if (numeroSeguro == null || numeroSeguro.trim().isEmpty()) {
        txtNumTarjeta.setText("No tiene tarjeta registrada");
        return;
    }

    String sql = "SELECT NUMTARJETA FROM PAGO WHERE CVV = ?";

    try (Connection conn = ConexionSQL.ConexionSQLServer();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, numeroSeguro);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String numTarjeta = rs.getString("numTarjeta");

                if (numTarjeta != null && numTarjeta.length() >= 4) {
                    // Mostrar solo los últimos 4 dígitos
                    String ult4 = numTarjeta.substring(numTarjeta.length() - 4);
                    txtNumTarjeta.setText("**** **** **** " + ult4);
                } else {
                    txtNumTarjeta.setText("Sin tarjeta");
                }

            } else {
                txtNumTarjeta.setText("No tiene tarjeta registrada");
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar tarjeta: " + e.getMessage());
    }
}


   private boolean validarDatosPago() {
      // 1. Validar que se haya cargado el paciente (usando usuarioId)
    if (usuarioId == null || usuarioId.trim().isEmpty() || txtPaciente.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "No se ha cargado la información del paciente.\nVerifique el NSS.",
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // 2. Validar número de tarjeta
    String numTarjeta = txtNumTarjeta.getText().replaceAll("\\D+", "");
    if (numTarjeta.length() < 13 || numTarjeta.length() > 19) {
        JOptionPane.showMessageDialog(this,
                "Número de tarjeta inválido.\nDebe contener entre 13 y 19 dígitos.",
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
        txtNumTarjeta.requestFocus();
        txtNumTarjeta.selectAll();
        return false;
    }  
       
       
    // CVV
    String cvv = txtCVV.getText().trim().replaceAll("\\D+","");
    if (cvv.length() < 3 || cvv.length() > 4) {
        JOptionPane.showMessageDialog(this, "CVV inválido.");
        txtCVV.requestFocus(); return false;
    }
    // Vencimiento
    if (fechaCita.getDate() == null || fechaCita.getDate().before(new java.util.Date())) {
        JOptionPane.showMessageDialog(this, "Vencimiento inválido.");
        fechaCita.requestFocus(); return false;
    }
    return true;
}

   private String obtenerNumeroTarjetaCompleto() {
    if (this.numeroSeguroActual == null || this.numeroSeguroActual.trim().isEmpty()) return null;

    String sql = "SELECT TOP 1 numTarjeta FROM Pago WHERE numeroSeguro=? ORDER BY fechaPago DESC";
    try (Connection conn = ConexionSQL.ConexionSQLServer();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, this.numeroSeguroActual);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getString("numTarjeta");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error leyendo tarjeta: " + e.getMessage());
    }
    return null;
}

   private void procesarPago() {
    if (!validarDatosPago()) return;

    if (importeAPagar.compareTo(BigDecimal.ZERO) <= 0) {
        JOptionPane.showMessageDialog(this, "Esta cita ya está pagada.");
        return;
    }

    Connection conn = null;
    PreparedStatement psPago = null;
    PreparedStatement psLink = null;
    PreparedStatement psUpd  = null;

    try {
        conn = ConexionSQL.ConexionSQLServer();
        conn.setAutoCommit(false);

        // último número de tarjeta usado por el paciente
        String numTarjetaReal = obtenerNumeroTarjetaCompleto(); // ya la tienes
        if (numTarjetaReal == null || numTarjetaReal.isBlank()) {
            JOptionPane.showMessageDialog(this, "No hay tarjeta registrada para este paciente.");
            return;
        }

        // 1) Insertar pago por el IMPORTE RESTANTE
        String sqlPago =
            "INSERT INTO Pago (monto, fechaPago, tipoPago, numTarjeta, cvv, FechVencimiento, numeroSeguro) " +
            "VALUES (?, GETDATE(), 'Tarjeta', ?, ?, ?, ?)";
        psPago = conn.prepareStatement(sqlPago, Statement.RETURN_GENERATED_KEYS);
        psPago.setBigDecimal(1, importeAPagar);
        psPago.setString(2, numTarjetaReal);
        psPago.setString(3, txtCVV.getText().replaceAll("\\D+",""));
        psPago.setDate  (4, new java.sql.Date(fechaCita.getDate().getTime())); // vencimiento
        psPago.setString(5, this.numeroSeguroActual);                   // NSS del paciente
        psPago.executeUpdate();

        int idPago;
        try (ResultSet gk = psPago.getGeneratedKeys()) {
            if (!gk.next()) throw new SQLException("No se obtuvo idPago.");
            idPago = gk.getInt(1);
        }

        // 2) Ligar pago a la cita
        String sqlPC = "INSERT INTO PagoCita(idPago, idCita) VALUES(?, ?)";
        psLink = conn.prepareStatement(sqlPC);
        psLink.setInt(1, idPago);
        psLink.setString(2, idCitaPago);
        psLink.executeUpdate();

        // 3) Calcular nuevo total y, si cubre tarifa, marcar como pagada
        BigDecimal nuevoTotal = yaPagado.add(importeAPagar);
        if (tarifa.compareTo(BigDecimal.ZERO) > 0 && nuevoTotal.compareTo(tarifa) >= 0) {
            psUpd = conn.prepareStatement("UPDATE CITA SET pagoRealizado = 'SI' WHERE idCita = ?");
            psUpd.setString(1, idCitaPago);
            psUpd.executeUpdate();
        }

        conn.commit();

        // 4) Comprobante
        String numTar = txtNumTarjeta.getText(); // ya está enmascarada
        String msg =
            "===== PAGO EXITOSO =====\n" +
            "ID Pago: " + idPago + "\n" +
            "Paciente: " + txtPaciente.getText() + "\n" +
            "Médico: " + txtNomMed.getText() + "\n" +
            "Monto: " + java.text.NumberFormat.getCurrencyInstance().format(importeAPagar) + "\n" +
            "Tarjeta: " + numTar + "\n" +
            "Estatus: " + (nuevoTotal.compareTo(tarifa) >= 0 ? "Pagado" : "Pago incompleto");
        JOptionPane.showMessageDialog(this, msg, "Comprobante", JOptionPane.INFORMATION_MESSAGE);

        dispose(); // cerrar la ventana

    } catch (Exception e) {
        try { if (conn != null) conn.rollback(); } catch (Exception ignore) {}
        JOptionPane.showMessageDialog(this, "Error al procesar pago: " + e.getMessage());
    } finally {
        try { if (psUpd  != null) psUpd.close(); }  catch (Exception ignore) {}
        try { if (psLink != null) psLink.close(); } catch (Exception ignore) {}
        try { if (psPago != null) psPago.close(); } catch (Exception ignore) {}
        try { if (conn   != null) conn.close(); }  catch (Exception ignore) {}
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
        lblEspecialidad.add(btnPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 370, 230, -1));

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