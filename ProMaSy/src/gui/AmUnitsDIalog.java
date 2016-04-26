package gui;

import model.AmountUnitsModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by laban on 26.04.2016.
 */
public class AmUnitsDialog extends JDialog {
    private JButton okButton;
    private JButton createAmUnit;
    private JButton editAmUnit;
    private JButton deleteAmUnit;
    private JComboBox<AmountUnitsModel> amUnitBox;
    private final AmountUnitsModel emptyModel = new AmountUnitsModel();

    public AmUnitsDialog(JFrame parent) {
        super(parent, Labels.getProperty("amUnitsDialogSuper"), false);
        setSize(300, 150);
        setLocationRelativeTo(parent);

        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboBoxDim = new Dimension(150, 25);

        DefaultComboBoxModel<AmountUnitsModel> amUnitModel = new DefaultComboBoxModel<>();
        amUnitBox = new JComboBox<>(amUnitModel);
        amUnitBox.addItem(emptyModel);
        amUnitBox.setPreferredSize(comboBoxDim);

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
            if(amUnitBox.getSelectedItem() instanceof String){
                String newAmUnit = (String) amUnitBox.getSelectedItem();
                System.out.println(newAmUnit);
            }
        });

        editAmUnit.addActionListener(e -> {
            if(amUnitBox.getSelectedItem() instanceof AmountUnitsModel) {
                AmountUnitsModel model = (AmountUnitsModel) amUnitBox.getSelectedItem();
                if (!model.equals(emptyModel)) {
                    System.out.println(model.getAmUnitDesc());
                }
            }
        });

        deleteAmUnit.addActionListener(e -> {
            if(amUnitBox.getSelectedItem() instanceof AmountUnitsModel){
                AmountUnitsModel model = (AmountUnitsModel) amUnitBox.getSelectedItem();
                if (!model.equals(emptyModel)) {
                    System.out.println(model.getAmUnitDesc()+" will be deleted");
                }
            }
        });

        okButton.addActionListener(e -> setVisible(false));
    }

    public void setData(java.util.List<AmountUnitsModel> amUnitDb){
        for (AmountUnitsModel amUnitModel : amUnitDb){
            amUnitBox.addItem(amUnitModel);
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
