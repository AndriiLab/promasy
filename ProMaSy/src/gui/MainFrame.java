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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import controller.Controller;
import gui.conset.ConSetDialog;
import gui.conset.ConSetEvent;
import gui.conset.ConSetListener;
import gui.cpv.CpvReqEvent;
import gui.cpv.CpvSearchListener;
import gui.empedit.CreateEmployeeDialog;
import gui.empedit.EditEmployeeDialog;
import gui.empedit.EditEmployeeDialogListener;
import gui.empedit.CreateEmployeeDialogListener;
import gui.empedit.EmployeeEvent;
import gui.instedit.OrganizationDialog;
import gui.instedit.OrganizationDialogListener;
import gui.login.LoginAttemptEvent;
import gui.login.LoginDialog;
import gui.login.LoginListener;
import gui.cpv.CpvPanel;
import model.DepartmentModel;
import model.InstituteModel;
import model.LoginData;
import model.RoleModel;
import model.SubdepartmentModel;

public class MainFrame extends JFrame {

	private LoginDialog loginDialog;
	private Toolbar toolbar;
	private ConSetDialog conSettDialog;
	private OrganizationDialog editOrgDialog;
	private EditEmployeeDialog editEmpDialog;
	private CreateEmployeeDialog addEmpDialog;
	private Controller controller;
	private InfoDialog infoDialog;
	private Preferences prefs;
	private CpvPanel cpvPanel;
	private JTabbedPane tabPane;
	private StatusPanel statusPanel;
	private List<InstituteModel> instModelList;

