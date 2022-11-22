package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class salesInvoiceUI extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadFile;
    private JMenuItem saveFile;

    private JPanel leftSection, rightSection;
    private Container form;
    private JTable invoicesTable;
    private int invoicesTableRowSelected;
    private JTable invoiceItems;
    private JButton createInvoice, deleteInvoice;
    private JButton saveChanges, cancelChanges;
    private JTextField invoiceDate,customerName, invoiceNo,invoiceTotal;

    public salesInvoiceUI (String title) {
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
        add(leftSection);
        //End leftSection

        //Start rightSection
        rightSection = new JPanel();

        add(rightSection);
        //End rightSection

        setSize(1000,750);
        setLocation(200,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
