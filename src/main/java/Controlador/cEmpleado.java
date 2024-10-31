/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Empleado;
import Vista.vistaHome;
import Vista.vistaRegEmpleado;
import dao.EmpleadoDao;
import java.util.ArrayList;


/**
 *
 * @author EMMANUEL
 */

/**
 * Clase Controlador que maneja las interacciones entre la Vista y el Modelo para Empleados.
 */
public class cEmpleado {
    private vistaRegEmpleado vista;
    private vistaHome vista1;
    private EmpleadoDao dao;
    
    /**
   * Constructor que inicializa el controlador con la vista y el DAO.
   * @param vista Objeto vistaRegEmpleado.
   */    
    public cEmpleado (vistaRegEmpleado vista){
        this.vista = vista;
        this.dao = new EmpleadoDao();
        vista.setControlador(this);
    }
    
    public cEmpleado (vistaHome vista1){
        this.vista1=vista1;
        this.dao = new EmpleadoDao();
        vista1.setControlador(this);
        cargarEmpleados();
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
            cargarEmpleados();
        }else{
            vista.mostrarMensaje("Error al agregar Empleado");
        }
    }
    
    /**
   * Método para cargar y mostrar todos los empleados en la vista.
   */
    public void cargarEmpleados() {
        ArrayList<Empleado> empleados = dao.obtenerTodosEmpleados();
        vista1.mostrarEmpleados(empleados);
        
    }  
    
    
}
