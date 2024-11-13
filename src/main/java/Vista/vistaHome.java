/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author EMMANUEL
 */
import Controlador.cRegCliente;
import Controlador.cRegEmpleado;
import Controlador.cHome;
import Modelo.Cliente;
import Modelo.Empleado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class vistaHome extends JFrame {

    
    private cRegEmpleado controladorE;
    private cRegCliente controladorRC;
    
    private JTabbedPane tabbedPane;
    
    private JScrollPane pedidosScrollPane;
    private JScrollPane scrollPane;
    private JScrollPane tableScrollPane;
    private JScrollPane tableScrollPane1;
    
    private JPanel searchPanel;
    private JPanel topPanel;
    private JPanel rightTablePanel;
    private JPanel tablePanel;
    private JPanel leftPanel;
    private JPanel leftScrollPanel;
    private JPanel pedidosPanel;
    private JPanel clientesPanel;
    private JPanel empleadosPanel;
    private JPanel reportesPanel;
    private JPanel configuracionPanel;
    private JPanel searchPanel1;
    
    private JLabel companyNameLabel;
    private JLabel tableLabel;
    
    private JButton searchButton;
    private JButton newClientButton;
    private JButton searchButton1;
    private JButton newEmpleadoButton;
    
    private JTable clientsTable;
    private JTable empleadosTable;
    
    private DefaultTableModel mTablaEmpleado;
    private DefaultTableModel mTablaCliente;
    
    private JTextField searchField1;
    private JTextField searchField;
    
    private cHome controladorH;
    
    
    
    // Color personalizado
    private final Color backgroundColor = new Color(0x0B, 0x11, 0x19);
    private final Color buttonBackgroundColor = new Color(0x20, 0x33, 0x4A);
    private final Color textColor = Color.WHITE;
    
    /**
   * Constructor que inicializa la interfaz gráfica.
   */
    public vistaHome() {
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
        String[] buttonNames = {"Pedidos", "Clientes", "Empleados", "Reportes", "Configuración"};

        // Inicializar el TabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setForeground(textColor);
        tabbedPane.setBackground(backgroundColor);
        tabbedPane.setEnabled(false);
        
        // Crear el primer panel para "Pedidos"
        pedidosPanel = new JPanel();
        pedidosPanel.setLayout(new BorderLayout()); // Usar un layout de BorderLayout

        // Panel izquierdo del primer tab (scroll vertical)
        leftScrollPanel = new JPanel();
        leftScrollPanel.setLayout(new BoxLayout(leftScrollPanel, BoxLayout.Y_AXIS));
        leftScrollPanel.setBackground(backgroundColor);
        leftScrollPanel.add(new JLabel("Información de Pedidos")); // Puedes añadir más componentes aquí

        pedidosScrollPane = new JScrollPane(leftScrollPanel);
        pedidosScrollPane.setPreferredSize(new Dimension(200, 500)); // Ajustar el tamaño del scroll
        pedidosPanel.add(pedidosScrollPane, BorderLayout.WEST); // Añadir al lado izquierdo

        // Panel derecho del primer tab (nombres de mesas)
        rightTablePanel = new JPanel();
        rightTablePanel.setLayout(new GridLayout(0, 4)); // 4 columnas para las mesas
        rightTablePanel.setBackground(backgroundColor);

        // Crear etiquetas para 7 mesas, cada una en un cuadro
        for (int i = 1; i <= 7; i++) {
            tablePanel = new JPanel(); // Crear un panel para la mesa
            tablePanel.setBorder(BorderFactory.createLineBorder(textColor, 2)); // Borde alrededor de la mesa
            tablePanel.setBackground(backgroundColor);
            tablePanel.setLayout(new BorderLayout()); // Usar un layout de BorderLayout

            tableLabel = new JLabel("Mesa " + i);
            tableLabel.setHorizontalAlignment(JLabel.CENTER);
            tableLabel.setForeground(textColor); // Color del texto
            tablePanel.add(tableLabel, BorderLayout.CENTER); // Añadir la etiqueta al panel de la mesa

            rightTablePanel.add(tablePanel); // Añadir el panel de la mesa al panel derecho
        }

        pedidosPanel.add(rightTablePanel, BorderLayout.CENTER); // Añadir al centro

        // Agregar el primer panel a las pestañas
        tabbedPane.addTab("Pedidos", pedidosPanel);

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

        // Añadir el panel de búsqueda al panel de empleados
        empleadosPanel.add(searchPanel1, BorderLayout.NORTH);
        
        // Definir modelo de Tabla empleados
        mTablaEmpleado = new DefaultTableModel(new Object[]{"ID Empleado", "Nombre", "Cargo", "Turno"},0);
        empleadosTable = new JTable (mTablaEmpleado);
        empleadosTable.setBackground(backgroundColor);
        empleadosTable.setForeground(textColor);
        empleadosTable.setFillsViewportHeight(true); // Hacer que la tabla llene el espacio disponible

        // Crear un JScrollPane para la tabla
        tableScrollPane1 = new JScrollPane(empleadosTable);
        empleadosPanel.add(tableScrollPane1, BorderLayout.CENTER);

        
        
        
        tabbedPane.addTab("Empleados", empleadosPanel);
        
        //pestaña para otros botones
        reportesPanel = new JPanel();
        reportesPanel.setBackground(backgroundColor);
        reportesPanel.add(new JLabel("Panel de Reportes")); // Puedes personalizar el contenido
        tabbedPane.addTab("Reportes", reportesPanel);

        configuracionPanel = new JPanel();
        configuracionPanel.setBackground(backgroundColor);
        configuracionPanel.add(new JLabel("Panel de Configuración")); // Puedes personalizar el contenido
        tabbedPane.addTab("Configuración", configuracionPanel);

        // Crear botones que cambian las pestañas
        for (int i = 0; i < buttonNames.length; i++) {
            String name = buttonNames[i];
            JButton button = new JButton(name);
            button.setForeground(textColor);
            button.setBackground(buttonBackgroundColor);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            // Añadir ActionListener a cada botón para cambiar de pestaña
            final int tabIndex = i; // Guarda el índice de la pestaña correspondiente
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
        
        // Agregar un DocumentListener para el campo de búsqueda
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


    }
    
    /**
   * Método para establecer el controlador.
   * @param controladorE Objeto ProductoControlador.
   */
    public void setControladorE(cRegEmpleado controladorE) {
      this.controladorE = controladorE;
    }
    
    public void setControladorC(cRegCliente controladorC) {
      this.controladorRC = controladorC;
    }
    
    /**
   * Método para mostrar mensajes al usuario.
   * @param mensaje Mensaje a mostrar.
   */
    public void mostrarMensaje(String mensaje) {
      JOptionPane.showMessageDialog(this, mensaje);
    }
    
    /**
   * Método para mostrar una lista de empleados en la tabla.
   * @param empleados Lista de empleados.
   */
    public void mostrarEmpleados(ArrayList<Empleado> empleados){
        mTablaEmpleado.setRowCount(0); //limpiar tabla
        for(Empleado emp : empleados){
            mTablaEmpleado.addRow(new Object[]{
                emp.getIdEmpleado(),
                emp.getNombreEmp(),
                emp.getCargo(),
                emp.getTurno()
            });
        }
    }
    
    /**
     * Método para realizar la búsqueda y filtrar datos
     * @param clientes lista clientes filtrada
     */
    public void realizarBusquesa(ArrayList<Cliente> clientes) {
        mostrarClientes(clientes); // Mostrar los clientes filtrados en la tabla
    }
    
    /**
     * Método para mostrar una lista de clientes en la tabla.
     * @param clientes Lista de clientes.
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

    public void setControladorH(cHome controladorH) {
        this.controladorH = controladorH;
    }

}