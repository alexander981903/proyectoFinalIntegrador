/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import javax.swing.*;
import java.awt.*;

public class vistaRegUsuario extends JFrame {
    // Colores personalizados
    private final Color backgroundColor = new Color(0x15, 0x22, 0x31); // Fondo color #152231
    private final Color buttonColor = new Color(0x2A, 0x44, 0x63); // Botón color #2a4463
    private final Color textColor = Color.WHITE; // Color del texto

    private JTextField loginField, dniField, nombreField, apellidoField, emailField, telefonoField;
    private JTextField nombreEmpField,cargoField,turnoField;
    private JLabel dni,Nombre,Apellido,email,telefono;
    private JLabel lblNombreEmp,lblCargo,lblTurno;
    private JPanel rightPanel;
    private JPasswordField claveField;
    private JButton registroButton;
    private String rol;

    private cUsuario controladorU;

    /**
     * Constructor de la clase vistaRegUsuario.
     * Inicializa los componentes de la ventana.
     */
    public vistaRegUsuario(JFrame vistaLogin){
        setTitle("Registro de Usuario");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana
        setLayout(new BorderLayout()); // Usamos BorderLayout para organizar los paneles
        getContentPane().setBackground(backgroundColor); // Fondo personalizado
        initComponents();
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                vistaLogin.setVisible(true);
            }
        });
    }
    
    
    private void initComponents() {
        
        controladorU = new cUsuario(this);
        

        // Panel principal que divide en dos secciones: izquierda (imagen) y derecha (formulario)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE)); // Línea divisoria vertical

        // Panel izquierdo para la imagen
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(backgroundColor);

        // Agregar una imagen al panel izquierdo
        String imagePath = "/img/registro.png"; // Cambia la ruta si es necesario
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
        Image scaledImage = originalIcon.getImage().getScaledInstance(200,300, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // Panel derecho para los campos del formulario
        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel tituloLabel = new JLabel("Registro de Usuario");
        tituloLabel.setForeground(textColor);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(tituloLabel, gbc);

        // Campos de texto
        gbc.gridwidth = 1;
        gbc.gridy++;
        rightPanel.add(createLabel("Login:"), gbc);
        loginField = new JTextField(20);
        gbc.gridx = 1;
        rightPanel.add(loginField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(createLabel("Contraseña:"), gbc);
        claveField = new JPasswordField(20);
        gbc.gridx = 1;
        rightPanel.add(claveField, gbc);
        
        
        boolean esAdmin = controladorU.existeAdministrador();
        
        if (esAdmin) {
            agregarCamposEmpleado(gbc);
            rol = "Empleado";
        } else {           
            agregarCamposCliente(gbc);
            rol = "Cliente";
        }


        // Botón de registro
        registroButton = new JButton("Registrar");
        registroButton.setBackground(buttonColor);
        registroButton.setForeground(textColor);
        registroButton.addActionListener(e -> {
            String login = loginField.getText().trim();
            String clave = new String(claveField.getPassword()).trim();
            
            String rolUser = rol;
                        
            
            if (rolUser.equals("Empleado")) {
                
                String nombreEmp = nombreEmpField.getText();
                String cargoEmp = cargoField.getText();
                String turnoEmp = turnoField.getText();
                // Verificar que los campos del empleado estén completos
                if (nombreEmp.isEmpty() || cargoEmp.isEmpty() || turnoEmp.isEmpty()) {
                    mostrarMensaje("Por favor, complete todos los campos de empleado.");
                    return; // Salir si los campos del empleado no están completos
                }
                // Llamada para agregar un usuario con los datos de empleado
                controladorU.agregarUsuario(login, clave, rolUser, "","","","","",
                    nombreEmp,cargoEmp,turnoEmp);
                dispose();
                vistaLogin ventanaLogin = new vistaLogin();
                new cLogin(ventanaLogin);
                ventanaLogin.setVisible(true);
            } else { 
                String dni = dniField.getText();
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();
                String email = emailField.getText();
                String telefono = telefonoField.getText();
                // Si es Cliente, usamos los campos del cliente
                if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                    mostrarMensaje("Por favor, complete todos los campos de cliente.");
                    return; // Salir si los campos del cliente no están completos
                }
                // Llamada para agregar un usuario con los datos del cliente
                controladorU.agregarUsuario(login, clave, rolUser, dni,nombre,apellido,email,telefono,
                    "","","");
                dispose();
                vistaLogin ventanaLogin = new vistaLogin();
                new cLogin(ventanaLogin);
                ventanaLogin.setVisible(true);
            }
            
            
            
        });


        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        rightPanel.add(registroButton, gbc);

        // Añadir los paneles izquierdo y derecho al panel principal
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    


    // Método para crear etiquetas con estilo personalizado
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        return label;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void setControlerU(cUsuario controladorU) {
        this.controladorU = controladorU;
    }
    
    public void visibleCampos(boolean habilitar){
        
    }
    
    private void agregarCamposEmpleado(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(createLabel("Nombre:"), gbc);
        nombreEmpField = new JTextField(20);
        gbc.gridx = 1;
        rightPanel.add(nombreEmpField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(createLabel("Cargo:"), gbc);
        cargoField = new JTextField(20);
        gbc.gridx = 1;
        rightPanel.add(cargoField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(createLabel("Turno:"), gbc);
        turnoField = new JTextField(20);
        gbc.gridx = 1;
        rightPanel.add(turnoField, gbc);
    }
    
    private void agregarCamposCliente(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(createLabel("DNI:"), gbc);
        dniField = new JTextField(20);
        gbc.gridx = 1;
        rightPanel.add(dniField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(createLabel("Nombre:"), gbc);
        nombreField = new JTextField(20);
        gbc.gridx = 1;
        rightPanel.add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(createLabel("Apellido:"), gbc);
        apellidoField = new JTextField(20);
        gbc.gridx = 1;
        rightPanel.add(apellidoField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(createLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        rightPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(createLabel("Teléfono:"), gbc);
        telefonoField = new JTextField(20);
        gbc.gridx = 1;
        rightPanel.add(telefonoField, gbc);
    }


}


