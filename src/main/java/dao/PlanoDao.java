/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Mesa;
import Modelo.Plano;
import conf.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase DAO que maneja las operaciones de base de datos para la entidad Plano.
 * Permite insertar, actualizar y obtener planos de la base de datos.
 * 
 * @autor EMMANUEL
 */
public class PlanoDao {
    private Connection conexion;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public PlanoDao() {
        this.conexion = DataSource.obtenerConexion();
    }

    /**
     * Método para insertar un nuevo plano en la base de datos.
     * 
     * @param plano Objeto Plano a insertar en la base de datos.
     * @return true si la inserción fue exitosa, false si ocurrió algún error.
     */
    public boolean insertarPlano(Plano plano) {
        String sql = "INSERT INTO plano (nombrePlano, descripcion, cantidadDeMesas) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, plano.getNombrePlano());
            ps.setString(2, plano.getDescripcion());
            ps.setInt(3, plano.getCantidadDeMesas());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar plano en la capa DAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para obtener todos los planos de la base de datos.
     * 
     * @return Una lista de objetos Plano que contienen los planos registrados.
     */
    public ArrayList<Plano> obtenerTodosPlanos() {
        ArrayList<Plano> planos = new ArrayList<>();
        String sql = "SELECT * FROM plano";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Plano plano = new Plano();
                plano.setIdPlano(rs.getInt("idPlano"));
                plano.setNombrePlano(rs.getString("nombrePlano"));
                plano.setDescripcion(rs.getString("descripcion"));
                plano.setCantidadDeMesas(rs.getInt("cantidadDeMesas"));
                planos.add(plano);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los planos: " + e.getMessage());
        }
        return planos;
    }

    /**
     * Método para actualizar los datos de un plano en la base de datos.
     * 
     * @param plano El objeto Plano con los datos actualizados.
     * @return true si la actualización fue exitosa, false si ocurrió un error.
     */
    public boolean actualizarPlano(Plano plano) {
        String sql = "UPDATE plano SET nombrePlano = ?, descripcion = ?, cantidadDeMesas = ? WHERE idPlano = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, plano.getNombrePlano());
            ps.setString(2, plano.getDescripcion());
            ps.setInt(3, plano.getCantidadDeMesas());
            ps.setInt(4, plano.getIdPlano());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar plano: " + e.getMessage());
            return false;
        }
    }
    
    public boolean asignarMesasAlPlano(int idPlano, ArrayList<Integer> mesasSeleccionadas) {
        String deleteSql = "DELETE FROM mesa_plano WHERE idPlano = ?";
        String insertSql = "INSERT INTO mesa_plano (idMesa, idPlano) VALUES (?, ?)";

        try (PreparedStatement deleteStmt = conexion.prepareStatement(deleteSql);
             PreparedStatement insertStmt = conexion.prepareStatement(insertSql)) {

            // Primero, eliminar todas las mesas asociadas a este plano
            deleteStmt.setInt(1, idPlano);
            deleteStmt.executeUpdate();

            // Luego, asignar las nuevas mesas
            for (Integer idMesa : mesasSeleccionadas) {
                insertStmt.setInt(1, idMesa);
                insertStmt.setInt(2, idPlano);
                insertStmt.addBatch();
            }

            // Ejecutar el batch de inserción de nuevas mesas
            insertStmt.executeBatch();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al asignar mesas al plano: " + e.getMessage());
            return false;
        }
    }

    
    public int obtenerIdPlanoMasReciente() {
        String sql = "SELECT idPlano FROM plano ORDER BY idPlano DESC LIMIT 1";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("idPlano");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el último ID del plano: " + e.getMessage());
        }
        return -1;  // Retorna -1 si no se pudo obtener el ID
    }
    
    
    /**
    * Método para obtener todas las mesas que están asociadas a algún plano.
    * Recupera todas las mesas que existen en la tabla `mesa_plano`, sin necesidad de especificar un plano.
    * 
    * @return Una lista de objetos Mesa que están asociadas a algún plano.
    */
    public ArrayList<Mesa> obtenerMesasPorPlano(int idPlano) {
        ArrayList<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT m.idMesa, m.numeroMesa, m.capacidad, m.estadoMesa " +
                     "FROM mesa m " +
                     "JOIN mesa_plano mp ON m.idMesa = mp.idMesa " +
                     "WHERE mp.idPlano = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Establecer el parámetro del idPlano
            ps.setInt(1, idPlano);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mesa mesa = new Mesa();
                    mesa.setIdMesa(rs.getInt("idMesa"));
                    mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                    mesa.setCapacidad(rs.getInt("capacidad"));
                    mesa.setEstadoMesa(rs.getString("estadoMesa"));

                    mesas.add(mesa);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mesas asociadas al plano: " + e.getMessage());
        }

        return mesas;
    }

    /**
    * Método para obtener un plano específico según su ID.
    * 
    * @param idPlano El ID del plano a recuperar.
    * @return Un objeto Plano con la información del plano solicitado, o null si no se encuentra.
    */
    public Plano obtenerPlanoPorId(int idPlano) {
        Plano plano = null; // Inicializar el objeto como null
        String sql = "SELECT idPlano, nombrePlano, descripcion, cantidadDeMesas FROM plano WHERE idPlano = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Establecer el parámetro del ID
            ps.setInt(1, idPlano);

            // Ejecutar la consulta
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    plano = new Plano();
                    plano.setIdPlano(rs.getInt("idPlano"));
                    plano.setNombrePlano(rs.getString("nombrePlano"));
                    plano.setDescripcion(rs.getString("descripcion"));
                    plano.setCantidadDeMesas(rs.getInt("cantidadDeMesas"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el plano por ID: " + e.getMessage());
        }

        return plano; // Retorna el plano encontrado o null
    }



}
