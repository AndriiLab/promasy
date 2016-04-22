package model;

import java.sql.Timestamp;

public class SubdepartmentModel extends AbstractModel {
	private long subdepId;
	private String subdepName;
	private long depId;
	
	public SubdepartmentModel() {
		this.subdepName = "";
	}
	
	public SubdepartmentModel(String subdepName, long depId) {
		this.subdepName = subdepName;
		this.depId = depId;
	}
	
	public SubdepartmentModel(long subdepId, String subdepName, long depId, long createdBy, Timestamp createdDate,
			long modifiedBy, Timestamp modifiedDate, boolean active) {
		super(createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.subdepId = subdepId;
		this.subdepName = subdepName;
		this.depId = depId;
	}

	public long getSubdepId() {
		return subdepId;
	}

	public void setSubdepId(long subdepId) {
		this.subdepId = subdepId;
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
