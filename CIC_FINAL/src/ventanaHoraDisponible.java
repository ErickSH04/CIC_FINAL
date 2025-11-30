
import java.awt.event.ItemEvent;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ventanaHoraDisponible extends javax.swing.JFrame {

    private static String usuarioId;
    private String idMedico;
    private String nombreMed;
    private String especialidad;
    private static Statement stmt;
    public static Connection con;

    public ventanaHoraDisponible(String usuarioId) {//String usuarioId
        this.setTitle("Citas Paciente");
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        con = ConexionSQL.ConexionSQLServer();
        m = (DefaultTableModel) tblHoras.getModel();
        txtEspecialidad.setEditable(false); 
    }
    public ventanaHoraDisponible(String usuarioId, String idMedico,
                                String nombreMed, String especialidad) {
        initComponents();
        this.usuarioId = usuarioId;
        this.idMedico = idMedico;
        this.nombreMed = nombreMed;
        this.especialidad = especialidad;
        this.setLocationRelativeTo(null);

        con = ConexionSQL.ConexionSQLServer();
        m = (DefaultTableModel) tblHoras.getModel();
        txtNombre.setText(nombreMed);
        txtNombre.setEditable(false); 
        txtEspecialidad.setText(especialidad);
        txtEspecialidad.setEditable(false); 
        cargarHorasDisponibles();
        // Agregar después de inicializar la tabla en el constructor
tblHoras.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int fila = tblHoras.getSelectedRow();
        
        if (fila >= 0) {
            String estatus = tblHoras.getValueAt(fila, 2).toString();
            String fecha = tblHoras.getValueAt(fila, 0).toString();
            String hora = tblHoras.getValueAt(fila, 1).toString();
            
            if ("Disponible".equals(estatus)) {
                // Aquí puedes abrir una ventana para agendar la cita
                int respuesta = JOptionPane.showConfirmDialog(
                    ventanaHoraDisponible.this,
                    "¿Desea agendar cita para el " + fecha + " a las " + hora + "?",
                    "Confirmar cita",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    agendarCita(fecha, hora);
                }
            } else {
                JOptionPane.showMessageDialog(
                    ventanaHoraDisponible.this,
                    "Este horario no está disponible para agendar.",
                    "Horario ocupado",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }
});    
    }

private void agendarCita(String fecha, String hora) {
    String horaFormateada = hora.length() > 5 ? hora.substring(0, 5) : hora;
    
    String idCita = generarIdCita();
    System.out.println("🔑 ID de cita generado: " + idCita);
    
    String sql = "INSERT INTO CITA (idCita, fecha, hora, numeroSeguro, idMedico, estatus) VALUES (?, ?, ?, ?, ?, 'Activa')";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        // Obtener numeroSeguro del paciente
        String obtenerPacienteSQL = "SELECT numeroSeguro FROM PACIENTE WHERE Correo = ?";
        PreparedStatement psPaciente = con.prepareStatement(obtenerPacienteSQL);
        psPaciente.setString(1, usuarioId);
        ResultSet rs = psPaciente.executeQuery();
        
        if (rs.next()) {
            String numeroSeguro = rs.getString("numeroSeguro");
            
            ps.setString(1, idCita);
            ps.setString(2, fecha);
            ps.setString(3, horaFormateada);
            ps.setString(4, numeroSeguro);
            ps.setInt(5, Integer.parseInt(idMedico));
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Cita agendada exitosamente - ID: " + idCita);
                // Recargar horarios
                if (fechaEsValida()) {
                    filtrarPorFecha();
                } else {
                    cargarHorasDisponibles();
                }
            }
       } else {
            JOptionPane.showMessageDialog(this, "❌ No se pudo encontrar el paciente");
        }
        
    } catch (SQLException e) {
        // ✅ MEJORAR manejo de errores
        if (e.getMessage().contains("duplicate key") || e.getMessage().contains("PRIMARY KEY")) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error: ID de cita duplicado. Intente nuevamente.\n" +
                "ID generado: " + idCita);
        } else {
            JOptionPane.showMessageDialog(this, "Error al agendar cita: " + e.getMessage());
        }
    }
}
    
