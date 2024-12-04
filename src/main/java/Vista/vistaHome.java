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
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class vistaHome extends JFrame {

    
    private cRegEmpleado controladorE;
    private cRegCliente controladorRC;
    private cRegProducto controladorP;
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
    private JLabel dateTimeLabel;
    
    private JButton searchButton;
    private JButton newClientButton;
    private JButton searchButton1;
    private JButton newEmpleadoButton;
    private JButton modificarPlanoButton;
    private JButton btnTabla;
    
    private JTable clientsTable;
    private JTable empleadosTable;
    private JTable cartaTable;
    private JTable reservasTable;
    private JTable tableReportes;
    
    private DefaultTableModel mTablaEmpleado;
    private DefaultTableModel mTablaCliente;
    private DefaultTableModel cartaTableModel;
    private DefaultTableModel reservasTableModel;
    private DefaultTableModel mtableReportes;
    
    private JTextField searchField1;
    private JTextField searchField;
    private JTextField searchReserva;
    private JTextField clienteField;
    
    private JComboBox<String> tipoReporte;
    
    private cHome controladorH;
    private cReporte controladorReporte;
    java.util.Date fechaInicioUtil;
    java.util.Date fechaFinUtil;
    private JDateChooser fechaFin;
    private JDateChooser fechaInicio;
    // Color personalizado
    private final Color backgroundColor = new Color(0x0B, 0x11, 0x19);
    private final Color buttonBackgroundColor = new Color(0x20, 0x33, 0x4A);
    private final Color textColor = Color.WHITE;
    
    /**
   * Constructor que inicializa la interfaz gráfica de la vista principal.
   */
    public vistaHome(Usuario usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        // Configuración del layout principal
        setLayout(new BorderLayout());
        initComponents();        
    }
    
    /**
     * Método para inicializar los componentes de la interfaz.
     * Incluye la creación de paneles, botones, tablas, y pestañas.
     */
    private void initComponents() {
        topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        String imagePath ="/img/logoEmpresa.png";
        ImageIcon logoIcon = new ImageIcon(getClass().getResource(imagePath));
        Image image = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(image));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(logoLabel,BorderLayout.CENTER);

        // Agregar el nombre de la empresa
        companyNameLabel = new JLabel("Cevicheria El Veridico");
        companyNameLabel.setForeground(textColor);
        companyNameLabel.setFont(new Font("Arial", Font.BOLD, 32));
        topPanel.add(companyNameLabel);
        
        // Panel izquierdo con scroll y botones
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(backgroundColor);
        scrollPane = new JScrollPane(leftPanel);
        scrollPane.setBackground(backgroundColor);
        scrollPane.getViewport().setBackground(backgroundColor);
        

        // Inicializar el TabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setForeground(textColor);
        tabbedPane.setBackground(backgroundColor);
        tabbedPane.setEnabled(false);
        
        // Crear el primer panel para "Reserva"
        reservaPanel = new JPanel();
        reservaPanel.setLayout(new BorderLayout()); // Usar un layout de BorderLayout
        
        // Panel superior 
        topPanelReserva = new JPanel();
        topPanelReserva.setLayout(new BorderLayout()); // Alinear a la izquierda
        topPanelReserva.setBackground(backgroundColor); // Color de fondo del panel superior
        
        // Campo de búsqueda de pedidos
        searchReserva = new JTextField(20); // Campo de búsqueda con ancho de 20 columnas
        searchReserva.setText("Buscar reservas...");
        searchReserva.setForeground(Color.GRAY); // Color de texto de búsqueda
        
        // Botón de reserva de mesa
        modificarPlanoButton = new JButton("Modificar Plano");
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

        // Crear el modelo de tabla
        reservasTableModel = new DefaultTableModel(new Object[]{
            "Confirmar", "ID", "Cliente", "Estado Reserva", "Fecha Reserva", "Hora Reserva", "Estado Mesa", "Estado Pedido"
        }, 0);

        // Crear la tabla y establecer el modelo
        reservasTable = new JTable(reservasTableModel);
        

        // Ocultar la columna de ID
        reservasTable.getColumnModel().getColumn(1).setMaxWidth(0);
        reservasTable.getColumnModel().getColumn(1).setMinWidth(0);
        reservasTable.getColumnModel().getColumn(1).setPreferredWidth(0);
        reservasTable.getColumnModel().getColumn(1).setResizable(false);

        // Ajustar los anchos de columnas
        reservasTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        reservasTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        reservasTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        reservasTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        reservasTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        reservasTable.getColumnModel().getColumn(7).setPreferredWidth(100);

        reservasTable.setBackground(backgroundColor);
        reservasTable.setForeground(textColor);
        reservasTable.setFillsViewportHeight(true);
        reservasTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        reservasTable.setPreferredScrollableViewportSize(new Dimension(500, 600));


        // Añadir la tabla a un JScrollPane
        JScrollPane reservasTableScrollPane = new JScrollPane(reservasTable);
        reservasTableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leftScrollPanel.add(reservasTableScrollPane);
        
        reservaScrollPane = new JScrollPane(leftScrollPanel);
        reservaScrollPane.setPreferredSize(new Dimension(600, 600));
        reservaPanel.add(reservaScrollPane, BorderLayout.WEST);
        
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

        mTablaCliente = new DefaultTableModel(new Object[]{"ID Cliente", "Nombre", "Apellido", "Email", "Teléfono","DNI"}, 0);
        clientsTable = new JTable(mTablaCliente);
        clientsTable.setBackground(backgroundColor);
        clientsTable.setForeground(textColor);
        clientsTable.setFillsViewportHeight(true); 


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
        empleadosTable.setFillsViewportHeight(true);

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
        finalTituloPanel.add(centroPanel, BorderLayout.CENTER);
        finalTituloPanel.add(derechaPanel, BorderLayout.EAST);

        // Crear la tabla cartaTable con una columna de checkbox
        cartaTableModel = new DefaultTableModel(
                new Object[]{"ID", "Plato", "Personal", "Fuente","stock", "Disponibilidad"},0); 
        cartaTable = new JTable(cartaTableModel);
        cartaTable.setBackground(new Color(240, 240, 240));
        cartaTable.setFillsViewportHeight(true);
        cartaTable.getTableHeader().setBackground(new Color(0, 51, 102));
        cartaTable.getTableHeader().setForeground(textColor);
        cartaTable.setFont(new Font("Arial", Font.PLAIN, 14));
        cartaTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        
        TableColumn column = cartaTable.getColumnModel().getColumn(5);
        column.setCellRenderer(new CheckboxRendererEditor(cartaTable));
        column.setCellEditor(new CheckboxRendererEditor(cartaTable));
        
        // Aplicar DecimalFormatRenderer a las columnas de precios
        TableColumn personalColumn = cartaTable.getColumnModel().getColumn(2);
        personalColumn.setCellRenderer(new DecimalFormatRenderer(cartaTable));

        TableColumn familiarColumn = cartaTable.getColumnModel().getColumn(3);
        familiarColumn.setCellRenderer(new DecimalFormatRenderer(cartaTable));

        JScrollPane cartaScrollPane = new JScrollPane(cartaTable);
        cartaScrollPane.setBackground(backgroundColor);

        // Agregar el título y la tabla a cartaPanel
        cartaPanel.add(finalTituloPanel, BorderLayout.NORTH);
        cartaPanel.add(cartaScrollPane, BorderLayout.CENTER); // Tabla en el centro

        // Agregar cartaPanel como una nueva pestaña en tabbedPane
        tabbedPane.addTab("Carta", cartaPanel);

        // Pestañas para otros botones
        reportesPanel = new JPanel();
        reportesPanel.setBackground(backgroundColor);
        reportesPanel.setLayout(new BorderLayout());

        // Título del panel de reportes
        JLabel titleLabel = new JLabel("Panel de Reportes", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(textColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        reportesPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de filtros
        JPanel filtersPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textColor), "Filtros de Reporte");
        border.setTitleColor(Color.WHITE);
        filtersPanel.setBorder(border);
        filtersPanel.setBackground(backgroundColor);

        // Campos de fechas y cliente
        JLabel fechaInicioLabel = new JLabel("Fecha de inicio:");
        fechaInicioLabel.setForeground(textColor);
        filtersPanel.add(fechaInicioLabel);
        fechaInicio = new JDateChooser();
        filtersPanel.add(fechaInicio);

        JLabel fechaFinLabel = new JLabel("Fecha de fin:");
        fechaFinLabel.setForeground(textColor);
        filtersPanel.add(fechaFinLabel);
        fechaFin = new JDateChooser();
        filtersPanel.add(fechaFin);

        JLabel clienteLabel = new JLabel("Cliente (DNI o Nombre):");
        clienteLabel.setForeground(textColor);
        filtersPanel.add(clienteLabel);
        clienteField = new JTextField();
        filtersPanel.add(clienteField);
        
        JLabel info = new JLabel("Tipo de Reporte: ");
        info.setForeground(textColor);
        filtersPanel.add(info);
        String[] tiposDeReporte = {"Historial de Reservas", "Informe de inventario", "Reservas por Cliente", "Productos preferidos"};
        tipoReporte = new JComboBox<>(tiposDeReporte);
        filtersPanel.add(tipoReporte);

        // Espacio para mejorar la alineación
        filtersPanel.add(new JLabel(""));
        filtersPanel.add(new JLabel(""));

        reportesPanel.add(filtersPanel, BorderLayout.NORTH);
        
        String[] columnNames = {};
        Object[][] data = {}; // Inicialmente vacía
        
        
        mtableReportes = new DefaultTableModel(data, columnNames);        
        tableReportes = new JTable(mtableReportes) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilita la edición de celdas
            }
        };
        
        

        // Estilizar la tabla
        tableReportes.setFillsViewportHeight(true);
        tableReportes.setBackground(backgroundColor);
        tableReportes.setForeground(textColor);
        tableReportes.setFont(new Font("Arial", Font.PLAIN, 14));
        tableReportes.setRowHeight(25);
        tableReportes.setGridColor(Color.GRAY);

        // Estilo para el encabezado
        JTableHeader header = tableReportes.getTableHeader();
        header.setBackground(new Color(100, 149, 237));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane tableScrollPane = new JScrollPane(tableReportes);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textColor), "Información del Reporte"));
        reportesPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonsPanel.setBackground(backgroundColor);

        JButton btnReporte = new JButton("Generar Reporte");
        JButton btnExportar = new JButton("Exportar a Excel");
        
        
        
        btnExportar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorReporte.exportarAExcel(tableReportes);
            }        
        });
        
        
        btnReporte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorReporte.mostrarReporte();
            }     
        });

        buttonsPanel.add(btnReporte);
        buttonsPanel.add(btnExportar);

        reportesPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Agregar el panel de reportes a las pestañas
        tabbedPane.addTab("Reportes", reportesPanel);


        configuracionPanel = new JPanel();
        configuracionPanel.setBackground(backgroundColor);
        configuracionPanel.add(new JLabel("Panel de Configuración"));
        
        JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 10, 10));
        panelSuperior.setBackground(backgroundColor);
        TitledBorder superiorBorder = BorderFactory.createTitledBorder("Datos de la Empresa");
        superiorBorder.setTitleColor(textColor);
        panelSuperior.setBorder(superiorBorder);

        JLabel nombreEmpresaLabel = new JLabel("Nombre de la Empresa:");
        nombreEmpresaLabel.setForeground(textColor);
        nombreEmpresaLabel.setBackground(backgroundColor);
        JTextField nombreEmpresaField = new JTextField(20);
        JLabel logoEmpresaLabel = new JLabel("Logo de la Empresa:");
        logoEmpresaLabel.setForeground(textColor);
        JButton logoEmpresaButton = new JButton("Seleccionar Logo");
        logoEmpresaButton.setBackground(buttonBackgroundColor);
        logoEmpresaButton.setForeground(textColor);

        panelSuperior.add(nombreEmpresaLabel);
        panelSuperior.add(nombreEmpresaField);
        panelSuperior.add(logoEmpresaLabel);
        panelSuperior.add(logoEmpresaButton);

        // Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 50));
        panelInferior.setBackground(backgroundColor);
        TitledBorder inferiorBorder = BorderFactory.createTitledBorder("Permisos");
        inferiorBorder.setTitleColor(textColor);
        panelInferior.setBorder(inferiorBorder);
        
        String [] checkBoxNames = {"Agregar Empleados", "Agregar Clientes", "Personalizar Menu", "Reportes"} ;
        
        for (int i = 0; i < checkBoxNames.length; i++) {
            JCheckBox checkBox = new JCheckBox();
            String name = checkBoxNames[i];
            checkBox.setBackground(backgroundColor);
            checkBox.setForeground(textColor);           
            checkBox.setText(name);
            panelInferior.add(checkBox);
        }

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
        configuracionPanel.add(panelSuperior, BorderLayout.NORTH);
        configuracionPanel.add(panelInferior, BorderLayout.CENTER);
        configuracionPanel.add(botonesPanel, BorderLayout.SOUTH);

        
        tabbedPane.addTab("Configuración", configuracionPanel);
        
        String[] buttonNames = {"Reserva", "Clientes", "Empleados","Carta", "Reportes"};
        
        String rolUser = usuarioAutenticado.getRol();
        
        for (int i = 0; i < buttonNames.length; i++) {
            String name = buttonNames[i];
            JButton button = new JButton(name);
            button.setForeground(textColor);
            button.setBackground(buttonBackgroundColor);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            
            final int tabIndex = i;
            
            if (rolUser.equals("Cliente")) {
                if (name.equals("Reserva")) {
                    button.setVisible(true);
                    modificarPlanoButton.setEnabled(false);
                } else {
                    button.setVisible(false);
                }
            }else if(rolUser.equals("Empleado")){
                Empleado emp = (Empleado) usuarioAutenticado.getObj();
                String cargoEmp = emp.getCargo();
                if (cargoEmp.equals("Administrador")) {
                    button.setVisible(true);
                } else if (cargoEmp.equals("Cajero")) {
                    if (name.equals("Clientes") || name.equals("Empleados")) {
                        button.setVisible(false);
                    } else {
                        button.setVisible(true);
                    }
                } else if (cargoEmp.equals("Mesero")) {
                    if (name.equals("Clientes") || name.equals("Empleados") ) {
                        button.setVisible(false);
                    } else {
                        button.setVisible(true);
                    }
                } else if (cargoEmp.equals("Chef Ejecutivo")) {
                    if (name.equals("Clientes") || name.equals("Empleados") || name.equals("Reportes")) {
                        button.setVisible(false);
                    } else {
                        button.setVisible(true);
                    }
                }
                
            }

            
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tabbedPane.setSelectedIndex(tabIndex);
                }
            });
            
            leftPanel.add(button);
            leftPanel.add(Box.createVerticalStrut(10)); // Espacio entre botones
        }
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new LineBorder(Color.WHITE, 5));
        
        bottomPanel.setBackground(backgroundColor);
        if (usuarioAutenticado.getRol().equals("Empleado")){
            Empleado emp = (Empleado) usuarioAutenticado.getObj();
            String rol = emp.getCargo();
            JLabel userLabel = new JLabel("Usuario Activo: " + usuarioAutenticado.getLogin() +" - " + rol);
            userLabel.setForeground(textColor);
            userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bottomPanel.add(userLabel, BorderLayout.WEST);
        }else if(usuarioAutenticado.getRol().equals("Cliente")){
            JLabel userLabel = new JLabel("Usuario Activo: " + usuarioAutenticado.getLogin());
            userLabel.setForeground(textColor);
            userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bottomPanel.add(userLabel, BorderLayout.WEST);
        }
        
        dateTimeLabel = new JLabel(getFechaHoraActual());
        dateTimeLabel.setForeground(textColor);
        dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bottomPanel.add(dateTimeLabel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.WEST);
        add(tabbedPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
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
        
        // Añadir el ActionListener para la acción al hacer clic para modificar un plano
        modificarPlanoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Deseas guardar un nuevo plano o modificar uno existente?",
                        "Seleccionar acción",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                // Si la opción es "Sí", es decir, el usuario desea guardar un nuevo plano
                if (opcion == JOptionPane.YES_OPTION) {
                    
                    vistaPlano vistaPlano = new vistaPlano(vistaHome.this);
                    vistaPlano.setVisible(true);
                    vistaPlano.setControladorH(controladorH);
                    new cRegPlano(vistaPlano);
                    vistaPlano.getBtnGuardar().setVisible(true);
                }
                // Si la opción es "No", es decir, el usuario desea modificar un plano existente
                else if (opcion == JOptionPane.NO_OPTION) {
                    // Mostrar la vista para modificar un plano
                    vistaPlano vistaPlano = new vistaPlano(vistaHome.this);
                    vistaPlano.setVisible(true);
                    vistaPlano.setControladorH(controladorH);
                    cRegPlano controladorPlanoR = new cRegPlano(vistaPlano);
                    controladorPlanoR.llenarDatosAModificar();
                    vistaPlano.getBtnModificar().setVisible(true);
                }
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
                vistaRegEmpleado vistaReg = new vistaRegEmpleado();
                vistaReg.setVisible(true);
                vistaReg.setControladorH(controladorH);
                new cRegEmpleado(vistaReg);
            }
        });
        
        // Añadir ActionListener al botón "Nuevo Cliente"
        newClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaRegCliente vistaRC = new vistaRegCliente();
                vistaRC.setControladorH(controladorH);
                vistaRC.setVisible(true);
                new cRegCliente(vistaRC);
            }
        });
        
        // Añadir MouseListener a la tabla de clientes
        clientsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = clientsTable.rowAtPoint(evt.getPoint());

                // Verificar que la fila seleccionada sea válida
                if (row >= 0) {
                    int idCliente = (int) clientsTable.getValueAt(row, 0);

                    // Mostrar cuadro de diálogo de confirmación
                    int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Está seguro de que desea modificar este cliente?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );

                    if (opcion == JOptionPane.YES_OPTION) {
                        // Si el usuario confirma, abrir la vista de edición
                        vistaRegCliente vista = new vistaRegCliente();
                        controladorRC = new cRegCliente(vista);
                        vista.setControladorH(controladorH);
                        vista.getBtnNuevo().setVisible(false);
                        vista.getBtnModificar().setVisible(true);
                        vista.activarCampos(false);

                        // Llamar al controlador para buscar al cliente usando el idCliente
                        controladorRC.buscarCliente(idCliente);
                        vista.setVisible(true);
                    } else {
                        // Si el usuario selecciona "No" o cierra el diálogo
                        JOptionPane.showMessageDialog(null, "Modificación cancelada.");
                    }
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
        
        // Añadir MouseListener a la tabla de carta
        cartaTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Obtener la fila y la columna seleccionada
                int row = cartaTable.rowAtPoint(evt.getPoint());
                int column = cartaTable.columnAtPoint(evt.getPoint());

                // Limitar la ejecución solo a las columnas 0 a 4
                if (column >= 0 && column <= 4) {
                    int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Está seguro de querer modificar este plato?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );

                    // Acciones según la respuesta del usuario
                    if (opcion == JOptionPane.YES_OPTION) {
                        if (row >= 0) {
                            // Obtener el idPlato de la primera columna (columna 0)
                            int idPlato = (int) cartaTable.getValueAt(row, 0);
                            vistaRegProducto vista = new vistaRegProducto();
                            controladorP = new cRegProducto(vista);
                            vista.setIdPlato(idPlato);
                            vista.activarCampos(false);
                            controladorP.llenarDatosParaActualizar();
                            vista.getRegistrarButton().setVisible(false);
                            vista.getModificarButton().setVisible(true);
                            vista.setControladorH(controladorH);
                            vista.setVisible(true);
                        }
                    } else if (opcion == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(null, "Has seleccionado 'No'. La acción se ha cancelado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Has cerrado el diálogo o cancelado la operación.");
                    }
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
    

    
    /**
    * Método para llenar la vista con los paneles de las mesas.
    */
    public void llenarVistaConMesas(ArrayList<Mesa> mesas) {
        
        rightTablePanel.removeAll();

        // Crear un panel visual para cada mesa
        for (Mesa mesa : mesas) {
            JPanel tablePanel = new JPanel();
            tablePanel.setBorder(BorderFactory.createLineBorder(textColor, 2));
            tablePanel.setLayout(new BorderLayout());

            // Crear el panel para contener tanto el título como la imagen
            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new BorderLayout());
            imagePanel.setBackground(Color.decode("#2A4463"));
            // Configuración para mesas reservadas
            if ("Reservada".equalsIgnoreCase(mesa.getEstadoMesa().trim())) {
                Timer timer = new Timer(500, new ActionListener() {
                    boolean isRed = false;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Alternar colores
                        SwingUtilities.invokeLater(() -> {
                            imagePanel.setBackground(isRed ? Color.RED : backgroundColor);
                            imagePanel.revalidate();
                            imagePanel.repaint();
                        });
                        isRed = !isRed;
                    }
                });
                timer.start();
            }else if("Ocupada".equalsIgnoreCase(mesa.getEstadoMesa().trim())){
                imagePanel.setBackground(Color.GREEN);
            }

            
            JLabel titleLabel = new JLabel("Mesa " + mesa.getNumeroMesa());
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            titleLabel.setForeground(textColor);
            imagePanel.add(titleLabel, BorderLayout.NORTH);
            
            JLabel capacidadLabel = new JLabel("Capacidad: " + mesa.getCapacidad() + " personas");
            capacidadLabel.setHorizontalAlignment(JLabel.CENTER);
            capacidadLabel.setForeground(textColor);
            imagePanel.add(capacidadLabel, BorderLayout.SOUTH);
            
            String imagePath = "/img/mesa-de-comedor.png";
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));

            // Escalar la imagen (si es necesario)
            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Crear un JLabel para la imagen
            JLabel imageLabel = new JLabel(scaledIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imagePanel.add(imageLabel, BorderLayout.CENTER);  // Añadir la imagen en el centro

            // Añadir el panel con la imagen y el título al panel principal
            tablePanel.add(imagePanel, BorderLayout.CENTER);

            // Añadir MouseListener para abrir vistaRegReserva al hacer clic
            tablePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!"Reservada".equalsIgnoreCase(mesa.getEstadoMesa()) && !"Ocupada".equalsIgnoreCase(mesa.getEstadoMesa())) {
                        vistaRegReserva vistaR = new vistaRegReserva(mesa,usuarioAutenticado);
                        vistaR.setVisible(true);                        
                        new cRegReserva(vistaR);
                        vistaR.setControladorH(controladorH);
                    } else {
                        if (usuarioAutenticado != null) {
                            if ("Cliente".equalsIgnoreCase(usuarioAutenticado.getRol())) {
                                // Para el rol Cliente
                                JOptionPane.showMessageDialog(
                                    tablePanel,
                                    "Esta mesa está " + mesa.getEstadoMesa() + " y no se puede modificar.",
                                    "Mesa " + mesa.getEstadoMesa(),
                                    JOptionPane.WARNING_MESSAGE
                                );
                            } else if ("Empleado".equalsIgnoreCase(usuarioAutenticado.getRol())) {
                                // Para el rol Empleado
                                int filaSeleccionada = reservasTable.getSelectedRow();
                                int columnaSeleccionada = reservasTable.getSelectedColumn();

                                // Solo actuar si se hizo clic en columnas de la 1 a la 7
                                if (columnaSeleccionada >= 1 && columnaSeleccionada <= 7) {
                                    Object idReserva = reservasTableModel.getValueAt(filaSeleccionada, 1);

                                    if (idReserva != null && idReserva instanceof Integer) {
                                        int id = (int) idReserva;
                                        vistaInfoPedido frameInfo = new vistaInfoPedido(id);
                                        frameInfo.setVisible(true);
                                        new cInfo(frameInfo);
                                    } else {
                                        mostrarMensaje("ID de reserva no encontrado.");
                                    }
                                }
                            }
                        }
                    }
                }
            });

            // Añadir el panel de la mesa al panel principal
            rightTablePanel.add(tablePanel);
        }

        iniciarReloj();
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
    
    public void mostrarReporte(String tipoReporte, ArrayList<?> data) {
    mtableReportes = (DefaultTableModel) tableReportes.getModel();
    mtableReportes.setRowCount(0); // Limpiar tabla

    // Modificar el modelo de la tabla según el tipo de reporte
    switch (tipoReporte) {
        case "Historial de Reservas":
            // Cambiar las columnas para el reporte de historial de reservas
            mtableReportes.setColumnIdentifiers(new Object[]{"ID Reserva", "Fecha", "Cliente", "Total Pedido", "Número de Mesa"});
            for (Reserva r : (ArrayList<Reserva>) data) {
                mtableReportes.addRow(new Object[]{
                    r.getIdReserva(),
                    r.getFechaReserva(),
                    r.getCliente().getNombre() + " " + r.getCliente().getApellido(),
                    r.getPedido().getTotal(),
                    r.getMesa().getNumeroMesa()
                });
            }
            break;

        case "Informe de inventario":
            // Cambiar las columnas para el reporte de inventario
            mtableReportes.setColumnIdentifiers(new Object[]{"ID Producto", "Nombre Producto", "Precio Personal", "Precio Familiar", "Stock"});
            for (Producto p : (ArrayList<Producto>) data) {
                mtableReportes.addRow(new Object[]{
                    p.getIdProducto(),
                    p.getNombreProducto(),
                    p.getPrecioPersonal(),
                    p.getPrecioFamiliar(),
                    p.getStock()
                });
            }
            break;

        case "Reservas por Cliente":
            // Cambiar las columnas para el reporte de reservas por cliente
            mtableReportes.setColumnIdentifiers(new Object[]{"ID Cliente", "Nombre", "Apellido", "Cantidad de Reservas"});
            for (Cliente c : (ArrayList<Cliente>) data) {
                mtableReportes.addRow(new Object[]{
                    c.getIdCliente(),
                    c.getNombre(),
                    c.getApellido(),
                    controladorReporte.getCantidad()  // Asegúrate de que controladorReporte.getCantidad() esté retornando la cantidad correcta
                });
            }
            break;

        

        default:
            // Caso por defecto (si no coincide con ningún tipo de reporte)
            System.err.println("Tipo de reporte no válido.");
            break;
    }
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
                cli.getTelefono(),
                cli.getDni()
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
     * Método para mostrar una lista de productos en la tabla.
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
     * Método para mostrar una lista Reservas  en la tabla.
     * Limpiar la tabla actual y luego agregar cada reserva de la lista a las filas de la tabla.
     * @param reservas Lista de reservas que se desea mostrar en la tabla.
     */
    public void mostrarReservas(ArrayList<Reserva> reservas) {
        reservasTableModel.setRowCount(0);
        reservasTable.requestFocus();
        ButtonRendererEditor rendererEditor = new ButtonRendererEditor( reservasTable,this);
        reservasTable.getColumnModel().getColumn(0).setCellRenderer(rendererEditor);
        reservasTable.getColumnModel().getColumn(0).setCellEditor(rendererEditor);
        for (Reserva r : reservas) {
            if (r.getCliente() != null && r.getCliente().getNombre() != null &&
                r.getEstadoReserva() != null && r.getMesa() != null && r.getMesa().getEstadoMesa() != null &&
                r.getPedido() != null && r.getPedido().getEstado() != null) {
                // Agregar la fila a la tabla
                reservasTableModel.addRow(new Object[]{
                    "",
                    r.getIdReserva(),
                    r.getCliente().getNombre(),
                    r.getEstadoReserva(),
                    r.getFechaReserva(),
                    r.getHoraReserva(),
                    r.getMesa().getEstadoMesa(),
                    r.getPedido().getEstado()
                });
            }
        }
    }
    
    /**
    * Inicia un temporizador que actualiza la fecha y hora cada segundo.
    * 
    * Este método utiliza un objeto {@link Timer} para ejecutar una tarea cada
    * 1000 milisegundos (1 segundo), actualizando el texto de la etiqueta
    * {@code dateTimeLabel} con la fecha y hora actual obtenida del método 
    * {@code getFechaHoraActual()}.
    * 
    */
    private void iniciarReloj() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateTimeLabel.setText(getFechaHoraActual());
            }
        });
        timer.start();
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    
    
    /**
     * Método para establecer el controlador principal del sistema.
     * @param controladorH Objeto de tipo cHome que actúa como controlador principal.
     */
    public void setControladorH(cHome controladorH) {
        this.controladorH = controladorH;
    }
    
    /**
     * Método para recuperar el controlador principal del sistema.
     * @return una instancia de {@link cHome}, que representa el controlador principal del sistema.
     */
    public cHome getControladorH() {
        return controladorH;
    }
    
    
    
    /**
     * Método para establecer el controlador principal del sistema.
     * @param controladorConf Objeto de tipo cHome que actúa como controlador principal.
     */
    public void setControladorConf(cConfig controladorConf) {
        this.controladorConf = controladorConf;
    }
    
    /**
     * Metodo para recuperar la fecha y hora actual.
     * 
     * @return "dd/MM/yyyy HH:mm:ss".
     */
    private String getFechaHoraActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date());
    }

    public void setControladorReporte(cReporte controladorReporte) {
        this.controladorReporte = controladorReporte;
    }

    public JComboBox<String> getTipoReporte() {
        return tipoReporte;
    }

    public Date getFechaInicioUtil() {        
        return fechaInicioUtil = fechaInicio.getDate();
    }

    public Date getFechaFinUtil() {
        return fechaFinUtil = fechaFin.getDate();
    }

    public JTextField getClienteField() {
        return clienteField;
    }
    
    
    

}