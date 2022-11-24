package model;
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
                invoices.add(new InvoiceHeader(Integer.valueOf(dataCell[0]),
                                                dataCell[1],
                                                dataCell[2]));
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (StackOverflowError e){
            e.printStackTrace();
        }finally {
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

            int nRow = list.size();
            int nCol = InvoiceHeader.getParameterNames().length;
            String data = "";

            //write row information
            for (int i = 0 ; i < nRow ; i++){
                for (int j = 0 ; j < nCol ; j++){
                    data += String.valueOf(list.get(i+j));

                    if(j==nCol-1) data=data.replace("\r","");
                    if (j!=nCol-1) data += ",";
                }
                if(i!=nRow-1)data += "\r\n";
            }

            byte[] b = data.getBytes();
            fos.write(b);

            int itemLen;
            for(int i = 0; i< list.size(); i++){
                int k = list.get(0).getInvoiceNum();
                itemLen = list.get(i).getInvoiceLines(k).size();
//                for(int j = 0; j < itemLen; j++){
                    ArrayList<InvoiceLine> items = list.get(i).getInvoiceLines(k);
                    String brePath = "..\\InvoiceTables\\InvoiceHeader" + "\\" + "invoiceHeader";
                    String itemPath = brePath +"\\"+ k+ ".csv";
                    writeInvoiceLineFile(itemPath, items);
//                }
            }



        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                fos.close();
            }catch (IOException e){}
        }

    }

    public static ArrayList<InvoiceLine> readInvoiceLineFile(String path){
        ArrayList<InvoiceLine> items = new ArrayList<>();

        FileInputStream fis = null;
        try {
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
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
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
            int nCol = InvoiceHeader.getParameterNames().length;
            String data = "";

            //write row information
            for (int i = 0 ; i < nRow ; i++){
                for (int j = 0 ; j < nCol ; j++){
                    data += String.valueOf(list.get(i+j));

                    if(j==nCol-1) data=data.replace("\r","");
                    if (j!=nCol-1) data += ",";
                }
                if(i!=nRow-1)data += "\r\n";
            }

            byte[] b = data.getBytes();
            fos.write(b);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
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
        } catch (NullPointerException e){
            e.printStackTrace();
        }catch (IOException e) {
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

    public static void test(ArrayList<InvoiceHeader> invoiceList) {
        try {

            for (int i = 0; i < invoiceList.size()-1; i++) {
                System.out.println("Invoice" + (i + 1) + "Num\n{");
                int k = invoiceList.get(0).getInvoiceNum();
                ArrayList<InvoiceLine> list = invoiceList.get(i).getInvoiceLines(k);
                int itemLen = list.size();

                for (int j = 0; j < itemLen-1; j++) {
                    System.out.println("Invoice" + i + "Date (" + invoiceList.get(1) + "), Customer" + i + "Name");
                    System.out.println("Item" + (j + 1) + "Name " + list.get(i).getItemName() +
                            ", Item" + (j + 1) + "Price " + list.get(i).getItemPrice() +
                            ", Count " + list.get(i).getCount());
                }

                System.out.println("}");
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }
}
