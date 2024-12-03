package RenderingTable;

import Vista.vistaHome;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private JButton button;
    private JTable table;
    private vistaHome vistaH;

    public ButtonRendererEditor(JTable table, vistaHome vistaH) {
        this.table = table;
        this.vistaH = vistaH;
        button = new JButton("OK");

        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                try {
                    int row = table.getSelectedRow();
                    
                    if (row >= 0) {
                        Object value = table.getValueAt(row, 1);
                        if (value != null && value instanceof Integer) {
                            int reservaId = (int) value;
                            vistaH.getControladorH().recuperarReserva(reservaId);
                            vistaH.getControladorH().cargarReservas();
                            vistaH.getControladorH().mostrarMesaPlano();
                            fireEditingStopped();
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Atencion: No existe reservaciones activas.", 
                                                  "Advertencia", JOptionPane.WARNING_MESSAGE);
                } catch (ClassCastException ex) {
                    JOptionPane.showMessageDialog(null, "Error al convertir el valor de la celda. Por favor, verifique los datos.", 
                                                  "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado: " + ex.getMessage(), 
                                                  "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (table.getRowCount() > 0) {
            return button; 
        } else {
            return null; // No renderizar el botón si la tabla está vacía
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (table.getRowCount() > 0) {
            return button; 
        } else {
            return null; // No renderizar el botón si la tabla está vacía
        }
    }

    @Override
    public Object getCellEditorValue() {
        return ""; 
    }
}
