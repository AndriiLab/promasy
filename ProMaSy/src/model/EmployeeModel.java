package model;

import java.sql.Timestamp;

public class EmployeeModel extends AbstractModel {
	private String empFName;
	private String empMName;
	private String empLName;
	private long instId;
	private String instName;
	private long depId;
	private String depName;
	private long subdepId;
	private String subdepName;
	private long roleId;
	private String roleName;
	private String login;
	private String password;
	private long salt;
	
	public EmployeeModel() {
	}
	
	public EmployeeModel(String empFName, String empMName, 
			String empLName, long depId, long subdepId, 
			long roleId, String login, String password, long salt) {
		this.empFName = empFName;
		this.empMName = empMName;
		this.empLName = empLName;
		this.depId = depId;
		this.subdepId = subdepId;
		this.roleId = roleId;
		this.login = login;
		this.password = password;
		this.salt = salt;
	}
	
	public EmployeeModel(long empId, String empFName, String empMName, String empLName, long instId, String instName,
			long depId, String depName, long subdepId, String subdepName, long roleId, String roleName, String login,
			String password, long salt, long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate,
			boolean active) {
		super(empId, createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.empFName = empFName;
		this.empMName = empMName;
		this.empLName = empLName;
		this.instId = instId;
		this.instName = instName;
		this.depId = depId;
		this.depName = depName;
		this.subdepId = subdepId;
		this.subdepName = subdepName;
		this.roleId = roleId;
		this.roleName = roleName;
		this.login = login;
		this.password = password;
        this.salt = salt;
	}

	public String getEmpFName() {
		return empFName;
	}

	public void setEmpFName(String empFName) {
		this.empFName = empFName;
	}

	public String getEmpMName() {
		return empMName;
	}

	public void setEmpMName(String empMName) {
		this.empMName = empMName;
	}

	public String getEmpLName() {
		return empLName;
	}

	public void setEmpLName(String empLName) {
		this.empLName = empLName;
	}

	public long getDepId() {
		return depId;
	}

	public void setDepId(long depId) {
		this.depId = depId;
	}

	public long getSubdepId() {
		return subdepId;
	}

	public void setSubdepId(long subdepId) {
		this.subdepId = subdepId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getInstId() {
		return instId;
	}

	public void setInstId(long instId) {
		this.instId = instId;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getSubdepName() {
		return subdepName;
	}

	public void setSubdepName(String subdepName) {
		this.subdepName = subdepName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

    public long getSalt() {
        return salt;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }

    public String toString() {
		if(empFName != null && empLName != null && empMName != null){
			return empLName + " " + empFName.substring(0, 1) + "."
					+ empMName.substring(0, 1)+".";
		}
		return "";
	}
}
