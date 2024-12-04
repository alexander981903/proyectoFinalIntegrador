/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.cInfo;
import Modelo.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
/**
 * Clase que representa la vista para mostrar la información de pedidos en una tabla.
 * Permite visualizar los detalles de los pedidos.
 * 
 * @author EMMANUEL
 */
public class vistaInfoPedido extends JFrame {

    // Colores para el diseño de la interfaz
    private final Color backgroundColor = new Color(0x0B, 0x11, 0x19);
    private final Color textColor = Color.WHITE;
    private final Color tableHeaderColor = new Color(0x1E, 0x2A, 0x38);
    private final Color tableRowColor = new Color(0x25, 0x34, 0x45);
    private final Color buttonColor = new Color(0x42, 0x85, 0xF4);

    // Componentes de la interfaz
    private JTable tablePedidos;
    private DefaultTableModel tableModel;
    private JButton btnCerrar;
    private cInfo controladorInfo;
    private int id;

    /**
     * Constructor de la clase vistaInfoPedido.
     * Configura la ventana y los componentes gráficos.
     */
    public vistaInfoPedido(int id) {
        this.id = id;
        setTitle("Información de Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(backgroundColor);
        initComponents();
        setVisible(true);
    }

    /**
     * Inicializa los componentes gráficos, incluyendo la tabla de pedidos y el botón de cierre.
     */
    private void initComponents() {
        // Configuración del modelo de la tabla
        String[] columnNames = {"ID Pedido", "Nombre", "Tamaño", "Cantidad"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tablePedidos = new JTable(tableModel);
        tablePedidos.setBackground(tableRowColor);
        tablePedidos.setForeground(textColor);
        tablePedidos.setSelectionBackground(new Color(0x6B, 0x8E, 0xA8));
        tablePedidos.setSelectionForeground(textColor);
        tablePedidos.setGridColor(Color.GRAY);

        // Configuración de la cabecera de la tabla
        tablePedidos.getTableHeader().setBackground(tableHeaderColor);
        tablePedidos.getTableHeader().setForeground(textColor);
        tablePedidos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablePedidos.setRowHeight(30);

        // Agregar tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tablePedidos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Botón para cerrar la ventana
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(buttonColor);
        btnCerrar.setForeground(textColor);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());    

        // Panel para el botón
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(btnCerrar);

        // Configuración del layout principal
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        
    }
    
    /**
    * Método para mostrar una lista de productos en la tabla de la vistaInfoPedido.
    * Limpiar la tabla actual y luego agregar cada producto de la lista a las filas de la tabla.
    * @param productos Lista de productos que se desea mostrar en la tabla.
    */
   public void mostrarProductos(ArrayList<Producto> productos) {
       
       tableModel.setRowCount(0);
       
       for (Producto p : productos) {
           tableModel.addRow(new Object[]{
               p.getIdProducto(),
               p.getNombreProducto(),
               p.getTamaño(),
               p.getCantidad()
           });
       }
    }

    public int getId() {
        return id;
    }
   
    
    public void setControlador(cInfo controladorInfo) {
        this.controladorInfo = controladorInfo;
    }
}
