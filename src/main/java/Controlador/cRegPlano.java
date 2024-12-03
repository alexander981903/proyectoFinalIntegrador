/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Plano;
import Vista.vistaPlano;
import dao.PlanoDao;
import java.util.ArrayList;

/**
 * Clase Controlador que maneja las interacciones entre la Vista y el Modelo para Planos.
 * Esta clase permite agregar, buscar y actualizar planos en la base de datos.
 * 
 * @author EMMANUEL
 */
public class cRegPlano {
    private vistaPlano vistaRP; // Vista de registro de plano
    private PlanoDao dao;          // DAO para gestionar planos en la base de datos
    
    /**
     * Constructor que inicializa el controlador con la vista de registro de plano y el DAO.
     * 
     * @param vistaRP Objeto de tipo vistaRegPlano que representa la vista de registro de plano.
     */
    public cRegPlano(vistaPlano vistaRP) {
        this.vistaRP = vistaRP;
        this.dao = new PlanoDao();
        vistaRP.setControladorPlano(this);
    }

    /**
     * Método para agregar un nuevo plano.
     * Este método crea un objeto Plano con los datos proporcionados y lo inserta
     * en la base de datos utilizando el DAO de Plano.
     * 
     * @param nombrePlano Nombre del plano (por ejemplo, "Terraza").
     * @param descripcion Descripción del plano (opcional).
     * @param cantidadDeMesas Número de mesas asociadas al plano.
     */
    public void agregarPlano(String nombrePlano, String descripcion, int cantidadDeMesas) {
        Plano plano = new Plano();
        plano.setNombrePlano(nombrePlano);
        plano.setDescripcion(descripcion);
        plano.setCantidadDeMesas(cantidadDeMesas);

        // Intentamos insertar el plano en la base de datos
        boolean exito = dao.insertarPlano(plano);
        if (exito) {
            vistaRP.mostrarMensaje("Plano agregado exitosamente.");
        } else {
            vistaRP.mostrarMensaje("Error al agregar el plano.");
        }
    }    

    /**
     * Método para actualizar los datos de un plano.
     * Este método permite modificar los datos de un plano
     * utilizando su identificador único.
     * 
     * @param idPlano Identificador único del plano.
     * @param nombrePlano Nuevo nombre del plano.
     * @param descripcion Nueva descripción del plano.
     * @param cantidadDeMesas Nueva cantidad de mesas asociadas al plano.
     */
    public void actualizarPlano(int idPlano, String nombrePlano, String descripcion, int cantidadDeMesas) {
        Plano plano = new Plano();
        plano.setIdPlano(idPlano);
        plano.setNombrePlano(nombrePlano);
        plano.setDescripcion(descripcion);
        plano.setCantidadDeMesas(cantidadDeMesas);

        // Intentamos actualizar el plano en la base de datos desde dao
        boolean exito = dao.actualizarPlano(plano);
        if (exito) {
            vistaRP.mostrarMensaje("Plano actualizado exitosamente.");
            vistaRP.dispose(); // Cerramos la ventana después de la actualización
        } else {
            vistaRP.mostrarMensaje("Error al actualizar el plano.");
        }
    }
    
    /**
    * Asigna las mesas seleccionadas a un plano específico en la base de datos.
    * 
    * Este método obtiene las mesas seleccionadas por el usuario y las asocia
    * al plano cuyo ID es proporcionado. Llama al método de DAO para realizar la inserción
    * de las relaciones mesa-plano en la tabla `mesa_plano`.
    * 
    * @param idPlano El ID del plano al que se asignarán las mesas.
    * @param mesasSeleccionadas Una lista de IDs de las mesas que se asignarán al plano.
    */
    public void asignarMesasAlPlano(int idPlano, ArrayList<Integer> mesasSeleccionadas) {
        // Verificamos si se han seleccionado mesas
        if (mesasSeleccionadas == null || mesasSeleccionadas.isEmpty()) {
            vistaRP.mostrarMensaje("No se han seleccionado mesas para asignar al plano.");
            return;
        }

        // Llamar al método de DAO para asignar las mesas al plano
        boolean exito = dao.asignarMesasAlPlano(idPlano, mesasSeleccionadas);

        if (exito) {
            vistaRP.mostrarMensaje("Mesas asignadas al plano correctamente.");
        } else {
            vistaRP.mostrarMensaje("Error al asignar mesas al plano.");
        }
    }
    
    public void llenarDatosAModificar(){
        int id = dao.obtenerIdPlanoMasReciente();
        Plano plano = dao.obtenerPlanoPorId(id);
        if (plano != null) {
            String nombrePlano = plano.getNombrePlano();
            String descripcion = plano.getDescripcion();
            int cantidad = plano.getCantidadDeMesas();
            vistaRP.llenarDatos(nombrePlano, descripcion, cantidad);
            vistaRP.mostrarMensaje("Datos Para Modificar Recuperados");
        } else {
            vistaRP.mostrarMensaje("No se encontró el plano.");
        }
    }
    
}

