/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.cHome;
import Controlador.cRegPedido;
import Controlador.cRegReserva;
import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Usuario;
import RenderingTable.DecimalFormatRenderer;
import RenderingTable.NonEditableTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * La clase vistaRegReserva es una ventana de interfaz gráfica para registrar reservas en un sistema de reservas.
 * La ventana se divide en dos paneles: uno para mostrar la carta del menú y otro para ingresar los datos de la reserva.
 * 
 * @author EMMANUEL
 */
public class vistaRegReserva extends JFrame {

    private JTable cartaTable;
    private DefaultTableModel cartaTableModel;
    private DefaultTableModel pedidosTableModel;
    private JTextField mesaField;
    private JDateChooser fechaChooser;
    private JSpinner horaSpinner;       
    private JSpinner duracionSpinner;
    private JButton confirmarReservaButton;
    private Usuario usuarioAutenticado;
    private int numeroMesa;
    private cHome controladorH;
    private cRegReserva controladorRR;
    private JComboBox comboBoxClientes;
    
    // Colores personalizados
    private final Color backgroundColor = new Color(0x0B, 0x11, 0x19);
    private final Color buttonBackgroundColor = new Color(0x20, 0x33, 0x4A);
    private final Color textColor = Color.WHITE;

    /**
     * Constructor de la clase vistaRegReserva.
     * Inicializa los componentes y configura la ventana de la interfaz gráfica.
     */
    public vistaRegReserva(int numeroMesa) {
        this.numeroMesa = numeroMesa;
        initComponents();
    }

    /**
     * Método para inicializar los componentes de la ventana.
     * Configura la tabla del menú, los campos de texto y los botones para interactuar con la reserva.
     */
   private void initComponents() {
        // Configuración de la ventana principal
        setTitle("Registrar Reserva");
        setSize(800,650);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Usamos BoxLayout para distribución vertical

        // Panel izquierdo para la carta del menú
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createTitledBorder("Carta del Menú"));

        // Crear la tabla cartaTable
        cartaTableModel = new NonEditableTableModel(
                new Object[]{"ID", "Plato", "Personal", "Fuente"}, 0);
        cartaTable = new JTable(cartaTableModel);
        cartaTable.setFillsViewportHeight(true);
        cartaTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Ajustar el tamaño de las columnas automáticamente
        
        cartaTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        // Aplicar DecimalFormatRenderer a las columnas de precios
        TableColumn personalColumn = cartaTable.getColumnModel().getColumn(2);
        personalColumn.setCellRenderer(new DecimalFormatRenderer(cartaTable));
        TableColumn familiarColumn = cartaTable.getColumnModel().getColumn(3);
        familiarColumn.setCellRenderer(new DecimalFormatRenderer(cartaTable));

