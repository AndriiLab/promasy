package com.github.andriilab.promasy.presentation.reports.bids;

import com.github.andriilab.promasy.domain.organization.enums.Role;

import java.util.Map;

/**
 * Listener for {@link ReportParametersDialog}
 */
public interface ReportParametersDialogListener {
    void roleSelectionOccurred(Role role);

    void reportParametersSelectionOccurred(Map<String, Object> parameters);
}
