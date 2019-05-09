package com.github.andriilab.promasy.gui.components.dialogs;

import com.github.andriilab.promasy.app.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.commons.persistence.IEntity;

/**
 * Listener for {@link AbstractCEDDialog}. Must be implemented by subclass listener
 */
public interface AbstractCEDDialogListener<T extends IEntity> {
    void persistModelEventOccurred(CreateOrUpdateCommand<T> command);

    void getAllEntries();
}
