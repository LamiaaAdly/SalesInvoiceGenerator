package view;

import controller.InvoiceController;
import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SalesInvoiceUI extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadFile;
    private JMenuItem saveFile;

    private JPanel leftSection, rightSection;
    private JPanel southBtnPanel, southBtnPanel2;
    private JPanel leftSectionChild, rightSectionChild, invoiceItemsPanel;
    private JPanel itemForm;
    private JTable invoicesTable;
    private JTable invoiceItems;
    private String[] invoicesTableHeaders;
    private String[] invoiceItemsHeaders;
    private int invoicesTableRowSelected, itemSelected;
    private String invoicePath, fileNameOfInvoices;
    private String itemPath;
    private ArrayList<InvoiceHeader> invoices;
    private ArrayList<InvoiceLine> items;
    private InvoiceHeaderModel invoicesModel;
    private InvoiceLineModel itemsModel;
    private JScrollPane invoiceScrollPane, itemScrollPane;
    private JButton createInvoice, deleteInvoice;
    private JButton createItem, deleteItem;
    private JTextField invoiceDate,customerName, invoiceNo,invoiceTotal;

    public SalesInvoiceUI(String title) {
        super(title);
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        loadFile = new JMenuItem("Load File", 'L');
        loadFile.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_DOWN_MASK));
        loadFile.addActionListener(this);
        loadFile.setActionCommand("L");

        saveFile = new JMenuItem("Save File", 'S');
        saveFile.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
        saveFile.addActionListener(this);
        saveFile.setActionCommand("S");

        setJMenuBar(menuBar);

        menuBar.add(fileMenu);
        fileMenu.add(loadFile);
        fileMenu.add(saveFile);

        setLayout(new GridLayout(1,2));
        //Start leftSection
        leftSection = new JPanel();
        leftSectionChild = new JPanel();
        leftSectionChild.setLayout(new BoxLayout(leftSectionChild,BoxLayout.Y_AXIS));
        leftSection.setBorder(BorderFactory.createEtchedBorder());
        leftSectionChild.setBorder(BorderFactory.createTitledBorder("Invoices Table"));
        leftSectionChild.setSize(leftSection.getWidth(),leftSection.getHeight()/2);

        invoicesTableHeaders = InvoiceHeader.getParameterNames();

        String[] paths = FileOperations.getPaths();
        invoicePath = paths[0];
        fileNameOfInvoices = paths[1];

//        InvoiceController.setInvoicePath(paths[0]);
////        InvoiceController.setInvoiceDirectory(paths[1]);
//        InvoiceController.setFileNameOfInvoices(paths[1]);

        invoices = FileOperations.readFile(invoicePath);
        FileOperations.test();

//        invoicesTable = new JTable();
//        invoicesModel =new InvoiceHeaderModel(invoices, invoicesTableHeaders);
//        invoicesTable.setModel(invoicesModel);
//        invoicesTable.setCellSelectionEnabled(true);
//        invoicesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//        invoiceScrollPane = new JScrollPane();
//        invoiceScrollPane.setBounds(10, 79, 300, 500);
//        invoiceScrollPane.setViewportView(invoicesTable);
//
//        //Add Selection Listener to Invoice Table
//        invoicesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                initializeItemsTable();
//            }
//        });

        invoicesTable = createInvoiceHeaderTable(invoices);

        leftSectionChild.add(invoicesTable.getTableHeader());
        leftSectionChild.add(invoiceScrollPane);
        leftSection.add(leftSectionChild);
        leftSection.add(leftSectionChild, BorderLayout.NORTH);

        //Buttons Area (createInvoice Button, deleteInvoice Button)
        southBtnPanel = new JPanel();
        createInvoice = new JButton("Create New Invoice");
        createInvoice.setActionCommand("createInvoice");
        createInvoice.addActionListener(this);
        southBtnPanel.add(createInvoice);

        deleteInvoice = new JButton("Delete Invoice");
        deleteInvoice.setActionCommand("deleteInvoice");
        deleteInvoice.addActionListener(this);
        southBtnPanel.add(deleteInvoice);

        leftSection.add(southBtnPanel, BorderLayout.SOUTH);
        add(leftSection);
        //End leftSection

        //Start rightSection
        rightSection = new JPanel();
        rightSection.setBorder(BorderFactory.createEtchedBorder());
        rightSectionChild =new JPanel();
        rightSectionChild.setLayout(new BoxLayout(rightSectionChild, BoxLayout.Y_AXIS));
        itemForm = new JPanel();
        itemForm.setLayout(new BoxLayout(itemForm, BoxLayout.Y_AXIS));
        itemForm.setSize(rightSection.getWidth(),rightSection.getHeight()/3);

        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Invoice Number:"));
        invoiceNo = new JTextField(15);
        invoiceNo.setEnabled(false);
        panel1.add(invoiceNo);
        itemForm.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Customer Name:"));
        customerName = new JTextField(15);
        panel2.add(customerName);
        itemForm.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("Date:"));
        invoiceDate = new JTextField(15);
        panel3.add(invoiceDate);
        itemForm.add(panel3);

        JPanel panel4 = new JPanel();
        panel4.add(new JLabel("Total:"));
        invoiceTotal = new JTextField(15);
        invoiceTotal.setEnabled(false);
        panel4.add(invoiceTotal);
        itemForm.add(panel4);

        invoiceItemsPanel = new JPanel();
        invoiceItemsPanel.setBorder(BorderFactory.createTitledBorder("Invoice Items"));
