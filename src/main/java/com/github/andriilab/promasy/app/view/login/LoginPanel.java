package com.github.andriilab.promasy.app.view.login;

import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Icons;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.app.view.employee.CreateEmployeeFromLoginListener;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final JTextField userField;
    private final JPasswordField passwordField;
    private LoginListener listener;
    private final MainFrame parent;

    public LoginPanel(MainFrame parent) {
        parent.setTitle(Labels.getProperty("loginDialogSuper"));
        parent.setSize(280, 150);
        parent.setResizable(false);
        this.parent = parent;
        listener = new EmptyLoginListener();

        userField = new JTextField(13);
        PromptSupport.setPrompt(Labels.getProperty("role.user"), userField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIGHLIGHT_PROMPT, userField);

        passwordField = new JPasswordField(13);
        PromptSupport.setPrompt(Labels.getProperty("password"), passwordField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIGHLIGHT_PROMPT, passwordField);

        JButton okButton = new JButton(Labels.getProperty("loginOkBtn"));
        JButton cancelButton = new JButton(Labels.getProperty("cancel"));
        JButton registerButton = new JButton(Icons.NEW_USER);
        registerButton.setToolTipText(Labels.getProperty("createNewUser"));

        JPanel loginPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border figureBorder = BorderFactory.createEtchedBorder();

        loginPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, figureBorder));
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        Insets rightPadding = new Insets(0, 0, 0, 10);
        Insets noPadding = new Insets(0, 0, 0, 0);

        ////// First row//////
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        loginPanel.add(new JLabel(Icons.USER_GREEN), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        loginPanel.add(userField, gc);

        ////// Next Row//////
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        loginPanel.add(new JLabel(Icons.PASSWORD), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        loginPanel.add(passwordField, gc);

        ////// Buttons panel//////
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(registerButton, gc);
        buttonsPanel.add(new JSeparator());
        buttonsPanel.add(okButton, gc);
        buttonsPanel.add(cancelButton, gc);

        Dimension btnSize = cancelButton.getPreferredSize();
        okButton.setPreferredSize(btnSize);
        registerButton.setPreferredSize(new Dimension(26, 26));

        // Add subpanels to dialog
        setLayout(new BorderLayout());
        add(loginPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> {
            String username = userField.getText();
            char[] password = passwordField.getPassword();
            if (!username.isEmpty() && password.length > 0) {
                listener.loginAttemptOccurred(username, password);
            } else showLoginError();
        });

        cancelButton.addActionListener(ev -> listener.loginCancelled());

        registerButton.addActionListener(e -> {
            parent.getCreateEmployeeDialog().setLoginListener(new CreateEmployeeFromLoginListener() {
                @Override
                public void newUserCreatedEvent() {
                    JOptionPane.showMessageDialog(parent, Labels.getProperty("youCanLoginAfterRestart"),
                            Labels.getProperty("accountCreated"), JOptionPane.INFORMATION_MESSAGE, Icons.INFO);
                    listener.loginCancelled();
                }

                @Override
                public void cancelEvent() {
                    listener.loginCancelled();
                }
            });
            userField.setText("");
            passwordField.setText("");
            if (listener.isAbleToRegister()) {
                parent.getCreateEmployeeDialog().setRoleBox(Role.USER);
                parent.getCreateEmployeeDialog().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(parent, Labels.getProperty("registrationClosed"),
                        Labels.getProperty("cannotCreateNewUser"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            }
        });

        SwingUtilities.getRootPane(parent).setDefaultButton(okButton);
    }

    public void setListener(LoginListener listener) {
        this.listener = listener;
    }

    private void showLoginError() {
        JOptionPane.showMessageDialog(parent, Labels.getProperty("noCredentialsMessage"),
                Labels.getProperty("noCredentialsTitle"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
    }
}