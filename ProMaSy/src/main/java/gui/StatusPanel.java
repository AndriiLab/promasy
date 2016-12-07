package main.java.gui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class StatusPanel extends JPanel {

    private JLabel currentUserLabel;
    private JLabel status;
    private MainFrame parent;

    public StatusPanel(MainFrame parent) {
        this.parent = parent;
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        setPreferredSize(new Dimension(parent.getWidth(), 20));
        setLayout(new BorderLayout());

        currentUserLabel = new JLabel("");
        currentUserLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentUserLabel.setIcon(Icons.USER);

        status = new JLabel("");
        status.setHorizontalAlignment(SwingConstants.RIGHT);

        add(currentUserLabel, BorderLayout.LINE_START);
        add(status, BorderLayout.LINE_END);

    }

    public void setCurrentUserLabel(String user) {
        currentUserLabel.setText(user);
        parent.logEvent(Labels.withColon("user") + " " + user, Utils.GREEN);
    }

    public void setStatus(String statusInfo, Color color) {
        status.setForeground(color);
        status.setText(statusInfo);
    }
}
