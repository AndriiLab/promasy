package com.github.andriilab.promasy.gui.commons;

import com.github.andriilab.promasy.commons.persistence.IEntity;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.gui.components.panes.ErrorOptionPane;

import javax.swing.*;

public class Validator {
    @SuppressWarnings("unchecked")
    public static <E extends IEntity> boolean isEmptyComboBox(JFrame parent, JComboBox<E> box, String name) {
        E selectedModel = (E) box.getSelectedItem();
        if (selectedModel.equals(EmptyModel.getByClass(selectedModel.getClass()))) {
            ErrorOptionPane.emptyField(parent, name);
            box.requestFocusInWindow();
            return true;
        }
        return false;
    }
}
