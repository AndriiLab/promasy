package com.github.andriilab.promasy.presentation.employee;

import com.github.andriilab.promasy.domain.organization.entities.Employee;

public interface CreateEmployeeDialogListener {
    void persistModelEventOccurred(Employee model);

    boolean checkUniqueLogin(String login);

    void loadInstitutes();
}
