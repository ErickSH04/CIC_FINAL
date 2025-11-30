
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;  
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class VentanaInfoMedRecp extends javax.swing.JFrame {

    private String usuarioId;
    //private static Connection con;
    //private static Statement stmt;
    private static String doctor;
    

    
    public VentanaInfoMedRecp() throws ClassNotFoundException {
        //con = ConexionSQL.ConexionSQLServer();
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
    }
    
    
    public VentanaInfoMedRecp(String usuarioId) throws ClassNotFoundException {//String usuarioId
        //con = ConexionSQL.ConexionSQLServer();
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        llenarTablaMedicos();
        configurarTablaMedicos();
        configurarMenuContextual();
    
        llenarTablaMedicos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblCerrarSesion = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblInformacion = new javax.swing.JLabel();
        lblNombreR = new javax.swing.JLabel();
        lblMedicoIdR = new javax.swing.JLabel();
        lblEspecialidadR = new javax.swing.JLabel();
        lblCedulaR = new javax.swing.JLabel();
        lblTelefonoR = new javax.swing.JLabel();
        lblCorreoR = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        lblCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblInicio.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pagina-de-inicio.png"))); // NOI18N
        lblInicio.setText("Inicio");
        lblInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInicioMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCerrarSesion)
                            .addComponent(lblInicio))))
                .addGap(0, 64, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInicio)
                .addGap(131, 131, 131)
                .addComponent(lblCerrarSesion)
                .addGap(448, 448, 448))
        );

        lblInformacion.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblInformacion.setText(" Medicos");

        lblNombreR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblMedicoIdR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblEspecialidadR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblCedulaR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblTelefonoR.setFont(new java.awt.Font("Roboto", 2, 14));

        lblCorreoR.setFont(new java.awt.Font("Roboto", 2, 14));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "IDMedico", "Nombre", "Apellido 1", "Apellido 2", "Especialidad", "Cedula"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCedulaR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMedicoIdR, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEspecialidadR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelefonoR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCorreoR, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombreR, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMedicoIdR, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblNombreR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEspecialidadR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(lblCedulaR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(lblTelefonoR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(lblCorreoR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(211, 211, 211))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        VentanaRecepcion vp = null;
        try {
            vp = new VentanaRecepcion(usuarioId);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaInfoMedRecp.class.getName()).log(Level.SEVERE, null, ex);
        }
        vp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

   
    public void llenarTablaMedicos() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            System.err.println("Error: No se pudo conectar a la BD");
            return;
        }
        
        // Limpiar tabla
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        String query = "SELECT idMedico, nombreMed, apellido1, apellido2, Especialidad, NumCedula " +
                      "FROM MEDICO " +
                      "ORDER BY Especialidad, nombreMed";
        
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            Object[] row = {
                rs.getString("idMedico"),
                rs.getString("nombreMed"),
                rs.getString("apellido1"),
                rs.getString("apellido2"),
                rs.getString("Especialidad"),
                rs.getString("NumCedula")
            };
            model.addRow(row);
        }
        
        System.out.println("Tabla de médicos llenada correctamente");
        
    } catch (SQLException ex) {
        System.err.println("Error llenando tabla de médicos: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
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
            //if (conn != null) ConexionSQL.cerrarConexion(conn);
        } catch (Exception e) {
            System.err.println("Error cerrando Connection: " + e.getMessage());
        }
    }
    
    }
    
    
   
private void configurarTablaMedicos() {
    // Configurar tabla con aspecto profesional
    jTable1.setRowHeight(30);
    jTable1.setShowGrid(true);
    jTable1.setGridColor(new java.awt.Color(240, 240, 240));
    jTable1.setSelectionBackground(new java.awt.Color(51, 153, 255));
    jTable1.setSelectionForeground(Color.WHITE);
    jTable1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
    
    // Centrar contenido de la tabla
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < jTable1.getColumnCount(); i++) {
        jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    // Configurar header de la tabla
    JTableHeader header = jTable1.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 13));
    header.setBackground(new Color(70, 130, 180));
    header.setForeground(Color.WHITE);
    
    // Hacer tabla no editable
    jTable1.setDefaultEditor(Object.class, null);

}

