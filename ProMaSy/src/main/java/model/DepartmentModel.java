package model;

import java.sql.Timestamp;

public class DepartmentModel extends AbstractModel {
	private String depName;
	private long instId;
	
	public DepartmentModel() {
		this.depName = "";
	}
	
	public DepartmentModel(String depName, long instId) {
		this.depName = depName;
		this.instId = instId;
	}
	
	public DepartmentModel(long depId, String depName, long instId, long createdBy, Timestamp createdDate,
			long modifiedBy, Timestamp modifiedDate, boolean active) {
		super(depId, createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.depName = depName;
		this.instId = instId;
	}
	
	public String getDepName() {
		return depName;
	}
	
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	public long getInstId() {
		return instId;
	}
	
	public void setInstId(long instId) {
		this.instId = instId;
	}
	
	public String toString(){
		return depName;
	}

}
