package com.github.andriilab.promasy.app.view.finance;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinancesQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

import java.math.BigDecimal;

public class EmptyFinancePanelListener extends AbstractEmptyListener implements FinancePanelListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(ICommand<T> command) {
        logEmptyListener(FinancePanelListener.class);
    }

    @Override
    public void loadDepartments() {
        logEmptyListener(FinancePanelListener.class);
    }

    @Override
    public void getFinancesByDepartment(GetFinancesQuery query) {
        logEmptyListener(FinancePanelListener.class);
    }

    @Override
    public void getAllData() {
        logEmptyListener(FinancePanelListener.class);
    }

    @Override
    public BigDecimal getUnassignedAmountEvent(GetFinanceUnassignedAmountQuery query) {
        logEmptyListener(FinancePanelListener.class);
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getLeftAmountEvent(GetFinanceLeftAmountQuery query) {
        logEmptyListener(FinancePanelListener.class);
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getLeftAmountEvent(GetFinanceDepartmentLeftAmountQuery query) {
        logEmptyListener(FinancePanelListener.class);
        return BigDecimal.ZERO;
    }
}
