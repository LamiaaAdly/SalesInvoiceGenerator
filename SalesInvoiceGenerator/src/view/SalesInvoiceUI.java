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
    private String inveoicePath, invoiceDirectory, fileNameOfInvoices;
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

        String[] paths = FileOperations.getPaths(this);
        inveoicePath = paths[0];
        invoiceDirectory = paths[1];
        fileNameOfInvoices = paths[2];

        invoices = FileOperations.readFile(inveoicePath);
        FileOperations.test(invoices);

        invoicesTable = new JTable();
        invoicesModel =new InvoiceHeaderModel(invoices, invoicesTableHeaders);
        invoicesTable.setModel(invoicesModel);
        invoicesTable.setCellSelectionEnabled(true);
        invoicesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        invoiceScrollPane = new JScrollPane();
        invoiceScrollPane.setBounds(10, 79, 300, 500);
        invoiceScrollPane.setViewportView(invoicesTable);

        //Add Selection Listener to Invoice Table
//        invoicesTableRowSelected =0;
//        itializeItemsTable();
        invoicesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                itializeItemsTable();
            }
        });

//        DefaultTableModel ivModel = (DefaultTableModel) invoicesTable.getModel();
//        TableColumn ivTotal = new TableColumn(ivModel.getColumnCount());
//        ivTotal.setHeaderValue("Total");
//        invoicesTable.addColumn(ivTotal);
//        ivModel.addColumn("Total");
//        invoicesTable.moveColumn(invoicesTable.getColumnCount()-1, 3);

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
        InvoiceController controller = new InvoiceController(invoices, invoicesModel,items,itemsModel,this);

        switch (e.getActionCommand()) {
            case "L":
                InvoiceController.loadFile(invoicesTable);
                break;
            case "S":
                InvoiceController.saveFile();
                break;
            case "createItem":
                InvoiceController.createItem(invoicesTableRowSelected);
                break;
            case "deleteItem":
                InvoiceController.deleteItem(itemSelected);
                createInvoiceItemsTable(items);
                break;
            case "createInvoice":
                if(invoicesTable.isRowSelected(invoicesTableRowSelected)) {
                    invoicesTable.clearSelection();
                }
                InvoiceController.createInvoice(invoicesTable,invoiceItems,invoicesTableRowSelected);
                int newInvoiceNo = (int)invoicesModel.getValueAt(invoicesModel.invoiceList.size()-1,0) +1;

                invoiceNo.setText(String.valueOf(newInvoiceNo));
                customerName.setText(" ");
                invoiceDate.setText(" ");
                invoiceTotal.setText(" ");

                ArrayList<InvoiceLine> emptyItems = new ArrayList<>();
                itemsModel  =
                        new InvoiceLineModel(emptyItems, invoiceItemsHeaders);
                if(invoiceItems == null)
                    invoiceItems = new JTable();
                invoiceItems.setModel(itemsModel);


                break;
            case "deleteInvoice":
                InvoiceController.deleteInvoice(invoicesTableRowSelected);
                break;
        }
    }

    public void createInvoiceItemsTable(ArrayList<InvoiceLine> items){
        invoiceItems = new JTable();
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

    public void itializeItemsTable(){
        items = new ArrayList<>();
        invoicesTableRowSelected = invoicesTable.getSelectedRow();
        if(invoicesTableRowSelected < 0)
//            invoicesTableRowSelected =0;
            invoicesTableRowSelected = invoicesModel.invoiceList.size()-1;
        if(invoicesTableRowSelected < invoices.size()) {
            try {
                invoiceNo.setText(String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,0)));
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            String brePath = invoiceDirectory+"\\" + fileNameOfInvoices.replace(".csv","");
            itemPath = brePath + "\\" + String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,0)) + ".csv";

            items = FileOperations.readInvoiceLineFile(itemPath);

            createInvoiceItemsTable(items);
            invoiceDate.setText(String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,1)));
            customerName.setText(String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,2)));
            invoiceTotal.setText(String.valueOf(InvoiceHeader.getInvoiceTotal(items)));
        }
    }
}
