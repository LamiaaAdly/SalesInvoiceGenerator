package controller;

import model.*;
import view.SalesInvoiceUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class InvoiceController {

    private static String invoicePath, invoiceDirectory,fileNameOfInvoices, itemPath;
    public static void loadFile(Component context, JTable invoicesTable){
        ArrayList<InvoiceHeader> invoicesTableData;
        InvoiceHeaderModel invoicesModel;

        String paths[] = FileOperations.getPaths(context);
        invoicePath = paths[0];
        invoiceDirectory = paths[1];
        fileNameOfInvoices = paths[2];

        invoicesTableData = FileOperations.readFile(invoicePath);

        invoicesModel =new InvoiceHeaderModel(invoicesTableData, InvoiceHeader.getParameterNames());
        invoicesTable.setModel(invoicesModel);
    }
    public static void saveFile(Component context, ArrayList<InvoiceHeader> list){

        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("..\\InvoiceTables\\InvoiceHeader"));
        int result = fc.showSaveDialog(context);
        if(result == JFileChooser.APPROVE_OPTION){
            String path = fc.getSelectedFile().getPath();
            if(list!=null) {
                FileOperations.writeFile(path, list);
            }
        }
    }

    public static void saveChanges(int invoiceNo,int invoicesTableRowSelected,InvoiceHeaderModel invoiceModel,InvoiceLineModel itemsModel){
        ArrayList<InvoiceLine> invoicesItems;
        invoicesItems = itemsModel.itemList;
//        double sum =0;
//        for(int i=0;i<itemsModel.list.size();i++) {
//            sum += Double.valueOf(itemsModel.getValueAt(i, 4).toString());
//        }
        String Ivdate = itemsModel.getValueAt(invoiceNo,1).toString();
        String IvcustomerName = itemsModel.getValueAt(invoiceNo,2).toString();

        String brePath = invoiceDirectory.replace("\\InvoiceHeader","\\InvoiceLines");
        itemPath = brePath + "\\" + String.valueOf(invoiceModel.getValueAt(invoicesTableRowSelected,0)) + ".csv";

        FileOperations.writeInvoiceLineFile(itemPath, invoicesItems);
        invoiceModel.setValueAt(Ivdate,invoicesTableRowSelected,1);
        invoiceModel.setValueAt(IvcustomerName,invoicesTableRowSelected,2);
//        invoiceModel.setValueAt(sum,invoicesTableRowSelected,3);

    }
    public static void cancelChanges(){

        JOptionPane.showMessageDialog(null, "Changes not saved to Invoice Items Table",
                "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);

        ArrayList<InvoiceLine> items = FileOperations.readInvoiceLineFile(itemPath);


    }

    public static void createInvoice(InvoiceHeaderModel invoiceModel, JTable invoiceItems){

        int newInvoiceNo = (int)invoiceModel.getValueAt(invoiceModel.invoiceList.size()-1,0) +1;
        String brePath = invoiceDirectory.replace("\\openInvoices","\\invoiceLines");
        itemPath = brePath + "\\" + newInvoiceNo+ ".csv";
        FileOperations.CreateFile(itemPath);
        invoiceModel.invoiceList.add(new InvoiceHeader(newInvoiceNo, "", ""));
        invoiceModel.fireTableStructureChanged();

        ArrayList<InvoiceLine> emptyItems = new ArrayList<>();
        InvoiceLineModel items  = new InvoiceLineModel(emptyItems, InvoiceLine.getParameterNames());
        if(invoiceItems == null)
            invoiceItems = new JTable();
        invoiceItems.setModel(items);

    }
    public static void deleteInvoice(int invoicesTableRowSelected, InvoiceHeaderModel invoicesModel){
        if(invoicesTableRowSelected >= 0) {
            invoicesModel.invoiceList.remove(invoicesTableRowSelected);
            System.out.println("file removed!");
        }
        String invoPath = invoiceDirectory.replace("\\invoices","\\InvoiceHeader") + fileNameOfInvoices +".csv";
        FileOperations.readFile(invoPath);
    }


}
