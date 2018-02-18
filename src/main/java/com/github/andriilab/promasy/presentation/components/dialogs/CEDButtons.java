package com.github.andriilab.promasy.presentation.components.dialogs;

import com.github.andriilab.promasy.presentation.commons.Colors;
import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.panes.ErrorOptionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Creates standard buttons "Create", "Edit" and "Delete"
 */
public class CEDButtons {

    private final JButton createButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final String propertyName;

    public CEDButtons(String propertyName) {
        this.propertyName = propertyName;
        Dimension buttonDim = new Dimension(25, 25);

        createButton = new JButton(Icons.CREATE);
        createButton.setToolTipText(Labels.withSpaceAfter("create") + propertyName);
        createButton.setPreferredSize(buttonDim);
        createButton.setEnabled(true);

        editButton = new JButton(Icons.EDIT);
        editButton.setToolTipText(Labels.withSpaceAfter("edit") + propertyName);
        editButton.setPreferredSize(buttonDim);
        editButton.setEnabled(true);

        deleteButton = new JButton(Icons.DELETE);
        deleteButton.setToolTipText(Labels.withSpaceAfter("delete") + propertyName);
        deleteButton.setPreferredSize(buttonDim);
        deleteButton.setEnabled(true);
    }

    public static JButton getCloseButton() {
        JButton closeButton = new JButton(Icons.CLOSE);
        closeButton.setPreferredSize(new Dimension(15, 15));
        closeButton.setBackground(Colors.RED_LIGHT_SELECTED);
        closeButton.setToolTipText(Labels.getProperty("closeBtn"));

        return closeButton;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public boolean deleteEntry(JFrame parent, String entryName) {
        return ErrorOptionPane.deleteEntry(parent, propertyName, entryName);
    }
}
