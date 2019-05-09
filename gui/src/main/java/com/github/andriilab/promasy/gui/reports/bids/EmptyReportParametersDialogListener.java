package com.github.andriilab.promasy.gui.reports.bids;

import com.github.andriilab.promasy.app.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EmptyReportParametersDialogListener extends AbstractEmptyListener implements ReportParametersDialogListener {
    @Override
    public void roleSelectionOccurred(Role role) {
        logEmptyListener(ReportParametersDialogListener.class);
    }

    @Override
    public List<Employee> getEmployees(GetEmployeesQuery query) {
        logEmptyListener(ReportParametersDialogListener.class);
        return Collections.emptyList();
    }

    @Override
    public void reportParametersSelectionOccurred(Map<String, Object> parameters) {
        logEmptyListener(ReportParametersDialogListener.class);
    }
}
