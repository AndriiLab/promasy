package gui.cpv;

import java.util.EventListener;

public interface CpvSearchListener extends EventListener {

    void cpvSelectionEventOccurred(CpvRequestContainer ev);

    void getTopCodes();
}
