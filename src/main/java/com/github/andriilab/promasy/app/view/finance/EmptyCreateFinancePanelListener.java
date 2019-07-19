package com.github.andriilab.promasy.app.view.finance;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.app.components.AbstractEmptyListener;

public class EmptyCreateFinancePanelListener extends AbstractEmptyListener implements CreateFinancePanelListener {
    @Override
    public void persistModelEventOccurred(ICommand<Finance> command) {
        logEmptyListener(CreateFinancePanelListener.class);
    }
}
