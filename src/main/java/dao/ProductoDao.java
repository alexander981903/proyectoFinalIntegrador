/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Producto;
import conf.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase DAO que gestiona las operaciones de bases de datos con la entidad Producto
 *
 * @author EMMANUEL
 */
public class ProductoDao {
    private Connection conexion;
    
    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public ProductoDao() {
        this.conexion = DataSource.obtenerConexion();
    }
    
    /**
     * Metodo para insertar un nuevo producto (platos) en la base de datos
     * 
     */
    public boolean insertarProductos(Producto producto){
        String sql = "INSERT INTO producto (nombrePlato,precio_personal, precio_familiar,disponibilidad,stock) VALUES (?,?,?,?,?)";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, producto.getNombreProducto());
            ps.setDouble(2, producto.getPrecioPersonal());
            ps.setDouble(3, producto.getPrecioFamiliar());
            ps.setBoolean(4, producto.isDisponibilidad());
            ps.setInt(5, producto.getStock());
            
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.err.println("Error al insertar Plato: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Metodo para obtener todos los Productos(PLATOS) en la base de datos.
     * 
     * @return Lista de Productos(PLATO)
     */
    public ArrayList<Producto> obtenerTodosProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try(PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombreProducto(rs.getString("nombrePlato"));
                p.setPrecioPersonal(rs.getDouble("precio_personal"));
                p.setPrecioFamiliar(rs.getDouble("precio_familiar"));
                p.setDisponibilidad(rs.getBoolean("disponibilidad"));
                p.setStock(rs.getInt("stock"));
                productos.add(p);
            }
        }catch(SQLException e){
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }
    
    public void actualizarDisponibilidad(int idProducto, boolean disponibilidad){
        String sql = "UPDATE producto SET disponibilidad = ? WHERE idProducto = ?";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setBoolean(1, disponibilidad);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Error al actualizar Disponibilidad: " + e.getMessage());
        }
    }
    
    public ArrayList<Producto> obtenerProductoDisponible(){
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE disponibilidad = 1";
        try(PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombreProducto(rs.getString("nombrePlato"));
                p.setPrecioPersonal(rs.getDouble("precio_personal"));
                p.setPrecioFamiliar(rs.getDouble("precio_familiar"));
                p.setDisponibilidad(rs.getBoolean("disponibilidad"));
                p.setStock(rs.getInt("stock"));
                productos.add(p);
            }
        }catch(SQLException e){
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }
    
    /**
    * Metodo para obtener un producto por su ID.
    * 
    * @param idProducto El ID del producto a buscar.
    * @return El producto con el ID especificado, o null si no se encuentra.
    */
   public Producto obtenerProductoPorId(int idProducto) {
       String sql = "SELECT * FROM producto WHERE idProducto = ?";
       try (PreparedStatement ps = conexion.prepareStatement(sql)) {
           ps.setInt(1, idProducto);
           try (ResultSet rs = ps.executeQuery()) {
               if (rs.next()) {
                   Producto producto = new Producto();
                   producto.setIdProducto(rs.getInt("idProducto"));
                   producto.setNombreProducto(rs.getString("nombrePlato"));
                   producto.setPrecioPersonal(rs.getDouble("precio_personal"));
                   producto.setPrecioFamiliar(rs.getDouble("precio_familiar"));
                   producto.setDisponibilidad(rs.getBoolean("disponibilidad"));
                   producto.setStock(rs.getInt("stock"));
                   return producto;
               }
           }
       } catch (SQLException e) {
           System.err.println("Error al obtener producto por ID: " + e.getMessage());
       }
       return null; // Retorna null si no se encuentra el producto o hay un error
   }

}
