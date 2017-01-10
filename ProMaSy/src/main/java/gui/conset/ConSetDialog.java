package gui.conset;

import gui.Labels;
import model.models.ConnectionSettingsModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ConSetDialog extends JDialog {

    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private JTextField serverField;
    private JTextField schemaField;
    private JTextField dbField;
    private JTextField userField;
    private JPasswordField passField;
    private ConSetListener prefsListener;

    public ConSetDialog(JFrame parent) {
        super(parent, Labels.getProperty("connectionWithDBSettings"), true);

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

            ConnectionSettingsModel model = new ConnectionSettingsModel(server, database, schema, portNumber, user,
                    String.valueOf(password));

            if (prefsListener != null) {
                prefsListener.preferencesSetEventOccurred(model);
            }
        });

        cancelButton.addActionListener(e -> setVisible(false));

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
        this.prefsListener = prefsListener;
    }

    public void setDefaults(ConnectionSettingsModel model) {
        serverField.setText(model.getServer());
        schemaField.setText(model.getSchema());
        dbField.setText(model.getDatabase());
        portSpinner.setValue(model.getPortNumber());
        userField.setText(model.getUser());
        passField.setText(model.getPassword());
    }
}
