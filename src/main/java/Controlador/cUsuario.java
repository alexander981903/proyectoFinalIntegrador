/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.Usuario;
import Vista.vistaRegUsuario;
import dao.ClienteDao;
import dao.EmpleadoDao;
import dao.UsuarioDao;
import java.util.ArrayList;

/**
 *
 * @author EMMANUEL
 */
public class cUsuario {
    private vistaRegUsuario vistaU;
    private UsuarioDao daoU;
    private EmpleadoDao daoE;
    private ClienteDao daoC;
    
    /**
   * Constructor que inicializa el controlador con la vista y el DAO.
   * @param vistaU Objeto vistaRegREmpleado para almacernar ususario.
   */
    
    public cUsuario(vistaRegUsuario vistaU) {
        this.vistaU = vistaU;
        this.daoU = new UsuarioDao();
        this.daoE = new EmpleadoDao();
        this.daoC = new ClienteDao();
        this.vistaU.setControlerU(this);
    }

    public void cargarComboboxEmpleados(){
        ArrayList<Empleado> empelados = daoE.obtenerTodosEmpleados();
        vistaU.mostrarEmpleadosEnComboBox(empelados);
    }
    
    public void cargarComboboxClientes(){
        ArrayList<Cliente> clientes = daoC.obtenerTodosClientes();
        vistaU.mostrarClientesEnComboBox(clientes);
    }
    
    public void agregarUsuario(String login, String clave, String rol, Object obj){
        Usuario user = new Usuario();
        user.setLogin(login);
        user.setClave(clave);
        user.setRol(rol);
        user.setObj(obj);
        
        boolean exito = daoU.insertarUsuario(user);
        if (exito){
            vistaU.mostrarMensaje("Usuario Agregado Exitosamente.");
        }else{
            vistaU.mostrarMensaje("Error Al Agregar Cliente");
        }
    }
    
    
}
