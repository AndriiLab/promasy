package com.github.andriilab.promasy.gui.conset;

import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.persistence.storage.ConnectionSettings;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ConSetDialog extends JDialog {

    private final JButton okButton;
    private final JButton cancelButton;
    private final JSpinner portSpinner;
    private final JTextField serverField;
    private final JTextField schemaField;
    private final JTextField dbField;
    private final JTextField userField;
    private final JPasswordField passField;
    private ConSetListener listener;

    public ConSetDialog(MainFrame parent) {
        super(parent, Labels.getProperty("connectionWithDBSettings"), true);

        listener = new EmptyConSetListener();
        okButton = new JButton(Labels.getProperty("okBtn"));
        cancelButton = new JButton(Labels.getProperty("cancel"));
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(5432, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerModel);
        serverField = new JTextField(10);
        schemaField = new JTextField(10);
        dbField = new JTextField(10);
        userField = new JTextField(10);
        passField = new JPasswordField(10);

        layoutControls();

        okButton.addActionListener(e -> {
            int portNumber = (Integer) portSpinner.getValue();
            String server = serverField.getText();
            String database = dbField.getText();
            String schema = schemaField.getText();
            String user = userField.getText();
            char[] password = passField.getPassword();
            setVisible(false);

            ConnectionSettings model = new ConnectionSettings(server, database, schema, portNumber, user,
                    String.valueOf(password));

            listener.preferencesSetEventOccurred(model);
        });

        cancelButton.addActionListener(e -> {
            setVisible(false);
            if (!parent.isVisible()) {
                listener.forceCloseEventOccurred();
            }
        });

        setSize(500, 180);
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void layoutControls() {

        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border titleBorder = BorderFactory.createEtchedBorder();

        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
        controlsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        Insets rightPadding = new Insets(0, 0, 0, 15);
        Insets noPadding = new Insets(0, 0, 0, 0);

        ////// Controls panel//////
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel(Labels.withColon("serverAddress")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(serverField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel(Labels.withColon("port")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(portSpinner, gc);

        ////// Next Row//////
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel(Labels.withColon("dbName")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(dbField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel(Labels.withColon("schemaName")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(schemaField, gc);

        ////// Next row//////
        gc.gridx = 0;
        gc.gridy++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel(Labels.withColon("userName")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(userField, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel(Labels.withColon("password")), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(passField, gc);

        ////// Buttons panel//////
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton, gc);
        buttonsPanel.add(cancelButton, gc);

        okButton.setPreferredSize(cancelButton.getPreferredSize());

        // Add subpanels to dialog
        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void setConSetListener(ConSetListener prefsListener) {
        this.listener = prefsListener;
    }

    public void setDefaults(ConnectionSettings model) {
        serverField.setText(model.getServer());
        schemaField.setText(model.getSchema());
        dbField.setText(model.getDatabase());
        portSpinner.setValue(model.getPortNumber());
        userField.setText(model.getUser());
        passField.setText(model.getPassword());
    }
}
