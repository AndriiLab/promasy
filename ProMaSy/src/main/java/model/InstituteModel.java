package model;

import java.sql.Timestamp;

public class InstituteModel extends AbstractModel {
	private String instName;
	
	public InstituteModel() {
		this.instName = "";
	}
	
	public InstituteModel(String instName) {
		this.instName = instName;
	}

	public InstituteModel(long instId, String instName, EmployeeModel createdBy,
						  Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate,
						  boolean active) {
		super(instId, createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.instName = instName;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}
	
	public String toString(){
		return instName;
	}

}
