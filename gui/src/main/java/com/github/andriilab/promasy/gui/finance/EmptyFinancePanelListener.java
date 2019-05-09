package com.github.andriilab.promasy.gui.finance;

import com.github.andriilab.promasy.app.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.app.queries.finance.GetFinanceLeftAmountQuery;
import com.github.andriilab.promasy.app.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.app.queries.finance.GetFinancesQuery;
import com.github.andriilab.promasy.app.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.commons.persistence.IEntity;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

import java.math.BigDecimal;

public class EmptyFinancePanelListener extends AbstractEmptyListener implements FinancePanelListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command) {
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
