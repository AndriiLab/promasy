package com.github.andriilab.promasy.presentation.employee;

import com.github.andriilab.promasy.domain.organization.entities.Employee;

public interface EditEmployeeDialogListener {
    void persistModelEventOccurred(Employee model);

    void getAllEmployees();
}
