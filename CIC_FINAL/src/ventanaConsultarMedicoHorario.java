import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ventanaConsultarMedicoHorario extends javax.swing.JFrame {

    private static String usuarioId;
    private static Statement stmt;
    public static Connection con;

    public ventanaConsultarMedicoHorario(String usuarioId) {//String usuarioId
        this.setTitle("Citas Paciente");
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        con = ConexionSQL.ConexionSQLServer();
        m = (DefaultTableModel) tblCita.getModel();
        lblFecha.setText(obtenerFecha());
        cargarMedicosFrecuentes();
        cargarTiposMedicos();  
        
       
        cmbTipos.addItemListener(evt -> {
         if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            filtrarPorTipo();
         }
       });
        cmbEspecialidad.addItemListener(evt -> {
            if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                mostrarEspecialidad();
            }
        });
        cmbMes.addItemListener(evt -> {
            if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                mostrarMedicoSeleccionado(); // recarga la tabla principal
                //cargarOtrosMedicos(); // recarga la tabla de otros médicos
            }
        });
        cmbDia.addItemListener(evt -> {
            if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                mostrarMedicoSeleccionado();
              //  cargarOtrosMedicos();
            }
        });

        tblCita.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int fila = tblCita.getSelectedRow();

        if (fila >= 0) {
            String idMedico = tblCita.getValueAt(fila, 0).toString();
            String nombreMed = tblCita.getValueAt(fila, 1).toString();
            String especialidad = tblCita.getValueAt(fila, 2).toString();

            // abrir ventana
            new ventanaHoraDisponible(usuarioId, idMedico, nombreMed, especialidad).setVisible(true);
            dispose();
        }
    }
});       
        
    txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
    @Override
    public void keyReleased(java.awt.event.KeyEvent evt) {
          String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            mostrarTodosLosMedicos();  
        } else {
            buscarMedico(texto);  
        }
    }
});

    }// Fin del constructor
    
    private void cargarTiposMedicos() {
    // Remover temporalmente el listener para evitar el bucle
   // cmbTipos.removeItemListener(cmbTipos.getItemListeners()[0]);
    for (ItemListener l : cmbTipos.getItemListeners()) {
    cmbTipos.removeItemListener(l);
}

    cmbTipos.removeAllItems();
    cmbTipos.addItem("Seleccionar");
    cmbTipos.addItem("Todos los médicos");
    cmbTipos.addItem("Médicos frecuentes");
    cmbTipos.addItem("Otros médicos");
    
    // Restaurar el listener
    cmbTipos.addItemListener(evt -> {
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            filtrarPorTipo();
        }
    });
}
    private void buscarPorFecha() {
    // Solo filtrar por fecha si está seleccionado "Todos los médicos"
    if (cmbTipos.getSelectedItem() != null && 
        !"Todos los médicos".equals(cmbTipos.getSelectedItem().toString())) {
        return;
    }
    
    int nDia = cmbDia.getSelectedIndex();    // 1–31  (0 = Dia...)
    int nMes = cmbMes.getSelectedIndex();    // 1–12 (0 = Mes...)

    // Validación: Si mes o día están sin seleccionar, no filtrar
    if (nMes == 0 || nDia == 0) {
        return;
    }

    String fechaSeleccionada = obtenerFechaSeleccionada();
    
    // Si no hay fecha válida, salir
    if (fechaSeleccionada == null) {
        return;
    }

    String sql = """
        SELECT m.idMedico, m.nombreMed, m.Especialidad
        FROM MEDICO m
        ORDER BY m.idMedico
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        m.setRowCount(0);

        while (rs.next()) {
            String idMedico = rs.getString("idMedico");
            String nombre = rs.getString("nombreMed");
            String especialidad = rs.getString("Especialidad");

            // Obtener estatus del médico según la fecha seleccionada
            //String estatus = obtenerEstatusMedicoPorFecha(idMedico, fechaSeleccionada);

            // Agregar fila
            m.addRow(new Object[]{
                idMedico,
                nombre,
                especialidad,
                fechaSeleccionada,
                //estatus
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Error al filtrar por fecha: " + e.getMessage());
    }
}
    
    
    private void filtrarPorTipo() {
    if (cmbTipos.getSelectedItem() == null) {
        return;
    }
    
    String tipoSeleccionado = cmbTipos.getSelectedItem().toString();
    
    switch (tipoSeleccionado) {
        case "Seleccionar":
            m.setRowCount(0);
            break;
        case "Todos los médicos":
            mostrarTodosLosMedicos();
            break;
        case "Médicos frecuentes":
            mostrarMedicosFrecuentes();
            break;
        case "Otros médicos":
            mostrarOtrosMedicos();
            break;
    }
}
    private void mostrarTodosLosMedicos() {
    System.out.println("🔍 EJECUTANDO mostrarTodosLosMedicos()");
    String fechaSelec = obtenerFechaSeleccionada(); 
    if (fechaSelec == null) {
        fechaSelec = obtenerFechaActual();
    }
    
    String sql = """   
        SELECT idMedico, nombreMed, Especialidad
        FROM MEDICO
        ORDER BY idMedico
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        m.setRowCount(0);

        while (rs.next()) {
            String idMedico = rs.getString("idMedico");
            m.addRow(new Object[]{
                idMedico,
                rs.getString("nombreMed"),
                rs.getString("Especialidad"),
                fechaSelec,
                //obtenerEstatusMedicoPorFecha(idMedico, fechaSelec)
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Error mostrando todos los médicos: " + e.getMessage());
    }
}
    
   private void mostrarMedicosFrecuentes() {
    String fechaSelec = obtenerFechaSeleccionada(); 
    if (fechaSelec == null) {
        fechaSelec = obtenerFechaActual();
    }
    
    String sql = """
        SELECT DISTINCT m.idMedico, m.nombreMed, m.Especialidad
        FROM MEDICO m
        INNER JOIN CITA c ON m.idMedico = c.idMedico
        INNER JOIN PACIENTE p ON c.numeroSeguro = p.numeroSeguro
        WHERE p.Correo = ?
        ORDER BY m.nombreMed
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, usuarioId);
        ResultSet rs = ps.executeQuery();
        m.setRowCount(0);

        while (rs.next()) {
            String idMedico = rs.getString("idMedico");
            m.addRow(new Object[]{
                idMedico,
                rs.getString("nombreMed"),
                rs.getString("Especialidad"),
                fechaSelec,
                //obtenerEstatusMedicoPorFecha(idMedico, fechaSelec)
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Error mostrando médicos frecuentes: " + e.getMessage());
    }
}
   
   private void mostrarOtrosMedicos() {
    String fechaSelec = obtenerFechaSeleccionada(); 
    if (fechaSelec == null) {
        fechaSelec = obtenerFechaActual();
    }
    
    String sql = """
        SELECT m.idMedico, m.nombreMed, m.Especialidad
        FROM MEDICO m
        WHERE m.idMedico NOT IN (
            SELECT DISTINCT c.idMedico
            FROM CITA c
            INNER JOIN PACIENTE p ON p.numeroSeguro = c.numeroSeguro
            WHERE p.Correo = ?
        )
        ORDER BY m.nombreMed
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, usuarioId);
        ResultSet rs = ps.executeQuery();
        m.setRowCount(0);

        while (rs.next()) {
            String idMedico = rs.getString("idMedico");
            m.addRow(new Object[]{
                idMedico,
                rs.getString("nombreMed"),
                rs.getString("Especialidad"),
                fechaSelec,
                //obtenerEstatusMedicoPorFecha(idMedico, fechaSelec)
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Error mostrando otros médicos: " + e.getMessage());
    }
}
   
    
    private void mostrarMedicoSeleccionado() {
    if (cmbEspecialidad.getSelectedItem() == null) {
        return;
    }
    
    String especialidadSeleccionada = cmbEspecialidad.getSelectedItem().toString();

    if (especialidadSeleccionada.equals("Seleccionar")) {
        txtBuscar.setText("");
        m.setRowCount(0);
        return;
    }

    // Obtener fecha seleccionada o actual
    String fechaSelec = obtenerFechaSeleccionada(); 
    if (fechaSelec == null) {
        fechaSelec = obtenerFechaActual();
    }
    
    String sql = """   
        SELECT idMedico, nombreMed, Especialidad
        FROM MEDICO
        WHERE Especialidad = ?
        ORDER BY nombreMed
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, especialidadSeleccionada);
        ResultSet rs = ps.executeQuery();

        m.setRowCount(0); // limpia tabla

        while (rs.next()) {
            String idMedico = rs.getString("idMedico");
            String nombreMedico = rs.getString("nombreMed");
            String especialidad = rs.getString("Especialidad");
           // String estatus = obtenerEstatusMedicoPorFecha(idMedico, fechaSelec);
            
            // Agregar fila con todas las columnas
            m.addRow(new Object[]{
                idMedico,
                nombreMedico,
                especialidad,
                fechaSelec // fecha formateada
               // estatus  // Disponible u Ocupado
            });
        }

        // Si no se encontraron médicos, mostrar mensaje
        if (m.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "No se encontraron médicos para la especialidad: " + especialidadSeleccionada);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Error mostrando médicos: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    

    
    /*private String obtenerEstatusMedicoPorFecha(String idMedico, String fecha) {

    String sql = """
        SELECT COUNT(*) AS total
        FROM CITA
        WHERE idMedico = ?
        AND fecha = ?
        AND estatus IN ('Activa','Modificado')
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, Integer.parseInt(idMedico));
        ps.setString(2, fecha);
        ResultSet rs = ps.executeQuery();

        if (rs.next() && rs.getInt("total") >= 8) {
            return "Ocupado";
        }

    } catch (SQLException e) {
        return "Desconocido";
    }

    return "Disponible";
}*/

    
    private String obtenerFechaActual() {
    LocalDate fecha = LocalDate.now();
    // Formato consistente: YYYY-MM-DD
    return String.format("%d-%02d-%02d", 
        fecha.getYear(), 
        fecha.getMonthValue(), 
        fecha.getDayOfMonth());
}
    
   
   /* private String obtenerEstatusMedicoHoy(String idMedico) {
    String sql = """
        SELECT COUNT(*) AS total
        FROM CITA
        WHERE idMedico = ?
        AND fecha = CAST(GETDATE() AS date)
        AND estatus IN ('Activa','Modificado')
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, Integer.parseInt(idMedico));
        ResultSet rs = ps.executeQuery();

        if (rs.next() && rs.getInt("total") >= 8) { // 8 horas laborales
            return "Ocupado";
        }
    } catch (SQLException e) {
        return "Desconocido";
    }

    return "Disponible";
}*/

  private String obtenerEstatusMedicoPorFecha(String idMedico, String fecha) {
    try {
        LocalDate fechaConsulta = LocalDate.parse(fecha);
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();
        
        // Si la fecha es pasada (no hoy), siempre mostrar "No Disponible"
        if (fechaConsulta.isBefore(hoy)) {
            return "No Disponible";
        }
        
        // Si la fecha es futura (después de hoy), verificar citas agendadas
        if (fechaConsulta.isAfter(hoy)) {
            return verificarCitasFuturas(idMedico, fecha);
        }
        
        // Si es HOY - verificar en tiempo real
        return verificarEstatusHoy(idMedico, fecha, ahora);
        
    } catch (Exception e) {
        return "Desconocido";
    }
}

private String verificarCitasFuturas(String idMedico, String fecha) {
    String sql = """
        SELECT COUNT(*) as total
        FROM CITA 
        WHERE idMedico = ? 
        AND fecha = ?
        AND estatus IN ('Activa','Modificado')
    """;
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, Integer.parseInt(idMedico));
        ps.setString(2, fecha);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            int totalCitas = rs.getInt("total");
            if (totalCitas >= 8) {
                return "Ocupado";
            } else if (totalCitas > 0) {
                return "Parcialmente Ocupado";
            } else {
                return "Disponible";
            }
        }
    } catch (SQLException e) {
        // Log error
    }
    return "Disponible";
}

