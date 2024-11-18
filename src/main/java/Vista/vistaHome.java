/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 * Vista principal del sistema que gestiona la interfaz de inicio con pestañas para diferentes secciones.
 * Permite navegar entre pedidos, clientes, empleados, reportes y configuración.
 * @author EMMANUEL
 */
import Controlador.*;
import Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import RenderingTable.ButtonRendererEditor;
import RenderingTable.CheckboxRendererEditor;
import RenderingTable.DecimalFormatRenderer;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

public class vistaHome extends JFrame {

    
    private cRegEmpleado controladorE;
    private cRegCliente controladorRC;
    private cConfig controladorConf;
    private Usuario usuarioAutenticado;
    private JTabbedPane tabbedPane;
    
    private JScrollPane reservaScrollPane;
    private JScrollPane scrollPane;
    private JScrollPane tableScrollPane;
    private JScrollPane tableScrollPane1;
    
    private JPanel searchPanel;
    private JPanel topPanel;
    private JPanel topPanelReserva;
    private JPanel rightTablePanel;
    private JPanel leftPanel;
    private JPanel leftScrollPanel;
    private JPanel reservaPanel;
    private JPanel clientesPanel;
    private JPanel empleadosPanel;
    private JPanel cartaPanel;
    private JPanel reportesPanel;
    private JPanel configuracionPanel;
    private JPanel searchPanel1;
    
    private JLabel companyNameLabel;
    
    private JButton searchButton;
    private JButton newClientButton;
    private JButton searchButton1;
    private JButton newEmpleadoButton;
    
    private JTable clientsTable;
    private JTable empleadosTable;
    private JTable cartaTable;
    private JTable reservasTable;
    
    private DefaultTableModel mTablaEmpleado;
    private DefaultTableModel mTablaCliente;
    private DefaultTableModel cartaTableModel;
    private DefaultTableModel reservasTableModel;
    
    private JTextField searchField1;
    private JTextField searchField;
    private JTextField searchReserva;
    
    private cHome controladorH;
    
    
    
    // Color personalizado
    private final Color backgroundColor = new Color(0x0B, 0x11, 0x19);
    private final Color buttonBackgroundColor = new Color(0x20, 0x33, 0x4A);
    private final Color textColor = Color.WHITE;
    
    /**
   * Constructor que inicializa la interfaz gráfica de la vista principal.
   */
    public vistaHome(Usuario usuarioAutenticado) {
        // Configuración de la ventana principal
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Configuración del layout principal
        setLayout(new BorderLayout());
        initComponents();
        
    }
    
