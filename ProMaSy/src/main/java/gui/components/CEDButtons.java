package gui.components;

import gui.commons.Icons;
import gui.commons.Labels;

import javax.swing.*;
import java.awt.*;

/**
 * Creates standard buttons "Create", "Edit" and "Delete"
 */
public class CEDButtons {

    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private String propertyName;

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
        return PJOptionPane.deleteEntry(parent, propertyName, entryName);
    }
}
