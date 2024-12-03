/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.cHome;
import Controlador.cRegPlano;
import Modelo.Mesa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Ventana gráfica para crear un plano en el sistema.
 * Permite ingresar un nombre para el plano, una descripción y la cantidad de mesas asociadas.
 * También incluye botones para guardar o cancelar la acción.
 * 
 * @author EMMANUEL
 */
public class vistaPlano extends JFrame {
    
    private cRegPlano controladorPlano;
    private cHome controladorH;
    // Color personalizado
    private final Color backgroundColor = new Color(0x0B, 0x11, 0x19);  // Color de fondo general
    private final Color buttonBackgroundColor = new Color(0x20, 0x33, 0x4A);  // Color de fondo de los botones
    private final Color textColor = Color.WHITE;  // Color del texto

    // Componentes de la interfaz
    private JTextField txtNombrePlano;
    private JTextArea txtDescripcion;
    private JSpinner spnCantidadDeMesas; 
    private JButton btnGuardar; 
    private JButton btnCancelar;
    private JButton btnModificar;
    private vistaHome vista;

    /**
     * Constructor de la clase.
     * Configura la ventana principal y llama al método {@code initComponents} para inicializar los componentes.
     */
    public vistaPlano(vistaHome vista) {
        setTitle("Crear Plano");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        initComponents();
        btnModificar.setVisible(false);
        btnGuardar.setVisible(false);
    }

    /**
     * Inicializa los componentes gráficos de la ventana.
     * Se utilizan paneles y layouts para organizar la interfaz.
     */
    private void initComponents() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(backgroundColor);
        panelPrincipal.setLayout(new BorderLayout());
        add(panelPrincipal);

        // Panel de entrada de datos
        JPanel panelDatos = new JPanel();
        panelDatos.setBackground(backgroundColor);
        panelDatos.setLayout(new GridLayout(3, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.add(panelDatos, BorderLayout.CENTER);

        // Nombre del plano
        JLabel lblNombrePlano = new JLabel("Nombre del Plano:");
        lblNombrePlano.setForeground(textColor);
        panelDatos.add(lblNombrePlano);

        txtNombrePlano = new JTextField();
        panelDatos.add(txtNombrePlano);

        // Descripción del plano
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setForeground(textColor);
        panelDatos.add(lblDescripcion);

        txtDescripcion = new JTextArea(3, 20);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        panelDatos.add(scrollDescripcion);

        // Cantidad de mesas
        JLabel lblCantidadMesas = new JLabel("Cantidad de Mesas:");
        lblCantidadMesas.setForeground(textColor);
        panelDatos.add(lblCantidadMesas);

        spnCantidadDeMesas = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1)); // Min 0, Max 10, Increment 1
        panelDatos.add(spnCantidadDeMesas);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(backgroundColor);
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(buttonBackgroundColor);
        btnGuardar.setForeground(textColor);
        btnGuardar.setFocusPainted(false);
        panelBotones.add(btnGuardar);
        
