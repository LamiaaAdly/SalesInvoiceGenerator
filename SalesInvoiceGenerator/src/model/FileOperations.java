package model;
import controller.InvoiceController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class FileOperations {

    public static ArrayList<InvoiceHeader> readFile(String path){
        ArrayList<InvoiceHeader> invoices = new ArrayList<>();

        FileInputStream fis = null;
        try {
            fis= new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis) );
            while(reader.ready()) {
                String line = reader.readLine();
                String[] dataCell = line.split(",");
                InvoiceHeader inv = new InvoiceHeader(Integer.valueOf(dataCell[0]),
                        dataCell[1],
                        dataCell[2]);
                String brePath = "..\\InvoiceTables\\InvoiceHeader" + "\\" + InvoiceController.getFileNameOfInvoices().replace(".csv","");
                String itemPath = brePath +"\\"+ inv.getInvoiceNum()+ ".csv";
                inv.setInvoiceLines(readInvoiceLineFile(itemPath));
                invoices.add(inv);

            }
        } catch (NullPointerException | IOException | StackOverflowError e){
            e.printStackTrace();
        } finally {
            try{
                if(fis!=null) {
                    fis.close();
                }
            }catch (IOException e){}
        }
        return invoices;
    }
    public static void writeFile(String path,ArrayList<InvoiceHeader> list){

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.flush();

            int nRow = list.size();
            int nCol = InvoiceHeader.getParameterNames().length;
            String data = "";

            //write row information
            for (int i = 0 ; i < nRow ; i++){
                for (int j = 0 ; j < nCol-1 ; j++){
                    switch (j){
                        case 0:
                            data += String.valueOf(list.get(i).getInvoiceNum());
                            break;
                        case 1:
                            data += String.valueOf(list.get(i).getInvoiceDate());
                            break;
                        case 2:
                            data += String.valueOf(list.get(i).getCustomerName());
                            break;
                    }
//                    if(j==nCol-1) data=data.replace("\r","");
                    if (j!=nCol-2) data += ",";
                }
                if(i!=nRow-1)data += "\r\n";
            }

            byte[] b = data.getBytes();
            fos.write(b);

//            int itemLen;
            for(int i = 0; i< list.size(); i++){
                int k = list.get(i).getInvoiceNum();
//                itemLen = list.get(i).getInvoiceLines(k).size();

                ArrayList<InvoiceLine> items = list.get(i).getInvoiceLines(k);
                String brePath = "..\\InvoiceTables\\InvoiceHeader" + "\\" + InvoiceController.getFileNameOfInvoices().replace(".csv","");
                String itemPath = brePath +"\\"+ k+ ".csv";
                CreateFile(itemPath);
                writeInvoiceLineFile(itemPath, items);
            }

        } catch (IndexOutOfBoundsException | IOException e){
            e.printStackTrace();
        } finally {
            try{
                fos.close();
            }catch (IOException e){}
        }

    }

    public static ArrayList<InvoiceLine> readInvoiceLineFile(String path){
        ArrayList<InvoiceLine> items = new ArrayList<>();

        FileInputStream fis = null;
        try {
            if(path== null){
                path= InvoiceController.getInvoicePath();
            }
            fis= new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis) );
            while(reader.ready()) {
                String line = reader.readLine();
                String[] dataCell = line.split(",");
                items.add(new InvoiceLine(Integer.valueOf(dataCell[0]),
                        dataCell[1],
                        Double.parseDouble(dataCell[2]),
                        Integer.valueOf(dataCell[3])));
            }
        } catch (NullPointerException | IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(fis!=null) {
                    fis.close();
                }
            }catch (IOException e){}
        }
        return items;
    }

    public static void writeInvoiceLineFile(String path, ArrayList<InvoiceLine> list){

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);

            int nRow = list.size();
            int nCol = InvoiceLine.getParameterNames().length -1;
            String data = "";

            //write row information
            for (int i = 0 ; i < nRow ; i++){
                for (int j = 0 ; j < nCol ; j++){
                    switch (j){
                        case 0:
                            data += String.valueOf(list.get(i).getInvoiceNum());
                            break;
                        case 1:
                            data += String.valueOf(list.get(i).getItemName());
                            break;
                        case 2:
                            data += String.valueOf(list.get(i).getItemPrice());
                            break;
                        case 3:
                            data += String.valueOf(list.get(i).getCount());
                            break;
                    }

//                    if(j==nCol-1) data=data.replace("\r","");
                    if (j!=nCol-1) data += ",";
                }
                if(i!=nRow-1)data += "\r\n";
            }

            byte[] b = data.getBytes();
            fos.write(b);

        } catch (NullPointerException | IOException e){
            e.printStackTrace();
        } finally {
            try{
                fos.close();
            }catch (IOException e){}
        }

    }

    public static void CreateFile(String path) {
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        }catch (NullPointerException | FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String[] getPaths(Component context)
    {
        String path =null;
        String parentDirectory =null;
        String fileName =null;
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("..\\InvoiceTables\\InvoiceHeader"));
        int result = fc.showOpenDialog(context);
        if(result == JFileChooser.APPROVE_OPTION) {
            path = fc.getSelectedFile().getPath();
            parentDirectory = fc.getSelectedFile().getParent();
            fileName = fc.getSelectedFile().getName();
        }
        return new String[] {path, parentDirectory, fileName};
    }

    public static void test() {
        try {
            ArrayList<InvoiceHeader> invoiceList = readFile(InvoiceController.getInvoicePath());
            for (int i = 0; i < invoiceList.size(); i++) {
                int invoNo = invoiceList.get(i).getInvoiceNum();
                System.out.println("Invoice" + invoNo+ "Num\n{");

                ArrayList<InvoiceLine> list = invoiceList.get(i).getInvoiceLines(invoNo);
                int itemLen = list.size();
                System.out.println("Invoice " + invoiceList.get(i).getInvoiceNum() +
                        ", Date (" + invoiceList.get(i).getInvoiceDate() +
                        "), Customer" + invoiceList.get(i).getInvoiceNum() +
                        "Name " + invoiceList.get(i).getCustomerName());
                for (int j = 0; j < itemLen; j++) {
                    System.out.println("Item" + (j + 1) + ": Name " + list.get(j).getItemName() +
                            ", Item" + (j + 1) + "Price " + list.get(j).getItemPrice() +
                            ", Count " + list.get(j).getCount());
                }

                System.out.println("}");
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }
}
