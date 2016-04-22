package gui.cpv;

import java.util.EventListener;

public interface CpvSearchListener extends EventListener {

	public void cpvEventOccured(CpvReqEvent ev);

}