	public MainFrame() {
		// Setting name of the window and its parameters
		super("Procurement Management System - Система Керування Закупівлями");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(false);
		
		// set location relative to the screen center
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		// adding implementation for closing operation via X-button on window 
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDialog();
			}
		});
		
		//initializing variables
		loginDialog = new LoginDialog(this);
		toolbar = new Toolbar();
		conSettDialog = new ConSetDialog(this);
		editOrgDialog = new OrganizationDialog(this);
		editEmpDialog = new EditEmployeeDialog(this);
		addEmpDialog = new CreateEmployeeDialog(this);
		infoDialog = new InfoDialog(this);
		// Preferences class used for storage of connection settings to DB
		prefs = Preferences.userRoot().node("db_con");
		controller = new Controller();
		statusPanel = new StatusPanel(this);
		cpvPanel = new CpvPanel();
		tabPane = new JTabbedPane();
		tabPane.addTab("Вибір CPV", cpvPanel);
		
		// trying to get connection settings form prefs object, 
		// if it doesn't exist defaults will be used
		String server = prefs.get("server", "localhost");
		String database = prefs.get("database", "inst2016test");
		String schema = prefs.get("schema", "inst_db");
		String user = prefs.get("user", "postcoder");
		String password = prefs.get("password", "codetest");
		int portNumber = prefs.getInt("port", 5432);
		
		// loginDialog appears first to MainFrame
		loginDialog.setVisible(true);
		loginDialog.setLoginListener(new LoginListener() {
			public void loginAttemptOccured(LoginAttemptEvent ev) {
				LoginData.INSTANCE.setLogin(ev.getUsername());
				LoginData.INSTANCE.setPassword(ev.getPassword());
				if(checkLogin(LoginData.INSTANCE)){
					// if login was successful setting MainFrame visible
					setVisible(true);
					loginDialog.setVisible(false);
					statusPanel.setCurrentUser(LoginData.INSTANCE.getShortName());
				} else if (!checkLogin(LoginData.INSTANCE)){
					// if login wasn't successful showing error dialog
					JOptionPane.showMessageDialog(MainFrame.this, 
							"Введені дані не розпізнані.\nПеревірте коректність введених даних.", "Помилка входу",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			
			// if user changed his mind to login call close method
			public void loginCancelled(EventObject ev) {
				close();
			}
		});
		
		// if user entered new settings for connection to DB - putting them to Prefs
		conSettDialog.setPrefsLitener(new ConSetListener() {
			public void preferencesSetOccured(ConSetEvent e) {
				prefs.put("server", e.getServer());
				prefs.put("host", e.getDatabase());
				prefs.put("schema", e.getSchema());
				prefs.putInt("port", e.getPortNumber());
				prefs.put("user", e.getUser());
				prefs.put("password", e.getPassword());
				controller.setConnectionSettings(e.getServer(), e.getDatabase(),
						e.getSchema(), Integer.toString(e.getPortNumber()), e.getUser(), e.getPassword());
				// trying to connect with new settings
				if (controller != null){
					controller.disconnect();
					connect();
				}
			}
		});
		// loading used for connection values to ConnectionSetingsDialog
		conSettDialog.setDefaults(server, database, schema, portNumber, user, password);
		// loading used for connection values to Controller
		controller.setConnectionSettings(server, database, schema,  
				Integer.toString(portNumber), user, password);

		//connecting with DB and loading default data to frames and dialogs
		connect();
		getCpvRequest("", true);
		getRolesRequest();
		getInstRequest();
		getEmployees();
		cpvPanel.setData(controller.getCpvList());
		List<RoleModel> rolesModelList = controller.getRolesList();
		editEmpDialog.setRolesData(rolesModelList);
		addEmpDialog.setRolesData(rolesModelList);
		instModelList = controller.getInstList();
		editOrgDialog.setInstData(instModelList);
		editEmpDialog.setInstData(instModelList);
		addEmpDialog.setInstData(instModelList);
		editEmpDialog.setEmpTableData(controller.getEmpList());

		// creating Menubar
		setJMenuBar(createMenuBar());
		
		//setting listeners to frames and dialogs
		cpvPanel.setCpvListener(new CpvSearchListener() {
			public void cpvEventOccured(CpvReqEvent ev) {
				getCpvRequest(ev.getCpvRequest(), ev.isSameLvlShow());
				cpvPanel.refresh();
			}
		});

		toolbar.setToolbarListener(new ToolbarListener() {
			public void testConEventOccured() {
				System.out.println("test");
			}

		});
		
		editEmpDialog.setEmployeeDialogListener(new EditEmployeeDialogListener() {
			
			public void instSelelectionEventOccured(long instId) {
				getDepRequest(instId);
				editEmpDialog.setDepData(controller.getDepList());
			}
			
			public void editPersonEventOccured(EmployeeEvent ev) {
				editEmployee(ev);
				
			}

			public void depSelelectionEventOccured(long depId) {
				getSubdepRequest(depId);
				editEmpDialog.setSubdepData(controller.getSubdepList());
				
			}

		});
		
		addEmpDialog.setEmployeeDialogListener(new CreateEmployeeDialogListener(){
			public void instSelelectionEventOccured(long instId) {
				getDepRequest(instId);
				addEmpDialog.setDepData(controller.getDepList());
			}

			public void depSelelectionEventOccured(long depId) {
				getSubdepRequest(depId);
				addEmpDialog.setSubdepData(controller.getSubdepList());
			}
			
			public void createPersonEventOccured(EmployeeEvent ev) {
				createEmployee(ev);
			}
		});
		
		editOrgDialog.setOrganizationDialogListener(new OrganizationDialogListener() {
			
			public void instSelelectionEventOccured(long instId) {
				getDepRequest(instId);
				editOrgDialog.setDepData(controller.getDepList());
			}

			public void depSelelectionEventOccured(long depId) {
				getSubdepRequest(depId);
				editOrgDialog.setSubdepData(controller.getSubdepList());
			}

			public void createInstEventOccured(InstituteModel instModel) {
				createInstitute(instModel);
				getInstRequest();
				instModelList = controller.getInstList();
				editOrgDialog.setInstData(instModelList);
				editEmpDialog.setInstData(instModelList);
			}

			public void editInstEventOccured(InstituteModel instModel) {
				editInstitute(instModel);
				getInstRequest();
				instModelList = controller.getInstList();
				editOrgDialog.setInstData(instModelList);
				editEmpDialog.setInstData(instModelList);
			}

			public void deleteInstEventOccured(InstituteModel instModel) {
				deleteInstitute(instModel);
				getInstRequest();
				instModelList = controller.getInstList();
				editOrgDialog.setInstData(instModelList);
				editEmpDialog.setInstData(instModelList);
			}

			public void createDepEventOccured(DepartmentModel model) {
				createDepartment(model);
				getDepRequest(model.getInstId());
				editOrgDialog.setDepData(controller.getDepList());
			}

			public void editDepEventOccured(DepartmentModel model) {
				editDepartment(model);
				getDepRequest(model.getInstId());
				editOrgDialog.setDepData(controller.getDepList());
			}

			public void deleteDepEventOccured(DepartmentModel model) {
				deleteDepartment(model);
				getDepRequest(model.getInstId());
				editOrgDialog.setDepData(controller.getDepList());
			}

			public void createSubdepEventOccured(SubdepartmentModel model) {
				createSubdepartment(model);
				getSubdepRequest(model.getDepId());
				editOrgDialog.setSubdepData(controller.getSubdepList());
			}

			public void editSubdepEventOccured(SubdepartmentModel model) {
				editSubdepartment(model);
				getSubdepRequest(model.getDepId());
				editOrgDialog.setSubdepData(controller.getSubdepList());
			}

			public void deleteSubdepEventOccured(SubdepartmentModel model) {
				deleteSubdepartment(model);
				getSubdepRequest(model.getDepId());
				editOrgDialog.setSubdepData(controller.getSubdepList());
			}
		});
		
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
				closeDialog();
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
	
	/*
	 * All methods below are using for sending and retrieving data from 
	 * Controller and handling exceptions
	 */
	private void connect() {
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Немає з'єднання з базою даних", "Database Connection Error",
					JOptionPane.ERROR_MESSAGE);
			// if can't connect - call ConnectionSettingsDialog
			conSettDialog.setVisible(true);
		}
	}

	private void getCpvRequest(String cpvRequest, boolean sameLvlShow) {
		try {
			controller.loadCpv(cpvRequest, sameLvlShow);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void getRolesRequest() {
		try {
			controller.loadRoles();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getEmployees() {
		try {
			controller.loadEmployees();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getInstRequest() {
		try {
			controller.loadInstitute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getDepRequest(long instId){
		try {
			controller.loadDepartment(instId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getSubdepRequest(long depId){
		try {
			controller.loadSubdepartment(depId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createEmployee (EmployeeEvent ev){
		try {
			controller.createEmployee(ev.getEmployeeModel());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void editEmployee(EmployeeEvent ev) {
		try {
			controller.editEmployee(ev.getEmployeeModel());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean checkLogin(LoginData loginData) {
		try {
			return controller.checkLogin(loginData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private void createInstitute(InstituteModel instModel) {
		try {
			controller.createInstitute(instModel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void editInstitute(InstituteModel instModel) {
		try {
			controller.editInstitute(instModel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void deleteInstitute(InstituteModel instModel) {
		try {
			controller.deleteInstitute(instModel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createDepartment(DepartmentModel model) {
		try {
			controller.createDepartment(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void editDepartment(DepartmentModel model) {
		try {
			controller.editDepartment(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void deleteDepartment(DepartmentModel model) {
		try {
			controller.deleteDepartment(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void createSubdepartment(SubdepartmentModel model) {
		try {
			controller.createSubdepartment(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void deleteSubdepartment(SubdepartmentModel model) {
		try {
			controller.deleteSubdepartment(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected void editSubdepartment(SubdepartmentModel model) {
		try {
			controller.editSubdepartment(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void closeDialog() {
		int action = JOptionPane.showConfirmDialog(MainFrame.this, "Ви дійсно хочете вийти з програми?",
				"Підтвердіть вихід", JOptionPane.OK_CANCEL_OPTION);
		if (action == JOptionPane.OK_OPTION) {
			close();
		}
	}
	
	/*
	 * This method closes MainFrame and do some cleanup
	 */
	private void close(){
		controller.disconnect();
		dispose();
		System.gc();
	}
}