private void configurarMenuContextual() {
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getClickCount() == 1) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                int col = jTable1.columnAtPoint(evt.getPoint());
                
                if (row >= 0 && col >= 0) {
                    mostrarMenuContextualMedico(row, evt.getX(), evt.getY());
                }
            }
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()) {
                mostrarMenuContextualEnFila(e);
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                mostrarMenuContextualEnFila(e);
            }
        }
        
        private void mostrarMenuContextualEnFila(MouseEvent e) {
            int row = jTable1.rowAtPoint(e.getPoint());
            if (row >= 0) {
                jTable1.setRowSelectionInterval(row, row);
                mostrarMenuContextualMedico(row, e.getX(), e.getY());
            }
        }
    });
}

// Y también necesitas este método:
private void mostrarMenuContextualMedico(int fila, int x, int y) {
    JPopupMenu menuContextual = new JPopupMenu();
    
    // Obtener datos del médico seleccionado
    String idMedico = jTable1.getValueAt(fila, 0).toString();
    String nombreMedico = jTable1.getValueAt(fila, 1).toString() + " " + 
                         jTable1.getValueAt(fila, 2).toString();
    String especialidad = jTable1.getValueAt(fila, 4).toString();
    
    // Opción 1: Ver información completa del médico
    JMenuItem itemInfo = new JMenuItem(" Ver Información Completa");
    itemInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    itemInfo.addActionListener(e -> {
        try {
            mostrarInformacionMedicoCompleta(idMedico, nombreMedico, especialidad);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaInfoMedRecp.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
    
    // Opción 2: Ver citas del médico
    JMenuItem itemCitas = new JMenuItem("Ver Citas Programadas");
    itemCitas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    itemCitas.addActionListener(e -> {
        try {
            mostrarCitasMedico(idMedico, nombreMedico, especialidad);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaInfoMedRecp.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
    
    menuContextual.add(itemInfo);
    menuContextual.add(itemCitas);
    
    // Mostrar menú en la posición del click
    menuContextual.show(jTable1, x, y);
}

  private void mostrarInformacionMedicoCompleta(String idMedico, String nombreMedico, String especialidad) throws ClassNotFoundException {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        stmt = conn.createStatement();
        
        String query = "SELECT * FROM MEDICO WHERE idMedico = '" + idMedico + "'";
        rs = stmt.executeQuery(query);
        
        if (rs.next()) {
            // Obtener todos los datos del médico
            String nombre = rs.getString("nombreMed");
            String apellido1 = rs.getString("apellido1");
            String apellido2 = getStringSeguro(rs, "apellido2");
            String correo = getStringSeguro(rs, "Correo");
            String cedula = rs.getString("NumCedula");
            String telefono = getStringSeguro(rs, "Telefono");
            String hospital = getStringSeguro(rs, "Hospital");
            String direccion = getStringSeguro(rs, "DireccionConsultorio");
            
            // Crear diálogo con pestañas
            JDialog dialog = new JDialog(this, "Perfil Médico - " + nombre, true);
            dialog.setLayout(new BorderLayout());
            dialog.setPreferredSize(new Dimension(850, 600));
            
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));
            
            // Agregar pestañas
            tabbedPane.addTab(" Información Profesional", 
                crearPanelInformacionMedico(idMedico, nombre, apellido1, apellido2, correo, especialidad, cedula, telefono, hospital, direccion));
            tabbedPane.addTab("Historial de Citas", 
                crearPanelAgendaMedico(idMedico, nombre, especialidad));
            tabbedPane.addTab("stadísticas", 
                crearPanelEstadisticasMedico(idMedico));
            
            dialog.add(tabbedPane, BorderLayout.CENTER);
            
            // Panel inferior
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JButton btnCerrar = new JButton("❌ Cerrar");
            btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnCerrar.addActionListener(e -> dialog.dispose());
            bottomPanel.add(btnCerrar);
            
            dialog.add(bottomPanel, BorderLayout.SOUTH);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
                
        } else {
            mostrarError("No se encontró información del médico.");
        }
        
    } catch (SQLException ex) {
        mostrarError("Error al obtener información: " + ex.getMessage());
    } finally {
        cerrarRecursos(conn, stmt, rs);
    }
}
  
  private JPanel crearPanelInformacionMedico(String idMedico, String nombre, String apellido1, String apellido2, 
                                         String correo, String especialidad, String cedula, String telefono, 
                                         String hospital, String direccion) {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(Color.WHITE);
    
    // Header
    JLabel headerLabel = new JLabel("INFORMACIÓN PROFESIONAL", JLabel.CENTER);
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    headerLabel.setForeground(new Color(0, 100, 0));
    headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    
    // Panel de información profesional
    JPanel infoPanel = new JPanel(new GridLayout(0, 2, 15, 12));
    infoPanel.setBorder(BorderFactory.createTitledBorder("Datos Profesionales y de Contacto"));
    infoPanel.setBackground(Color.WHITE);
    
    // Información básica del médico
    agregarCampoEstilizado(infoPanel, "ID Médico:", idMedico);
    agregarCampoEstilizado(infoPanel, "Nombre Completo:", nombre + " " + apellido1 + (apellido2 != null ? " " + apellido2 : ""));
    agregarCampoEstilizado(infoPanel, "Especialidad:", especialidad);
    agregarCampoEstilizado(infoPanel, "Cédula Profesional:", cedula);
    agregarCampoEstilizado(infoPanel, "Teléfono:", telefono != null ? telefono : "No especificado");
    agregarCampoEstilizado(infoPanel, "Correo Electrónico:", correo != null ? correo : "No especificado");
    agregarCampoEstilizado(infoPanel, "Hospital/Clínica:", hospital != null ? hospital : "No especificado");
    agregarCampoEstilizado(infoPanel, "Dirección Consultorio:", direccion != null ? direccion : "No especificado");
    
    panel.add(headerLabel, BorderLayout.NORTH);
    panel.add(infoPanel, BorderLayout.CENTER);
    
    return panel;
}

private JPanel crearPanelAgendaMedico(String idMedico, String nombreMedico, String especialidad) {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    panel.setBackground(Color.WHITE);
    
    JLabel titulo = new JLabel("HISTORIAL DE CITAS PROGRAMADAS", JLabel.CENTER);
    titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
    titulo.setForeground(new Color(0, 100, 0));
    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
    
    // Crear tabla de citas del médico
    DefaultTableModel modelCitas = new DefaultTableModel(
        new Object[]{"Fecha", "Hora", "Paciente", "Número de Seguro", "Estatus"}, 0
    );
    
    int totalCitas = 0;
    int citasActivas = 0;
    int citasCompletadas = 0;
    
    try {
        Connection conn = ConexionSQL.ConexionSQLServer();
        Statement stmt = conn.createStatement();
        
        String query = "SELECT c.fecha, c.hora, " +
                      "CONCAT(p.nombrePac, ' ', p.apellido1) as Paciente, " +
                      "p.numeroSeguro, " +
                      "CASE WHEN c.estatus IS NULL THEN 'ACTIVA' ELSE c.estatus END as estatus " +
                      "FROM CITA c " +
                      "INNER JOIN PACIENTE p ON c.numeroSeguro = p.numeroSeguro " +
                      "WHERE c.idMedico = '" + idMedico + "' " +
                      "ORDER BY c.fecha, c.hora";
        
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            String estatus = rs.getString("estatus");
            modelCitas.addRow(new Object[]{
                rs.getDate("fecha"),
                rs.getString("hora"),
                rs.getString("Paciente"),
                rs.getString("numeroSeguro"),
                estatus
            });
            totalCitas++;
            if ("ACTIVA".equals(estatus)) citasActivas++;
            if ("COMPLETADA".equals(estatus)) citasCompletadas++;
        }
        
        conn.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    
    // Panel de resumen
    JPanel resumenPanel = new JPanel(new GridLayout(1, 3, 10, 10));
    resumenPanel.setBorder(BorderFactory.createTitledBorder("Resumen de Citas"));
    resumenPanel.setBackground(new Color(240, 248, 255));
    
    agregarTarjetaResumen(resumenPanel, "Total", String.valueOf(totalCitas), Color.BLUE);
    agregarTarjetaResumen(resumenPanel, "Activas", String.valueOf(citasActivas), Color.GREEN);
    agregarTarjetaResumen(resumenPanel, "Completadas", String.valueOf(citasCompletadas), new Color(255, 165, 0));
    
    // Tabla de citas
    JTable tablaCitas = new JTable(modelCitas);
    configurarTablaCitasMedico(tablaCitas);
    
    JScrollPane scrollPane = new JScrollPane(tablaCitas);
    scrollPane.setPreferredSize(new Dimension(700, 300));
    
    if (totalCitas == 0) {
        JLabel lblSinCitas = new JLabel("El médico no tiene citas programadas", JLabel.CENTER);
        lblSinCitas.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblSinCitas.setForeground(Color.GRAY);
        panel.add(lblSinCitas, BorderLayout.CENTER);
    } else {
        panel.add(resumenPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
    }
    
    panel.add(titulo, BorderLayout.NORTH);
    
    return panel;
}

private JPanel crearPanelEstadisticasMedico(String idMedico) {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(Color.WHITE);
    
    JLabel titulo = new JLabel("ESTADÍSTICAS PROFESIONALES", JLabel.CENTER);
    titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
    titulo.setForeground(new Color(0, 100, 0));
    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    
    JPanel statsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
    statsPanel.setBorder(BorderFactory.createTitledBorder("Métricas del Médico"));
    statsPanel.setBackground(Color.WHITE);
    
    try {
        Connection conn = ConexionSQL.ConexionSQLServer();
        Statement stmt = conn.createStatement();
        
        // Consulta para estadísticas del médico
        String queryStats = "SELECT " +
            "COUNT(*) as total_citas, " +
            "SUM(CASE WHEN estatus = 'ACTIVA' OR estatus IS NULL THEN 1 ELSE 0 END) as citas_activas, " +
            "SUM(CASE WHEN estatus = 'COMPLETADA' THEN 1 ELSE 0 END) as citas_completadas, " +
            "SUM(CASE WHEN estatus = 'CANCELADA' THEN 1 ELSE 0 END) as citas_canceladas " +
            "FROM CITA WHERE idMedico = '" + idMedico + "'";
            
        ResultSet rs = stmt.executeQuery(queryStats);
        
        if (rs.next()) {
            int totalCitas = rs.getInt("total_citas");
            int citasActivas = rs.getInt("citas_activas");
            int citasCompletadas = rs.getInt("citas_completadas");
            int citasCanceladas = rs.getInt("citas_canceladas");
            
            agregarTarjetaEstadistica(statsPanel, "Total Citas", String.valueOf(totalCitas), Color.BLUE);
            agregarTarjetaEstadistica(statsPanel, "Citas Activas", String.valueOf(citasActivas), Color.GREEN);
            agregarTarjetaEstadistica(statsPanel, "Citas Completadas", String.valueOf(citasCompletadas), new Color(255, 165, 0));
            agregarTarjetaEstadistica(statsPanel, "Citas Canceladas", String.valueOf(citasCanceladas), Color.RED);
        }
        
        conn.close();
        
    } catch (SQLException ex) {
        agregarTarjetaEstadistica(statsPanel, "Total Citas", "0", Color.BLUE);
        agregarTarjetaEstadistica(statsPanel, "Citas Activas", "0", Color.GREEN);
        agregarTarjetaEstadistica(statsPanel, "Citas Completadas", "0", new Color(255, 165, 0));
        agregarTarjetaEstadistica(statsPanel, "Citas Canceladas", "0", Color.RED);
    }
    
    panel.add(titulo, BorderLayout.NORTH);
    panel.add(statsPanel, BorderLayout.CENTER);
    
    return panel;
}

private void agregarTarjetaEstadistica(JPanel panel, String titulo, String valor, Color color) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(color, 2),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)
    ));
    
    JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
    lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lblTitulo.setForeground(color);
    
    JLabel lblValor = new JLabel(valor, JLabel.CENTER);
    lblValor.setFont(new Font("Segoe UI", Font.BOLD, 18));
    lblValor.setForeground(Color.BLACK);
    
    card.add(lblTitulo, BorderLayout.NORTH);
    card.add(lblValor, BorderLayout.CENTER);
    
    panel.add(card);
}

