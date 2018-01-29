package com.github.andriilab.promasy.presentation.cpv;

import com.github.andriilab.promasy.data.queries.cpv.CpvRequestQuery;

import java.util.EventListener;

public interface CpvSearchListener extends EventListener {

    void cpvSelectionEventOccurred(CpvRequestQuery ev);

}
