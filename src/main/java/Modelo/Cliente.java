/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Clase que representa un Cliente.
 * 
 * Esta clase contiene información sobre un cliente, incluyendo su ID, nombre, apellido,
 * email y teléfono. Proporciona métodos para acceder y modificar estos datos.
 * 
 * @author EMMANUEL
 */
public class Cliente {
    private int idCliente;
    private String nombre;
    private String apellido;
    private String Email;
    private String telefono;
    private String Dni;
    
    /**
     * Constructor por defecto.
     */
    
    public Cliente() {
    }
    
    /**
     * Constructor que inicializa un Cliente con los datos especificados.
     * 
     * @param idCliente ID único del cliente
     * @param nombre Nombre del cliente
     * @param apellido Apellido del cliente
     * @param Email Email del cliente
     * @param telefono Número de teléfono del cliente
     * @param Dni Documento Nacional de Identidad
     */
    public Cliente(int idCliente, String nombre, String apellido, String Email, String telefono, String Dni) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.Email = Email;
        this.telefono = telefono;
        this.Dni = Dni;
    }
    
     /**
     * Obtiene el ID del cliente.
     * 
     * @return ID del cliente
     */
    public int getIdCliente() {
        return idCliente;
    }
    
    /**
     * Establece el ID del cliente.
     * 
     * @param idCliente ID del cliente a establecer
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
     /**
     * Obtiene el nombre del cliente.
     * 
     * @return Nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre del cliente.
     * 
     * @param nombre Nombre del cliente a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el apellido del cliente.
     * 
     * @return Apellido del cliente
     */
    public String getApellido() {
        return apellido;
    }
    
    /**
     * Establece el apellido del cliente.
     * 
     * @param apellido Apellido del cliente a establecer
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    /**
     * Obtiene el email del cliente.
     * 
     * @return Email del cliente
     */
    public String getEmail() {
        return Email;
    }
    
    /**
     * Establece el email del cliente.
     * 
     * @param Email Email del cliente a establecer
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }
    
    /**
     * Obtiene el número de teléfono del cliente.
     * 
     * @return Número de teléfono del cliente
     */
    public String getTelefono() {
        return telefono;
    }
    
    /**
     * Establece el número de teléfono del cliente.
     * 
     * @param telefono Número de teléfono del cliente a establecer
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return Dni;
    }

    public void setDni(String Dni) {
        this.Dni = Dni;
    }
    
    
}
