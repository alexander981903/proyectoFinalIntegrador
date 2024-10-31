/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author EMMANUEL
 */
public class Empleado {
    private int idEmpleado;
    private String nombreEmp;
    private String cargo;
    private String turno;

    public Empleado() {
    }

    public Empleado(String nombreEmp, String cargo, String turno) {
        this.nombreEmp = nombreEmp;
        this.cargo = cargo;
        this.turno = turno;
    }
    
    

    public Empleado(int idEmpleado, String nombreEmp, String cargo, String turno) {
        this.idEmpleado = idEmpleado;
        this.nombreEmp = nombreEmp;
        this.cargo = cargo;
        this.turno = turno;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmp() {
        return nombreEmp;
    }

    public void setNombreEmp(String nombreEmp) {
        this.nombreEmp = nombreEmp;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    
}
