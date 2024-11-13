/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author EMMANUEL
 */
import Controlador.cRegEmpleado;
import Controlador.cHome;
import Controlador.cLogin;
import Controlador.cUsuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaLogin extends JFrame {
    
    private cRegEmpleado controladorE ;
    private cLogin controladorL;
    
    // Color de fondo
    private final Color backgroundColor = new Color(0x15, 0x22, 0x31);
    private final Color buttonColor = new Color(0x2A, 0x44, 0x63);
    private final Color textColor = Color.WHITE;
    
    private JPanel panel;
    
    private GridBagConstraints gbc;
    
    private JLabel userLabel;
    private JLabel passwordLabel;
    
    private JTextField userField;
    
    private JPasswordField passwordField;
    
    private JButton loginButton;
    
    private JLabel createAccountLabel; 
    
    public vistaLogin() {
        // Configuración de la ventana
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Centrar en la pantalla    
        
        initComponents();
    }
    
    private void initComponents(){
        
        // Panel principal
        panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.setLayout(new GridBagLayout()); // Usar GridBagLayout para centrar los componentes

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espacio alrededor de los componentes

        // Etiqueta de nombre de usuario
        userLabel = new JLabel("Nombre de usuario:");
        userLabel.setForeground(textColor);
        gbc.gridx = 0; // Columna
        gbc.gridy = 0; // Fila
        panel.add(userLabel, gbc);

        // Campo de texto para el nombre de usuario
        userField = new JTextField(20);
        gbc.gridx = 1; // Columna
        gbc.gridy = 0; // Fila
        panel.add(userField, gbc);

        // Etiqueta de contraseña
        passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(textColor);
        gbc.gridx = 0; // Columna
        gbc.gridy = 1; // Fila
        panel.add(passwordLabel, gbc);

        // Campo de texto para la contraseña
        passwordField = new JPasswordField(20);
        gbc.gridx = 1; // Columna
        gbc.gridy = 1; // Fila
        panel.add(passwordField, gbc);

        // Botón de inicio de sesión
        loginButton = new JButton("Iniciar sesión");
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(textColor);
        gbc.gridx = 0; // Columna
        gbc.gridy = 2; // Fila
        gbc.gridwidth = 2; // Ocupa dos columnas
        panel.add(loginButton, gbc);

        // Etiqueta "Crear una cuenta nueva"
        createAccountLabel = new JLabel("¿Crear una cuenta nueva?");
        createAccountLabel.setForeground(textColor);
        gbc.gridx = 0; // Columna
        gbc.gridy = 3; // Fila
        gbc.gridwidth = 2; // Ocupa dos columnas
        createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        createAccountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cambiar el cursor a mano
        panel.add(createAccountLabel, gbc);

        // Evento al hacer clic en "Crear una cuenta nueva"
        createAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {                
                vistaRegUsuario regUsuario = new vistaRegUsuario();
                new cUsuario (regUsuario);
                regUsuario.setVisible(true);
                
                dispose(); // Cerrar la ventana de login al abrir el registro
            }
        });

        // Evento al hacer clic en el botón de inicio de sesión
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());
                // lógica de inicio de sesión
                controladorL.validarCredenciales(username, password);            
            }
        });

        // Añadir el panel al frame
        add(panel);
        
    }

    public void setControladorL(cLogin controladorL) {
        this.controladorL = controladorL;
    }
    
    /**
   * Método para mostrar mensajes al usuario.
   * @param mensaje Mensaje a mostrar.
   */
    public void mostrarMensaje(String mensaje) {
      JOptionPane.showMessageDialog(this, mensaje);
    }
}