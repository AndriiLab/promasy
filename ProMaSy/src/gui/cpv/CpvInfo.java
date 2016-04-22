package gui.cpv;

public class CpvInfo {
	private String cpvCode;
	private String cpvUkr;
	private String cpvEng;

	public CpvInfo(String cpv_code, String cpv_ukr, String cpv_eng) {
		this.cpvCode = cpv_code;
		this.cpvUkr = cpv_ukr;
		this.cpvEng = cpv_eng;
	}

	public String getCpvCode() {
		return cpvCode;
	}

	public String getCpvEng() {
		return cpvEng;
	}

	public String toString() {
		return cpvUkr;
	}
}
