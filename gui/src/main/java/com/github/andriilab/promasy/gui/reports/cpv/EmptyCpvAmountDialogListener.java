package com.github.andriilab.promasy.gui.reports.cpv;

import com.github.andriilab.promasy.app.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

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
