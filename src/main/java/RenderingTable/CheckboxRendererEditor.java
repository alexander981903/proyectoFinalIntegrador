package RenderingTable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;


public class CheckboxRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private JCheckBox checkBox;
    private JTable table;

    public CheckboxRendererEditor(JTable table) {
        this.table = table;
        checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(SwingConstants.CENTER); // Alineación horizontal del checkbox
        
        // Agregar un ChangeListener para detectar cambios en el estado del checkbox
        checkBox.addActionListener(e -> fireEditingStopped()); // Esto termina la edición cuando el checkbox cambia
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Establece si el checkbox está marcado o no
        checkBox.setSelected((Boolean) value);
        return checkBox;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Establece si el checkbox está marcado o no
        checkBox.setSelected((Boolean) value);
        return checkBox;
    }

    @Override
    public Object getCellEditorValue() {
        // Devuelve el valor booleano (si está marcado o no)
        return checkBox.isSelected();
    }
}
