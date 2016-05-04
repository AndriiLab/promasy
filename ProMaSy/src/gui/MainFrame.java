/**
 * This class makes a base for interface of Promasy. It instantiates all parent frames
 * and dialog boxes.
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import gui.amunits.AmUnitsDialog;
import gui.conset.ConSetDialog;
import gui.empedit.CreateEmployeeDialog;
import gui.empedit.EditEmployeeDialog;
import gui.instedit.OrganizationDialog;
import gui.login.LoginDialog;
import gui.cpv.CpvPanel;
import gui.prodsupl.ProdSuplDialog;

public class MainFrame extends JFrame {

	private LoginDialog loginDialog;
	private Toolbar toolbar;
	private ConSetDialog conSettDialog;
	private OrganizationDialog editOrgDialog;
	private EditEmployeeDialog editEmpDialog;
	private CreateEmployeeDialog addEmpDialog;
	private InfoDialog infoDialog;
	private CpvPanel cpvPanel;
	private JTabbedPane tabPane;
	private StatusPanel statusPanel;
	private JMenuItem exitItem;
	private AmUnitsDialog amUnitsDialog;
	private ProdSuplDialog prodSuplDialog;


	public MainFrame() {
        // Setting name of the window and its parameters
		super(Labels.getProperty("mainFrameSuper"));
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// set location relative to the screen center
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		//initializing variables
		loginDialog = new LoginDialog(this);
		toolbar = new Toolbar();
		conSettDialog = new ConSetDialog(this);
		editOrgDialog = new OrganizationDialog(this);
		editEmpDialog = new EditEmployeeDialog(this);
		addEmpDialog = new CreateEmployeeDialog(this);
		amUnitsDialog = new AmUnitsDialog(this);
		prodSuplDialog = new ProdSuplDialog(this);
		infoDialog = new InfoDialog(this);
		statusPanel = new StatusPanel(this);
		cpvPanel = new CpvPanel();
		tabPane = new JTabbedPane();
		tabPane.addTab(Labels.getProperty("cpvPanelTab"), cpvPanel);

		// creating Menubar
		setJMenuBar(createMenuBar());

		// setting layout and formating frames on mainframe
		setLayout(new BorderLayout());
		add(toolbar, BorderLayout.PAGE_START);
		add(tabPane, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);
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
		JMenuItem editOrgItem = new JMenuItem(Labels.getProperty("editOrganizationsDepartmnets"));
		JMenuItem editEmpItem = new JMenuItem(Labels.getProperty("editEmployees"));
		JMenuItem addEmpItem = new JMenuItem(Labels.getProperty("addEmployees"));
		JMenuItem editAmUnitsItem = new JMenuItem(Labels.getProperty("amUnitsDialogSuper")+"...");
        JMenuItem editProdSuplItem = new JMenuItem(Labels.getProperty("prodSuplDialogSuper")+"...");
		editMenu.add(editOrgItem);
		editMenu.add(editEmpItem);
		editMenu.add(addEmpItem);
        editMenu.add(editAmUnitsItem);
        editMenu.add(editProdSuplItem);

		JMenu settingsMenu = new JMenu(Labels.getProperty("settings"));
		JMenuItem conSettItem = new JMenuItem(Labels.getProperty("ConnectionWithDBSettings")+"...");
		settingsMenu.add(conSettItem);

		JMenu helpMenu = new JMenu(Labels.getProperty("help"));
		JMenuItem infoItem = new JMenuItem(Labels.getProperty("aboutSoftware")+"...");
		helpMenu.add(infoItem);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(settingsMenu);
		menuBar.add(helpMenu);

		editOrgItem.addActionListener(e -> editOrgDialog.setVisible(true));

		editEmpItem.addActionListener(e -> editEmpDialog.setVisible(true));

		addEmpItem.addActionListener(e -> addEmpDialog.setVisible(true));

        editAmUnitsItem.addActionListener(e -> amUnitsDialog.setVisible(true));

        editProdSuplItem.addActionListener(e -> prodSuplDialog.setVisible(true));

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
	public CreateEmployeeDialog getAddEmpDialog() {
		return addEmpDialog;
	}
	public CpvPanel getCpvPanel() {
		return cpvPanel;
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
    public ProdSuplDialog getProdSuplDialog() {
        return prodSuplDialog;
    }
}
