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
 * Clase DAO que maneja las operaciones de base de datos para la entidad Empleado.
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
     * 
     * @param empleado Objeto Empleado a insertar.
     * @return true si la inserción fue exitosa, false de lo contrario.
     */
    public boolean insertarEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleado (nombreEmpleado, cargo, turno) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, empleado.getNombreEmp());
            ps.setString(2, empleado.getCargo());
            ps.setString(3, empleado.getTurno());

            ps.executeUpdate();
            return true;        
        } catch (SQLException e) {
            System.err.println("Error al insertar Empleado en la capa DAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para obtener todos los empleados de la base de datos.
     * 
     * @return Lista de empleados.
     */
    public ArrayList<Empleado> obtenerTodosEmpleados() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("idEmpleado"));
                emp.setNombreEmp(rs.getString("nombreEmpleado"));
                emp.setCargo(rs.getString("cargo"));
                emp.setTurno(rs.getString("turno"));
                empleados.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener empleados: " + e.getMessage());
        }
        return empleados;
    }

    /**
     * Método para obtener un empleado específico de la base de datos por su ID.
     * 
     * @param idEmpleado El ID del empleado que se desea obtener.
     * @return El objeto Empleado correspondiente al ID, o null si no se encuentra.
     */
    public Empleado buscarPorId(int idEmpleado) {
        String sql = "SELECT * FROM empleado WHERE idEmpleado=?";
        try (Connection conn = DataSource.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Retorna un nuevo objeto Empleado con los datos obtenidos
                return new Empleado(
                    rs.getInt("idEmpleado"),
                    rs.getString("nombreEmpleado"),
                    rs.getString("cargo"),
                    rs.getString("turno")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener empleado: " + e.getMessage());
        }
        return null; // Retorna null si no se encuentra el empleado
    }

    /**
     * Método para actualizar los datos de un empleado en la base de datos.
     * 
     * @param emp El objeto Empleado con los datos actualizados.
     * @return true si la actualización fue exitosa, false de lo contrario.
     */
    public boolean actualizarEmpleado(Empleado emp) {
        String sql = "UPDATE empleado SET nombreEmpleado = ?, cargo = ?, turno = ? WHERE idEmpleado = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, emp.getNombreEmp());
            ps.setString(2, emp.getCargo());
            ps.setString(3, emp.getTurno());
            ps.setInt(4, emp.getIdEmpleado());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Método para buscar empleados en la base de datos por su nombre.
     * 
     * @param nombre El nombre que se desea buscar en la base de datos.
     * @return Una lista de objetos Empleado que coinciden con el nombre buscado.
     */
    public ArrayList<Empleado> buscarEmpleadoPorNombre(String nombre) {
        ArrayList<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado WHERE nombreEmpleado LIKE ?";
        
        // Utilizamos el signo de porcentaje '%' para hacer coincidir cualquier parte del nombre
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");  // El '%' indica que puede haber cualquier cosa antes o después del nombre
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Empleado emp = new Empleado();
                    emp.setIdEmpleado(rs.getInt("idEmpleado"));
                    emp.setNombreEmp(rs.getString("nombreEmpleado"));
                    emp.setCargo(rs.getString("cargo"));
                    emp.setTurno(rs.getString("turno"));
                    
                    empleados.add(emp);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar clientes por nombre: " + e.getMessage());
        }
        return empleados;
    }
}
