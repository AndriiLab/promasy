package gui.employee;

import model.models.EmployeeModel;

public interface EditEmployeeDialogListener {
    void persistModelEventOccurred(EmployeeModel model);

    void getAllEmployees();
}
