/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author EMMANUEL
 */
public class Usuario {
    private String login;
    private String clave;
    private Empleado emp;
    private Cliente cliente;

    public Usuario() {
    }

    public Usuario(String login, String clave, Empleado emp) {
        this.login = login;
        this.clave = clave;   
        this.emp = emp;
    }

    public Usuario(String login, String clave, Cliente cliente) {
        this.login = login;
        this.clave = clave;
        this.cliente = cliente;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return emp.getCargo();
    }

    public Empleado getEmp() {
        return emp;
    }

    public void setEmp(Empleado emp) {
        this.emp = emp;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }   
}