package gui;

import controller.Logger;
import gui.commons.Colors;
import gui.commons.Icons;
import gui.commons.Labels;
import model.models.EmptyModel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

class StatusPanel extends JPanel {

    private JLabel currentUserLabel;
    private JLabel currentDbLabel;
    private JLabel status;
    private MainFrame parent;

    StatusPanel(MainFrame parent) {
        this.parent = parent;
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        setPreferredSize(new Dimension(parent.getWidth(), 20));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        currentUserLabel = new JLabel(EmptyModel.STRING);
        currentUserLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentUserLabel.setIcon(Icons.USER);

        currentDbLabel = new JLabel(EmptyModel.STRING);
        currentDbLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentDbLabel.setIcon(Icons.CONNECTION_SETTINGS);

        status = new JLabel(EmptyModel.STRING);
        status.setHorizontalAlignment(SwingConstants.RIGHT);

        add(currentUserLabel);
        add(Box.createHorizontalStrut(5));
        add(new JSeparator(SwingConstants.VERTICAL));
        add(currentDbLabel);
        add(Box.createHorizontalStrut(5));
        add(new JSeparator(SwingConstants.VERTICAL));
        add(Box.createHorizontalStrut(5));
        add(status);

    }

    void setCurrentUserLabel(String user) {
        currentUserLabel.setText(user);
        Logger.infoEvent(parent, Labels.withColon("role.user") + " " + user);
    }

    void setCurrentDb(String dbName) {
        String message = Labels.withColon("dbName") + " " + dbName;

        if (dbName.contains("test")) {
            currentDbLabel.setIcon(Icons.CONNECTION_SETTINGS_RED);
            currentDbLabel.setForeground(Colors.RED);
            dbName = Labels.getProperty("testDb");
            message = Labels.getProperty("connectedToTestDb");
        }

        currentDbLabel.setText(dbName);
        Logger.infoEvent(parent, message);
    }


    void setStatus(String statusInfo, Color color) {
        status.setForeground(color);
        status.setText(statusInfo);
    }
}
