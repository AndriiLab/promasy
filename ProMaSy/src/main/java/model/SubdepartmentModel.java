package main.java.model;

import java.sql.Timestamp;

public class SubdepartmentModel extends AbstractModel {
	private String subdepName;
	private long depId;
	
	public SubdepartmentModel() {

	}
	
	public SubdepartmentModel(String subdepName, long depId) {
		this.subdepName = subdepName;
		this.depId = depId;
	}
	
	public SubdepartmentModel(long subdepId, String subdepName, long depId, long createdBy, Timestamp createdDate,
			long modifiedBy, Timestamp modifiedDate, boolean active) {
		super(subdepId, createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.subdepName = subdepName;
		this.depId = depId;
	}

	public String getSubdepName() {
		return subdepName;
	}

	public void setSubdepName(String subdepName) {
		this.subdepName = subdepName;
	}

	public long getDepId() {
		return depId;
	}

	public void setDepId(long depId) {
		this.depId = depId;
	}
	
	public String toString(){
		return subdepName;
	}

}
