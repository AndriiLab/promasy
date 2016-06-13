/**
 * This class makes a base for interface of Promasy. It instantiates all parent frames
 * and dialog boxes.
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import gui.amunits.AmUnitsDialog;
import gui.bids.BidsListPanel;
import gui.conset.ConSetDialog;
import gui.cpv.CpvDialog;
import gui.empedit.EditEmployeeDialog;
import gui.finance.FinancePanel;
import gui.instedit.OrganizationDialog;
import gui.login.LoginDialog;
import gui.prodsupl.ProducerDialog;
import gui.prodsupl.SupplierDialog;

public class MainFrame extends JFrame {

    private LoginDialog loginDialog;
    private Toolbar toolbar;
    private ConSetDialog conSettDialog;
    private OrganizationDialog editOrgDialog;
    private EditEmployeeDialog editEmpDialog;
    private InfoDialog infoDialog;
    private CpvDialog cpvDialog;
    private JTabbedPane tabPane;
    private StatusPanel statusPanel;
    private JMenuItem exitItem;
    private AmUnitsDialog amUnitsDialog;
    private ProducerDialog producerDialog;
    private SupplierDialog supplierDialog;
    private FinancePanel financePanel;
    private BidsListPanel bidsListPanel;
    private LoggerDialog loggerDialog;

    public MainFrame() {
        // Setting name of the window and its parameters
        super(Labels.getProperty("mainFrameSuper"));
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // set location relative to the screen center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        //initializing variables
        loginDialog = new LoginDialog(this);
        toolbar = new Toolbar();
        conSettDialog = new ConSetDialog(this);
        editOrgDialog = new OrganizationDialog(this);
        editEmpDialog = new EditEmployeeDialog(this);
        amUnitsDialog = new AmUnitsDialog(this);
        producerDialog = new ProducerDialog(this);
        supplierDialog = new SupplierDialog(this);
        infoDialog = new InfoDialog(this);
        statusPanel = new StatusPanel(this);
        cpvDialog = new CpvDialog(this);
        financePanel = new FinancePanel(this);
        tabPane = new JTabbedPane();
        bidsListPanel = new BidsListPanel(this);
        loggerDialog = new LoggerDialog(this);

        tabPane.addTab(Labels.getProperty("bids"), bidsListPanel);
        tabPane.addTab(Labels.getProperty("finances"), financePanel);

        // creating Menubar
        setJMenuBar(createMenuBar());

        // setting layout and formating frames on mainframe
        setLayout(new BorderLayout());
        add(toolbar, BorderLayout.PAGE_START);
        add(tabPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        statusPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loggerDialog.setVisible(true);
            }
        });
    }


    /*
     * This method generates menubar
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu(Labels.getProperty("file"));
        JMenuItem printItem = new JMenuItem(Labels.getProperty("print"));
        exitItem = new JMenuItem(Labels.getProperty("exit"));
        fileMenu.add(printItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu(Labels.getProperty("edit"));
        JMenuItem editOrgItem = new JMenuItem(Labels.withThreeDots("editOrganizationsDepartments"));
        JMenuItem editEmpItem = new JMenuItem(Labels.withThreeDots("editEmployee"));
        JMenuItem editAmUnitsItem = new JMenuItem(Labels.withThreeDots("amUnitsDialogSuper"));
        JMenuItem editProdItem = new JMenuItem(Labels.withThreeDots("prodDialogSuper"));
        JMenuItem editSuplItem = new JMenuItem(Labels.withThreeDots("suplDialogSuper"));
        editMenu.add(editOrgItem);
        editMenu.add(editEmpItem);
        editMenu.add(editAmUnitsItem);
        editMenu.add(editProdItem);
        editMenu.add(editSuplItem);

        JMenu settingsMenu = new JMenu(Labels.getProperty("settings"));
        JMenuItem conSettItem = new JMenuItem(Labels.withThreeDots("ConnectionWithDBSettings"));
        settingsMenu.add(conSettItem);

        JMenu helpMenu = new JMenu(Labels.getProperty("help"));
        JMenuItem infoItem = new JMenuItem(Labels.withThreeDots("aboutSoftware"));
        helpMenu.add(infoItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        editOrgItem.addActionListener(e -> editOrgDialog.setVisible(true));

        editEmpItem.addActionListener(e -> editEmpDialog.setVisible(true));

        editAmUnitsItem.addActionListener(e -> amUnitsDialog.setVisible(true));

        editProdItem.addActionListener(e -> producerDialog.setVisible(true));

        editSuplItem.addActionListener(e -> supplierDialog.setVisible(true));

        conSettItem.addActionListener(e -> conSettDialog.setVisible(true));

        infoItem.addActionListener(e -> infoDialog.setVisible(true));

        return menuBar;
    }

    public LoginDialog getLoginDialog() {
        return loginDialog;
    }

    public StatusPanel getStatusPanel() {
        return statusPanel;
    }

    public ConSetDialog getConSettDialog() {
        return conSettDialog;
    }

    public OrganizationDialog getEditOrgDialog() {
        return editOrgDialog;
    }

    public EditEmployeeDialog getEditEmpDialog() {
        return editEmpDialog;
    }

    public CpvDialog getCpvDialog() {
        return cpvDialog;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public InfoDialog getInfoDialog() {
        return infoDialog;
    }

    public JTabbedPane getTabPane() {
        return tabPane;
    }

    public JMenuItem getExitItem() {
        return exitItem;
    }

    public AmUnitsDialog getAmUnitsDialog() {
        return amUnitsDialog;
    }

    public FinancePanel getFinancePanel() {
        return financePanel;
    }

    public BidsListPanel getBidsListPanel() {
        return bidsListPanel;
    }

    public ProducerDialog getProducerDialog() {
        return producerDialog;
    }

    public SupplierDialog getSupplierDialog() {
        return supplierDialog;
    }

    public LoggerDialog getLoggerDialog() {
        return loggerDialog;
    }
}
