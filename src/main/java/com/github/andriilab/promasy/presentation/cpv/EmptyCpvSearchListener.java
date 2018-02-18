package com.github.andriilab.promasy.presentation.cpv;

import com.github.andriilab.promasy.data.queries.cpv.CpvRequestQuery;
import com.github.andriilab.promasy.presentation.components.AbstractEmptyListener;

public class EmptyCpvSearchListener extends AbstractEmptyListener implements CpvSearchListener {
    @Override
    public void cpvSelectionEventOccurred(CpvRequestQuery ev) {
        logEmptyListener(CpvSearchListener.class);
    }
}
