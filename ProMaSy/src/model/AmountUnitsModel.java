package model;

import java.sql.Timestamp;

public class AmountUnitsModel extends AbstractModel{
	
	private long amUnitId;
	private String amUnitDesc;
	
	public AmountUnitsModel(long amUnitId, String amUnitDesc, long createdBy, 
			Timestamp createdDate, long modifiedBy, Timestamp modifiedDate,
			boolean active) {
		super(createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.amUnitId = amUnitId;
		this.amUnitDesc = amUnitDesc;
	}

	public AmountUnitsModel(String amUnitDesc) {
		this.amUnitDesc = amUnitDesc;
	}

	public AmountUnitsModel() {
		this.amUnitDesc = "";
	}

	public long getAmUnitId() {
		return amUnitId;
	}

	public void setAmUnitId(long amUnitId) {
		this.amUnitId = amUnitId;
	}

	public String getAmUnitDesc() {
		return amUnitDesc;
	}

	public void setAmUnitDesc(String amUnitDesc) {
		this.amUnitDesc = amUnitDesc;
	}
	
	public String toString(){
		return amUnitDesc;
	}

}
