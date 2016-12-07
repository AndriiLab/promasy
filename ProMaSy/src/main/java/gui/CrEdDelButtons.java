package main.java.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Creates standard buttons "Create", "Edit" and "Delete"
 */
public class CrEdDelButtons {

    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;

    public CrEdDelButtons(String createButtonName, String editButtonName, String deleteButtonName) {
        Dimension buttonDim = new Dimension(25, 25);

        createButton = new JButton(Icons.CREATE);
        createButton.setToolTipText(createButtonName);
        createButton.setPreferredSize(buttonDim);
        createButton.setEnabled(true);

        editButton = new JButton(Icons.EDIT);
        editButton.setToolTipText(editButtonName);
        editButton.setPreferredSize(buttonDim);
        editButton.setEnabled(true);

        deleteButton = new JButton(Icons.DELETE);
        deleteButton.setToolTipText(deleteButtonName);
        deleteButton.setPreferredSize(buttonDim);
        deleteButton.setEnabled(true);
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
}
