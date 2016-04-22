package model;

import java.sql.Timestamp;

public class DepartmentModel extends AbstractModel {
	private long depId;
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
		super(createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.depId = depId;
		this.depName = depName;
		this.instId = instId;
	}
	
	public long getDepId() {
		return depId;
	}
	
	public void setDepId(long depId) {
		this.depId = depId;
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
	
	public String toString(){
		return depName;
	}

}
