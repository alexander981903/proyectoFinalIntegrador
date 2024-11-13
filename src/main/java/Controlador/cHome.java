/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Empleado;
import Vista.vistaHome;
import dao.ClienteDao;
import dao.EmpleadoDao;
import dao.UsuarioDao;
import java.util.ArrayList;

/**
 *
 * @author EMMANUEL
 */
public class cHome {
    private vistaHome vistaH;
    private ClienteDao daoC;
    private UsuarioDao daoU;
    private EmpleadoDao daoE;

    public cHome(vistaHome vistaH) {
        this.vistaH = vistaH;
        this.daoC = new ClienteDao();
        this.daoE = new EmpleadoDao();
        this.vistaH.setControladorH(this);
        cargarClientes();
        cargarEmpleados();
    }

    public void cargarClientes() {
        ArrayList<Cliente> clientes = daoC.obtenerTodosClientes();
        vistaH.mostrarClientes(clientes);
    }
    
    public void cargarEmpleados(){
        ArrayList<Empleado> empleados = daoE.obtenerTodosEmpleados();
        vistaH.mostrarEmpleados(empleados);
    }
    
    
    public void buscarClientesPorNombre(String nombre){
        ArrayList<Cliente> clientes = daoC.buscarClientesPorNombre(nombre);
        vistaH.realizarBusquesa(clientes);
    }
    
    public void cargarUsuarios(){
        //ArrayList<Usuario> usuarios = daoU.obtenerTodosUsuarios();
    }
    
    
}
