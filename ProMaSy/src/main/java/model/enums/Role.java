package model.enums;

import gui.Labels;

/**
 * This Enum holds data about available user roles
 */
public enum Role {
    ADMIN(Labels.getProperty("role.admin")),
    DIRECTOR(Labels.getProperty("role.director")),
    DEPUTY_DIRECTOR(Labels.getProperty("role.deputyDirector")),
    HEAD_OF_TENDER_COMMITTEE(Labels.getProperty("role.headOfTenderCommittee")),
    ECONOMIST(Labels.getProperty("role.economist")),
    ACCOUNTANT(Labels.getProperty("role.accountant")),
    HEAD_OF_DEPARTMENT(Labels.getProperty("role.headOfDepartment")),
    PERSONALLY_LIABLE_EMPLOYEE(Labels.getProperty("role.personallyLiableEmployee")),
    USER(Labels.getProperty("role.user"));

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
