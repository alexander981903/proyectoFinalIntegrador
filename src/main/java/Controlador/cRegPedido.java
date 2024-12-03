/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Producto;
import Vista.vistaRegPedido;
import dao.PedidoDao;
import dao.ProductoDao;

/**
 *
 * @author EMMANUEL
 */
public class cRegPedido {
    private PedidoDao daoPed;
    private ProductoDao daoP;
    private vistaRegPedido vistaRegPed;

    public cRegPedido(vistaRegPedido vistaRegPed) {
        this.daoPed = new PedidoDao();
        this.daoP = new ProductoDao();
        this.vistaRegPed = vistaRegPed;
        this.vistaRegPed.setControladorRegPed(this);
        getProducto();
    }
    
    /**
    * Método para obtener un producto por su ID utilizando ProductoDao.
    */
   public void getProducto() {
       int id = vistaRegPed.getIdPlato();
       Producto producto = daoP.obtenerProductoPorId(id);
       vistaRegPed.llenarCamposPlato(producto);
   }
   
   public Producto getProductoID(){
       int id = vistaRegPed.getIdPlato();
       Producto producto = daoP.obtenerProductoPorId(id);
       return producto;
   }
    
}
