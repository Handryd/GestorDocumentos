package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/gestor_documentos";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    /**
     * Obtiene una conexión a la base de datos
     */
    public static Connection getConnection() {

        Connection conexion = null;

        try {

            // Cargar driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Crear conexión
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);

            System.out.println("CONEXIÓN OK");
            System.out.println("Base: " + conexion.getCatalog());

            System.out.println("Conexión exitosa a la base de datos.");

        } catch (ClassNotFoundException e) {

            System.err.println("Error: Driver MySQL no encontrado.");
            e.printStackTrace();

        } catch (SQLException e) {

            System.err.println("ERROR MYSQL:");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return conexion;
    }

    /**
     * Cierra una conexión abierta
     */
    public static void cerrarConexion(Connection conexion) {

        if (conexion != null) {

            try {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");

            } catch (SQLException e) {

                System.err.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
}