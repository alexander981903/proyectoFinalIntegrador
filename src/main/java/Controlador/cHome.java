package Controlador;

import Modelo.*;
import Vista.vistaHome;
import Vista.vistaRegUsuario;
import dao.*;
import java.util.ArrayList;

/**
 * Clase controladora que gestiona las interacciones entre la vista principal 
 * (vistaHome) y los datos de los clientes y empleados. 
 * @author EMMANUEL
 */
public class cHome {
    // Atributos
    private vistaHome vistaH;
    private ClienteDao daoC;
    private UsuarioDao daoU;
    private EmpleadoDao daoE;
    private ProductoDao daoP;
    private vistaRegUsuario vistaRU;
    private MesaDao daoM;
    
    /**
     * Constructor de la clase cHome. Inicializa los objetos necesarios y carga 
     * la información de clientes y empleados.
     * 
     * @param vistaH Instancia de la vista principal (vistaHome).
     */
    public cHome(vistaHome vistaH) {
        this.vistaH = vistaH;
        this.daoC = new ClienteDao(); 
        this.daoE = new EmpleadoDao(); 
        this.daoP = new ProductoDao();
        this.daoM = new MesaDao();
        this.vistaH.setControladorH(this); // Establece el controlador en la vista
        cargarClientes();  // Carga los clientes en la vista
        cargarEmpleados(); // Carga los empleados en la vista
        cargarProductos();
        mostrarTodasLasMesas();
    }
    
    public cHome(vistaRegUsuario vistaRU){
        this.vistaRU = vistaRU;
        this.daoC = new ClienteDao();
        this.daoE = new EmpleadoDao();
        
        this.vistaRU.setControlerH(this);
        if (vistaH != null) {
        vistaRU.setVistaHome(vistaH);}  // Pasar la vistaHome a vistaLogin
        cargarClientes();
        cargarEmpleados();
 
    }

    /**
     * Carga todos los clientes desde la base de datos y los muestra en la vista.
     */
    public void cargarClientes(){
        ArrayList<Cliente> clientes = daoC.obtenerTodosClientes(); // Obtiene todos los clientes
        if(vistaH != null){
            vistaH.mostrarClientes(clientes);
        }else if(vistaRU != null){
            vistaRU.mostrarClientesEnVistaHome(clientes);
        }
    }
    
    /**
     * Carga todos los empleados desde la base de datos y los muestra en la vista.
     */
    public void cargarEmpleados(){
        ArrayList<Empleado> empleados = daoE.obtenerTodosEmpleados(); // Obtiene todos los empleados
        if(vistaH != null){
            vistaH.mostrarEmpleados(empleados); // Muestra los empleados en la vista
        }else if(vistaRU != null){
            vistaRU.mostrarEmpleadosEnVistaHome(empleados);
        }
    }
    
    /**
     * Realiza una búsqueda de clientes por nombre y muestra los resultados en la vista.
     * 
     * @param nombre El nombre del cliente que se desea buscar.
     */
    public void buscarClientesPorNombre(String nombre){
        ArrayList<Cliente> clientes = daoC.buscarClientesPorNombre(nombre); // Busca los clientes por nombre
        vistaH.mostrarClientes(clientes); // Muestra los resultados de la búsqueda en la vista
    }
    
    /**
     * Realiza una búsqueda de empleados por nombre y muestra los resultados en la vista.
     * 
     * @param nombre El nombre del cliente que se desea buscar.
     */
    public void buscarEmpleadosPorNombre(String nombre){
        ArrayList<Empleado> empleados = daoE.buscarEmpleadoPorNombre(nombre); // Busca los clientes por nombre
        vistaH.mostrarEmpleados(empleados); // Muestra los resultados de la búsqueda en la vista
    }

    /**
     * Método para cargar todos los usuarios (actualmente no implementado).
     */
    public void cargarUsuarios(){
        // Este método podría implementarse cuando se agregue la funcionalidad de usuarios.
        // ArrayList<Usuario> usuarios = daoU.obtenerTodosUsuarios();
    }
    
    /**
     * Método para cargar todos los productos.
     */
    public void cargarProductos(){
        ArrayList<Producto> productos = daoP.obtenerTodosProductos();
        vistaH.mostrarProductos(productos);
    }
    
    public void actualizarDisponibilidad(int id, boolean disponibilidad){
        daoP.actualizarDisponibilidad(id, disponibilidad);
    }
    
    public void agregarMesa(int numeroMesa, int capacidad, String estadoMesa){
        Mesa mesa = new Mesa();
        mesa.setNumeroMesa(numeroMesa);
        mesa.setCapacidad(capacidad);
        mesa.setEstadoMesa(estadoMesa);
        
        boolean exito = daoM.insertarMesa(mesa);
        if(exito){
            vistaH.mostrarMensaje("Mesa Agregada Correctamente");
        }else{
            vistaH.mostrarMensaje("Error Al agregar Mesa");
        }
    }
    
    /**
    * Obtiene todas las mesas desde la base de datos.
    * 
    */
    public void mostrarTodasLasMesas() {
        ArrayList<Mesa> mesas = daoM.obtenerTodasLasMesas();
        vistaH.llenarVistaConMesas(mesas);
    }
    
    /**
    * Actualiza una mesa existente en la base de datos.
    * 
    * @param mesa Objeto Mesa con los datos actualizados.
    * @return true si la actualización fue exitosa, false de lo contrario.
    */
    public boolean actualizarMesa(Mesa mesa) {
        return daoM.actualizarMesa(mesa);
    }

   /**
    * Obtiene todas las mesas de la base de datos.
    * 
    * @return Lista de objetos Mesa con todas las mesas registradas.
    */
    public ArrayList<Mesa> obtenerTodasLasMesas() {
        return daoM.obtenerTodasLasMesas();
    }

}