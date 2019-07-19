package com.github.andriilab.promasy.app.view.bids.status;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.IEntity;

/**
 * Listener for {@link StatusDialog}
 */
public interface StatusDialogListener {
    <T extends IEntity> void persistModelEventOccurred(ICommand<T> command);
}
