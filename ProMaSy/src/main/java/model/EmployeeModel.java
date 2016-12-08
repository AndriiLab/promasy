package main.java.model;

import java.sql.Timestamp;

public class EmployeeModel extends AbstractModel {
	private String empFName;
	private String empMName;
	private String empLName;
    private String email;
    private String phoneMain;
    private String phoneReserve;
    private long instId;
	private String instName;
	private long depId;
	private String depName;
	private long subdepId;
	private String subdepName;
	private int roleId;
	private String roleName;
	private String login;
	private String password;
	private long salt;

    public EmployeeModel(long modelId, long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate, boolean active, String empFName, String empMName, String empLName, String email, String phoneMain, String phoneReserve, long instId, String instName, long depId, String depName, long subdepId, String subdepName, int roleId, String roleName, String login, String password, long salt) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.empFName = empFName;
        this.empMName = empMName;
        this.empLName = empLName;
        this.email = email;
        this.phoneMain = phoneMain;
        this.phoneReserve = phoneReserve;
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

    public EmployeeModel() {
    }

    public EmployeeModel(String empFName, String empMName, String email, String phoneMain, String phoneReserve,
                         String empLName, long depId, long subdepId, int roleId, String login, String password, long salt) {
        this.empFName = empFName;
		this.empMName = empMName;
		this.empLName = empLName;
        this.email = email;
        this.phoneMain = phoneMain;
        this.phoneReserve = phoneReserve;
        this.depId = depId;
		this.subdepId = subdepId;
		this.roleId = roleId;
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneMain() {
        return phoneMain;
    }

    public void setPhoneMain(String phoneMain) {
        this.phoneMain = phoneMain;
    }

    public String getPhoneReserve() {
        return phoneReserve;
    }

    public void setPhoneReserve(String phoneReserve) {
        this.phoneReserve = phoneReserve;
    }

    public String toString() {
		if(empFName != null && empLName != null && empMName != null){
			return empLName + " " + empFName.substring(0, 1) + "."
					+ empMName.substring(0, 1)+".";
		}
		return "";
	}
}