//        invoiceItemsPanel.setSize(rightSection.getWidth(),rightSection.getHeight()/2);

        invoiceItemsHeaders = InvoiceLine.getParameterNames();
        itemScrollPane = new JScrollPane();
        itemScrollPane.setViewportView(new JTable());
        itemScrollPane.setBounds(10, 79, 300, 400);
        invoiceItemsPanel.add(itemScrollPane);
        itemForm.add(invoiceItemsPanel);

        rightSectionChild.add(itemForm);
        //rightSection.add(rightSectionChild);
        rightSection.add(rightSectionChild, BorderLayout.NORTH);

        southBtnPanel2 = new JPanel();
        createItem = new JButton("Create Item");
        createItem.setActionCommand("createItem");
        createItem.addActionListener(this);
        southBtnPanel2.add(createItem);

        deleteItem = new JButton("Delete Item");
        deleteItem.setActionCommand("deleteItem");
        deleteItem.addActionListener(this);
        southBtnPanel2.add(deleteItem);
        rightSection.add(southBtnPanel2, BorderLayout.SOUTH);

        add(rightSection);
        //End rightSection

        setSize(1000,750);
        setLocation(200,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
//        InvoiceController controller = new InvoiceController(invoices, invoicesModel,items,itemsModel,this);
        ArrayList<InvoiceLine> emptyItems  = new ArrayList<>();
        ArrayList<InvoiceHeader> emptyInvoices  = new ArrayList<>();
        int ret;
        switch (e.getActionCommand()) {
            case "L": //Load
                invoiceNo.setText("");
                customerName.setText("");
                invoiceDate.setText("");
                invoiceTotal.setText("0.00");

                itemsModel  =
                        new InvoiceLineModel(emptyItems, invoiceItemsHeaders);
                invoiceItems.setModel(itemsModel);

                invoicesModel = InvoiceController.loadFile(invoicesTable);
                invoices = invoicesModel.invoiceList;

//                itemsModel.fireTableStructureChanged();
                invoicesTable.setModel(invoicesModel);
                invoicesModel.fireTableStructureChanged();

                break;
            case "S": //Save
                for(int i=0; i<invoicesModel.invoiceList.size(); i++) {
                    int num = invoicesModel.invoiceList.get(i).getInvoiceNum(); //InvoiceNum from Invoices

                    int invoNumItem;
                    if(invoicesTableRowSelected < 0)
                        invoNumItem = 0;
                    else
                        invoNumItem = itemsModel.getInvoiceNum(invoices.get(invoicesTableRowSelected)); // InvoiceNum from itemsModel

                    if(invoNumItem == num){
                            try {
                                InvoiceHeader invoice = invoices.get(i);
                                invoice.setInvoiceLines(itemsModel.itemList);
                                invoices.set(i, invoice);
                                invoicesModel.invoiceList.set(i, invoice);

                                if(invoiceDate.getText().isEmpty() || customerName.getText().isEmpty()) {
                                    System.out.println("Date ot Customer name is empty!");
                                }
                                else {
                                    invoicesModel.setValueAt(invoicesModel.invoiceList.get(i).getInvoiceNum(), i, 0);
                                    invoicesModel.setValueAt(invoiceDate.getText(), i, 1);
                                    invoicesModel.setValueAt(customerName.getText(), i, 2);
                                }
                                invoicesModel.fireTableDataChanged();
                            }catch (Exception ex){
                                System.out.println("error");
                            }
                    }
                    else {
                        invoicesModel.setValueAt(invoicesModel.invoiceList.get(i).getInvoiceNum(), i, 0);
                        invoicesModel.setValueAt(invoicesModel.invoiceList.get(i).getInvoiceDate(), i, 1);
                        invoicesModel.setValueAt(invoicesModel.invoiceList.get(i).getCustomerName(), i, 2);
                        invoicesModel.setValueAt(invoicesModel.invoiceList.get(i).getInvoiceLines(num), i, 3);
                    }
                }
                InvoiceController.saveFile(invoices, this);
                break;
            case "createItem":
                String date = invoiceDate.getText();
                String name = customerName.getText();
                if(!date.isEmpty() && !name.isEmpty()) {
                    int invoiceNumber = invoices.get(invoicesTableRowSelected).getInvoiceNum();
                    InvoiceController.createItem(itemsModel, invoiceNumber);

                    invoicesModel.setValueAt(invoiceNo.getText(), invoicesTableRowSelected,0);
                    invoicesModel.setValueAt(invoiceDate.getText(), invoicesTableRowSelected, 1);
                    invoicesModel.setValueAt(customerName.getText(), invoicesTableRowSelected, 2);

                    invoiceTotal.setText(String.valueOf(InvoiceHeader.getInvoiceTotal(items)));
//                    invoicesModel.fireTableStructureChanged();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Customer Name or Invoice Date is not set. Try again!",
                            "INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case "deleteItem":
                ret = JOptionPane.showConfirmDialog(null, "Are you sure, you want to delete this item?",
                        "Confirmation MESSAGE",  JOptionPane.OK_CANCEL_OPTION);
                if(ret != JOptionPane.OK_OPTION)
                    break;

                int invoNum = invoicesModel.invoiceList.get(invoicesTableRowSelected).getInvoiceNum();
                InvoiceController.deleteItem(invoNum, itemSelected, itemsModel);
                createInvoiceItemsTable(items);
                break;
            case "createInvoice":
                //clear selection from invoiceTable
                if(invoicesTable.isRowSelected(invoicesTableRowSelected)) {
                    invoicesTable.clearSelection();
                }
                int newInvoiceNo = (int)invoicesModel.getValueAt(invoicesModel.invoiceList.size()-1,0) +1;
                InvoiceController.createInvoice(invoicesModel, newInvoiceNo);
                invoiceNo.setText(String.valueOf(newInvoiceNo));
                customerName.setText("");
                invoiceDate.setText("");
                invoiceTotal.setText("0.00");

                itemsModel  =
                        new InvoiceLineModel(emptyItems, invoiceItemsHeaders);
                if(invoiceItems == null)
                    invoiceItems = new JTable();
                invoiceItems.setModel(itemsModel);

                invoicesModel.fireTableStructureChanged();
                invoicesTable.setModel(invoicesModel);
                break;
            case "deleteInvoice":
                ret = JOptionPane.showConfirmDialog(null, "Are you sure, you want to delete this invoice?",
                        "Confirmation MESSAGE",  JOptionPane.OK_CANCEL_OPTION);
                if(ret != JOptionPane.OK_OPTION)
                    break;

                InvoiceController.deleteInvoice(invoicesTableRowSelected, invoicesModel);

                invoiceNo.setText("");
                customerName.setText("");
                invoiceDate.setText("");
                invoiceTotal.setText("0.00");

                ArrayList<InvoiceLine> emptyItems2 = new ArrayList<>();
                itemsModel  =
                        new InvoiceLineModel(emptyItems2, invoiceItemsHeaders);
                if(invoiceItems == null)
                    invoiceItems = new JTable();
                invoiceItems.setModel(itemsModel);
                break;
        }
    }

    public JTable createInvoiceHeaderTable(ArrayList<InvoiceHeader> invoices){
        invoicesTable = new JTable();
        invoicesModel =new InvoiceHeaderModel(invoices, invoicesTableHeaders);
        invoicesTable.setModel(invoicesModel);
        invoicesTable.setCellSelectionEnabled(true);
        invoicesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        invoiceScrollPane = new JScrollPane();
        invoiceScrollPane.setBounds(10, 79, 300, 500);
        invoiceScrollPane.setViewportView(invoicesTable);

        //Add Selection Listener to Invoice Table
        invoicesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                initializeItemsTable();
            }
        });
        return invoicesTable;
    }
    public void createInvoiceItemsTable(ArrayList<InvoiceLine> items){
        invoiceItems = new JTable();

        if(items == null)
            items = new ArrayList<>();
        itemsModel  = new InvoiceLineModel(items, invoiceItemsHeaders);

        invoiceItems.setModel(itemsModel);
        invoiceItems.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                itemSelected = invoiceItems.getSelectedRow();
                if (itemSelected < 0)
                    itemSelected = itemsModel.itemList.size() - 1;
            }
        });

        invoiceItems.setCellSelectionEnabled(true);
        invoiceItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemScrollPane.setViewportView(invoiceItems);
    }

    public void initializeItemsTable(){
        items = new ArrayList<>();
        invoicesTableRowSelected = invoicesTable.getSelectedRow();
        if(invoicesTableRowSelected < 0) {
//            JOptionPane.showMessageDialog(null, "Select the invoice to add an item to.",
//                        "INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);

            return;
        }

        if(invoicesTableRowSelected < invoices.size()) {
            try {
                invoiceNo.setText(String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,0)));
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            items = invoices.get(invoicesTableRowSelected).getInvoiceLines(invoices.get(invoicesTableRowSelected).getInvoiceNum());

            createInvoiceItemsTable(items);
            invoiceDate.setText(String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,1)));
            customerName.setText(String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,2)));
            invoiceTotal.setText(String.valueOf(InvoiceHeader.getInvoiceTotal(items)));
        }
    }
}
