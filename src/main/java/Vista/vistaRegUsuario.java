/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author EMMANUEL
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Controlador.cHome;
import Controlador.cRegCliente;
import Controlador.cRegEmpleado;
import Controlador.cUsuario;
import Modelo.Cliente;
import Modelo.Empleado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class vistaRegUsuario extends JFrame {
    // Colores personalizados
    private final Color backgroundColor = new Color(0x15, 0x22, 0x31); // Fondo color #152231
    private final Color buttonColor = new Color(0x2A, 0x44, 0x63); // Botón color #2a4463
    private final Color textColor = Color.WHITE; // Color del texto

    // Componentes de la interfaz
    private JTextField loginField;
    private JPasswordField claveField;
    private JComboBox<String> rolComboBox;
    private JComboBox<Object> idComboBox; 
    private JButton registroButton;
    private JButton nuevoEmpleadoButton;
    private JButton nuevoClienteButton;
    
    private cHome controladorH;
    private cUsuario controladorU;

    public vistaRegUsuario() {
        initComponents();
        nuevoEmpleadoButton.setVisible(false);
        nuevoClienteButton.setVisible(false);
    }
    
    public void initComponents(){
    // Configuración de la ventana
    setTitle("Registro de Usuario");
    setSize(500, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Centrar la ventana
    setLayout(new BorderLayout()); // Usamos BorderLayout para organizar los paneles
    getContentPane().setBackground(backgroundColor); // Fondo personalizado

    // Panel superior para loginField y claveField
    JPanel panelSuperior = new JPanel(new GridBagLayout());
    panelSuperior.setBackground(backgroundColor);
    GridBagConstraints gbcSuperior = new GridBagConstraints();
    gbcSuperior.insets = new Insets(10, 10, 10, 10); // Espaciado entre los elementos

    // Crear un JLabel grande para el título "Crea una cuenta"
    JLabel tituloLabel = new JLabel("Crea una cuenta");
    tituloLabel.setForeground(textColor); // Color de texto personalizado
    tituloLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente y tamaño del título

    // Añadir el título al panel superior
    gbcSuperior.gridx = 0;
    gbcSuperior.gridy = 0;
    gbcSuperior.gridwidth = 2; // Ocupa dos columnas para centrar
    panelSuperior.add(tituloLabel, gbcSuperior);

    // Crear los componentes de login y clave
    JLabel loginLabel = new JLabel("Login:");
    loginLabel.setForeground(textColor); // Color de texto personalizado
    loginField = new JTextField(20);

    JLabel claveLabel = new JLabel("Clave:");
    claveLabel.setForeground(textColor); // Color de texto personalizado
    claveField = new JPasswordField(20);

    // Añadir componentes de login y clave debajo del título
    gbcSuperior.gridwidth = 1; // Restablecer a una columna
    gbcSuperior.gridx = 0;
    gbcSuperior.gridy = 1;
    panelSuperior.add(loginLabel, gbcSuperior);

    gbcSuperior.gridx = 1;
    panelSuperior.add(loginField, gbcSuperior);

    gbcSuperior.gridx = 0;
    gbcSuperior.gridy = 2;
    panelSuperior.add(claveLabel, gbcSuperior);

    gbcSuperior.gridx = 1;
    panelSuperior.add(claveField, gbcSuperior);

    // Panel inferior para rolComboBox, idComboBox y botones
    JPanel panelInferior = new JPanel(new GridBagLayout());
    panelInferior.setBackground(backgroundColor);
    GridBagConstraints gbcInferior = new GridBagConstraints();
    gbcInferior.insets = new Insets(10, 10, 10, 10); // Espaciado entre los elementos

    // Crear los componentes del panel inferior
    JLabel rolLabel = new JLabel("Rol:");
    rolLabel.setForeground(textColor); // Color de texto personalizado
    String[] roles = {"Empleado", "Cliente"};
    rolComboBox = new JComboBox<>(roles);
    rolComboBox.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Actualizar el ComboBox de ID según el rol seleccionado
            actualizarIdComboBox((String) rolComboBox.getSelectedItem());
        }
    });

    JLabel idLabel = new JLabel("Empleado/Cliente:");
    idLabel.setForeground(textColor); // Color de texto personalizado
    idComboBox = new JComboBox<>(); // Inicialmente vacío

    nuevoEmpleadoButton = new JButton("Nuevo Empleado");
    nuevoEmpleadoButton.setBackground(buttonColor); // Color del botón
    nuevoEmpleadoButton.setForeground(textColor); // Color del texto del botón
    nuevoEmpleadoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            vistaRegEmpleado ventanaEmpleado = new vistaRegEmpleado();
            ventanaEmpleado.setVisible(true);
            new cRegEmpleado(ventanaEmpleado);
        }
    });

    nuevoClienteButton = new JButton("Nuevo Cliente");
    nuevoClienteButton.setBackground(buttonColor); // Color del botón
    nuevoClienteButton.setForeground(textColor); // Color del texto del botón
    nuevoClienteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            vistaRegCliente ventanaCliente = new vistaRegCliente();
            ventanaCliente.setVisible(true);
            new cRegCliente(ventanaCliente);
        }
    });

    registroButton = new JButton("Registrar");
    registroButton.setBackground(buttonColor); // Color de botón personalizado
    registroButton.setForeground(textColor); // Color de texto del botón
    registroButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String login = loginField.getText();
            char[] claveChar = claveField.getPassword();
            String clave = new String(claveChar);
            String rol = (String) rolComboBox.getSelectedItem();
            Object obj = idComboBox.getSelectedItem();
            
            controladorU.agregarUsuario(login, clave, rol, obj);
        }
    });
    
    idComboBox.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Empleado) {
                Empleado emp = (Empleado) value;
                setText(emp.getIdEmpleado() + " || " + emp.getNombreEmp() + " || " + emp.getCargo());
            } else if (value instanceof Cliente) {
                Cliente cliente = (Cliente) value;
                setText(cliente.getIdCliente() + " || " + cliente.getNombre() + " || " + cliente.getApellido());
            }
            return this;
        }
    });


    // Añadir componentes al panel inferior
    gbcInferior.gridx = 0;
    gbcInferior.gridy = 0;
    panelInferior.add(rolLabel, gbcInferior);

    gbcInferior.gridx = 1;
    panelInferior.add(rolComboBox, gbcInferior);

    gbcInferior.gridx = 0;
    gbcInferior.gridy = 1;
    panelInferior.add(idLabel, gbcInferior);

    gbcInferior.gridx = 1;
    panelInferior.add(idComboBox, gbcInferior);

    gbcInferior.gridx = 2;
    panelInferior.add(nuevoEmpleadoButton, gbcInferior);

    gbcInferior.gridx = 3;
    panelInferior.add(nuevoClienteButton, gbcInferior);

    gbcInferior.gridx = 1;
    gbcInferior.gridy = 2;
    panelInferior.add(registroButton, gbcInferior);

    // Añadir paneles superior e inferior a la ventana principal
    add(panelSuperior, BorderLayout.NORTH);
    add(panelInferior, BorderLayout.CENTER);
}


    // Método para actualizar el ComboBox de IDs según el rol seleccionado
    private void actualizarIdComboBox(String selectedRole) {
        idComboBox.removeAllItems(); // Limpiar el JComboBox        
        
        if ("Empleado".equals(selectedRole)) {
            nuevoEmpleadoButton.setVisible(true);
            nuevoClienteButton.setVisible(false);
            // Cargar empleados en el ComboBox
            controladorU.cargarComboboxEmpleados();
        } else if ("Cliente".equals(selectedRole)) {
            nuevoEmpleadoButton.setVisible(false);
            nuevoClienteButton.setVisible(true);
            // Cargar clientes en el ComboBox
            controladorU.cargarComboboxClientes();
        }
    }

    // Método para mostrar una lista de empleados en el JComboBox
    public void mostrarEmpleadosEnComboBox(ArrayList<Empleado> empleados) {
        idComboBox.removeAllItems(); // Limpiar el JComboBox
        for (Empleado emp : empleados) {
            idComboBox.addItem(emp);
        }
    }
    
    // Método para mostrar una lista de clientes en el JComboBox
    public void mostrarClientesEnComboBox(ArrayList<Cliente> clientes) {
        idComboBox.removeAllItems(); // Limpiar el JComboBox
        for (Cliente c : clientes) {
            idComboBox.addItem(c);
        }
    }
    
    /**
   * Método para mostrar mensajes al usuario.
   * @param mensaje Mensaje a mostrar.
   */
    public void mostrarMensaje(String mensaje) {
      JOptionPane.showMessageDialog(this, mensaje);
    }
    
    public void setControlerU(cUsuario controladorU){
        this.controladorU = controladorU;
    }
}

