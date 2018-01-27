package com.github.andriilab.promasy.presentation.cpv;

import java.util.EventListener;

public interface CpvSearchListener extends EventListener {

    void cpvSelectionEventOccurred(CpvRequestContainer ev);

}
