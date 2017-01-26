package gui;

import model.models.EmptyModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;

/**
 * Dialog displays program log
 */
public class LoggerDialog extends JDialog {

    private JTextPane logPane;

    public LoggerDialog (JFrame parent){
        super(parent, Labels.getProperty("log"), true);
        setSize(500, 300);
        setLocationRelativeTo(parent);

        logPane = new JTextPane();
        logPane.setEditable(false);
        logPane.setText(EmptyModel.STRING);

        JButton saveButton = new JButton(Labels.getProperty("saveLog"));
        saveButton.addActionListener(e -> {
            try {
                String filePath = Utils.saveLog(logPane.getText());
                JOptionPane.showMessageDialog(parent, Labels.withSpaceAfter("logSavedAs") + filePath, Labels.getProperty("fileSaved"), JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (IOException e1) {
                //TODO handle exception
                e1.printStackTrace();
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

    public void addToLog (String status, Color color) {
        StyledDocument doc = logPane.getStyledDocument();
        Style style = logPane.addStyle("CurrentStyle", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), Utils.getSystemTime() + ":\t" + status + "\n", style);
        } catch (BadLocationException e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }
}
