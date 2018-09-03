package com.github.andriilab.promasy.presentation.components.dialogs;

import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.commons.SystemCommands;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.nio.file.Paths;

public class FileSavedDialog extends JDialog {
    private JLabel messageLabel;
    private JTextField pathField;

    public FileSavedDialog(Frame parent, boolean modal) {
        super(parent, modal);

        setTitle(Labels.getProperty("fileSaved"));
        setSize(515, 165);
        setResizable(false);
        setLocationRelativeTo(parent);

        Dimension buttonDim = new Dimension(25, 25);

        messageLabel = new JLabel("", Icons.INFO, JLabel.CENTER);

        JLabel pathLabel = new JLabel(Labels.withColon("pathToFile"));

        pathField = new JTextField("");
        pathField.setEditable(false);
        pathField.setPreferredSize(new Dimension(300, 25));

        JButton copyPathButton = new JButton(Icons.COPY);
        copyPathButton.setPreferredSize(buttonDim);
        copyPathButton.setToolTipText(Labels.getProperty("copyPathToClipboard"));
        copyPathButton.addActionListener(e -> SystemCommands.copyToClipboard(pathField.getText()));

        JButton openInExplorerButton = new JButton(Icons.OPEN_FOLDER);
        openInExplorerButton.setPreferredSize(buttonDim);
        openInExplorerButton.addActionListener(e -> SystemCommands.showInExplorer(pathField.getText()));

        JPanel pathPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel infoPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton okButton = new JButton(Labels.getProperty("okBtn"));
        okButton.setPreferredSize(new Dimension(75, 25));
        okButton.addActionListener(e -> this.setVisible(false));
        pathPanel.add(pathLabel);
        pathPanel.add(pathField);
        pathPanel.add(copyPathButton);
        pathPanel.add(openInExplorerButton);

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border figureBorder = BorderFactory.createEtchedBorder();

        infoPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, figureBorder));
        infoPanel.add(messageLabel, BorderLayout.NORTH);
        infoPanel.add(pathPanel, BorderLayout.CENTER);

        buttonPanel.add(okButton);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setVisible(String fileName) {
        pathField.setText(Paths.get(System.getProperty("user.dir"), fileName).toString());
        messageLabel.setText(Labels.withSpaceAfter("exportedTo") + fileName);
        setVisible(true);
    }
}
