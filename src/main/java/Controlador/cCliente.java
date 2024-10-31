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
import Vista.vistaHome;
import Vista.vistaRegCliente;
import dao.ClienteDao;
import java.util.ArrayList;

/**
 * Clase Controlador que maneja las interacciones entre la Vista y el Modelo para Clientes.
 */
public class cCliente {
    private vistaRegCliente vista;
    private vistaHome vista1;
    private ClienteDao dao;

    /**
     * Constructor que inicializa el controlador con la vista de registro de cliente y el DAO.
     * @param vista Objeto vistaRegCliente.
     */
    public cCliente(vistaRegCliente vista) {
        this.vista = vista;
        this.dao = new ClienteDao();
        vista.setControladorC(this);
    }

    /**
     * Constructor que inicializa el controlador con la vista principal (home) y el DAO.
     * @param vista1 Objeto vistaHome.
     */
    public cCliente(vistaHome vista1) {
        this.vista1 = vista1;
        this.dao = new ClienteDao();
        vista1.setControladorC(this);
        cargarClientes();
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
            vista.mostrarMensaje("Cliente agregado exitosamente.");
            cargarClientes();
        } else {
            vista.mostrarMensaje("Error al agregar Cliente.");
        }
    }

    /**
     * Método para cargar y mostrar todos los clientes en la vista.
     */
    public void cargarClientes() {
        ArrayList<Cliente> clientes = dao.obtenerTodosClientes();
        vista1.mostrarClientes(clientes);
    }
}
