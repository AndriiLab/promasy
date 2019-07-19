package com.github.andriilab.promasy.app.view.bids;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

import java.math.BigDecimal;

public class EmptyCreateBidPanelListener extends AbstractEmptyListener implements CreateBidPanelListener {
    @Override
    public <T extends IEntity> void persistModelEventOccurred(ICommand<T> model) {
        logEmptyListener(CreateBidPanelListener.class);
    }

    @Override
    public void getAllData() {
        logEmptyListener(CreateBidPanelListener.class);
    }

    @Override
    public BigDecimal getLeftAmount(GetFinanceDepartmentLeftAmountQuery query) {
        logEmptyListener(CreateBidPanelListener.class);
        return BigDecimal.ZERO;
    }
}
