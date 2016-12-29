package gui.empedit;

import model.EmployeeModel;

public interface CreateEmployeeDialogListener {
	void instSelectionEventOccurred(long instId);
	void depSelectionEventOccurred(long depId);
	void createEmployeeEventOccurred(EmployeeModel model);
	void editEmployeeEventOccurred(EmployeeModel model);

    boolean checkUniqueLogin(String login);
}
