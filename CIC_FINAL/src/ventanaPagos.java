import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class ventanaPagos extends javax.swing.JFrame {

    //private static Statement stmt;
    //public static Connection con;
    private String usuarioId;
    public static String medicoId;
    private String correo;
    private String query;
    private String nss;
    //private JTextField txtNSS;
    private String nombrePacienteConfirmado; // Variable para almacenar el nombre confirmado

    public ventanaPagos(String usuarioId) {
        initComponents();
        this.usuarioId = usuarioId;   // NSS del paciente
        this.nombrePacienteConfirmado = null;
        
        txtMetodoPago.setEditable(false);
        txtPaciente.setEditable(false);
        
        // Agregar placeholders y configurar validación
        agregarPlaceholders();
        configurarValidacionPaciente();
        
        // Cargar datos del paciente y médico en los campos
        cargarDatosPaciente();
    }
    
     public ventanaPagos(String usuarioId, String correo, String nss) {
        initComponents();
        this.usuarioId = usuarioId;
        this.correo = correo;
        this.nss = nss;
        this.nombrePacienteConfirmado = null;
        
        txtMetodoPago.setEditable(false);
        txtPaciente.setEditable(false);
        
        agregarPlaceholders();
        configurarValidacionPaciente();
        cargarDatosPaciente();
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

        // Nombre del Titular
        txtNombreTitular.setText("Ingrese nombre completo");
        txtNombreTitular.setForeground(Color.GRAY);
        txtNombreTitular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtNombreTitular.getText().equals("Ingrese nombre completo")) {
                    txtNombreTitular.setText("");
                    txtNombreTitular.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtNombreTitular.getText().isEmpty()) {
                    txtNombreTitular.setForeground(Color.GRAY);
                    txtNombreTitular.setText("Ingrese nombre completo");
                }
            }
        });

        // CVV
        txtCVV.setText("123");
        txtCVV.setForeground(Color.GRAY);
        txtCVV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtCVV.getText().equals("123")) {
                    txtCVV.setText("");
                    txtCVV.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtCVV.getText().isEmpty()) {
                    txtCVV.setForeground(Color.GRAY);
                    txtCVV.setText("123");
                }
            }
        });
        // MONTO
    TextMonto.setText("0.00");
    TextMonto.setForeground(Color.GRAY);
    TextMonto.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (TextMonto.getText().equals("0.00")) {
                TextMonto.setText("");
                TextMonto.setForeground(Color.BLACK);
            }
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            if (TextMonto.getText().isEmpty()) {
                TextMonto.setForeground(Color.GRAY);
                TextMonto.setText("0.00");
            }
        }
    });  
    }
    
    // Configurar el KeyListener para el campo de nombre del titular
    private void configurarValidacionPaciente() {
        txtNombreTitular.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    validarPaciente();
                }
            }
        });
    }
    
    // Método para validar paciente
    private void validarPaciente() {
        String nombrePaciente = txtNombreTitular.getText().trim();
        
        if (nombrePaciente.isEmpty() || nombrePaciente.equals("Ingrese nombre completo")) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre del paciente");
            txtNombreTitular.requestFocus();
            return;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionSQL.ConexionSQLServer();
            if (conn != null) {
                // Consulta para buscar paciente
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
                        this.nombrePacienteConfirmado = nombreCompleto;
                        
                        // Actualizar el campo con el nombre completo
                        txtNombreTitular.setText(nombreCompleto);
                        txtNombreTitular.setEditable(false);
                        txtNombreTitular.setForeground(new Color(0, 100, 0)); // Verde oscuro para indicar confirmado
                        
                        // Si el paciente ya está cargado, no sobrescribir
                        if (txtPaciente.getText().isEmpty()) {
                            txtPaciente.setText(nombreCompleto);
                        }
                        
                        JOptionPane.showMessageDialog(this, 
                            "Paciente confirmado:\n" +
                            "Nombre: " + nombreCompleto + "\n" +
                            "NSS: " + nss);
                    } else {
                        this.nombrePacienteConfirmado = null;
                        txtNombreTitular.setEditable(true);
                        txtNombreTitular.setForeground(Color.BLACK);
                        txtNombreTitular.setText("");
                        txtNombreTitular.requestFocus();
                    }
                } else {
                    // PACIENTE NO ENCONTRADO
                    int opcion = JOptionPane.showConfirmDialog(this,
                        "No se encontró el paciente: " + nombrePaciente + "\n\n" +
                        "¿Desea intentar con otro nombre?",
                        "Paciente No Encontrado",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (opcion == JOptionPane.YES_OPTION) {
                        txtNombreTitular.setText("");
                        txtNombreTitular.requestFocus();
                    } else {
                        txtNombreTitular.setText("Ingrese nombre completo");
                        txtNombreTitular.setForeground(Color.GRAY);
                    }
                    this.nombrePacienteConfirmado = null;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al buscar paciente: " + ex.getMessage());
            this.nombrePacienteConfirmado = null;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
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
    
    private void cargarDatosPaciente() {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = ConexionSQL.ConexionSQLServer();

        // VERIFICAR primero si el usuarioId es válido
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "NSS no proporcionado. El campo usuarioId está vacío.");
            txtPaciente.setText("NO ESPECIFICADO");
            return;
        }

        // DEBUG: Mostrar qué NSS se está buscando
        System.out.println("Buscando paciente con NSS: '" + usuarioId + "'");

        String sql = "SELECT nombrePac, apellido1, apellido2, metodoPago, numeroSeguro "
                   + "FROM Paciente WHERE numeroSeguro = ?";
        
        ps = conn.prepareStatement(sql);
        ps.setString(1, usuarioId.trim()); // Asegurar limpieza de espacios

        rs = ps.executeQuery();

        if (rs.next()) {
            // Obtener el NSS real de la BD para comparar
            String nssBD = rs.getString("numeroSeguro");
            System.out.println("NSS encontrado en BD: '" + nssBD + "'");
            
            String nombreCompleto = rs.getString("nombrePac") + " "
                                  + rs.getString("apellido1") + " "
                                  + rs.getString("apellido2");

            txtPaciente.setText(nombreCompleto);
            txtMetodoPago.setText(rs.getString("metodoPago"));
            
            // DEBUG
            System.out.println("Paciente cargado: " + nombreCompleto);

        } else {
            // Paciente no encontrado - buscar alternativas
            buscarPacienteAlternativo();
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, 
            "Error al cargar datos del paciente: " + ex.getMessage());
        ex.printStackTrace(); // Para debugging
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }
}
   
    // Método auxiliar para buscar paciente alternativamente
