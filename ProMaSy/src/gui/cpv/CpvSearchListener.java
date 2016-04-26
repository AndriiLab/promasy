package gui.cpv;

import java.util.EventListener;

public interface CpvSearchListener extends EventListener {

	void cpaEventOccurred(CpvReqEvent ev);

}
