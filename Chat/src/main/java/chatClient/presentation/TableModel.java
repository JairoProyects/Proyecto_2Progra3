package chatClient.presentation;


import chatProtocol.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {

    String[] colNames = new String[2];
    public static final int NOMBRE=0;
    public static final int STATUS=1;

    List<User> rows;
    int[] cols;

    private void initColNames(){
        colNames[NOMBRE]= "Nombre";
        colNames[STATUS]= "Status";
    }

    public TableModel(int[] cols, List<User> rows){
        initColNames();
        this.cols=cols;
        this.rows=rows;
    }

    public int getColumnCount() {
        return cols.length;
    }
    public String getColumnName(int col){
        return colNames[cols[col]];
    }
    public int getRowCount() {
        return rows.size();
    }
    public Class<?> getColumnClass(int col){
       switch(cols[col]){
           case 1: return Boolean.class;
           default: return super.getColumnClass(col);
       }
    }

    public Object getValueAt(int row, int col) {
        User user = rows.get(row);
        switch (cols[col]){
            case 0: return user.getNombre();
            case 1: return true;
            default: return "";
        }
    }
}