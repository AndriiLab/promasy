package model;

import java.sql.Timestamp;

public enum LoginData {
	INSTANCE;
	
	private long empId;
	private String empFName;
	private String empMName;
	private String empLName;
	private long instId;
	private long depId;
	private long subdepId;
	private long roleId;
	private String login;
	private String password;
	private long createdBy;
	private Timestamp createdDate;
	private long modifiedBy;
	private Timestamp modifiedDate;
	
	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
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

	public long getInstId() {
		return instId;
	}

	public void setInstId(long instId) {
		this.instId = instId;
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

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public String getShortName(){
		if(empFName != null && empLName != null && empMName != null){
			return empLName + " " + empFName.substring(0, 1) + "."
					+ empMName.substring(0, 1)+".";
		}
		return null;
	}
}