private String generarIdCita() {
    // Método simple y confiable - siempre usa formato C + número secuencial
    String sql = "SELECT idCita FROM CITA WHERE idCita LIKE 'C%' ORDER BY idCita";
    
    try (PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        int maxC = 0;
        
        // Buscar manualmente el máximo número después de "C"
        while (rs.next()) {
            String idActual = rs.getString("idCita");
            if (idActual != null && idActual.startsWith("C") && idActual.length() > 1) {
                try {
                    String numeroStr = idActual.substring(1); // Quitar la "C"
                    int numero = Integer.parseInt(numeroStr);
                    if (numero > maxC) {
                        maxC = numero;
                    }
                } catch (NumberFormatException e) {
                    // Ignorar IDs que no tengan número válido después de C
                }
            }
        }
        
        // Si no encontró ningún C válido, empezar desde C1
        if (maxC == 0) {
            return "C1";
        }
        
        // Generar siguiente ID: C8, C9, C10, etc.
        return "C" + (maxC + 1);
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al generar ID: " + e.getMessage());
        
        // Fallback de emergencia - muy poco probable que se duplique
        long timestamp = System.currentTimeMillis();
        return "E" + (timestamp % 10000); // E + últimos 4 dígitos del timestamp
    }
}
   

private void filtrarPorFecha() {
    if(cmbMes.getSelectedIndex() == 0 || cmbDia.getSelectedIndex() == 0){
        return;
    }
    int año = LocalDate.now().getYear();
    String mes = String.format("%02d", cmbMes.getSelectedIndex());
    String dia = String.format("%02d", Integer.parseInt(cmbDia.getSelectedItem().toString()));
    String fecha = año + "-" + mes + "-" + dia;

    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("Fecha");
    modelo.addColumn("Hora Disponible");
    modelo.addColumn("Estatus");

    // ✅ CORREGIR esta consulta - falta el estatus
    String sql = """
    WITH Horarios AS (
        SELECT CAST('08:00' AS time) AS hora UNION ALL
        SELECT '09:00' UNION ALL
        SELECT '10:00' UNION ALL
        SELECT '11:00' UNION ALL
        SELECT '12:00' UNION ALL
        SELECT '13:00' UNION ALL
        SELECT '14:00' UNION ALL
        SELECT '15:00'
    )
    SELECT 
        ? as fecha,
        CONVERT(varchar(5), h.hora, 108) AS hora,
        CASE 
            WHEN EXISTS (
                SELECT 1 FROM CITA c
                WHERE c.idMedico = ?
                AND CONVERT(varchar(10), c.fecha, 120) = ?
                AND CONVERT(varchar(5), c.hora, 108) = CONVERT(varchar(5), h.hora, 108)
                AND c.estatus IN ('Activa','Modificado')
            ) THEN 'Ocupado'
            ELSE 'Disponible'
        END AS estatus
    FROM Horarios h
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, fecha);
        ps.setInt(2, Integer.parseInt(idMedico));
        ps.setString(3, fecha);

        ResultSet rs = ps.executeQuery();
        boolean hayDatos = false;

        while (rs.next()) {
            modelo.addRow(new Object[]{
                fecha,
                rs.getString("hora"),
                rs.getString("estatus")  // ✅ AGREGAR el estatus
            });
            hayDatos = true;
        }

        tblHoras.setModel(modelo);

        if(!hayDatos){
            JOptionPane.showMessageDialog(this, "❌ No hay horarios disponibles en esa fecha.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}


private boolean fechaEsValida() {
    if(cmbMes.getSelectedIndex() == 0 || cmbDia.getSelectedIndex() == 0)
        return false;

    int año = LocalDate.now().getYear();
    int mes = cmbMes.getSelectedIndex();
    int dia = Integer.parseInt(cmbDia.getSelectedItem().toString());

    LocalDate hoy = LocalDate.now();
    LocalDate fechaSeleccionada;

    try {
        fechaSeleccionada = LocalDate.of(año, mes, dia);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "❌ Fecha inválida.");
        return false;
    }

    if (fechaSeleccionada.isBefore(hoy)) {
        JOptionPane.showMessageDialog(this, "⚠️ No puedes seleccionar una fecha pasada.");
        return false;
    }

    return true;
}


/*private void cargarHorasDisponibles() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("Fecha");
    modelo.addColumn("Hora Disponible");
    modelo.addColumn("Estatus");

    String sql = """
    WITH Horarios AS (
        SELECT CAST('08:00' AS time) AS hora UNION ALL
        SELECT '09:00' UNION ALL
        SELECT '10:00' UNION ALL
        SELECT '11:00' UNION ALL
        SELECT '12:00' UNION ALL
        SELECT '13:00' UNION ALL
        SELECT '14:00' UNION ALL
        SELECT '15:00'
    ),
    Fechas AS (
        SELECT TOP 30 DATEADD(DAY, ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) - 1,
        CAST(GETDATE() AS date)) AS fecha
        FROM sys.objects
    )
    SELECT f.fecha, CONVERT(varchar(8), h.hora, 108) AS hora
    FROM Fechas f
    CROSS JOIN Horarios h
    WHERE NOT EXISTS (
        SELECT 1 FROM CITA c
        WHERE c.idMedico = ?
        AND c.fecha = f.fecha
        AND CONVERT(varchar(5), c.hora, 108) = CONVERT(varchar(5), h.hora, 108)
        AND c.estatus IN ('Activa','Modificado')
    )
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, Integer.parseInt(idMedico));
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getDate("fecha"),
                rs.getString("hora")
            });
        }

        tblHoras.setModel(modelo);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}*/

