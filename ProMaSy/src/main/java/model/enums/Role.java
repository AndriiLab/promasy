package main.java.model.enums;

/**
 * This Enum holds data about available user roles
 */
public enum Role {
    ADMIN(900, "Адміністратор"),
    DIRECTOR(1000, "Директор"),
    DEPUTY_DIRECTOR(2000, "Заступник директора"),
    HEAD_OF_TENDER_COMMITTEE(2500, "Голова тендерного комітету"),
    ECONOMIST(3000, "Головний економіст"),
    ACCOUNTANT(4000, "Головний бухгалтер"),
    HEAD_OF_DEPARTMENT(5000, "Керівник підрозділу"),
    PERSONALLY_LIABLE_EMPLOYEE(6000, "Матеріально-відповідальна особа"),
    USER(7000, "Користувач");

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
