package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class InvoiceHeaderModel extends AbstractTableModel {
    public ArrayList<InvoiceHeader> invoiceList;
    String[] headers;


    public InvoiceHeaderModel(ArrayList<InvoiceHeader> list, String[] headers){
        this.invoiceList = list;
        this.headers = headers;
    }
    @Override
    public int getRowCount() {
        return invoiceList.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(invoiceList.size() <= rowIndex || rowIndex < 0)
            return "";

        switch (columnIndex){
            case 0:
                return invoiceList.get(rowIndex).getInvoiceNum();
            case 1:
                return invoiceList.get(rowIndex).getInvoiceDate();
            case 2:
                return invoiceList.get(rowIndex).getCustomerName();
            case 3:
                ArrayList<InvoiceLine> items;
                items = invoiceList.get(rowIndex).getInvoiceLines(invoiceList.get(rowIndex).getInvoiceNum());
                return InvoiceHeader.getInvoiceTotal(items);
        }
        return null;
    }

    @Override
    public String getColumnName(int column)
    {
        return headers[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);

        InvoiceHeader item = invoiceList.get(rowIndex);

        String Value = aValue.toString();

        switch (columnIndex){
            case 0:
                item.setInvoiceNum(Integer.valueOf(Value));
                break;
            case 1:
                item.setInvoiceDate(Value);
                break;
            case 2:
                item.setCustomerName(Value);
                break;

        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

}
