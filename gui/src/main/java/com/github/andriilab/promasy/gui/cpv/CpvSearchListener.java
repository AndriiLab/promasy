package com.github.andriilab.promasy.gui.cpv;

import com.github.andriilab.promasy.app.queries.cpv.CpvRequestQuery;

import java.util.EventListener;

public interface CpvSearchListener extends EventListener {

    void cpvSelectionEventOccurred(CpvRequestQuery ev);

}
