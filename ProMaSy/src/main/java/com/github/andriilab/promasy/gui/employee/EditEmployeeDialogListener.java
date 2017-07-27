package com.github.andriilab.promasy.gui.employee;

import com.github.andriilab.promasy.model.models.EmployeeModel;

public interface EditEmployeeDialogListener {
    void persistModelEventOccurred(EmployeeModel model);

    void getAllEmployees();
}
