package com.github.andriilab.promasy.presentation.reports.cpv;

import com.github.andriilab.promasy.data.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

public class EmptyCpvAmountDialogListener extends AbstractEmptyListener implements CpvAmountDialogListener {
    @Override
    public void getData(int year) {
        logEmptyListener(CpvAmountDialogListener.class);
    }

    @Override
    public String getEmployee(GetEmployeesQuery query) {
        logEmptyListener(CpvAmountDialogListener.class);
        return "";
    }
}
