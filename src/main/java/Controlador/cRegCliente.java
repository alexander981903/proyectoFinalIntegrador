/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cliente;
import Vista.vistaHome;
import Vista.vistaRegCliente;
import dao.ClienteDao;

/**
 * Clase Controlador que maneja las interacciones entre la Vista y el Modelo para Clientes.
 * Esta clase permite agregar, buscar y actualizar clientes.
 * 
 * @author EMMANUEL
 */
public class cRegCliente {
    private vistaRegCliente vistaRC;
    private ClienteDao dao;
    
    /**
     * Constructor que inicializa el controlador con la vista de registro de cliente y el DAO.
     * 
     * @param vistaRC Objeto de tipo vistaRegCliente que representa la vista de registro de cliente.
     */
    public cRegCliente(vistaRegCliente vistaRC) {
        this.vistaRC = vistaRC;
        this.dao = new ClienteDao();
        vistaRC.setControladorC(this);
    }

    /**
     * Método para agregar un nuevo cliente.
     * Este método crea un objeto Cliente con los datos proporcionados y los inserta
     * en la base de datos utilizando el DAO de Cliente.
     * 
     * @param nombre Nombre del cliente.
     * @param apellido Apellido del cliente.
     * @param email Email del cliente.
     * @param telefono Teléfono del cliente.
     */
    public void agregarCliente(String nombre, String apellido, String email, String telefono) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);

        // Intentamos insertar el cliente en la base de datos
        boolean exito = dao.insertarCliente(cliente);
        if (exito) {
            vistaRC.mostrarMensaje("Cliente agregado exitosamente.");
        } else {
            vistaRC.mostrarMensaje("Error al agregar Cliente.");
        }
    }
    
    /**
     * Método para buscar un cliente por su ID.
     * Este método busca al cliente y muestra sus detalles en la vista
     * si el cliente se encuentra.
     * 
     * @param idCliente El identificador único del cliente que se desea buscar.
     */
    public void buscarCliente(int idCliente) {
        // Recuperamos al cliente desde el DAO
        Cliente cliente = dao.buscarPorId(idCliente);
        
        if (cliente != null) {
            // Si el cliente se encuentra, mostramos la información en la vista
            vistaRC.mostrarMensaje("Cliente encontrado: " + cliente.getNombre());
            vistaRC.datosCliente(cliente);
        } else {
            // Si no se encuentra el cliente, mostramos un mensaje de error
            vistaRC.mostrarMensaje("Cliente no encontrado.");
        }
    }
    
    /**
     * Método para actualizar los datos de un cliente.
     * Este método permite modificar los datos de un cliente
     * utilizando su identificador único.
     * 
     * @param id Identificador único del cliente.
     * @param nombre Nuevo nombre del cliente.
     * @param apellido Nuevo apellido del cliente.
     * @param email Nuevo email del cliente.
     * @param telefono Nuevo teléfono del cliente.
     */
    public void actualizarCliente(int id, String nombre, String apellido, String email, String telefono) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(id);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);

        // Intentamos actualizar el cliente en la base de datos desde dao
        boolean exito = dao.actualizarCliente(cliente);
        if (exito) {
            vistaRC.mostrarMensaje("Cliente actualizado exitosamente.");
            vistaRC.dispose(); // Cerramos la ventana después de la actualización
        } else {
            vistaRC.mostrarMensaje("Error al actualizar Cliente.");
        }
    }
}

