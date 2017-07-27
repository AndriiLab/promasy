package com.github.andriilab.promasy.gui.finance;

import com.github.andriilab.promasy.model.models.FinanceModel;

/**
 * Listener for {@link CreateFinancePanel}
 */
interface CreateFinancePanelListener {
    void persistModelEventOccurred(FinanceModel model);
}
