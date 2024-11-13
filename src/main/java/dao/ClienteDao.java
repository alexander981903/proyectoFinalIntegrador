/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author EMMANUEL
 */

import Modelo.Cliente;
import conf.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase DAO que maneja las operaciones de base de datos para la entidad Cliente.
 */
public class ClienteDao {
    private Connection conexion;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public ClienteDao() {
        this.conexion = DataSource.obtenerConexion();
    }

    /**
     * Método para insertar un nuevo cliente en la base de datos.
     * @param cliente Objeto Cliente a insertar.
     * @return true si la inserción fue exitosa, false de lo contrario.
     */
    public boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, apellido, email, telefono) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefono());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar Cliente en la capa DAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para obtener todos los clientes de la base de datos.
     * @return Lista de clientes.
     */
    public ArrayList<Cliente> obtenerTodosClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefono(rs.getString("telefono"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener Clientes: " + e.getMessage());
        }
        return clientes;
    }
    
    /**
     * Obtiene un cliente específico de la base de datos.
     * 
     * @param idCliente El ID del cliente que se desea obtener.
     * @return El objeto Cliente correspondiente al ID, o null si no se encuentra.
     */
     public Cliente buscarPorId(int idCliente) {
        String sql = "SELECT * FROM cliente WHERE idCliente=?";
        try (Connection conn = DataSource.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Retorna un nuevo objeto Cliente con los datos obtenidos
                return new Cliente(
                    rs.getInt("idCliente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("telefono")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente: " + e.getMessage());
        }
        return null; // Retorna null si no se encuentra el cliente
    }
     
    public boolean actualizarCliente(Cliente cliente){
        String sql = "UPDATE cliente SET nombre = ?, apellido = ?, email = ?, telefono = ? WHERE idCliente = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3,cliente.getEmail());
            ps.setString(4, cliente.getTelefono());
            ps.setInt(5, cliente.getIdCliente());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar Cliente: " + e.getMessage());
            return false;
        }
    }
    
    public ArrayList<Cliente> buscarClientesPorNombre(String nombre) {
    ArrayList<Cliente> clientes = new ArrayList<>();
    String sql = "SELECT * FROM cliente WHERE nombre LIKE ?";
    
    // Utilizamos el signo de porcentaje '%' para hacer coincidir cualquier parte del nombre
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, "%" + nombre + "%");  // El '%' indica que puede haber cualquier cosa antes o después del nombre
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefono(rs.getString("telefono"));
                clientes.add(cliente);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar clientes por nombre: " + e.getMessage());
    }
        return clientes;
    }

}