private void buscarPacienteAlternativo() {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        
        // Intento 1: Buscar por email si usuarioId parece ser un email
        if (usuarioId.contains("@")) {
            String sql = "SELECT nombrePac, apellido1, apellido2, metodoPago, numeroSeguro "
                       + "FROM Paciente WHERE Correo = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuarioId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String nombreCompleto = rs.getString("nombrePac") + " "
                                      + rs.getString("apellido1") + " "
                                      + rs.getString("apellido2");
                
                txtPaciente.setText(nombreCompleto);
                txtMetodoPago.setText(rs.getString("metodoPago"));
                
                // Actualizar usuarioId al NSS real
                String nssReal = rs.getString("numeroSeguro");
                this.usuarioId = nssReal;
                
                JOptionPane.showMessageDialog(this,
                    "Paciente encontrado por email.\n" +
                    "NSS actualizado a: " + nssReal);
                return;
            }
        }
        
        // Intento 2: Buscar pacientes que contengan el texto en cualquier campo
        String sql = "SELECT TOP 1 nombrePac, apellido1, apellido2, metodoPago, numeroSeguro "
                   + "FROM Paciente "
                   + "WHERE numeroSeguro LIKE ? "
                   + "OR nombrePac LIKE ? "
                   + "OR apellido1 LIKE ? "
                   + "OR Correo LIKE ?";
        
        ps = conn.prepareStatement(sql);
        String busqueda = "%" + usuarioId + "%";
        ps.setString(1, busqueda);
        ps.setString(2, busqueda);
        ps.setString(3, busqueda);
        ps.setString(4, busqueda);
        
        rs = ps.executeQuery();
        
        if (rs.next()) {
            String nombreCompleto = rs.getString("nombrePac") + " "
                                  + rs.getString("apellido1") + " "
                                  + rs.getString("apellido2");
            String nssReal = rs.getString("numeroSeguro");
            
            int opcion = JOptionPane.showConfirmDialog(this,
                "Se encontró un posible paciente:\n" +
                "Nombre: " + nombreCompleto + "\n" +
                "NSS: " + nssReal + "\n\n" +
                "¿Es este el paciente correcto?",
                "Confirmar Paciente",
                JOptionPane.YES_NO_OPTION);
            
            if (opcion == JOptionPane.YES_OPTION) {
                txtPaciente.setText(nombreCompleto);
                txtMetodoPago.setText(rs.getString("metodoPago"));
                this.usuarioId = nssReal;
            } else {
                txtPaciente.setText("Paciente no confirmado");
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "No se encontró ningún paciente con NSS/email: " + usuarioId + "\n" +
                "Por favor, verifique el NSS e intente nuevamente.",
                "Paciente No Encontrado",
                JOptionPane.WARNING_MESSAGE);
            txtPaciente.setText("NO ENCONTRADO: " + usuarioId);
        }
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
            "Error en búsqueda alternativa: " + ex.getMessage());
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }
}
    
    // Métodos de validación mejorados
    private boolean validarNumeroTarjeta(String numTarjeta) {
        return numTarjeta.matches("\\d{13,19}");
    }
    
    private boolean validarFechaVencimiento() {
        if (fechaCita.getDate() == null) {
            return false;
        }
        
        // Validar que no sea fecha pasada
        Calendar fechaVencimiento = Calendar.getInstance();
        fechaVencimiento.setTime(fechaCita.getDate());
        Calendar hoy = Calendar.getInstance();
        
        return !fechaVencimiento.before(hoy);
    }
    
    private boolean validarCVV(String cvv) {
        return cvv.matches("\\d{3,4}");
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

    // 3. Validar CVV
    String cvv = txtCVV.getText().replaceAll("\\D+", "");
    if (cvv.length() < 3 || cvv.length() > 4) {
        JOptionPane.showMessageDialog(this,
                "CVV inválido.\nDebe ser de 3 o 4 dígitos.",
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
        txtCVV.requestFocus();
        txtCVV.selectAll();
        return false;
    }

    // 4. Validar nombre del titular
    if (txtNombreTitular.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Debe ingresar el nombre del titular de la tarjeta.",
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
        txtNombreTitular.requestFocus();
        return false;
    }

    // 5. Validar fecha de vencimiento
    if (fechaCita.getDate() == null) {
        JOptionPane.showMessageDialog(this,
                "Debe seleccionar una fecha de vencimiento.",
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
        fechaCita.requestFocus();
        return false;
    }

    Date hoy = new Date();
    if (fechaCita.getDate().before(hoy)) {
        JOptionPane.showMessageDialog(this,
                "La fecha de vencimiento no puede ser anterior a hoy.",
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
        fechaCita.requestFocus();
        return false;
    }

    // 6. Validar tipo de tarjeta
    if (cmbTtipo.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this,
                "Debe seleccionar un tipo de tarjeta.",
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
        cmbTtipo.requestFocus();
        return false;
    }
 // 7. Validar monto - VERSIÓN MEJORADA
    String montoTexto = TextMonto.getText().trim();
    
    // Verificar si es placeholder (gris) o campo vacío
    if (TextMonto.getForeground().equals(Color.GRAY) || 
        montoTexto.isEmpty() || 
        montoTexto.equals("0.00") ||
        montoTexto.equals("50.00")) {
        
        JOptionPane.showMessageDialog(this,
                "Debe ingresar un monto válido.",
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
        TextMonto.requestFocus();
        TextMonto.setForeground(Color.BLACK);
        TextMonto.selectAll();
        return false;
    }
    
    try {
        // Limpiar símbolos de moneda
        String montoLimpio = montoTexto.replace("$", "").replace(",", "").trim();
        
        if (montoLimpio.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar un monto.",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            TextMonto.requestFocus();
            TextMonto.selectAll();
            return false;
        }
        
        BigDecimal monto = new BigDecimal(montoLimpio);
        
        // Validar que sea positivo y mayor a 0
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(this,
                    "El monto debe ser mayor a cero.",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            TextMonto.requestFocus();
            TextMonto.selectAll();
            return false;
        }
        
        // Validar límite máximo (ej: $1,000,000)
        if (monto.compareTo(new BigDecimal("10000")) > 0) {
            JOptionPane.showMessageDialog(this,
                    "El monto no puede exceder $10,000.",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            TextMonto.requestFocus();
            TextMonto.selectAll();
            return false;
        }
        
        // Validar que tenga máximo 2 decimales
        if (monto.scale() > 2) {
            JOptionPane.showMessageDialog(this,
                    "El monto no puede tener más de 2 decimales.",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            TextMonto.requestFocus();
            TextMonto.selectAll();
            return false;
        }
        
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
                "Monto inválido. Ingrese un valor numérico válido.\nEjemplo: 50.00, 100.50",
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
        TextMonto.requestFocus();
        TextMonto.selectAll();
        return false;
    }

    return true;
}

    
    private void limpiarCampos() {
        txtNumTarjeta.setText("Ingrese número de tarjeta");
        txtNumTarjeta.setForeground(Color.GRAY);
        
        txtNombreTitular.setText("Ingrese nombre completo");
        txtNombreTitular.setForeground(Color.GRAY);
        txtNombreTitular.setEditable(true);
        
        txtCVV.setText("123");
        txtCVV.setForeground(Color.GRAY);
        
        fechaCita.setDate(null);
        cmbTtipo.setSelectedIndex(0);
        
        // Mantener datos del paciente cargado
        // txtPaciente y txtMetodoPago no se limpian
    }
    

    /*private void registrarPago() {
    // Validación previa
    if (!validarDatosPago()) {
        return;
    }

    Connection conn = null;
    PreparedStatement ps = null;

    try {
        conn = ConexionSQL.ConexionSQLServer();

        // ID autogenerado
        int idPago = obtenerProximoIdPago();

        // Normalizar el número de tarjeta (solo dígitos)
        String numTarjeta = txtNumTarjeta.getText() == null
                ? ""
                : txtNumTarjeta.getText().replaceAll("\\D+", "");

        // Validar longitud real
        if (numTarjeta.length() < 13 || numTarjeta.length() > 19) {
            JOptionPane.showMessageDialog(this,
                    "Número de tarjeta inválido. Debe tener entre 13 y 19 dígitos.");
            txtNumTarjeta.requestFocus();
            return;
        }

        // Fecha de vencimiento
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaVenc = sdf.format(fechaCita.getDate());

        // CONVERTIR MONTO A BigDecimal
        BigDecimal monto;
        try {
            // Eliminar símbolos de moneda y espacios
            String montoTexto = TextMonto.getText().trim()
                    .replace("$", "")
                    .replace(",", "")
                    .trim();
            monto = new BigDecimal(montoTexto);
            
            // Validar que sea positivo
            if (monto.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El monto debe ser mayor a cero.",
                        "Error de Validación", JOptionPane.WARNING_MESSAGE);
                TextMonto.requestFocus();
                TextMonto.selectAll();
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Monto inválido. Por favor ingrese un valor numérico válido.",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            TextMonto.requestFocus();
            TextMonto.selectAll();
            return;
        }

        // Query EXACTA para tu tabla Pago
        String sql = "INSERT INTO Pago "
                + "(monto, fechaPago, tipoPago, numTarjeta, NomTitular, cvv, FechVencimiento, TipoTarjeta) "
                + "VALUES (?, GETDATE(), ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(sql);

        // Asignación ORDENADA y CORRECTA - USANDO setBigDecimal para monto
        ps.setBigDecimal(1, monto);                           // monto (money) - ¡IMPORTANTE!
        ps.setString(2, txtMetodoPago.getText());             // tipoPago
        ps.setString(3, numTarjeta);                          // numTarjeta
        ps.setString(4, txtNombreTitular.getText());          // NomTitular
        ps.setString(5, txtCVV.getText());                    // cvv
        ps.setString(6, fechaVenc);                           // FechVencimiento
        ps.setString(7, cmbTtipo.getSelectedItem().toString()); // TipoTarjeta

        int r = ps.executeUpdate();

        if (r > 0) {
            // Formatear el monto para mostrar
            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance();
            String montoFormateado = formatoMoneda.format(monto);

            JOptionPane.showMessageDialog(this,
                    "Pago registrado correctamente.\n\n"
                    + "ID Pago: " + idPago + "\n"
                    + "Paciente: " + txtPaciente.getText() + "\n"
                    + "Monto: " + montoFormateado
            );

            // Ventana siguiente
            loginHospitalNuevo v = new loginHospitalNuevo();
            v.setVisible(true);
            this.dispose();
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error al registrar pago: " + ex.getMessage());
        ex.printStackTrace(); // Para debugging

    } finally {
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }
}*/
    
  private void registrarPago() {
    // Validación previa
    if (!validarDatosPago()) {
        return;
    }

    Connection conn = null;
    PreparedStatement ps = null;

    try {
        conn = ConexionSQL.ConexionSQLServer();

        // ID autogenerado
        int idPago = obtenerProximoIdPago();

        // Normalizar el número de tarjeta (solo dígitos)
        String numTarjeta = txtNumTarjeta.getText() == null
                ? ""
                : txtNumTarjeta.getText().replaceAll("\\D+", "");

        // Validar longitud real
        if (numTarjeta.length() < 13 || numTarjeta.length() > 19) {
            JOptionPane.showMessageDialog(this,
                    "Número de tarjeta inválido. Debe tener entre 13 y 19 dígitos.");
            txtNumTarjeta.requestFocus();
            return;
        }

        // Fecha de vencimiento
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaVenc = sdf.format(fechaCita.getDate());

        // CONVERTIR MONTO A BigDecimal
        BigDecimal monto;
        try {
            // Eliminar símbolos de moneda y espacios
            String montoTexto = TextMonto.getText().trim()
                    .replace("$", "")
                    .replace(",", "")
                    .trim();
            monto = new BigDecimal(montoTexto);
            
            // Validar que sea positivo
            if (monto.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El monto debe ser mayor a cero.",
                        "Error de Validación", JOptionPane.WARNING_MESSAGE);
                TextMonto.requestFocus();
                TextMonto.selectAll();
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Monto inválido. Por favor ingrese un valor numérico válido.",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            TextMonto.requestFocus();
            TextMonto.selectAll();
            return;
        }

        // Query EXACTA para tu tabla Pago
        String sql = "INSERT INTO Pago "
                + "(monto, fechaPago, tipoPago, numTarjeta, NomTitular, cvv, FechVencimiento, TipoTarjeta) "
                + "VALUES (?, GETDATE(), ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(sql);

        // Asignación ORDENADA y CORRECTA - USANDO setBigDecimal para monto
        ps.setBigDecimal(1, monto);                           // monto (money) - ¡IMPORTANTE!
        ps.setString(2, txtMetodoPago.getText());             // tipoPago
        ps.setString(3, numTarjeta);                          // numTarjeta
        ps.setString(4, txtNombreTitular.getText());          // NomTitular
        ps.setString(5, txtCVV.getText());                    // cvv
        ps.setString(6, fechaVenc);                           // FechVencimiento
        ps.setString(7, cmbTtipo.getSelectedItem().toString()); // TipoTarjeta

        int r = ps.executeUpdate();

        if (r > 0) {
            // Formatear el monto para mostrar (solo para uso interno si lo necesitas)
            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance();
            String montoFormateado = formatoMoneda.format(monto);

            // NOTA: NO se muestra mensaje aquí, porque ya se mostró en el comprobante
            // Solo se registra en BD y se navega a la siguiente ventana
            
            // Ventana siguiente
            loginHospitalNuevo v = new loginHospitalNuevo();
            v.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar el pago en la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error al registrar pago: " + ex.getMessage(),
                "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace(); // Para debugging

    } finally {
        try { 
            if (ps != null) ps.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        try { 
            if (conn != null) conn.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}  

private void mostrarComprobantePago() {
    // Primero validar los datos
    if (!validarDatosPago()) {
        return;
    }
    
    // Obtener todos los datos para el comprobante (ANTES de registrar)
    int idPago = obtenerProximoIdPago();
    String paciente = txtPaciente.getText().trim();
    String metodoPago = txtMetodoPago.getText().trim();
    
    // Formatear el monto
    BigDecimal monto;
    try {
        String montoTexto = TextMonto.getText().trim()
                .replace("$", "")
                .replace(",", "")
                .trim();
        monto = new BigDecimal(montoTexto);
    } catch (NumberFormatException ex) {
        monto = BigDecimal.ZERO;
    }
    
    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance();
    String montoFormateado = formatoMoneda.format(monto);
    
    // Datos de la tarjeta (mostrar solo últimos 4 dígitos por seguridad)
    String numTarjeta = txtNumTarjeta.getText().replaceAll("\\D+", "");
    String ultimos4Digitos = numTarjeta.length() > 4 ? 
            "**** **** **** " + numTarjeta.substring(numTarjeta.length() - 4) : 
            numTarjeta;
    
    String nombreTitular = txtNombreTitular.getText().trim();
    String tipoTarjeta = cmbTtipo.getSelectedItem().toString();
    
    // Fecha de vencimiento
    String fechaVencimiento = "No especificada";
    if (fechaCita.getDate() != null) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        fechaVencimiento = sdf.format(fechaCita.getDate());
    }
    
    // Fecha y hora actual
    SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
    String fechaActual = sdfFecha.format(new Date());
    String horaActual = sdfHora.format(new Date());
    
    // Construir el mensaje del comprobante
    StringBuilder comprobante = new StringBuilder();
    comprobante.append("========================================\n");
    comprobante.append("       COMPROBANTE DE PAGO\n");
    comprobante.append("========================================\n\n");
    
    comprobante.append("INFORMACIÓN DEL PAGO:\n");
    comprobante.append("ID de Pago:      ").append(idPago).append("\n");
    comprobante.append("Fecha:           ").append(fechaActual).append("\n");
    comprobante.append("Hora:            ").append(horaActual).append("\n");
    comprobante.append("Monto Pagado:    ").append(montoFormateado).append("\n");
    comprobante.append("Método de Pago:  ").append(metodoPago).append("\n\n");
    
    comprobante.append("INFORMACIÓN DEL PACIENTE:\n");
    comprobante.append("Paciente:        ").append(paciente).append("\n\n");
    
    comprobante.append("INFORMACIÓN DE LA TARJETA:\n");
    comprobante.append("Tipo Tarjeta:    ").append(tipoTarjeta).append("\n");
    comprobante.append("Número:          ").append(ultimos4Digitos).append("\n");
    comprobante.append("Nombre Titular:  ").append(nombreTitular).append("\n");
    comprobante.append("Vencimiento:     ").append(fechaVencimiento).append("\n\n");
    
    comprobante.append("========================================\n");
    comprobante.append("     CONFIRMAR REGISTRO DE PAGO\n");
    comprobante.append("========================================\n");
    
    // Mostrar el comprobante en un JOptionPane simple
    int respuesta = JOptionPane.showConfirmDialog(
            this,
            comprobante.toString(),
            "Comprobante de Pago - ID: " + idPago,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE
    );
    
    if (respuesta == JOptionPane.YES_OPTION) {
        // Si el usuario confirma, registrar el pago
        registrarPago();
    } else {
        // Si cancela, mostrar mensaje
        JOptionPane.showMessageDialog(this,
            "Pago cancelado. Los datos no se han registrado.",
            "Pago Cancelado",
            JOptionPane.INFORMATION_MESSAGE);
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
        txtMetodoPago = new javax.swing.JTextField();
        txtCVV = new javax.swing.JTextField();
        btnAtras = new javax.swing.JButton();
        fechaCita = new com.toedter.calendar.JDateChooser();
        txtPaciente = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNumTarjeta = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNombreTitular = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbTtipo = new javax.swing.JComboBox<>();
        lblPago = new javax.swing.JLabel();
        TextMonto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(136, 212, 234));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Roboto", 2, 18)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favicon.png"))); // NOI18N
        jLabel6.setText("Metodo de pago ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paciente.png"))); // NOI18N
        jLabel5.setText("Paciente:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Tarjeta.png"))); // NOI18N
        jLabel7.setText("Número Tarjeta:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Vencimiento.png"))); // NOI18N
        jLabel8.setText("Vencimiento (MM/AA):");

        jLabel9.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CVV.png"))); // NOI18N
        jLabel9.setText("CVV:");

        btnConfirmar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        txtMetodoPago.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtCVV.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N

        btnAtras.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnAtras.setText("Atrás");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        txtPaciente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Metodo.png"))); // NOI18N
        jLabel11.setText("Metodo de pago:");

        txtNumTarjeta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel12.setText("Nombre Titular:");

        txtNombreTitular.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel13.setText("Tipo de Tarjeta");

        cmbTtipo.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        cmbTtipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "VISA", "Mastercard" }));

        lblPago.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lblPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dar-dinero.png"))); // NOI18N
        lblPago.setText("Monto:");

        TextMonto.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(202, 202, 202)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbTtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(fechaCita, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCVV, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38))
                            .addComponent(txtPaciente)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 486, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(txtNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNombreTitular, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(lblPago, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addComponent(txtNombreTitular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCVV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPago)
                            .addComponent(TextMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fechaCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cmbTtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtras)
                    .addComponent(btnConfirmar))
                .addGap(45, 45, 45))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 930, 460));

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

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        //registrarPago();
        mostrarComprobantePago(); 
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        registroPacienteCuestionario res = new registroPacienteCuestionario(correo, nss, null, true);
        res.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

   
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TextMonto;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox<String> cmbTtipo;
    private com.toedter.calendar.JDateChooser fechaCita;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblPago;
    private javax.swing.JTextField txtCVV;
    private javax.swing.JTextField txtMetodoPago;
    private javax.swing.JTextField txtNombreTitular;
    private javax.swing.JTextField txtNumTarjeta;
    private javax.swing.JTextField txtPaciente;
    // End of variables declaration//GEN-END:variables
}