private String verificarEstatusHoy(String idMedico, String fecha, LocalTime ahora) {
    String sql = """
        SELECT hora, estatus 
        FROM CITA 
        WHERE idMedico = ? 
        AND fecha = ? 
        AND estatus IN ('Activa','Modificado')
        ORDER BY hora
    """;
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, Integer.parseInt(idMedico));
        ps.setString(2, fecha);
        ResultSet rs = ps.executeQuery();
        
        boolean tieneCitaAhora = false;
        boolean tieneCitasPendientes = false;
        int totalCitas = 0;
        
        while (rs.next()) {
            totalCitas++;
            String horaCita = rs.getString("hora");
            LocalTime hora = LocalTime.parse(horaCita);
            
            // Verificar si el médico tiene cita EN ESTE MOMENTO (rango de 1 hora)
            if (!tieneCitaAhora) {
                tieneCitaAhora = estaEnRangoDeCita(ahora, hora);
            }
            
            // Verificar si tiene citas PENDIENTES para hoy
            if (!tieneCitasPendientes && hora.isAfter(ahora)) {
                tieneCitasPendientes = true;
            }
        }
        
        // Lógica de estatus para HOY
        if (tieneCitaAhora) {
            return "Ocupado (En consulta)";
        } else if (tieneCitasPendientes) {
            return "Parcialmente Ocupado";
        } else if (totalCitas >= 8) {
            return "Ocupado";
        } else {
            return "Disponible";
        }
        
    } catch (SQLException e) {
        return "Desconocido";
    }
}