    /**
     * Método para inicializar los componentes de la interfaz.
     * Incluye la creación de paneles, botones, tablas, y pestañas.
     */
    private void initComponents() {
        // Panel superior para logo y nombre de la empresa
        topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alineación a la izquierda

        // Agregar el nombre de la empresa
        companyNameLabel = new JLabel("Nombre de la Empresa");
        companyNameLabel.setForeground(textColor);
        companyNameLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Ajusta el tamaño de la fuente
        topPanel.add(companyNameLabel);
        
        // Panel izquierdo con scroll y botones
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Layout vertical para botones
        leftPanel.setBackground(backgroundColor); // Fondo del panel izquierdo
        scrollPane = new JScrollPane(leftPanel);
        scrollPane.setBackground(backgroundColor); // Fondo del scroll pane
        scrollPane.getViewport().setBackground(backgroundColor); // Fondo del área de visualización del scroll

        // Crear botones con nombres específicos y ajustar al ancho del panel izquierdo
        String[] buttonNames = {"Reserva", "Clientes", "Empleados","Carta", "Reportes", "Configuración"};

        // Inicializar el TabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setForeground(textColor);
        tabbedPane.setBackground(backgroundColor);
        tabbedPane.setEnabled(false);
        
        // Crear el primer panel para "Reserva"
        reservaPanel = new JPanel();
        reservaPanel.setLayout(new BorderLayout()); // Usar un layout de BorderLayout
        
        // Panel superior para la búsqueda y el botón de reserva
        topPanelReserva = new JPanel();
        topPanelReserva.setLayout(new BorderLayout()); // Alinear a la izquierda
        topPanelReserva.setBackground(backgroundColor); // Color de fondo del panel superior
        
        // Campo de búsqueda de pedidos
        searchReserva = new JTextField(20); // Campo de búsqueda con ancho de 20 columnas
        searchReserva.setText("Buscar pedidos...");
        searchReserva.setForeground(Color.GRAY); // Color de texto de búsqueda
        
        // Botón de reserva de mesa
        JButton modificarPlanoButton = new JButton("Modificar Plano");
        modificarPlanoButton.setBackground(Color.LIGHT_GRAY); // Cambia el color de fondo
        modificarPlanoButton.setForeground(textColor); // Cambia el color del texto

        // Añadir el campo de búsqueda y el botón al panel superior con la alineacion correspondiente
        topPanelReserva.add(new JLabel("Buscar Reserva"),BorderLayout.WEST);
        topPanelReserva.add(searchReserva, BorderLayout.WEST);
        topPanelReserva.add(modificarPlanoButton,BorderLayout.EAST);

        // Añadir el panel superior al "pedidosPanel" en la parte superior
        reservaPanel.add(topPanelReserva, BorderLayout.NORTH);

        // Panel izquierdo del primer tab (scroll vertical)
        leftScrollPanel = new JPanel();
        leftScrollPanel.setLayout(new BoxLayout(leftScrollPanel, BoxLayout.Y_AXIS));
        leftScrollPanel.setBackground(backgroundColor);
        
        
        // Crear un modelo de tabla para mostrar la información
        reservasTableModel = new DefaultTableModel(new Object[]{"Comfirmar", "Cliente", "Fecha", "Total"}, 0);
        //Crea la tabla reservas
        reservasTable = new JTable(reservasTableModel);
        
        // Configurar el renderizador y editor de la primera columna para ser un botón
        reservasTable.getColumnModel().getColumn(0).setCellRenderer(new ButtonRendererEditor(reservasTable));
        reservasTable.getColumnModel().getColumn(0).setCellEditor(new ButtonRendererEditor(reservasTable));
        
        reservasTable.setBackground(backgroundColor);
        reservasTable.setForeground(textColor);
        reservasTable.setFillsViewportHeight(true); // Hacer que la tabla llene el espacio disponible

        // Agregar algunos datos de ejemplo a la tabla
        reservasTableModel.addRow(new Object[]{"", "Cliente A", "2024-11-13", "$50"});
        reservasTableModel.addRow(new Object[]{"", "Cliente B", "2024-11-13", "$35"});

        // Crear un JScrollPane para la tabla y añadirla al panel izquierdo
        JScrollPane reservasTableScrollPane = new JScrollPane(reservasTable);
        leftScrollPanel.add(reservasTableScrollPane);

        // Crear el JScrollPane general para el panel izquierdo
        reservaScrollPane = new JScrollPane(leftScrollPanel);
        reservaScrollPane.setPreferredSize(new Dimension(400, 500)); // Ajustar el tamaño del scroll
        reservaPanel.add(reservaScrollPane, BorderLayout.WEST); // Añadir al lado izquierdo

        // Panel derecho del primer tab (nombres de mesas)
        rightTablePanel = new JPanel();
        rightTablePanel.setLayout(new GridLayout(0, 4)); // 4 columnas para las mesas
        rightTablePanel.setBackground(backgroundColor);

        reservaPanel.add(rightTablePanel, BorderLayout.CENTER); // Añadir al centro

        // Agregar el primer panel a las pestañas
        tabbedPane.addTab("Reserva", reservaPanel);

        // Crear pestaña de Clientes
        clientesPanel = new JPanel();
        clientesPanel.setLayout(new BorderLayout());
        clientesPanel.setBackground(backgroundColor);

        // Panel superior para buscar y añadir nuevo cliente
        searchPanel = new JPanel();
        searchPanel.setBackground(backgroundColor);
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alinear a la izquierda

        // Caja de texto para buscar
        searchField = new JTextField(20); // 20 columnas
        searchPanel.add(searchField);

        // Botón para buscar 
        searchButton = new JButton("Buscar");
        searchButton.setForeground(textColor);
        searchButton.setBackground(buttonBackgroundColor);
        searchPanel.add(searchButton);

        // Botón para nuevo cliente 
        newClientButton = new JButton("Nuevo Cliente");
        newClientButton.setForeground(textColor);
        newClientButton.setBackground(buttonBackgroundColor);
        searchPanel.add(newClientButton);        

        // Añadir el panel de búsqueda al panel de clientes
        clientesPanel.add(searchPanel, BorderLayout.NORTH);

        mTablaCliente = new DefaultTableModel(new Object[]{"ID Cliente", "Nombre", "Apellido", "Email", "Teléfono"}, 0);
        clientsTable = new JTable(mTablaCliente);
        clientsTable.setBackground(backgroundColor);
        clientsTable.setForeground(textColor);
        clientsTable.setFillsViewportHeight(true); // Hacer que la tabla llene el espacio disponible


        // Crear un JScrollPane para la tabla
        tableScrollPane = new JScrollPane(clientsTable);
        clientesPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Agregar la pestaña de Clientes al TabbedPane
        tabbedPane.addTab("Clientes", clientesPanel);

        // pestaña Empleados
        empleadosPanel = new JPanel();
        empleadosPanel.setLayout(new BorderLayout());
        empleadosPanel.setBackground(backgroundColor);
        
        // Panel superior para buscar y añadir nuevo Empleado
        searchPanel1 = new JPanel();
        searchPanel1.setBackground(backgroundColor);
        searchPanel1.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alinear a la izquierda

        // Caja de texto para buscar Empleado
        searchField1 = new JTextField(20); // 20 columnas
        searchPanel1.add(searchField1);
        

        // Botón para buscar Empleado
        searchButton1 = new JButton("Buscar");
        searchButton1.setForeground(textColor);
        searchButton1.setBackground(buttonBackgroundColor);
        searchPanel1.add(searchButton1);
        

        // Botón para nuevo Empleado
        newEmpleadoButton = new JButton("Nuevo Empleado");
        newEmpleadoButton.setForeground(textColor);
        newEmpleadoButton.setBackground(buttonBackgroundColor);
        searchPanel1.add(newEmpleadoButton);
        
        // Añade el panel de búsqueda al panel de empleados
        empleadosPanel.add(searchPanel1, BorderLayout.NORTH);
        
        // Define modelo de Tabla empleados
        mTablaEmpleado = new DefaultTableModel(new Object[]{"ID Empleado", "Nombre", "Cargo", "Turno"},0);
        empleadosTable = new JTable (mTablaEmpleado);
        empleadosTable.setBackground(backgroundColor);
        empleadosTable.setForeground(textColor);
        empleadosTable.setFillsViewportHeight(true); // Hace que la tabla llene el espacio disponible

        // Crear un JScrollPane para la tabla
        tableScrollPane1 = new JScrollPane(empleadosTable);
        empleadosPanel.add(tableScrollPane1, BorderLayout.CENTER);                
        
        tabbedPane.addTab("Empleados", empleadosPanel);
        
        // Crear el panel principal cartaPanel
        cartaPanel = new JPanel(new BorderLayout());
        cartaPanel.setBackground(backgroundColor);

        // Crear el panel de título con FlowLayout y agregar la etiqueta
        JPanel tituloPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); // Flujo a la izquierda con un poco de espacio entre los componentes
        tituloPanel.setBackground(backgroundColor);