private void cargarHorasDisponibles() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("Fecha");
    modelo.addColumn("Hora Disponible");
    modelo.addColumn("Estatus");

    String sql = """
    WITH Horarios AS (
        SELECT CAST('08:00' AS time) AS hora UNION ALL
        SELECT '09:00' UNION ALL
        SELECT '10:00' UNION ALL
        SELECT '11:00' UNION ALL
        SELECT '12:00' UNION ALL
        SELECT '13:00' UNION ALL
        SELECT '14:00' UNION ALL
        SELECT '15:00'
    ),
    Fechas AS (
        SELECT TOP 30 DATEADD(DAY, ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) - 1,
        CAST(GETDATE() AS date)) AS fecha
        FROM sys.objects
    )
    SELECT 
        f.fecha, 
        CONVERT(varchar(8), h.hora, 108) AS hora,
        CASE 
            WHEN EXISTS (
                SELECT 1 FROM CITA c
                WHERE c.idMedico = ?
                AND c.fecha = f.fecha
                AND CONVERT(varchar(5), c.hora, 108) = CONVERT(varchar(5), h.hora, 108)
                AND c.estatus IN ('Activa','Modificado')
            ) THEN 'Ocupado'
            WHEN EXISTS (
                SELECT 1 FROM CITA c
                WHERE c.idMedico = ?
                AND c.fecha = f.fecha
                AND CONVERT(varchar(5), c.hora, 108) = CONVERT(varchar(5), h.hora, 108)
                AND c.estatus = 'Cancelado'
            ) THEN 'Disponible'
            ELSE 'Disponible'
        END AS estatus
    FROM Fechas f
    CROSS JOIN Horarios h
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, Integer.parseInt(idMedico));
        ps.setInt(2, Integer.parseInt(idMedico));
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getDate("fecha"),
                rs.getString("hora"),
                rs.getString("estatus")
            });
        }

        tblHoras.setModel(modelo);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

private void limpiarTabla() {
    DefaultTableModel modelo = (DefaultTableModel) tblHoras.getModel();
    modelo.setRowCount(0);
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
        tblHoras = new javax.swing.JTable();
        cmbMes = new javax.swing.JComboBox<>();
        cmbDia = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        reloj11 = new Reloj1();
        jLabel1 = new javax.swing.JLabel();
        txtEspecialidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        lblSalir.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblSalir.setText("Regresar");
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lblInicio))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(lblSalir)))
                .addContainerGap(176, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(158, 158, 158)
                .addComponent(lblInicio)
                .addGap(155, 155, 155)
                .addComponent(lblSalir)
                .addContainerGap(548, Short.MAX_VALUE))
        );

        tblHoras.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblHoras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Fecha", "Hora Disponibles", "Estatus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblHoras);

        cmbMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mes...", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cmbMes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMesItemStateChanged(evt);
            }
        });

        cmbDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dia...", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        cmbDia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDiaItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/citaM.png"))); // NOI18N
        jLabel3.setText("Horarios Disponibles Medicos");

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");

        lblBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar (1).png"))); // NOI18N
        lblBuscar.setText("Nombre del medico:");

        btnLimpiar.setText("Refresh");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Especialidad:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Seleccionar Fecha:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(384, 384, 384))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(223, 223, 223)
                                .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblFecha)
                                .addGap(38, 38, 38))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(btnLimpiar)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(btnLimpiar))
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
       m.setRowCount(0);
    cmbMes.setSelectedIndex(0);
    cmbDia.setSelectedIndex(0);
    cargarHorasDisponibles(); 
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void cmbDiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDiaItemStateChanged
    if (evt.getStateChange() == ItemEvent.SELECTED) {
        if (fechaEsValida()) {
            filtrarPorFecha();
        } else {
            limpiarTabla();
        }
    }
    }//GEN-LAST:event_cmbDiaItemStateChanged

    private void cmbMesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMesItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
        if (fechaEsValida()) {
            filtrarPorFecha();
        } else {
            limpiarTabla();
        }
    }
    }//GEN-LAST:event_cmbMesItemStateChanged

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
       VentanaPaciente vp = new VentanaPaciente(usuarioId);
       vp.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        ventanaConsultarMedicoHorario v =   new ventanaConsultarMedicoHorario(usuarioId);
        v.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

 
    
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
                //ventanaCitasMed vcm = new ventanaCitasMed(usuarioId);
                //vcm.setVisible(true);
            }
        });
    }
    private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private Reloj1 reloj11;
    private javax.swing.JTable tblHoras;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}