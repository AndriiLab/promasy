package com.github.andriilab.promasy.app.components;

import com.github.andriilab.promasy.domain.IEntity;

import javax.swing.*;
import java.util.List;

/**
 * {@link JComboBox} for entity models
 */
public class EntityComboBox<T extends IEntity> extends PJComboBox<T> {
    public EntityComboBox() {
        super();
    }

    public EntityComboBox(T[] values) {
        super(values);
    }

    public EntityComboBox(ComboBoxModel<T> defaultComboBoxModel) {
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
    @Override
    public void setBoxData(List<T> db, T emptyModel, boolean isFirstEmpty) {
        this.removeAllItems();
        if (isFirstEmpty) {
            this.addItem(emptyModel);
        }
        if (!isFirstEmpty && (db == null || db.isEmpty())) {
            this.addItem(emptyModel);
        } else if (db != null && !db.isEmpty()) {
            for (T model : db) {
                if (model.isActive()) {
                    this.addItem(model);
                }
            }
        }
        this.repaint();
    }

    public void setSelectedModel(T model) {
        if (model != null && model.getModelId() != 0L) {
            setSelectedItem(model);
        }
    }
}
