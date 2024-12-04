/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 * Clase vistaRegEmpleado.
 * Representa la ventana para el registro y modificación de empleados en la aplicación.
 * Esta ventana permite al usuario ingresar los datos del empleado y registrar un nuevo empleado 
 * o modificar los datos de un empleado existente.
 * 
 * @author EMMANUEL
 */

import Controlador.cRegEmpleado;
import Controlador.cHome;
import Modelo.Empleado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaRegEmpleado extends JFrame {

    private cRegEmpleado controladorE;
    private cHome controladorH;

    private JTextField txtNombreEmp;
    private JComboBox<String> cmbCargo;
    private JTextField txtTurno;

    private JPanel mainPanel;
    private JPanel fieldsPanel;
    private JPanel buttonPanel;

    private JLabel lblNombreEmp;
    private JLabel lblCargo;
    private JLabel lblTurno;

    private JButton btnNuevo;
    private JButton btnModificar;
    private JButton btnCancelar;
    
    private int idEmpleado;

    // Colores personalizados
    private final Color backgroundColor = new Color(0x15, 0x22, 0x31); // Fondo color #152231
    private final Color buttonColor = new Color(0x2A, 0x44, 0x63); // Botón color #2a4463
    private final Color textColor = Color.WHITE; // Color del texto
    
    /**
     * Constructor de la clase vistaRegEmpleado.
     * Inicializa la interfaz gráfica de la ventana para registro y modificación de empleados.
     */
    public vistaRegEmpleado() {
        // Configuración de la ventana
        setTitle("Registro de Empleado");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Configuración del layout principal
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }
    
    /**
     * Inicializa los componentes visuales de la ventana.
     * Configura los campos de texto, combo box, botones y el layout de la ventana.
     */
    private void initComponents() {
        
        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(5, 2, 10, 10));
        fieldsPanel.setBackground(backgroundColor);

        // Campos para el empleado
        lblNombreEmp = new JLabel("Nombre:");
        lblNombreEmp.setForeground(textColor);
        txtNombreEmp = new JTextField(20);

        lblCargo = new JLabel("Cargo:");
        lblCargo.setForeground(textColor);
        cmbCargo = new JComboBox<>(new String[] {"Administrador", "Mesero", "Cajero", "Chef Ejecutivo"});

        lblTurno = new JLabel("Turno:");
        lblTurno.setForeground(textColor);
        txtTurno = new JTextField(20);

        // Campos para el usuario
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setForeground(textColor);
        JTextField txtLogin = new JTextField(20);

        JLabel lblClave = new JLabel("Contraseña:");
        lblClave.setForeground(textColor);
        JPasswordField txtClave = new JPasswordField(20);

        // Agregar componentes al panel de campos
        fieldsPanel.add(lblNombreEmp);
        fieldsPanel.add(txtNombreEmp);
        fieldsPanel.add(lblCargo);
        fieldsPanel.add(cmbCargo);
        fieldsPanel.add(lblTurno);
        fieldsPanel.add(txtTurno);
        fieldsPanel.add(lblLogin);
        fieldsPanel.add(txtLogin);
        fieldsPanel.add(lblClave);
        fieldsPanel.add(txtClave);

        // Panel para los botones
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(backgroundColor);

        // Botones
        btnNuevo = new JButton("Registrar");
        btnNuevo.setBackground(buttonColor);
        btnNuevo.setForeground(textColor);

        btnModificar = new JButton("Modificar");
        btnModificar.setBackground(buttonColor);
        btnModificar.setForeground(textColor);
        btnModificar.setVisible(false);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(buttonColor);
        btnCancelar.setForeground(textColor);

        // Agregar botones al panel
        buttonPanel.add(btnNuevo);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnCancelar);

        // Panel principal para contener campos y botones
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        getContentPane().setBackground(backgroundColor);
        setVisible(true);


        // Añadir ActionListener al botón "Registrar" para guardar el nuevo empleado
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEmp = txtNombreEmp.getText();
                String cargoEmp = (String) cmbCargo.getSelectedItem();
                String turnoEmp = txtTurno.getText();
                String login = txtLogin.getText();
                String passw = new String(txtClave.getPassword());
                String rol = "Empleado";
                
                controladorE.agregarEmpleado(nombreEmp, cargoEmp, turnoEmp, login, passw,rol);
                controladorH.cargarEmpleados();
                dispose();
            }
        });
        
        // Añadir ActionListener al botón "Modificar" para actualizar el empleado seleccionado
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEmp = txtNombreEmp.getText();
                String cargoEmp = (String) cmbCargo.getSelectedItem();
                String turnoEmp = txtTurno.getText();

                controladorE.actualizarEmpleado(idEmpleado,nombreEmp, cargoEmp, turnoEmp);
                controladorH.cargarEmpleados();
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

    /**
     * Método para establecer el controlador de empleado.
     * Este método se utiliza para inyectar el controlador que gestionará las acciones relacionadas con el registro y modificación de empleados.
     * 
     * @param controladorE Objeto de tipo cRegEmpleado que maneja la lógica de negocio del registro de empleados.
     */
    public void setControladorE(cRegEmpleado controladorE) {
        this.controladorE = controladorE;
    }

    /**
     * Método para establecer el controlador de la vista principal.
     * Este método se utiliza para inyectar el controlador que maneja la vista principal de la aplicación, 
     * lo que permite realizar operaciones en otras partes de la interfaz, como actualizar la lista de empleados.
     * 
     * @param controladorH Objeto de tipo cHome que maneja la vista principal de la aplicación.
     */
    public void setControladorH(cHome controladorH) {
        this.controladorH = controladorH;
    }

    /**
     * Método para mostrar un mensaje emergente en la ventana.
     * Este método se utiliza para mostrar mensajes informativos, de advertencia o de error en un cuadro de diálogo emergente.
     * 
     * @param mensaje El mensaje que se mostrará al usuario.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Método que obtiene el botón "Nuevo" de la vista.
     * Este botón se utiliza para registrar un nuevo empleado.
     * 
     * @return El botón "Nuevo" de la vista para ser utilizado en otros componentes o controladores.
     */
    public JButton getBtnNuevo() {
        return btnNuevo;
    }

    /**
     * Método que obtiene el botón "Modificar" de la vista.
     * Este botón se utiliza para modificar los datos de un empleado existente.
     * 
     * @return El botón "Modificar" de la vista para ser utilizado en otros componentes o controladores.
     */
    public JButton getBtnModificar() {
        return btnModificar;
    }

    /**
     * Método para llenar los campos de la vista con los datos de un empleado.
     * Este método se utiliza cuando se desea cargar los datos de un empleado seleccionado previamente 
     * para modificarlos en la interfaz.
     * 
     * @param emp Objeto de tipo Empleado que contiene los datos del empleado a cargar en los campos de texto.
     */
    public void datosEmpleado(Empleado emp) {
        // Establecer los valores de los campos con los datos del empleado
        idEmpleado = emp.getIdEmpleado();
        txtNombreEmp.setText(emp.getNombreEmp());
        cmbCargo.setSelectedItem(emp.getCargo());
        txtTurno.setText(emp.getTurno());
    }

}
