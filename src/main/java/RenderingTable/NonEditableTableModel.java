package RenderingTable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {
    
    public NonEditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Hacer que todas las celdas no sean editables
    }
}


