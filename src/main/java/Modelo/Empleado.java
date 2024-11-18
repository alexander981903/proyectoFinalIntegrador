/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Clase que representa un empleado en el sistema.
 * Contiene los datos de un empleado, como su ID, nombre, cargo y turno.
 * 
 * @author EMMANUEL
 */
public class Empleado {
    
    private int idEmpleado;
    private String nombreEmp;
    private String cargo;
    private String turno;

    /**
     * Constructor por defecto de la clase Empleado.
     */
    public Empleado() {
    }

    /**
     * Constructor para crear un empleado con nombre, cargo y turno.
     * @param nombreEmp El nombre del empleado.
     * @param cargo El cargo que ocupa el empleado.
     * @param turno El turno en el que trabaja el empleado.
     */
    public Empleado(String nombreEmp, String cargo, String turno) {
        this.nombreEmp = nombreEmp;
        this.cargo = cargo;
        this.turno = turno;
    }

    /**
     * Constructor para crear un empleado con un ID, nombre, cargo y turno.
     * @param idEmpleado El identificador único del empleado.
     * @param nombreEmp El nombre del empleado.
     * @param cargo El cargo que ocupa el empleado.
     * @param turno El turno en el que trabaja el empleado.
     */
    public Empleado(int idEmpleado, String nombreEmp, String cargo, String turno) {
        this.idEmpleado = idEmpleado;
        this.nombreEmp = nombreEmp;
        this.cargo = cargo;
        this.turno = turno;
    }

    /**
     * Obtiene el identificador del empleado.
     * @return El ID del empleado.
     */
    public int getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * Establece el identificador del empleado.
     * @param idEmpleado El ID a asignar al empleado.
     */
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    /**
     * Obtiene el nombre del empleado.
     * @return El nombre del empleado.
     */
    public String getNombreEmp() {
        return nombreEmp;
    }

    /**
     * Establece el nombre del empleado.
     * @param nombreEmp El nombre a asignar al empleado.
     */
    public void setNombreEmp(String nombreEmp) {
        this.nombreEmp = nombreEmp;
    }

    /**
     * Obtiene el cargo del empleado.
     * @return El cargo del empleado.
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Establece el cargo del empleado.
     * @param cargo El cargo a asignar al empleado.
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Obtiene el turno del empleado.
     * @return El turno del empleado.
     */
    public String getTurno() {
        return turno;
    }

    /**
     * Establece el turno del empleado.
     * @param turno El turno a asignar al empleado.
     */
    public void setTurno(String turno) {
        this.turno = turno;
    }
}
