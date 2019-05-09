package com.github.andriilab.promasy.gui.cpv;

import com.github.andriilab.promasy.app.queries.cpv.CpvRequestQuery;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

public class EmptyCpvSearchListener extends AbstractEmptyListener implements CpvSearchListener {
    @Override
    public void cpvSelectionEventOccurred(CpvRequestQuery ev) {
        logEmptyListener(CpvSearchListener.class);
    }
}
