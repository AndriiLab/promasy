package gui.bids.reports;

import model.enums.Role;

import java.util.Map;

/**
 * Listener for {@link ReportParametersDialog}
 */
public interface ReportParametersDialogListener {
    void roleSelectionOccurred(Role role);

    void reportParametersSelectionOccurred(Map<String, Object> parameters);
}