private boolean estaEnRangoDeCita(LocalTime ahora, LocalTime horaCita) {
    // Considerar que una cita dura aproximadamente 1 hora
    LocalTime inicioCita = horaCita;
    LocalTime finCita = horaCita.plusHours(1);
    
    return (ahora.isAfter(inicioCita) || ahora.equals(inicioCita)) && 
           ahora.isBefore(finCita);
}  
    
public void actualizarEstatusDespuesDeCita() {
    System.out.println("🔄 Actualizando estatus después de agendar cita...");
    
    if (cmbTipos.getSelectedItem() != null) {
        filtrarPorTipo();
    } else {
        mostrarTodosLosMedicos();
    }
}
   private void mostrarEspecialidad() {
    if (cmbEspecialidad.getSelectedItem() == null) {
        return;
    }

    String especialidadSeleccionada = cmbEspecialidad.getSelectedItem().toString();

    if (especialidadSeleccionada.equals("Seleccionar")) {
        txtBuscar.setText("");
        m.setRowCount(0); // limpia tabla
        return;
    }

    // Consultar el primer médico de esta especialidad para mostrar en txtBuscar
    String sql = "SELECT nombreMed FROM MEDICO WHERE Especialidad = ? ORDER BY nombreMed";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, especialidadSeleccionada);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // Mostrar el nombre del primer médico en txtBuscar
            txtBuscar.setText(rs.getString("nombreMed"));
        } else {
            txtBuscar.setText("No hay médicos en " + especialidadSeleccionada);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Error obteniendo médico: " + e.getMessage());
        txtBuscar.setText("");
    }

    // Mostrar los médicos de esa especialidad en la tabla
    mostrarMedicoSeleccionado();
}

