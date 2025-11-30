
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class VerificadorCitasProximas extends Thread {

    private Connection con;

    public VerificadorCitasProximas(Connection con) {
        this.con = con;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Verificando citas");
                // Consulta citas para las próximas 24 horas que no han sido notificadas
                String query = "SELECT cit.idCita, cit.fecha, cit.hora, pac.correo AS correoPaciente, med.correo AS correoMedico\n"
                        + "FROM Cita cit\n"
                        + "INNER JOIN Paciente pac ON pac.numeroSeguro = cit.numeroSeguro\n"
                        + "INNER JOIN Medico med ON med.idMedico = cit.idMedico\n"
                        + "WHERE cit.fecha BETWEEN CAST(GETDATE() AS DATE) AND CAST(DATEADD(DAY, 1, GETDATE()) AS DATE) AND notificado24h = 0\n" 
                        + "ORDER BY cit.fecha ASC, cit.hora ASC;";

                try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                    while (rs.next()) {
                        // Extraer información de la cita
                        String idCita = rs.getString("idCita");
                        String fecha = rs.getString("fecha");
                        String hora = rs.getString("hora");
                        String correoPaciente = rs.getString("correoPaciente");
                        String correoMedico = rs.getString("correoMedico");

                        // Construir el mensaje
                        String mensaje = "Recordatorio: Tienes una cita programada para el " + fecha + " a las " + hora + ".\n";

                        // Enviar notificación al paciente y al médico
                        NotificacionCorreo.enviarCorreo24(correoPaciente, "Recordatorio de cita médica", mensaje);
                        NotificacionCorreo.enviarCorreo24(correoMedico, "Recordatorio de cita médica", mensaje);

                        // Marcar la cita como notificada
                        String updateQuery = "UPDATE Cita SET notificado24h = 1 WHERE idCita = '" + idCita + "';";
                        try (Statement updateStmt = con.createStatement()) {
                            updateStmt.executeUpdate(updateQuery);
                        }
                        System.out.println("Se mandaron los correo correctamente");
                    }
                    System.out.println("No hubo citas proximas");
                }

                // Esperar 2 minutos antes de volver a ejecutar
                Thread.sleep(2 * 60 * 1000); // 2 minutos
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
