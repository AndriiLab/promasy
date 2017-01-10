package gui.empedit;

import model.models.EmployeeModel;

public interface CreateEmployeeDialogListener {
    void persistModelEventOccurred(EmployeeModel model);

    boolean checkUniqueLogin(String login);

    void loadInstitutes();
}
