/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión a la base de datos MySQL.
 * Proporciona un método estático para obtener una nueva conexión cada vez que se necesita.
 * 
 * @author EMMANUEL
 */
public class DataSource {

    // URL de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/restaurante";
    
    // Usuario para la conexión a la base de datos
    private static final String USUARIO = "root";
    
    // Contraseña para la conexión a la base de datos
    private static final String CONTRASENA = "";

    /**
     * Método estático que obtiene una nueva conexión a la base de datos.
     * Este método utiliza las credenciales definidas en la clase (URL, USUARIO, CONTRASENA)
     * para establecer una conexión con la base de datos MySQL.
     * 
     * @return Una instancia de Connection si la conexión es exitosa, 
     *         o null si ocurre un error al conectar.
     */
    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            // Intentar establecer la conexión a la base de datos
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexión establecida exitosamente.");
        } catch (SQLException e) {
            // En caso de error, imprimir el mensaje de error
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }
}
