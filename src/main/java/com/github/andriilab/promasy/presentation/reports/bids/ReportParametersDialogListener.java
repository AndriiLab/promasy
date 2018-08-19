package com.github.andriilab.promasy.presentation.reports.bids;

import com.github.andriilab.promasy.data.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.enums.Role;

import java.util.List;
import java.util.Map;

/**
 * Listener for {@link ReportParametersDialog}
 */
public interface ReportParametersDialogListener {
    void roleSelectionOccurred(Role role);

    List<Employee> getEmployees(GetEmployeesQuery query);

    void reportParametersSelectionOccurred(Map<String, Object> parameters);
}
