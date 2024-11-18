package RenderingTable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private JButton button;
    private JTable table;

    public ButtonRendererEditor(JTable table) {
        this.table = table;
        button = new JButton("Ver");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow(); // Obtener la fila seleccionada
                String pedidoId = table.getValueAt(row, 1).toString(); 
                JOptionPane.showMessageDialog(null, "Botón clicado " + pedidoId);
                fireEditingStopped(); // Termina la edición después del clic
            }
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return button; 
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button; 
    }

    @Override
    public Object getCellEditorValue() {
        return ""; 
    }
}
