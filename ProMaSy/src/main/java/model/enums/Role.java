package main.java.model.enums;

import main.java.gui.Labels;

/**
 * This Enum holds data about available user roles
 */
public enum Role {
    ADMIN(900, Labels.getProperty("administrator")),
    DIRECTOR(1000, Labels.getProperty("director")),
    DEPUTY_DIRECTOR(2000, Labels.getProperty("deputyDirector")),
    HEAD_OF_TENDER_COMMITTEE(2500, Labels.getProperty("headOfTenderCommittee")),
    ECONOMIST(3000, Labels.getProperty("chiefEconomist")),
    ACCOUNTANT(4000, Labels.getProperty("chiefAccountant")),
    HEAD_OF_DEPARTMENT(5000, Labels.getProperty("headOfDepartment")),
    PERSONALLY_LIABLE_EMPLOYEE(6000, Labels.getProperty("personallyLiableEmployee")),
    USER(7000, Labels.getProperty("user"));

    private final int roleId;
    private final String roleName;

    Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
