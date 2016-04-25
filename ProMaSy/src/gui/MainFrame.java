/**
 * This class makes a base for interface of Promasy. It instantiates all parent frames
 * and dialog boxes.
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import gui.conset.ConSetDialog;
import gui.empedit.CreateEmployeeDialog;
import gui.empedit.EditEmployeeDialog;
import gui.instedit.OrganizationDialog;
import gui.login.LoginDialog;
import gui.cpv.CpvPanel;

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


	public MainFrame() {
        // Setting name of the window and its parameters
		super(LabelsLocale.getProperty("mainFrameSuper"));
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
		infoDialog = new InfoDialog(this);
		statusPanel = new StatusPanel(this);
		cpvPanel = new CpvPanel();
		tabPane = new JTabbedPane();
		tabPane.addTab(LabelsLocale.getProperty("cpvPanelTab"), cpvPanel);

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

		JMenu fileMenu = new JMenu(LabelsLocale.getProperty("fileMenuLabel"));
		JMenuItem printItem = new JMenuItem(LabelsLocale.getProperty("printItemLabel"));
		exitItem = new JMenuItem(LabelsLocale.getProperty("exitItemLabel"));
		fileMenu.add(printItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		JMenu editMenu = new JMenu(LabelsLocale.getProperty("editMenuLabel"));
		JMenuItem editOrgItem = new JMenuItem(LabelsLocale.getProperty("editOrgItemLabel"));
		JMenuItem editEmpItem = new JMenuItem(LabelsLocale.getProperty("editEmpItemLabel"));
		JMenuItem addEmpItem = new JMenuItem(LabelsLocale.getProperty("addEmpItemLabel"));
		editMenu.add(editOrgItem);
		editMenu.add(editEmpItem);
		editMenu.add(addEmpItem);

		JMenu settingsMenu = new JMenu(LabelsLocale.getProperty("settingsMenuLabel"));
		JMenuItem conSettItem = new JMenuItem(LabelsLocale.getProperty("conSettItemLabel"));
		settingsMenu.add(conSettItem);

		JMenu helpMenu = new JMenu(LabelsLocale.getProperty("helpMenuLabel"));
		JMenuItem infoItem = new JMenuItem(LabelsLocale.getProperty("infoItemLabel"));
		helpMenu.add(infoItem);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(settingsMenu);
		menuBar.add(helpMenu);

		editOrgItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editOrgDialog.setVisible(true);
			}
		});

		editEmpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editEmpDialog.setVisible(true);
			}
		});

		addEmpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEmpDialog.setVisible(true);
			}
		});

		conSettItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conSettDialog.setVisible(true);
			}
		});

		infoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoDialog.setVisible(true);
			}
		});

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

}