        // Crear la etiqueta del título
        JLabel tituloLabel = new JLabel("Menú del Día");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLabel.setForeground(textColor); // Cambia el color del texto según sea necesario
        tituloPanel.add(tituloLabel); // Añadir el título al panel

        // Crear un panel de contenedor central que mantendrá el título centrado
        JPanel centroPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); 
        centroPanel.setBackground(backgroundColor);
        centroPanel.add(tituloLabel); // Añadir título al panel centrado

        // Crear el botón a la derecha dentro del mismo panel
        JButton botonRegPlato = new JButton("Agregar Plato");
        botonRegPlato.setBackground(buttonBackgroundColor);
        botonRegPlato.setForeground(textColor);
        
        // Crear un panel contenedor para la derecha y añadir el botón
        JPanel derechaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        derechaPanel.setBackground(backgroundColor);
        derechaPanel.add(botonRegPlato); // Añadir el botón al panel

        // Crear un panel de título que contendrá tanto el título centrado como el botón
        JPanel finalTituloPanel = new JPanel(new BorderLayout());
        finalTituloPanel.setBackground(backgroundColor);
        finalTituloPanel.add(centroPanel, BorderLayout.CENTER); // Título centrado
        finalTituloPanel.add(derechaPanel, BorderLayout.EAST);  // Botón a la derecha

        // Crear la tabla cartaTable con una columna de checkbox
        cartaTableModel = new DefaultTableModel(
                new Object[]{"ID", "Plato", "Personal", "Fuente","stock", "Disponibilidad"},0); 
        cartaTable = new JTable(cartaTableModel);
        cartaTable.setFillsViewportHeight(true); // Hace que la tabla llene el espacio disponible

        
        TableColumn column = cartaTable.getColumnModel().getColumn(5); // La columna de disponibilidad
        column.setCellRenderer(new CheckboxRendererEditor(cartaTable));  // Establecer el renderizador
        column.setCellEditor(new CheckboxRendererEditor(cartaTable));    // Establecer el editor
        
        // Aplicar DecimalFormatRenderer a las columnas de precios
        TableColumn personalColumn = cartaTable.getColumnModel().getColumn(2); // Columna Personal
        personalColumn.setCellRenderer(new DecimalFormatRenderer(cartaTable));

        TableColumn familiarColumn = cartaTable.getColumnModel().getColumn(3); // Columna Familiar
        familiarColumn.setCellRenderer(new DecimalFormatRenderer(cartaTable));


        // Agregar la tabla a un JScrollPane para soporte de desplazamiento
        JScrollPane cartaScrollPane = new JScrollPane(cartaTable);
        cartaScrollPane.setBackground(backgroundColor);

        // Agregar el título y la tabla a cartaPanel
        cartaPanel.add(finalTituloPanel, BorderLayout.NORTH);
        cartaPanel.add(cartaScrollPane, BorderLayout.CENTER); // Tabla en el centro

        // Agregar cartaPanel como una nueva pestaña en tabbedPane
        tabbedPane.addTab("Carta", cartaPanel);

        //pestañas para otros botones
        reportesPanel = new JPanel();
        reportesPanel.setBackground(backgroundColor);
        reportesPanel.add(new JLabel("Panel de Reportes")); // Se puede personalizar el contenido
        tabbedPane.addTab("Reportes", reportesPanel);

        configuracionPanel = new JPanel();
        configuracionPanel.setBackground(backgroundColor);
        configuracionPanel.add(new JLabel("Panel de Configuración")); // Se puede personalizar el contenido
        
        // Crear panel para los campos
        JPanel camposPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        camposPanel.setBackground(backgroundColor);

        // Etiquetas y campos de entrada
        JLabel nombreEmpresaLabel = new JLabel("Nombre de la Empresa:");
        nombreEmpresaLabel.setForeground(textColor);
        camposPanel.add(nombreEmpresaLabel);
        JTextField nombreEmpresaField = new JTextField();
        camposPanel.add(nombreEmpresaField);

        JLabel cantidadMesasLabel = new JLabel("Cantidad de Mesas a Mostrar:");
        cantidadMesasLabel.setForeground(textColor);
        camposPanel.add(cantidadMesasLabel);
        JTextField cantidadMesasField = new JTextField();
        camposPanel.add(cantidadMesasField);

        JLabel permisosAgregarLabel = new JLabel("Permitir Agregar:");
        permisosAgregarLabel.setForeground(textColor);
        camposPanel.add(permisosAgregarLabel);
        JCheckBox permisosAgregarCheckBox = new JCheckBox();
        camposPanel.add(permisosAgregarCheckBox);

        JLabel permisosModificarLabel = new JLabel("Permitir Modificar:");
        permisosModificarLabel.setForeground(textColor);
        camposPanel.add(permisosModificarLabel);
        JCheckBox permisosModificarCheckBox = new JCheckBox();
        camposPanel.add(permisosModificarCheckBox);

        JLabel permisosAccederVentanasLabel = new JLabel("Permitir Acceder a Ventanas:");
        permisosAccederVentanasLabel.setForeground(textColor);
        camposPanel.add(permisosAccederVentanasLabel);
        JCheckBox permisosAccederVentanasCheckBox = new JCheckBox();
        camposPanel.add(permisosAccederVentanasCheckBox);

        JLabel permisosAccederBotonesLabel = new JLabel("Permitir Acceder a Botones:");
        permisosAccederBotonesLabel.setForeground(textColor);
        camposPanel.add(permisosAccederBotonesLabel);
        JCheckBox permisosAccederBotonesCheckBox = new JCheckBox();
        camposPanel.add(permisosAccederBotonesCheckBox);

        JLabel configuracionActivaLabel = new JLabel("Configuración Activa:");
        configuracionActivaLabel.setForeground(textColor);
        camposPanel.add(configuracionActivaLabel);
        JCheckBox configuracionActivaCheckBox = new JCheckBox();
        camposPanel.add(configuracionActivaCheckBox);

        // Crear panel para los botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.setBackground(backgroundColor);

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBackground(buttonBackgroundColor);
        actualizarButton.setForeground(textColor);

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.setBackground(buttonBackgroundColor);
        cancelarButton.setForeground(textColor);


        botonesPanel.add(actualizarButton);
        botonesPanel.add(cancelarButton);

        // Agregar paneles al configuracionPanel
        configuracionPanel.setLayout(new BorderLayout());
        configuracionPanel.add(camposPanel, BorderLayout.CENTER);
        configuracionPanel.add(botonesPanel, BorderLayout.SOUTH);

        
        tabbedPane.addTab("Configuración", configuracionPanel);

        // Crear botones que cambian las pestañas
        for (int i = 0; i < buttonNames.length; i++) {
            String name = buttonNames[i];
            JButton button = new JButton(name);
            button.setForeground(textColor);
            button.setBackground(buttonBackgroundColor);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            
            final int tabIndex = i; // Guarda el índice de la pestaña correspondiente
            // Añadir ActionListener para cambiar de pestaña
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tabbedPane.setSelectedIndex(tabIndex);
                }
            });
            
            leftPanel.add(button);
            leftPanel.add(Box.createVerticalStrut(10)); // Espacio entre botones
        }

        add(topPanel, BorderLayout.NORTH); // Agregar el panel superior
        add(scrollPane, BorderLayout.WEST);
        add(tabbedPane, BorderLayout.CENTER);
        
        // Añadir placeholder y limpiar el campo de búsqueda cuando el usuario haga clic
        searchReserva.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchReserva.getText().equals("Buscar pedidos...")) {
                    searchReserva.setText("");
                    searchReserva.setForeground(Color.BLACK); // Cambiar el color al hacer clic
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchReserva.getText().isEmpty()) {
                    searchReserva.setText("Buscar pedidos...");
                    searchReserva.setForeground(Color.GRAY);
                }
            }
        });
        
        // Añadir el ActionListener para la acción al hacer clic
        modificarPlanoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al método que actualiza el plano de mesas
                actualizarPlanoDeMesas();
            }
        });
        
        //Añadir ActionListener al botón "Nuevo Plato"
        botonRegPlato.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                vistaRegProducto vistaRP = new vistaRegProducto();
                vistaRP.setVisible(true);
                new cRegProducto(vistaRP);
                vistaRP.setControladorH(controladorH);
            }
        });
        
        // Añadir ActionListener al botón "Nuevo Empleado"
        newEmpleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaRegEmpleado vistaReg = new vistaRegEmpleado(); // Abre el formulario de registro de empleado
                vistaReg.setVisible(true);
                vistaReg.setControladorH(controladorH);
                new cRegEmpleado(vistaReg);
            }
        });
        
        // Añadir ActionListener al botón "Nuevo Cliente"
        newClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaRegCliente vistaRC = new vistaRegCliente(); // Abre el formulario de registro de clientes
                vistaRC.setControladorH(controladorH);
                vistaRC.setVisible(true);
                new cRegCliente(vistaRC);
            }
        });
        
        // Añadir MouseListener a la tabla de clientes
        clientsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Obtener la fila seleccionada
                int row = clientsTable.rowAtPoint(evt.getPoint());

                // Verificar que la fila seleccionada sea válida
                if (row >= 0) {
                    // Obtener el idCliente de la primera columna (suponiendo que es la columna 0)
                    int idCliente = (int) clientsTable.getValueAt(row, 0);
                    vistaRegCliente vista = new vistaRegCliente();
                    controladorRC = new cRegCliente (vista);
                    vista.setControladorH(controladorH);
                    vista.getBtnNuevo().setVisible(false);
                    vista.getBtnModificar().setVisible(true);
                    

                    // Llamar al controlador para buscar al cliente usando el idCliente
                    controladorRC.buscarCliente(idCliente);
                    vista.setVisible(true);
                }
            }
        });
        
        // Añadir MouseListener a la tabla de Empleados
        empleadosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Obtener la fila seleccionada
                int row = empleadosTable.rowAtPoint(evt.getPoint());

                // Verificar que la fila seleccionada sea válida
                if (row >= 0) {
                    // Obtener el idCliente de la primera columna (suponiendo que es la columna 0)
                    int idEmpleado = (int) empleadosTable.getValueAt(row, 0);
                    vistaRegEmpleado vista = new vistaRegEmpleado();
                    controladorE = new cRegEmpleado (vista);
                    vista.setControladorH(controladorH);
                    vista.getBtnNuevo().setVisible(false);
                    vista.getBtnModificar().setVisible(true);
                    

                    // Llamar al controlador para buscar al empleado usando el idEmpleado
                    controladorE.buscarEmpleadoxId(idEmpleado);
                    vista.setVisible(true);
                }
            }
        });
        
        //Añadir Mouse listener a la tabla productos
        cartaTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                // Verificar si el cambio fue en la columna de disponibilidad
                if (column == 5) { // Columna de "Disponibilidad"
                    Boolean disponibilidad = (Boolean) cartaTableModel.getValueAt(row, column);
                    int idProducto = (int) cartaTableModel.getValueAt(row, 0); // Suponiendo que el ID está en la columna 0

                    // Si la disponibilidad cambió, actualizamos en la base de datos
                    controladorH.actualizarDisponibilidad(idProducto, disponibilidad);
                }
            }
        });

        
        // Agregar un DocumentListener para el campo de búsqueda Clientes
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                controladorH.buscarClientesPorNombre(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                controladorH.buscarClientesPorNombre(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
        
        // Agregar un DocumentListener para el campo de búsqueda Empleado
        searchField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                controladorH.buscarEmpleadosPorNombre(searchField1.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                controladorH.buscarEmpleadosPorNombre(searchField1.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
    }
    
    private void actualizarPlanoDeMesas() {
        // Obtener las mesas existentes desde el controlador
        ArrayList<Mesa> mesasExistentes = controladorH.obtenerTodasLasMesas();
        // Solicitar al usuario el número de mesas a agregar o actualizar
        String input = JOptionPane.showInputDialog("Ingrese el número de mesas a agregar o modificar:");
        if (input != null && !input.isEmpty()) {
            try {
                int cantidadMesas = Integer.parseInt(input);
                if (cantidadMesas <= 0) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un número mayor que 0.");
                    return;
                }

                // Actualizar las mesas existentes y crear las adicionales si es necesario
                for (int i = 1; i <= cantidadMesas; i++) {
                    // Solicitar la capacidad de la mesa
                    String capacidadInput = JOptionPane.showInputDialog("Ingrese la capacidad para la Mesa " + i + ":");
                    if (capacidadInput == null || capacidadInput.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar una capacidad válida.");
                        i--; // Retrocede el índice para volver a pedir la capacidad para esta mesa
                        continue;
                    }

                    int capacidad;
                    try {
                        capacidad = Integer.parseInt(capacidadInput);
                        if (capacidad <= 0) {
                            JOptionPane.showMessageDialog(null, "Debe ingresar un número mayor que 0.");
                            i--; // Retrocede el índice para volver a pedir la capacidad para esta mesa
                            continue;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.");
                        i--; // Retrocede el índice para volver a pedir la capacidad para esta mesa
                        continue;
                    }

                    if (i <= mesasExistentes.size()) {
                        // Si la mesa ya existe, actualizar sus datos
                        Mesa mesaExistente = mesasExistentes.get(i - 1);
                        mesaExistente.setNumeroMesa(i);
                        mesaExistente.setCapacidad(capacidad);
                        controladorH.actualizarMesa(mesaExistente);
                    } else {
                        // Si la mesa no existe, crear una nueva
                        controladorH.agregarMesa(i, capacidad, "Disponible");
                    }
                }

                // Llenar la vista con las mesas actualizadas en la base de datos
                controladorH.mostrarTodasLasMesas();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.");
            }
        }
    }

    
    /**
    * Método para llenar la vista con los paneles de las mesas obtenidas de la base de datos.
    */
    public void llenarVistaConMesas(ArrayList<Mesa> mesas) {
        // Limpiar el panel de mesas actual
        rightTablePanel.removeAll();
        
        // Crear un panel visual para cada mesa
        for (Mesa mesa : mesas) {
            JPanel tablePanel = new JPanel();
            tablePanel.setBorder(BorderFactory.createLineBorder(textColor, 2));
            tablePanel.setBackground(backgroundColor);
            tablePanel.setLayout(new BorderLayout());

            // Crear y agregar la etiqueta con el número de la mesa
            JLabel tableLabel = new JLabel("Mesa " + mesa.getNumeroMesa());
            tableLabel.setHorizontalAlignment(JLabel.CENTER);
            tableLabel.setForeground(textColor);
            tablePanel.add(tableLabel, BorderLayout.CENTER);

            // Añadir MouseListener para abrir vistaRegReserva al hacer clic
            final int mesaNumero = mesa.getNumeroMesa();
            tablePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    vistaRegReserva vistaR = new vistaRegReserva(mesaNumero);
                    vistaR.setVisible(true);
                    new cRegReserva(vistaR);
                    vistaR.setControladorH(controladorH);
                }
            });

            // Añadir el panel de la mesa al panel principal
            rightTablePanel.add(tablePanel);
        }

       // Actualizar el diseño del panel para que se muestren las mesas nuevas
       rightTablePanel.revalidate();
       rightTablePanel.repaint();
   }
    
    /**
     * Método para establecer el controlador para empleados.
     * @param controladorE Objeto de tipo cRegEmpleado que actúa como controlador de los empleados.
     */
    public void setControladorE(cRegEmpleado controladorE) {
        this.controladorE = controladorE;
    }

    /**
     * Método para establecer el controlador para clientes.
     * @param controladorC Objeto de tipo cRegCliente que actúa como controlador de los clientes.
     */
    public void setControladorC(cRegCliente controladorC) {
        this.controladorRC = controladorC;
    }

    /**
     * Método para mostrar mensajes al usuario en una ventana emergente.
     * @param mensaje El mensaje que se va a mostrar al usuario.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Método para mostrar una lista de clientes en la tabla.
     * Limpiar la tabla actual y luego agregar cada cliente de la lista a las filas de la tabla.
     * @param clientes Lista de clientes que se desea mostrar en la tabla.
     */
    public void mostrarClientes(ArrayList<Cliente> clientes) {
        mTablaCliente.setRowCount(0); // Limpiar tabla
        for (Cliente cli : clientes) {
            mTablaCliente.addRow(new Object[]{
                cli.getIdCliente(),
                cli.getNombre(),
                cli.getApellido(),
                cli.getEmail(),
                cli.getTelefono()
            });
        }
    }
    
    /**
     * Método para mostrar una lista de empleados en la tabla.
     * Limpiar la tabla actual y luego agregar cada empleado de la lista a las filas de la tabla.
     * @param empleados Lista de empleados que se desea mostrar en la tabla.
     */
    public void mostrarEmpleados(ArrayList<Empleado> empleados) {
        mTablaEmpleado.setRowCount(0); // Limpiar tabla
        for (Empleado emp : empleados) {
            mTablaEmpleado.addRow(new Object[]{
                emp.getIdEmpleado(),
                emp.getNombreEmp(),
                emp.getCargo(),
                emp.getTurno()
            });
        }
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

    /**
     * Método para establecer el controlador principal del sistema.
     * @param controladorH Objeto de tipo cHome que actúa como controlador principal.
     */
    public void setControladorH(cHome controladorH) {
        this.controladorH = controladorH;
    }
    
    /**
     * Método para establecer el controlador principal del sistema.
     * @param controladorConf Objeto de tipo cHome que actúa como controlador principal.
     */
    public void setControladorConf(cConfig controladorConf) {
        this.controladorConf = controladorConf;
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