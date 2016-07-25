package main.java.model;

import java.sql.Timestamp;

public final class LoginData {

	private static volatile LoginData instance;

    private final long empId;
    private final String empFName;
    private final String empMName;
    private final String empLName;
    private final long instId;
    private final long depId;
    private final long subdepId;
    private final long roleId;
    private final String login;
    private final String password;
    private final long createdBy;
    private final Timestamp createdDate;
    private final long modifiedBy;
    private final Timestamp modifiedDate;

    public static LoginData getInstance(long empId, String empFName, String empMName, String empLName, long instId, long depId, long subdepId, long roleId, String login, String password, long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate) {
        LoginData localInstance = instance;
        if (localInstance == null) {
            synchronized (LoginData.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LoginData(empId, empFName, empMName, empLName, instId, depId, subdepId, roleId, login, password, createdBy, createdDate, modifiedBy, modifiedDate);
                }
            }
        }
        return localInstance;
    }

    public static LoginData getInstance(){
        return instance;
    }

    private LoginData(long empId, String empFName, String empMName, String empLName, long instId, long depId, long subdepId, long roleId, String login, String password, long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate) {
        this.empId = empId;
        this.empFName = empFName;
        this.empMName = empMName;
        this.empLName = empLName;
        this.instId = instId;
        this.depId = depId;
        this.subdepId = subdepId;
        this.roleId = roleId;
        this.login = login;
        this.password = password;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    public long getEmpId() {
		return empId;
	}

	public String getEmpFName() {
		return empFName;
	}

	public String getEmpMName() {
		return empMName;
	}

	public String getEmpLName() {
		return empLName;
	}

	public long getInstId() {
		return instId;
	}

	public long getDepId() {
		return depId;
	}

	public long getSubdepId() {
		return subdepId;
	}

	public long getRoleId() {
		return roleId;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public long getModifiedBy() {
		return modifiedBy;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public String getShortName(){
		if(empFName != null && empLName != null && empMName != null){
			return empLName + " " + empFName.substring(0, 1) + "."
					+ empMName.substring(0, 1)+".";
		}
		return null;
	}
}
