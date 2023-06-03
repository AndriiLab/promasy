package com.github.andriilab.promasy.app.components.dialogs;

import com.github.andriilab.promasy.data.commands.ICommand;
import com.github.andriilab.promasy.domain.IEntity;

/**
 * Listener for {@link AbstractCEDDialog}. Must be implemented by subclass listener
 */
public interface AbstractCEDDialogListener<T extends IEntity> {
    void persistModelEventOccurred(ICommand<T> command);

    void getAllEntries();
}
