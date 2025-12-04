
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
import java.math.BigDecimal;


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
  private void obtenerDetallesCompletosPago(String idPago, String ignorado) {
    try (Connection conn = ConexionSQL.ConexionSQLServer()) {
        if (conn == null) return;

        String q =
  "SELECT p.idPago, p.monto, p.fechaPago, p.tipoPago, p.numTarjeta, " +
  "       pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro, " +
  "       c.idCita, c.fecha AS fechaCita, c.hora AS horaCita, " +
  "       m.nombreMed, m.apellido1 AS apellido1Med, m.Especialidad, te.precio AS tarifa " +
  "FROM dbo.Pago p " +
  "JOIN dbo.PagoCita pc ON pc.idPago = p.idPago " +      // <- obligatorio
  "JOIN dbo.Cita c ON c.idCita = pc.idCita " +           // <- obligatorio
  "JOIN dbo.Medico m ON m.idMedico = c.idMedico " +
  "LEFT JOIN dbo.PACIENTE pac ON pac.numeroSeguro = LTRIM(RTRIM(c.numeroSeguro)) " +
  "LEFT JOIN dbo.tarifa_especialidad te ON te.especialidad = m.Especialidad " +
  "WHERE p.idPago = ?";



        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, idPago);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return;

                BigDecimal montoBD = rs.getBigDecimal("monto");
                BigDecimal tarifaBD = rs.getBigDecimal("tarifa");
                String monto = "$" + (montoBD == null ? "0.00" : montoBD.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString());

                String estatus;
                if (tarifaBD == null) estatus = "Pagado";
                else if (montoBD != null && montoBD.compareTo(tarifaBD) >= 0) estatus = "Pagado";
                else {
                    BigDecimal falta = tarifaBD.subtract(montoBD == null ? BigDecimal.ZERO : montoBD);
                    estatus = "Pago incompleto: falta $" + falta.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
                }

                String numTarjeta = rs.getString("numTarjeta");
                if (numTarjeta == null || numTarjeta.isBlank()) numTarjeta = "No registrada";
                else if (numTarjeta.length() >= 4) numTarjeta = "**** **** **** " + numTarjeta.substring(numTarjeta.length() - 4);

                String mensaje = crearMensajeDetallesSimple(
                    idPago, monto, String.valueOf(rs.getObject("fechaPago")), rs.getString("tipoPago"), numTarjeta,
                    s(rs.getString("nombrePac")) + " " + s(rs.getString("apellido1")) + " " + s(rs.getString("apellido2")),
                    s(rs.getString("numeroSeguro")),
                    s(rs.getString("idCita")), s(rs.getString("fechaCita")), s(rs.getString("horaCita")),
                    s(rs.getString("nombreMed")) + " " + s(rs.getString("apellido1Med")),
                    s(rs.getString("Especialidad")),
                    (tarifaBD == null ? "Sin tarifa definida" : "$" + tarifaBD.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString()),
                    estatus
                );

                JOptionPane.showMessageDialog(this, mensaje,
                    "DETALLES COMPLETOS DEL PAGO - ID: " + idPago,
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener detalles: " + ex.getMessage(),
                "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
    }
}



    
    
   private String crearMensajeDetallesSimple(
    String idPago, String monto, String fechaPago, String tipoPago, String numTarjeta,
    String nombrePaciente, String nss,
    String idCita, String fechaCita, String horaCita,
    String nombreMedico, String especialidad,
    String tarifaStr, String estatus) {

    StringBuilder mensaje = new StringBuilder();
    mensaje.append("========================================\n");
    mensaje.append("       DETALLES COMPLETOS DEL PAGO       \n");
    mensaje.append("========================================\n\n");

    // Pago
    mensaje.append("INFORMACIÓN DEL PAGO\n");
    mensaje.append("────────────────────────────────────────\n");
    mensaje.append("ID de Pago:            ").append(idPago).append("\n");
    mensaje.append("Monto:                 ").append(monto).append("\n");
    mensaje.append("Fecha de Pago:         ").append(fechaPago).append("\n");
    mensaje.append("Método de Pago:        ").append(tipoPago).append("\n");
    mensaje.append("Tarjeta:               ").append(numTarjeta).append("\n");
    mensaje.append("Tarifa (especialidad): ").append(tarifaStr).append("\n");
    mensaje.append("Estatus:               ").append(estatus).append("\n\n");

    // Paciente
    mensaje.append("INFORMACIÓN DEL PACIENTE\n");
    mensaje.append("────────────────────────────────────────\n");
    mensaje.append("Nombre:                ").append(nombrePaciente).append("\n");
    mensaje.append("Número de Seguro:      ").append(nss).append("\n\n");

    // Cita/Médico
    if (idCita != null) {
        mensaje.append("INFORMACIÓN DE LA CITA\n");
        mensaje.append("────────────────────────────────────────\n");
        mensaje.append("ID Cita:               ").append(idCita).append("\n");
        mensaje.append("Fecha:                 ").append(fechaCita).append("\n");
        mensaje.append("Hora:                  ").append(horaCita).append("\n\n");

        mensaje.append("INFORMACIÓN DEL MÉDICO\n");
        mensaje.append("────────────────────────────────────────\n");
        mensaje.append("Médico:                ").append(nombreMedico).append("\n");
        mensaje.append("Especialidad:          ").append(especialidad != null ? especialidad : "No especificada").append("\n");
    } else {
        mensaje.append("NOTA\n");
        mensaje.append("────────────────────────────────────────\n");
        mensaje.append("No se encontró cita relacionada con este pago.\n");
    }

    mensaje.append("\n========================================\n");
    return mensaje.toString();
}

   private static boolean hasColumn(Connection conn, String table, String column) throws SQLException {
    String sql = "SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME=? AND COLUMN_NAME=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, table);
        ps.setString(2, column);
        try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
    }
}

    
    private void ensureTablaTarifaIfMissing() throws SQLException {
    String ddl = ""
        + "IF NOT EXISTS (SELECT 1 FROM sys.objects "
        + "WHERE object_id = OBJECT_ID(N'[dbo].[tarifa_especialidad]') AND type = N'U') "
        + "BEGIN "
        + "  CREATE TABLE dbo.tarifa_especialidad( "
        + "    especialidad    VARCHAR(100) NOT NULL PRIMARY KEY, "
        + "    precio          DECIMAL(10,2) NOT NULL, "
        + "    actualizado_por VARCHAR(100) NULL, "
        + "    actualizado_en  DATETIME NOT NULL DEFAULT GETDATE() "
        + "  ); "
        + "END";
    try (Statement st = con.createStatement()) { st.executeUpdate(ddl); }
}

    private static String s(Object o){ return o==null? "" : o.toString(); }

