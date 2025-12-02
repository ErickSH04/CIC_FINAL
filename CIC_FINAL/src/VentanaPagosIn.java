
import java.awt.Color;
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
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;

public class VentanaPagosIn extends javax.swing.JFrame {

    private static String usuarioId;
    private static Statement stmt;
    public static Connection con;
    private String numeroSeguroSeleccionado; // Variable para almacenar el número de seguro
    private String nombrePacienteConfirmado; // Variable para almacenar el nombre confirmado

    public VentanaPagosIn(String usuarioId) {//String usuarioId
        this.usuarioId = usuarioId;
        this.numeroSeguroSeleccionado = null; // Inicializar como null
        this.nombrePacienteConfirmado = null;
        initComponents();
        this.setLocationRelativeTo(this);
        con = ConexionSQL.ConexionSQLServer();
        
        agregarPlaceholders();
        configurarValidacionPaciente(); // Configurar la validación del paciente
    }
    
    private int obtenerProximoIdPago() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int proximoId = 1;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn != null) {
            // Obtener el máximo ID actual
            String query = "SELECT ISNULL(MAX(idPago), 0) + 1 as proximo_id FROM Pago";
            
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                proximoId = rs.getInt("proximo_id");
            }
        }
    } catch (SQLException ex) {
        System.err.println("Error obteniendo próximo ID de pago: " + ex.getMessage());
        // Si hay error, genera un ID basado en timestamp
        proximoId = (int) (System.currentTimeMillis() % 1000000);
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
    
    return proximoId;
}
    
    
    // Configurar el KeyListener para el campo de nombre del paciente
    private void configurarValidacionPaciente() {
    TextNombreTitular.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyPressed(java.awt.event.KeyEvent evt) {
            if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                validarPaciente();
            }
        }
    });
  }


// Método para validar paciente (similar al de registroCitaPac)
private void validarPaciente() {
    String nombrePaciente = TextNombreTitular.getText().trim();
    
    if (nombrePaciente.isEmpty() || nombrePaciente.equals("Ingrese nombre del paciente")) {
        JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre del paciente");
        TextNombreTitular.requestFocus();
        return;
    }
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn != null) {
            // Consulta para buscar paciente (similar a la de registroCitaPac)
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
                
                // Mostrar confirmación
                int opcion = JOptionPane.showConfirmDialog(this,
                    "Paciente encontrado:\n" +
                    "Nombre: " + nombreCompleto + "\n" +
                    "NSS: " + nss + "\n\n" +
                    "¿Es este el paciente correcto?",
                    "Confirmar Paciente",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    // Guardar el número de seguro seleccionado
                    this.numeroSeguroSeleccionado = nss;
                    this.nombrePacienteConfirmado = nombreCompleto;
                    
                    // Actualizar el campo con el nombre completo
                    TextNombreTitular.setText(nombreCompleto);
                    TextNombreTitular.setEditable(false);
                    TextNombreTitular.setForeground(new Color(0, 100, 0)); // Verde oscuro para indicar confirmado
                    
                   
                    
                    JOptionPane.showMessageDialog(this, 
                        "Paciente confirmado:\n" +
                        "Nombre: " + nombreCompleto + "\n" +
                        "NSS: " + nss);
                } else {
                    this.numeroSeguroSeleccionado = null;
                    this.nombrePacienteConfirmado = null;
                    TextNombreTitular.setEditable(true);
                    TextNombreTitular.setForeground(Color.BLACK);
                    
                    
                }
            } else {
                // PACIENTE NO ENCONTRADO
                int opcion = JOptionPane.showConfirmDialog(this,
                    "No se encontró el paciente: " + nombrePaciente + "\n\n" +
                    "¿Desea intentar con otro nombre?",
                    "Paciente No Encontrado",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    TextNombreTitular.setText("");
                    TextNombreTitular.requestFocus();
                } else {
                    TextNombreTitular.setText("Ingrese nombre del paciente");
                    TextNombreTitular.setForeground(Color.GRAY);
                }
                this.numeroSeguroSeleccionado = null;
                this.nombrePacienteConfirmado = null;
                
                
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, 
            "Error al buscar paciente: " + ex.getMessage());
        this.numeroSeguroSeleccionado = null;
        this.nombrePacienteConfirmado = null;
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}