private void agregarTarjetaResumen(JPanel panel, String titulo, String valor, Color color) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(color, 1),
        BorderFactory.createEmptyBorder(8, 8, 8, 8)
    ));
    
    JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
    lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
    lblTitulo.setForeground(color);
    
    JLabel lblValor = new JLabel(valor, JLabel.CENTER);
    lblValor.setFont(new Font("Segoe UI", Font.BOLD, 14));
    lblValor.setForeground(Color.BLACK);
    
    card.add(lblTitulo, BorderLayout.NORTH);
    card.add(lblValor, BorderLayout.CENTER);
    
    panel.add(card);
}

private void configurarTablaCitasMedico(JTable tabla) {
    tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    tabla.setRowHeight(30);
    tabla.setIntercellSpacing(new Dimension(0, 1));
    tabla.setShowGrid(true);
    tabla.setGridColor(new Color(240, 240, 240));
    
    // Header de la tabla
    JTableHeader header = tabla.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 13));
    header.setBackground(new Color(50, 100, 150));
    header.setForeground(Color.WHITE);
    header.setReorderingAllowed(false);
    
    // Centrar contenido de columnas
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < tabla.getColumnCount(); i++) {
        tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    // Colores según estatus
    tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                String estatus = table.getValueAt(row, 4).toString().toUpperCase();
                switch (estatus) {
                    case "ACTIVA":
                        c.setBackground(new Color(220, 255, 220));
                        break;
                    case "CANCELADA":
                        c.setBackground(new Color(255, 220, 220));
                        break;
                    case "COMPLETADA":
                        c.setBackground(new Color(220, 230, 255));
                        break;
                    default:
                        c.setBackground(Color.WHITE);
                }
                c.setForeground(Color.BLACK);
            } else {
                c.setBackground(new Color(51, 153, 255));
                c.setForeground(Color.WHITE);
            }
            
            return c;
        }
    });
    
    tabla.setDefaultEditor(Object.class, null);
}
    
   



