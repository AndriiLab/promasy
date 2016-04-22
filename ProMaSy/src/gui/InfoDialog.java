package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;

public class InfoDialog extends JDialog {
	private JTextPane infoPane;
	private JButton okButton;

	public InfoDialog(JFrame parent) {
		super(parent, "Про програму", false);
		setSize(300, 200);
		setLocationRelativeTo(parent);

		infoPane = new JTextPane();
		infoPane.setEditable(false);
		infoPane.setText(
				"PROcurement MAnagement SYstem\n" + "Система Керування Закупівлями\n" + "\n" + "\tВерсія 0.1\n");

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		layoutControls();
	}

	private void layoutControls() {
		JPanel infoPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		int space = 5;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border simpleBorder = BorderFactory.createRaisedBevelBorder();

		infoPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, simpleBorder));
		infoPanel.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.CENTER;
		infoPanel.add(infoPane);

		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(okButton, gc);

		setLayout(new BorderLayout());
		add(infoPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
}
