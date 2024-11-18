/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Cliente;
import conf.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase DAO (Data Access Object) que maneja las operaciones de base de datos para la entidad Cliente.
 * Permite realizar acciones como insertar, obtener, actualizar y buscar clientes en la base de datos.
 * 
 * @author EMMANUEL
 */
public class ClienteDao {

    // Conexión a la base de datos
    private Connection conexion;

    /**
     * Constructor de la clase ClienteDao.
     * Inicializa la conexión a la base de datos utilizando el DataSource.
     */
    public ClienteDao() {
        this.conexion = DataSource.obtenerConexion();
    }

    /**
     * Método para insertar un nuevo cliente en la base de datos.
     * 
     * @param cliente El objeto Cliente que contiene los datos a insertar.
     * @return true si la inserción fue exitosa, false si ocurrió un error.
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
     * 
     * @return Una lista de objetos Cliente que representan a todos los clientes en la base de datos.
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
     * Método para obtener un cliente específico de la base de datos, basado en su ID.
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

    /**
     * Método para actualizar los datos de un cliente en la base de datos.
     * 
     * @param cliente El objeto Cliente que contiene los nuevos datos a actualizar.
     * @return true si la actualización fue exitosa, false si ocurrió un error.
     */
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre = ?, apellido = ?, email = ?, telefono = ? WHERE idCliente = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefono());
            ps.setInt(5, cliente.getIdCliente());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar Cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar clientes en la base de datos por su nombre.
     * 
     * @param nombre El nombre que se desea buscar en la base de datos.
     * @return Una lista de objetos Cliente que coinciden con el nombre buscado.
     */
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