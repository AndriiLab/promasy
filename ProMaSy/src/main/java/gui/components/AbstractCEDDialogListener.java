package gui.components;

import model.models.Model;

/**
 * Listener for {@link AbstractCEDDialog}. Must be implemented by subclass listener
 */
public interface AbstractCEDDialogListener<T extends Model> {
    void persistModelEventOccurred(T model);
    void getAllEntries();
}
