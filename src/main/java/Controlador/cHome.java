package Controlador;

import Modelo.*;
import Vista.vistaHome;
import Vista.vistaRegUsuario;
import dao.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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
    private PlanoDao daoPlano;
    private ReservaDao daoR;
    private PedidoDao daoPedido;
    
    /**
     * Constructor de la clase cHome. Inicializa los objetos necesarios y carga 
     * la información de clientes y empleados.
     * 
     * @param vistaH Instancia de la vista principal (vistaHome).
     */
    public cHome(vistaHome vistaH) {
        this.vistaH = vistaH;
        this.daoPlano = new PlanoDao();
        this.daoC = new ClienteDao(); 
        this.daoE = new EmpleadoDao(); 
        this.daoP = new ProductoDao();
        this.daoR = new ReservaDao();
        this.daoM = new MesaDao();
        this.daoPedido = new PedidoDao();
        this.vistaH.setControladorH(this); // Establece el controlador en la vista
        cargarClientes();
        cargarEmpleados(); 
        cargarProductos();
        mostrarMesaPlano();
        cargarReservas();
    }
    
    

    /**
     * Carga todos los clientes desde la base de datos y los muestra en la vista.
     */
    public void cargarClientes(){
        ArrayList<Cliente> clientes = daoC.obtenerTodosClientes();
            vistaH.mostrarClientes(clientes);
    }
    
    /**
     * Carga todos los empleados desde la base de datos y los muestra en la vista.
     */
    public void cargarEmpleados(){
        ArrayList<Empleado> empleados = daoE.obtenerTodosEmpleados();
        if(vistaH != null){
            vistaH.mostrarEmpleados(empleados);
        }
    }
    
    /**
     * Realiza una búsqueda de clientes por nombre y muestra los resultados en la vista.
     * 
     * @param dato El nombre o dni del cliente que se desea buscar.
     */
    public void buscarClientesPorNombre(String dato){
        ArrayList<Cliente> clientes = daoC.buscarClientesPorNombre(dato);
        vistaH.mostrarClientes(clientes);
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
    public void cargarReservas(){
        ArrayList<Reserva> reservas = daoR.obtenerReservas();
        if(reservas != null){
            vistaH.mostrarReservas(reservas);
        }
        
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
     * Método para insertar una nueva mesa  y recuperar id de la base de datos.
     * 
     * @param numeroMesa El número de la mesa.
     * @param capacidad La capacidad de la mesa.
     * @param estado El estado inicial de la mesa.
     * @return El ID de la mesa recién creada o -1 si ocurrió un error.
     */
    public int insertarMesa(int numeroMesa, int capacidad, String estado) {
        return daoM.agregarMesa(numeroMesa, capacidad, estado);
    }

    /**
     * Método para obtener el ID de la última mesa creada en la base de datos.
     * 
     * @return El ID de la última mesa o -1 si no se pudo obtener.
     */
    public int obtenerUltimoIdMesa() {
        return daoM.obtenerUltimoIdMesa();
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
    * Obtiene todas las mesas.
    * 
    * @return Lista de objetos Mesa con todas las mesas registradas.
    */
    public ArrayList<Mesa> obtenerTodasLasMesas() {
        return daoM.obtenerTodasLasMesas();
    }
    
    public void mostrarMesaPlano(){
        int id = daoPlano.obtenerIdPlanoMasReciente();
        ArrayList<Mesa> mesas = daoPlano.obtenerMesasPorPlano(id);
        vistaH.llenarVistaConMesas(mesas);
    }
    
    public int getIdPlanoMasReciente(){
        return daoPlano.obtenerIdPlanoMasReciente();
    }
    
    public void recuperarReserva(int id) {
        Reserva reserva = daoPedido.getReservaPorId(id);

        if (reserva != null) {
            String estadoMesa = reserva.getMesa().getEstadoMesa();
            if("Ocupada".equals(estadoMesa)){
                int opcion = JOptionPane.showConfirmDialog(null, 
                    "¿Desea generar el comprobante para la reserva de " + reserva.getCliente().getNombre() + "?", 
                    "Generar Comprobante", 
                    JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    generarComprobante(reserva);
                    boolean exito = daoPedido.actualizarEstadoAtencion(
                    reserva.getMesa().getIdMesa(), id, reserva.getPedido().getIdPedido());
                    if (exito) {
                        vistaH.mostrarMensaje("Reserva de " + reserva.getCliente().getNombre() + " encontrada: actualizando estado ...");
                    } else {
                        vistaH.mostrarMensaje("Error al actualizar el estado de la reserva.");
                    }
                }else if(opcion == JOptionPane.NO_OPTION){
                    vistaH.mostrarMensaje("Comprobante cancelado");
                }
                
            }else{
                boolean exito = daoPedido.actualizarEstadoAtencion(
                reserva.getMesa().getIdMesa(), id, reserva.getPedido().getIdPedido());
                if (exito) {
                    vistaH.mostrarMensaje("Reserva de " + reserva.getCliente().getNombre() + " encontrada: actualizando estado ...");
                } else {
                    vistaH.mostrarMensaje("Error al actualizar el estado de la reserva.");
                }
            }          
        } else {
            vistaH.mostrarMensaje("Reserva no encontrada.");
        }
    }
    
    public void generarComprobante(Reserva reserva){
        daoPedido.generarComprobanteWord(reserva.getIdReserva());
        vistaH.mostrarMensaje("Comprobante generado para la reserva de " + reserva.getCliente().getNombre() + ".");
    }
}