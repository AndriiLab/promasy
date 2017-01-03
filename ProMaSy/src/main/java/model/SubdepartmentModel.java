package model;

import java.sql.Timestamp;

public class SubdepartmentModel extends AbstractModel {
	private String subdepName;
	private DepartmentModel department;
	
	public SubdepartmentModel() {

	}

	public SubdepartmentModel(String subdepName, DepartmentModel department) {
		this.subdepName = subdepName;
		this.department = department;
	}

	public SubdepartmentModel(long subdepId, String subdepName, DepartmentModel department, EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active) {
		super(subdepId, createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.subdepName = subdepName;
		this.department = department;
	}

	public String getSubdepName() {
		return subdepName;
	}

	public void setSubdepName(String subdepName) {
		this.subdepName = subdepName;
	}

	public DepartmentModel getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentModel department) {
		this.department = department;
	}
	
	public String toString(){
		return subdepName;
	}

}
