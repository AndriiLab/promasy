/**
 * This class makes a base for interface of Promasy. It instantiates all parent frames
 * and dialog boxes. This class also sends commands to Controller class.
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

	public MainFrame() {
		// Setting name of the window and its parameters
		super("Procurement Management System - Система Керування Закупівлями");
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
		tabPane.addTab("Вибір CPV", cpvPanel);

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

		JMenu fileMenu = new JMenu("Файл");
		JMenuItem printItem = new JMenuItem("Друк");
		JMenuItem exitItem = new JMenuItem("Вихід");
		fileMenu.add(printItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		JMenu editMenu = new JMenu("Редагувати");
		JMenuItem editOrgItem = new JMenuItem("організації\\відділи...");
		JMenuItem editEmpItem = new JMenuItem("користувачів...");
		JMenuItem addEmpItem = new JMenuItem("Створити користувача...");
		editMenu.add(editOrgItem);
		editMenu.add(editEmpItem);
		editMenu.add(addEmpItem);

		JMenu settingsMenu = new JMenu("Налаштування");
		JMenuItem conSettItem = new JMenuItem("Параметри з'єднання...");
		settingsMenu.add(conSettItem);

		JMenu helpMenu = new JMenu("Допомога");
		JMenuItem infoItem = new JMenuItem("Про програму...");
		helpMenu.add(infoItem);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(settingsMenu);
		menuBar.add(helpMenu);

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
//				closeDialog();
			}
		});

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

}
