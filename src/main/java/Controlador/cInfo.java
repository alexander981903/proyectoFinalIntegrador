/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Pedido;
import Modelo.Producto;
import Vista.vistaInfoPedido;
import dao.ReservaDao;
import java.util.ArrayList;

/**
 * Controlador para gestionar la visualización de la información de pedidos.
 * Se encarga de interactuar con la vista `vistaInfoPedido` y el DAO `ReservaDao`
 * para mostrar la información de los pedidos asociados a una reserva.
 * 
 * @author EMMANUEL
 */
public class cInfo {

    private vistaInfoPedido frameInfo;
    private ReservaDao daoR;

    /**
     * Constructor del controlador `cInfo`.
     * Inicializa la vista de información de pedidos y el DAO de reservas.
     * 
     * @param frameInfo La vista que muestra la información de los pedidos.
     */
    public cInfo(vistaInfoPedido frameInfo) {
        this.frameInfo = frameInfo;
        this.daoR = new ReservaDao();
        this.frameInfo.setControlador(this);
        obtenerPedidoDeReserva();
    }

    /**
     * Obtiene la lista de pedidos asociados a una reserva específica.Este método utiliza el DAO de reservas para recuperar los pedidos vinculados
     * a una reserva.
     * 
     */
    public void obtenerPedidoDeReserva() {
        try {
            int id = frameInfo.getId();
            ArrayList<Producto> productos = daoR.obtenerProductosPorReserva(id);
            frameInfo.mostrarProductos(productos);
        } catch (Exception e) {
            System.err.println("Error al obtener los pedidos de la reserva: " + e.getMessage());
        }
    }
}

