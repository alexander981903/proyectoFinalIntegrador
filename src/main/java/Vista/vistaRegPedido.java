package Vista;

import Controlador.cRegPedido;
import Modelo.Producto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.HashMap;

public class vistaRegPedido extends JFrame {

    private JTextField txtId;
    private JTextField txtNombrePlato;
    private JComboBox<String> comboBoxTamaño;
    private JTextField txtCantidad;
    private JButton btnMas;
    private JButton btnMenos;
    private JTextField txtPrecio;
    private JButton btnListo;
    private cRegPedido controladorRegPed;
    private int idPlato;
    private vistaRegReserva vistaRegR;

    // Colores personalizados
    private final Color backgroundColor = new Color(0x0B, 0x11, 0x19);
    private final Color buttonBackgroundColor = new Color(0x20, 0x33, 0x4A);
    private final Color textColor = Color.WHITE;

    public vistaRegPedido(vistaRegReserva vistaRegR) {
        // Configuración básica del JFrame
        setTitle("Vista Registro Pedido");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        this.vistaRegR = vistaRegR;
        // Llamar al método de inicialización de componentes
        initComponents();
        
        // Hacer visible el frame
        setVisible(true);
    }

    // Método para inicializar y configurar los componentes
    private void initComponents() {
        // Configuración del fondo del contenedor
        getContentPane().setBackground(backgroundColor);

        // Inicialización de componentes
        txtId = new JTextField(10);
        txtId.setEditable(false);
        txtNombrePlato = new JTextField(10);
        txtNombrePlato.setEditable(false);
        comboBoxTamaño = new JComboBox<>(new String[]{"Personal", "Fuente"});
        
        // Inicialización del campo y botones de cantidad
        txtCantidad = new JTextField("1", 5); // Cantidad inicial en 1
        txtCantidad.setHorizontalAlignment(JTextField.CENTER);
        txtCantidad.setEditable(true);
        btnMas = new JButton("+");
        btnMenos = new JButton("-");
        
        txtPrecio = new JTextField(10);
        txtPrecio.setEditable(false); // Campo de precio no editable
        btnListo = new JButton("Listo");

        // Aplicación de colores personalizados a los componentes
        btnMas.setBackground(buttonBackgroundColor);
        btnMas.setForeground(textColor);
        btnMenos.setBackground(buttonBackgroundColor);
        btnMenos.setForeground(textColor);
        btnListo.setBackground(buttonBackgroundColor);
        btnListo.setForeground(textColor);

        // Configuración del layout para agregar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta y campo ID
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblId = new JLabel("ID:");
        lblId.setForeground(textColor);
        add(lblId, gbc);
        gbc.gridx = 1;
        add(txtId, gbc);

        // Etiqueta y campo Nombre de Plato
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblNombrePlato = new JLabel("Nombre de Plato:");
        lblNombrePlato.setForeground(textColor);
        add(lblNombrePlato, gbc);
        gbc.gridx = 1;
        add(txtNombrePlato, gbc);

        // Etiqueta y ComboBox de Tamaño de Plato
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblTamaño = new JLabel("Tamaño del Plato:");
        lblTamaño.setForeground(textColor);
        add(lblTamaño, gbc);
        gbc.gridx = 1;
        add(comboBoxTamaño, gbc);

        // Etiqueta y panel de Cantidad con botones
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setForeground(textColor);
        add(lblCantidad, gbc);
        
        gbc.gridx = 1;
        JPanel panelCantidad = new JPanel(new BorderLayout());
        panelCantidad.setBackground(backgroundColor);
        panelCantidad.add(btnMas, BorderLayout.WEST);
        panelCantidad.add(txtCantidad, BorderLayout.CENTER);
        panelCantidad.add(btnMenos, BorderLayout.EAST);
        add(panelCantidad, gbc);

        // Etiqueta y campo Precio
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setForeground(textColor);
        add(lblPrecio, gbc);
        gbc.gridx = 1;
        add(txtPrecio, gbc);

        // Botón Listo
        gbc.gridx = 1; gbc.gridy = 5;
        add(btnListo, gbc);
        
        
        btnMas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Leer el valor actual de txtCantidad, convertirlo a un número
                int cantidad = Integer.parseInt(txtCantidad.getText());

                // Incrementar la cantidad
                cantidad++;

                // Actualizar el campo de texto con el nuevo valor
                txtCantidad.setText(String.valueOf(cantidad));
            }
        });
        
        btnMenos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Leer el valor actual de txtCantidad y convertirlo a un número
                    int cantidad = Integer.parseInt(txtCantidad.getText());

                    // Disminuir la cantidad solo si es mayor que cero
                    if (cantidad > 0) {
                        cantidad--;
                    }

                    // Actualizar el campo de texto con el nuevo valor
                    txtCantidad.setText(String.valueOf(cantidad));
                } catch (NumberFormatException ex) {
                    // En caso de que el campo de texto no contenga un número, restablecer a cero
                    txtCantidad.setText("0");
                }
            }
        });
        
        btnListo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos de los campos
                int id = Integer.parseInt(txtId.getText()); // ID
                String nombrePlato = txtNombrePlato.getText(); // Nombre del plato
                String tamaño = (String) comboBoxTamaño.getSelectedItem(); // Tamaño del plato
                int cantidad = Integer.parseInt(txtCantidad.getText()); // Cantidad
                double precio = Double.parseDouble(txtPrecio.getText()); // Precio
                
                HashMap<String,Object> producto = new HashMap<>();
                producto.put("id", id);
                producto.put("nombrePlato", nombrePlato);
                producto.put("tamaño",tamaño);
                producto.put("cantidad", cantidad);
                producto.put("precio", precio);
                
                if(vistaRegR != null && vistaRegR.isVisible()){
                    vistaRegR.agregarProductoAlaTabla(producto);
                }
                dispose();
            }
        });

    }

    public void setControladorRegPed(cRegPedido controladorRegPed) {
        this.controladorRegPed = controladorRegPed;
    }

    public void setIdPlato(int idPlato) {
        this.idPlato = idPlato;
    }

    public int getIdPlato() {
        return idPlato;
    }
    
    
    
    public void llenarCamposPlato(Producto producto){

        // Llenar los campos de la vista si el producto fue encontrado
        if (producto != null) {
            txtId.setText(String.valueOf(idPlato));
            txtNombrePlato.setText(producto.getNombreProducto());
            txtPrecio.setText(String.valueOf(producto.getPrecioPersonal()));
            // Establecer el precio inicial basado en el tamaño "Personal"
            txtPrecio.setText(String.valueOf(producto.getPrecioPersonal()));
            // Añadir un ItemListener para actualizar el precio cuando cambie el tamaño
            comboBoxTamaño.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String tamañoSeleccionado = (String) comboBoxTamaño.getSelectedItem();
                    if ("Fuente".equals(tamañoSeleccionado)) {
                        txtPrecio.setText(String.valueOf(producto.getPrecioFamiliar()));
                    } else if ("Personal".equals(tamañoSeleccionado)) {
                        txtPrecio.setText(String.valueOf(producto.getPrecioPersonal()));
                    }
                }
            });
            
        } else {
            System.err.println("Producto no encontrado con ID: " + idPlato);
        }
    }
    
    
}
