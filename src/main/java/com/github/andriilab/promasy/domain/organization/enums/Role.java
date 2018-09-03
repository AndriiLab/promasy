package com.github.andriilab.promasy.domain.organization.enums;

import com.github.andriilab.promasy.presentation.commons.Labels;
import lombok.Getter;

/**
 * Enum holds data about available user roles
 */
public enum Role {
    ADMIN(Labels.getProperty("role.admin")),
    DIRECTOR(Labels.getProperty("role.director")),
    DEPUTY_DIRECTOR(Labels.getProperty("role.deputyDirector")),
    HEAD_OF_TENDER_COMMITTEE(Labels.getProperty("role.headOfTenderCommittee")),
    SECRETARY_OF_TENDER_COMMITTEE(Labels.getProperty("role.secretaryOfTenderCommittee")),
    ECONOMIST(Labels.getProperty("role.economist")),
    ACCOUNTANT(Labels.getProperty("role.accountant")),
    HEAD_OF_DEPARTMENT(Labels.getProperty("role.headOfDepartment")),
    PERSONALLY_LIABLE_EMPLOYEE(Labels.getProperty("role.personallyLiableEmployee")),
    USER(Labels.getProperty("role.user"));

    private final @Getter
    String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
