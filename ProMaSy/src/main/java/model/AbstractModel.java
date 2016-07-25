package main.java.model;

import java.sql.Timestamp;

public abstract class AbstractModel {

    private long modelId;
	private long createdBy;
	private Timestamp createdDate;
	private long modifiedBy;
	private Timestamp modifiedDate;
	private boolean active;
	
	AbstractModel() {
		this.createdBy = 0;
		this.createdDate = null;
		this.modifiedBy = 0;
		this.modifiedDate = null;
		this.active = true;
	}
	
	AbstractModel(long modelId, long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate,
				  boolean active) {
        this.modelId = modelId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.active = active;
	}

    public long getModelId() {
        return modelId;
    }

    void setModelId(long modelId) {
        this.modelId = modelId;
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
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}