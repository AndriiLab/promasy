package com.github.andriilab.promasy.presentation.finance;

import com.github.andriilab.promasy.domain.finance.entities.Finance;

/**
 * Listener for {@link CreateFinancePanel}
 */
interface CreateFinancePanelListener {
    void persistModelEventOccurred(Finance model);
}
