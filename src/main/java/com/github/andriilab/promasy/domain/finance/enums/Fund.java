package com.github.andriilab.promasy.domain.finance.enums;

import com.github.andriilab.promasy.presentation.commons.Labels;

/**
 * Fund types
 */
public enum Fund {
    COMMON_FUND(Labels.getProperty("fund.commonFund")),
    SPECIAL_FUND(Labels.getProperty("fund.specialFund"));

    private final String name;

    Fund(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
