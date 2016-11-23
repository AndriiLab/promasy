package main.java.gui.bids.status;

import main.java.model.StatusModel;

/**
 * Listener for {@link StatusDialog}
 */
public interface StatusDialogListener {

    void statusChangeEventOccured(StatusModel model);
}
