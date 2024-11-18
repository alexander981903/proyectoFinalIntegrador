/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Clase que representa una mesa en el sistema.
 * Contiene los datos de una mesa, como su ID, número, capacidad y nombre.
 * 
 * @author EMMANUEL
 */
public class Mesa {
    
    private int idMesa;
    private int numeroMesa;
    private int capacidad;
    private String estadoMesa;

    /**
     * Constructor por defecto de la clase Mesa.
     */
    public Mesa() {
    }

    /**
     * Constructor para crear una mesa con su ID, número, capacidad y nombre.
     * @param idMesa El identificador único de la mesa.
     * @param numeroMesa El número de la mesa.
     * @param capacidad La capacidad de la mesa.
     */
    public Mesa(int idMesa, int numeroMesa, int capacidad) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
    }

    /**
     * Obtiene el identificador de la mesa.
     * @return El ID de la mesa.
     */
    public int getIdMesa() {
        return idMesa;
    }

    /**
     * Establece el identificador de la mesa.
     * @param idMesa El ID a asignar a la mesa.
     */
    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    /**
     * Obtiene el número de la mesa.
     * @return El número de la mesa.
     */
    public int getNumeroMesa() {
        return numeroMesa;
    }

    /**
     * Establece el número de la mesa.
     * @param numeroMesa El número a asignar a la mesa.
     */
    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    /**
     * Obtiene la capacidad de la mesa.
     * @return La capacidad de la mesa (número de personas que puede acomodar).
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Establece la capacidad de la mesa.
     * @param capacidad La capacidad a asignar a la mesa.
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }
    
    

}

