package gui.prodsupl;

import gui.Labels;
import gui.Utils;
import model.ProducerModel;
import model.SupplierModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by laban on 26.04.2016.
 */
public class ProdSuplDialog extends JDialog implements ActionListener {
    private JButton okButton;
    private JButton createProd;
    private JButton editProd;
    private JButton deleteProd;
    private JButton createSupl;
    private JButton editSupl;
    private JButton deleteSupl;
    private JComboBox<ProducerModel> prodBox;
    private JComboBox<SupplierModel> suplBox;
    private final ProducerModel emptyProdModel = new ProducerModel();
    private final SupplierModel emptySuplModel = new SupplierModel();
    private ProdSuplDialogListener listener;
    private ProducerModel privateProdModel;
    private String newProdName;
    private SupplierModel privateSuplModel;
    private String newSuplName;
    private String newSuplTel;
    private String newSuplComment;
    private JTextField telField;
    private JTextPane commentsPane;

    public ProdSuplDialog(JFrame parent) {
        super(parent, Labels.getProperty("prodSuplDialogSuper"), false);
        setSize(280, 310);
        setLocationRelativeTo(parent);

        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboBoxDim = new Dimension(150, 25);

        privateProdModel = emptyProdModel;
        privateSuplModel = emptySuplModel;

        DefaultComboBoxModel<ProducerModel> prodModel = new DefaultComboBoxModel<>();
        prodBox = new JComboBox<>(prodModel);
        prodBox.addItem(emptyProdModel);
        prodBox.setPreferredSize(comboBoxDim);
        prodBox.setEditable(true);
        prodBox.addActionListener(this);

        DefaultComboBoxModel<SupplierModel> suplModel = new DefaultComboBoxModel<>();
        suplBox = new JComboBox<>(suplModel);
        suplBox.addItem(emptySuplModel);
        suplBox.setPreferredSize(comboBoxDim);
        suplBox.setEditable(true);
        suplBox.addActionListener(this);

        createProd = new JButton();
        createProd.setToolTipText(Labels.getProperty("addProd"));
        createProd.setIcon(Utils.createIcon("/images/Add16.gif"));
        createProd.setPreferredSize(buttonDim);
        createProd.setEnabled(true);
        editProd = new JButton();
        editProd.setToolTipText(Labels.getProperty("editProd"));
        editProd.setIcon(Utils.createIcon("/images/Edit16.gif"));
        editProd.setPreferredSize(buttonDim);
        editProd.setEnabled(true);
        deleteProd = new JButton();
        deleteProd.setToolTipText(Labels.getProperty("delProd"));
        deleteProd.setIcon(Utils.createIcon("/images/Delete16.gif"));
        deleteProd.setPreferredSize(buttonDim);
        deleteProd.setEnabled(true);

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

        createProd.addActionListener(e -> {
            if (newProdName != null && !newProdName.equals("") && privateProdModel.equals(emptyProdModel)) {
                ProducerModel model = new ProducerModel(newProdName);
                if (listener != null) {
                    prodBox.removeAllItems();
                    prodBox.addItem(emptyProdModel);
                    listener.createProdEventOccurred(model);
                }
            }
            privateProdModel = emptyProdModel;
            newProdName = null;
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
            newProdName = null;
        });

        deleteProd.addActionListener(e -> {
            if (!privateProdModel.equals(emptyProdModel) && listener != null) {
                prodBox.removeAllItems();
                prodBox.addItem(emptyProdModel);
                listener.deleteProdEventOccurred(privateProdModel);
            }
            privateProdModel = emptyProdModel;
            newProdName = null;
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

    public void setProdData(java.util.List<ProducerModel> prodDb) {
        for (ProducerModel prodModel : prodDb) {
            prodBox.addItem(prodModel);
        }
    }

    public void setSuplData(java.util.List<SupplierModel> suplDb) {
        for (SupplierModel suplModel : suplDb) {
            suplBox.addItem(suplModel);
        }
    }

    public void setListener(ProdSuplDialogListener listener) {
        this.listener = listener;
    }

    private boolean isSuplDataValid() {
        newSuplTel = telField.getText();
        return newSuplName != null
                && !newSuplName.equals("")
                && newSuplTel != null
                && !newSuplTel.equals("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox box = (JComboBox) e.getSource();
        Object item = (box).getSelectedItem();
        if (box == prodBox) {
            if (item instanceof ProducerModel && !item.equals(emptyProdModel)) {
                privateProdModel = (ProducerModel) item;
            } else if (item instanceof String && !item.equals(null)) {
                newProdName = (String) item;
            }
        } else if (box == suplBox) {
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
        }
    }

    private void layoutControls() {
        JPanel prodPanel = new JPanel();
        JPanel supplPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border prodBorder = BorderFactory.createTitledBorder(Labels.getProperty("prodBorder"));
        Border suplBorder = BorderFactory.createTitledBorder(Labels.getProperty("suplBorder"));

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        prodPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, prodBorder));
        supplPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, suplBorder));

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

        supplPanel.setLayout(new GridBagLayout());
        gc = new GridBagConstraints();

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
        add(prodPanel, BorderLayout.NORTH);
        add(supplPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
