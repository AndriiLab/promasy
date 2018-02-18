package com.github.andriilab.promasy.presentation.components.dialogs;

import com.github.andriilab.promasy.data.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.domain.IEntity;

/**
 * Listener for {@link AbstractCEDDialog}. Must be implemented by subclass listener
 */
public interface AbstractCEDDialogListener<T extends IEntity> {
    void persistModelEventOccurred(CreateOrUpdateCommand<T> command);
    void getAllEntries();
}
