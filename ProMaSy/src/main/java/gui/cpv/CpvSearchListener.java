package gui.cpv;

import java.util.EventListener;

public interface CpvSearchListener extends EventListener {

    void cpvSelectionEventOccurred(CpvReqEvent ev);

}
