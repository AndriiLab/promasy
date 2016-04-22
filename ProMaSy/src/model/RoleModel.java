package model;

import java.sql.Timestamp;

public class RoleModel extends AbstractModel{
	private long rolesId;
	private String rolesName;
	
	public RoleModel() {
		this.rolesName = "";
	}
	
	public RoleModel(long rolesId, String rolesName, long createdBy, Timestamp createdDate, long modifiedBy,
			Timestamp modifiedDate, boolean active) {
		super(createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.rolesId = rolesId;
		this.rolesName = rolesName;
	}
	public long getRolesId() {
		return rolesId;
	}
	public void setRolesId(long rolesId) {
		this.rolesId = rolesId;
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