private void cargarTodosLosPagos() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Sin conexión.");
            return;
        }

       String query =
    "WITH tot AS ( " +
    "  SELECT c.idCita, SUM(p.monto) AS total_pagado " +
    "  FROM dbo.Pago p " +
    "  JOIN dbo.PagoCita pc ON pc.idPago = p.idPago " +       // <- SOLO pagos con cita
    "  JOIN dbo.Cita c ON c.idCita = pc.idCita " +
    "  GROUP BY c.idCita " +
    ") " +
    "SELECT p.idPago, p.fechaPago, p.monto, " +
    "       pac.nombrePac, pac.apellido1, pac.apellido2, c.numeroSeguro, " +
    "       te.precio AS tarifa, ISNULL(tot.total_pagado, 0) AS total_pagado_cita, " +
    "       CASE " +
    "         WHEN te.precio IS NULL THEN 'Pagado' " +
    "         WHEN ISNULL(tot.total_pagado,0) >= te.precio THEN 'Pagado' " +
    "         ELSE 'Pago incompleto: falta $' + CONVERT(varchar(32), te.precio - ISNULL(tot.total_pagado,0)) " +
    "       END AS estatus " +
    "FROM dbo.Pago p " +
    "JOIN dbo.PagoCita pc ON pc.idPago = p.idPago " +          // <- INNER, no LEFT
    "JOIN dbo.Cita c ON c.idCita = pc.idCita " +
    "JOIN dbo.Medico m ON m.idMedico = c.idMedico " +
    "LEFT JOIN dbo.PACIENTE pac ON pac.numeroSeguro = c.numeroSeguro " +
    "LEFT JOIN dbo.tarifa_especialidad te ON te.especialidad = m.Especialidad " +
    "LEFT JOIN tot ON tot.idCita = c.idCita " +
    "ORDER BY p.fechaPago DESC, p.idPago DESC";



        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);

        m.setRowCount(0);
        double totalMonto = 0.0;
        int totalPagos = 0;

        while (rs.next()) {
           double monto = rs.getBigDecimal("monto") == null ? 0.0 : rs.getBigDecimal("monto").doubleValue();
double tarifa = rs.getBigDecimal("tarifa") == null ? 0.0 : rs.getBigDecimal("tarifa").doubleValue();

m.addRow(new Object[] {
    rs.getObject("idPago"),
    rs.getObject("fechaPago"),
    "$" + String.format("%.2f", monto),
    (tarifa == 0.0 ? "—" : "$" + String.format("%.2f", tarifa)),
    s(rs.getString("nombrePac")),
    s(rs.getString("apellido1")),
    s(rs.getString("apellido2")),
    s(rs.getString("numeroSeguro")),
    s(rs.getString("estatus"))
});

            totalMonto += monto;
            totalPagos++;
        }

        lblPago.setText("Pagos registrados — Total: $" + String.format("%.2f", totalMonto));
        jLabel2.setText("Pagos: " + totalPagos);

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar los pagos: " + ex.getMessage(),
                "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
        try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
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

        lblpagos = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCita = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lblPago = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblpagos.setBackground(new java.awt.Color(255, 255, 255));
        lblpagos.setMaximumSize(new java.awt.Dimension(850, 600));

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
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Fecha Pago", "Abono", "Costo", "Nombre", "Apellido 1", "Apellido 2", "NSS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, true, false
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

        javax.swing.GroupLayout lblpagosLayout = new javax.swing.GroupLayout(lblpagos);
        lblpagos.setLayout(lblpagosLayout);
        lblpagosLayout.setHorizontalGroup(
            lblpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblpagosLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(lblpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblpagosLayout.createSequentialGroup()
                        .addGroup(lblpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(lblpagosLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(lblpagosLayout.createSequentialGroup()
                                .addGap(285, 285, 285)
                                .addComponent(lblPago)))
                        .addContainerGap(13, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblpagosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(164, 164, 164))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblpagosLayout.createSequentialGroup()
                .addGap(363, 917, Short.MAX_VALUE)
                .addComponent(lblFecha)
                .addGap(53, 53, 53))
        );
        lblpagosLayout.setVerticalGroup(
            lblpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(lblpagosLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblPago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFecha)
                .addGap(90, 90, 90)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblpagos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblpagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblSalirMouseClicked
   
    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
         try {
        VentanaRecepcion vp = new VentanaRecepcion(usuarioId);
        vp.setVisible(true);
        this.dispose();
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(ventanaCitasPacRecepcionista.class.getName())
              .log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(
                this,
                "Error al abrir la ventana de inicio:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
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
                ventaPagos vcm = new ventaPagos(usuarioId);
                vcm.setVisible(true);
            }
        });
    }
    private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblPago;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel lblpagos;
    private javax.swing.JTable tblCita;
    // End of variables declaration//GEN-END:variables
}
