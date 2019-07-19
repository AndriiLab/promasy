package com.github.andriilab.promasy.app.view.finance;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.finance.entities.Finance;

/**
 * Listener for {@link CreateFinancePanel}
 */
interface CreateFinancePanelListener {
    void persistModelEventOccurred(ICommand<Finance> command);
}
