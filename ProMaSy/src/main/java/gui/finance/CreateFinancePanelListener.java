package gui.finance;

import model.models.FinanceModel;

/**
 * Listener for {@link CreateFinancePanel}
 */
interface CreateFinancePanelListener {
    void persistModelEventOccurred(FinanceModel model);
}
