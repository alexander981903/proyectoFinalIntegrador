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
}
