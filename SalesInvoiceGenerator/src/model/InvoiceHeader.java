package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceHeader {

    private int invoiceNum;
    private String invoiceDate;
    private String customerName;

    ArrayList<InvoiceLine> invoiceLines;

    public InvoiceHeader(int invoiceNum, String invoiceDate, String customerName){
        this.invoiceNum = invoiceNum;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
    }


    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(ArrayList<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public static String[] getParameterNames() {
        String[] parameterNames = new String[]{"Invoice Num",
                                                "Invoice Date",
                                                "Customer Name"};
        return parameterNames;
    }

    public static double getInvoiceTotal(ArrayList<InvoiceLine> items){
        double total=0;
        if(items != null) {
            for (int i = 0; i < items.size(); i++) {
                total += items.get(i).getItemPrice() *
                        items.get(i).getCount();
            }
        }
        return total;
    }
}
