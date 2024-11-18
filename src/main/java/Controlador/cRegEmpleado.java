/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Empleado;
import Vista.vistaRegEmpleado;
import dao.EmpleadoDao;

/**
 * Clase Controlador que maneja las interacciones entre la Vista y el Modelo para Empleados.
 * Esta clase permite realizar operaciones como agregar, buscar y actualizar empleados.
 * 
 * @author EMMANUEL
 */
public class cRegEmpleado {
    private vistaRegEmpleado vista;
    private EmpleadoDao dao;
    
    /**
     * Constructor que inicializa el controlador con la vista y el DAO de empleado.
     * 
     * @param vista Objeto de tipo vistaRegEmpleado que representa la vista de registro de empleado.
     */
    public cRegEmpleado(vistaRegEmpleado vista) {
        this.vista = vista;
        this.dao = new EmpleadoDao();
        vista.setControladorE(this);
    }
        
    /**
     * Método para agregar un nuevo empleado.
     * Este método crea un objeto Empleado con los datos proporcionados y los inserta
     * en la base de datos utilizando el DAO de Empleado.
     * 
     * @param nombreEmp Nombre del empleado.
     * @param cargo Cargo o puesto del empleado.
     * @param turno Turno en el que trabaja el empleado.
     */
    public void agregarEmpleado(String nombreEmp, String cargo, String turno) {
        Empleado emp = new Empleado();
        emp.setNombreEmp(nombreEmp);
        emp.setCargo(cargo);
        emp.setTurno(turno);
        
        // Intentamos insertar el empleado en la base de datos
        boolean exito = dao.insertarEmpleado(emp);
        if (exito) {
            vista.mostrarMensaje("Empleado agregado exitosamente.");
        } else {
            vista.mostrarMensaje("Error al agregar Empleado.");
        }
    }
    
    /**
     * Método para buscar un empleado por su ID.
     * Este método busca al empleado en la base de datos utilizando el ID proporcionado
     * y muestra sus detalles en la vista si el empleado se encuentra.
     * 
     * @param idEmpleado El identificador único del empleado que se desea buscar.
     */
    public void buscarEmpleadoxId(int idEmpleado) {
        // Recuperamos el empleado desde el DAO
        Empleado emp = dao.buscarPorId(idEmpleado);
        
        if (emp != null) {
            // Si el empleado se encuentra, mostramos la información en la vista
            vista.mostrarMensaje("Empleado encontrado: " + emp.getNombreEmp());
            vista.datosEmpleado(emp);
        } else {
            // Si no se encuentra el empleado, mostramos un mensaje de error
            vista.mostrarMensaje("Empleado no encontrado.");
        }
    }
    
    /**
     * Método para actualizar los datos de un empleado.
     * Este método permite modificar los datos de un empleado en la base de datos
     * utilizando su identificador único.
     * 
     * @param id Identificador único del empleado.
     * @param nombre Nuevo nombre del empleado.
     * @param cargo Nuevo cargo del empleado.
     * @param turno Nuevo turno del empleado.
     */
    public void actualizarEmpleado(int id, String nombre, String cargo, String turno) {
        Empleado emp = new Empleado();
        emp.setIdEmpleado(id);
        emp.setNombreEmp(nombre);
        emp.setCargo(cargo);
        emp.setTurno(turno);

        // Intentamos actualizar el empleado en la base de datos
        boolean exito = dao.actualizarEmpleado(emp);
        if (exito) {
            vista.mostrarMensaje("Empleado actualizado exitosamente.");
            vista.dispose(); // Cerramos la ventana después de la actualización
        } else {
            vista.mostrarMensaje("Error al actualizar Empleado.");
        }
    }
}