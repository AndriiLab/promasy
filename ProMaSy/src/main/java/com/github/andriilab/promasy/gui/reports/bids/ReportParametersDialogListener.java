package com.github.andriilab.promasy.gui.reports.bids;

import com.github.andriilab.promasy.model.enums.Role;

import java.util.Map;

/**
 * Listener for {@link ReportParametersDialog}
 */
public interface ReportParametersDialogListener {
    void roleSelectionOccurred(Role role);

    void reportParametersSelectionOccurred(Map<String, Object> parameters);
}
