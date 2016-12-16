package main.java.model;

import main.java.model.enums.Role;

public class RoleModel extends AbstractModel{
	private String rolesName;
    private int roleId;

	public RoleModel() {

	}
	
	public RoleModel(int roleId, String rolesName) {
        super.setModelId(((long)roleId));
        this.roleId = roleId;
        this.rolesName = rolesName;
	}

    public RoleModel(Role role) {
        super.setModelId(((long) role.getRoleId()));
        this.roleId = role.getRoleId();
        this.rolesName = role.getRoleName();
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRolesName() {
		return rolesName;
	}

	public String toString(){
		return rolesName;
	}
}