private void procesarPago() {
    // Validar que todos los campos estén llenos
    if (TextNumeroTarjeta.getText().isEmpty() || 
        TextNumeroTarjeta.getText().equals("Ingrese número de tarjeta")) {
        JOptionPane.showMessageDialog(this, "Ingrese el número de tarjeta");
        TextNumeroTarjeta.requestFocus();
        return;
    }
    
    if (TextNombreTitular.getText().isEmpty() || 
        TextNombreTitular.getText().equals("Ingrese nombre del paciente") ||
        TextNombreTitular.isEditable()) { // Si aún es editable, no se ha validado
        JOptionPane.showMessageDialog(this, 
            "Por favor, valide el paciente primero\n" +
            "(Escriba el nombre y presione ENTER)");
        TextNombreTitular.requestFocus();
        return;
    }
    
    // Validar que se haya seleccionado un paciente
    if (numeroSeguroSeleccionado == null || numeroSeguroSeleccionado.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un paciente válido primero");
        TextNombreTitular.requestFocus();
        return;
    }
    
    if (TextFechaVencimiento.getText().isEmpty() || 
        TextFechaVencimiento.getText().equals("MM/AA")) {
        JOptionPane.showMessageDialog(this, "Ingrese la fecha de vencimiento");
        TextFechaVencimiento.requestFocus();
        return;
    }
    
    if (TextCVV.getText().isEmpty() || 
        TextCVV.getText().equals("123")) {
        JOptionPane.showMessageDialog(this, "Ingrese el CVV");
        TextCVV.requestFocus();
        return;
    }
    
    if (TextMonto.getText().isEmpty() || 
        TextMonto.getText().equals("$0.00") ||
        TextMonto.getText().equals("$")) {
        JOptionPane.showMessageDialog(this, "Ingrese el monto");
        TextMonto.requestFocus();
        return;
    }
    
    // Obtener valores
    String numeroTarjeta = TextNumeroTarjeta.getText().trim();
    String nombrePaciente = TextNombreTitular.getText().trim();
    String fechaVencimiento = TextFechaVencimiento.getText().trim();
    String cvv = TextCVV.getText().trim();
    String montoStr = TextMonto.getText().trim();
    
    // Validar formato de tarjeta
    if (!validarNumeroTarjeta(numeroTarjeta)) {
        JOptionPane.showMessageDialog(this, 
            "Número de tarjeta inválido.\nDebe tener entre 13 y 19 dígitos");
        TextNumeroTarjeta.requestFocus();
        return;
    }
    
    // Validar fecha de vencimiento
    if (!validarFechaVencimiento(fechaVencimiento)) {
        JOptionPane.showMessageDialog(this, 
            "Fecha de vencimiento inválida.\nUse formato MM/AA (mes/año)");
        TextFechaVencimiento.requestFocus();
        return;
    }
    
    // Validar CVV
    if (!validarCVV(cvv)) {
        JOptionPane.showMessageDialog(this, 
            "CVV inválido.\nDebe tener 3 o 4 dígitos");
        TextCVV.requestFocus();
        return;
    }
    
    // Procesar monto
    double monto = 0.0;
    try {
        // Remover el símbolo $ si existe
        if (montoStr.startsWith("$")) {
            montoStr = montoStr.substring(1).trim();
        }
        monto = Double.parseDouble(montoStr);
        if (monto <= 0) {
            JOptionPane.showMessageDialog(this, "El monto debe ser mayor a 0");
            TextMonto.requestFocus();
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Monto inválido. Ingrese un número válido");
        TextMonto.requestFocus();
        return;
    }
    
    // Confirmar el pago
    int confirmacion = JOptionPane.showConfirmDialog(
        this,
        "¿Confirmar pago?\n\n" +
        "Paciente: " + nombrePaciente + "\n" +
        "NSS: " + numeroSeguroSeleccionado + "\n" +
        "Monto: $" + String.format("%.2f", monto) + "\n" +
        "Tarjeta: **** **** **** " + 
        (numeroTarjeta.length() >= 4 ? numeroTarjeta.substring(numeroTarjeta.length() - 4) : numeroTarjeta),
        "Confirmar Pago",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );
    
    if (confirmacion == JOptionPane.YES_OPTION) {
        // Insertar en la base de datos
        if (insertarPago(numeroTarjeta, nombrePaciente, monto, numeroSeguroSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Pago realizado exitosamente");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al realizar el pago");
        }
    }
}


private void limpiarCampos() {
    TextNumeroTarjeta.setText("Ingrese número de tarjeta");
    TextNumeroTarjeta.setForeground(Color.GRAY);
    
    TextNombreTitular.setText("Ingrese nombre del paciente");
    TextNombreTitular.setForeground(Color.GRAY);
    TextNombreTitular.setEditable(true);
    
    TextFechaVencimiento.setText("MM/AA");
    TextFechaVencimiento.setForeground(Color.GRAY);
    
    TextCVV.setText("123");
    TextCVV.setForeground(Color.GRAY);
    
    TextMonto.setText("$0.00");
    TextMonto.setForeground(Color.GRAY);
    
    // Limpiar variables
    numeroSeguroSeleccionado = null;
    nombrePacienteConfirmado = null;
    
}

      private boolean insertarPago(String numTarjeta, String nombrePaciente, double monto, String numeroSeguro) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Error de conexión a la base de datos");
            return false;
        }
        
        // Obtener el próximo ID de pago
        int idPago = obtenerProximoIdPago();
        
        // Obtener solo los últimos 4 dígitos de la tarjeta por seguridad
        String ultimos4Digitos = "";
        if (numTarjeta.length() >= 4) {
            ultimos4Digitos = numTarjeta.substring(numTarjeta.length() - 4);
        } else {
            ultimos4Digitos = numTarjeta;
        }
        
        // Insertar en la tabla Pago
        String query = "INSERT INTO Pago (idPago, monto, fechaPago, tipoPago, numTarjeta, numeroSeguro) " +
                      "VALUES (?, ?, GETDATE(), ?, ?, ?)";
        
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, idPago);           // idPago (INT)
        pstmt.setDouble(2, monto);         // monto
        pstmt.setInt(3, 1);                // tipoPago (1 = Tarjeta)
        pstmt.setString(4, ultimos4Digitos); // últimos 4 dígitos
        pstmt.setString(5, numeroSeguro);  // numeroSeguro
        
        int filasAfectadas = pstmt.executeUpdate();
        
        if (filasAfectadas > 0) {
            // Mostrar comprobante en un diálogo
            //mostrarComprobantePago(idPago, monto, numeroSeguro, ultimos4Digitos, nombrePaciente);
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el pago", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(VentanaPagosIn.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error SQL: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        return false;
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPagosIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
     



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        lblPago = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        TextNumeroTarjeta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TextNombreTitular = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TextFechaVencimiento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TextCVV = new javax.swing.JTextField();
        btnPagar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        TextMonto = new javax.swing.JTextField();

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
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(lblInicio)
                    .addComponent(lblSalir))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblUsuario)
                .addGap(98, 98, 98)
                .addComponent(lblInicio)
                .addGap(28, 28, 28)
                .addComponent(lblSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblPago.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/metodo-de-pago.png"))); // NOI18N
        lblPago.setText("Pago");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logotipos-de-la-visa-y-mastercard-102631953.png"))); // NOI18N
        jLabel3.setText("jLabel3");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tarjeta-de-credito.png"))); // NOI18N

        TextNumeroTarjeta.setText("jTextField1");

        jLabel2.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabel2.setText("Número de Tarjeta");

        jLabel4.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabel4.setText("Nombre del TItular");

        TextNombreTitular.setText("jTextField1");
        TextNombreTitular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextNombreTitularActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabel5.setText("Fecha de Vencimiento");

        TextFechaVencimiento.setText("jTextField1");
        TextFechaVencimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFechaVencimientoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabel6.setText("CVV");

        TextCVV.setText("jTextField1");
        TextCVV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextCVVActionPerformed(evt);
            }
        });

        btnPagar.setText("Pagar Ahora");
        btnPagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPagarMouseClicked(evt);
            }
        });
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabel7.setText("Ingrese Monto");

        TextMonto.setText("jTextField1");
        TextMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextMontoActionPerformed(evt);
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
                        .addGap(355, 355, 355)
                        .addComponent(lblPago))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 657, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextNumeroTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(TextFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(TextMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TextNombreTitular, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TextCVV, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnPagar))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblPago)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextNombreTitular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextNumeroTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextCVV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPagar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
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

    
    // Método para agregar placeholders
    private void agregarPlaceholders() {
        // Número de Tarjeta
        TextNumeroTarjeta.setText("Ingrese número de tarjeta");
        TextNumeroTarjeta.setForeground(Color.GRAY);
        TextNumeroTarjeta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextNumeroTarjeta.getText().equals("Ingrese número de tarjeta")) {
                    TextNumeroTarjeta.setText("");
                    TextNumeroTarjeta.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextNumeroTarjeta.getText().isEmpty()) {
                    TextNumeroTarjeta.setForeground(Color.GRAY);
                    TextNumeroTarjeta.setText("Ingrese número de tarjeta");
                }
            }
        });

        // Nombre del Titular
        TextNombreTitular.setText("Ingrese nombre completo");
        TextNombreTitular.setForeground(Color.GRAY);
        TextNombreTitular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextNombreTitular.getText().equals("Ingrese nombre completo")) {
                    TextNombreTitular.setText("");
                    TextNombreTitular.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextNombreTitular.getText().isEmpty()) {
                    TextNombreTitular.setForeground(Color.GRAY);
                    TextNombreTitular.setText("Ingrese nombre completo");
                }
            }
        });

        // Fecha de Vencimiento
        TextFechaVencimiento.setText("MM/AA");
        TextFechaVencimiento.setForeground(Color.GRAY);
        TextFechaVencimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextFechaVencimiento.getText().equals("MM/AA")) {
                    TextFechaVencimiento.setText("");
                    TextFechaVencimiento.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextFechaVencimiento.getText().isEmpty()) {
                    TextFechaVencimiento.setForeground(Color.GRAY);
                    TextFechaVencimiento.setText("MM/AA");
                }
            }
        });

        // Monto
        TextCVV.setText("123");
        TextCVV.setForeground(Color.GRAY);
        TextCVV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextCVV.getText().equals("123")) {
                    TextCVV.setText("");
                    TextCVV.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextCVV.getText().isEmpty()) {
                    TextCVV.setForeground(Color.GRAY);
                    TextCVV.setText("123");
                }
            }
        });
        
        
        TextMonto.setText("$");
        TextMonto.setForeground(Color.GRAY);
        TextMonto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextMonto.getText().equals("123")) {
                    TextMonto.setText("");
                    TextMonto.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextMonto.getText().isEmpty()) {
                    TextMonto.setForeground(Color.GRAY);
                    TextMonto.setText("123");
                }
            }
        });
    }
    
    
    
    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblSalirMouseClicked
   
    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        try {
            VentanaRecepcion vr = new VentanaRecepcion(this.usuarioId);
            vr.setVisible(true);
            this.dispose();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ventaPagos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblInicioMouseClicked

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPagarActionPerformed

    private void TextNombreTitularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNombreTitularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextNombreTitularActionPerformed

    private void TextFechaVencimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFechaVencimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFechaVencimientoActionPerformed

    private void btnPagarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagarMouseClicked
        procesarPago();
    }//GEN-LAST:event_btnPagarMouseClicked

    private void TextCVVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextCVVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextCVVActionPerformed

    private void TextMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextMontoActionPerformed
   
    
    // Métodos de validación
private boolean validarNumeroTarjeta(String numTarjeta) {
    return numTarjeta.matches("\\d{13,19}");
}

private boolean validarFechaVencimiento(String fecha) {
    if (!fecha.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
        return false;
    }
    return true; // Validación básica
}

private boolean validarCVV(String cvv) {
    return cvv.matches("\\d{3,4}");
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
    private javax.swing.JTextField TextCVV;
    private javax.swing.JTextField TextFechaVencimiento;
    private javax.swing.JTextField TextMonto;
    private javax.swing.JTextField TextNombreTitular;
    private javax.swing.JTextField TextNumeroTarjeta;
    private javax.swing.JButton btnPagar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblPago;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    // End of variables declaration//GEN-END:variables
}
