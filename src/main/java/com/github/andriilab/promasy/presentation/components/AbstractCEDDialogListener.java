package com.github.andriilab.promasy.presentation.components;

import com.github.andriilab.promasy.domain.IEntity;

/**
 * Listener for {@link AbstractCEDDialog}. Must be implemented by subclass listener
 */
public interface AbstractCEDDialogListener<T extends IEntity> {
    void persistModelEventOccurred(T model);
    void getAllEntries();
}
