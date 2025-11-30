
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement; 
import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class ventanaPacientesRecp extends javax.swing.JFrame {

    private static String usuarioId;
    //private static Statement stmt;
    //public static Connection con;

    public ventanaPacientesRecp(String usuarioId) throws ClassNotFoundException {//String usuarioId
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        //con = ConexionSQL.ConexionSQLServer();
        m = (DefaultTableModel) tblCita.getModel();
        lblFecha.setText(obtenerFecha());
        llenarCitas();
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
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        reloj11 = new Reloj1();

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
                .addComponent(lblUsuario)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSalir)
                    .addComponent(lblInicio))
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInicio)
                .addGap(216, 216, 216)
                .addComponent(lblSalir)
                .addGap(119, 119, 119))
        );

        tblCita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblCita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido1", "Apellido2", "numeroSeguro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
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
        jLabel2.setText("Datos de pacientes");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuarioM.png"))); // NOI18N
        jLabel3.setText("Pacientes");

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                                .addComponent(lblFecha)
                                .addGap(0, 89, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(201, 201, 201)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(76, 76, 76)))
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(160, 160, 160)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblSalirMouseClicked

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        char c = (char) evt.getKeyCode();
        if (c == evt.VK_ENTER){
            try {
                buscarPaciente(txtBuscar.getText());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ventanaPacientesRecp.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(txtBuscar.getText().equals("")){
                try {
                    llenarCitas();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ventanaPacientesRecp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        
    }//GEN-LAST:event_txtBuscarKeyReleased
  
    public static String obtenerIdMed(String correo) throws ClassNotFoundException {
    Connection conn = null;
    Statement stmt2 = null;
    ResultSet resultadoMed = null;
    String identificadorMed = "";
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            System.err.println("Error: No se pudo conectar a la BD");
            return "";
        }
        
        stmt2 = conn.createStatement();
        String idMed = "SELECT med.idMedico \n" +
                      "FROM MEDICO med\n" +  
                      "INNER JOIN usuario u ON (med.Correo = u.correo)\n" +
                      "WHERE med.Correo = '" + correo + "'";
        
        resultadoMed = stmt2.executeQuery(idMed);
        
        while(resultadoMed.next()){
            identificadorMed = resultadoMed.getString("idMedico");
        }
       
    } catch (SQLException ex) {
        System.err.println("Error en obtenerIdMed: " + ex.getMessage());
    } finally {
        try {
            if (resultadoMed != null) resultadoMed.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (stmt2 != null) stmt2.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    return identificadorMed;
}
    
    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        VentanaRecepcion vm = null;
        try {
            vm = new VentanaRecepcion(this.usuarioId);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ventanaPacientesRecp.class.getName()).log(Level.SEVERE, null, ex);
        }
        vm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked
    
    private void mostrarMenuContextual(int fila, int x, int y) {
    JPopupMenu menuContextual = new JPopupMenu();
    
    // Obtener datos del paciente seleccionado
    String numeroSeguro = tblCita.getValueAt(fila, 3).toString();
    String nombrePaciente = tblCita.getValueAt(fila, 0).toString() + " " + 
                           tblCita.getValueAt(fila, 1).toString();
    
    // Opción 1: Ver información del paciente
    JMenuItem itemInfo = new JMenuItem("Ver Información del Paciente");
    itemInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    itemInfo.addActionListener(e -> {
       try {
        mostrarInformacionCompletaPaciente(numeroSeguro, nombrePaciente); // ← CAMBIADO
       } catch (ClassNotFoundException ex) {
        Logger.getLogger(ventanaPacientesRecp.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
    
    // Opción 2: Ver citas del paciente
    JMenuItem itemCitas = new JMenuItem("Ver Citas del Paciente");
    itemCitas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    itemCitas.addActionListener(e -> {
        try {
            mostrarCitasPaciente(numeroSeguro, nombrePaciente);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ventanaPacientesRecp.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
    
    menuContextual.add(itemInfo);
    menuContextual.add(itemCitas);
    
    // Mostrar menú en la posición del click
    menuContextual.show(tblCita, x, y);
 }
    
    private void mostrarInformacionCompletaPaciente(String numeroSeguro, String nombrePaciente) throws ClassNotFoundException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        String query = "SELECT * FROM PACIENTE WHERE numeroSeguro = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, numeroSeguro);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            // Obtener todos los datos del paciente
            String nombre = rs.getString("nombrePac");
            String apellido1 = rs.getString("apellido1");
            String apellido2 = rs.getString("apellido2");
            String correo = rs.getString("Correo");
            
            // Crear diálogo con pestañas
            JDialog dialog = new JDialog(this, "Historial Médico - " + nombre, true);
            dialog.setLayout(new BorderLayout());
            dialog.setPreferredSize(new Dimension(800, 500));
            
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));
            
            // Agregar pestañas
            tabbedPane.addTab("Información Personal", 
                crearPanelInformacionPersonal(numeroSeguro, nombre, apellido1, apellido2, correo));
            tabbedPane.addTab("Historial de Citas", 
                crearPanelHistorialCitas(numeroSeguro));
            
            dialog.add(tabbedPane, BorderLayout.CENTER);
            
            // Panel inferior
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JButton btnCerrar = new JButton("❌ Cerrar");
            btnCerrar.addActionListener(e -> dialog.dispose());
            bottomPanel.add(btnCerrar);
            
            dialog.add(bottomPanel, BorderLayout.SOUTH);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
                
        } else {
            mostrarError("No se encontró información del paciente.");
        }
        
    } catch (SQLException ex) {
        mostrarError("Error al obtener información: " + ex.getMessage());
        Logger.getLogger(ventanaPacientesRecp.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        cerrarRecursos(conn, stmt, rs);
    }
}

private JPanel crearPanelInformacionPersonal(String numeroSeguro, String nombre, String apellido1, String apellido2, String correo) {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    // Header
    JLabel headerLabel = new JLabel("INFORMACIÓN PERSONAL DEL PACIENTE", JLabel.CENTER);
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    headerLabel.setForeground(new Color(70, 130, 180));
    headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    
    // Panel de información
    JPanel infoPanel = new JPanel(new GridLayout(0, 2, 15, 10));
    infoPanel.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));
    
    agregarCampoInfo(infoPanel, "Número de Seguro:", numeroSeguro);
    agregarCampoInfo(infoPanel, "Nombre:", nombre);
    agregarCampoInfo(infoPanel, "Primer Apellido:", apellido1);
    agregarCampoInfo(infoPanel, "Segundo Apellido:", apellido2);
    agregarCampoInfo(infoPanel, "Correo Electrónico:", correo);
    
        // Obtener datos adicionales
        /*
        Connection conn = ConexionSQL.ConexionSQLServer();
        String queryPaciente = "SELECT telefono, fechaRegistro FROM PACIENTE WHERE numeroSeguro = ?";
        PreparedStatement stmt = conn.prepareStatement(queryPaciente);
        stmt.setString(1, numeroSeguro);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
        agregarCampoInfo(infoPanel, "Teléfono:", rs.getString("telefono"));
        agregarCampoInfo(infoPanel, "Fecha de Registro:", rs.getString("fechaRegistro"));
        }
        rs.close();
        stmt.close();
        conn.close();
         */
    
    panel.add(headerLabel, BorderLayout.NORTH);
    panel.add(infoPanel, BorderLayout.CENTER);
    
    return panel;
}

private JPanel crearPanelHistorialCitas(String numeroSeguro) {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
    JLabel titulo = new JLabel("HISTORIAL DE CITAS MÉDICAS", JLabel.CENTER);
    titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
    titulo.setForeground(new Color(70, 130, 180));
    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
    
    // Crear tabla de citas (usa el mismo código que mostrarCitasPaciente pero sin el diálogo)
    DefaultTableModel modelCitas = new DefaultTableModel(
        new Object[]{"Fecha", "Hora", "Médico", "Especialidad", "Estatus"}, 0
    );
    
    try {
        Connection conn = ConexionSQL.ConexionSQLServer();
        String query = "SELECT c.fecha, c.hora, " +
                      "CONCAT(med.nombreMed, ' ', med.apellido1) as Medico, " +
                      "med.Especialidad, " +
                      "CASE WHEN c.estatus IS NULL THEN 'ACTIVA' ELSE c.estatus END as estatus " +
                      "FROM CITA c " +
                      "INNER JOIN MEDICO med ON CAST(med.idMedico AS VARCHAR) = c.idMedico " +
                      "WHERE c.numeroSeguro = ? " +
                      "ORDER BY c.fecha DESC, c.hora DESC";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, numeroSeguro);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            modelCitas.addRow(new Object[]{
                rs.getDate("fecha"),
                rs.getString("hora"),
                rs.getString("Medico"),
                rs.getString("Especialidad"),
                rs.getString("estatus")
            });
        }
        
        conn.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    
    JTable tablaCitas = new JTable(modelCitas);
    tablaCitas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    tablaCitas.setRowHeight(25);
    tablaCitas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    tablaCitas.setDefaultEditor(Object.class, null);
    
    JScrollPane scrollPane = new JScrollPane(tablaCitas);
    
    panel.add(titulo, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);
    
    return panel;
}
    

    
    private void cerrarRecursos(Connection conn, Statement stmt, ResultSet rs) {
    try {
        if (rs != null) rs.close();
    } catch (SQLException e) {
        System.err.println("Error cerrando ResultSet: " + e.getMessage());
    }
    try {
        if (stmt != null) stmt.close();
    } catch (SQLException e) {
        System.err.println("Error cerrando Statement: " + e.getMessage());
    }
    try {
        if (conn != null) conn.close();
    } catch (SQLException e) {
        System.err.println("Error cerrando Connection: " + e.getMessage());
    }
}
    
    
    

    private void mostrarCitasPaciente(String numeroSeguro, String nombrePaciente) throws ClassNotFoundException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        String query = "SELECT c.fecha, c.hora, " +
                      "CONCAT(med.nombreMed, ' ', med.apellido1) as Medico, " +
                      "med.Especialidad, " +
                      "CASE WHEN c.estatus IS NULL THEN 'ACTIVA' ELSE c.estatus END as estatus " +
                      "FROM CITA c " +
                      "INNER JOIN MEDICO med ON CAST(med.idMedico AS VARCHAR) = c.idMedico " +
                      "WHERE c.numeroSeguro = ? " +
                      "ORDER BY c.fecha DESC, c.hora DESC";
        
        stmt = conn.prepareStatement(query);
        stmt.setString(1, numeroSeguro);
        rs = stmt.executeQuery();
        
        // Crear modelo de tabla para las citas
        DefaultTableModel modelCitas = new DefaultTableModel(
            new Object[]{"Fecha", "Hora", "Médico", "Especialidad", "Estatus"}, 0
        );
        
        int totalCitas = 0;
        while (rs.next()) {
            modelCitas.addRow(new Object[]{
                rs.getDate("fecha"),
                rs.getString("hora"),
                rs.getString("Medico"),      
                rs.getString("Especialidad"),
                rs.getString("estatus")
            });
            totalCitas++;
        }
        
        // Resto del código para crear la tabla...
        JTable tablaCitas = new JTable(modelCitas);
        tablaCitas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaCitas.setRowHeight(25);
        tablaCitas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaCitas.setDefaultEditor(Object.class, null);
        
        // Configurar colores según estatus
        tablaCitas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                String estatus = table.getValueAt(row, 4).toString();
                if ("ACTIVA".equals(estatus)) {
                    c.setBackground(new Color(220, 255, 220));
                } else if ("CANCELADA".equals(estatus)) {
                    c.setBackground(new Color(255, 220, 220));
                } else if ("COMPLETADA".equals(estatus)) {
                    c.setBackground(new Color(220, 220, 255));
                } else {
                    c.setBackground(Color.WHITE);
                }
                
                if (isSelected) {
                    c.setBackground(new Color(51, 153, 255));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setForeground(Color.BLACK);
                }
                
                return c;
            }
        });
        
        // Crear panel contenedor
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel de título
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(70, 130, 180));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel titulo = new JLabel("CITAS DEL PACIENTE: " + nombrePaciente);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        
        JLabel subtitulo = new JLabel("Número de Seguro: " + numeroSeguro + " | Total de citas: " + totalCitas);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitulo.setForeground(Color.LIGHT_GRAY);
        
        panelTitulo.add(titulo, BorderLayout.NORTH);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);
        
        // Panel de tabla con scroll
        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        
        panel.add(panelTitulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Mostrar en un JDialog personalizado
        JDialog dialog = new JDialog(this, "Historial de Citas", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
    } catch (SQLException ex) {
        mostrarError("Error al obtener citas: " + ex.getMessage());
        Logger.getLogger(ventanaPacientesRecp.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        cerrarRecursos(conn, stmt, rs);
    }
}

// Método auxiliar para agregar campos de información
private void agregarCampoInfo(JPanel panel, String etiqueta, String valor) {
    JLabel lblEtiqueta = new JLabel(etiqueta);
    lblEtiqueta.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lblEtiqueta.setForeground(new Color(70, 70, 70));
    
    JLabel lblValor = new JLabel(valor != null ? valor : "No especificado");
    lblValor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    lblValor.setForeground(Color.BLACK);
    
    panel.add(lblEtiqueta);
    panel.add(lblValor);
}

private void mostrarError(String mensaje) {
    JOptionPane.showMessageDialog(this, 
        mensaje, 
        "Error", 
        JOptionPane.ERROR_MESSAGE);
}


    
    private void tblCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitaMouseClicked
        if (evt.getClickCount() == 1) { // Click simple
        int row = tblCita.rowAtPoint(evt.getPoint());
        int col = tblCita.columnAtPoint(evt.getPoint());
        
        if (row >= 0 && col >= 0) {
            mostrarMenuContextual(row, evt.getX(), evt.getY());
        }
       }
    }//GEN-LAST:event_tblCitaMouseClicked
    
    private String obtenerFecha(){
        String fecha="";
        LocalDateTime hoy = LocalDateTime.now();  
        fecha = hoy.getMonthValue()+"/"+hoy.getDayOfMonth()+"/"+hoy.getYear();
        return fecha;
    }
    
    private void buscarPaciente(String nombre) throws ClassNotFoundException {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            System.err.println("Error: No se pudo conectar a la BD");
            return;
        }
        
        m.setRowCount(0);
        String busqueda = nombre; 
        
        String query = "SELECT pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro\n" +
                      "FROM PACIENTE pac\n" +  // ← CAMBIADO AQUÍ
                      "WHERE pac.nombrePac LIKE '%" + busqueda + "%'";
        
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        Object R[] = new Object[4];
        
        while (rs.next()) {
            R[0] = rs.getObject("nombrePac");
            R[1] = rs.getObject("apellido1");
            R[2] = rs.getObject("apellido2");
            R[3] = rs.getObject("numeroSeguro");
            m.addRow(R);
        }
    } catch (SQLException ex) {
        System.err.println("Error buscando paciente: " + ex.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
    
    public void llenarCitas() throws ClassNotFoundException {
    Connection conn = null;
    Statement stmt2 = null;
    Statement stmt = null;
    ResultSet resultadoMed = null;
    ResultSet result = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            System.err.println("Error: No se pudo conectar a la BD");
            return;
        }
        
        m.setRowCount(0);
        String identificadorMed = "";
        
        stmt2 = conn.createStatement();
        String idMed = "SELECT med.idMedico \n" +
                      "FROM MEDICO med\n" +  // ← CAMBIADO AQUÍ
                      "INNER JOIN usuario u ON (med.Correo = u.correo)\n" +
                      "WHERE med.Correo = '" + this.usuarioId + "'";
        
        resultadoMed = stmt2.executeQuery(idMed);
        
        while(resultadoMed.next()){
            identificadorMed = resultadoMed.getString("idMedico");
        }
        
        stmt = conn.createStatement();
        String query = "SELECT pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro\n" +
                      "FROM CITA cit\n" +  // ← CAMBIADO
                      "INNER JOIN PACIENTE pac ON (pac.numeroSeguro = cit.numeroSeguro)\n" +  // ← CAMBIADO
                      "WHERE cit.idMedico = '" + identificadorMed + "'";
        
        result = stmt.executeQuery(query);
        
        Object R[] = new Object[4];
        while (result.next()) {
            R[0] = result.getObject("nombrePac");
            R[1] = result.getObject("apellido1");
            R[2] = result.getObject("apellido2");
            R[3] = result.getObject("numeroSeguro");
            m.addRow(R);
        }
        
    } catch (SQLException ex) {
        System.err.println("Error llenando citas: " + ex.getMessage());
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar pacientes: " + ex.getMessage());
    } finally {
        try {
            if (result != null) result.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (resultadoMed != null) resultadoMed.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (stmt2 != null) stmt2.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) { e.printStackTrace(); }
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
                ventanaCitasMed vcm = null;
                vcm = new ventanaCitasMed(usuarioId);
                vcm.setVisible(true);
            }
        });
    }
    private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private Reloj1 reloj11;
    private javax.swing.JTable tblCita;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
