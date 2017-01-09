package model.enums;

import gui.Labels;

/**
 * This Enum holds data about available user roles
 */
public enum Role {
    ADMIN(Labels.getProperty("administrator")),
    DIRECTOR(Labels.getProperty("director")),
    DEPUTY_DIRECTOR(Labels.getProperty("deputyDirector")),
    HEAD_OF_TENDER_COMMITTEE(Labels.getProperty("headOfTenderCommittee")),
    ECONOMIST(Labels.getProperty("chiefEconomist")),
    ACCOUNTANT(Labels.getProperty("chiefAccountant")),
    HEAD_OF_DEPARTMENT(Labels.getProperty("headOfDepartment")),
    PERSONALLY_LIABLE_EMPLOYEE(Labels.getProperty("personallyLiableEmployee")),
    USER(Labels.getProperty("user"));

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
