package model;

import java.sql.Timestamp;

public class InstituteModel extends AbstractModel {
	private long instId;
	private String instName;
	
	public InstituteModel() {
		this.instName = "";
	}
	
	public InstituteModel(String instName) {
		this.instName = instName;
	}

	public InstituteModel(long instId, String instName,long createdBy, 
			Timestamp createdDate, long modifiedBy, Timestamp modifiedDate,
			boolean active) {
		super(instId, createdDate, instId, createdDate, active);
		this.instId = instId;
		this.instName = instName;
	}

	public long getInstId() {
		return instId;
	}

	public void setInstId(long instId) {
		this.instId = instId;
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
