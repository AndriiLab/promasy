package com.github.andriilab.promasy.gui.bids.status;

import com.github.andriilab.promasy.domain.bid.entities.BidStatus;
import com.github.andriilab.promasy.gui.components.AbstractEmptyListener;

public class EmptyStatusDialogListener extends AbstractEmptyListener implements StatusDialogListener {
    @Override
    public void persistModelEventOccurred(BidStatus bidModel) {
        logEmptyListener(StatusDialogListener.class);
    }
}
