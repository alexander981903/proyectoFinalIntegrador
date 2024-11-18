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
 * Clase Controlador que maneja las interacciones entre la Vista y el Modelo para los Usuarios.
 * Esta clase sirve como intermediaria entre la vista de registro de usuarios (`vistaRegUsuario`) 
 * y los DAOs correspondientes que gestionan las operaciones con los datos de usuarios, empleados y clientes.
 * 
 * @author EMMANUEL
 */
public class cUsuario {
    private vistaRegUsuario vistaU;  // Vista para registrar el usuario
    private UsuarioDao daoU;         // DAO para interactuar con los datos de Usuario
    private EmpleadoDao daoE;        // DAO para interactuar con los datos de Empleado
    private ClienteDao daoC;         // DAO para interactuar con los datos de Cliente
    
    /**
     * Constructor que inicializa el controlador con la vista de usuario y los DAOs de Usuario, Empleado y Cliente.
     * Establece la relación entre el controlador y la vista.
     * 
     * @param vistaU Objeto de tipo `vistaRegUsuario`, que representa la interfaz para registrar un usuario.
     */
    public cUsuario(vistaRegUsuario vistaU) {
        this.vistaU = vistaU;
        this.daoU = new UsuarioDao();  // Inicializamos el DAO de Usuario
        this.daoE = new EmpleadoDao(); // Inicializamos el DAO de Empleado
        this.daoC = new ClienteDao();  // Inicializamos el DAO de Cliente
        this.vistaU.setControlerU(this); // Asociamos el controlador a la vista
    }

    /**
     * Método para cargar todos los empleados en el ComboBox de la vista de registro de usuario.
     * Recupera todos los empleados de la base de datos a través del DAO de Empleado
     * y los muestra en el ComboBox correspondiente en la vista.
     */
    public void cargarComboboxEmpleados() {
        // Obtenemos todos los empleados desde la base de datos
        ArrayList<Empleado> empleados = daoE.obtenerTodosEmpleados();
        // Mostramos los empleados en el ComboBox de la vista
        vistaU.mostrarEmpleadosEnComboBox(empleados);
    }

    /**
     * Método para cargar todos los clientes en el ComboBox de la vista de registro de usuario.
     * Recupera todos los clientes de la base de datos a través del DAO de Cliente
     * y los muestra en el ComboBox correspondiente en la vista.
     */
    public void cargarComboboxClientes() {
        // Obtenemos todos los clientes desde la base de datos
        ArrayList<Cliente> clientes = daoC.obtenerTodosClientes();
        // Mostramos los clientes en el ComboBox de la vista
        vistaU.mostrarClientesEnComboBox(clientes);
    }

    /**
     * Método para agregar un nuevo usuario.
     * Este método recibe los datos del usuario (login, clave, rol, objeto asociado) 
     * y los guarda en la base de datos utilizando el DAO de Usuario.
     * 
     * @param login El nombre de usuario para el login.
     * @param clave La clave o contraseña asociada al usuario.
     * @param rol El rol que se asignará al usuario (Empleado o Cliente).
     * @param obj El objeto asociado al usuario, que puede ser un empleado o un cliente dependiendo del rol.
     */
    public void agregarUsuario(String login, String clave, String rol, Object obj) {
        Usuario user = new Usuario();
        user.setLogin(login);  // Establece el login del usuario
        user.setClave(clave);  // Establece la clave del usuario
        user.setRol(rol);      // Establece el rol del usuario (Empleado o Cliente)
        user.setObj(obj);      // Establece el objeto relacionado (Empleado o Cliente)
        
        // Intentamos agregar el usuario utilizando el DAO
        boolean exito = daoU.insertarUsuario(user);
        if (exito) {
            vistaU.mostrarMensaje("Usuario agregado exitosamente.");
        } else {
            vistaU.mostrarMensaje("Error al agregar el usuario.");
        }
    }
}
