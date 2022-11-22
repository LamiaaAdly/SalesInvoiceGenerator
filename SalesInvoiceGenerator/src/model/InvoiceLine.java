package model;

import javax.swing.*;
import java.lang.reflect.Field;

public class InvoiceLine {

    private String itemName;
    private double itemPrice;
    private int count;

    public InvoiceLine(String itemName, double itemPrice, int count) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
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
    public static String[] getParameterNames() {
        String[] parameterNames = new String[]{"Item Name",
                                                "Price",
                                                "Count"};
        return parameterNames;
    }

    public static double getItemTotal(double price, int count){
        return price*count;
    }
}
