package main.java.model;

import java.sql.Timestamp;

public class AmountUnitsModel extends AbstractModel{
	
	private String amUnitDesc;
	
	public AmountUnitsModel(long amUnitId, String amUnitDesc, long createdBy, 
			Timestamp createdDate, long modifiedBy, Timestamp modifiedDate,
			boolean active) {
		super(amUnitId, createdBy, createdDate, modifiedBy, modifiedDate, active);
		this.amUnitDesc = amUnitDesc;
	}

	public AmountUnitsModel(String amUnitDesc) {
		this.amUnitDesc = amUnitDesc;
	}

	public AmountUnitsModel() {

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
