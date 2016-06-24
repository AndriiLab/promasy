package gui.login;

import gui.Labels;
import gui.Utils;

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
	private long salt;

	public LoginDialog(JFrame parent) {
		super(parent, Labels.getProperty("loginDialogSuper"), false);
		this.parent = parent;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(220, 150);
		setResizable(false);
		setLocationRelativeTo(parent);

		userField = new JTextField(10);
		passwordField = new JPasswordField(10);
		JButton okButton = new JButton(Labels.getProperty("loginOkBtn"));
		JButton cancelButton = new JButton(Labels.getProperty("cancel"));

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
		loginPanel.add(new JLabel(Labels.withColon("userName")), gc);

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
		loginPanel.add(new JLabel(Labels.withColon("password")), gc);

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

		okButton.addActionListener(e -> {
			String username = userField.getText();
			if (username.length() > 0 && loginListener != null) {
				loginListener.usernameEntered(username);
				String passw = Utils.makePass(passwordField.getPassword(), salt);
				if (salt != 0 && passw.length() > 0 && loginListener != null) {
					LoginAttemptEvent ev = new LoginAttemptEvent(this, username, passw);
					loginListener.loginAttemptOccurred(ev);
				} else if (salt != 0 || passw.length() > 0) {
					showLoginError();
				}
			} else
				showLoginError();
		});

		cancelButton.addActionListener(ev -> {
			if (loginListener != null) {
				loginListener.loginCancelled();
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

	public void setSalt(long salt) {
		this.salt = salt;
	}

	public void setLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;
	}

	private void showLoginError() {
		JOptionPane.showMessageDialog(parent, Labels.getProperty("noCredentialsMessage"),
				Labels.getProperty("noCredentialsTitle"), JOptionPane.ERROR_MESSAGE);
	}
}
