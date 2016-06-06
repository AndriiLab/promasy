package gui.prodsupl;

import gui.Labels;
import gui.Utils;
import model.SupplierModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by laban on 04.06.2016.
 */
public class SupplierDialog extends JDialog {
    private JButton okButton;
    private JButton createSupl;
    private JButton editSupl;
    private JButton deleteSupl;
    private JComboBox<SupplierModel> suplBox;
    private final SupplierModel emptySuplModel = new SupplierModel();
    private SupplierDialogListener listener;
    private SupplierModel privateSuplModel;
    private String newSuplName;
    private String newSuplTel;
    private String newSuplComment;
    private JTextField telField;
    private JTextPane commentsPane;

    public SupplierDialog(JFrame parent) {
        super(parent, Labels.getProperty("suplDialogSuper"), false);
        setSize(280, 250);
        setLocationRelativeTo(parent);

        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboBoxDim = new Dimension(150, 25);

        privateSuplModel = emptySuplModel;

        DefaultComboBoxModel<SupplierModel> suplModel = new DefaultComboBoxModel<>();
        suplBox = new JComboBox<>(suplModel);
        suplBox.addItem(emptySuplModel);
        suplBox.setPreferredSize(comboBoxDim);
        suplBox.setEditable(true);

        createSupl = new JButton();
        createSupl.setToolTipText(Labels.getProperty("addSupl"));
        createSupl.setIcon(Utils.createIcon("/images/Add16.gif"));
        createSupl.setPreferredSize(buttonDim);
        createSupl.setEnabled(true);
        editSupl = new JButton();
        editSupl.setToolTipText(Labels.getProperty("editSupl"));
        editSupl.setIcon(Utils.createIcon("/images/Edit16.gif"));
        editSupl.setPreferredSize(buttonDim);
        editSupl.setEnabled(true);
        deleteSupl = new JButton();
        deleteSupl.setToolTipText(Labels.getProperty("delSupl"));
        deleteSupl.setIcon(Utils.createIcon("/images/Delete16.gif"));
        deleteSupl.setPreferredSize(buttonDim);
        deleteSupl.setEnabled(true);

        telField = new JTextField(10);
        telField.setPreferredSize(comboBoxDim);
        telField.setEditable(true);
        //TODO limit size of input to 20 char

        commentsPane = new JTextPane();
        commentsPane.setPreferredSize(new Dimension(140, 75));

        okButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        suplBox.addActionListener(e -> {
            Object item = ((JComboBox) e.getSource()).getSelectedItem();
            if (item instanceof SupplierModel) {
                if (!item.equals(emptySuplModel)) {
                    privateSuplModel = (SupplierModel) item;
                    telField.setText(privateSuplModel.getSupplierTel());
                    commentsPane.setText(privateSuplModel.getSupplierComments());
                } else if (item.equals(emptySuplModel)) {
                    telField.setText("");
                    commentsPane.setText("");
                }
            } else if (item instanceof String && !item.equals(null)) {
                newSuplName = (String) item;
            }
        });

        createSupl.addActionListener(e -> {
            if (isSuplDataValid() && privateSuplModel.equals(emptySuplModel)) {
                newSuplComment = commentsPane.getText();
                SupplierModel model = new SupplierModel(newSuplName, newSuplTel, newSuplComment);
                if (listener != null) {
                    suplBox.removeAllItems();
                    suplBox.addItem(emptySuplModel);
                    listener.createSuplEventOccurred(model);
                    telField.setText("");
                    commentsPane.setText("");
                }
            }
            newSuplName = null;
            newSuplTel = null;
            newSuplComment = null;
            privateSuplModel = emptySuplModel;
        });

        editSupl.addActionListener(e -> {
            if (isSuplDataValid() && !privateSuplModel.equals(emptySuplModel)
                    && listener != null) {
                newSuplComment = commentsPane.getText();
                privateSuplModel.setSupplierName(newSuplName);
                privateSuplModel.setSupplierTel(newSuplTel);
                privateSuplModel.setSupplierComments(newSuplComment);
                suplBox.removeAllItems();
                suplBox.addItem(emptySuplModel);
                listener.editSuplEventOccurred(privateSuplModel);
                telField.setText("");
                commentsPane.setText("");
            }
            newSuplName = null;
            newSuplTel = null;
            newSuplComment = null;
            privateSuplModel = emptySuplModel;
        });

        deleteSupl.addActionListener(e -> {
            if (!privateSuplModel.equals(emptySuplModel) && listener != null) {
                suplBox.removeAllItems();
                suplBox.addItem(emptySuplModel);
                listener.deleteSuplEventOccurred(privateSuplModel);
                telField.setText("");
                commentsPane.setText("");
            }
        });

        okButton.addActionListener(e -> setVisible(false));
    }

    public void setSuplData(java.util.List<SupplierModel> suplDb) {
        for (SupplierModel suplModel : suplDb) {
            suplBox.addItem(suplModel);
        }
    }

    public void setListener(SupplierDialogListener listener) {
        this.listener = listener;
    }

    private boolean isSuplDataValid() {
        newSuplTel = telField.getText();
        return newSuplName != null
                && !newSuplName.equals("")
                && newSuplTel != null
                && !newSuplTel.equals("");
    }

    private void layoutControls() {
        JPanel supplPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border suplBorder = BorderFactory.createTitledBorder(Labels.getProperty("suplBorder"));

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
        gc.gridx = 0;
        gc.gridy++;
        gc.insets = smallPadding;
        supplPanel.add(telField, gc);

        ///////Next row///////////
        gc.gridx = 0;
        gc.gridy++;
        gc.insets = smallPadding;
        supplPanel.add(new JScrollPane(commentsPane), gc);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);

        setLayout(new BorderLayout());
        add(supplPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