private void mostrarCitasMedico(String idMedico, String nombreMedico, String especialidad) throws ClassNotFoundException {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        stmt = conn.createStatement();
        
        String query = "SELECT c.fecha, c.hora, " +
                      "CONCAT(p.nombrePac, ' ', p.apellido1) as Paciente, " +
                      "p.numeroSeguro, " +
                      "CASE WHEN c.estatus IS NULL THEN 'ACTIVA' ELSE c.estatus END as estatus " +
                      "FROM CITA c " +
                      "INNER JOIN PACIENTE p ON c.numeroSeguro = p.numeroSeguro " +
                      "WHERE c.idMedico = '" + idMedico + "' " +
                      "ORDER BY c.fecha DESC, c.hora DESC";
        
        rs = stmt.executeQuery(query);
        
        DefaultTableModel modelCitas = new DefaultTableModel(
            new Object[]{"Fecha", "Hora", "Paciente", "Número de Seguro", "Estatus"}, 0
        );
        
        int totalCitas = 0;
        while (rs.next()) {
            modelCitas.addRow(new Object[]{
                rs.getDate("fecha"),
                rs.getString("hora"),
                rs.getString("Paciente"),
                rs.getString("numeroSeguro"),
                rs.getString("estatus")
            });
            totalCitas++;
        }
        
        if (totalCitas == 0) {
            mostrarInfo("El médico " + nombreMedico + " no tiene citas programadas.");
            return;
        }
        
        // DISEÑO DE LA VENTANA DE CITAS
        JDialog dialog = new JDialog(this, "Agenda del Médico", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.WHITE);
        
        //HEADER CON INFORMACIÓN DEL MÉDICO 
        JPanel panelHeader = new JPanel(new BorderLayout(10, 5));
        panelHeader.setBackground(new Color(70, 130, 180));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitulo = new JLabel("AGENDA MÉDICA - CITAS PROGRAMADAS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblSubtitulo = new JLabel(
            "<html><b>Médico:</b> " + nombreMedico + 
            " &nbsp;&nbsp;&nbsp; <b>Especialidad:</b> " + especialidad + 
            " &nbsp;&nbsp;&nbsp; <b>Total de citas:</b> " + totalCitas + "</html>");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);
        
        panelHeader.add(lblTitulo, BorderLayout.NORTH);
        panelHeader.add(lblSubtitulo, BorderLayout.SOUTH);
        
        // --- TABLA DE CITAS ---
        JTable tablaCitas = new JTable(modelCitas);
        
        // Diseño profesional de la tabla
        tablaCitas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaCitas.setRowHeight(30);
        tablaCitas.setIntercellSpacing(new Dimension(0, 1));
        tablaCitas.setShowGrid(true);
        tablaCitas.setGridColor(new Color(240, 240, 240));
        
        // Header de la tabla
        JTableHeader header = tablaCitas.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(50, 100, 150));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        
        // Centrar contenido de columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaCitas.getColumnCount(); i++) {
            tablaCitas.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Colores según estatus
        tablaCitas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String estatus = table.getValueAt(row, 4).toString().toUpperCase();
                    switch (estatus) {
                        case "ACTIVA":
                            c.setBackground(new Color(220, 255, 220)); // Verde claro
                            break;
                        case "CANCELADA":
                            c.setBackground(new Color(255, 220, 220)); // Rojo claro
                            break;
                        case "COMPLETADA":
                            c.setBackground(new Color(220, 230, 255)); // Azul claro
                            break;
                        default:
                            c.setBackground(Color.WHITE);
                    }
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(new Color(51, 153, 255));
                    c.setForeground(Color.WHITE);
                }
                
                return c;
            }
        });
        
        tablaCitas.setDefaultEditor(Object.class, null); // No editable
        
        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scrollPane.setPreferredSize(new Dimension(700, 350));
        
        // --- ENSAMBLAR TODO ---
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        dialog.getContentPane().add(panelPrincipal);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
    } catch (SQLException ex) {
        mostrarError("Error al obtener citas: " + ex.getMessage());
    } finally {
        cerrarRecursos(conn, stmt, rs);
    }
}


