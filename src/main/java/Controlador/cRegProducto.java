/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Producto;
import Vista.vistaRegProducto;
import dao.ProductoDao;

/**
 *
 * @author EMMANUEL
 */
public class cRegProducto {
    
    private vistaRegProducto vistaRP;
    private ProductoDao daoP;
    
    public cRegProducto(vistaRegProducto vistaRP){
        this.vistaRP = vistaRP;
        this.daoP = new ProductoDao();
        this.vistaRP.setControlerRP(this);
    }
    
    
    public void agregarProducto (String nombrePlato,double precio_Personal, 
            double precio_Familiar,boolean disponibilidad, int stock ){
        Producto p = new Producto();
        p.setNombreProducto(nombrePlato);
        p.setPrecioPersonal(precio_Personal);
        p.setPrecioFamiliar(precio_Familiar);
        p.setDisponibilidad(disponibilidad);
        p.setStock(stock);
        
        boolean exito = daoP.insertarProductos(p);
        if(exito){
            vistaRP.mostrarMensaje("Platillo agregado correctamente al menu");
        }else{
            vistaRP.mostrarMensaje("Error al agregar el platillo al menu");
        }
    }
    
    public void actualizarProducto(int idProducto, String nombre, double precioPersonal, 
            double precioFamiliar, boolean disponibilidad, int stock){
        boolean exito = daoP.actualizarProductoPorId(idProducto, nombre, precioPersonal, precioFamiliar, disponibilidad, stock);
        if(exito){
            vistaRP.mostrarMensaje("Plato Actualizado Correctamente");
        }else{
            vistaRP.mostrarMensaje("Error al Actualizar");
        }
    }
    
    public void llenarDatosParaActualizar(){
        Producto p = daoP.obtenerProductoPorId(vistaRP.getIdPlato());
        vistaRP.getNombrePlatoField().setText(p.getNombreProducto());
        vistaRP.getStockField().setText(String.valueOf(p.getStock()));
        vistaRP.getPrecioFamiliarField().setText(String.valueOf(p.getPrecioFamiliar()));
        vistaRP.getPrecioPersonalField().setText(String.valueOf(p.getPrecioPersonal()));
        
    }
}
