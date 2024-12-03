/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Clase que representa un pedido realizado por un cliente.
 * Contiene información sobre el pedido, como su ID, fecha, estado, el cliente que realizó el pedido y los productos asociados.
 * 
 * @author EMMANUEL
 */
public class Pedido {
    
    private int idPedido;
    private Date fechaPedido;
    private String estado;
    private Cliente cliente;
    private double total;
    private ArrayList<Producto> producto;

    /**
     * Constructor por defecto de la clase Pedido.
     */
    public Pedido() {
    }

    /**
     * Constructor para crear un pedido con su ID, fecha, estado, cliente y productos.
     * @param idPedido El identificador único del pedido.
     * @param fechaPedido La fecha en que se realizó el pedido.
     * @param estado El estado del pedido.
     * @param cliente El cliente que realizó el pedido.
     * @param producto La lista de productos asociados al pedido.
     * @param total
     */
    public Pedido(int idPedido, Date fechaPedido, String estado, Cliente cliente, ArrayList<Producto> producto, double total) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.cliente = cliente;
        this.producto = producto;
        this.total = total;
    }

    /**
     * Obtiene el identificador del pedido.
     * @return El ID del pedido.
     */
    public int getIdPedido() {
        return idPedido;
    }

    /**
     * Establece el identificador del pedido.
     * @param idPedido El ID a asignar al pedido.
     */
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    /**
     * Obtiene la fecha en que se realizó el pedido.
     * @return La fecha del pedido.
     */
    public Date getFechaPedido() {
        return fechaPedido;
    }

    /**
     * Establece la fecha en que se realizó el pedido.
     * @param fechaPedido La fecha a asignar al pedido.
     */
    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    /**
     * Obtiene el estado del pedido.
     * @return El estado del pedido.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del pedido.
     * @param estado El estado a asignar al pedido.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el cliente que realizó el pedido.
     * @return El cliente del pedido.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente que realizó el pedido.
     * @param cliente El cliente a asignar al pedido.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la lista de productos asociados al pedido.
     * @return La lista de productos del pedido.
     */
    public ArrayList<Producto> getProducto() {
        return producto;
    }

    /**
     * Establece la lista de productos asociados al pedido.
     * @param producto La lista de productos a asignar al pedido.
     */
    public void setProducto(ArrayList<Producto> producto) {
        this.producto = producto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}