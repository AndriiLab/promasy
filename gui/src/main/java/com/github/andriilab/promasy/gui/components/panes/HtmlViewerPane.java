package com.github.andriilab.promasy.gui.components.panes;

import com.github.andriilab.promasy.gui.controller.Logger;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlViewerPane extends JEditorPane {
    public HtmlViewerPane(String url) {
        this.setContentType("text/html;charset=UTF-8");
        this.setEditable(false);
        try {
            this.setPage(getClass().getResource(url));
        } catch (IOException e) {
            this.setContentType("text/html");
            this.setText("<html>Could not load document</html>");
            Logger.warnEvent(this.getClass(), e);
        }

        this.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                String desc = e.getDescription();
                if (desc == null) return;
                if (desc.startsWith("#")) {
                    desc = desc.substring(1);
                    this.scrollToReference(desc);
                } else if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        Logger.warnEvent(this.getClass(), ex);
                    }
                }
            }
        });
    }
}
