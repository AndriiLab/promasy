package main.java.gui.prodsupl;

import main.java.gui.CrEdDelButtons;
import main.java.gui.Labels;
import main.java.model.ProducerModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Class for CRUD of item Producers
 */
public class ProducerDialog extends JDialog {
    private final ProducerModel emptyProdModel = new ProducerModel();
    private JButton okButton;
    private JButton createProd;
    private JButton editProd;
    private JButton deleteProd;
    private JComboBox<ProducerModel> prodBox;
    private ProducerDialogListener listener;
    private ProducerModel privateProdModel;
    private String newProdName;

    public ProducerDialog(JFrame parent) {
        super(parent, Labels.getProperty("prodDialogSuper"), false);
        setSize(280, 150);
        setLocationRelativeTo(parent);

        Dimension comboBoxDim = new Dimension(150, 25);

        privateProdModel = emptyProdModel;

        DefaultComboBoxModel<ProducerModel> prodModel = new DefaultComboBoxModel<>();
        prodBox = new JComboBox<>(prodModel);
        prodBox.addItem(emptyProdModel);
        prodBox.setPreferredSize(comboBoxDim);
        prodBox.setEditable(true);

        CrEdDelButtons ced = new CrEdDelButtons(Labels.getProperty("addProd"), Labels.getProperty("editProd"), Labels.getProperty("delProd"));
        createProd = ced.getCreateButton();
        editProd = ced.getEditButton();
        deleteProd = ced.getDeleteButton();

        okButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        prodBox.addActionListener(e -> {
            Object item = prodBox.getSelectedItem();
            if (item instanceof ProducerModel && !item.equals(emptyProdModel)) {
                privateProdModel = (ProducerModel) item;
            } else if (item instanceof String && !item.equals("")) {
                newProdName = (String) item;
            }
        });

        createProd.addActionListener(e -> {
            if (!newProdName.equals("")) {
                ProducerModel model = new ProducerModel(newProdName);
                if (listener != null) {
                    prodBox.removeAllItems();
                    prodBox.addItem(emptyProdModel);
                    listener.createProdEventOccurred(model);
                }
            }
            privateProdModel = emptyProdModel;
            newProdName = "";
        });

        editProd.addActionListener(e -> {
            if (newProdName != null && !newProdName.equals("") && !privateProdModel.equals(emptyProdModel)) {
                if (listener != null) {
                    prodBox.removeAllItems();
                    prodBox.addItem(emptyProdModel);
                    privateProdModel.setBrandName(newProdName);
                    listener.editProdEventOccurred(privateProdModel);
                }
            }
            privateProdModel = emptyProdModel;
            newProdName = "";
        });

        deleteProd.addActionListener(e -> {
            if (!privateProdModel.equals(emptyProdModel) && listener != null) {
                prodBox.removeAllItems();
                prodBox.addItem(emptyProdModel);
                listener.deleteProdEventOccurred(privateProdModel);
            }
            privateProdModel = emptyProdModel;
            newProdName = "";
        });

        okButton.addActionListener(e -> setVisible(false));
    }

    public void setProdData(java.util.List<ProducerModel> prodDb) {
        for (ProducerModel prodModel : prodDb) {
            prodBox.addItem(prodModel);
        }
    }

    public void setListener(ProducerDialogListener listener) {
        this.listener = listener;
    }


    private void layoutControls() {
        JPanel prodPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border prodBorder = BorderFactory.createTitledBorder(Labels.getProperty("producer"));

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        prodPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, prodBorder));

        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets noPadding = new Insets(1, 0, 1, 0);

        prodPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        ////// First row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        prodPanel.add(prodBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        prodPanel.add(createProd, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        prodPanel.add(editProd, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        prodPanel.add(deleteProd, gc);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);

        setLayout(new BorderLayout());
        add(prodPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
