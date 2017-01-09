package model.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cpv")
public class CPVModel {

    @Id
    @Column(name = "cpv_code")
    private String cpvId;

    @Column(name = "cpv_ukr")
    private String cpvUkr;

    @Column(name = "cpv_eng")
    private String cpvEng;

    @Column(name = "cpv_level")
    private int cpvLevel;

    @Column(name = "terminal")
    private boolean cpvTerminal;

	public CPVModel(String cpvId, String cpvUkr, String cpvEng, 
			int cpvLevel, boolean cpvTerminal) {
		this.cpvId = cpvId;
		this.cpvUkr = cpvUkr;
		this.cpvEng = cpvEng;
		this.cpvLevel = cpvLevel;
		this.cpvTerminal = cpvTerminal;
	}

    public CPVModel() {
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
