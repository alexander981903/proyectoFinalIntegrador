package RenderingTable;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;
import javax.swing.JTable;

public class DecimalFormatRenderer extends DefaultTableCellRenderer {
    private final DecimalFormat formatter = new DecimalFormat("#.00");
    private JTable table;

    public DecimalFormatRenderer(JTable table) {
        this.table = table;       
    }   
    
    @Override
    public void setValue(Object value) {
        if (value instanceof Number) {
            value = formatter.format(((Number) value).doubleValue());
        }
        super.setValue(value);
    }
}
