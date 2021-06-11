package general;

import javax.swing.table.DefaultTableModel;

public class NonEditableModel extends DefaultTableModel {

    public NonEditableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}