// Método para agregar campos estilizados
private void agregarCampoEstilizado(JPanel panel, String etiqueta, String valor) {
    JLabel lblEtiqueta = new JLabel(etiqueta);
    lblEtiqueta.setFont(new Font("Segoe UI", Font.BOLD, 13));
    lblEtiqueta.setForeground(new Color(60, 60, 60));
    
    JLabel lblValor = new JLabel(valor != null ? valor : "No especificado");
    lblValor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    lblValor.setForeground(Color.BLACK);
    lblValor.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
    
    panel.add(lblEtiqueta);
    panel.add(lblValor);
}

// Método seguro para obtener strings
private String getStringSeguro(ResultSet rs, String columnName) {
    try {
        return rs.getString(columnName);
    } catch (SQLException e) {
        return null;
    }
}

// Métodos para mensajes al usuario
private void mostrarError(String mensaje) {
    JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
}

private void mostrarInfo(String mensaje) {
    JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
}

// Método para cerrar recursos
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
                new VentanaPaciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCedulaR;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblCorreoR;
    private javax.swing.JLabel lblEspecialidadR;
    private javax.swing.JLabel lblInformacion;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblMedicoIdR;
    private javax.swing.JLabel lblNombreR;
    private javax.swing.JLabel lblTelefonoR;
    // End of variables declaration//GEN-END:variables
}
