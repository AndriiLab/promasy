package com.github.andriilab.promasy.presentation.supplier;

import com.github.andriilab.promasy.data.commands.CreateOrUpdateCommand;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.item.entities.Supplier;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.AbstractCEDDialog;
import com.github.andriilab.promasy.presentation.components.ErrorOptionPane;
import com.github.andriilab.promasy.presentation.components.PJComboBox;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Creates dialog for suppliers CRUD
 */
public class SupplierDialog extends AbstractCEDDialog<Supplier, SupplierDialogListener> {
    private String newSuplTel;
    private String newSuplComment;
    private final JTextField telField;
    private final JTextPane commentsPane;

    public SupplierDialog(MainFrame parent) {
        super(Supplier.class, parent, new Dimension(335, 235), Labels.getProperty("suplDialogSuper"), Labels.getProperty("supplier_ced"), parent.getCreateBidPanel().getSupplierBox());

        newSuplTel = EmptyModel.STRING;
        newSuplComment = EmptyModel.STRING;

        telField = new JTextField(13);
        telField.setPreferredSize(new Dimension(150, 25));
        telField.setEditable(true);

        commentsPane = new JTextPane();
        commentsPane.setPreferredSize(new Dimension(140, 75));

        layoutControls();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj instanceof PJComboBox) {
            Object item = ((PJComboBox) e.getSource()).getSelectedItem();
            if (item instanceof Supplier) {
                privateModel = (Supplier) item;
                if (privateModel.equals(emptyModel)) {
                    telField.setText(EmptyModel.STRING);
                    commentsPane.setText(EmptyModel.STRING);
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    oldName = privateModel.toString();
                    telField.setText(privateModel.getSupplierTel());
                    commentsPane.setText(privateModel.getSupplierComments());
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            } else if (item instanceof String) {
                newName = (String) item;
            }
        }
    }

    private boolean isSuplDataValid() {
        if (newName.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("name"));
            comboBox.requestFocusInWindow();
            return false;
        }
        newSuplTel = telField.getText();
        if (newSuplTel.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("phone"));
            telField.requestFocusInWindow();
            return false;
        } else if (newSuplTel.length() > 20) {
            JOptionPane.showMessageDialog(parent, Labels.getProperty("telephoneTooLong"), Labels.getProperty("wrongFormat"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            telField.requestFocusInWindow();
            return false;
        }
        newSuplComment = commentsPane.getText();
        return true;
    }

    @Override
    protected Supplier createOrUpdate(MainFrame parent) {
        Supplier returnModel = privateModel;
        if (!newName.isEmpty() && isSuplDataValid() && listener != null) {
            //if box is not empty by default set to create new com.github.andriilab.promasy.domain.model
            int choice = JOptionPane.NO_OPTION;
            //if not a new com.github.andriilab.promasy.domain.model was selected - show dialog to confirm edit
            if (!privateModel.equals(emptyModel)) {
                choice = ErrorOptionPane.renameEntry(parent, oldName, newName);
            }
            //it edit confirmed - updating com.github.andriilab.promasy.domain.model
            if (choice == JOptionPane.YES_OPTION) {
                privateModel.setDescription(newName);
                privateModel.setSupplierTel(newSuplTel);
                privateModel.setSupplierComments(newSuplComment);
                privateModel.setUpdated();
                returnModel = privateModel;
                listener.persistModelEventOccurred(new CreateOrUpdateCommand<>(privateModel));
            } else if (choice == JOptionPane.NO_OPTION) {
                //if edit is not confirmed or com.github.andriilab.promasy.domain.model does not exist - creating new com.github.andriilab.promasy.domain.model with specified name
                Supplier model = new Supplier(newName, newSuplTel, newSuplComment);
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

    @Override
    protected void clearDialog() {
        newSuplTel = EmptyModel.STRING;
        newSuplComment = EmptyModel.STRING;
        telField.setText(EmptyModel.STRING);
        commentsPane.setText(EmptyModel.STRING);
    }

    @Override
    protected void layoutControls() {
        JPanel supplPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border suplBorder = BorderFactory.createEtchedBorder();

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        supplPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, suplBorder));

        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets noPadding = new Insets(1, 0, 1, 0);

        supplPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        ////// First row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        supplPanel.add(new JLabel(Labels.withColon("name")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        supplPanel.add(comboBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        supplPanel.add(createButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        supplPanel.add(editButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        supplPanel.add(deleteButton, gc);

        ///////Next row///////////
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        supplPanel.add(new JLabel(Labels.withColon("phone")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        supplPanel.add(telField, gc);

        ///////Next row///////////
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.NORTHEAST;
        gc.insets = smallPadding;
        supplPanel.add(new JLabel(Labels.withColon("contacts")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        supplPanel.add(new JScrollPane(commentsPane), gc);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(applyButton);
        buttonsPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(supplPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
