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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
     * @param usuario Objeto Usuario a insertar.
     * @return true si la inserción fue exitosa, false de lo contrario.
     */
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (login, clave, idEmpleado, idCliente) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getLogin());
            ps.setString(2, usuario.getClave());
            
            // Asigna el idEmpleado o idCliente según el tipo de usuario
            if (usuario.getEmp() != null) {
                ps.setInt(3, usuario.getEmp().getIdEmpleado());
                ps.setNull(4, java.sql.Types.INTEGER);
            } else if (usuario.getCliente() != null) {
                ps.setNull(3, java.sql.Types.INTEGER);
                ps.setInt(4, usuario.getCliente().getIdCliente());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar Usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para obtener todos los usuarios de la base de datos.
     * @return Lista de usuarios.
     */
    public ArrayList<Usuario> obtenerTodosUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.login, u.clave, e.idEmpleado, e.nombreEmpleado, e.cargo, c.idCliente, c.nombre AS nombreCliente " +
                     "FROM usuario u " +
                     "LEFT JOIN empleado e ON u.idEmpleado = e.idEmpleado " +
                     "LEFT JOIN cliente c ON u.idCliente = c.idCliente";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setLogin(rs.getString("login"));
                usuario.setClave(rs.getString("clave"));
                
                int idEmpleado = rs.getInt("idEmpleado");
                int idCliente = rs.getInt("idCliente");

                // Verifica si es un empleado o cliente y asigna el objeto correspondiente
                if (idEmpleado != 0) {
                    Empleado emp = new Empleado();
                    emp.setIdEmpleado(idEmpleado);
                    emp.setNombreEmp(rs.getString("nombreEmpleado"));
                    emp.setCargo(rs.getString("cargo"));
                    usuario.setEmp(emp);
                } else if (idCliente != 0) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(idCliente);
                    cliente.setNombre(rs.getString("nombreCliente"));
                    usuario.setCliente(cliente);
                }

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener Usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    /**
     * Método para buscar un usuario por login o cargo en la base de datos.
     * @param login Login del usuario a buscar (puede ser null si se busca por cargo).
     * @param cargo Cargo del empleado (rol) a buscar (puede ser null si se busca por login).
     * @return Objeto Usuario si se encuentra, null en caso contrario.
     */
    public Usuario buscarUsuario(String login, String cargo) {
        String sql;
        Usuario usuario = null;

        if (login != null) {
            sql = "SELECT u.login, u.clave, e.idEmpleado, e.nombreEmpleado, e.cargo, c.idCliente, c.nombre AS nombreCliente " +
                  "FROM usuario u " +
                  "LEFT JOIN empleado e ON u.idEmpleado = e.idEmpleado " +
                  "LEFT JOIN cliente c ON u.idCliente = c.idCliente " +
                  "WHERE u.login = ?";
        } else if (cargo != null) {
            sql = "SELECT u.login, u.clave, e.idEmpleado, e.nombreEmpleado, e.cargo, c.idCliente, c.nombre AS nombreCliente " +
                  "FROM usuario u " +
                  "LEFT JOIN empleado e ON u.idEmpleado = e.idEmpleado " +
                  "LEFT JOIN cliente c ON u.idCliente = c.idCliente " +
                  "WHERE e.cargo = ?";
        } else {
            System.err.println("Se debe proporcionar un login o un cargo para la búsqueda.");
            return null;
        }

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            if (login != null) {
                ps.setString(1, login);
            } else {
                ps.setString(1, cargo);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setLogin(rs.getString("login"));
                    usuario.setClave(rs.getString("clave"));
                    
                    int idEmpleado = rs.getInt("idEmpleado");
                    int idCliente = rs.getInt("idCliente");

                    if (idEmpleado != 0) {
                        Empleado emp = new Empleado();
                        emp.setIdEmpleado(idEmpleado);
                        emp.setNombreEmp(rs.getString("nombreEmpleado"));
                        emp.setCargo(rs.getString("cargo"));
                        usuario.setEmp(emp);
                    } else if (idCliente != 0) {
                        Cliente cliente = new Cliente();
                        cliente.setIdCliente(idCliente);
                        cliente.setNombre(rs.getString("nombreCliente"));
                        usuario.setCliente(cliente);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return usuario;
    }
}
