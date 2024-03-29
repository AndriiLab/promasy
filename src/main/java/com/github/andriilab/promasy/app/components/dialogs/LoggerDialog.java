package com.github.andriilab.promasy.app.components.dialogs;

import com.github.andriilab.promasy.app.controller.Logger;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Icons;
import com.github.andriilab.promasy.app.commons.Labels;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Dialog displays program log
 */
public class LoggerDialog extends JDialog {

    private final MainFrame parent;
    private final JTextPane logPane;

    public LoggerDialog(MainFrame parent) {
        super(parent, Labels.getProperty("log"), true);
        this.parent = parent;
        setSize(500, 300);
        setLocationRelativeTo(parent);

        logPane = new JTextPane();
        logPane.setEditable(false);
        logPane.setText("");

        JButton saveButton = new JButton(Labels.getProperty("saveLog"));
        saveButton.addActionListener(e -> saveLog());

        JButton closeButton = new JButton(Labels.getProperty("closeBtn"));
        closeButton.addActionListener(e -> this.setVisible(false));

        closeButton.setPreferredSize(saveButton.getPreferredSize());

        JPanel logPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        int space = 1;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border simpleBorder = BorderFactory.createRaisedBevelBorder();

        logPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, simpleBorder));
        logPanel.setLayout(new GridBagLayout());

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(logPane), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    public void saveLog() {
        String fileName = "log_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        try (
                FileWriter fw = new FileWriter(fileName);
                BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(logPane.getText());
            this.setVisible(false);
            parent.showFileSavedDialog(fileName);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(parent, Labels.withSpaceAfter("fileSaveError"), Labels.getProperty("error"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            Logger.errorEvent(this.getClass(), parent, e1);
        }
    }

    public void addToLog(String status, Color color) {
        StyledDocument doc = logPane.getStyledDocument();
        Style style = logPane.addStyle("CurrentStyle", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ":\t" + status + "\n", style);
        } catch (BadLocationException e) {
            Logger.errorEvent(this.getClass(), null, e);
        }
    }

}
