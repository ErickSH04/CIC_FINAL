
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class NotificacionCorreo {

    public static void enviarCorreo(String destinatario, String asunto, String mensaje) {
        // Credenciales
        String remitente = "starkinc45@gmail.com"; // Cambia esto por tu correo
        String clave = "bnjo hrpq gsrb qqpy ";      // Carga desde variable de entorno

        if (remitente == null || clave == null) {
            System.err.println("Error: Las credenciales no están configuradas.");
            return;
        }

        // Configuración de propiedades para Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Usar puerto 587 para STARTTLS
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Crear sesión con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            // Crear mensaje
            Message mensajeCorreo = new MimeMessage(session);
            mensajeCorreo.setFrom(new InternetAddress(remitente));
            mensajeCorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensajeCorreo.setSubject(asunto);
            mensajeCorreo.setText(mensaje);

            // Enviar correo
            Transport.send(mensajeCorreo);
            System.out.println("Correo enviado a: " + destinatario);

        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void enviarCorreo24(String destinatario, String asunto, String mensaje) {
        // Credenciales
        String remitente = "mariafernandazepedacastaneda@gmail.com"; // Cambia esto por tu correo
        String clave = "ohwp ypgv gtdf cwxg";     // Carga desde variable de entorno

        if (remitente == null || clave == null) {
            System.err.println("Error: Las credenciales no están configuradas.");
            return;
        }

        // Configuración de propiedades para Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Usar puerto 587 para STARTTLS
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Crear sesión con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            // Crear mensaje
            Message mensajeCorreo = new MimeMessage(session);
            mensajeCorreo.setFrom(new InternetAddress(remitente));
            mensajeCorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensajeCorreo.setSubject(asunto);
            mensajeCorreo.setText(mensaje);

            // Enviar correo
            Transport.send(mensajeCorreo);
            System.out.println("Correo enviado a: " + destinatario);

        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
