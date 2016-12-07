package main.java.gui.login;

import main.java.gui.Icons;
import main.java.gui.Labels;
import main.java.gui.MainFrame;
import main.java.gui.empedit.CreateEmployeeFromLoginListener;
import main.java.model.enums.Role;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginDialog extends JDialog {

    private JTextField userField;
    private JPasswordField passwordField;
    private LoginListener loginListener;
    private JFrame parent;

    public LoginDialog(MainFrame parent) {
        super(parent, Labels.getProperty("loginDialogSuper"), true);
        this.parent = parent;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(240, 150);
        setResizable(false);
        setLocationRelativeTo(parent);

        userField = new JTextField(13);
        PromptSupport.setPrompt(Labels.getProperty("user"), userField);
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

        ////// Login Panel //////
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
            if (username.length() > 0 && password.length > 0 && loginListener != null) {
                loginListener.loginAttemptOccurred(username, password);
            } else showLoginError();
        });

        cancelButton.addActionListener(ev -> {
            if (loginListener != null) {
                loginListener.loginCancelled();
            }
        });

        registerButton.addActionListener(e -> {
            parent.getCreateEmployeeDialog().setLoginListener(new CreateEmployeeFromLoginListener() {
                @Override
                public void newUserCreatedEvent() {
                    JOptionPane.showMessageDialog(parent, Labels.getProperty("youCanLoginAfterRestart"),
                            Labels.getProperty("accountCreated"), JOptionPane.INFORMATION_MESSAGE);
                    if (loginListener != null) {
                        loginListener.loginCancelled();
                    }
                }

                @Override
                public void cancelEvent() {
                    if (loginListener != null) {
                        loginListener.loginCancelled();
                    }
                }
            });
            if (loginListener != null) {
                userField.setText("");
                passwordField.setText("");
                if (loginListener.isAbleToRegister()) {
                    parent.getCreateEmployeeDialog().setRoleBox(false, Role.USER.getRoleId());
                    parent.getCreateEmployeeDialog().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(parent, Labels.getProperty("registrationClosed"),
                            Labels.getProperty("cannotCreateNewUser"), JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        this.getRootPane().setDefaultButton(okButton);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (loginListener != null) {
                    loginListener.loginCancelled();
                }
            }
        });

    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    private void showLoginError() {
        JOptionPane.showMessageDialog(parent, Labels.getProperty("noCredentialsMessage"),
                Labels.getProperty("noCredentialsTitle"), JOptionPane.ERROR_MESSAGE);
    }
}