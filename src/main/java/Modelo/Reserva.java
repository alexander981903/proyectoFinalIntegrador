/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Date;
import java.sql.Time;


/**
 * Clase que representa una reserva en el sistema.
 * Contiene información relacionada con el cliente, la mesa asignada,
 * el estado de la reserva, y detalles de fecha y hora.
 * 
 * @author EMMANUEL
 */
public class Reserva {

    private int idReserva;
    private Date fechaReserva;
    private Time horaReserva;
    private int duracionReserva;
    private String estadoReserva;
    private Cliente cliente;
    private Mesa mesa;
    private Pedido pedido;
    
    public Reserva() {
    }

    /**
     * Constructor con parámetros para inicializar todos los atributos.
     * 
     * @param idReserva Identificador único de la reserva.
     * @param fechaReserva Fecha de la reserva.
     * @param horaReserva Hora de la reserva.
     * @param duracionReserva tiempo de permanencia del cliente.
     * @param estadoReserva Estado de la reserva.
     * @param cliente Cliente asociado a la reserva.
     * @param mesa Mesa asignada a la reserva.
     * @param pedido Pedido asociado a la reserva.
     */
    public Reserva(int idReserva, Date fechaReserva, Time horaReserva, int duracionReserva,String estadoReserva, Cliente cliente, Mesa mesa, Pedido pedido) {
        this.idReserva = idReserva;
        this.fechaReserva = fechaReserva;
        this.horaReserva = horaReserva;
        this.duracionReserva = duracionReserva;
        this.estadoReserva = estadoReserva;
        this.cliente = cliente;
        this.mesa = mesa;
        this.pedido = pedido;
    }

    /**
     * Obtiene el identificador único de la reserva.
     * 
     * @return El identificador de la reserva.
     */
    public int getIdReserva() {
        return idReserva;
    }

    /**
     * Establece el identificador único de la reserva.
     * 
     * @param idReserva El identificador de la reserva.
     */
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    /**
     * Obtiene la fecha de la reserva.
     * 
     * @return La fecha de la reserva.
     */
    public Date getFechaReserva() {
        return fechaReserva;
    }

    /**
     * Establece la fecha de la reserva.
     * 
     * @param fechaReserva La fecha de la reserva.
     */
    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    /**
     * Obtiene la hora de la reserva.
     * 
     * @return La hora de la reserva.
     */
    public Time getHoraReserva() {
        return horaReserva;
    }

    /**
     * Establece la hora de la reserva.
     * 
     * @param horaReserva La hora de la reserva.
     */
    public void setHoraReserva(Time horaReserva) {
        this.horaReserva = horaReserva;
    }

    /**
     * Obtiene el estado actual de la reserva.
     * 
     * @return El estado de la reserva.
     */
    public String getEstadoReserva() {
        return estadoReserva;
    }

    /**
     * Establece el estado actual de la reserva.
     * 
     * @param estadoReserva El estado de la reserva.
     */
    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    /**
     * Obtiene el cliente asociado a la reserva.
     * 
     * @return El cliente asociado a la reserva.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado a la reserva.
     * 
     * @param cliente El cliente asociado a la reserva.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la mesa asignada a la reserva.
     * 
     * @return La mesa asignada a la reserva.
     */
    public Mesa getMesa() {
        return mesa;
    }

    /**
     * Establece la mesa asignada a la reserva.
     * 
     * @param mesa La mesa asignada a la reserva.
     */
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    /**
     * Obtiene el pedido asociado a la reserva.
     * 
     * @return El pedido asociado a la reserva.
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Establece el pedido asociado a la reserva.
     * 
     * @param pedido El pedido asociado a la reserva.
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getDuracionReserva() {
        return duracionReserva;
    }

    public void setDuracionReserva(int duracionReserva) {
        this.duracionReserva = duracionReserva;
    }
    
    
}
