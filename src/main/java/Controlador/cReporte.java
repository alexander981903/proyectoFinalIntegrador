/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Pedido;
import Modelo.Producto;
import Modelo.Reserva;
import Vista.vistaHome;
import dao.ProductoDao;
import dao.ReporteDao;
import java.util.ArrayList;
import java.sql.Date;
import javax.swing.JTable;

/**
 * Controlador para manejar las interacciones entre la Vista de Reportes y el Modelo.
 * Se encarga de procesar datos de reportes y mostrarlos en la interfaz.
 * 
 * @author EMMANUEL
 */
public class cReporte {
    private vistaHome vistaH;
    private ReporteDao daoR;
    private ProductoDao daoP;
    private int cantidad;
    

    /**
     * Constructor que inicializa el controlador con la vista de reportes y el DAO de Reportes.
     * 
     * @param vistaH Objeto de tipo `vistaHome`, que representa la interfaz principal de reportes.
     */
    public cReporte(vistaHome vistaH) {
        this.vistaH = vistaH;
        this.daoR = new ReporteDao();
        this.daoP = new ProductoDao();
        this.vistaH.setControladorReporte(this);
    }

    /**
     * Método para obtener y mostrar las reservas en un rango de fechas Especifico.
     * 
     * 
     */
    public void mostrarReporte () {
        String filtro = vistaH.getClienteField().getText();
        cantidad = daoR.getCantidadReservas();
        java.util.Date fechaInicioUtil = vistaH.getFechaInicioUtil();
        java.util.Date fechaFinUtil= vistaH.getFechaFinUtil();
        
        ArrayList<Producto> productos = daoP.obtenerTodosProductos();
        String tipoSeleccionado = (String) vistaH.getTipoReporte().getSelectedItem();
        
        if (tipoSeleccionado.equals("Historial de Reservas")) {
            if(fechaInicioUtil != null && fechaFinUtil != null){
                java.sql.Date fechaI = new java.sql.Date(fechaInicioUtil.getTime());
                java.sql.Date fechaF = new java.sql.Date(fechaFinUtil.getTime());
                ArrayList<Reserva> reservas = daoR.obtenerReservasPorRangoDeFechas(fechaI, fechaF);
                vistaH.mostrarReporte(tipoSeleccionado, reservas); 
            }else{
                vistaH.mostrarMensaje("Los Campos de fecha no deben estar vacios");
            }
            
        } else if (tipoSeleccionado.equals("Informe de inventario")) {
           vistaH.mostrarReporte(tipoSeleccionado, productos);
        } else if (tipoSeleccionado.equals("Reservas por Cliente")) {
            if(fechaInicioUtil != null && fechaFinUtil != null && filtro != null){
                java.sql.Date fechaI = new java.sql.Date(fechaInicioUtil.getTime());
                java.sql.Date fechaF = new java.sql.Date(fechaFinUtil.getTime());
                ArrayList<Cliente> clientes = daoR.obtenerReservasCliente(filtro,fechaI,fechaF);
                vistaH.mostrarReporte(tipoSeleccionado, clientes);
            }else{
                vistaH.mostrarMensaje("Los Campos de fecha y cliente no deben estar vacios");
            }
            
        } else if (tipoSeleccionado.equals("Productos preferidos")) {
           // mostrarReporte(tipoSeleccionado, productosPreferidos);
        }     
    }
    
    public void exportarAExcel(JTable table) {
       daoR.exportarReporteAExcel(table);
    }

    public int getCantidad() {
        return cantidad;
    }
}

