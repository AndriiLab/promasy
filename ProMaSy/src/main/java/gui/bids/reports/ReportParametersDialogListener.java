package gui.bids.reports;

/**
 * Listener for {@link ReportParametersDialog}
 */
public interface ReportParametersDialogListener {
    void roleSelectionOccurred(int roleId);
    void reportParametersSelectionOccurred(ReportParametersEvent ev);
}
