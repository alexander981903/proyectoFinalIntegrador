/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author EMMANUEL
 */
import Controlador.cEmpleado;
import Controlador.cUsuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaRegEmpleado extends JFrame {
    
    private cEmpleado controlador;
    private cUsuario controladorUser;
    
    private JTextField txtNombreEmp;
    private JTextField txtCargo;
    private JTextField txtTurno;
    private JTextField txtLogin;
    private JTextField txtClave;
    private JTextField txtTipoUsuario;
    
    private JPanel mainPanel;
    private JPanel fieldsPanel;
    private JPanel userPanel;
    private JPanel buttonPanel;
    
    private JLabel lblNombreEmp;
    private JLabel lblCargo;
    private JLabel lblTurno;
    private JLabel lblLogin;
    private JLabel lblClave;
    private JLabel lblTipoUsuario;
    
    
    private JButton btnNuevo;
    private JButton btnModificar;
    private JButton btnCancelar;
    
    
    
    // Colores personalizados
    private final Color backgroundColor = new Color(0x15, 0x22, 0x31); // Fondo color #152231
    private final Color buttonColor = new Color(0x2A, 0x44, 0x63); // Botón color #2a4463
    private final Color textColor = Color.WHITE; // Color del texto

    public vistaRegEmpleado() {
        // Configuración de la ventana
        setTitle("Registro de Empleado");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Configuración del layout principal
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }
    
    private void initComponents(){
        // Crear un panel para los campos del empleado
        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 4 filas, 2 columnas
        fieldsPanel.setBackground(backgroundColor); // Establecer color de fondo

        // Crear etiquetas y campos de texto para empleado
        lblNombreEmp = new JLabel("Nombre:");
        lblNombreEmp.setForeground(textColor); // Color del texto
        txtNombreEmp = new JTextField();
        setFieldSize(txtNombreEmp); // Ajustar tamaño

        lblCargo = new JLabel("Cargo:");
        lblCargo.setForeground(textColor); // Color del texto
        txtCargo = new JTextField();
        setFieldSize(txtCargo); // Ajustar tamaño

        lblTurno = new JLabel("Turno:");
        lblTurno.setForeground(textColor); // Color del texto
        txtTurno = new JTextField();
        setFieldSize(txtTurno); // Ajustar tamaño

        // Agregar componentes al panel de campos
        fieldsPanel.add(lblNombreEmp);
        fieldsPanel.add(txtNombreEmp);
        fieldsPanel.add(lblCargo);
        fieldsPanel.add(txtCargo);
        fieldsPanel.add(lblTurno);
        fieldsPanel.add(txtTurno);

        // Crear un panel para los campos del usuario
        userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 filas, 2 columnas (sin rol)
        userPanel.setBackground(backgroundColor); // Establecer color de fondo

        // Crear etiquetas y campos de texto para usuario
        lblLogin = new JLabel("Login:");
        lblLogin.setForeground(textColor); // Color del texto
        txtLogin = new JTextField();
        setFieldSize(txtLogin); // Ajustar tamaño

        lblClave = new JLabel("Clave:");
        lblClave.setForeground(textColor); // Color del texto
        txtClave = new JTextField();
        setFieldSize(txtClave); // Ajustar tamaño

        lblTipoUsuario = new JLabel("Tipo Usuario:");
        lblTipoUsuario.setForeground(textColor); // Color del texto
        
        // Crear campo de texto para "Tipo Usuario" y configurarlo como no editable
        txtTipoUsuario = new JTextField("Empleado");
        txtTipoUsuario.setEditable(false);
        txtTipoUsuario.setBackground(Color.LIGHT_GRAY); // Cambiar el color de fondo para indicar que no es editable
        setFieldSize(txtTipoUsuario); // Ajustar tamaño

        // Agregar componentes al panel de usuario
        userPanel.add(lblLogin);
        userPanel.add(txtLogin);
        userPanel.add(lblClave);
        userPanel.add(txtClave);
        userPanel.add(lblTipoUsuario);
        userPanel.add(txtTipoUsuario);

        // Crear un panel para los botones
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Alinear los botones en el centro
        buttonPanel.setBackground(backgroundColor); // Establecer color de fondo
        

        // Crear botones
        btnNuevo = new JButton("Registrar");
        btnNuevo.setBackground(buttonColor);
        btnNuevo.setForeground(textColor); // Color del texto

        btnModificar = new JButton("Modificar");
        btnModificar.setBackground(buttonColor);
        btnModificar.setForeground(textColor); // Color del texto
        btnModificar.setVisible(false);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(buttonColor);
        btnCancelar.setForeground(textColor); // Color del texto

        // Agregar botones al panel
        buttonPanel.add(btnNuevo);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnCancelar);

        // Crear un panel principal para contener ambos paneles
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2)); // 1 fila, 2 columnas
        mainPanel.add(fieldsPanel);
        mainPanel.add(userPanel);

        // Agregar paneles a la ventana
        add(mainPanel, BorderLayout.CENTER); // Panel principal en el centro
        add(buttonPanel, BorderLayout.SOUTH); // Panel de botones en la parte inferior
        
        // Visualizar la ventana
        getContentPane().setBackground(backgroundColor); // Establecer color de fondo de la ventana
        setVisible(true);
        
        // Añadir ActionListener al botón "Registrar" para guardar el nuevo empleado
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String nombreEmp = txtNombreEmp.getText();
               String cargoEmp = txtCargo.getText();
               String turnoEmp = txtTurno.getText();
               
               controlador.agregarEmpleado(nombreEmp, cargoEmp, turnoEmp);
               dispose();
            }
        });

        // Añadir ActionListener al botón "Cancelar" para cerrar la ventana
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });
    }

    // Método para establecer el tamaño de los campos
    private void setFieldSize(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 20)); // Ancho 200, alto 20
        textField.setMaximumSize(new Dimension(200, 20)); // Establecer tamaño máximo
        textField.setMinimumSize(new Dimension(200, 20)); // Establecer tamaño mínimo
    }
    
    /**
   * Método para establecer el controlador.
   * @param controlador Objeto cEmpleado.
   */
    public void setControlador(cEmpleado controlador) {
        this.controlador = controlador;
    }
    
    public void setControladorUser(cUsuario controladorUser) {
        this.controladorUser = controladorUser;
    }
    
    
    
    public void mostrarMensaje(String mensaje) {
    JOptionPane.showMessageDialog(this, mensaje);
    }   
}