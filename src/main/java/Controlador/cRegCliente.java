/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author EMMANUEL
 */
import Modelo.Cliente;

import Vista.vistaRegCliente;
import dao.ClienteDao;

/**
 * Clase Controlador que maneja las interacciones entre la Vista y el Modelo para Clientes.
 */
public class cRegCliente {
    private vistaRegCliente vistaRC;
    private ClienteDao dao;

    /**
     * Constructor que inicializa el controlador con la vista de registro de cliente y el DAO.
     * @param vistaRC Objeto vistaRegCliente.
     */
    public cRegCliente(vistaRegCliente vistaRC) {
        this.vistaRC = vistaRC;
        this.dao = new ClienteDao();
        vistaRC.setControladorC(this);
    }

    /**
     * Método para agregar un nuevo cliente.
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

        boolean exito = dao.insertarCliente(cliente);
        if (exito) {
            vistaRC.mostrarMensaje("Cliente agregado exitosamente.");
            
        } else {
            vistaRC.mostrarMensaje("Error al agregar Cliente.");
        }
    }
    
    public void buscarCliente(int idCliente) {
        // Lógica para buscar el cliente por idCliente
        Cliente cliente = dao.buscarPorId(idCliente); // Srecuperamos al cliente desde DAO
        if (cliente != null) {
            // Mostrar o manejar la información del cliente encontrado
            vistaRC.mostrarMensaje("Cliente encontrado: " + cliente.getNombre());
            vistaRC.datosCliente(cliente);
        } else {
            // Si no se encuentra el cliente
            vistaRC.mostrarMensaje("Cliente no encontrado.");
        }
    }
    
    /**
     * Método para actualizar un cliente.
     * @param id Identificador unico de cada cliente
     * @param nombre Nombre del cliente.
     * @param apellido Apellido del cliente.
     * @param email Email del cliente.
     * @param telefono Teléfono del cliente.
     */
    public void actualizarCliente(int id,String nombre, String apellido, String email, String telefono) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(id);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);

        boolean exito = dao.actualizarCliente(cliente);
        if (exito) {
            vistaRC.mostrarMensaje("Cliente actualizado exitosamente.");            
            vistaRC.dispose();
        } else {
            vistaRC.mostrarMensaje("Error al actualizar Cliente.");
        }
    }


    
}
