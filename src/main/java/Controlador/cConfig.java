/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Configuracion;
import Vista.*;
import dao.ConfiguracionDao;

/**
 * Clase Controlador que maneja las interacciones entre la Vista y el Modelo para la configuración de la aplicación.
 * Esta clase permite obtener, actualizar y modificar la configuración.
 * 
 * @author EMMANUEL
 */
public class cConfig {

    private vistaHome vistaH;
    private ConfiguracionDao dao;

    /**
     * Constructor que inicializa el controlador con la vista y el DAO.
     * 
     * @param vistaH Objeto de tipo vistaHome que representa la vista principal. 
     */
    public cConfig(vistaHome vistaH) {
        this.vistaH = vistaH;
        this.dao = new ConfiguracionDao();
        this.vistaH.setControladorConf(this);
    }

    /**
     * Método para obtener la configuración actual desde la base de datos y mostrarla en la vista.
     */
    public void obtenerConfiguracion() {
        // Recuperamos la configuración desde el DAO
        Configuracion configuracion = dao.obtenerConfiguracion();
        
        if (configuracion != null) {
            //vistaConfig.mostrarConfiguracion(configuracion);
        } else {
            vistaH.mostrarMensaje("No se encontró configuración actual.");
        }
    }

    /**
     * Método para actualizar los datos de la configuración.
     * Este método permite modificar los valores de la configuración utilizando los nuevos datos proporcionados.
     * 
     * @param nombreEmpresa Nuevo nombre de la empresa.
     * @param cantidadDeMesasParaMostrar Nueva cantidad de mesas a mostrar.
     * @param permisosAgregar Nuevo valor para el permiso de agregar.
     * @param permisosModificar Nuevo valor para el permiso de modificar.
     * @param permisosAccederVentanas Nuevo valor para el permiso de acceder a ventanas.
     * @param permisosAccederBotones Nuevo valor para el permiso de acceder a botones.
     * @param configuracionActiva Nuevo estado de la configuración (activa o no activa).
     */
    public void actualizarConfiguracion(String nombreEmpresa, int cantidadDeMesasParaMostrar, boolean permisosAgregar, 
                                        boolean permisosModificar, boolean permisosAccederVentanas, 
                                        boolean permisosAccederBotones, boolean configuracionActiva) {
        
        // Crear un objeto Configuracion con los nuevos valores
        Configuracion configuracion = new Configuracion();
        configuracion.setNombreEmpresa(nombreEmpresa);
        configuracion.setCantidadDeMesasParaMostrar(cantidadDeMesasParaMostrar);
        configuracion.setPermisosAgregar(permisosAgregar);
        configuracion.setPermisosModificar(permisosModificar);
        configuracion.setPermisosAccederVentanas(permisosAccederVentanas);
        configuracion.setPermisosAccederBotones(permisosAccederBotones);
        configuracion.setConfiguracionActiva(configuracionActiva);
        
        boolean exito = dao.actualizarConfiguracion(configuracion);
        if (exito) {
            vistaH.mostrarMensaje("Configuración actualizada exitosamente.");
            vistaH.dispose();
        } else {
            vistaH.mostrarMensaje("Error al actualizar la configuración.");
        }
    }
}

