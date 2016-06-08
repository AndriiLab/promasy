package model;

import java.sql.Timestamp;

public class RoleModel extends AbstractModel{
	private String rolesName;
	
	public RoleModel() {

	}
	
	public RoleModel(long rolesId, String rolesName, long createdBy, Timestamp createdDate, long modifiedBy,
			Timestamp modifiedDate, boolean active) {
		super(rolesId, createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.rolesName = rolesName;
	}
	public String getRolesName() {
		return rolesName;
	}
	public void setRolesName(String rolesName) {
		this.rolesName = rolesName;
	}
	public String toString(){
		return rolesName;
	}

}
