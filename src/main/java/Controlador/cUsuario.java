/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.Usuario;
import Vista.vistaRegUsuario;
import dao.UsuarioDao;
import javax.swing.JOptionPane;

/**
 * Clase Controlador que maneja las interacciones entre la Vista y el Modelo para los Usuarios.
 * Esta clase sirve como intermediaria entre la vista de registro de usuarios (`vistaRegUsuario`) 
 * y los DAOs correspondientes que gestionan las operaciones con los datos de usuarios, empleados y clientes.
 * 
 * @author EMMANUEL
 */
public class cUsuario {
    private vistaRegUsuario vistaU;
    private UsuarioDao daoU;
    
    /**
     * Constructor que inicializa el controlador con la vista de usuario y los DAOs de Usuario, Empleado y Cliente.
     * Establece la relación entre el controlador y la vista.
     * 
     * @param vistaU Objeto de tipo `vistaRegUsuario`, que representa la interfaz para registrar un usuario.
     */
    public cUsuario(vistaRegUsuario vistaU) {
        this.vistaU = vistaU;
        this.daoU = new UsuarioDao();  
        this.vistaU.setControlerU(this);
        //existeAdministrador();
    }



    /**
     * Método para agregar un nuevo usuario.Este método recibe los datos del usuario 
     * y los guarda en la base de datos utilizando el DAO de Usuario.
     * 
     * @param login El nombre de usuario para el login.
     * @param clave La clave o contraseña asociada al usuario.
     * @param rol El rol que se asignará al usuario (Empleado o Cliente).
     * @param dni
     * @param nombre
     * @param apellido
     * @param email
     * @param telefono
     */
    public void agregarUsuario(String login, String clave, String rol, String dni,String nombre ,String apellido, String email, String telefono,  
            String nombreEmp , String Cargo , String Turno) {
        
        
        Cliente cliente = new Cliente();
        cliente.setDni(dni);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        
        Empleado emp = new Empleado();
        emp.setNombreEmp(nombreEmp);
        emp.setCargo(Cargo);
        emp.setTurno(Turno);

        Usuario user = new Usuario();
        user.setLogin(login);  
        user.setClave(clave);
        user.setRol(rol);
        if (!nombreEmp.isEmpty() && !Cargo.isEmpty() && !Turno.isEmpty()) {
            user.setObj(emp);
        } else {
            user.setObj(cliente);
        }
        
        boolean exito = daoU.insertarUsuario(user);
        if (exito) {
            vistaU.mostrarMensaje("Usuario agregado exitosamente.");
        } else {
            vistaU.mostrarMensaje("Error al agregar el usuario.");
        }
    }
    
    public boolean existeAdministrador() {
        boolean existeAdmin = daoU.existeAdmin();
        if (!existeAdmin) {
            int respuesta = JOptionPane.showConfirmDialog(null, 
                    "No hay un administrador registrado. ¿Desea registrar uno ahora?.", 
                    "Registrar Administrador", JOptionPane.YES_NO_OPTION);
            return respuesta == JOptionPane.YES_OPTION;
        }            
        return false;
    }


}
