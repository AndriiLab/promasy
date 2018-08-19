package com.github.andriilab.promasy.presentation.components;

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

    public PJComboBox(ComboBoxModel<T> defaultComboBoxModel) {
        super(defaultComboBoxModel);
    }

    /**
     * Function sets PJComboBox with data with special parameters.
     * First it removes all objects from comboBox and adds emptyModel (if selected)
     *
     * @param db           parametrized list
     * @param emptyModel   com.github.andriilab.promasy.domain.model with default data
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
                this.addItem(model);
            }
        }
        this.repaint();
    }
}
