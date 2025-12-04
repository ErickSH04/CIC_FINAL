
import java.awt.event.ItemEvent;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Disponible extends javax.swing.JFrame {

    private static String usuarioId;
    private String idMedico;
    private String nombreMed;
    private String especialidad;
    private static Statement stmt;
    public static Connection con;
private static final int DURACION_CITA_MINUTOS = 60;
private String nssPacienteValidado = null;

    public Disponible(String usuarioId) {//String usuarioId
        this.setTitle("Citas Paciente");
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        con = ConexionSQL.ConexionSQLServer();
        m = (DefaultTableModel) tblHoras.getModel();
        txtEspecialidad.setEditable(false); 
        configurarValidacionPaciente();
    }
    public Disponible(String usuarioId, String idMedico,
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
         configurarValidacionPaciente();
        cargarHorasDisponibles();
  // MouseListener modificado para usar validaciones mejoradas
        tblHoras.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tblHoras.getSelectedRow();
                
                if (fila >= 0) {
                    String estatus = tblHoras.getValueAt(fila, 2).toString();
                    String fecha = tblHoras.getValueAt(fila, 0).toString();
                    String hora = tblHoras.getValueAt(fila, 1).toString();
                    
                    if ("Pasada".equals(estatus)) {
                        mostrarMensajeHorarioPasado(fecha, hora);
                    } else if ("Disponible".equals(estatus)) {
                        mostrarConfirmacionCita(fecha, hora);
                    } else {
                        mostrarInformacionHorarioOcupado(fecha, hora);
                    }
                }
            }
        });

configurarColoresTabla();
    }// Fin de constructor
    
    // Método para configurar colores en la tabla
