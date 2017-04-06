package gui;

import controller.Logger;
import gui.commons.Icons;
import gui.commons.Labels;
import model.models.EmptyModel;

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

    private JTextPane logPane;

    public LoggerDialog(MainFrame parent) {
        super(parent, Labels.getProperty("log"), true);
        setSize(500, 300);
        setLocationRelativeTo(parent);

        logPane = new JTextPane();
        logPane.setEditable(false);
        logPane.setText(EmptyModel.STRING);

        JButton saveButton = new JButton(Labels.getProperty("saveLog"));
        saveButton.addActionListener(e -> {
            try {
                String filePath = saveLog(logPane.getText());
                JOptionPane.showMessageDialog(parent, Labels.withSpaceAfter("logSavedAs") + filePath, Labels.getProperty("fileSaved"), JOptionPane.INFORMATION_MESSAGE, Icons.INFO);
                this.setVisible(false);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(parent, Labels.withSpaceAfter("fileSaveError"), Labels.getProperty("error"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
                Logger.errorEvent(parent, e1);
            }
        });

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

    private static String saveLog(String log) throws IOException {
        String fileName = String.format("log_%s.txt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(log);
        bw.close();
        fw.close();
        return fileName;
    }

    public void addToLog (String status, Color color) {
        StyledDocument doc = logPane.getStyledDocument();
        Style style = logPane.addStyle("CurrentStyle", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ":\t" + status + "\n", style);
        } catch (BadLocationException e) {
            Logger.errorEvent(null, e);
        }
    }

}
