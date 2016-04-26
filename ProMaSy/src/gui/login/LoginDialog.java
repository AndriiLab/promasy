package gui.login;

import gui.Labels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LoginDialog extends JDialog {

	private JTextField userField;
	private JPasswordField passwordField;
    private LoginListener loginListener;

	public LoginDialog(JFrame parent) {
		super(parent, Labels.getProperty("loginDialogSuper"), false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(220, 150);
		setResizable(false);
		setLocationRelativeTo(parent);

		userField = new JTextField(10);
		passwordField = new JPasswordField(10);
        JButton okButton = new JButton(Labels.getProperty("loginOkBtn"));
        JButton cancelButton = new JButton(Labels.getProperty("cancelBtn"));

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
		loginPanel.add(new JLabel(Labels.getProperty("userName")), gc);

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
		loginPanel.add(new JLabel(Labels.getProperty("password")), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		loginPanel.add(passwordField, gc);

		////// Buttons panel//////
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okButton, gc);
		buttonsPanel.add(cancelButton, gc);

		Dimension btnSize = cancelButton.getPreferredSize();
		okButton.setPreferredSize(btnSize);

		// Add subpanels to dialog
		setLayout(new BorderLayout());
		add(loginPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = userField.getText();
				String passw = new String(passwordField.getPassword());
				
				if (username.length() > 0 && passw.length() > 0) {
					LoginAttemptEvent ev = new LoginAttemptEvent(this, username, passw);

					if (loginListener != null) {
						loginListener.loginAttemptOccurred(ev);
					}
				} else JOptionPane.showMessageDialog(parent,
                        Labels.getProperty("noCredentialsMessage"),
                        Labels.getProperty("noCredentialsTitle"),
                        JOptionPane.ERROR_MESSAGE);
			}
		});

		cancelButton.addActionListener(ev -> {
            if (loginListener != null) {
                loginListener.loginCancelled(ev);
            }
        });
		
		this.getRootPane().setDefaultButton(okButton);
	}

	public void setLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;
	}
}
