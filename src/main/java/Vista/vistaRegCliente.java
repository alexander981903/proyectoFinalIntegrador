/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author EMMANUEL
 */
import Controlador.cCliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaRegCliente extends JFrame {
    
    private cCliente controladorC;
    
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;

    public vistaRegCliente() {
        // Configuración de la ventana
        setTitle("Registro de Cliente");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Colores personalizados
        Color backgroundColor = new Color(0x15, 0x22, 0x31); // Fondo color #152231
        Color buttonColor = new Color(0x2A, 0x44, 0x63); // Botón color #2a4463
        Color textColor = Color.WHITE; // Color del texto

        // Configuración del layout principal
        setLayout(new BorderLayout(10, 10));

        // Crear un panel para los campos del cliente
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 4 filas, 2 columnas
        fieldsPanel.setBackground(backgroundColor); // Establecer color de fondo

        // Crear etiquetas y campos de texto para cliente
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(textColor); // Color del texto
        txtNombre = new JTextField();
        setFieldSize(txtNombre); // Ajustar tamaño

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setForeground(textColor); // Color del texto
        txtApellido = new JTextField();
        setFieldSize(txtApellido); // Ajustar tamaño

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(textColor); // Color del texto
        txtEmail = new JTextField();
        setFieldSize(txtEmail); // Ajustar tamaño

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setForeground(textColor); // Color del texto
        txtTelefono = new JTextField();
        setFieldSize(txtTelefono); // Ajustar tamaño

        // Agregar componentes al panel de campos
        fieldsPanel.add(lblNombre);
        fieldsPanel.add(txtNombre);
        fieldsPanel.add(lblApellido);
        fieldsPanel.add(txtApellido);
        fieldsPanel.add(lblEmail);
        fieldsPanel.add(txtEmail);
        fieldsPanel.add(lblTelefono);
        fieldsPanel.add(txtTelefono);

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Alinear los botones en el centro
        buttonPanel.setBackground(backgroundColor); // Establecer color de fondo

        // Crear botones
        JButton btnNuevo = new JButton("Registrar");
        btnNuevo.setBackground(buttonColor);
        btnNuevo.setForeground(textColor); // Color del texto

        JButton btnModificar = new JButton("Modificar");
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
        
        // Añadir ActionListener al botón "Nuevo Empleado"
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreCliente = txtNombre.getText();
                String apellidoCliente = txtApellido.getText();
                String emailCliente = txtEmail.getText();
                String telefonoCliente = txtTelefono.getText();

                controladorC.agregarCliente(nombreCliente, apellidoCliente, emailCliente, telefonoCliente);
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

        // Visualizar la ventana
        getContentPane().setBackground(backgroundColor); // Establecer color de fondo de la ventana
        setVisible(true);
    }

    // Método para establecer el tamaño de los campos
    private void setFieldSize(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 20)); // Ancho 200, alto 20
        textField.setMaximumSize(new Dimension(200, 20)); // Establecer tamaño máximo
        textField.setMinimumSize(new Dimension(200, 20)); // Establecer tamaño mínimo
    }
    
    /**
    * Método para establecer el controlador.
    * @param controladorC Objeto cCliente.
    */
    public void setControladorC(cCliente controladorC) {
        this.controladorC = controladorC;
    }

    /**
   * Método para mostrar mensajes al usuario.
   * @param mensaje Mensaje a mostrar.
   */
    public void mostrarMensaje(String mensaje) {
      JOptionPane.showMessageDialog(this, mensaje);
    }
}