        btnModificar = new JButton("Modificar");
        btnModificar.setBackground(buttonBackgroundColor);
        btnModificar.setForeground(textColor);
        btnModificar.setFocusPainted(false);
        panelBotones.add(btnModificar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(buttonBackgroundColor);
        btnCancelar.setForeground(textColor);
        btnCancelar.setFocusPainted(false);
        panelBotones.add(btnCancelar);
        
        // Agregar el ActionListener al botón guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al controlador para agregar el nuevo plano
                String nombrePlano = txtNombrePlano.getText();
                String descripcion = txtDescripcion.getText();
                int cantidadDeMesas;
                cantidadDeMesas = (Integer)spnCantidadDeMesas.getValue();

                // Verificamos que todos los campos obligatorios estén llenos
                if (nombrePlano.isEmpty()) {
                    mostrarMensaje("EL nombre del Plano es obligatorio");
                    return;
                }
                controladorPlano.agregarPlano(nombrePlano, descripcion, cantidadDeMesas);
                agregarMesasAlPlano(cantidadDeMesas);
                controladorH.mostrarMesaPlano();
                dispose();
            }
        });
        
        // Agregar el ActionListener al botón guardar
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al controlador para agregar el nuevo plano
                int idPlano = controladorH.getIdPlanoMasReciente();
                String nombrePlano = txtNombrePlano.getText();
                String descripcion = txtDescripcion.getText();
                int cantidadDeMesas = (Integer)spnCantidadDeMesas.getValue();

                // Verificamos que todos los campos obligatorios estén llenos
                if (nombrePlano.isEmpty()) {
                    mostrarMensaje("EL nombre del Plano es obligatorio");
                    return;
                }
                controladorPlano.actualizarPlano(idPlano,nombrePlano,descripcion,cantidadDeMesas);
                
                agregarMesasAlPlano(cantidadDeMesas);
                controladorH.mostrarMesaPlano();
                dispose();
            }
        });
        
        // Agregar el ActionListener al botón guardar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        
    }
    
    private void agregarMesasAlPlano(int cantidadDeMesas) {
        ArrayList<Mesa> mesasExistentes = controladorH.obtenerTodasLasMesas();
        // Verificar que la cantidad de mesas es mayor a 0
        if (cantidadDeMesas <= 0) {
            mostrarMensaje("La cantidad de mesas debe ser mayor a 0.");
            return;
        }
        
        // Crear una lista para almacenar las mesas que serán asignadas
        ArrayList<Integer> mesasParaAsignar = new ArrayList<>();
       
        if(mesasExistentes.size() > cantidadDeMesas){
            // Si ya existen mesas, solo tomamos la cantidad necesaria
            int mesasAjustadas = Math.min(mesasExistentes.size(), cantidadDeMesas);

            // Crear las mesas necesarias
            for (int i = 1; i <= mesasAjustadas; i++) {

                String capacidadInput = JOptionPane.showInputDialog("Ingrese la capacidad para la Mesa " + i + ":");
                if (capacidadInput == null || capacidadInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar una capacidad válida.");
                    i--; // Retrocede el índice para volver a pedir la capacidad para esta mesa
                    continue;
                }

                int capacidad;
                try {
                    capacidad = Integer.parseInt(capacidadInput);
                    if (capacidad <= 0) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un número mayor que 0.");
                        i--; // Retrocede el índice para volver a pedir la capacidad para esta mesa
                        continue;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.");
                    i--; // Retrocede el índice para volver a pedir la capacidad para esta mesa
                    continue;
                }
                if (i <= mesasExistentes.size()) {
                    // Si la mesa ya existe, actualizar sus datos
                    Mesa mesaExistente = mesasExistentes.get(i - 1);
                    mesaExistente.setNumeroMesa(i);
                    mesaExistente.setCapacidad(capacidad);
                    controladorH.actualizarMesa(mesaExistente);
                    mesasParaAsignar.add(mesaExistente.getIdMesa());
                } else {
                    // Si la mesa no existe, crear una nueva
                    int id = controladorH.insertarMesa(i, capacidad, "Disponible");
                    mesasParaAsignar.add(id);
                }

            }
            // Una vez creadas y actualizadas las mesas, asignarlas al plano
            int idPlano = controladorH.getIdPlanoMasReciente(); 
            controladorPlano.asignarMesasAlPlano(idPlano, mesasParaAsignar);
        }else{
            
            // Crear las mesas necesarias
            for (int i = 1; i <= cantidadDeMesas; i++) {

                String capacidadInput = JOptionPane.showInputDialog("Ingrese la capacidad para la Mesa " + i + ":");
                if (capacidadInput == null || capacidadInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar una capacidad válida.");
                    i--; // Retrocede el índice para volver a pedir la capacidad para esta mesa
                    continue;
                }

                int capacidad;
                try {
                    capacidad = Integer.parseInt(capacidadInput);
                    if (capacidad <= 0) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un número mayor que 0.");
                        i--; // Retrocede el índice para volver a pedir la capacidad para esta mesa
                        continue;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.");
                    i--; // Retrocede el índice para volver a pedir la capacidad para esta mesa
                    continue;
                }
                if (i <= mesasExistentes.size()) {
                    // Si la mesa ya existe, actualizar sus datos
                    Mesa mesaExistente = mesasExistentes.get(i - 1);
                    mesaExistente.setNumeroMesa(i);
                    mesaExistente.setCapacidad(capacidad);
                    controladorH.actualizarMesa(mesaExistente);
                    mesasParaAsignar.add(mesaExistente.getIdMesa());
                } else {
                    // Si la mesa no existe, crear una nueva
                    int id = controladorH.insertarMesa(i, capacidad, "Disponible");
                    mesasParaAsignar.add(id);
                }

                // Una vez creadas y actualizadas las mesas, asignarlas al plano
                int idPlano = controladorH.getIdPlanoMasReciente(); 
                controladorPlano.asignarMesasAlPlano(idPlano, mesasParaAsignar);
            }

        }
        
        // Mostrar mensaje de éxito
        mostrarMensaje("Mesas agregadas correctamente al plano.");
        controladorH.mostrarMesaPlano();
    }



    public void setControladorPlano(cRegPlano controladorPlano) {
        this.controladorPlano = controladorPlano;
    }
    
    /**
     * Método para mostrar mensajes al usuario en una ventana emergente.
     * @param mensaje El mensaje que se va a mostrar al usuario.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }
    
    public void llenarDatos(String nombrePlano,String descripcion,int cantidad){
        txtNombrePlano.setText(nombrePlano);
        txtDescripcion.setText(descripcion);
        spnCantidadDeMesas.setValue(cantidad);
    }
    
    /**
     * Método para establecer el controlador principal del sistema.
     * @param controladorH Objeto de tipo cHome que actúa como controlador principal.
     */
    public void setControladorH(cHome controladorH) {
        this.controladorH = controladorH;
    }

    public cRegPlano getControladorPlano() {
        return controladorPlano;
    }
    
    
}
