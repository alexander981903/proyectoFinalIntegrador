/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.cHome;
import Controlador.cRegProducto;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.*;

/**
 *
 * @author EMMANUEL
 */
public class vistaRegProducto extends JFrame {
    private cRegProducto controlerRP;
    private cHome controladorH;
    
    private JTextField nombrePlatoField;
    private JTextField precioPersonalField;
    private JTextField precioFamiliarField;
    private JTextField stockField;
    
    private JButton registrarButton;
    private JButton modificarButton;
    private JButton cancelarButton;
    
    private boolean disponibilidad = false; // Por defecto en false
    
    // Color personalizado
    private final Color backgroundColor = new Color(0x0B, 0x11, 0x19);
    private final Color buttonBackgroundColor = new Color(0x20, 0x33, 0x4A);
    private final Color textColor = Color.WHITE;

    public vistaRegProducto() {
        setTitle("Vista Registro de Mis Productos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Crear panel para los campos
        JPanel camposPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        camposPanel.setBackground(backgroundColor); // Fondo del panel de campos

        // Etiquetas y campos de entrada
        JLabel nombreLabel = new JLabel("Nombre del Plato:");
        nombreLabel.setForeground(textColor);
        camposPanel.add(nombreLabel);
        nombrePlatoField = new JTextField();
        camposPanel.add(nombrePlatoField);
        
        JLabel precioPersonalLabel = new JLabel("Precio Personal:");
        precioPersonalLabel.setForeground(textColor);
        camposPanel.add(precioPersonalLabel);
        precioPersonalField = new JTextField();
        camposPanel.add(precioPersonalField);
        
        JLabel precioFamiliarLabel = new JLabel("Precio Familiar:");
        precioFamiliarLabel.setForeground(textColor);
        camposPanel.add(precioFamiliarLabel);
        precioFamiliarField = new JTextField();
        camposPanel.add(precioFamiliarField);
        
        JLabel stockLabel = new JLabel("Stock:");
        stockLabel.setForeground(textColor);
        camposPanel.add(stockLabel);
        stockField = new JTextField();
        camposPanel.add(stockField);

        // Crear panel para los botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.setBackground(backgroundColor); // Fondo del panel de botones
        
        registrarButton = new JButton("Registrar");
        modificarButton = new JButton("Modificar");
        cancelarButton = new JButton("Cancelar");

        // Configuración de colores de los botones
        configurarBotonColor(registrarButton);
        configurarBotonColor(modificarButton);
        configurarBotonColor(cancelarButton);

        botonesPanel.add(registrarButton);
        botonesPanel.add(modificarButton);
        botonesPanel.add(cancelarButton);

        // Agregar paneles al frame
        setLayout(new BorderLayout());
        add(camposPanel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
        
        registrarButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombrePlato =  nombrePlatoField.getText();
                    double precio_Personal = Double.parseDouble(precioPersonalField.getText());
                    double precio_Familiar = Double.parseDouble(precioFamiliarField.getText());
                    int stock = Integer.parseInt(stockField.getText());

                    controlerRP.agregarProducto(nombrePlato, precio_Personal, precio_Familiar, disponibilidad, stock);
                    controladorH.cargarProductos();
                    dispose();
                } catch (NumberFormatException ex) {
                    mostrarMensaje("Por favor, ingresa valores numéricos válidos para los precios y el stock.");
                }
            }
        });
    }

    private void configurarBotonColor(JButton boton) {
        boton.setBackground(buttonBackgroundColor);
        boton.setForeground(textColor);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
    }
    
    
    
    public void setControlerRP (cRegProducto controlerRP){
        this.controlerRP = controlerRP;
    }
    
    /**
     * Método para mostrar mensajes al usuario.
     * 
     * @param mensaje El mensaje a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    /**
     * Método para establecer el controlador de la vista principal.
     * Este método se utiliza para inyectar el controlador que maneja la vista principal de la aplicación, 
     * lo que permite realizar operaciones en otras partes de la interfaz, como actualizar la lista de Productos.
     * 
     * @param controladorH Objeto de tipo cHome que maneja la vista principal de la aplicación.
     */
    public void setControladorH(cHome controladorH) {
        this.controladorH = controladorH;
    }
}  
