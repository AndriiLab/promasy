package gui.cpv;

import java.util.EventObject;

public class CpvReqEvent extends EventObject {
	private String cpvRequest;
	private boolean sameLvlShow;

	CpvReqEvent(Object source, String cpvRequest, boolean sameLvlShow) {
		super(source);
		this.cpvRequest = cpvRequest;
		this.sameLvlShow = sameLvlShow;
	}

	public String getCpvRequest() {
		return cpvRequest;
	}
	
	public boolean isSameLvlShow(){
		return sameLvlShow;
	}

	public void setCpvRequest(String cpvRequest, boolean sameLvlShow) {
		this.cpvRequest = cpvRequest;
		this.sameLvlShow = sameLvlShow;
	}

}
