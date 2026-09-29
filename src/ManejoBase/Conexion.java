package ManejoBase;
//Librerias
import java.sql.*;
//Gabriel Ricardo Rodriguez de León
public class Conexion {
    //Esta es solo la clase para la conexión, aún no esta en uso
    //Conexion
    Connection con;
    public Connection getConnection() {
        // driver, puerto y el nombre de la base de datos
        String url = "jdbc:mysql://localhost:3306/Expo3";
        //usuario
        String user = "root";
        //contraseña
        String pass = "12345";
        //try catch para manejo de excepciones
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
        }
        return con;
    }
}
