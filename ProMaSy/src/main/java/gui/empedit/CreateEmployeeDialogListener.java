package main.java.gui.empedit;

import main.java.model.EmployeeModel;

public interface CreateEmployeeDialogListener {
	void instSelectionEventOccurred(long instId);
	void depSelectionEventOccurred(long depId);
	void createEmployeeEventOccurred(EmployeeModel model);
	void editEmployeeEventOccurred(EmployeeModel model);

    boolean checkUniqueLogin(String login);
}
