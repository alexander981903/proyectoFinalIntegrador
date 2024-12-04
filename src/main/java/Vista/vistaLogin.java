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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
        setSize(600, 480);
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
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(backgroundColor);
        
        topPanel.add(Box.createVerticalStrut(20));
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/icon_login48.png"));
        Image scaledIcon = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(iconLabel);
        
        JLabel welcomeLabel = new JLabel("¡Bienvenidos!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE); 
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        topPanel.add(Box.createVerticalStrut(20)); 
        topPanel.add(welcomeLabel); 
        topPanel.add(Box.createVerticalStrut(20)); 
        
        // Panel izquierdo para la imagen de fondo
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(318, 400)); // Ancho del panel izquierdo
        leftPanel.setBackground(backgroundColor);

        // Imagen de fondo
        String imagePath = "/img/login.png";
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource(imagePath));
        Image scaledImage = backgroundImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(backgroundLabel, BorderLayout.CENTER);
        
        // Panel derecho para los campos y botones
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botón de inicio de sesión
        loginButton = new JButton("Iniciar sesión");
        loginButton.setBackground(new Color(42, 68, 99));
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1; // Botón ocupará solo una columna
        rightPanel.add(loginButton, gbc);

        // Botón para salir de la aplicación
        JLabel exitLabel = new JLabel();
        exitLabel.setPreferredSize(new Dimension(40,40));
        ImageIcon iconCancel = new ImageIcon(getClass().getResource("/img/icon cancel.png"));
        Image imageCancel = iconCancel.getImage();
        Image scaledImageCancel = imageCancel.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        exitLabel.setIcon(new ImageIcon(scaledImageCancel));
        exitLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitLabel.setToolTipText("Salir de la aplicación");
        exitLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Evento para cerrar la aplicación al hacer clic en el JLabel
        exitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.exit(0);  // Cerrar la aplicación
            }
        });
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        rightPanel.add(exitLabel, gbc);
        
        userLabel = new JLabel("Nombre de usuario:");
        userLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER; 
        rightPanel.add(userLabel, gbc);
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userPanel.setBackground(backgroundColor);
        ImageIcon userIcon = new ImageIcon(getClass().getResource("/img/icon_user48.png"));
        JLabel userIconLabel = new JLabel(userIcon);
        userField = new JTextField(20);
        
        userIcon.setImage(userIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

        userPanel.add(userIconLabel);
        userPanel.add(userField);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        rightPanel.add(userPanel, gbc);
        
        
        passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(passwordLabel, gbc);
        
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(backgroundColor);
        
        ImageIcon passwordIcon = new ImageIcon(getClass().getResource("/img/icon_password48.png"));
        JLabel passwordIconLabel = new JLabel(passwordIcon);
        passwordIcon.setImage(passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordIconLabel);
        passwordPanel.add(passwordField);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        rightPanel.add(passwordPanel, gbc);


        // Etiqueta para crear una cuenta nueva
        createAccountLabel = new JLabel("¿Crear una cuenta nueva?");
        createAccountLabel.setForeground(Color.WHITE);
        createAccountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(createAccountLabel, gbc);
        
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(backgroundColor);
        panelInferior.add(Box.createVerticalStrut(20));
        panelInferior.add(Box.createVerticalStrut(20));

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
        mainPanel.add(topPanel,BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(panelInferior,BorderLayout.SOUTH);
        
        
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