package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InfoDialog extends JDialog {

    public InfoDialog(JFrame parent) {
        super(parent, Labels.getProperty("aboutSoftware"), true);
        setSize(300, 200);
		setLocationRelativeTo(parent);

        JTextPane infoPane = new JTextPane();
		infoPane.setEditable(false);
		infoPane.setText(Labels.withColon("infoPaneText") + Labels.getVersion() + "\n" + Labels.withColon("build") + Labels.getBuildDate());

        JButton okButton = new JButton(Labels.getProperty("okBtn"));
		okButton.addActionListener(e -> setVisible(false));

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
