/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Clase que representa un plano de un restaurante.
 * Contiene información sobre el nombre del plano, su descripción
 * y la cantidad de mesas asociadas al plano.
 * 
 * @author EMMANUEL
 */
public class Plano {
    private int idPlano;    
    private String nombrePlano;  
    private String descripcion;     
    private int cantidadDeMesas;    

    /**
     * Constructor vacío para la clase Plano.
     * Se utiliza para crear un objeto de plano sin establecer ningún valor inicial.
     */
    public Plano() {
    }

    /**
     * Constructor con parámetros para crear un objeto Plano con todos los datos.
     * 
     * @param idPlano ID único del plano.
     * @param nombrePlano Nombre del plano.
     * @param descripcion Descripción del plano.
     * @param cantidadDeMesas Número de mesas asociadas a este plano.
     */
    public Plano(int idPlano, String nombrePlano, String descripcion, int cantidadDeMesas) {
        this.idPlano = idPlano;
        this.nombrePlano = nombrePlano;
        this.descripcion = descripcion;
        this.cantidadDeMesas = cantidadDeMesas;
    }

    /**
     * Obtiene el ID del plano.
     * 
     * @return El ID del plano.
     */
    public int getIdPlano() {
        return idPlano;
    }

    /**
     * Establece el ID del plano.
     * 
     * @param idPlano El ID del plano a establecer.
     */
    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    /**
     * Obtiene el nombre del plano.
     * 
     * @return El nombre del plano.
     */
    public String getNombrePlano() {
        return nombrePlano;
    }

    /**
     * Establece el nombre del plano.
     * 
     * @param nombrePlano El nombre del plano a establecer.
     */
    public void setNombrePlano(String nombrePlano) {
        this.nombrePlano = nombrePlano;
    }

    /**
     * Obtiene la descripción del plano.
     * 
     * @return La descripción del plano.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del plano.
     * 
     * @param descripcion La descripción del plano a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la cantidad de mesas asociadas al plano.
     * 
     * @return La cantidad de mesas en el plano.
     */
    public int getCantidadDeMesas() {
        return cantidadDeMesas;
    }

    /**
     * Establece la cantidad de mesas asociadas al plano.
     * 
     * @param cantidadDeMesas La cantidad de mesas a establecer en el plano.
     */
    public void setCantidadDeMesas(int cantidadDeMesas) {
        this.cantidadDeMesas = cantidadDeMesas;
    }
}