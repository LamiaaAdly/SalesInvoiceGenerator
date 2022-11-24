package model;

import javax.swing.*;
import java.lang.reflect.Field;

public class InvoiceLine {
    private int itemNum =0;
    private double itemTotal;
    private String itemName;
    private double itemPrice;
    private int count;
    private int invoiceNum;
    InvoiceHeader invoiceHeader;

    public InvoiceLine(int invoiceNum,String itemName, double itemPrice, int count) {
//        invoiceNum = invoiceHeader.getInvoiceNum();
        this.itemNum = getItemNum();
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.itemTotal = getItemTotal();
        itemNum++;
    }

    public InvoiceHeader getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(InvoiceHeader invoiceHeader){
        this.invoiceHeader = invoiceHeader;
        if(!invoiceHeader.getInvoiceLines(invoiceNum).contains(this)){
            invoiceHeader.getInvoiceLines(invoiceNum).add(this);
        }
    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }
    public void setItemPrice(double itemPrice) {
        if(itemPrice >= 0) this.itemPrice = itemPrice;
        else System.out.println("Price can not be negative value.");
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if(count > 0) this.count = count;
        else System.out.println("Count must be more than 0.");

    }
    public int getInvoiceNum() {
        invoiceNum = invoiceHeader.getInvoiceNum();
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }
    public static String[] getParameterNames() {
        String[] parameterNames = new String[]{"Item Num",
//                                                "Invoice Num",
                                                "Item Name",
                                                "Price",
                                                "Count",
                                                "Item Total"};
        return parameterNames;
    }

    public int getItemNum() {
        return itemNum;
    }

    public double getItemTotal() {
        double total = getItemPrice() * getCount();
        return total;
    }


//    public static double getItemTotal(double price, int count){
//        return price*count;
//    }
}
