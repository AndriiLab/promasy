package model;

import java.sql.Timestamp;

public abstract class AbstractModel {

	long createdBy;
	Timestamp createdDate;
	long modifiedBy;
	Timestamp modifiedDate;
	boolean active;
	
	public AbstractModel() {
		this.createdBy = 0;
		this.createdDate = null;
		this.modifiedBy = 0;
		this.modifiedDate = null;
		this.active = true;
	}
	
	public AbstractModel(long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate,
			boolean active) {
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.active = active;
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

	public void setCreatedDate(Timestamp crearedDate) {
		this.createdDate = crearedDate;
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
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}