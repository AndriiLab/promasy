package model;

import model.enums.Role;

import java.sql.Timestamp;

public class EmployeeModel extends AbstractModel {

    private DepartmentModel department;
    private SubdepartmentModel subdepartment;
    private Role role;

    private String empFName;
    private String empMName;
	private String empLName;
    private String email;
    private String phoneMain;
    private String phoneReserve;
	private String login;
	private String password;
	private long salt;

    public EmployeeModel(long modelId, EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active, String empFName, String empMName, String empLName, String email, String phoneMain, String phoneReserve, DepartmentModel department, SubdepartmentModel subdepartment, Role role, String login, String password, long salt) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.empFName = empFName;
        this.empMName = empMName;
        this.empLName = empLName;
        this.email = email;
        this.phoneMain = phoneMain;
        this.phoneReserve = phoneReserve;
        this.department = department;
        this.subdepartment = subdepartment;
        this.role = role;
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    public EmployeeModel() {
    }

    public EmployeeModel(long modelId, Role role) {
        super.setModelId(modelId);
        this.role = role;
    }

    public EmployeeModel(String empFName, String empMName, String email, String phoneMain, String phoneReserve,
                         String empLName, DepartmentModel department, SubdepartmentModel subdepartment, Role role, String login, String password, long salt) {
        this.empFName = empFName;
		this.empMName = empMName;
		this.empLName = empLName;
        this.email = email;
        this.phoneMain = phoneMain;
        this.phoneReserve = phoneReserve;
        this.department = department;
        this.subdepartment = subdepartment;
        this.role = role;
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

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }

    public SubdepartmentModel getSubdepartment() {
        return subdepartment;
    }

    public void setSubdepartment(SubdepartmentModel subdepartment) {
        this.subdepartment = subdepartment;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public long getSalt() {
        return salt;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }

    public String getShortName() {
        if (empFName != null && empLName != null && empMName != null) {
			return empLName + " " + empFName.substring(0, 1) + "."
					+ empMName.substring(0, 1) + ".";
		}
		return "";
	}

	public String toString() {
		return getShortName();
	}
}
