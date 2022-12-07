package controller;

import model.*;
import view.SalesInvoiceUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class InvoiceController {
    // declaring the variables model and view
    private static ArrayList<InvoiceHeader> invoices;
    private static InvoiceHeaderModel invoicesModel;
    private static ArrayList<InvoiceLine> items;
    private static InvoiceLineModel invoiceLineModel;
    private static SalesInvoiceUI view;
    private static String invoicePath, invoiceDirectory, fileNameOfInvoices, itemPath;

    public InvoiceController(ArrayList<InvoiceHeader> invoices, InvoiceHeaderModel invoiceHeaderModel,
                             ArrayList<InvoiceLine> items, InvoiceLineModel invoiceLineModel,
                             SalesInvoiceUI view) {
        this.invoices = invoices;
        this.invoicesModel = invoiceHeaderModel;
        this.items = items;
        this.invoiceLineModel = invoiceLineModel;
        this.view = view;
    }
    public static String getInvoicePath() {
        return invoicePath;
    }
    public static void setInvoicePath(String invoicePath) {
        InvoiceController.invoicePath = invoicePath;
    }

    public static String getInvoiceDirectory() {
        return invoiceDirectory;
    }
    public static void setInvoiceDirectory(String invoiceDirectory) {
        InvoiceController.invoiceDirectory = invoiceDirectory;
    }

    public static String getFileNameOfInvoices() {
        return fileNameOfInvoices;
    }
    public static void setFileNameOfInvoices(String fileNameOfInvoices) {
        InvoiceController.fileNameOfInvoices = fileNameOfInvoices;
    }

    public static void loadFile(JTable invoicesTable){

        String paths[] = FileOperations.getPaths(view);
        setInvoicePath(paths[0]);
//        setInvoiceDirectory(paths[1]);
        setFileNameOfInvoices(paths[1]);

        invoices = FileOperations.readFile(invoicePath);

        invoicesModel =new InvoiceHeaderModel(invoices, InvoiceHeader.getParameterNames());
        invoicesTable.setModel(invoicesModel);
        for (int i = 0; i < invoicesModel.invoiceList.size();i++)
        {
            InvoiceHeader inv = invoicesModel.invoiceList.get(i);
            inv.getInvoiceLines(inv.getInvoiceNum());
        }
    }
    public static void saveFile(ArrayList<InvoiceHeader> invoicesData, Component context){
        String path =".\\invoiceHeader.csv";
//                "..\\InvoiceTables\\InvoiceHeader\\invoiceHeader.csv";
//        JFileChooser fc = new JFileChooser();
//        fc.setCurrentDirectory(new File("..\\InvoiceTables\\InvoiceHeader"));
        try {
//            int result = fc.showSaveDialog(context);
//            if(result == JFileChooser.APPROVE_OPTION){
//                path = fc.getSelectedFile().getPath();
                if(invoicesData!=null) {
                    FileOperations.writeFile(path, invoicesData);
                }
//            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            String invoPath = ".\\invoiceHeader.csv";
            FileOperations.readFile(invoPath);
        }
    }
    // was "saveChanges" and updates to be "createItem"
    public static void createItem(int invoiceNum){
        invoiceLineModel.itemList.add(new InvoiceLine(invoiceNum,"", 0.0, 0));
        invoiceLineModel.fireTableStructureChanged();
    }
    // was "cancelChanges" and updates to be "deleteItem"
    public static void deleteItem(int InvoiceNum,int itemSelected){

        if(itemSelected >= 0) {
            invoiceLineModel.itemList.remove(itemSelected);
            System.out.println("item No."+ itemSelected +" removed!");
        }
        try {
//            String brePath = "..\\InvoiceTables\\InvoiceHeader" + "\\" + fileNameOfInvoices;
            String itemPath = ".\\invoiceLines.csv";

            items = FileOperations.readInvoiceLineFile(itemPath, InvoiceNum);
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createInvoice(JTable invoicesTable,int invoicesTableRowSelected){

        if(invoicesTable.isRowSelected(invoicesTableRowSelected)) {
            invoicesTable.clearSelection();
        }
        int newInvoiceNo = (int) invoicesModel.getValueAt(invoicesModel.invoiceList.size() - 1, 0) + 1;

        if(fileNameOfInvoices!=null) {
//            String brePath = "..\\InvoiceTables\\InvoiceHeader" + "\\" + fileNameOfInvoices;
            itemPath = ".\\invoiceLines.csv";
//            brePath.replace("invoiceHeader","invoiceLines");
        }
        FileOperations.CreateFile(itemPath);
        InvoiceHeader inv = new InvoiceHeader(newInvoiceNo, "", "");
        inv.setInvoiceLines(new ArrayList<>());
        invoicesModel.invoiceList.add(inv);
        invoicesModel.fireTableStructureChanged();
    }
    public static void deleteInvoice(int invoicesTableRowSelected){
        if(invoicesTableRowSelected >= 0) {
            invoicesModel.invoiceList.remove(invoicesTableRowSelected);
            invoicesModel.fireTableStructureChanged();
            //System.out.println(fileNameOfInvoices+" file removed!");
        }
    }
    public static void updateItemsModel(int invoicesTableRowSelected){

    }
}
