/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 * Clase vistaRegCliente.
 * Representa la ventana para el registro y modificación de clientes en la aplicación.
 * Esta ventana permite al usuario ingresar los datos del cliente y registrar un nuevo cliente 
 * o modificar los datos de un cliente existente.
 * 
 * @author EMMANUEL
 */
import Controlador.cHome;
import Controlador.cRegCliente;
import Modelo.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaRegCliente extends JFrame {
    
    private cRegCliente controladorRC;
    private cHome controladorH;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtDni;
    private JButton btnNuevo;
    private JButton btnModificar;
    
    private int idCliente ;
    
    // Colores personalizados
    private final Color backgroundColor = new Color(0x15, 0x22, 0x31); // Fondo color #152231
    private final Color buttonColor = new Color(0x2A, 0x44, 0x63); // Botón color #2a4463
    private final Color textColor = Color.WHITE; // Color del texto
    
    
    /**
     * Constructor de la clase vistaRegCliente.
     * Inicializa la interfaz gráfica de la ventana para registro y modificación de clientes.
     */
    public vistaRegCliente() {
        initComponents();
    }
    
    /**
     * Inicializa los componentes visuales de la ventana.
     * Configura los campos de texto, botones y el layout de la ventana.
     */
    public void initComponents(){
        // Configuración de la ventana
        setTitle("Registro de Cliente");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        

        // Configuración del layout principal
        setLayout(new BorderLayout(10, 10));

        // Crear un panel para los campos del cliente
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(5, 2, 10, 10));
        fieldsPanel.setBackground(backgroundColor); 

        // Crear etiquetas y campos de texto para cliente
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(textColor);
        txtNombre = new JTextField();
        setFieldSize(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setForeground(textColor);
        txtApellido = new JTextField();
        setFieldSize(txtApellido); 

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(textColor); 
        txtEmail = new JTextField();
        setFieldSize(txtEmail); 

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setForeground(textColor); 
        txtTelefono = new JTextField();
        setFieldSize(txtTelefono);
        
        JLabel lblDni = new JLabel("DNI: ");
        lblDni.setForeground(textColor);
        txtDni = new JTextField();
        setFieldSize(txtDni);

        // Agregar componentes al panel de campos
        fieldsPanel.add(lblNombre);
        fieldsPanel.add(txtNombre);
        fieldsPanel.add(lblApellido);
        fieldsPanel.add(txtApellido);
        fieldsPanel.add(lblEmail);
        fieldsPanel.add(txtEmail);
        fieldsPanel.add(lblTelefono);
        fieldsPanel.add(txtTelefono);
        fieldsPanel.add(lblDni);
        fieldsPanel.add(txtDni);

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
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

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(buttonColor);
        btnCancelar.setForeground(textColor); // Color del texto

        // Agregar botones al panel
        buttonPanel.add(btnNuevo);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnCancelar);

        // Agregar paneles a la ventana
        add(fieldsPanel, BorderLayout.CENTER); // Panel de campos en el centro
        add(buttonPanel, BorderLayout.SOUTH); // Panel de botones en la parte inferior
        
        // Añadir ActionListener al botón "Nuevo" para agragar un nuevo cliente
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreCliente = txtNombre.getText();
                String apellidoCliente = txtApellido.getText();
                String emailCliente = txtEmail.getText();
                String telefonoCliente = txtTelefono.getText();
                String Dni = txtDni.getText();

                controladorRC.agregarCliente(nombreCliente, apellidoCliente, emailCliente, telefonoCliente,Dni);
                controladorH.cargarClientes();
                dispose();

            }
        });
        
        // Añadir ActionListener al botón "Modificar" para actualizar un cliente seleccionado
        btnModificar.addActionListener(new ActionListener() {
            private boolean camposHabilitados = false; // Bandera para controlar el estado de los campos

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!camposHabilitados) {
                    int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Está seguro de que desea modificar este cliente?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );

                    if (opcion == JOptionPane.YES_OPTION) {
                        activarCampos(true); // Habilitar campos para edición
                        camposHabilitados = true; // Cambiar el estado de la bandera
                        btnModificar.setText("Guardar Cambios"); // Cambiar el texto del botón
                    } else {
                        JOptionPane.showMessageDialog(null, "Modificación cancelada.");
                    }
                } else {
                    // Obtener los datos actualizados
                    String nombreCliente = txtNombre.getText();
                    String apellidoCliente = txtApellido.getText();
                    String emailCliente = txtEmail.getText();
                    String telefonoCliente = txtTelefono.getText();
                    String Dni = txtDni.getText();

                    // Validar los campos antes de actualizar
                    if (nombreCliente.isEmpty() || apellidoCliente.isEmpty() || emailCliente.isEmpty() || telefonoCliente.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    controladorRC.actualizarCliente(idCliente, nombreCliente, apellidoCliente, emailCliente, telefonoCliente, Dni);
                    JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente.");
                    dispose();
                    controladorH.cargarClientes();
                }
            }
        });



        // Añadir ActionListener al botón "Cancelar" para cerrar la ventana
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Visualizar la ventana
        getContentPane().setBackground(backgroundColor);
        setVisible(true);
    }

    /**
     * Establece el tamaño de los campos de texto.
     * Este método configura el tamaño preferido, máximo y mínimo de los campos de texto
     * para asegurarse de que se ajusten adecuadamente al diseño de la interfaz de usuario.
     * 
     * @param textField El campo de texto al que se le establecerá el tamaño.
     */
    private void setFieldSize(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 20));
        textField.setMaximumSize(new Dimension(200, 20)); 
        textField.setMinimumSize(new Dimension(200, 20));
    }

    /**
     * Establece el controlador para gestionar las acciones relacionadas con el registro de clientes.
     * 
     * @param controladorC El controlador que gestiona las acciones relacionadas con el registro de clientes.
     */
    public void setControladorC(cRegCliente controladorC) {
        this.controladorRC = controladorC;
    }

    /**
     * Establece el controlador para gestionar la vista principal de la aplicación.
     * 
     * @param controladorH El controlador que gestiona la vista principal de la aplicación (carga de clientes, entre otros).
     */
    public void setControladorH(cHome controladorH) {
        this.controladorH = controladorH;
    }

    /**
     * Muestra un mensaje al usuario en una ventana emergente.
     * Este método puede ser utilizado para mostrar alertas, errores o información al usuario.
     * 
     * @param mensaje El mensaje que se mostrará en la ventana emergente.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Establece los datos de un cliente en los campos de texto de la ventana.
     * Este método es útil cuando se desea modificar los datos de un cliente seleccionado previamente.
     * 
     * @param cliente El objeto Cliente con los datos a mostrar en los campos.
     */
    public void datosCliente(Cliente cliente) {
        // Establecer los valores de los campos con los datos del cliente
        idCliente = cliente.getIdCliente();
        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        txtEmail.setText(cliente.getEmail());
        txtTelefono.setText(cliente.getTelefono());
        txtDni.setText(cliente.getDni());
    }

    /**
     * Obtiene el botón "Registrar" de la vista.
     * 
     * @return El botón "Registrar" de la vista.
     */
    public JButton getBtnNuevo() {
        return btnNuevo;
    }

    /**
     * Obtiene el botón "Modificar" de la vista.
     * 
     * @return El botón "Modificar" de la vista.
     */
    public JButton getBtnModificar() {
        return btnModificar;
    }
    
    /**
     * Metodo para que los campos no sean editables por error a la hora de modificar un Cliente
     * 
     * @param habilitar valor falso o verdaro para habilitar la edicion en los campos
     */
    public void activarCampos(boolean habilitar){
        txtNombre.setEditable(habilitar);
        txtApellido.setEditable(habilitar);
        txtEmail.setEditable(habilitar);
        txtTelefono.setEditable(habilitar);
        txtDni.setEditable(habilitar);
    }
    
    
}