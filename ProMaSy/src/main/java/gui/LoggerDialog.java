package gui;

import model.dao.ServerQueries;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

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
        logPane.setText("");

        JButton saveButton = new JButton(Labels.getProperty("saveLog"));

        JButton closeButton = new JButton(Labels.getProperty("closeBtn"));
        closeButton.addActionListener(e -> setVisible(false));

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
            doc.insertString(doc.getLength(), ServerQueries.getServerTimestamp() + ":   " + status + "\n", style);
        } catch (BadLocationException e) {
            //TODO handle
            e.printStackTrace();
        }
    }
}
