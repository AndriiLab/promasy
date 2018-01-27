package com.github.andriilab.promasy.presentation.components;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.IEntity;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * {@link JComboBox} with additional functions
 */
public class PJComboBox<T> extends JComboBox<T> {

    public static final Dimension COMBOBOX_DIMENSION = new Dimension(225, 20);

    public PJComboBox() {
        super();
    }

    public PJComboBox(T[] values) {
        super(values);
    }

    public PJComboBox(DefaultComboBoxModel defaultComboBoxModel) {
        super(defaultComboBoxModel);
    }

    /**
     * Function sets PJComboBox with data with special parameters.
     * First it removes all objects from comboBox and adds emptyModel (if selected)
     * Next it adds only models where com.github.andriilab.promasy.domain.model.isActive()
     *
     * @param db           parametrized list, which implements {@link IEntity} interface
     * @param emptyModel   com.github.andriilab.promasy.domain.model with default data which implements {@link IEntity} interface
     * @param isFirstEmpty add first emptyModel to comboBox
     */

    public void setBoxData(List<T> db, T emptyModel, boolean isFirstEmpty) {
        this.removeAllItems();
        if (isFirstEmpty) {
            this.addItem(emptyModel);
        }
        if (!isFirstEmpty && (db == null || db.isEmpty())) {
            this.addItem(emptyModel);
        } else if (db != null && !db.isEmpty()) {
            for (T model : db) {
                if (model instanceof IEntity) {
                    if (((AbstractEntity) model).isActive()) {
                        this.addItem(model);
                    }
                } else {
                    this.addItem(model);
                }
            }
        }
        this.repaint();
    }

    public <T extends IEntity> void setSelectedModel(T model) {
        if (model != null && model.getModelId() != 0L) {
            setSelectedItem(model);
        }
    }
}
