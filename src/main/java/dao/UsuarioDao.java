/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Usuario;
import Modelo.Empleado;
import Modelo.Cliente;
import conf.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Clase para manejar las operaciones CRUD de Usuario en la base de datos.
 * 
 * @author EMMANUEL
 */
public class UsuarioDao {
    private Connection conexion;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public UsuarioDao() {
        this.conexion = DataSource.obtenerConexion();
    }

    /**
     * Método para insertar un nuevo usuario en la base de datos.
     * 
     * @param user Objeto Usuario a insertar.
     * @return true si la inserción fue exitosa, false de lo contrario.
     */
    public boolean insertarUsuario(Usuario user) {
        String sqlUsuario = "INSERT INTO usuario (login, clave, rol, idEmpleado, idCliente) VALUES (?, ?, ?, ?, ?)";
        String sqlEmpleado = "INSERT INTO empleado (nombreEmpleado, cargo, turno) VALUES (?, ?, ?)";
        String sqlCliente = "INSERT INTO cliente (nombre,apellido,email, telefono, dni) VALUES (?, ?, ?, ?, ?)";

        try {
            conexion.setAutoCommit(false);  // Inicia la transacción

            int idGenerado = -1;

            // Inserta Empleado o Cliente según corresponda
            if (user.getObj() instanceof Empleado empleado) {
                try (PreparedStatement psEmpleado = conexion.prepareStatement(sqlEmpleado, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    psEmpleado.setString(1, empleado.getNombreEmp());
                    psEmpleado.setString(2, empleado.getCargo());
                    psEmpleado.setString(3, empleado.getTurno());
                    psEmpleado.executeUpdate();

                    try (ResultSet rs = psEmpleado.getGeneratedKeys()) {
                        if (rs.next()) {
                            idGenerado = rs.getInt(1);  // Recupera el ID generado del Empleado
                        }
                    }
                }
            } else if (user.getObj() instanceof Cliente cliente) {
                try (PreparedStatement psCliente = conexion.prepareStatement(sqlCliente, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    psCliente.setString(1, cliente.getNombre());
                    psCliente.setString(2, cliente.getApellido());
                    psCliente.setString(3, cliente.getEmail());
                    psCliente.setString(4, cliente.getTelefono());
                    psCliente.setString(5, cliente.getDni());
                    psCliente.executeUpdate();

                    try (ResultSet rs = psCliente.getGeneratedKeys()) {
                        if (rs.next()) {
                            idGenerado = rs.getInt(1);  // Recupera el ID generado del Cliente
                        }
                    }
                }
            }

            if (idGenerado == -1) {
                conexion.rollback();  // Revertir si falla la inserción
                return false;
            }

            // Inserta el Usuario con el ID recuperado
            try (PreparedStatement psUsuario = conexion.prepareStatement(sqlUsuario)) {
                String hashedPassword = hashPassword(user.getClave());
                psUsuario.setString(1, user.getLogin());
                psUsuario.setString(2, hashedPassword);
                psUsuario.setString(3, user.getRol());

                if (user.getObj() instanceof Empleado) {
                    psUsuario.setInt(4, idGenerado);
                    psUsuario.setNull(5, java.sql.Types.INTEGER);
                } else if (user.getObj() instanceof Cliente) {
                    psUsuario.setNull(4, java.sql.Types.INTEGER);
                    psUsuario.setInt(5, idGenerado);
                }

                int rowsAffected = psUsuario.executeUpdate();
                if (rowsAffected > 0) {
                    conexion.commit();  // Confirmar transacción
                    return true;
                } else {
                    conexion.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al revertir la transacción: " + ex.getMessage());
            }
            System.err.println("Error al insertar usuario con transacción: " + e.getMessage());
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar el auto-commit: " + e.getMessage());
            }
        }
    }


    /**
     * Método para obtener todos los usuarios de la base de datos.
     * 
     * @return Lista de usuarios.
     */
    public ArrayList<Usuario> obtenerTodosUsuarios(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.login, u.clave, u.rol, u.idEmpleado, u.idCliente, e.nombreEmpleado, e.cargo, c.nombre\n" +
            "FROM usuario u\n" +
            "LEFT JOIN empleado e ON u.idEmpleado = e.idEmpleado\n" +
            "LEFT JOIN cliente c ON u.idCliente = c.idCliente;";
        try(PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Usuario user = new Usuario();
                    user.setLogin(rs.getString("login"));
                    user.setClave(rs.getString("clave"));
                    user.setRol(rs.getString("rol"));
                    
                    // Si idEmpleado es nulo, lo dejamos como null, si no, lo establecemos
                int idEmpleado = rs.getInt("idEmpleado");
                if (rs.wasNull()) {
                    user.setObj(null);
                } else {
                    // Aquí puedes optar por pasar la información completa del empleado
                    Empleado empleado = new Empleado(idEmpleado, rs.getString("nombreEmpleado"), 
                            rs.getString("cargo"),rs.getString("turno"));
                    user.setObj(empleado);
                }
            
            // Si idCliente es nulo, lo dejamos como null, si no, lo establecemos
            int idCliente = rs.getInt("idCliente");
            if (rs.wasNull()) {
                user.setObj(null);
            } else {
                // Aquí puedes optar por pasar la información completa del cliente
                Cliente cliente = new Cliente(idCliente, rs.getString("nombre"),rs.getString("apellido"),
                        rs.getString("email"),rs.getString("telefono"), rs.getString("dni"));
                user.setObj(cliente);
            }
            
                usuarios.add(user);
            }
        }catch (SQLException e){
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    /**
     * Método para validar las credenciales de un usuario.
     * 
     * @param login El login del usuario.
     * @param clave La clave del usuario.
     * @return El objeto Usuario si las credenciales son correctas, null en caso contrario.
     */
    public Usuario validarCredenciales(String login, String clave) {
        String sql = "SELECT u.login, u.clave, u.rol, u.idEmpleado, u.idCliente, " +
                 "e.nombreEmpleado, e.cargo, e.turno, c.nombre, c.apellido, c.email, c.telefono, c.dni " +
                 "FROM usuario u " +
                 "LEFT JOIN empleado e ON u.idEmpleado = e.idEmpleado " +
                 "LEFT JOIN cliente c ON u.idCliente = c.idCliente " +
                 "WHERE u.login = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, login);  // Establecemos el login en la consulta
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Obtenemos los datos del usuario
                    String storedPasswordHash = rs.getString("clave");  // Obtiene el hash almacenado
                    // Hasheamos la clave proporcionada por el usuario
                    String hashedPassword = hashPassword(clave);

                    // Comparamos el hash de la clave proporcionada con el hash almacenado
                    if (storedPasswordHash.equals(hashedPassword)) {
                        // Si las contraseñas coinciden, creamos el objeto Usuario
                        Usuario user = new Usuario();
                        user.setLogin(rs.getString("login"));
                        user.setClave(storedPasswordHash);
                        user.setRol(rs.getString("rol"));

                        // Si idEmpleado es nulo, lo dejamos como null, si no, lo establecemos
                        int idEmpleado = rs.getInt("idEmpleado");
                        if (!rs.wasNull()) {
                            Empleado empleado = new Empleado(idEmpleado, 
                                 rs.getString("nombreEmpleado"),
                                    rs.getString("cargo"), 
                                    rs.getString("turno"));
                            user.setObj(empleado);
                        }

                        // Si idCliente es nulo, lo dejamos como null, si no, lo establecemos
                        int idCliente = rs.getInt("idCliente");
                        if (!rs.wasNull()) {
                            Cliente cliente = new Cliente(idCliente, 
                                 rs.getString("nombre"),
                                rs.getString("apellido"), 
                                  rs.getString("email"),
                                rs.getString("telefono"),
                                    rs.getString("dni"));
                            user.setObj(cliente);
                        }

                        return user;  // Si las credenciales son correctas, devolvemos el usuario
                    } else {
                        System.out.println("Contraseña incorrecta.");
                    }
                } else {
                    System.out.println("Usuario no encontrado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al validar credenciales: " + e.getMessage());
        }

        return null;  // Si las credenciales no son correctas, devolvemos null
    }

    /**
     * Método para hashear la contraseña utilizando SHA-256.
     * 
     * @param password La contraseña a hashear.
     * @return La contraseña hasheada en formato Base64.
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }
    
    /**
    * Método que valida si ya existe un usuario con el rol de "Empleado" 
    * y el cargo de "Administrador" en el sistema. Este método realiza una 
    * consulta en las tablas `usuario` y `empleado` para verificar si ya 
    * hay un usuario registrado con ese rol y cargo específicos.
    * 
    * Si la consulta devuelve un resultado mayor a cero, significa que ya existe 
    * al menos un administrador en el sistema. Si no es así, el método 
    * devuelve `false`, indicando que aún no se ha registrado un administrador.
    * 
    * @return `true` si ya existe un usuario con rol "Empleado" y cargo "Administrador", 
    *         `false` en caso contrario.
    */    
    public boolean existeAdmin() {
        String sql = "SELECT COUNT(*) FROM usuario u " +
                     "INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado " +
                     "WHERE u.rol = 'Empleado' AND e.cargo = 'Administrador'";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // Si el conteo es mayor a 0, significa que ya existe un administrador
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar la existencia de administrador: " + e.getMessage());
        }

        return false;  // Si no existe administrador, devolvemos false
    }

    
}
