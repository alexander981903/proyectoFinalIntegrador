/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Empleado;
import Vista.vistaRegEmpleado;
import dao.EmpleadoDao;


/**
 *
 * @author EMMANUEL
 */

/**
 * Clase Controlador que maneja las interacciones entre la Vista y el Modelo para Empleados.
 */
public class cRegEmpleado {
    private vistaRegEmpleado vista;
    private EmpleadoDao dao;
    
    /**
   * Constructor que inicializa el controlador con la vista y el DAO.
   * @param vista Objeto vistaRegEmpleado.
   */    
    public cRegEmpleado (vistaRegEmpleado vista){
        this.vista = vista;
        this.dao = new EmpleadoDao();
        vista.setControladorE(this);
    }
        
    
    /**
   * Método para agregar un nuevo empleado.
   * @param nombreEmp Nombre del empleado.
   * @param cargo Descripción del empleado.
   * @param turno Precio del empleado.
   */
    public void agregarEmpleado(String nombreEmp, String cargo, String turno){
        Empleado emp = new Empleado();
        emp.setNombreEmp(nombreEmp);
        emp.setCargo(cargo);
        emp.setTurno(turno);
        boolean exito = dao.insertarEmpleado(emp);
        if(exito){
            vista.mostrarMensaje("Empleado agregado exitosamente.");
        }else{
            vista.mostrarMensaje("Error al agregar Empleado");
        }
    }
    
    
    public void buscarEmpleadoxId(int idEmpleado) {
        // Lógica para buscar el empleado por idEmpleado
        Empleado emp = dao.buscarPorId(idEmpleado); // Srecuperamos al cliente desde DAO
        if (emp != null) {
            // Mostrar o manejar la información del cliente encontrado
            vista.mostrarMensaje("Empleado encontrado: " + emp.getNombreEmp());
            vista.datosEmpleado(emp);
        } else {
            // Si no se encuentra el cliente
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }
    
    /**
     * Método para actualizar un empleado.
     * @param id Identificador unico de cada empleado.
     * @param nombre Nombre del empleado.
     * @param cargo Cargo que dispone el empleado.
     * @param turno Turno que Dispone el empleado.
     */
    public void actualizarEmpleado(int id,String nombre, String cargo, String turno) {
        Empleado emp = new Empleado();
        emp.setIdEmpleado(id);
        emp.setNombreEmp(nombre);
        emp.setCargo(cargo);
        emp.setTurno(turno);

        boolean exito = dao.actualizarEmpleado(emp);
        if (exito) {
            vista.mostrarMensaje("Empleado actualizado exitosamente.");            
            vista.dispose();
        } else {
            vista.mostrarMensaje("Error al actualizar Empleado.");
        }
    }
}
