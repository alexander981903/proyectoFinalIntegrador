/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Mesa;
import conf.DataSource;
import java.sql.*;
import java.util.ArrayList;

/**
 * Clase DAO que maneja las operaciones de base de datos para la entidad Mesa.
 * 
 * @author EMMANUEL
 */
public class MesaDao {
    private Connection conexion;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public MesaDao() {
        this.conexion = DataSource.obtenerConexion();
    }

    /**
     * Método para insertar una nueva mesa en la base de datos.
     * 
     * @param mesa Objeto Mesa a insertar.
     * @return true si la inserción fue exitosa, false de lo contrario.
     */
    public boolean insertarMesa(Mesa mesa) {
        String sql = "INSERT INTO mesa (numeroMesa, capacidad, estadoMesa) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, mesa.getNumeroMesa());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getEstadoMesa());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar mesa: " + e.getMessage());
            return false;
        }
    }
    
    public int agregarMesa(int numeroMesa, int capacidad, String estado) {
        String sql = "INSERT INTO mesa (numeroMesa, capacidad, estadoMesa) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, numeroMesa);
            ps.setInt(2, capacidad);
            ps.setString(3, estado);

            ps.executeUpdate();

            // Recuperar el ID generado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar mesa: " + e.getMessage());
        }
        return -1; // Retorna -1 si no se pudo insertar
    }
    
    public int obtenerUltimoIdMesa() {
        String sql = "SELECT MAX(idMesa) AS idMesa FROM mesa";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("idMesa");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el último ID de la mesa: " + e.getMessage());
        }
        return -1; // Retorna -1 si no se pudo obtener
    }



    /**
     * Método para obtener todas las mesas de la base de datos.
     * 
     * @return Una lista de objetos Mesa con todos los registros.
     */
    public ArrayList<Mesa> obtenerTodasLasMesas() {
        ArrayList<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT * FROM mesa";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("idMesa"));
                mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setEstadoMesa(rs.getString("estadoMesa"));
                mesas.add(mesa);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mesas: " + e.getMessage());
        }
        return mesas;
    }

    /**
     * Método para obtener una mesa específica por su ID.
     * 
     * @param idMesa El ID de la mesa que se desea obtener.
     * @return El objeto Mesa correspondiente al ID, o null si no se encuentra.
     */
    public Mesa obtenerMesaPorId(int idMesa) {
        String sql = "SELECT * FROM mesa WHERE idMesa = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Mesa mesa = new Mesa();
                    mesa.setIdMesa(rs.getInt("idMesa"));
                    mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                    mesa.setCapacidad(rs.getInt("capacidad"));
                    mesa.setEstadoMesa(rs.getString("estadoMesa"));
                    return mesa;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mesa por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Método para actualizar los datos de una mesa en la base de datos.
     * 
     * @param mesa El objeto Mesa con los datos actualizados.
     * @return true si la actualización fue exitosa, false de lo contrario.
     */
    public boolean actualizarMesa(Mesa mesa) {
        String sql = "UPDATE mesa SET numeroMesa = ?, capacidad = ?, estadoMesa = ? WHERE idMesa = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, mesa.getNumeroMesa());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getEstadoMesa());
            ps.setInt(4, mesa.getIdMesa());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar mesa: " + e.getMessage());
            return false;
        }
    }
    
    
}
