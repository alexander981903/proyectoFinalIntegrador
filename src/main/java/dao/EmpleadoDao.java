/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Empleado;
import conf.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author EMMANUEL
 */
public class EmpleadoDao {
    private Connection conexion;
    /**
   * Constructor que inicializa la conexión a la base de datos.
   */
    public EmpleadoDao() {
        this.conexion = DataSource.obtenerConexion();
    }
    
    /**
   * Método para insertar un nuevo empleado en la base de datos.
   * @param empleado Objeto Empleado a insertar.
   * @return true si la inserción fue exitosa, false de lo contrario.
   */
    public boolean insertarEmpleado(Empleado empleado){
        String sql = "INSERT INTO empleado (nombreEmpleado, cargo, turno) VALUES (?, ?, ?)";
        //PreparedStatement evitar inyecciones SQL y manejar los parámetros de manera segura
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, empleado.getNombreEmp());
            ps.setString(2, empleado.getCargo());
            ps.setString(3, empleado.getTurno());

            ps.executeUpdate();
            return true;        
        }catch (SQLException e) {
          System.err.println("Error al insertar Empleado En la capaDAO: " + e.getMessage());
          return false;
        }
    }
    
    /**
   * Método para obtener todos los empleados de la base de datos.
   * @return Lista de productos.
   */
    
    public ArrayList<Empleado> obtenerTodosEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        try(PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("idEmpleado"));
                emp.setNombreEmp(rs.getString("nombreEmpleado"));
                emp.setCargo(rs.getString("cargo"));
                emp.setTurno(rs.getString("turno"));
                empleados.add(emp);
            }
        }catch(SQLException e){
            System.err.println("Error al obtener Empleados: " + e.getMessage());
        }
        return empleados;
    }
    
    /**
     * Obtiene un empleado específico de la base de datos.
     * 
     * @param idEmpleado El ID del empleado que se desea obtener.
     * @return El objeto empleado correspondiente al ID, o null si no se encuentra.
     */
     public Empleado buscarPorId(int idEmpleado) {
        String sql = "SELECT * FROM empleado WHERE idEmpleado=?";
        try (Connection conn = DataSource.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Retorna un nuevo objeto Empleado con los datos obtenidos
                return new Empleado (
                    rs.getInt("idEmpleado"),
                    rs.getString("nombreEmpleado"),
                    rs.getString("cargo"),
                    rs.getString("Turno")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener Empleado: " + e.getMessage());
        }
        return null; // Retorna null si no se encuentra el empleado
    }
     
    public boolean actualizarEmpleado(Empleado emp){
        String sql = "UPDATE empleado SET nombreEmpleado = ?, cargo = ?, turno = ? WHERE idEmpleado = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, emp.getNombreEmp());
            ps.setString(2, emp.getCargo());
            ps.setString(3,emp.getTurno());
            ps.setInt(4, emp.getIdEmpleado());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar Empleado: " + e.getMessage());
            return false;
        }
    }
    
}