private void cargarMedicosFrecuentes() {
    cmbEspecialidad.removeAllItems();
    cmbEspecialidad.addItem("Seleccionar");

    String sql = """
        SELECT m.Especialidad
        FROM MEDICO m 
        ORDER BY m.Especialidad
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        //ps.setString(1, usuarioId); // correo del paciente logueado
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            cmbEspecialidad.addItem(
                rs.getString("Especialidad")
            );
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Error cargando médicos frecuentes: " + e.getMessage());
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCita = new javax.swing.JTable();
        cmbMes = new javax.swing.JComboBox<>();
        cmbDia = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        reloj11 = new Reloj1();
        cmbEspecialidad = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbTipos = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();

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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblInicio)
                    .addComponent(lblSalir))
                .addContainerGap(190, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(158, 158, 158)
                .addComponent(lblInicio)
                .addGap(166, 166, 166)
                .addComponent(lblSalir)
                .addContainerGap(549, Short.MAX_VALUE))
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
                "ID Medico", "Nombre Medico", "Especialidad", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCita);

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
        jLabel3.setText("Medicos ");

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");

        lblBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar (1).png"))); // NOI18N
        lblBuscar.setText("Buscar Nombre Medico:");

        btnLimpiar.setText("Refresh");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        cmbEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar" }));
        cmbEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEspecialidadActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Especialidad:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Seleccionar Fecha:");

        cmbTipos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar" }));
        cmbTipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTiposActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Tipo:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFecha)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblBuscar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 342, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)))))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbTipos, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(cmbTipos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar))
                .addGap(102, 102, 102)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2))
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
        cmbTipos.setSelectedIndex(0); 
        if (cmbTipos.getItemCount() > 0) {
        cmbTipos.setSelectedIndex(0); 
    }
    // 4. Limpiar el combo de especialidad
    if (cmbEspecialidad.getItemCount() > 0) {
        cmbEspecialidad.setSelectedIndex(0); 
    }
    // 5. Limpiar el campo de búsqueda
    txtBuscar.setText("");
    // 6. Mostrar TODOS los médicos en la tabla
    mostrarTodosLosMedicos();
    
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void cmbDiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDiaItemStateChanged
buscarPorFecha();

        
    }//GEN-LAST:event_cmbDiaItemStateChanged

    private void cmbMesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMesItemStateChanged
 buscarPorFecha();

        
    }//GEN-LAST:event_cmbMesItemStateChanged

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        VentanaPaciente vp = new VentanaPaciente(usuarioId);
        vp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblSalirMouseClicked

    private void cmbEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEspecialidadActionPerformed
    mostrarEspecialidad();
    }//GEN-LAST:event_cmbEspecialidadActionPerformed

    private void cmbTiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTiposActionPerformed
        filtrarPorTipo();
    }//GEN-LAST:event_cmbTiposActionPerformed

private void buscarMedico(String nombre) {
    String busqueda = nombre.trim();
    
    // Si la búsqueda está vacía, no hacer nada
    if (busqueda.isEmpty()) {
        return;
    }

    // Obtener fecha seleccionada o actual
    String fechaSelec = obtenerFechaSeleccionada(); 
    if (fechaSelec == null) {
        fechaSelec = obtenerFechaActual();
    }

    // Usar PreparedStatement para evitar SQL injection
    String sql = """
        SELECT m.idMedico, m.nombreMed, m.Especialidad
        FROM MEDICO m
        WHERE m.nombreMed LIKE ?
        ORDER BY m.nombreMed
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, busqueda + "%");
        ResultSet rs = ps.executeQuery();
        
        m.setRowCount(0); 

        int contador = 0;
        while (rs.next()) {
            contador++;
            String idMedico = rs.getString("idMedico");
            String nombreMedico = rs.getString("nombreMed");
            String especialidad = rs.getString("Especialidad");
            
            // Obtener estatus del médico según la fecha
           // String estatus = obtenerEstatusMedicoPorFecha(idMedico, fechaSelec);
            
            // Agregar fila con todas las columnas (5 columnas)
            m.addRow(new Object[]{
                idMedico,
                nombreMedico,
                especialidad,
                fechaSelec,
                //estatus
            });
        }

        // Mostrar mensaje si no se encontraron resultados
        if (contador == 0) {
            JOptionPane.showMessageDialog(this,
                "No se encontraron médicos con el nombre: " + busqueda,
                "Búsqueda sin resultados",
                JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
            "Error al buscar médico: " + ex.getMessage(),
            "Error de base de datos",
            JOptionPane.ERROR_MESSAGE);
        System.out.println(ex.getMessage());
    }
}
  
    private String obtenerFecha() {
        String fecha = "";
        LocalDateTime hoy = LocalDateTime.now();
        fecha = hoy.getMonthValue() + "/" + hoy.getDayOfMonth() + "/" + hoy.getYear();

        return fecha;
    }

    private String obtenerFechaSeleccionada() {
    if (cmbMes.getSelectedIndex() == 0 || cmbDia.getSelectedIndex() == 0) {
        return null;
    }
    
    int añoActual = LocalDate.now().getYear();
    int mes = cmbMes.getSelectedIndex(); // Enero=1
    int dia = Integer.parseInt(cmbDia.getSelectedItem().toString());
    
    // Validar si la fecha es válida
    try {
        LocalDate fechaSeleccionada = LocalDate.of(añoActual, mes, dia);
        LocalDate fechaActual = LocalDate.now();
        
        // Si la fecha es pasada, usar fecha actual
        if (fechaSeleccionada.isBefore(fechaActual)) {
            JOptionPane.showMessageDialog(this, 
                "No se pueden seleccionar fechas pasadas. Se usará la fecha actual.",
                "Fecha inválida", 
                JOptionPane.WARNING_MESSAGE);
            return obtenerFechaActual();
        }
        
        return String.format("%d-%02d-%02d", añoActual, mes, dia);
    } catch (Exception e) {
        // Si la fecha no es válida (ej: 30 de febrero), usar fecha actual
        JOptionPane.showMessageDialog(this, 
            "Fecha inválida. Se usará la fecha actual.",
            "Fecha inválida", 
            JOptionPane.WARNING_MESSAGE);
        return obtenerFechaActual();
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
                ventanaCitasMed vcm = new ventanaCitasMed(usuarioId);
                vcm.setVisible(true);
            }
        });
    }
    private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JComboBox<String> cmbEspecialidad;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JComboBox<String> cmbTipos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
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