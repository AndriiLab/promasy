package model;

import java.sql.Timestamp;

public class DepartmentModel extends AbstractModel {
	private String depName;
	private InstituteModel institute;
	
	public DepartmentModel() {
		this.depName = "";
	}

	public DepartmentModel(String depName, InstituteModel institute) {
		this.depName = depName;
		this.institute = institute;
	}

	public DepartmentModel(long depId, String depName, InstituteModel institute, EmployeeModel createdBy, Timestamp createdDate,
						   EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active) {
		super(depId, createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.depName = depName;
		this.institute = institute;
	}
	
	public String getDepName() {
		return depName;
	}
	
	public void setDepName(String depName) {
		this.depName = depName;
	}

	public InstituteModel getInstitute() {
		return institute;
	}

	public void setInstitute(InstituteModel institute) {
		this.institute = institute;
	}
	
	public String toString(){
		return depName;
	}

}
