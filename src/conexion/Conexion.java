package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
  public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/bd_sistemaVentas?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "root";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("❌ Error en la conexión: " + e.getMessage());
            return null;
        }
    }
}
