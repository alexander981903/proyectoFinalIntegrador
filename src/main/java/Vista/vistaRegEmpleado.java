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
        setSize(400, 200);
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
        // Crear un panel para los campos del empleado
        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 filas, 2 columnas
        fieldsPanel.setBackground(backgroundColor); // Establecer color de fondo

        // Crear etiquetas y campos de texto para empleado
        lblNombreEmp = new JLabel("Nombre:");
        lblNombreEmp.setForeground(textColor); // Color del texto
        txtNombreEmp = new JTextField(20);

        lblCargo = new JLabel("Cargo:");
        lblCargo.setForeground(textColor); // Color del texto
        cmbCargo = new JComboBox<>(new String[] {"Administrador", "Mesero", "Cajero", "Chef Ejecutivo"}); // ComboBox de cargos

        lblTurno = new JLabel("Turno:");
        lblTurno.setForeground(textColor); // Color del texto
        txtTurno = new JTextField(20);

        // Agregar componentes al panel de campos
        fieldsPanel.add(lblNombreEmp);
        fieldsPanel.add(txtNombreEmp);
        fieldsPanel.add(lblCargo);
        fieldsPanel.add(cmbCargo);
        fieldsPanel.add(lblTurno);
        fieldsPanel.add(txtTurno);

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

        // Crear un panel principal para contener los campos y botones
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar panel principal a la ventana
        add(mainPanel);

        // Visualizar la ventana
        getContentPane().setBackground(backgroundColor); // Establecer color de fondo de la ventana
        setVisible(true);

        // Añadir ActionListener al botón "Registrar" para guardar el nuevo empleado
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEmp = txtNombreEmp.getText();
                String cargoEmp = (String) cmbCargo.getSelectedItem();
                String turnoEmp = txtTurno.getText();

                controladorE.agregarEmpleado(nombreEmp, cargoEmp, turnoEmp);
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