        JScrollPane menuScrollPane = new JScrollPane(cartaTable);
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);
        menuPanel.setPreferredSize(new Dimension(350, 400)); // Tamaño preferido para que se ajuste a la ventana

        // Panel derecho principal para los datos de la reserva
        JPanel reservaPanel = new JPanel(new BorderLayout()); // Layout principal para organizar los subpaneles
        reservaPanel.setBorder(BorderFactory.createTitledBorder("Datos de la Reserva"));

        // Subpanel para los campos de entrada
        JPanel camposPanel = new JPanel(new GridLayout(6, 2, 5, 5));  // Panel con los campos de entrada
        camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Para mejorar el espaciado

        // Campos de entrada de datos
        camposPanel.add(new JLabel("Cliente:"));
        comboBoxClientes = new JComboBox();
        comboBoxClientes.setEditable(false);
        camposPanel.add(comboBoxClientes);
        
        

        camposPanel.add(new JLabel("Mesa:"));
        mesaField = new JTextField("Mesa " + numeroMesa);
        mesaField.setEditable(false);
        camposPanel.add(mesaField);

        camposPanel.add(new JLabel("Fecha:"));
        fechaChooser = new JDateChooser();
        camposPanel.add(fechaChooser);

        camposPanel.add(new JLabel("Hora:"));
        Date horaInicial = new Date();
        SpinnerDateModel horaModelo = new SpinnerDateModel(horaInicial, null, null, java.util.Calendar.HOUR_OF_DAY);
        horaSpinner = new JSpinner(horaModelo);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(horaSpinner, "hh:mm a");
        horaSpinner.setEditor(editor);
        camposPanel.add(horaSpinner);

        camposPanel.add(new JLabel("Duración (minutos):"));
        duracionSpinner = new JSpinner(new SpinnerNumberModel(60, 30, 240, 15));
        camposPanel.add(duracionSpinner);

        // Agregar el subpanel de campos al panel derecho
        reservaPanel.add(camposPanel, BorderLayout.CENTER);

        // Subpanel para los botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirmarReservaButton = new JButton("Confirmar Reserva");
        botonesPanel.add(confirmarReservaButton);
        reservaPanel.add(botonesPanel, BorderLayout.SOUTH);

        // Crear un panel superior para contener menuPanel y reservaPanel
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(menuPanel, BorderLayout.WEST);
        panelSuperior.add(reservaPanel, BorderLayout.CENTER);

        // Agregar el panel superior a la parte superior de la ventana principal
        add(panelSuperior); // Lo agregamos sin usar BorderLayout.NORTH, porque ahora estamos usando BoxLayout

        // Crear el panel inferior para realizar el pedido
        JPanel pedidoPanel = new JPanel(new BorderLayout());
        pedidoPanel.setBackground(backgroundColor);
        pedidoPanel.setBorder(BorderFactory.createTitledBorder("Pedido"));

        // Crear la tabla para almacenar los productos (platos), la cantidad y el precio
        pedidosTableModel = new DefaultTableModel(
                new Object[]{"ID","Nombre del Plato","Tamaño", "Cantidad", "Precio", "Total"}, 0);
        JTable pedidosTable = new JTable(pedidosTableModel);
        pedidosTable.getColumnModel().getColumn(0).setMinWidth(0);
        pedidosTable.getColumnModel().getColumn(0).setMaxWidth(0);
        pedidosTable.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane pedidosScrollPane = new JScrollPane(pedidosTable);

        // Crear un subpanel para la tabla y agregarla al panel izquierdo de pedidoPanel
        JPanel tablaPanel = new JPanel(new BorderLayout());
        tablaPanel.add(pedidosScrollPane, BorderLayout.CENTER);
        pedidoPanel.add(tablaPanel, BorderLayout.CENTER);

        

        // Crear un subpanel para el botón y agregarlo al panel derecho de pedidoPanel
        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pedidoPanel.add(botonPanel, BorderLayout.EAST);

        // Agregar el pedidoPanel a la ventana principal
        add(pedidoPanel);
        
        // Agregar MouseListener para abrir vistaRegPedido al hacer clic en una fila
        cartaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && cartaTable.getSelectedRow() != -1) {
                    
                    int selectedRow = cartaTable.getSelectedRow();
                    int idPlato = (int) cartaTable.getValueAt(selectedRow, 0);                    
                    vistaRegPedido vistaRegPedido = new vistaRegPedido(vistaRegReserva.this);
                    vistaRegPedido.setIdPlato(idPlato);
                    vistaRegPedido.setVisible(true);                   
                    new cRegPedido(vistaRegPedido);
                    
                }
            }
        });
        
        comboBoxClientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Cliente cliente = (Cliente) value;
                setText(cliente.getIdCliente() + " || " + cliente.getNombre() + " || " + cliente.getApellido());
                return this;
            }
        });
        
        confirmarReservaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                // Obtener el cliente seleccionado
                Cliente cliente = (Cliente) comboBoxClientes.getSelectedItem();
                if (cliente == null) {
                    mostrarMensaje("Seleccione un cliente.");
                    return;
                }
                int idCliente = cliente.getIdCliente();

                // Obtener el número de mesa
                int idMesa = numeroMesa;  // Se supone que se obtiene del campo de número de mesa

                // Obtener la fecha y hora de reserva
                Date fechaReserva = fechaChooser.getDate();
                if (fechaReserva == null) {
                    mostrarMensaje("Seleccione una fecha.");
                    return;
                }

                java.sql.Time horaReserva = new java.sql.Time(((Date) horaSpinner.getValue()).getTime());

                // Obtener duración y cantidad
                int duracionReserva = (int) duracionSpinner.getValue();

                // Obtener productos de la tabla pedidosTable
                ArrayList<Producto> productos = controladorRR.obtenerProductosDeTabla();

                // Llamar al método en el controlador
                controladorRR.realizarPedidoYReserva(idCliente, idMesa, productos, (java.sql.Date) fechaReserva, horaReserva, duracionReserva);


            } catch (Exception ex) {
                mostrarMensaje("Error al procesar la reserva: " + ex.getMessage());
            }
                
            }
        });


        
    }

    public void setControladorH(cHome controladorH){
        this.controladorH = controladorH;
    }

    public void setControladorRR(cRegReserva controladorRR){
        this.controladorRR = controladorRR;
    }

    /**
     * Método para mostrar una lista de empleados en la tabla.
     * Limpiar la tabla actual y luego agregar cada producto de la lista a las filas de la tabla.
     * @param productos Lista de productos que se desea mostrar en la tabla.
     */
    public void mostrarProductos(ArrayList<Producto> productos) {
        cartaTableModel.setRowCount(0); // Limpiar tabla
        for (Producto p : productos) {
            cartaTableModel.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombreProducto(),
                p.getPrecioPersonal(),
                p.getPrecioFamiliar(),
                p.getStock(),
                p.isDisponibilidad()
            });
        }
    }
    
    public void agregarProductoAlaTabla(HashMap<String, Object> producto) {
        // Extraer los datos del HashMap
        int id = (int) producto.get("id");
        String nombrePlato = (String) producto.get("nombrePlato");
        String tamaño = (String) producto.get("tamaño");
        int cantidad = (int) producto.get("cantidad");
        double precio = (double) producto.get("precio");

        // Agregar el producto a la tabla
        pedidosTableModel.addRow(new Object[]{
            id,
            nombrePlato,
            tamaño,
            cantidad,
            precio,
            cantidad * precio // Total = cantidad * precio
        });
    }
    
    public DefaultTableModel getPedidosTableModel() {
        return pedidosTableModel;
    }


    
    /**
     * Método para mostrar una lista de clientes en el JComboBox.
     * Este método limpia el JComboBox y agrega los clientes recibidos como parámetros.
     * 
     * @param clientes Lista de clientes a mostrar en el JComboBox.
     */
    public void mostrarClientesEnComboBox(ArrayList<Cliente> clientes) {
        comboBoxClientes.removeAllItems(); // Limpiar el JComboBox
        for (Cliente c : clientes) {
            comboBoxClientes.addItem(c);
        }
    }
    
    /**
     * Método para mostrar mensajes al usuario en una ventana emergente.
     * @param mensaje El mensaje que se va a mostrar al usuario.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    } 
    
    /**
    * Establece el usuario autenticado en la vista.
    * Este método se utiliza para asignar el objeto {@link Usuario} que representa
    * al usuario autenticado, permitiendo que la vista tenga acceso a los datos
    * del usuario y los utilice en la interfaz de usuario (por ejemplo, para mostrar
    * el nombre del usuario, su rol, etc.).
    * 
    * @param usuario El objeto {@link Usuario} que representa al usuario autenticado.
    *                Este parámetro no debe ser {@code null}.
    */
    public void setUsuario(Usuario usuario) {
        this.usuarioAutenticado = usuario;
    }
}

