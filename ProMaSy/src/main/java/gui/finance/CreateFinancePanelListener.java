package gui.finance;

import model.models.FinanceModel;

/**
 * Listener for {@link CreateFinancePanel}
 */
public interface CreateFinancePanelListener {
    void persistModelEventOccurred(FinanceModel model);
}
