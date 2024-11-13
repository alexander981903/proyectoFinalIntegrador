/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author EMMANUEL
 */
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
     * @param user Objeto Usuario a insertar.
     * @return true si la inserción fue exitosa, false de lo contrario.
     */
    public boolean insertarUsuario(Usuario user) {
        String sql = "INSERT INTO usuario (login, clave, rol, idEmpleado, idCliente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Hashear la contraseña antes de almacenarla
            String hashedPassword = hashPassword(user.getClave());
            // Establece los parámetros de la consulta
            ps.setString(1, user.getLogin());
            ps.setString(2, hashedPassword);
            ps.setString(3, user.getRol());

            // Asigna idEmpleado o idCliente según el tipo de objeto almacenado en 'obj'
            if (user.getObj() instanceof Empleado) {
                Empleado empleado = (Empleado) user.getObj();
                ps.setInt(4, empleado.getIdEmpleado());
                ps.setNull(5, java.sql.Types.INTEGER);  // idCliente es NULL
            } else if (user.getObj() instanceof Cliente) {
                Cliente cliente = (Cliente) user.getObj();
                ps.setNull(4, java.sql.Types.INTEGER);  // idEmpleado es NULL
                ps.setInt(5, cliente.getIdCliente());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);  // Ambos son NULL si no es ni cliente ni empleado
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            // Ejecuta la consulta de inserción
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Devuelve true si se inserta correctamente

        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }





    /**
     * Método para obtener todos los usuarios de la base de datos.
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
                        rs.getString("email"),rs.getString("telefono"));
                user.setObj(cliente);
            }
            
                usuarios.add(user);
            }
        }catch (SQLException e){
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }
    
    public Usuario validarCredenciales(String login, String clave) {
        String sql = "SELECT u.login, u.clave, u.rol, u.idEmpleado, u.idCliente, " +
                 "e.nombreEmpleado, e.cargo, e.turno, c.nombre, c.apellido, c.email, c.telefono " +
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
                            Empleado empleado = new Empleado(idEmpleado, rs.getString("nombreEmpleado"),
                                                              rs.getString("cargo"), rs.getString("turno"));
                            user.setObj(empleado);
                        }

                        // Si idCliente es nulo, lo dejamos como null, si no, lo establecemos
                        int idCliente = rs.getInt("idCliente");
                        if (!rs.wasNull()) {
                            Cliente cliente = new Cliente(idCliente, rs.getString("nombre"),
                                                          rs.getString("apellido"), rs.getString("email"),
                                                          rs.getString("telefono"));
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

    
    // Método para hashear la contraseña
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes); // Convierte a Base64 para almacenar
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }
}
