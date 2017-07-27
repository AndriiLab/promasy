package com.github.andriilab.promasy.gui;

import com.github.andriilab.promasy.controller.Logger;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

class HTMLViewer extends JDialog {
    HTMLViewer(MainFrame parent, String label, String url, boolean isModal) {
        super(parent, label, isModal);
        setSize(600, 500);
        setLocationRelativeTo(parent);

        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);
        try {
            jep.setPage(getClass().getResource(url));
        } catch (IOException e) {
            jep.setContentType("text/html");
            jep.setText("<html>Could not load document</html>");
            Logger.warnEvent(e);
        }

        jep.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                String desc = e.getDescription();
                if (desc == null) return;
                if (desc.startsWith("#")) {
                    desc = desc.substring(1);
                    jep.scrollToReference(desc);
                } else if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        Logger.warnEvent(ex);
                    }
                }
            }
        });

        add(new JScrollPane(jep));
    }
}
