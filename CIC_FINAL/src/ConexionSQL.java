import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQL {
    public static Connection con;

   /*public static Connection ConexionSQLServer() {
        // Cambia "database-name" por el nombre de tu base de datos, "username" y "password" por tus credenciales
        // Y "db-instance-endpoint" por el endpoint de tu base de datos en RDS.
        String conexionUr1 = "jdbc:sqlserver://localhost:1433;"  // Reemplaza con tu endpoint de RDS
                + "database=Agenda;"  // Reemplaza con el nombre de tu base de datos en RDS
                + "user=sa;"  // Reemplaza con tu nombre de usuario
                + "password=1234;"  // Reemplaza con tu contraseña
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";  // Tiempo de espera en segundos

        try {
            con = DriverManager.getConnection(conexionUr1);
            System.out.println("Conectado");
            return con;
        } catch (SQLException ex) {
            System.out.println("Error de conexión");
            System.out.println(ex.toString());
            return null;
        }
    }*/
   
    public static Connection ConexionSQLServer() {
        String conexionUr1 = "jdbc:sqlserver://database-1.ck3g6s0mwbro.us-east-1.rds.amazonaws.com:1433;"  // Reemplaza con tu endpoint de RDS
                + "database=Agenda1;"  // Reemplaza con el nombre de tu base de datos en RDS
                + "user=admin;"  // Reemplaza con tu nombre de usuario
                + "password=12345678CIC;"  // Reemplaza con tu contraseña
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";  // Tiempo de espera en segundos
        try {
            con = DriverManager.getConnection(conexionUr1);
            System.out.println("Conectado");
            return con;
        } catch (SQLException ex) {
            System.out.println("Error de conexión");
            System.out.println(ex.toString());
            return null;
        }
    }

   

    public static void main(String[] args) {
        ConexionSQLServer();
    }
}