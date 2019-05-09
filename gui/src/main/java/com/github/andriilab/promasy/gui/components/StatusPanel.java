package com.github.andriilab.promasy.gui.components;

import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Colors;
import com.github.andriilab.promasy.gui.commons.Icons;
import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.gui.controller.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class StatusPanel extends JPanel {

    private final JLabel currentDbLabel;
    private final JLabel status;
    private final MainFrame parent;

    public StatusPanel(MainFrame parent) {
        this.parent = parent;
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        setPreferredSize(new Dimension(parent.getWidth(), 20));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        currentDbLabel = new JLabel("");
        currentDbLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentDbLabel.setIcon(Icons.CONNECTION_SETTINGS);

        status = new JLabel("");
        status.setHorizontalAlignment(SwingConstants.RIGHT);

        add(currentDbLabel);
        add(Box.createHorizontalStrut(5));
        add(new JSeparator(SwingConstants.VERTICAL));
        add(Box.createHorizontalStrut(5));
        add(status);

    }

    public void setCurrentDb(String dbName) {
        String message = Labels.withColon("dbName") + " " + dbName;

        if (dbName.contains("test")) {
            currentDbLabel.setIcon(Icons.CONNECTION_SETTINGS_RED);
            currentDbLabel.setForeground(Colors.RED);
            dbName = Labels.getProperty("testDb");
            message = Labels.getProperty("connectedToTestDb");
        }

        currentDbLabel.setText(dbName);
        Logger.infoEvent(this.getClass(), parent, message);
    }


    public void setStatus(String statusInfo, Color color) {
        status.setForeground(color);
        status.setText(statusInfo);
    }
}
