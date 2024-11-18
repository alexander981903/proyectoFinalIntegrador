/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Clase que representa un producto (PLATO) en el sistema.
 * Contiene información sobre el producto, como su ID, nombre, precios y disponibilidad.
 * 
 * @author EMMANUEL
 */
public class Producto {

    private int idProducto;
    private String nombreProducto;
    private String tamaño;
    private double precioPersonal;
    private double precioFamiliar;
    private boolean disponibilidad;
    private int stock;
    private int cantidad;
    private double precio;

    /**
     * Constructor por defecto de la clase Producto.
     */
    public Producto() {
    }

    /**
     * Constructor para crear un producto con su ID, nombre, precios y disponibilidad.
     * @param idProducto El identificador único del producto.
     * @param nombreProducto El nombre del producto.
     * @param precioPersonal El precio para el tamaño Personal.
     * @param precioFamiliar El precio para el tamaño Familiar.
     * @param disponibilidad La disponibilidad del producto.
     * @param stock cantidad del producto en inventario.
     * @param cantidad cantidad de plato pedido.
     * @param tamaño El tamaño del plato a pedir.
     * @param precio El precio segun el tamaño que elija.
     */
    public Producto(int idProducto, String nombreProducto, double precioPersonal, double precioFamiliar, 
            boolean disponibilidad, int stock, int cantidad, String tamaño, double precio) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioPersonal = precioPersonal;
        this.precioFamiliar = precioFamiliar;
        this.disponibilidad = disponibilidad;
        this.stock = stock;
        this.cantidad = cantidad;
        this.tamaño = tamaño;
        this.precio = precio;
    }

    /**
     * Obtiene el identificador del producto.
     * @return El ID del producto.
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto.
     * @param idProducto El ID a asignar al producto.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre del producto.
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * Establece el nombre del producto.
     * @param nombreProducto El nombre a asignar al producto.
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * Obtiene el precio para el plato de tamaño Personal.
     * @return El precio para el tamaño Personal.
     */
    public double getPrecioPersonal() {
        return precioPersonal;
    }

    /**
     * Establece el precio del palto para el tamaño Personal.
     * @param precioPersonal El precio a asignar al tamaño Personal.
     */
    public void setPrecioPersonal(double precioPersonal) {
        this.precioPersonal = precioPersonal;
    }

    /**
     * Obtiene el precio del plato para el tamaño Familiar.
     * @return El precio para el tamaño Familiar.
     */
    public double getPrecioFamiliar() {
        return precioFamiliar;
    }

    /**
     * Establece el precio del plato para el tamaño Familiar.
     * @param precioFamiliar El precio a asignar al tamaño Familiar.
     */
    public void setPrecioFamiliar(double precioFamiliar) {
        this.precioFamiliar = precioFamiliar;
    }

    /**
     * Obtiene la disponibilidad del producto.
     * @return Verdadero si el producto está disponible, falso si no lo está.
     */
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Establece la disponibilidad del producto.
     * @param disponibilidad La disponibilidad a asignar al producto.
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
    /**
     * Obtiene la cantidad de platillos que se prepara en el dia.
     * @return stock en existencia
     */
    public int getStock() {
        return stock;
    }
    
    /**
     * Establece la cantidad de platos a preparar en el dia
     * @param stock asigna la cantidad en existencia
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
 
}
