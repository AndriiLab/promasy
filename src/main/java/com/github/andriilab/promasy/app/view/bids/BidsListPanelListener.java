package com.github.andriilab.promasy.app.view.bids;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentSpentAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentsQuery;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;

import java.math.BigDecimal;
import java.util.List;

/**
 * Listener for {@link BidsListPanel}
 */
public interface BidsListPanelListener {
    <T extends IEntity> void persistModelEventOccurred(ICommand<T> command);

    void getBids(GetBidsQuery query);

    void getAllData();

    List<Subdepartment> getSubdepartments(long departmentId);

    List<FinanceDepartment> getFinanceDepartments(GetFinanceDepartmentsQuery query);

    BigDecimal getLeftAmountEvent(GetFinanceDepartmentLeftAmountQuery getFinanceDepartmentLeftAmountQuery);

    BigDecimal getSpentAmountEvent(GetFinanceDepartmentSpentAmountQuery getFinanceDepartmentSpentAmountQuery);
}
