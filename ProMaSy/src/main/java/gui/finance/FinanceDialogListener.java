package gui.finance;

import model.models.FinanceModel;

/**
 * Listener for {@link CreateFinanceDialog}
 */
public interface FinanceDialogListener {
    void persistModelEventOccurred(FinanceModel model);
}
