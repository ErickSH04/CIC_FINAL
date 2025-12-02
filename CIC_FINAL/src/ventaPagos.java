
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ventaPagos extends javax.swing.JFrame {

    private static String usuarioId;
    private static Statement stmt;
    public static Connection con;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem menuVerDetalles;

    public ventaPagos(String usuarioId) {//String usuarioId
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        con = ConexionSQL.ConexionSQLServer();
        m = (DefaultTableModel) tblCita.getModel();
           
        configurarPopupMenu();
        cargarTodosLosPagos();
        actualizarFechaActual();
    }
    
    
    private void configurarPopupMenu() {
    popupMenu = new javax.swing.JPopupMenu();
    menuVerDetalles = new javax.swing.JMenuItem("Ver detalles completos del pago");
    //menuVerDetalles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/detalles.png"))); // Opcional
    
    popupMenu.add(menuVerDetalles);
    
    // Agregar ActionListener al menú
    menuVerDetalles.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            menuVerDetallesActionPerformed(evt);
        }
    });
    
    // Configurar la tabla para usar el popup
    tblCita.setComponentPopupMenu(popupMenu);
}
    
    
    private void menuVerDetallesActionPerformed(java.awt.event.ActionEvent evt) {                                                
    mostrarDetallesPago();
    }
    
    
    private void mostrarDetallesPago() {
    int filaSeleccionada = tblCita.getSelectedRow();
    
    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, 
            "Por favor, seleccione un pago de la tabla", 
            "Seleccionar Pago", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        String idPago = m.getValueAt(filaSeleccionada, 0).toString();
        String numeroSeguro = m.getValueAt(filaSeleccionada, 6).toString();
        
        // Obtener información detallada del pago
        obtenerDetallesCompletosPago(idPago, numeroSeguro);
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Error al obtener detalles: " + ex.getMessage(),
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
    private void obtenerDetallesCompletosPago(String idPago, String numeroSeguro) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn != null) {
            String query = 
                "SELECT " +
                // Información del pago
                "p.idPago, p.monto, p.fechaPago, p.tipoPago, p.numTarjeta, " +
                // Información del paciente
                "pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro, " +
                // Información de la última cita (si existe)
                "c.idCita, c.fecha AS fechaCita, c.hora AS horaCita, " +
                // Información del médico de la cita
                "m.nombreMed, m.apellido1 AS apellido1Med, m.especialidad " +
                "FROM Pago p " +
                "INNER JOIN Paciente pac ON p.numeroSeguro = pac.numeroSeguro " +
                "LEFT JOIN Cita c ON p.numeroSeguro = c.numeroSeguro " +
                "LEFT JOIN Medico m ON c.idMedico = m.idMedico " +
                "WHERE p.idPago = ? AND p.numeroSeguro = ? " +
                "ORDER BY c.fecha DESC, c.hora DESC " + // Última cita primero
                "OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY"; // Solo la más reciente
            
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, idPago);
            pstmt.setString(2, numeroSeguro);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Extraer todos los datos
                String monto = "$" + String.format("%.2f", rs.getDouble("monto"));
                String fechaPago = rs.getString("fechaPago");
                String tipoPago = rs.getInt("tipoPago") == 1 ? "Tarjeta de Crédito" : "Efectivo";
                String numTarjeta = rs.getString("numTarjeta") != null ? 
                                   "**** **** **** " + rs.getString("numTarjeta") : "No registrada";
                
                // Información del paciente
                String nombrePaciente = rs.getString("nombrePac") + " " + 
                                       rs.getString("apellido1") + " " + 
                                       rs.getString("apellido2");
                String nss = rs.getString("numeroSeguro");
                
                // Información de la cita (si existe)
                String idCita = rs.getString("idCita");
                String fechaCita = rs.getString("fechaCita");
                String horaCita = rs.getString("horaCita");
                // ELIMINADO: String servicio = rs.getString("servicio");
                
                // Información del médico (si existe)
                String nombreMedico = rs.getString("nombreMed");
                String apellidoMedico = rs.getString("apellido1Med");
                String especialidad = rs.getString("especialidad");
                
                String nombreCompletoMedico = (nombreMedico != null && apellidoMedico != null) ?
                                             nombreMedico + " " + apellidoMedico : "No asignado";
                
                // Crear mensaje simple para el diálogo (sin servicio)
                String mensaje = crearMensajeDetallesSimple(
                    idPago, monto, fechaPago, tipoPago, numTarjeta,
                    nombrePaciente, nss,
                    idCita, fechaCita, horaCita, /* servicio, */ // Eliminado
                    nombreCompletoMedico, especialidad
                );
                
                // Mostrar en un JOptionPane simple
                JOptionPane.showMessageDialog(this, 
                    mensaje, 
                    "DETALLES COMPLETOS DEL PAGO - ID: " + idPago, 
                    JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                // Si no hay cita relacionada, mostrar solo información del pago
                //mostrarInformacionBasicaPago(idPago, numeroSeguro, conn);
            }
        }
    } catch (SQLException ex) {
        System.err.println("Error al obtener detalles del pago: " + ex.getMessage());
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Error al obtener detalles: " + ex.getMessage(),
            "Error de Base de Datos", 
            JOptionPane.ERROR_MESSAGE);
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
    
    
    private String crearMensajeDetallesSimple(
    String idPago, String monto, String fechaPago, String tipoPago, String numTarjeta,
    String nombrePaciente, String nss,
    String idCita, String fechaCita, String horaCita, String servicio,
    String nombreMedico, String especialidad) {
    
    StringBuilder mensaje = new StringBuilder();
    mensaje.append("========================================\n");
    mensaje.append("       DETALLES COMPLETOS DEL PAGO       \n");
    mensaje.append("========================================\n\n");
    
    // SECCIÓN 1: INFORMACIÓN DEL PAGO
    mensaje.append("INFORMACIÓN DEL PAGO\n");
    mensaje.append("────────────────────────────────────────\n");
    mensaje.append("ID de Pago:        ").append(idPago).append("\n");
    mensaje.append("Monto:             ").append(monto).append("\n");
    mensaje.append("Fecha de Pago:     ").append(fechaPago).append("\n");
    mensaje.append("Método de Pago:    ").append(tipoPago).append("\n");
    mensaje.append("Tarjeta:           ").append(numTarjeta).append("\n\n");
    
    // SECCIÓN 2: INFORMACIÓN DEL PACIENTE
    mensaje.append("INFORMACIÓN DEL PACIENTE\n");
    mensaje.append("────────────────────────────────────────\n");
    mensaje.append("Nombre:            ").append(nombrePaciente).append("\n");
    mensaje.append("Número de Seguro:  ").append(nss).append("\n\n");
    
    // SECCIÓN 3: INFORMACIÓN DE LA CITA (si existe)
    if (idCita != null) {
        mensaje.append("INFORMACIÓN DE LA CITA\n");
        mensaje.append("────────────────────────────────────────\n");
        mensaje.append("ID Cita:          ").append(idCita).append("\n");
        mensaje.append("Fecha:            ").append(fechaCita).append("\n");
        mensaje.append("Hora:             ").append(horaCita).append("\n");
        mensaje.append("Servicio:         ").append(servicio).append("\n\n");
        
        // SECCIÓN 4: INFORMACIÓN DEL MÉDICO
        mensaje.append("INFORMACIÓN DEL MÉDICO\n");
        mensaje.append("────────────────────────────────────────\n");
        mensaje.append("Médico:           ").append(nombreMedico).append("\n");
        mensaje.append("Especialidad:     ").append(especialidad != null ? especialidad : "No especificada").append("\n");
    } else {
        mensaje.append("ℹ️  NOTA:\n");
        mensaje.append("────────────────────────────────────────\n");
        mensaje.append("No se encontró cita relacionada con este pago.\n");
    }
    
    mensaje.append("\n========================================\n");
    
    return mensaje.toString();
}
    
    
    private String crearMensajeDetallesSimple(
    String idPago, String monto, String fechaPago, String tipoPago, String numTarjeta,
    String nombrePaciente, String nss,
    String idCita, String fechaCita, String horaCita, // Eliminado: String servicio,
    String nombreMedico, String especialidad) {
    
    StringBuilder mensaje = new StringBuilder();
    mensaje.append("========================================\n");
    mensaje.append("       DETALLES COMPLETOS DEL PAGO       \n");
    mensaje.append("========================================\n\n");
    
    // SECCIÓN 1: INFORMACIÓN DEL PAGO
    mensaje.append("INFORMACIÓN DEL PAGO\n");
    mensaje.append("────────────────────────────────────────\n");
    mensaje.append("ID de Pago:        ").append(idPago).append("\n");
    mensaje.append("Monto:             ").append(monto).append("\n");
    mensaje.append("Fecha de Pago:     ").append(fechaPago).append("\n");
    mensaje.append("Método de Pago:    ").append(tipoPago).append("\n");
    mensaje.append("Tarjeta:           ").append(numTarjeta).append("\n\n");
    
    // SECCIÓN 2: INFORMACIÓN DEL PACIENTE
    mensaje.append("INFORMACIÓN DEL PACIENTE\n");
    mensaje.append("────────────────────────────────────────\n");
    mensaje.append("Nombre:            ").append(nombrePaciente).append("\n");
    mensaje.append("Número de Seguro:  ").append(nss).append("\n\n");
    
    // SECCIÓN 3: INFORMACIÓN DE LA CITA (si existe)
    if (idCita != null) {
        mensaje.append("INFORMACIÓN DE LA CITA\n");
        mensaje.append("────────────────────────────────────────\n");
        mensaje.append("ID Cita:          ").append(idCita).append("\n");
        mensaje.append("Fecha:            ").append(fechaCita).append("\n");
        mensaje.append("Hora:             ").append(horaCita).append("\n");
        // ELIMINADO: mensaje.append("Servicio:         ").append(servicio).append("\n\n");
        mensaje.append("\n");
        
        // SECCIÓN 4: INFORMACIÓN DEL MÉDICO
        mensaje.append("INFORMACIÓN DEL MÉDICO\n");
        mensaje.append("────────────────────────────────────────\n");
        mensaje.append("Médico:           ").append(nombreMedico).append("\n");
        mensaje.append("Especialidad:     ").append(especialidad != null ? especialidad : "No especificada").append("\n");
    } else {
        mensaje.append("ℹ️  NOTA:\n");
        mensaje.append("────────────────────────────────────────\n");
        mensaje.append("No se encontró cita relacionada con este pago.\n");
    }
    
    mensaje.append("\n========================================\n");
    
    return mensaje.toString();
}
   
    
    
    
    private void cargarTodosLosPagos() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn != null) {
            stmt = conn.createStatement();
            
            // Consulta para obtener pagos con información del paciente
            String query = "SELECT p.idPago, p.fechaPago, p.monto, " +
                          "pac.nombrePac, pac.apellido1, pac.apellido2, " +
                          "p.numeroSeguro " +
                          "FROM Pago p " +
                          "INNER JOIN Paciente pac ON p.numeroSeguro = pac.numeroSeguro " +
                          "ORDER BY p.fechaPago DESC, p.idPago DESC";
            
            System.out.println("Ejecutando consulta: " + query);
            rs = stmt.executeQuery(query);
            
            m.setRowCount(0); // Limpiar tabla
            Object R[] = new Object[7];
            
            double totalMonto = 0.0;
            int totalPagos = 0;
            
            while (rs.next()) {
                R[0] = rs.getObject("idPago");
                R[1] = rs.getObject("fechaPago");
                R[2] = "$" + String.format("%.2f", rs.getDouble("monto"));
                R[3] = rs.getObject("nombrePac");
                R[4] = rs.getObject("apellido1");
                R[5] = rs.getObject("apellido2");
                R[6] = rs.getObject("numeroSeguro");
                m.addRow(R);
                
                totalMonto += rs.getDouble("monto");
                totalPagos++;
            }
            
            // Actualizar título con estadísticas
            jLabel2.setText("Registro de Pagos: (" + totalPagos + " pagos - Total: $" + 
                           String.format("%.2f", totalMonto) + ")");
            
            System.out.println("Cargados " + totalPagos + " pagos. Total: $" + totalMonto);
        }
    } catch (SQLException ex) {
        System.err.println("Error al cargar pagos: " + ex.getMessage());
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Error al cargar los pagos: " + ex.getMessage(),
            "Error de Base de Datos",
            JOptionPane.ERROR_MESSAGE);
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (stmt != null) stmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
    
    
    private void actualizarFechaActual() {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String fechaActual = sdf.format(new java.util.Date());
    lblFecha.setText(fechaActual);
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
        tblCita = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lblPago = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();

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
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUsuario)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSalir)
                            .addComponent(lblInicio))
                        .addGap(8, 8, 8)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblUsuario)
                .addGap(99, 99, 99)
                .addComponent(lblInicio)
                .addGap(39, 39, 39)
                .addComponent(lblSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                "ID Pago", "Fecha Pago", "Monto", "Nombre", "Apellido 1", "Apellido 2", "Numero Seguro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, true
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

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Total: $");

        lblPago.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dar-dinero.png"))); // NOI18N
        lblPago.setText("Pago Registrados");

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");

        lblBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar (1).png"))); // NOI18N
        lblBuscar.setText("Buscar paciente");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(285, 285, 285)
                                .addComponent(lblPago)))
                        .addContainerGap(13, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(164, 164, 164))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(363, 363, 363)
                .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFecha)
                .addGap(53, 53, 53))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblPago)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFecha)
                        .addGap(90, 90, 90)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
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

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        
    }//GEN-LAST:event_txtBuscarKeyReleased
   
    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        try {
            VentanaRecepcion vr = new VentanaRecepcion(this.usuarioId);
            vr.setVisible(true);
            this.dispose();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ventaPagos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblInicioMouseClicked
    
    private void tblCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitaMouseClicked
      // Si es clic derecho, mostrar el menú
    if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
        int fila = tblCita.rowAtPoint(evt.getPoint());
        if (fila >= 0 && fila < tblCita.getRowCount()) {
            tblCita.setRowSelectionInterval(fila, fila);
            popupMenu.show(tblCita, evt.getX(), evt.getY());
        }
    }
    // Si es doble clic izquierdo, mostrar detalles
    else if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
        mostrarDetallesPago();
    }
    }//GEN-LAST:event_tblCitaMouseClicked
    
    
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblPago;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblCita;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
