package com.github.andriilab.promasy.app.view.bids;

import com.github.andriilab.promasy.app.components.AbstractEmptyListener;
import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentSpentAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentsQuery;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class EmptyBidsListPanelListener extends AbstractEmptyListener implements BidsListPanelListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(ICommand<T> command) {
        logEmptyListener(BidsListPanelListener.class);
    }

    @Override
    public void getBids(GetBidsQuery query) {
        logEmptyListener(BidsListPanelListener.class);
    }

    @Override
    public void getAllData() {
        logEmptyListener(BidsListPanelListener.class);
    }

    @Override
    public List<Subdepartment> getSubdepartments(long departmentId) {
        logEmptyListener(BidsListPanelListener.class);
        return Collections.emptyList();
    }

    @Override
    public List<FinanceDepartment> getFinanceDepartments(GetFinanceDepartmentsQuery query) {
        logEmptyListener(BidsListPanelListener.class);
        return Collections.emptyList();
    }

    @Override
    public BigDecimal getLeftAmountEvent(GetFinanceDepartmentLeftAmountQuery getFinanceDepartmentLeftAmountQuery) {
        logEmptyListener(BidsListPanelListener.class);
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getSpentAmountEvent(GetFinanceDepartmentSpentAmountQuery getFinanceDepartmentSpentAmountQuery) {
        logEmptyListener(BidsListPanelListener.class);
        return BigDecimal.ZERO;
    }
}
