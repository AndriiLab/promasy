package main.java.model;

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