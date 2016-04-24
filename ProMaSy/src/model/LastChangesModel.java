package model;

import java.sql.Timestamp;

public class LastChangesModel {
	private int numElements;
	private Timestamp lastCreated;
	private Timestamp lastModified;
	
	public LastChangesModel(int numElements, Timestamp lastCreated, Timestamp lastModified) {
		super();
		this.numElements = numElements;
		this.lastCreated = lastCreated;
		this.lastModified = lastModified;
	}

	public int getNumElements() {
		return numElements;
	}

	public void setNumElements(int numElements) {
		this.numElements = numElements;
	}

	public Timestamp getLastCreated() {
		return lastCreated;
	}

	public void setLastCreated(Timestamp lastCreated) {
		this.lastCreated = lastCreated;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

}