private void configurarColoresTabla() {
    // Crear el renderer personalizado
    EstatusColorRenderer renderer = new EstatusColorRenderer();
    
    // Aplicar el renderer a todas las columnas
    for (int i = 0; i < tblHoras.getColumnCount(); i++) {
        tblHoras.getColumnModel().getColumn(i).setCellRenderer(renderer);
    }
    
    // Configurar propiedades visuales de la tabla
    tblHoras.setRowHeight(30); // Aumentar altura de filas
    tblHoras.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 16));
    tblHoras.getTableHeader().setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 16));
    tblHoras.getTableHeader().setBackground(new java.awt.Color(136, 212, 234));
    tblHoras.setShowGrid(true);
    tblHoras.setGridColor(java.awt.Color.LIGHT_GRAY);
    
    // Hacer la tabla no editable
    tblHoras.setDefaultEditor(Object.class, null);
}
      private void mostrarMensajeHorarioPasado(String fecha, String hora) {
        String[] fechaPartes = fecha.split("-");
        String mesNumero = fechaPartes[1];
        String dia = fechaPartes[2];
        
        String nombreMes = obtenerNombreMes(Integer.parseInt(mesNumero) - 1);
        
        String mensajePasado = String.format(
            "⚠️ Horario no disponible\n\n" +
            "👨‍⚕️  Médico: %s\n" +
            "🏥  Especialidad: %s\n" +
            "📅  Día: %s\n" +
            "🗓️  Mes: %s\n" +
            "⏰  Hora: %s\n\n" +
            "No puedes agendar una cita en un horario que ya pasó.",
            nombreMed,
            especialidad,
            dia,
            nombreMes,
            hora
        );
        
        JOptionPane.showMessageDialog(
            this,
            mensajePasado,
            "Horario no disponible",
            JOptionPane.WARNING_MESSAGE
        );
    }
     private void mostrarConfirmacionCita(String fecha, String hora) {
        String[] fechaPartes = fecha.split("-");
        String mesNumero = fechaPartes[1];
        String dia = fechaPartes[2];
        
        String nombreMes = obtenerNombreMes(Integer.parseInt(mesNumero) - 1);
        
        String mensaje = String.format(
            "📋 Información de la cita:\n\n" +
            "👨‍⚕️  Médico: %s\n" +
            "🏥  Especialidad: %s\n" +
            "📅  Día: %s\n" +
            "🗓️  Mes: %s\n" +
            "⏰  Hora: %s\n" +
            "⏱️  Duración: %d minutos\n\n" +
            "¿Desea agendar esta cita?",
            nombreMed,
            especialidad,
            dia,
            nombreMes,
            hora,
            DURACION_CITA_MINUTOS
        );
        
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            mensaje,
            "Confirmar cita",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (respuesta == JOptionPane.YES_OPTION) {
            agendarCitaConValidaciones(fecha, hora);
        }
    }
      private void mostrarInformacionHorarioOcupado(String fecha, String hora) {
        String[] fechaPartes = fecha.split("-");
        String mesNumero = fechaPartes[1];
        String dia = fechaPartes[2];
        
        String nombreMes = obtenerNombreMes(Integer.parseInt(mesNumero) - 1);
        
        String mensajeOcupado = String.format(
            "⚠️ Horario no disponible\n\n" +
            "👨‍⚕️  Médico: %s\n" +
            "🏥  Especialidad: %s\n" +
            "📅  Día: %s\n" +
            "🗓️  Mes: %s\n" +
            "⏰  Hora: %s\n\n" +
            "Este horario ya está reservado.",
            nombreMed,
            especialidad,
            dia,
            nombreMes,
            hora
        );
        
        JOptionPane.showMessageDialog(
            this,
            mensajeOcupado,
            "Horario ocupado",
            JOptionPane.WARNING_MESSAGE
        );
    }
    
      // MÉTODO MEJORADO CON TODAS LAS VALIDACIONES
   private void agendarCitaConValidaciones(String fecha, String hora) {
    // 1. Validar NSS del paciente
    if (nssPacienteValidado == null || nssPacienteValidado.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "❌ No se pudo identificar al paciente.\n" +
            "Por favor verifique sus datos.",
            "Error de Paciente",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // 2. Validar si la hora ya pasó
    LocalDate fechaActual = LocalDate.now();
    LocalTime horaActual = LocalTime.now();
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate fechaCita = LocalDate.parse(fecha, formatter);
    
    String[] partesHora = hora.split(":");
    LocalTime horaCita = LocalTime.of(Integer.parseInt(partesHora[0]), 
                                     Integer.parseInt(partesHora[1]));
    
    if (fechaCita.isEqual(fechaActual) && horaCita.isBefore(horaActual)) {
        JOptionPane.showMessageDialog(this, 
            "❌ No puedes agendar una cita en un horario que ya pasó.\n" +
            "Fecha: " + fecha + "\n" +
            "Hora: " + hora,
            "Horario no disponible",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // 3. Verificar disponibilidad del médico (con duración)
    if (!verificarDisponibilidadMedico(idMedico, fecha, hora)) {
        return;
    }
    
    // 4. Verificar que el paciente no tenga otra cita a la misma hora
    if (!verificarDisponibilidadPaciente(nssPacienteValidado, fecha, hora)) {
    return;
}
    
    // 5. Proceder a agendar la cita
    agendarCitaFinal(fecha, hora, nssPacienteValidado);
}
      
   private boolean verificarDisponibilidadPaciente(String fecha, String hora) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        // Usar el NSS que ya obtuviste
        String nss = nssPacienteValidado;
        if (nss == null || nss.trim().isEmpty()) {
            return true; // Si no hay NSS, permitir continuar
        }
        
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            return true; // En caso de error de conexión, permitir continuar
        }
        
        // Consulta que verifica SOLAPAMIENTO de horarios (como en tu código ejemplo)
        String query = """
            SELECT COUNT(*) as citas_existentes,
                   (SELECT TOP 1 CONCAT(m.nombreMed, ' ', m.apellido1, ' - ', c.hora) 
                    FROM MEDICO m INNER JOIN CITA c ON m.idMedico = c.idMedico 
                    WHERE c.numeroSeguro = ? 
                    AND c.fecha = ? 
                    AND c.estatus IN ('Activa','Modificado')
                    AND ( 
                        (? BETWEEN c.hora AND DATEADD(MINUTE, ?, c.hora) 
                        OR DATEADD(MINUTE, ?, ?) BETWEEN c.hora AND DATEADD(MINUTE, ?, c.hora) 
                        OR c.hora BETWEEN ? AND DATEADD(MINUTE, ?, ?) 
                        )
                    )) as medico_hora 
            FROM CITA 
            WHERE numeroSeguro = ? 
            AND fecha = ? 
            AND estatus IN ('Activa','Modificado')
            AND ( 
                (? BETWEEN hora AND DATEADD(MINUTE, ?, hora) 
                OR DATEADD(MINUTE, ?, ?) BETWEEN hora AND DATEADD(MINUTE, ?, hora) 
                OR hora BETWEEN ? AND DATEADD(MINUTE, ?, ?) 
                )
            )
            """;

        pstmt = conn.prepareStatement(query);
        
        int paramIndex = 1;
        
        // Parámetros para la subconsulta (1-9)
        pstmt.setString(paramIndex++, nss);
        pstmt.setString(paramIndex++, fecha);
        pstmt.setString(paramIndex++, hora);
        pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
        pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
        pstmt.setString(paramIndex++, hora);
        pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
        pstmt.setString(paramIndex++, hora);
        pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
        pstmt.setString(paramIndex++, hora);
        
        // Parámetros para la consulta principal (10-18)
        pstmt.setString(paramIndex++, nss);
        pstmt.setString(paramIndex++, fecha);
        pstmt.setString(paramIndex++, hora);
        pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
        pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
        pstmt.setString(paramIndex++, hora);
        pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
        pstmt.setString(paramIndex++, hora);
        pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
        pstmt.setString(paramIndex++, hora);
        
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            int citasExistentes = rs.getInt("citas_existentes");
            String medicoHora = rs.getString("medico_hora");
            
            if (citasExistentes > 0) {
                JOptionPane.showMessageDialog(this,
                    "EL PACIENTE YA TIENE UNA CITA EN ESE HORARIO\n\n" +
                    "El paciente ya tiene una cita que se solapa:\n" +
                    "Fecha: " + fecha + "\n" +
                    "Hora solicitada: " + hora + " (Duración: " + DURACION_CITA_MINUTOS + " min)\n" +
                    "Conflicto con: " + (medicoHora != null ? medicoHora : "Otra cita") + "\n\n" +
                    "Un paciente no puede tener dos citas en horarios que se solapan.",
                    "Conflicto de Horario del Paciente",
                    JOptionPane.WARNING_MESSAGE
                );
                return false;
            }
        }
        return true;
        
    } catch (SQLException ex) {
        System.err.println("Error verificando disponibilidad del paciente: " + ex.getMessage());
        return true; // En caso de error, permitir continuar
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
  /* private boolean verificarDisponibilidadPacienteSimple(String nss, String fecha, String hora) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = ConexionSQL.ConexionSQLServer();
        if (conn == null) {
            return true; // En caso de error, permitir continuar
        }
        
        // Consulta simplificada - verifica si hay citas activas en la misma fecha y hora exacta
        String query = """
            SELECT COUNT(*) as citas_existentes
            FROM CITA 
            WHERE numeroSeguro = ? 
            AND fecha = ? 
            AND hora = ?
            AND estatus IN ('Activa','Modificado')
            """;

        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, nss);
        pstmt.setString(2, fecha);
        pstmt.setString(3, hora);
        
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            int citasExistentes = rs.getInt("citas_existentes");
            
            if (citasExistentes > 0) {
                JOptionPane.showMessageDialog(this,
                    "EL PACIENTE YA TIENE UNA CITA EN ESE HORARIO\n\n" +
                    "El paciente ya tiene una cita programada:\n" +
                    "Fecha: " + fecha + "\n" +
                    "Hora: " + hora + "\n\n" +
                    "Un paciente no puede tener dos citas en el mismo horario.",
                    "Conflicto de Horario del Paciente",
                    JOptionPane.WARNING_MESSAGE
                );
                return false;
            }
        }
        return true;
        
    } catch (SQLException ex) {
        System.err.println("Error verificando disponibilidad del paciente: " + ex.getMessage());
        return true; // En caso de error, permitir continuar
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}*/
      
    private String obtenerNombreMes(int index) {
        String[] nombresMeses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        return nombresMeses[index];
    }
private void configurarValidacionPaciente() {
        // Si necesitas validar paciente por nombre, aquí iría el código
        // Actualmente en Disponible se usa usuarioId (correo) para obtener NSS
        this.nssPacienteValidado = obtenerNSSPorCorreo();
    }
    
    private String obtenerNSSPorCorreo() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionSQL.ConexionSQLServer();
            String query = "SELECT numeroSeguro FROM PACIENTE WHERE Correo = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, usuarioId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("numeroSeguro");
            }
        } catch (SQLException ex) {
            System.err.println("Error obteniendo NSS: " + ex.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return null;
    }

 private boolean verificarDisponibilidadMedico(String idMedico, String fecha, String hora) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionSQL.ConexionSQLServer();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Error de conexión a la base de datos");
                return false;
            }
            
            // Consulta optimizada del segundo código
            String query = """
                SELECT COUNT(*) as citas_existentes,
                       (SELECT TOP 1 CONCAT(c.hora, ' - ', p.nombrePac, ' ', p.apellido1) 
                        FROM CITA c 
                        INNER JOIN PACIENTE p ON c.numeroSeguro = p.numeroSeguro 
                        WHERE c.idMedico = ? 
                        AND c.fecha = ? 
                        AND c.estatus IN ('Activa','Modificado')
                        AND (
                            (? BETWEEN c.hora AND DATEADD(MINUTE, ?, c.hora)
                            OR DATEADD(MINUTE, ?, ?) BETWEEN c.hora AND DATEADD(MINUTE, ?, c.hora)
                            OR c.hora BETWEEN ? AND DATEADD(MINUTE, ?, ?)
                            )
                        )) as cita_conflicto
                FROM CITA 
                WHERE idMedico = ? 
                AND fecha = ? 
                AND estatus IN ('Activa','Modificado')
                AND (
                    (? BETWEEN hora AND DATEADD(MINUTE, ?, hora)
                    OR DATEADD(MINUTE, ?, ?) BETWEEN hora AND DATEADD(MINUTE, ?, hora)
                    OR hora BETWEEN ? AND DATEADD(MINUTE, ?, ?)
                    )
                )
                """;

            pstmt = conn.prepareStatement(query);
            
            // Configurar parámetros
            int paramIndex = 1;
            
            // Para subconsulta
            pstmt.setString(paramIndex++, idMedico);
            pstmt.setString(paramIndex++, fecha);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            
            // Para consulta principal
            pstmt.setString(paramIndex++, idMedico);
            pstmt.setString(paramIndex++, fecha);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int citasExistentes = rs.getInt("citas_existentes");
                String citaConflicto = rs.getString("cita_conflicto");
                
                if (citasExistentes > 0) {
                    // Mostrar información detallada del conflicto
                    String mensaje = "CONFLICTO DE HORARIO\n\n" +
                                   "El Dr. " + nombreMed + " ya tiene una cita en ese rango horario.\n\n" +
                                   "Fecha: " + fecha + "\n" +
                                   "Hora solicitada: " + hora + " (Duración: " + DURACION_CITA_MINUTOS + " minutos)\n\n";
                    
                    if (citaConflicto != null) {
                        mensaje += "Conflicto con: " + citaConflicto + "\n\n";
                    }
                    
                    mensaje += "Las citas tienen una duración de " + DURACION_CITA_MINUTOS + 
                              " minutos. Por favor seleccione otro horario.";
                    
                    JOptionPane.showMessageDialog(this,
                        mensaje,
                        "Conflicto de Duración de Cita",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return false;
                }
            }
            return true;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al verificar disponibilidad: " + ex.getMessage(), 
                "Error de Base de Datos", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    
    
    
    
    private boolean verificarDisponibilidadPaciente(String nss, String fecha, String hora) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionSQL.ConexionSQLServer();
            if (conn == null) {
                return true; // En caso de error, permitir continuar
            }
            
            // Consulta del segundo código adaptada
            String query = """
                SELECT COUNT(*) as citas_existentes,
                       (SELECT TOP 1 CONCAT(m.nombreMed, ' ', m.apellido1, ' - ', c.hora) 
                        FROM MEDICO m INNER JOIN CITA c ON m.idMedico = c.idMedico 
                        WHERE c.numeroSeguro = ? AND c.fecha = ? 
                        AND c.estatus IN ('Activa','Modificado')
                        AND ( 
                            (? BETWEEN c.hora AND DATEADD(MINUTE, ?, c.hora) 
                            OR DATEADD(MINUTE, ?, ?) BETWEEN c.hora AND DATEADD(MINUTE, ?, c.hora) 
                            OR c.hora BETWEEN ? AND DATEADD(MINUTE, ?, ?) 
                            )
                        )) as medico_hora 
                FROM CITA 
                WHERE numeroSeguro = ? 
                AND fecha = ? 
                AND estatus IN ('Activa','Modificado')
                AND ( 
                    (? BETWEEN hora AND DATEADD(MINUTE, ?, hora) 
                    OR DATEADD(MINUTE, ?, ?) BETWEEN hora AND DATEADD(MINUTE, ?, hora) 
                    OR hora BETWEEN ? AND DATEADD(MINUTE, ?, ?) 
                    )
                )
                """;

            pstmt = conn.prepareStatement(query);
            
            int paramIndex = 1;
            
            // Subconsulta (parámetros 1-9)
            pstmt.setString(paramIndex++, nss);
            pstmt.setString(paramIndex++, fecha);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            
            // Consulta principal (parámetros 10-18)
            pstmt.setString(paramIndex++, nss);
            pstmt.setString(paramIndex++, fecha);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            pstmt.setInt(paramIndex++, DURACION_CITA_MINUTOS);
            pstmt.setString(paramIndex++, hora);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int citasExistentes = rs.getInt("citas_existentes");
                String medicoHora = rs.getString("medico_hora");
                
                if (citasExistentes > 0) {
                    JOptionPane.showMessageDialog(this,
                        "EL PACIENTE YA TIENE UNA CITA EN ESE HORARIO\n\n" +
                        "El paciente ya tiene una cita que se solapa:\n" +
                        "Fecha: " + fecha + "\n" +
                        "Hora solicitada: " + hora + " (Duración: " + DURACION_CITA_MINUTOS + " min)\n" +
                        "Conflicto con: " + (medicoHora != null ? medicoHora : "Otra cita") + "\n\n" +
                        "Un paciente no puede tener dos citas en horarios que se solapan.",
                        "Conflicto de Horario del Paciente",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return false;
                }
            }
            return true;
            
        } catch (SQLException ex) {
            System.err.println("Error verificando disponibilidad del paciente: " + ex.getMessage());
            return true; // En caso de error, permitir continuar
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    
    /*private void agendarCitaFinal(String fecha, String hora, String nss) {
        String horaFormateada = hora.length() > 5 ? hora.substring(0, 5) : hora;
        
        // Usar el método mejorado de generación de ID
        String idCita = generarIdCitaSecuencial();
        
        String sql = "INSERT INTO CITA (idCita, fecha, hora, numeroSeguro, idMedico, estatus) VALUES (?, ?, ?, ?, ?, 'Activa')";
        
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionSQL.ConexionSQLServer();
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, idCita);
            ps.setString(2, fecha);
            ps.setString(3, horaFormateada);
            ps.setString(4, nss);
            ps.setInt(5, Integer.parseInt(idMedico));
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Opcional: Enviar notificaciones por correo (como en el segundo código)
                try {
                    String correoMedico = obtenerCorreoMedico(conn, idMedico);
                    String correoPaciente = usuarioId; // Asumiendo que usuarioId es el correo
                    
                    String mensajePaciente = "Programaste una cita para el " + fecha + " a las " + hora + ".\n";
                    String mensajeMedico = "Tienes una cita para el " + fecha + " a las " + hora + ".\n";
                    
                    NotificacionCorreo noti = new NotificacionCorreo();
                    if (correoPaciente != null && !correoPaciente.trim().isEmpty()) {
                        noti.enviarCorreo(correoPaciente, "Recordatorio de cita médica", mensajePaciente);
                    }
                    if (correoMedico != null && !correoMedico.trim().isEmpty()) {
                        noti.enviarCorreo(correoMedico, "Recordatorio de cita médica", mensajeMedico);
                    }
                } catch (Exception e) {
                    System.err.println("Error enviando correos: " + e.getMessage());
                    // No lanzar excepción, solo log
                }
                
                JOptionPane.showMessageDialog(this, 
                    "✅ Cita agendada exitosamente\n\n" +
                    "ID de cita: " + idCita + "\n" +
                    "Médico: " + nombreMed + "\n" +
                    "Fecha: " + fecha + "\n" +
                    "Hora: " + hora,
                    "Cita Confirmada",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Recargar horarios
                if (fechaEsValida()) {
                    filtrarPorFecha();
                } else {
                    cargarHorasDisponibles();
                }
            }
            
        } catch (SQLException e) {
            // Manejo mejorado de errores
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("PRIMARY KEY")) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error: ID de cita duplicado. Intente nuevamente.\n" +
                    "ID generado: " + idCita,
                    "Error de Duplicado",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al agendar cita: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (ps2 != null) ps2.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }*/
    
    private void agendarCitaFinal(String fecha, String hora, String nss) {
    String horaFormateada = hora.length() > 5 ? hora.substring(0, 5) : hora;
    
    // Usar el método mejorado de generación de ID
    String idCita = generarIdCitaSecuencial();
    
    String sql = "INSERT INTO CITA (idCita, fecha, hora, numeroSeguro, idMedico, estatus) VALUES (?, ?, ?, ?, ?, 'Activa')";
    
    Connection conn = null;
    PreparedStatement ps = null;
    
    try {
        conn = ConexionSQL.ConexionSQLServer();
        
        ps = conn.prepareStatement(sql);
        ps.setString(1, idCita);
        ps.setString(2, fecha);
        ps.setString(3, horaFormateada);
        ps.setString(4, nss);
        ps.setInt(5, Integer.parseInt(idMedico));
        
        int filasAfectadas = ps.executeUpdate();
        
        if (filasAfectadas > 0) {
            /// Opcional: Enviar notificaciones por correo
            /*try {
                String correoMedico = obtenerCorreoMedico(conn, idMedico);
                String correoPaciente = usuarioId; // Asumiendo que usuarioId es el correo
                
                String mensajePaciente = "Programaste una cita para el " + fecha + " a las " + hora + ".\n";
                String mensajeMedico = "Tienes una cita para el " + fecha + " a las " + hora + ".\n";
                
                NotificacionCorreo noti = new NotificacionCorreo();
                if (correoPaciente != null && !correoPaciente.trim().isEmpty()) {
                    noti.enviarCorreo(correoPaciente, "Recordatorio de cita médica", mensajePaciente);
                }
                if (correoMedico != null && !correoMedico.trim().isEmpty()) {
                    noti.enviarCorreo(correoMedico, "Recordatorio de cita médica", mensajeMedico);
                }
            } catch (Exception e) {
                System.err.println("Error enviando correos: " + e.getMessage());
                // No lanzar excepción, solo log
            }*/
            
            JOptionPane.showMessageDialog(this, 
                "✅ Cita agendada exitosamente\n\n" +
                "ID de cita: " + idCita + "\n" +
                "Médico: " + nombreMed + "\n" +
                "Fecha: " + fecha + "\n" +
                "Hora: " + hora,
                "Cita Confirmada",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Recargar horarios
            if (fechaEsValida()) {
                filtrarPorFecha();
            } else {
                cargarHorasDisponibles();
            }
        }
        
    } catch (SQLException e) {
        // Manejo mejorado de errores
        if (e.getMessage().contains("duplicate key") || e.getMessage().contains("PRIMARY KEY")) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error: ID de cita duplicado. Intente nuevamente.\n" +
                "ID generado: " + idCita,
                "Error de Duplicado",
                JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al agendar cita: " + e.getMessage(),
                "Error de Base de Datos",
                JOptionPane.ERROR_MESSAGE);
        }
    } finally {
        try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
    
    
    private String obtenerCorreoMedico(Connection conn, String idMedico) throws SQLException {
        String query = "SELECT Correo FROM MEDICO WHERE idMedico = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, idMedico);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getString("Correo");
        }
        return null;
    }
    
    // Método mejorado de generación de ID (del segundo código)
    private String generarIdCitaSecuencial() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String nuevoId = "C1";
        
        try {
            conn = ConexionSQL.ConexionSQLServer();
            if (conn != null) {
                stmt = conn.createStatement();
                
                String query = "SELECT MAX(CAST(SUBSTRING(idCita, 2, LEN(idCita)-1) AS INT)) as max_num " +
                               "FROM CITA WHERE idCita LIKE 'C%'";
                
                rs = stmt.executeQuery(query);
                
                if (rs.next()) {
                    int maxNum = rs.getInt("max_num");
                    if(!rs.wasNull()){
                        nuevoId = "C" + (maxNum + 1);
                    }
                } 
            }
        } catch (SQLException ex) {
            System.err.println("Error generando ID: " + ex.getMessage());
            // Fallback al método original
            return generarIdCita();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) { }
            try { if (stmt != null) stmt.close(); } catch (SQLException ex) { }
            try { if (conn != null) conn.close(); } catch (SQLException ex) { }
        }
        return nuevoId;
    }
    
    // Métodos existentes (sin cambios)
    private String generarIdCita() {
        String sql = "SELECT idCita FROM CITA WHERE idCita LIKE 'C%' ORDER BY idCita";
        
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            int maxC = 0;
            
            while (rs.next()) {
                String idActual = rs.getString("idCita");
                if (idActual != null && idActual.startsWith("C") && idActual.length() > 1) {
                    try {
                        String numeroStr = idActual.substring(1);
                        int numero = Integer.parseInt(numeroStr);
                        if (numero > maxC) {
                            maxC = numero;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar IDs no válidos
                    }
                }
            }
            
            if (maxC == 0) {
                return "C1";
            }
            
            return "C" + (maxC + 1);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al generar ID: " + e.getMessage());
            long timestamp = System.currentTimeMillis();
            return "E" + (timestamp % 10000);
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

        String sql = """
            WITH Horarios AS (
                SELECT CAST('07:00' AS time) AS hora UNION ALL
                SELECT '08:00' UNION ALL
                SELECT '09:00' UNION ALL
                SELECT '10:00' UNION ALL
                SELECT '11:00' UNION ALL
                SELECT '12:00' UNION ALL
                SELECT '13:00' UNION ALL
                SELECT '14:00' UNION ALL
                SELECT '15:00' UNION ALL
                SELECT '16:00' UNION ALL
                SELECT '17:00' UNION ALL
                SELECT '18:00' UNION ALL
                SELECT '19:00' UNION ALL
                SELECT '20:00' UNION ALL
                SELECT '21:00' UNION ALL
                SELECT '22:00'
            )
            SELECT 
                ? as fecha,
                CONVERT(varchar(5), h.hora, 108) AS hora,
                CASE 
                    WHEN ? = CONVERT(varchar(10), GETDATE(), 120) AND h.hora < CAST(GETDATE() AS time) THEN 'Pasada'
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
            ORDER BY h.hora
            """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fecha);
            ps.setString(2, fecha);
            ps.setInt(3, Integer.parseInt(idMedico));
            ps.setString(4, fecha);

            ResultSet rs = ps.executeQuery();
            boolean hayDatos = false;

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    fecha,
                    rs.getString("hora"),
                    rs.getString("estatus")
                });
                hayDatos = true;
            }

            tblHoras.setModel(modelo);
            repintarTabla();

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
    
    // Agrega este método después de configurarColoresTabla()
