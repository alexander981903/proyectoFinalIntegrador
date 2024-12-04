/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.cHome;
import Controlador.cRegPedido;
import Controlador.cRegReserva;
import Modelo.Cliente;
import Modelo.Mesa;
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
import javax.swing.border.TitledBorder;
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
    private JTextField igvField;
    private JTextField subtotalField;
    private JTextField totalField;
    private JDateChooser fechaChooser;
    private JSpinner horaSpinner;       
    private JSpinner duracionSpinner;
    private JButton confirmarReservaButton;
    private Usuario usuarioAutenticado;
    private cHome controladorH;
    private cRegReserva controladorRR;
    private JComboBox comboBoxClientes;
    private Mesa mesa;
    
    // Colores personalizados
    private final Color backgroundColor = new Color(0x0B, 0x11, 0x19);
    private final Color buttonBackgroundColor = new Color(0x20, 0x33, 0x4A);
    private final Color textColor = Color.WHITE;

    /**
     * Constructor de la clase vistaRegReserva.
     * Inicializa los componentes y configura la ventana de la interfaz gráfica.
     */
    public vistaRegReserva(Mesa mesa, Usuario usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
        this.mesa = mesa;
        initComponents();
    }

    /**
     * Método para inicializar los componentes de la ventana.
     * Configura la tabla del menú, los campos de texto y los botones para interactuar con la reserva.
     */
    private void initComponents() {
        
        setTitle("Registrar Reserva");
        setSize(800,650);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        JPanel menuPanel = new JPanel(new BorderLayout());
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Carta del Menú");
        titledBorder.setTitleColor(textColor); // Cambiar el color del título a blanco
        menuPanel.setBorder(titledBorder);

        menuPanel.setSize(700,500);
        menuPanel.setBackground(backgroundColor);
        menuPanel.setForeground(textColor);
        
        cartaTableModel = new NonEditableTableModel(
                new Object[]{"ID", "Plato", "Personal", "Fuente"}, 0);
        cartaTable = new JTable(cartaTableModel);
        cartaTable.setFillsViewportHeight(true);
        cartaTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        cartaTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        TableColumn personalColumn = cartaTable.getColumnModel().getColumn(2);
        personalColumn.setCellRenderer(new DecimalFormatRenderer(cartaTable));
        TableColumn familiarColumn = cartaTable.getColumnModel().getColumn(3);
        familiarColumn.setCellRenderer(new DecimalFormatRenderer(cartaTable));

        JScrollPane menuScrollPane = new JScrollPane(cartaTable);
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);
        menuPanel.setPreferredSize(new Dimension(550, 400));

         // Panel derecho principal para los datos de la reserva
        JPanel reservaPanel = new JPanel(new BorderLayout());
        TitledBorder titledReserva = BorderFactory.createTitledBorder("Datos de la Reserva");
        titledReserva.setTitleColor(textColor);
        reservaPanel.setBorder(titledReserva);
        reservaPanel.setBackground(backgroundColor);
        reservaPanel.setForeground(textColor);
        
        JPanel camposPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        camposPanel.setBackground(backgroundColor);
        camposPanel.setForeground(textColor);
        
        JLabel lblClientes = new JLabel("Cliente: ");
        lblClientes.setForeground(textColor);
        camposPanel.add(lblClientes);

        if(usuarioAutenticado.getRol().equals("Empleado")){
            comboBoxClientes = new JComboBox();
            comboBoxClientes.setEditable(false);
            camposPanel.add(comboBoxClientes);

            comboBoxClientes.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    Cliente cliente = (Cliente) value;
                    setText(cliente.getIdCliente() + " || " + cliente.getNombre() + " || " + cliente.getApellido());
                    return this;
                }
            });

        }else if(usuarioAutenticado.getRol().equals("Cliente")){
            JTextField clienteField = new JTextField(20);
            Cliente c =  (Cliente) usuarioAutenticado.getObj();
            clienteField.setText(c.getNombre()+ " " + c.getApellido());
            clienteField.setEditable(false);
            camposPanel.add(clienteField);
        }


        JLabel lblMesa = new JLabel("Mesa:");
        lblMesa.setForeground(textColor);
        camposPanel.add(lblMesa);
        mesaField = new JTextField("Mesa " + mesa.getNumeroMesa());
        mesaField.setEditable(false);
        camposPanel.add(mesaField);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setForeground(textColor);
        camposPanel.add(lblFecha);
        fechaChooser = new JDateChooser();
        camposPanel.add(fechaChooser);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setForeground(textColor);
        camposPanel.add(lblHora);
        Date horaInicial = new Date();
        SpinnerDateModel horaModelo = new SpinnerDateModel(horaInicial, null, null, java.util.Calendar.HOUR_OF_DAY);
        horaSpinner = new JSpinner(horaModelo);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(horaSpinner, "hh:mm a");
        horaSpinner.setEditor(editor);
        camposPanel.add(horaSpinner);

        JLabel lblDuracion = new JLabel("Duración (minutos):");
        lblDuracion.setForeground(textColor);
        camposPanel.add(lblDuracion);
        duracionSpinner = new JSpinner(new SpinnerNumberModel(60, 30, 240, 15));
        camposPanel.add(duracionSpinner);
        
        reservaPanel.add(camposPanel, BorderLayout.CENTER);
        
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirmarReservaButton = new JButton("Confirmar Reserva");
        botonesPanel.setBackground(backgroundColor);
        botonesPanel.add(confirmarReservaButton);
        reservaPanel.add(botonesPanel, BorderLayout.SOUTH);
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(backgroundColor);
        panelSuperior.add(menuPanel, BorderLayout.WEST);
        panelSuperior.add(reservaPanel, BorderLayout.CENTER);
        
        add(panelSuperior);
        
        JPanel pedidoPanel = new JPanel(new BorderLayout());
        pedidoPanel.setBackground(backgroundColor);
        TitledBorder titledPedido = BorderFactory.createTitledBorder("Pedido");
        titledPedido.setTitleColor(textColor);
        pedidoPanel.setBorder(titledPedido);
        
        pedidosTableModel = new DefaultTableModel(
                new Object[]{"ID","Nombre del Plato","Tamaño", "Cantidad", "Precio", "Total"}, 0);
        JTable pedidosTable = new JTable(pedidosTableModel);
        pedidosTable.getColumnModel().getColumn(0).setMinWidth(0);
        pedidosTable.getColumnModel().getColumn(0).setMaxWidth(0);
        pedidosTable.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane pedidosScrollPane = new JScrollPane(pedidosTable);
        
        JPanel tablaPanel = new JPanel(new BorderLayout());
        tablaPanel.setBackground(backgroundColor);
        tablaPanel.add(pedidosScrollPane, BorderLayout.CENTER);
        pedidoPanel.add(tablaPanel, BorderLayout.CENTER);
        
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel subtotalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel subtotalLabel = new JLabel("Subtotal:");
        subtotalField = new JTextField(10);
        subtotalField.setEditable(false);
        subtotalField.setHorizontalAlignment(JTextField.RIGHT);
        subtotalPanel.add(subtotalLabel);
        subtotalPanel.add(subtotalField);
        subPanel.add(subtotalPanel);
        
        JPanel igvPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel igvLabel = new JLabel("IGV:");
        igvField = new JTextField(10);
        igvField.setEditable(false);
        igvField.setHorizontalAlignment(JTextField.RIGHT);
        igvPanel.add(igvLabel);
        igvPanel.add(igvField);
        subPanel.add(igvPanel);
        
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel totalLabel = new JLabel("Total:");
        totalField = new JTextField(10);
        totalField.setEditable(false);
        totalField.setHorizontalAlignment(JTextField.RIGHT);
        totalPanel.add(totalLabel);
        totalPanel.add(totalField);
        subPanel.add(totalPanel);
        
        pedidoPanel.add(subPanel, BorderLayout.EAST);
        
        add(pedidoPanel);
        
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


        confirmarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if(usuarioAutenticado.getRol().equals("Empleado")){
                        Cliente cliente = (Cliente) comboBoxClientes.getSelectedItem();
                        if (cliente == null) {
                            mostrarMensaje("Seleccione un cliente.");
                            return;
                        }
                        int idCliente = cliente.getIdCliente();
                        int idMesa = mesa.getIdMesa();
                        Date fechaReserva = fechaChooser.getDate();
                        if (fechaReserva == null) {
                            mostrarMensaje("Seleccione una fecha.");
                            return;
                        }
                        java.sql.Date sqlFechaReserva = new java.sql.Date(fechaReserva.getTime());
                        java.sql.Time horaReserva = new java.sql.Time(((Date) horaSpinner.getValue()).getTime());
                        int duracionReserva = (int) duracionSpinner.getValue();
                        
                        ArrayList<Producto> productos = controladorRR.obtenerProductosDeTabla();
                        
                        controladorRR.realizarPedidoYReserva(idCliente, idMesa, productos, sqlFechaReserva, 
                                 horaReserva, duracionReserva);
                        controladorH.cargarReservas();
                        controladorH.mostrarMesaPlano();
                        dispose();

                    }else if(usuarioAutenticado.getRol().equals("Cliente")){
                        Cliente usuario = (Cliente) usuarioAutenticado.getObj();
                        int idCliente = usuario.getIdCliente();
                        int idMesa = mesa.getIdMesa();
                        Date fechaReserva = fechaChooser.getDate();
                        if (fechaReserva == null) {
                            mostrarMensaje("Seleccione una fecha.");
                            return;
                        }
                        java.sql.Date sqlFechaReserva = new java.sql.Date(fechaReserva.getTime());
                        java.sql.Time horaReserva = new java.sql.Time(((Date) horaSpinner.getValue()).getTime());
                        int duracionReserva = (int) duracionSpinner.getValue();
                        
                        ArrayList<Producto> productos = controladorRR.obtenerProductosDeTabla();
                        
                        controladorRR.realizarPedidoYReserva(idCliente, idMesa, productos, sqlFechaReserva, 
                                 horaReserva, duracionReserva);
                        controladorH.cargarReservas();
                        controladorH.mostrarMesaPlano();
                        dispose();
                    }

                } catch (Exception ex) {
                    System.out.println("Error al procesar la reserva: " + ex.getMessage());
                }
            }
        });




    }
   
    double calcularTotal() {
        double total = 0.00;
        double igv = 0.00;
        double subTotal = 0.00;

        // Iterar a través de las filas del modelo de tabla
        for (int i = 0; i < pedidosTableModel.getRowCount(); i++) {
            // Obtener el valor de la columna "Total" (índice 5)
            Object value = pedidosTableModel.getValueAt(i, 5);

            // Asegurarse de que el valor no sea nulo y sea convertible a double
            if (value != null) {
                try {
                    total += Double.parseDouble(value.toString());
                    subTotal = total/1.18;
                    igv = total - subTotal;
                    totalField.setText(String.valueOf(total));
                    igvField.setText(String.format("%.2f", igv));
                    subtotalField.setText(String.format("%.2f", subTotal));
                } catch (NumberFormatException e) {
                    // Manejar el caso de formato incorrecto
                    System.err.println("Valor inválido en la columna Total: " + value);
                }
            }
        }

        return total;
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
        if(usuarioAutenticado.getRol().equals("Empleado")){
            comboBoxClientes.removeAllItems();
            
            for (Cliente c : clientes) {
                comboBoxClientes.addItem(c);
            }
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
    * @param usuarioAutenticado El objeto {@link Usuario} que representa al usuario autenticado.
    *                Este parámetro no debe ser {@code null}.
    */
    public void setUsuario(Usuario usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
    }
}

