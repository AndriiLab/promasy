/**
 * This class makes a base for interface of Promasy. It instantiates all parent frames
 * and dialog boxes.
 */

package main.java.gui;

import main.java.gui.amunits.AmUnitsDialog;
import main.java.gui.bids.BidsListPanel;
import main.java.gui.bids.reports.ReportParametersDialog;
import main.java.gui.conset.ConSetDialog;
import main.java.gui.cpv.CpvDialog;
import main.java.gui.empedit.EditEmployeeDialog;
import main.java.gui.finance.FinancePanel;
import main.java.gui.instedit.OrganizationDialog;
import main.java.gui.login.LoginDialog;
import main.java.gui.prodsupl.ProducerDialog;
import main.java.gui.prodsupl.SupplierDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private ReportParametersDialog reportParametersDialog;
    private MainFrameMenuListener listener;

    public MainFrame() {
        // Setting name of the window and its parameters
        super(Labels.getProperty("mainFrameSuper"));
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // set location relative to the screen center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        //initializing toolbar, login and connection settings windows, logger
        toolbar = new Toolbar();
        loginDialog = new LoginDialog(this);
        conSettDialog = new ConSetDialog(this);
        loggerDialog = new LoggerDialog(this);
        statusPanel = new StatusPanel(this);

        //initializing other common windows
        amUnitsDialog = new AmUnitsDialog(this);
        producerDialog = new ProducerDialog(this);
        supplierDialog = new SupplierDialog(this);
        infoDialog = new InfoDialog(this);
        cpvDialog = new CpvDialog(this);
    }

    public void initialize(int roleId, long departmentId){
        //setting layout
        setLayout(new BorderLayout());

        //TODO constructor here
        switch (roleId){
            case 7000: // 'Користувач'
                bidsListPanel = new BidsListPanel(this, departmentId);
                financePanel = new FinancePanel(this, departmentId); // not needed
//                add(bidsListPanel, BorderLayout.CENTER);
                setTabPane();
                break;
            case 6000: // 'Матеріально-відповідальна особа'
                bidsListPanel = new BidsListPanel(this, departmentId);
                financePanel = new FinancePanel(this, departmentId);
                setTabPane();
                break;
            case 5000: // 'Керівник підрозділу'
                bidsListPanel = new BidsListPanel(this, departmentId);
                financePanel = new FinancePanel(this, departmentId);
                setTabPane();
                break;
            case 3000: // 'Головний економіст'
                bidsListPanel = new BidsListPanel(this); // not needed
                financePanel = new FinancePanel(this);
                add(financePanel, BorderLayout.CENTER);
                break;
            case 4000: // 'Головний бухгалтер'
                bidsListPanel = new BidsListPanel(this); // not needed
                financePanel = new FinancePanel(this);
                add(financePanel, BorderLayout.CENTER);
                break;
            case 2000: // 'Заступник директора'
                bidsListPanel = new BidsListPanel(this);
                financePanel = new FinancePanel(this);
                setTabPane();
                break;
            case 1000: // 'Директор'
                bidsListPanel = new BidsListPanel(this);
                financePanel = new FinancePanel(this);
                setTabPane();
                break;
            case 900: // 'Адміністратор'
                bidsListPanel = new BidsListPanel(this);
                financePanel = new FinancePanel(this);
                setTabPane();
                break;
        }
        // initializing other windows and toolbars

        editOrgDialog = new OrganizationDialog(this);
        editEmpDialog = new EditEmployeeDialog(this);
        reportParametersDialog = new ReportParametersDialog(this);

        // creating MenuBar
        setJMenuBar(createMenuBar());

        // setting layout and formating frames on mainframe
        add(toolbar, BorderLayout.PAGE_START);
        add(statusPanel, BorderLayout.SOUTH);

        statusPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loggerDialog.setVisible(true);
            }
        });
    }

    private void setTabPane(){
        tabPane = new JTabbedPane();
        tabPane.addTab(Labels.getProperty("bids"), bidsListPanel);
        tabPane.addTab(Labels.getProperty("finances"), financePanel);
        add(tabPane, BorderLayout.CENTER);
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

        printItem.addActionListener(e -> {
            if(listener != null){
                listener.printEventOccurred();
            }
        });

        editOrgItem.addActionListener(e -> editOrgDialog.setVisible(true));

        editEmpItem.addActionListener(e -> editEmpDialog.setVisible(true));

        editAmUnitsItem.addActionListener(e -> amUnitsDialog.setVisible(true));

        editProdItem.addActionListener(e -> producerDialog.setVisible(true));

        editSuplItem.addActionListener(e -> supplierDialog.setVisible(true));

        conSettItem.addActionListener(e -> conSettDialog.setVisible(true));

        infoItem.addActionListener(e -> infoDialog.setVisible(true));

        return menuBar;
    }

    public void setMainFrameMenuListener(MainFrameMenuListener listener){
        this.listener = listener;
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

	public ReportParametersDialog getReportParametersDialog() {
		return reportParametersDialog;
	}
}
