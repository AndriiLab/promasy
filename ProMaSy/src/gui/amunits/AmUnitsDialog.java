package gui.amunits;

import gui.Labels;
import gui.Utils;
import model.AmountUnitsModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This Dialog Create, Modify, and Delete data of Amounts and Units entries in DB
 */
public class AmUnitsDialog extends JDialog implements ActionListener {
    private JButton okButton;
    private JButton createAmUnit;
    private JButton editAmUnit;
    private JButton deleteAmUnit;
    private JComboBox<AmountUnitsModel> amUnitBox;
    private final AmountUnitsModel emptyModel = new AmountUnitsModel();
    private AmUnitsDialogListener listener;
    private AmountUnitsModel privateModel;
    private String newName;

    public AmUnitsDialog(JFrame parent) {
        super(parent, Labels.getProperty("amUnitsDialogSuper"), false);
        setSize(300, 150);
        setLocationRelativeTo(parent);

        privateModel = emptyModel;
        newName = "";

        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboBoxDim = new Dimension(150, 25);

        DefaultComboBoxModel<AmountUnitsModel> amUnitModel = new DefaultComboBoxModel<>();

        amUnitBox = new JComboBox<>(amUnitModel);
        amUnitBox.addItem(emptyModel);
        amUnitBox.setPreferredSize(comboBoxDim);
        amUnitBox.setEditable(true);
        amUnitBox.addActionListener(this);

        createAmUnit = new JButton();
        createAmUnit.setToolTipText(Labels.getProperty("addAmUnit"));
        createAmUnit.setIcon(Utils.createIcon("/images/Add16.gif"));
        createAmUnit.setPreferredSize(buttonDim);
        createAmUnit.setEnabled(true);

        editAmUnit = new JButton();
        editAmUnit.setToolTipText(Labels.getProperty("editAmUnit"));
        editAmUnit.setIcon(Utils.createIcon("/images/Edit16.gif"));
        editAmUnit.setPreferredSize(buttonDim);
        editAmUnit.setEnabled(true);

        deleteAmUnit = new JButton();
        deleteAmUnit.setToolTipText(Labels.getProperty("delAmUnit"));
        deleteAmUnit.setIcon(Utils.createIcon("/images/Delete16.gif"));
        deleteAmUnit.setPreferredSize(buttonDim);
        deleteAmUnit.setEnabled(true);

        okButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        createAmUnit.addActionListener(e -> {
            if (!newName.equals("") && privateModel.equals(emptyModel)) {
                AmountUnitsModel model = new AmountUnitsModel(newName);
                if (listener != null) {
                    amUnitBox.removeAllItems();
                    amUnitBox.addItem(emptyModel);
                    listener.createEventOccurred(model);
                }
            }
            clearDialog();
        });

        editAmUnit.addActionListener(e -> {
            if (!newName.equals("") && !privateModel.equals(emptyModel)) {
                if (listener != null) {
                    amUnitBox.removeAllItems();
                    amUnitBox.addItem(emptyModel);
                    privateModel.setAmUnitDesc(newName);
                    listener.editEventOccurred(privateModel);
                }
            }
            clearDialog();
        });

        deleteAmUnit.addActionListener(e -> {
            if (!privateModel.equals(emptyModel) && listener != null) {
                amUnitBox.removeAllItems();
                amUnitBox.addItem(emptyModel);
                listener.deleteEventOccurred(privateModel);
            }
            clearDialog();
        });

        okButton.addActionListener(e -> setVisible(false));
    }

    private void clearDialog(){
        privateModel = emptyModel;
        newName = "";
    }

    public void setData(java.util.List<AmountUnitsModel> amUnitDb) {
        for (AmountUnitsModel amUnitModel : amUnitDb) {
            amUnitBox.addItem(amUnitModel);
        }
    }

    public void setListener(AmUnitsDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object item = ((JComboBox)e.getSource()).getSelectedItem();
            if(item instanceof AmountUnitsModel && !item.equals(emptyModel)){
                privateModel = (AmountUnitsModel) item;
            } else if (item instanceof String && !item.equals("")){
                newName = (String) item;
            }
    }

    private void layoutControls() {
        JPanel amUnitPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border instituteBorder = BorderFactory.createEtchedBorder();

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        amUnitPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, instituteBorder));

        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets noPadding = new Insets(1, 0, 1, 0);

        amUnitPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        ////// First row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        amUnitPanel.add(amUnitBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        amUnitPanel.add(createAmUnit, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        amUnitPanel.add(editAmUnit, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        amUnitPanel.add(deleteAmUnit, gc);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);

        setLayout(new BorderLayout());
        add(amUnitPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
