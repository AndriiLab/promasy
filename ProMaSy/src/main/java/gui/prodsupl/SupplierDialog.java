package gui.prodsupl;

import gui.commons.Labels;
import gui.components.CEDButtons;
import gui.components.PJComboBox;
import gui.components.PJOptionPane;
import model.models.EmptyModel;
import model.models.SupplierModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Creates dialog for suppliers CRUD
 */
public class SupplierDialog extends JDialog {
    private JButton okButton;
    private JButton createSupl;
    private JButton editSupl;
    private JButton deleteSupl;
    private PJComboBox<SupplierModel> suplBox;
    private SupplierDialogListener listener;
    private SupplierModel privateSuplModel;
    private String newSuplName;
    private String newSuplTel;
    private String newSuplComment;
    private JTextField telField;
    private JTextPane commentsPane;
    private JFrame parent;

    public SupplierDialog(JFrame parent) {
        super(parent, Labels.getProperty("suplDialogSuper"), true);
        this.parent = parent;
        setSize(335, 235);
        setResizable(false);
        setLocationRelativeTo(parent);

        Dimension comboBoxDim = new Dimension(150, 25);

        newSuplName = EmptyModel.STRING;
        newSuplTel = EmptyModel.STRING;
        newSuplComment = EmptyModel.STRING;

        privateSuplModel = EmptyModel.SUPPLIER;

        DefaultComboBoxModel<SupplierModel> suplModel = new DefaultComboBoxModel<>();
        suplBox = new PJComboBox<>(suplModel);
        suplBox.addItem(EmptyModel.SUPPLIER);
        suplBox.setPreferredSize(comboBoxDim);
        suplBox.setEditable(true);

        CEDButtons ced = new CEDButtons(Labels.getProperty("supplier_ced"));
        createSupl = ced.getCreateButton();
        editSupl = ced.getEditButton();
        deleteSupl = ced.getDeleteButton();
        editSupl.setEnabled(false);
        deleteSupl.setEnabled(false);

        telField = new JTextField(13);
        telField.setPreferredSize(comboBoxDim);
        telField.setEditable(true);

        commentsPane = new JTextPane();
        commentsPane.setPreferredSize(new Dimension(140, 75));

        okButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        suplBox.addActionListener(e -> {
            Object item = ((JComboBox) e.getSource()).getSelectedItem();
            if (item instanceof SupplierModel) {
                if (!item.equals(EmptyModel.SUPPLIER)) {
                    privateSuplModel = (SupplierModel) item;
                    telField.setText(privateSuplModel.getSupplierTel());
                    commentsPane.setText(privateSuplModel.getSupplierComments());
                    newSuplName = privateSuplModel.getSupplierName();

                    createSupl.setEnabled(false);
                    editSupl.setEnabled(true);
                    deleteSupl.setEnabled(true);
                } else if (item.equals(EmptyModel.SUPPLIER)) {
                    telField.setText(EmptyModel.STRING);
                    commentsPane.setText(EmptyModel.STRING);

                    createSupl.setEnabled(true);
                    editSupl.setEnabled(false);
                    deleteSupl.setEnabled(false);
                }
            } else if (item instanceof String) {
                newSuplName = (String) item;
            }
        });

        createSupl.addActionListener(e -> {
            if (isSuplDataValid() && privateSuplModel.equals(EmptyModel.SUPPLIER)) {
                SupplierModel model = new SupplierModel(newSuplName, newSuplTel, newSuplComment);
                if (listener != null) {
                    model.setCreated();
                    listener.persistModelEventOccurred(model);
                    telField.setText(EmptyModel.STRING);
                    commentsPane.setText(EmptyModel.STRING);
                }
            }
            newSuplName = EmptyModel.STRING;
            newSuplTel = EmptyModel.STRING;
            newSuplComment = EmptyModel.STRING;
            privateSuplModel = EmptyModel.SUPPLIER;
        });

        editSupl.addActionListener(e -> {
            if (isSuplDataValid() && !privateSuplModel.equals(EmptyModel.SUPPLIER)
                    && listener != null) {
                privateSuplModel.setSupplierName(newSuplName);
                privateSuplModel.setSupplierTel(newSuplTel);
                privateSuplModel.setSupplierComments(newSuplComment);
                privateSuplModel.setUpdated();
                listener.persistModelEventOccurred(privateSuplModel);
                telField.setText(EmptyModel.STRING);
                commentsPane.setText(EmptyModel.STRING);
            }
            newSuplName = EmptyModel.STRING;
            newSuplTel = EmptyModel.STRING;
            newSuplComment = EmptyModel.STRING;
            privateSuplModel = EmptyModel.SUPPLIER;
        });

        deleteSupl.addActionListener(e -> {
            if (!privateSuplModel.equals(EmptyModel.SUPPLIER) && ced.deleteEntry(parent, privateSuplModel.getSupplierName()) && listener != null) {
                privateSuplModel.setDeleted();
                listener.persistModelEventOccurred(privateSuplModel);
                telField.setText(EmptyModel.STRING);
                commentsPane.setText(EmptyModel.STRING);
            }
        });

        okButton.addActionListener(e -> setVisible(false));
    }

    public void setSuplData(java.util.List<SupplierModel> suplDb) {
        suplBox.setBoxData(suplDb, EmptyModel.SUPPLIER, true);
    }

    public void setListener(SupplierDialogListener listener) {
        this.listener = listener;
    }

    private boolean isSuplDataValid() {
        if (newSuplName.isEmpty()) {
            PJOptionPane.emptyField(parent, Labels.getProperty("name"));
            suplBox.requestFocusInWindow();
            return false;
        }
        newSuplTel = telField.getText();
        if (newSuplName.isEmpty()) {
            PJOptionPane.emptyField(parent, Labels.getProperty("phone"));
            telField.requestFocusInWindow();
            return false;
        } else if (newSuplTel.length() > 20) {
            JOptionPane.showMessageDialog(parent, Labels.getProperty("telephoneTooLong"), Labels.getProperty("wrongFormat"), JOptionPane.ERROR_MESSAGE);
            telField.requestFocusInWindow();
            return false;
        }
        newSuplComment = commentsPane.getText();
        return true;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && listener != null) {
            listener.getAllSuppliers();
        }
        super.setVisible(visible);
    }

    private void layoutControls() {
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
        supplPanel.add(suplBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        supplPanel.add(createSupl, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        supplPanel.add(editSupl, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        supplPanel.add(deleteSupl, gc);

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
        buttonsPanel.add(okButton);

        setLayout(new BorderLayout());
        add(supplPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
