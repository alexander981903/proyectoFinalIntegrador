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
