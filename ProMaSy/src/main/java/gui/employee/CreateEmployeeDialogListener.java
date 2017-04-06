package gui.employee;

import model.models.EmployeeModel;

public interface CreateEmployeeDialogListener {
    void persistModelEventOccurred(EmployeeModel model);

    boolean checkUniqueLogin(String login);

    void loadInstitutes();
}
