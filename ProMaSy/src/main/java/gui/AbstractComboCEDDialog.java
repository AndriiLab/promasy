package gui;

import model.models.AbstractModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Abstract dialog with combo box and createOrUpdate, edit, delete buttons
 */
public abstract class AbstractComboCEDDialog<T extends AbstractModel> extends JDialog {
    protected final T emptyModel;
    protected CrEdDelButtons ced;
    protected JButton createButton;
    protected JButton editButton;
    protected JButton deleteButton;
    protected JComboBox<T> comboBox;
    protected T privateModel;
    protected String newName;
    private JButton closeButton;

    public AbstractComboCEDDialog(JFrame parent, T newModel, String windowLabel, String nameCED) {
        super(parent, windowLabel, true);
        emptyModel = newModel;
        setSize(271, 128);
        setLocationRelativeTo(parent);
        setResizable(false);

        Dimension comboBoxDim = new Dimension(150, 25);

        privateModel = emptyModel;
        newName = "";

        DefaultComboBoxModel<T> prodModel = new DefaultComboBoxModel<>();
        comboBox = new JComboBox<>(prodModel);
        comboBox.addItem(emptyModel);
        comboBox.setPreferredSize(comboBoxDim);
        comboBox.setEditable(true);

        ced = new CrEdDelButtons(nameCED);
        createButton = ced.getCreateButton();
        editButton = ced.getEditButton();
        deleteButton = ced.getDeleteButton();
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        closeButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        closeButton.addActionListener(e -> setVisible(false));
    }

    protected void clearDialog() {
        privateModel = emptyModel;
        newName = "";
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    public void setData(java.util.List<T> modelDb) {
        comboBox.removeAllItems();
        comboBox.addItem(emptyModel);
        for (T model : modelDb) {
            if (model.isActive()) {
                comboBox.addItem(model);
            }
        }
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
        buttonsPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(prodPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
