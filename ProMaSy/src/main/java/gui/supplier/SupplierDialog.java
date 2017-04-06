package gui.supplier;

import gui.MainFrame;
import gui.commons.Icons;
import gui.commons.Labels;
import gui.components.AbstractCEDDialog;
import gui.components.PJComboBox;
import gui.components.PJOptionPane;
import model.models.EmptyModel;
import model.models.SupplierModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Creates dialog for suppliers CRUD
 */
public class SupplierDialog extends AbstractCEDDialog<SupplierModel, SupplierDialogListener> {
    private String newSuplTel;
    private String newSuplComment;
    private JTextField telField;
    private JTextPane commentsPane;

    public SupplierDialog(MainFrame parent) {
        super(SupplierModel.class, parent, new Dimension(335, 235), Labels.getProperty("suplDialogSuper"), Labels.getProperty("supplier_ced"), parent.getCreateBidPanel().getSupplierBox());

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
            if (item instanceof SupplierModel) {
                privateModel = (SupplierModel) item;
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
            PJOptionPane.emptyField(parent, Labels.getProperty("name"));
            comboBox.requestFocusInWindow();
            return false;
        }
        newSuplTel = telField.getText();
        if (newSuplTel.isEmpty()) {
            PJOptionPane.emptyField(parent, Labels.getProperty("phone"));
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
    protected SupplierModel createOrUpdate(MainFrame parent) {
        SupplierModel returnModel = privateModel;
        if (!newName.isEmpty() && isSuplDataValid() && listener != null) {
            //if box is not empty by default set to create new model
            int choice = JOptionPane.NO_OPTION;
            //if not a new model was selected - show dialog to confirm edit
            if (!privateModel.equals(emptyModel)) {
                choice = PJOptionPane.renameEntry(parent, oldName, newName);
            }
            //it edit confirmed - updating model
            if (choice == JOptionPane.YES_OPTION) {
                privateModel.setDescription(newName);
                privateModel.setSupplierTel(newSuplTel);
                privateModel.setSupplierComments(newSuplComment);
                privateModel.setUpdated();
                returnModel = privateModel;
                listener.persistModelEventOccurred(privateModel);
            } else if (choice == JOptionPane.NO_OPTION) {
                //if edit is not confirmed or model does not exist - creating new model with specified name
                SupplierModel model = new SupplierModel(newName, newSuplTel, newSuplComment);
                model.setDescription(newName);
                model.setCreated();
                returnModel = model;
                listener.persistModelEventOccurred(model);
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
