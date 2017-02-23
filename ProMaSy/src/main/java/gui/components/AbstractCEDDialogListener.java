package gui.components;

import model.models.AbstractModel;

/**
 * Listener for {@link AbstractCEDDialog}. Must be implemented by subclass listener
 */
public interface AbstractCEDDialogListener<T extends AbstractModel> {
    void persistModelEventOccurred(T model);
    void getAllEntries();
}
