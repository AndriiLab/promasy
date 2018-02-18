package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

public class EmptyCreateFinancePanelListener extends AbstractEmptyListener implements CreateFinancePanelListener {
    @Override
    public void persistModelEventOccurred(Finance model) {
        logEmptyListener(CreateFinancePanelListener.class);
    }
}
