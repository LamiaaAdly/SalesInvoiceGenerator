package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class InvoiceLineModel extends AbstractTableModel {

    public ArrayList<InvoiceLine> itemList;
    String[] headers;

    public InvoiceLineModel(ArrayList<InvoiceLine> list, String[] headers){
        this.itemList = list;
        this.headers = headers;
    }
    @Override
    public int getRowCount() {
        return itemList.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return itemList.get(rowIndex).getItemNum();
//            case 1:
//                return itemList.get(rowIndex).getInvoiceNum();
            case 1:
                return itemList.get(rowIndex).getItemName();
            case 2:
                return itemList.get(rowIndex).getItemPrice();
            case 3:
                return itemList.get(rowIndex).getCount();
            case 4:
                return itemList.get(rowIndex).getItemTotal();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);

        InvoiceLine item = itemList.get(rowIndex);
        String Value = aValue.toString();
        switch (columnIndex){
            case 0:
                item.setInvoiceNum(Integer.valueOf(Value));
                break;
            case 1:
                item.setItemName(Value);
                break;
            case 2:
                item.setItemPrice(Double.valueOf(Value));
                break;
            case 3:
                item.setCount(Integer.valueOf(Value));
                break;

        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column)
    {
        return headers[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
