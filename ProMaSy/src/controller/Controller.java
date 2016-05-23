/**
 * This class connects MainFrame and Database
 */
package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.EventObject;
import java.util.List;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import gui.amunits.AmUnitsDialogListener;
import gui.Labels;
import gui.MainFrame;
import gui.finance.FinancePanelListener;
import gui.prodsupl.ProdSuplDialogListener;
import gui.empedit.CreateEmployeeDialogListener;
import gui.empedit.EditEmployeeDialogListener;
import gui.empedit.EmployeeEvent;
import gui.instedit.OrganizationDialogListener;
import gui.login.LoginAttemptEvent;
import gui.login.LoginListener;
import model.*;

public class Controller {

	private Properties conSet;
	CPVQueries cpv;
	RoleQueries roles;
	InstituteQueries institutes;
	DepartmentQueries departments;
	SubdepartmentQueries subdepartmens;
	EmployeeQueries employees;
	private MainFrame mainFrame;
	private Preferences prefs;
	private List<InstituteModel> instModelList;

	public Controller(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		mainFrame.setVisible(false);
		
		// adding implementation for closing operation via X-button on window 
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDialog();
			}
		});

		// loginDialog appears first to MainFrame
		mainFrame.getLoginDialog().setVisible(true);
		mainFrame.getLoginDialog().setLoginListener(new LoginListener() {
			public void loginAttemptOccurred(LoginAttemptEvent ev) {
				LoginData.INSTANCE.setLogin(ev.getUsername());
				LoginData.INSTANCE.setPassword(ev.getPassword());
				if(checkLogin()){
					// if login was successful setting MainFrame visible
					mainFrame.setVisible(true);
					mainFrame.getLoginDialog().setVisible(false);
					mainFrame.getStatusPanel().setCurrentUser(LoginData.INSTANCE.getShortName());
                    //post login requests to DB
                    //setting to FinancePanel departments data relative to login person
                    getDepRequest(LoginData.INSTANCE.getInstId());
                    mainFrame.getFinancePanel().setDepartmentBoxData(Database.DEPARTMENTS.getList());
				} else if (!checkLogin()){
					// if login wasn't successful showing error dialog
					JOptionPane.showMessageDialog(mainFrame, 
							"Введені дані не розпізнані.\nПеревірте коректність введених даних.", "Помилка входу",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			// if user changed his mind about login call close method
			public void loginCancelled(EventObject ev) {
				close();
			}
		});

		// Preferences class used for storage of connection settings to DB
		prefs = Preferences.userRoot().node("db_con");

		// trying to get connection settings form prefs object, 
		// if it doesn't exist defaults will be used
		String server = prefs.get("server", "localhost");
		String database = prefs.get("database", "inst2016test");
		String schema = prefs.get("schema", "inst_db");
		String user = prefs.get("user", "postcoder");
		String password = prefs.get("password", "codetest");
		int portNumber = prefs.getInt("port", 5432);

		// if user entered new settings for connection to DB - putting them to Prefs
		mainFrame.getConSettDialog().setPrefsLitener(e -> {
            prefs.put("server", e.getServer());
            prefs.put("host", e.getDatabase());
            prefs.put("schema", e.getSchema());
            prefs.putInt("port", e.getPortNumber());
            prefs.put("user", e.getUser());
            prefs.put("password", e.getPassword());
            setConnectionSettings(e.getServer(), e.getDatabase(),
                    e.getSchema(), Integer.toString(e.getPortNumber()), e.getUser(), e.getPassword());

            // trying to connect with new settings
            disconnect();
            connect();
        });
		// loading connection values to ConnectionSetingsDialog and Controller
		mainFrame.getConSettDialog().setDefaults(server, database, schema, 
				portNumber, user, password);
		setConnectionSettings(server, database, schema,  
				Integer.toString(portNumber), user, password);

		//connecting with DB and loading default data to frames and dialogs
		connect();
		getCpvRequest("", true);
		getRolesRequest();
		getInstRequest();
		getEmployees();
		getAmUnits();
		getProd();
        getSupl();
		getFinances();
		getDepartmentFinances(0);
		mainFrame.getCpvPanel().setData(Database.CPV.getList());
		List<RoleModel> rolesModelList = Database.ROLES.getList();
		mainFrame.getEditEmpDialog().setRolesData(rolesModelList);
		mainFrame.getAddEmpDialog().setRolesData(rolesModelList);
		instModelList = Database.INSTITUTES.getList();
		mainFrame.getEditOrgDialog().setInstData(instModelList);
		mainFrame.getEditEmpDialog().setInstData(instModelList);
		mainFrame.getAddEmpDialog().setInstData(instModelList);
        mainFrame.getEditEmpDialog().setEmpTableData(Database.EMPLOYEES.getList());
		mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
        mainFrame.getProdSuplDialog().setProdData(Database.PRODUCERS.getList());
        mainFrame.getProdSuplDialog().setSuplData(Database.SUPPLIERS.getList());
		mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
		mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());

		//setting listeners to frames and dialogs
		mainFrame.getCpvPanel().setCpvListener(ev -> {
            getCpvRequest(ev.getCpvRequest(), ev.isSameLvlShow());
            mainFrame.getCpvPanel().refresh();
        });

		mainFrame.getToolbar().setToolbarListener(() -> System.out.println("test"));

		mainFrame.getEditEmpDialog().setEmployeeDialogListener(new EditEmployeeDialogListener() {

			public void instSelectionEventOccurred(long instId) {
				getDepRequest(instId);
				mainFrame.getEditEmpDialog().setDepData(Database.DEPARTMENTS.getList());
			}

			public void editPersonEventOccurred(EmployeeEvent ev) {
				editEmployee(ev);
                mainFrame.getEditEmpDialog().setEmpTableData(Database.EMPLOYEES.getList());
                mainFrame.getEditEmpDialog().refresh();
			}

			public void depSelectionEventOccurred(long depId) {
				getSubdepRequest(depId);
				mainFrame.getEditEmpDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
			}

		});

		mainFrame.getAddEmpDialog().setEmployeeDialogListener(new CreateEmployeeDialogListener(){
			public void instSelectionEventOccurred(long instId) {
				getDepRequest(instId);
				mainFrame.getAddEmpDialog().setDepData(Database.DEPARTMENTS.getList());
			}

			public void deaSelectionEventOccurred(long depId) {
				getSubdepRequest(depId);
				mainFrame.getAddEmpDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
			}

			public void createPersonEventOccurred(EmployeeEvent ev) {
				createEmployee(ev);
			}
		});

		mainFrame.getEditOrgDialog().setOrganizationDialogListener(new OrganizationDialogListener() {

			public void instSelectionEventOccurred(long instId) {
				getDepRequest(instId);
				mainFrame.getEditOrgDialog().setDepData(Database.DEPARTMENTS.getList());
			}

			public void depSelectionEventOccurred(long depId) {
				getSubdepRequest(depId);
				mainFrame.getEditOrgDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
			}

			public void createInstEventOccurred(InstituteModel instModel) {
				createInstitute(instModel);
				getInstRequest();
				instModelList = Database.INSTITUTES.getList();
				mainFrame.getEditOrgDialog().setInstData(instModelList);
				mainFrame.getEditEmpDialog().setInstData(instModelList);
			}

			public void editInstEventOccurred(InstituteModel instModel) {
				editInstitute(instModel);
				getInstRequest();
				instModelList = Database.INSTITUTES.getList();
				mainFrame.getEditOrgDialog().setInstData(instModelList);
				mainFrame.getEditEmpDialog().setInstData(instModelList);
			}

			public void deleteInstEventOccurred(InstituteModel instModel) {
				deleteInstitute(instModel);
				getInstRequest();
				instModelList = Database.INSTITUTES.getList();
				mainFrame.getEditOrgDialog().setInstData(instModelList);
				mainFrame.getEditEmpDialog().setInstData(instModelList);
			}

			public void createDepEventOccurred(DepartmentModel model) {
				createDepartment(model);
				getDepRequest(model.getInstId());
				mainFrame.getEditOrgDialog().setDepData(Database.DEPARTMENTS.getList());
			}

			public void editDepEventOccurred(DepartmentModel model) {
				editDepartment(model);
				getDepRequest(model.getInstId());
				mainFrame.getEditOrgDialog().setDepData(Database.DEPARTMENTS.getList());
			}

			public void deleteDepEventOccurred(DepartmentModel model) {
				deleteDepartment(model);
				getDepRequest(model.getInstId());
				mainFrame.getEditOrgDialog().setDepData(Database.DEPARTMENTS.getList());
			}

			public void createSubdepEventOccurred(SubdepartmentModel model) {
				createSubdepartment(model);
				getSubdepRequest(model.getDepId());
				mainFrame.getEditOrgDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
			}

			public void editSubdepEventOccurred(SubdepartmentModel model) {
				editSubdepartment(model);
				getSubdepRequest(model.getDepId());
				mainFrame.getEditOrgDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
			}

			public void deleteSubdepEventOccurred(SubdepartmentModel model) {
				deleteSubdepartment(model);
				getSubdepRequest(model.getDepId());
				mainFrame.getEditOrgDialog().setSubdepData(Database.SUBDEPARTMENS.getList());
			}
		});

        mainFrame.getAmUnitsDialog().setListener(new AmUnitsDialogListener() {
            @Override
            public void createEventOccurred(AmountUnitsModel model) {
                createAmUnit(model);
                getAmUnits();
                mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
            }

            @Override
            public void editEventOccurred(AmountUnitsModel model) {
                editAmUnit(model);
                getAmUnits();
                mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
            }

            @Override
            public void deleteEventOccurred(AmountUnitsModel model) {
                deleteAmUnit(model);
                getAmUnits();
                mainFrame.getAmUnitsDialog().setData(Database.AMOUNTUNITS.getList());
            }
        });

		mainFrame.getProdSuplDialog().setListener(new ProdSuplDialogListener() {
            @Override
            public void createProdEventOccurred(ProducerModel model) {
                createProd(model);
                getProd();
                mainFrame.getProdSuplDialog().setProdData(Database.PRODUCERS.getList());
            }

            @Override
            public void editProdEventOccurred(ProducerModel model) {
                editProd(model);
                getProd();
                mainFrame.getProdSuplDialog().setProdData(Database.PRODUCERS.getList());
            }

            @Override
            public void deleteProdEventOccurred(ProducerModel model) {
                deleteProd(model);
                getProd();
                mainFrame.getProdSuplDialog().setProdData(Database.PRODUCERS.getList());
            }

            @Override
            public void createSuplEventOccurred(SupplierModel model) {
                createSupl(model);
                getSupl();
                mainFrame.getProdSuplDialog().setSuplData(Database.SUPPLIERS.getList());
            }

            @Override
            public void editSuplEventOccurred(SupplierModel model) {
                editSupl(model);
                getSupl();
                mainFrame.getProdSuplDialog().setSuplData(Database.SUPPLIERS.getList());
            }

            @Override
            public void deleteSuplEventOccurred(SupplierModel model) {
                deleteSupl(model);
                getSupl();
                mainFrame.getProdSuplDialog().setSuplData(Database.SUPPLIERS.getList());
            }
        });

        mainFrame.getFinancePanel().setFinancePanelListener(new FinancePanelListener() {
            @Override
            public void createOrderEventOccurred(FinanceModel model) {
				createFinance(model);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
            }

            @Override
            public void editOrderEventOccurred(FinanceModel model) {
                editFinance(model);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();
            }

            @Override
            public void deleteOrderEventOccurred(FinanceModel model) {
                deleteFinance(model);
                getFinances();
                mainFrame.getFinancePanel().setFinanceTableData(Database.FINANCES.getList());
                mainFrame.getFinancePanel().refreshFinanceTable();

            }

            @Override
            public void departmentSelectionEventOccurred(long departmentId) {
                getEmployees(departmentId);
                mainFrame.getFinancePanel().setEmployeeBoxData(Database.EMPLOYEES.getList());
            }

			@Override
			public void orderSelectionEventOccurred(long orderId) {
				getDepartmentFinances(orderId);
				mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
				mainFrame.getFinancePanel().refreshDepartmentFinanceTable();
			}

			@Override
            public void createDepOrderEventOccurred(FinanceDepartmentModel model) {
                createDepartmentFinances(model);
                getDepartmentFinances(model.getOrderId());
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();

            }

            @Override
            public void editDepOrderEventOccurred(FinanceDepartmentModel model) {
                editDepartmentFinances(model);
                getDepartmentFinances(model.getOrderId());
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();
            }

            @Override
            public void deleteDepOrderEventOccurred(FinanceDepartmentModel model) {
                deleteDepartmentFinances(model);
                getDepartmentFinances(model.getOrderId());
                mainFrame.getFinancePanel().setDepartmentFinanceTableData(Database.DEPARTMENT_FINANCES.getList());
                mainFrame.getFinancePanel().refreshDepartmentFinanceTable();
            }
        });

		mainFrame.getExitItem().addActionListener(ev -> closeDialog());
	}

    // sets connection settings to Properties object
	private void setConnectionSettings(String host, String database, String schema,
									   String port, String user, String password) {
		if (conSet == null){
			conSet = new Properties();
		}
		conSet.setProperty("host", host);
		conSet.setProperty("port", port);
		conSet.setProperty("database", database);
		conSet.setProperty("user", user);
		conSet.setProperty("password", password);
		conSet.setProperty("currentSchema", schema);
	}

	//connecting to DB
	private void connect() {
		try {
			Database.DB.connect(conSet);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame,
					Labels.getProperty("NoConnectionToDB"),
                    Labels.getProperty("DatabaseConnectionError"),
					JOptionPane.ERROR_MESSAGE);
			// if can't connect - call ConnectionSettingsDialog
			mainFrame.getConSettDialog().setVisible(true);
		}
	}
	//disconnecting from DB
	private void disconnect() {
		Database.DB.disconnect();
	}

	//general methods for loging modifications in DB entries
	private <T extends AbstractModel> void setCreated(T model){
		model.setCreatedBy(LoginData.INSTANCE.getEmpId());
		model.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	}

	private <T extends AbstractModel> void setModified(T model){
		model.setModifiedBy(LoginData.INSTANCE.getEmpId());
		model.setModifiedDate(new Timestamp(System.currentTimeMillis()));
	}

	private <T extends AbstractModel> void setInactive(T model){
		setModified(model);
		model.setActive(false);
	}

	//methods sending requests to DB
	//GETTERS
	private void getCpvRequest(String cpvRequest, boolean sameLvlShow) {
		try {
			Database.CPV.retrieve(cpvRequest, sameLvlShow);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void getRolesRequest() {
		try {
			Database.ROLES.retrieve();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getEmployees() {
		try {
			Database.EMPLOYEES.retrieve();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    private void getEmployees(long depId) {
        try {
            Database.EMPLOYEES.retrieve(depId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	private void getInstRequest() {
		try {
			Database.INSTITUTES.retrieve();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getDepRequest(long instId){
		try {
			Database.DEPARTMENTS.retrieve(instId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getSubdepRequest(long depId){
		try {
			Database.SUBDEPARTMENS.retrieve(depId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getAmUnits() {
		try {
			Database.AMOUNTUNITS.retrieve();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    private void getProd(){
        try {
            Database.PRODUCERS.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getSupl(){
        try {
            Database.SUPPLIERS.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getFinances() {
        try {
            Database.FINANCES.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getDepartmentFinances(long orderId){
        try {
            Database.DEPARTMENT_FINANCES.retrieve(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	// check user login and pass
	private boolean checkLogin() {
		try {
			return Database.EMPLOYEES.checkLogin(LoginData.INSTANCE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//CRUD Employees
	private void createEmployee (EmployeeEvent ev){
		try {
			EmployeeModel model = ev.getEmployeeModel();
			setCreated(model);
			Database.EMPLOYEES.create(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void editEmployee(EmployeeEvent ev) {
		try {
			EmployeeModel model = ev.getEmployeeModel();
			setModified(model);
			Database.EMPLOYEES.update(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//CRUD Institutes
	private void createInstitute(InstituteModel instModel) {
		try {
			setCreated(instModel);
			Database.INSTITUTES.create(instModel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void editInstitute(InstituteModel instModel) {
		try {
			setModified(instModel);
			Database.INSTITUTES.update(instModel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void deleteInstitute(InstituteModel instModel) {
		try {
			setInactive(instModel);
			Database.INSTITUTES.delete(instModel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//CRUD Departments
	private void createDepartment(DepartmentModel model) {
		try {
			setCreated(model);
			Database.DEPARTMENTS.create(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void editDepartment(DepartmentModel model) {
		try {
			setModified(model);
			Database.DEPARTMENTS.update(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteDepartment(DepartmentModel model) {
		try {
			setInactive(model);
			Database.DEPARTMENTS.delete(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//CRUD Subdepartments
	private void createSubdepartment(SubdepartmentModel model) {
		try {
			setCreated(model);
			Database.SUBDEPARTMENS.create(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void editSubdepartment(SubdepartmentModel model) {
		try {
			setModified(model);
			Database.SUBDEPARTMENS.update(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteSubdepartment(SubdepartmentModel model) {
		try {
			setInactive(model);
			Database.SUBDEPARTMENS.delete(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    private void createAmUnit (AmountUnitsModel model) {
        setCreated(model);
        try {
            Database.AMOUNTUNITS.create(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editAmUnit (AmountUnitsModel model) {
        setModified(model);
        try {
            Database.AMOUNTUNITS.update(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteAmUnit (AmountUnitsModel model) {
        setInactive(model);
        try {
            Database.AMOUNTUNITS.delete(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createProd(ProducerModel model){
        setCreated(model);
        try {
            Database.PRODUCERS.create(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editProd(ProducerModel model){
        setModified(model);
        try {
            Database.PRODUCERS.update(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteProd(ProducerModel model){
        setInactive(model);
        try {
            Database.PRODUCERS.delete(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createSupl(SupplierModel model){
        setCreated(model);
        try {
            Database.SUPPLIERS.create(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editSupl(SupplierModel model){
        setModified(model);
        try {
            Database.SUPPLIERS.update(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteSupl(SupplierModel model){
        setInactive(model);
        try {
            Database.SUPPLIERS.delete(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createFinance(FinanceModel model){
        setCreated(model);
        try {
            Database.FINANCES.create(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editFinance(FinanceModel model){
        setModified(model);
        try {
            Database.FINANCES.update(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteFinance(FinanceModel model){
        setInactive(model);
        try {
            Database.FINANCES.delete(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDepartmentFinances(FinanceDepartmentModel model){
        try {
            setCreated(model);
            Database.DEPARTMENT_FINANCES.create(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editDepartmentFinances(FinanceDepartmentModel model){
        try {
            setModified(model);
            Database.DEPARTMENT_FINANCES.update(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteDepartmentFinances(FinanceDepartmentModel model){
        try {
            setInactive(model);
            Database.DEPARTMENT_FINANCES.delete(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	//default close method
	private void close(){
		Database.DB.disconnect();
		mainFrame.dispose();
		System.gc();
	}
	
	//but it calls only in this close() method (except close in login dialog)
	private void closeDialog() {
		int action = JOptionPane.showConfirmDialog(this.mainFrame,
                Labels.getProperty("WantExit"),
                Labels.getProperty("ConfirmExit"),
                JOptionPane.OK_CANCEL_OPTION);
		if (action == JOptionPane.OK_OPTION) {
			close();
		}
	}
}
