package model;

public class CPVModel extends AbstractModel {
	private String cpvId;
	private String cpvUkr;
	private String cpvEng;
	private int cpvLevel;
	private boolean cpvTerminal;

	public CPVModel(String cpvId, String cpvUkr, String cpvEng, 
			int cpvLevel, boolean cpvTerminal) {
		//WARNING no super method
		this.cpvId = cpvId;
		this.cpvUkr = cpvUkr;
		this.cpvEng = cpvEng;
		this.cpvLevel = cpvLevel;
		this.cpvTerminal = cpvTerminal;
	}

	public String getCpvId() {
		return cpvId;
	}

	public void setCpvId(String cpvId) {
		this.cpvId = cpvId;
	}

	public String getCpvUkr() {
		return cpvUkr;
	}

	public void setCpvUkr(String cpvUkr) {
		this.cpvUkr = cpvUkr;
	}

	public String getCpvEng() {
		return cpvEng;
	}

	public void setCpvEng(String cpvEng) {
		this.cpvEng = cpvEng;
	}

	public int getCpvLevel() {
		return cpvLevel;
	}

	public void setCpvLevel(int cpvLevel) {
		this.cpvLevel = cpvLevel;
	}

	public boolean isCpvTerminal() {
		return cpvTerminal;
	}

	public void setCpvTerminal(boolean cpvTerminal) {
		this.cpvTerminal = cpvTerminal;
	}

	@Override
	public String toString() {
		return cpvUkr;
	}

}
