/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Producto;
import Vista.vistaRegReserva;
import dao.ClienteDao;
import dao.PedidoDao;
import dao.ProductoDao;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


/**
 * Controlador para el registro de la reserva y el pedido.
 * Se encarga de manejar la lógica entre la vista y los modelos para realizar pedidos y reservas.
 * 
 * @author EMMANUEL
 */
public class cRegReserva {
    private ProductoDao daoP;
    private ClienteDao daoC;
    private PedidoDao daoPedido;
    private vistaRegReserva vistaRR;

    public cRegReserva(vistaRegReserva vistaRR) {
        this.daoP = new ProductoDao();
        this.daoC = new ClienteDao();
        this.daoPedido = new PedidoDao();
        this.vistaRR = vistaRR;
        this.vistaRR.setControladorRR(this);
        cargarProductos();
        cargarComboboxClientes();
    }
    
    /**
     * Carga los productos disponibles en la vista de reserva.
     * Recupera los productos de la base de datos que están disponibles y los muestra en la vista.
     */
    public void cargarProductos() {
        ArrayList<Producto> productos = daoP.obtenerProductoDisponible();
        vistaRR.mostrarProductos(productos);
    }
    
    /**
     * Carga todos los clientes en el ComboBox de la vista de registro de reserva.
     * Recupera todos los clientes de la base de datos a través del DAO de Cliente
     * y los muestra en el ComboBox correspondiente en la vista.
     */
    public void cargarComboboxClientes() {
        ArrayList<Cliente> clientes = daoC.obtenerTodosClientes();
        vistaRR.mostrarClientesEnComboBox(clientes);
    }

    /**
     * Realiza el pedido y la reserva.Este método recoge los datos de la vista y luego llama al método correspondiente 
 en el DAO de Pedido para realizar el pedido y la reserva en la base de datos.
     * 
     * @param idCliente ID del cliente que realiza la reserva.
     * @param idMesa ID de la mesa reservada.
     * @param productos Lista de productos seleccionados para el pedido.
     * @param fechaReserva Fecha de la reserva.
     * @param horaReserva Hora de la reserva.
     * @param duracionReserva Duración de la reserva en minutos.
     * @return true si el pedido y la reserva fueron realizados correctamente, false si hubo un error.
     */
    public boolean realizarPedidoYReserva(int idCliente, int idMesa, ArrayList<Producto> productos, Date fechaReserva, Time horaReserva, int duracionReserva) {
        // Llamar al método realizarPedidoYReserva del DAO de Pedido
        boolean resultado = daoPedido.realizarPedidoYReserva(idCliente, new Date(System.currentTimeMillis()), 
                idMesa, productos, fechaReserva, horaReserva, duracionReserva);
        
        // Verificar el resultado y actualizar la vista en consecuencia
        if (resultado) {
            vistaRR.mostrarMensaje("Reserva y pedido realizados con éxito.");
        } else {
            vistaRR.mostrarMensaje("Hubo un error al realizar la reserva y el pedido.");
        }
        
        return resultado;
    }
    
    
    /**
    * Convierte las filas de la tabla pedidosTable en una lista de objetos Producto.
    * Este método recorre todas las filas del modelo de la tabla pedidosTableModel, 
    * y para cada fila, extrae la información de las columnas correspondientes 
    * y crea un objeto Producto. Finalmente, agrega cada producto a una lista 
    * de productos.
    *
    * @return Una lista de objetos Producto con los datos extraídos de la tabla pedidosTable.
    */
    public ArrayList<Producto> obtenerProductosDeTabla() {
        ArrayList<Producto> productos = new ArrayList<>();
        DefaultTableModel pedidosTableModel = vistaRR.getPedidosTableModel();
        for (int i = 0; i < pedidosTableModel.getRowCount(); i++) {
            int id = (int) pedidosTableModel.getValueAt(i, 0);
            String nombre = (String) pedidosTableModel.getValueAt(i, 1);
            String tamaño = (String) pedidosTableModel.getValueAt(i, 2);
            int cantidad = (int) pedidosTableModel.getValueAt(i, 3);
            double precio = (double) pedidosTableModel.getValueAt(i, 4);

            // Crear el producto con los datos necesarios 
            Producto producto = new Producto();
            producto.setIdProducto(id);
            producto.setNombreProducto(nombre);
            producto.setTamaño(tamaño);
            producto.setCantidad(cantidad);
            producto.setPrecio(precio);

            productos.add(producto);
        }
        return productos;
    }
}

