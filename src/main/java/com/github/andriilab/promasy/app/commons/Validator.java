package com.github.andriilab.promasy.app.commons;

import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.app.components.panes.ErrorOptionPane;

import javax.swing.*;

public final class Validator {

    private Validator() {
    }

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
