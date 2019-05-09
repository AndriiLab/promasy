package com.github.andriilab.promasy.gui.components.dialogs;

import com.github.andriilab.promasy.app.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.commons.persistence.IEntity;
import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.gui.components.EntityComboBox;
import com.github.andriilab.promasy.gui.components.PJComboBox;
import com.github.andriilab.promasy.gui.components.panes.ErrorOptionPane;
import com.github.andriilab.promasy.gui.controller.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

/**
 * Abstract dialog with combo box and createOrUpdate, edit, delete buttons
 */
@SuppressWarnings("unchecked")
public abstract class AbstractCEDDialog<T extends IEntity, U extends AbstractCEDDialogListener<T>> extends JDialog implements ActionListener {
    protected final T emptyModel;
    protected final JButton createButton;
    protected final JButton editButton;
    protected final JButton deleteButton;
    protected final EntityComboBox<T> comboBox;
    protected T privateModel;
    protected String newName;
    protected final JButton applyButton;
    protected final JButton closeButton;
    protected final MainFrame parent;
    protected String oldName;
    protected U listener;
    private final CEDButtons ced;
    private final Class<T> clazz;

    protected AbstractCEDDialog(Class<T> clazz, MainFrame parent, Dimension windowDimension, String windowLabel, String nameCED, EntityComboBox<T> parentComboBox) {
        super(parent, windowLabel, true);
        this.clazz = clazz;
        this.parent = parent;
        emptyModel = createNewInstance();
        setSize(windowDimension);
        setLocationRelativeTo(parent);
        setResizable(false);

        Dimension comboBoxDim = new Dimension(150, 25);

        privateModel = emptyModel;
        newName = "";

        DefaultComboBoxModel<T> prodModel = new DefaultComboBoxModel<>();
        comboBox = new EntityComboBox<>(prodModel);
        comboBox.addItem(emptyModel);
        comboBox.setPreferredSize(comboBoxDim);
        comboBox.setEditable(true);

        ced = new CEDButtons(nameCED);
        createButton = ced.getCreateButton();
        editButton = ced.getEditButton();
        deleteButton = ced.getDeleteButton();
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        closeButton = new JButton(Labels.getProperty("closeBtn"));
        applyButton = new JButton(Labels.getProperty("apply"));

        closeButton.addActionListener(e -> setVisible(false));

        comboBox.addActionListener(this);

        createButton.addActionListener(e -> createOrUpdate(parent));

        editButton.addActionListener(e -> createOrUpdate(parent));

        deleteButton.addActionListener(e -> {
            if (!privateModel.equals(emptyModel) && ced.deleteEntry(parent, privateModel.toString()) && listener != null) {
                privateModel.setDeleted();
                listener.persistModelEventOccurred(new CreateOrUpdateCommand<>(privateModel));
            }
            clearDialog();
        });

        applyButton.addActionListener(e -> {
            T model = createOrUpdate(parent);
            if (model.getLastEditDate() != null) {
                parentComboBox.setSelectedModel(model);
                setVisible(false);
            } else {
                ErrorOptionPane.emptyModelSelected(parent, nameCED);
            }

        });
    }

    protected AbstractCEDDialog(Class<T> clazz, MainFrame parent, String windowLabel, String nameCED, EntityComboBox<T> parentComboBox) {
        this(clazz, parent, new Dimension(271, 128), windowLabel, nameCED, parentComboBox);
        layoutControls();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj instanceof PJComboBox) {
            Object item = ((PJComboBox) e.getSource()).getSelectedItem();
            if (clazz.isInstance(item)) {
                privateModel = (T) item;
                oldName = privateModel.toString();
                if (privateModel.equals(emptyModel)) {
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            } else if (item instanceof String) {
                newName = (String) item;
            }
        }
    }

    private T createNewInstance() {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Logger.errorEvent(clazz, parent, e);
            return null;
        }
    }

    protected void clearDialog() {
        privateModel = emptyModel;
        newName = "";
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    protected T createOrUpdate(MainFrame parent) {
        T returnModel = privateModel;
        if (!newName.isEmpty() && listener != null) {
            //if box is not empty by default set to create new com.github.andriilab.promasy.domain.model
            int choice = JOptionPane.NO_OPTION;
            //if not a new com.github.andriilab.promasy.domain.model was selected - show dialog to confirm edit
            if (!privateModel.equals(emptyModel)) {
                choice = ErrorOptionPane.renameEntry(parent, oldName, newName);
            }
            //it edit confirmed - updating com.github.andriilab.promasy.domain.model
            if (choice == JOptionPane.YES_OPTION) {
                privateModel.setDescription(newName);
                privateModel.setUpdated();
                returnModel = privateModel;
                listener.persistModelEventOccurred(new CreateOrUpdateCommand<>(privateModel));
            } else if (choice == JOptionPane.NO_OPTION) {
                //if edit is not confirmed or com.github.andriilab.promasy.domain.model does not exist - creating new com.github.andriilab.promasy.domain.model with specified name
                T model = createNewInstance();
                model.setDescription(newName);
                model.setCreated();
                returnModel = model;
                listener.persistModelEventOccurred(new CreateOrUpdateCommand<>(model));
            }
            // if cancel pressed - do nothing
        }
        clearDialog();

        return returnModel;
    }

    public void setData(java.util.List<T> modelDb) {
        comboBox.setBoxData(modelDb, emptyModel, true);
    }

    public void setListener(U listener) {
        this.listener = listener;
    }

    protected void layoutControls() {
        JPanel prodPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border prodBorder = BorderFactory.createEtchedBorder();

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        prodPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, prodBorder));

        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets noPadding = new Insets(1, 0, 1, 0);

        prodPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        prodPanel.add(comboBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        prodPanel.add(createButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        prodPanel.add(editButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        prodPanel.add(deleteButton, gc);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        Dimension btnDim = new Dimension(110, 25);
        applyButton.setPreferredSize(btnDim);
        closeButton.setPreferredSize(btnDim);

        buttonsPanel.add(applyButton);
        buttonsPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(prodPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && listener != null) {
            listener.getAllEntries();
        }
        if (parent.getCreateBidPanel().isVisible()) {
            applyButton.setVisible(visible);
            applyButton.getParent().revalidate();
        } else if (visible) {
            applyButton.setVisible(false);
            applyButton.getParent().revalidate();
        }
        super.setVisible(visible);
    }
}
