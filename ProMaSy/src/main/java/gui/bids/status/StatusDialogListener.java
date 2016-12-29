package gui.bids.status;

import model.StatusModel;

/**
 * Listener for {@link StatusDialog}
 */
public interface StatusDialogListener {

    void statusChangeEventOccured(StatusModel model);
}
