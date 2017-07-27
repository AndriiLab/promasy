package com.github.andriilab.promasy.gui.employee;

import com.github.andriilab.promasy.model.models.EmployeeModel;

public interface CreateEmployeeDialogListener {
    void persistModelEventOccurred(EmployeeModel model);

    boolean checkUniqueLogin(String login);

    void loadInstitutes();
}
