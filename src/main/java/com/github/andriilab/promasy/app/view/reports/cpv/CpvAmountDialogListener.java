package com.github.andriilab.promasy.app.view.reports.cpv;

import com.github.andriilab.promasy.data.queries.employees.GetEmployeesQuery;

/**
 * Listener for {@link CpvAmountDialog}
 */
public interface CpvAmountDialogListener {
    void getData(int year);

    String getEmployee(GetEmployeesQuery query);
}
