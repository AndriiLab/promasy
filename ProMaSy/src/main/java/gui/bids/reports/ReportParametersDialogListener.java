package gui.bids.reports;

import model.enums.Role;

/**
 * Listener for {@link ReportParametersDialog}
 */
public interface ReportParametersDialogListener {
    void roleSelectionOccurred(Role role);
    void reportParametersSelectionOccurred(ReportParametersEvent ev);
}
