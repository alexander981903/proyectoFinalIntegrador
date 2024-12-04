/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Clase que representa la configuración general de la aplicación.
 * Correspondiente a la tabla 'configuracion' en la base de datos.
 * 
 * @author EMMANUEL
 */
public class Configuracion {
    
    // Atributos
    private int id;
    private String nombreEmpresa;
    private int cantidadDeMesasParaMostrar;
    private boolean permisosAgregar;
    private boolean permisosModificar;
    private boolean permisosAccederVentanas;
    private boolean permisosAccederBotones;
    private boolean configuracionActiva;

    // Constructor

    public Configuracion() {
    }
    
    
    /**
     * Constructor por defecto para la clase Configuracion.
     * 
     * @param id El identificador único de la configuración.
     * @param nombreEmpresa El nombre de la empresa.
     * @param cantidadDeMesasParaMostrar El número de mesas a mostrar en la interfaz.
     * @param permisosAgregar Permite agregar elementos.
     * @param permisosModificar Permite modificar elementos.
     * @param permisosAccederVentanas Permite acceder a ciertas ventanas.
     * @param permisosAccederBotones Permite acceder a ciertos botones.
     * @param configuracionActiva Indica si la configuración está activa.
     */
    public Configuracion(int id, String nombreEmpresa, int cantidadDeMesasParaMostrar, 
                         boolean permisosAgregar, boolean permisosModificar, 
                         boolean permisosAccederVentanas, boolean permisosAccederBotones, 
                         boolean configuracionActiva) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.cantidadDeMesasParaMostrar = cantidadDeMesasParaMostrar;
        this.permisosAgregar = permisosAgregar;
        this.permisosModificar = permisosModificar;
        this.permisosAccederVentanas = permisosAccederVentanas;
        this.permisosAccederBotones = permisosAccederBotones;
        this.configuracionActiva = configuracionActiva;
    }

    // Getters y Setters
    /**
     * Obtiene el ID de la configuración.
     * 
     * @return El ID de la configuración.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la configuración.
     * 
     * @param id El nuevo ID de la configuración.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la empresa.
     * 
     * @return El nombre de la empresa.
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     * Establece el nombre de la empresa.
     * 
     * @param nombreEmpresa El nuevo nombre de la empresa.
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * Obtiene la cantidad de mesas a mostrar en la interfaz.
     * 
     * @return El número de mesas a mostrar.
     */
    public int getCantidadDeMesasParaMostrar() {
        return cantidadDeMesasParaMostrar;
    }

    /**
     * Establece la cantidad de mesas a mostrar en la interfaz.
     * 
     * @param cantidadDeMesasParaMostrar El nuevo número de mesas a mostrar.
     */
    public void setCantidadDeMesasParaMostrar(int cantidadDeMesasParaMostrar) {
        this.cantidadDeMesasParaMostrar = cantidadDeMesasParaMostrar;
    }

    /**
     * Obtiene si el usuario tiene permiso para agregar elementos.
     * 
     * @return true si tiene permiso para agregar, false en caso contrario.
     */
    public boolean isPermisosAgregar() {    
        return permisosAgregar;
    }

    /**
     * Establece si el usuario tiene permiso para agregar elementos.
     * 
     * @param permisosAgregar El nuevo permiso para agregar.
     */
    public void setPermisosAgregar(boolean permisosAgregar) {
        this.permisosAgregar = permisosAgregar;
    }

    /**
     * Obtiene si el usuario tiene permiso para modificar elementos.
     * 
     * @return true si tiene permiso para modificar, false en caso contrario.
     */
    public boolean isPermisosModificar() {
        return permisosModificar;
    }

    /**
     * Establece si el usuario tiene permiso para modificar elementos.
     * 
     * @param permisosModificar El nuevo permiso para modificar.
     */
    public void setPermisosModificar(boolean permisosModificar) {
        this.permisosModificar = permisosModificar;
    }

    /**
     * Obtiene si el usuario tiene permiso para acceder a las ventanas.
     * 
     * @return true si tiene permiso para acceder a las ventanas, false en caso contrario.
     */
    public boolean isPermisosAccederVentanas() {
        return permisosAccederVentanas;
    }

    /**
     * Establece si el usuario tiene permiso para acceder a las ventanas.
     * 
     * @param permisosAccederVentanas El nuevo permiso para acceder a las ventanas.
     */
    public void setPermisosAccederVentanas(boolean permisosAccederVentanas) {
        this.permisosAccederVentanas = permisosAccederVentanas;
    }

    /**
     * Obtiene si el usuario tiene permiso para acceder a los botones.
     * 
     * @return true si tiene permiso para acceder a los botones, false en caso contrario.
     */
    public boolean isPermisosAccederBotones() {
        return permisosAccederBotones;
    }

    /**
     * Establece si el usuario tiene permiso para acceder a los botones.
     * 
     * @param permisosAccederBotones El nuevo permiso para acceder a los botones.
     */
    public void setPermisosAccederBotones(boolean permisosAccederBotones) {
        this.permisosAccederBotones = permisosAccederBotones;
    }

    /**
     * Obtiene si la configuración está activa.
     * 
     * @return true si la configuración está activa, false en caso contrario.
     */
    public boolean isConfiguracionActiva() {
        return configuracionActiva;
    }

    /**
     * Establece si la configuración está activa.
     * 
     * @param configuracionActiva El nuevo estado de la configuración activa.
     */
    public void setConfiguracionActiva(boolean configuracionActiva) {
        this.configuracionActiva = configuracionActiva;
    }
}

