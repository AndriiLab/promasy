package main.java.gui.cpv;

import java.util.EventListener;

public interface CpvSearchListener extends EventListener {

	void cpvEventOccurred(CpvReqEvent ev);

}
