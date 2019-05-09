package com.github.andriilab.promasy.gui.bids;

import com.github.andriilab.promasy.app.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.app.commands.RefreshCommand;
import com.github.andriilab.promasy.app.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.app.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.app.queries.financepartment.GetFinanceDepartmentSpentAmountQuery;
import com.github.andriilab.promasy.app.queries.financepartment.GetFinanceDepartmentsQuery;
import com.github.andriilab.promasy.commons.persistence.IEntity;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class EmptyBidsListPanelListener extends AbstractEmptyListener implements BidsListPanelListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(CreateOrUpdateCommand<T> command) {
        logEmptyListener(BidsListPanelListener.class);
    }

    @Override
    public <T extends IEntity> void refreshModelEventOccurred(RefreshCommand<T> command) {
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
