package com.github.andriilab.promasy.gui.finance;

import com.github.andriilab.promasy.domain.finance.entities.Finance;

/**
 * Listener for {@link CreateFinancePanel}
 */
interface CreateFinancePanelListener {
    void persistModelEventOccurred(Finance model);
}
