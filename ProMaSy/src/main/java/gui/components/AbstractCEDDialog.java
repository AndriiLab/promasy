package gui.components;

import gui.MainFrame;
import gui.Utils;
import gui.commons.Labels;
import model.models.AbstractModel;
import model.models.EmptyModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Abstract dialog with combo box and createOrUpdate, edit, delete buttons
 */
public abstract class AbstractCEDDialog<T extends AbstractModel, U extends AbstractCEDDialogListener<T>> extends JDialog {
    private final T emptyModel;
    private CEDButtons ced;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private PJComboBox<T> comboBox;
    private T privateModel;
    private Class<T> clazz;
    private String newName;
    private JButton applyButton;
    private JButton closeButton;
    private MainFrame parent;
    private String oldName;
    private U listener;

    public AbstractCEDDialog(Class<T> clazz, MainFrame parent, String windowLabel, String nameCED, PJComboBox<T> parentComboBox) {
        super(parent, windowLabel, true);
        this.clazz = clazz;
        this.parent = parent;
        emptyModel = createNewInstance();
        setSize(271, 128);
        setLocationRelativeTo(parent);
        setResizable(false);

        Dimension comboBoxDim = new Dimension(150, 25);

        privateModel = emptyModel;
        newName = EmptyModel.STRING;

        DefaultComboBoxModel<T> prodModel = new DefaultComboBoxModel<>();
        comboBox = new PJComboBox<>(prodModel);
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

        layoutControls();

        closeButton.addActionListener(e -> setVisible(false));

        comboBox.addActionListener(e -> {
            Object item = ((JComboBox) e.getSource()).getSelectedItem();
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
        });

        createButton.addActionListener(e -> createOrUpdate(parent));

        editButton.addActionListener(e -> createOrUpdate(parent));

        deleteButton.addActionListener(e -> {
            if (!privateModel.equals(emptyModel) && ced.deleteEntry(parent, privateModel.toString()) && listener != null) {
                privateModel.setDeleted();
                listener.persistModelEventOccurred(privateModel);
            }
            clearDialog();
        });

        applyButton.addActionListener(e -> {
            parentComboBox.setSelectedModel(createOrUpdate(parent));
            setVisible(false);
        });
    }

    private T createNewInstance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            //TODO handle exteption
            e.printStackTrace();
            return null;
        }
    }

    protected void clearDialog() {
        privateModel = emptyModel;
        newName = EmptyModel.STRING;
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private T createOrUpdate(MainFrame parent) {
        T returnModel = emptyModel;
        if (!newName.isEmpty() && listener != null) {
            //if box is not empty by default set to create new model
            int choice = JOptionPane.NO_OPTION;
            //if not a new model was selected - show dialog to confirm edit
            if (!privateModel.equals(emptyModel)) {
                choice = PJOptionPane.renameEntry(parent, oldName, newName);
            }
            //it edit confirmed - updating model
            if (choice == JOptionPane.YES_OPTION) {
                privateModel.setDescription(newName);
                privateModel.setUpdated();
                returnModel = privateModel;
                listener.persistModelEventOccurred(privateModel);
            } else if (choice == JOptionPane.NO_OPTION) {
                //if edit is not confirmed or model does not exist - creating new model with specified name
                T model = createNewInstance();
                model.setDescription(newName);
                model.setCreated();
                returnModel = model;
                listener.persistModelEventOccurred(model);
            }
            // if cancel pressed - do nothing
        } else {
            PJOptionPane.emptyField(parent, null);
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

    private void layoutControls() {
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

        buttonsPanel.add(applyButton);
        buttonsPanel.add(closeButton);
        Utils.setPreferredButtonSizes(applyButton, closeButton);

        setLayout(new BorderLayout());
        add(prodPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && listener != null) {
            listener.getAllEntries();
        }
        if (parent.getCreateBidDialog().isVisible()) {
            applyButton.setVisible(visible);
            applyButton.getParent().revalidate();
        } else if (visible) {
            applyButton.setVisible(false);
            applyButton.getParent().revalidate();
        }
        super.setVisible(visible);
    }
}
