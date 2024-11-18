/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Configuracion;
import conf.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase DAO (Data Access Object) que maneja las operaciones de base de datos para la entidad Configuracion.
 * Permite realizar acciones como insertar, obtener y actualizar la configuración de la aplicación.
 * 
 * @author EMMANUEL
 */
public class ConfiguracionDao {

    // Conexión a la base de datos
    private Connection conexion;

    /**
     * Constructor de la clase ConfiguracionDao.
     * Inicializa la conexión a la base de datos utilizando el DataSource.
     */
    public ConfiguracionDao() {
        this.conexion = DataSource.obtenerConexion();
    }

    /**
     * Método para insertar una nueva configuración en la base de datos.
     * 
     * @param configuracion El objeto Configuracion que contiene los datos a insertar.
     * @return true si la inserción fue exitosa, false si ocurrió un error.
     */
    public boolean insertarConfiguracion(Configuracion configuracion) {
        String sql = "INSERT INTO configuracion (nombreEmpresa, cantidadDeMesasParaMostrar, permisosAgregar, permisosModificar, "
                   + "permisosAccederVentanas, permisosAccederBotones, configuracionActiva) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, configuracion.getNombreEmpresa());
            ps.setInt(2, configuracion.getCantidadDeMesasParaMostrar());
            ps.setBoolean(3, configuracion.isPermisosAgregar());
            ps.setBoolean(4, configuracion.isPermisosModificar());
            ps.setBoolean(5, configuracion.isPermisosAccederVentanas());
            ps.setBoolean(6, configuracion.isPermisosAccederBotones());
            ps.setBoolean(7, configuracion.isConfiguracionActiva());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar Configuración en la capa DAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para obtener la configuración actual desde la base de datos.
     * 
     * @return El objeto Configuracion con los datos obtenidos, o null si no se encuentra.
     */
    public Configuracion obtenerConfiguracion() {
        String sql = "SELECT * FROM configuracion LIMIT 1"; // Solo obtenemos una fila, ya que normalmente solo habrá una configuración activa
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // Retorna un nuevo objeto Configuracion con los datos obtenidos
                return new Configuracion(
                    rs.getInt("id"),
                    rs.getString("nombreEmpresa"),
                    rs.getInt("cantidadDeMesasParaMostrar"),
                    rs.getBoolean("permisosAgregar"),
                    rs.getBoolean("permisosModificar"),
                    rs.getBoolean("permisosAccederVentanas"),
                    rs.getBoolean("permisosAccederBotones"),
                    rs.getBoolean("configuracionActiva")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener configuración: " + e.getMessage());
        }
        return null; // Retorna null si no se encuentra la configuración
    }

    /**
     * Método para actualizar los datos de la configuración en la base de datos.
     * 
     * @param configuracion El objeto Configuracion que contiene los nuevos datos a actualizar.
     * @return true si la actualización fue exitosa, false si ocurrió un error.
     */
    public boolean actualizarConfiguracion(Configuracion configuracion) {
        String sql = "UPDATE configuracion SET nombreEmpresa = ?, cantidadDeMesasParaMostrar = ?, permisosAgregar = ?, "
                   + "permisosModificar = ?, permisosAccederVentanas = ?, permisosAccederBotones = ?, configuracionActiva = ? "
                   + "WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, configuracion.getNombreEmpresa());
            ps.setInt(2, configuracion.getCantidadDeMesasParaMostrar());
            ps.setBoolean(3, configuracion.isPermisosAgregar());
            ps.setBoolean(4, configuracion.isPermisosModificar());
            ps.setBoolean(5, configuracion.isPermisosAccederVentanas());
            ps.setBoolean(6, configuracion.isPermisosAccederBotones());
            ps.setBoolean(7, configuracion.isConfiguracionActiva());
            ps.setInt(8, configuracion.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar Configuración: " + e.getMessage());
            return false;
        }
    }
}

