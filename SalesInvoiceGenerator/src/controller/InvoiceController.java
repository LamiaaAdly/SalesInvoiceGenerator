package controller;

import model.*;
import view.SalesInvoiceUI;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class InvoiceController {
    // declaring the variables model and view
    private static ArrayList<InvoiceHeader> invoices;
    private static InvoiceHeaderModel invoicesModel;
    private static ArrayList<InvoiceLine> items;
    private static InvoiceLineModel invoiceLineModel;
    private static SalesInvoiceUI view;
    private static String invoicePath, invoiceDirectory,fileNameOfInvoices, itemPath;

    public InvoiceController(ArrayList<InvoiceHeader> invoices, InvoiceHeaderModel invoiceHeaderModel,
                             ArrayList<InvoiceLine> items, InvoiceLineModel invoiceLineModel,
                             SalesInvoiceUI view) {
        this.invoices = invoices;
        this.invoicesModel = invoiceHeaderModel;
        this.items = items;
        this.invoiceLineModel = invoiceLineModel;
        this.view = view;


    }

    public static void setInvoicePath(String invoicePath) {
        InvoiceController.invoicePath = invoicePath;
    }

    public static void setInvoiceDirectory(String invoiceDirectory) {
        InvoiceController.invoiceDirectory = invoiceDirectory;
    }

    public static void setFileNameOfInvoices(String fileNameOfInvoices) {
        InvoiceController.fileNameOfInvoices = fileNameOfInvoices;
    }

    public static void setItemPath(String itemPath) {
        InvoiceController.itemPath = itemPath;
    }

    public void updateView() {
        //print Data
    }

    public static void loadFile(JTable invoicesTable){
//        ArrayList<InvoiceHeader> invoicesTableData = invoices;
//        InvoiceHeaderModel invoicesModel;
        String paths[] = FileOperations.getPaths(view);
        invoicePath = paths[0];
        invoiceDirectory = paths[1];
        fileNameOfInvoices = paths[2];

        invoices = FileOperations.readFile(invoicePath);

        invoicesModel =new InvoiceHeaderModel(invoices, InvoiceHeader.getParameterNames());
        invoicesTable.setModel(invoicesModel);
    }
    public static void saveFile(){

        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("..\\InvoiceTables\\InvoiceHeader"));
        int result = fc.showSaveDialog(view);
        if(result == JFileChooser.APPROVE_OPTION){
            String path = fc.getSelectedFile().getPath();
            if(invoices!=null) {
                FileOperations.writeFile(path, invoices);
            }
        }
    }

    public static void createItem(){//save changes

        invoiceLineModel.itemList.add(new InvoiceLine("", 0.0, 0));
        invoiceLineModel.fireTableStructureChanged();

//        int ivNum = Integer.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,0).toString());
//        String ivDate = invoiceLineModel.getValueAt(ivNum,1).toString();
//        String ivCustomerName = invoiceLineModel.getValueAt(ivNum,2).toString();
//
//        String brePath = invoiceDirectory.replace("\\InvoiceHeader","\\InvoiceLines");
//        itemPath = brePath + "\\" + String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,0)) + ".csv";
//
//        FileOperations.writeInvoiceLineFile(itemPath, items);
//        invoicesModel.setValueAt(ivDate,invoicesTableRowSelected,1);
//        invoicesModel.setValueAt(ivCustomerName,invoicesTableRowSelected,2);

    }
    public static void deleteItem(int itemSelected){// cancel changes

        if(itemSelected >= 0) {
            invoiceLineModel.itemList.remove(itemSelected);
            System.out.println("item No."+ itemSelected +" removed!");
        }
        try {
            items = FileOperations.readInvoiceLineFile(itemPath);
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


//        JOptionPane.showMessageDialog(null, "Changes not saved to Invoice Items Table",
//                "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
//
//        items = FileOperations.readInvoiceLineFile(itemPath);


    }

    public static void createInvoice(JTable invoicesTable,JTable invoiceItems,int invoicesTableRowSelected){

        if(invoicesTable.isRowSelected(invoicesTableRowSelected)) {
            invoicesTable.clearSelection();
        }

        int newInvoiceNo = (int)invoicesModel.getValueAt(invoicesModel.invoiceList.size()-1,0) +1;
        String brePath = invoiceDirectory.replace("\\InvoiceHeader","\\invoiceLines");
        itemPath = brePath + "\\" + newInvoiceNo+ ".csv";
        FileOperations.CreateFile(itemPath);
        invoicesModel.invoiceList.add(new InvoiceHeader(newInvoiceNo, "", ""));
        invoicesModel.fireTableStructureChanged();



//        int newInvoiceNo = (int)invoicesModel.getValueAt(invoicesModel.invoiceList.size()-1,0) +1;
//        String brePath = invoiceDirectory.replace("\\openInvoices","\\invoiceLines");
//        itemPath = brePath + "\\" + newInvoiceNo+ ".csv";
//        FileOperations.CreateFile(itemPath);
//        invoicesModel.invoiceList.add(new InvoiceHeader(newInvoiceNo, "", ""));
//        invoicesModel.fireTableStructureChanged();
//
//        ArrayList<InvoiceLine> emptyItems = new ArrayList<>();
//        InvoiceLineModel items  = new InvoiceLineModel(emptyItems, InvoiceLine.getParameterNames());
//        if(invoiceItems == null)
//            invoiceItems = new JTable();
//        invoiceItems.setModel(items);

    }
    public static void deleteInvoice(int invoicesTableRowSelected){
        if(invoicesTableRowSelected >= 0) {
            invoicesModel.invoiceList.remove(invoicesTableRowSelected);
            System.out.println("file removed!");
        }
        String invoPath = invoiceDirectory.replace("\\invoices","\\InvoiceHeader") + fileNameOfInvoices +".csv";
        FileOperations.readFile(invoPath);
    }


}
