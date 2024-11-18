/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Clase que representa un reporte en el sistema.
 * Un reporte contiene información sobre el tipo de reporte, el contenido y el empleado que lo generó.
 * 
 * @author EMMANUEL
 */
public class Reporte {

    private int idReporte;
    private String fechaCreacion;
    private String tipo;
    private String contenido;
    private Empleado Empleado;

    /**
     * Constructor por defecto de la clase Reporte.
     */
    public Reporte() {
    }

    /**
     * Constructor para crear un reporte con todos sus atributos.
     * @param idReporte El identificador único del reporte.
     * @param fechaCreacion La fecha en que se creó el reporte.
     * @param tipo El tipo de reporte (por ejemplo, reporte de reservas o de pedidos).
     * @param contenido El contenido del reporte.
     * @param Empleado El empleado que generó el reporte.
     */
    public Reporte(int idReporte, String fechaCreacion, String tipo, String contenido, Empleado Empleado) {
        this.idReporte = idReporte;
        this.fechaCreacion = fechaCreacion;
        this.tipo = tipo;
        this.contenido = contenido;
        this.Empleado = Empleado;
    }

    /**
     * Obtiene el contenido del reporte.
     * @return El contenido del reporte.
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Establece el contenido del reporte.
     * @param contenido El contenido a asignar al reporte.
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtiene el identificador del reporte.
     * @return El ID del reporte.
     */
    public int getIdReporte() {
        return idReporte;
    }

    /**
     * Establece el identificador del reporte.
     * @param idReporte El ID a asignar al reporte.
     */
    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    /**
     * Obtiene la fecha de creación del reporte.
     * @return La fecha de creación del reporte.
     */
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del reporte.
     * @param fechaCreacion La fecha a asignar al reporte.
     */
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Obtiene el tipo del reporte.
     * @return El tipo de reporte.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del reporte.
     * @param tipo El tipo a asignar al reporte.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el empleado que generó el reporte.
     * @return El empleado que generó el reporte.
     */
    public Empleado getEmpleado() {
        return Empleado;
    }

    /**
     * Establece el empleado que generó el reporte.
     * @param Empleado El empleado a asignar al reporte.
     */
    public void setEmpleado(Empleado Empleado) {
        this.Empleado = Empleado;
    }
}