private void repintarTabla() {
    // Forzar repintado para aplicar colores
    tblHoras.repaint();
    
    // Opcional: También puedes re-aplicar el renderer si es necesario
    EstatusColorRenderer renderer = new EstatusColorRenderer();
    for (int i = 0; i < tblHoras.getColumnCount(); i++) {
        tblHoras.getColumnModel().getColumn(i).setCellRenderer(renderer);
    }
}

    private void cargarHorasDisponibles() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora Disponible");
        modelo.addColumn("Estatus");

        String sql = """
            WITH Horarios AS (
                SELECT CAST('07:00' AS time) AS hora UNION ALL
                SELECT '08:00' UNION ALL
                SELECT '09:00' UNION ALL
                SELECT '10:00' UNION ALL
                SELECT '11:00' UNION ALL
                SELECT '12:00' UNION ALL
                SELECT '13:00' UNION ALL
                SELECT '14:00' UNION ALL
                SELECT '15:00' UNION ALL
                SELECT '16:00' UNION ALL
                SELECT '17:00' UNION ALL
                SELECT '18:00' UNION ALL
                SELECT '19:00' UNION ALL
                SELECT '20:00' UNION ALL
                SELECT '21:00' UNION ALL
                SELECT '22:00'
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
                    WHEN f.fecha = CAST(GETDATE() AS date) AND h.hora < CAST(GETDATE() AS time) THEN 'Pasada'
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
            ORDER BY f.fecha, h.hora
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
            repintarTabla();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

private void limpiarTabla() {
    DefaultTableModel modelo = (DefaultTableModel) tblHoras.getModel();
    modelo.setRowCount(0);
}

// Clase interna para renderizar celdas con colores
class EstatusColorRenderer extends javax.swing.table.DefaultTableCellRenderer {
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        java.awt.Component cell = super.getTableCellRendererComponent(table, value, 
                isSelected, hasFocus, row, column);
        
        // Obtener el valor de la columna "Estatus" (columna 2)
        String estatus = table.getValueAt(row, 2).toString();
        
        // Configurar colores según el estatus
        if ("Disponible".equals(estatus)) {
            cell.setBackground(new java.awt.Color(255, 255, 153)); // Amarillo claro
            cell.setForeground(java.awt.Color.BLACK);
        } else if ("Ocupado".equals(estatus)) {
            cell.setBackground(new java.awt.Color(255, 165, 0)); // Naranja
            cell.setForeground(java.awt.Color.BLACK);
        } else if ("Pasada".equals(estatus)) {
            cell.setBackground(new java.awt.Color(200, 200, 200)); // Gris para pasado
            cell.setForeground(java.awt.Color.DARK_GRAY);
        } else {
            cell.setBackground(java.awt.Color.WHITE);
            cell.setForeground(java.awt.Color.BLACK);
        }
        
        // Mantener color de selección
        if (isSelected) {
            cell.setBackground(new java.awt.Color(100, 149, 237)); // Azul para selección
            cell.setForeground(java.awt.Color.WHITE);
        }
        
        // Centrar texto
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        return cell;
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
        jLabel3.setText("Horarios Disponibles - Médicos");

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");

        lblBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/asistencia-medica.png"))); // NOI18N
        lblBuscar.setText("Nombre del medico:");

        btnLimpiar.setText("Refresh");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Especialidad.png"))); // NOI18N
        jLabel1.setText("Especialidad:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/calendario.png"))); // NOI18N
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
                        .addGap(0, 190, Short.MAX_VALUE)
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
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBuscar)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99)
                                .addComponent(btnLimpiar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGap(32, 32, 32)
                                .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 985, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBuscar)
                            .addComponent(jLabel1)
                            .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
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