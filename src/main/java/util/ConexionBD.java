package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

<<<<<<< HEAD
    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/gestor_documentos";
=======
    private static final String URL =
            "jdbc:mysql://localhost:3306/gestor_documentos"
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=America/Mexico_City";

>>>>>>> ad124b3 (Registro de usuario en el login)
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos: " + conexion.getCatalog());
            return conexion;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado en el classpath.", e);
        } catch (SQLException e) {
            throw new SQLException(
                    "No se pudo conectar a la base de datos MySQL. Verifica que MySQL esté ejecutándose y que la URL, usuario y contraseña sean correctos.",
                    e
            );
        }
    }

    public static void cerrarConexion(Connection conexion) {

        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
}
