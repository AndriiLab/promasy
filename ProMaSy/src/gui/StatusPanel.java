package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

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

        status = new JLabel("");
        status.setHorizontalAlignment(SwingConstants.RIGHT);

        add(currentUserLabel, BorderLayout.LINE_START);
        add(status, BorderLayout.LINE_END);

    }

    public void setCurrentUserLabel(String user) {
        String currentUser = Labels.withColon("currentUser") + " " + user;
        currentUserLabel.setText(currentUser);
        parent.getLoggerDialog().addToLog(currentUser, Utils.GREEN);
    }

    public void setStatus(String statusInfo, Color color) {
        status.setForeground(color);
        status.setText(statusInfo);
    }
}
