/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 * Clase vistaLogin que representa la ventana de inicio de sesión en la aplicación.
 * Esta clase extiende JFrame y contiene los componentes necesarios para que el usuario 
 * ingrese su nombre de usuario y contraseña, y posteriormente inicie sesión.
 * @author EMMANUEL
 */
import Controlador.cHome;
import Controlador.cRegEmpleado;
import Controlador.cLogin;
import Controlador.cUsuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class vistaLogin extends JFrame {
    
    private cRegEmpleado controladorE ;
    private cHome controladorH;
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
    private Point initialClick;
    /**
     * Constructor que configura la ventana de inicio de sesión.
     * Inicializa los componentes de la interfaz gráfica y ajusta las propiedades de la ventana.
     */
    public vistaLogin() {
        // Configuración de la ventana
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 390);
        setLocationRelativeTo(null); // Centrar en la pantalla    
        setUndecorated(true);
        initComponents();
        
        // Hacer la ventana movible
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // Obtener la posición del mouse en la pantalla
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Determinar el movimiento del mouse
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Mover la ventana
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });
    }
    
    /**
     * Método para inicializar y configurar los componentes de la interfaz de usuario.
     * Establece los detalles visuales, la disposición y agrega los componentes al panel.
     */
    private void initComponents(){
        
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel izquierdo para la imagen de fondo
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(300, 400)); // Ancho del panel izquierdo
        leftPanel.setBackground(backgroundColor); 

        // Imagen de fondo
        String imagePath = "/img/login.png"; 
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource(imagePath));
        Image scaledImage = backgroundImage.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(backgroundLabel, BorderLayout.CENTER);

        // Panel derecho para los campos y botones
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botón para salir de la aplicación
        JButton exitButton = new JButton();
        exitButton.setPreferredSize(new Dimension(2,40));
        exitButton.setIcon(new ImageIcon(getClass().getResource("/img/icon cancel.png"))); 
        exitButton.setFocusPainted(false); // Sin bordes al seleccionar
        exitButton.setContentAreaFilled(false);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.setBorderPainted(false); // Sin borde
        exitButton.setToolTipText("Salir de la aplicación");
        // Evento para cerrar la aplicación
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridx = 1; // Columna
        gbc.gridy = 0; // Fila
        gbc.anchor = GridBagConstraints.NORTHEAST; // Alinear a la esquina superior derecha
        rightPanel.add(exitButton, gbc);

        // Etiqueta de nombre de usuario
        userLabel = new JLabel("Nombre de usuario:");
        userLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER; // Centrar el texto
        rightPanel.add(userLabel, gbc);

        // Campo de texto para el nombre de usuario
        userField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        rightPanel.add(userField, gbc);

        // Etiqueta de contraseña
        passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        rightPanel.add(passwordLabel, gbc);

        // Campo de texto para la contraseña
        passwordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        rightPanel.add(passwordField, gbc);

        // Botón de inicio de sesión
        loginButton = new JButton("Iniciar sesión");
        loginButton.setBackground(new Color(42, 68, 99));
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        rightPanel.add(loginButton, gbc);

        // Etiqueta para crear una cuenta nueva
        createAccountLabel = new JLabel("¿Crear una cuenta nueva?");
        createAccountLabel.setForeground(Color.WHITE);
        createAccountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(createAccountLabel, gbc);

        // Eventos
        createAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vistaRegUsuario regUsuario = new vistaRegUsuario(vistaLogin.this);
                new cUsuario(regUsuario);
                regUsuario.setVisible(true);
                dispose();
            }
        });

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passwordField.getPassword());
            controladorL.validarCredenciales(username, password);
        });

        // Agregar los paneles al panel principal
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Agregar el panel principal al JFrame
        add(mainPanel);

        
    }

    /**
     * Establece el controlador para la vista de inicio de sesión.
     * 
     * @param controladorL El controlador que gestiona las acciones relacionadas con el inicio de sesión.
     */
    public void setControladorL(cLogin controladorL) {
        this.controladorL = controladorL;
    }
    
    public void setControladorH (cHome controladorH){
        this.controladorH = controladorH;
    }

    /**
     * Muestra un mensaje en una ventana emergente (JOptionPane).
     * Este método puede ser utilizado para mostrar alertas, errores o información al usuario.
     * 
     * @param mensaje El mensaje que se mostrará en la ventana emergente.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}