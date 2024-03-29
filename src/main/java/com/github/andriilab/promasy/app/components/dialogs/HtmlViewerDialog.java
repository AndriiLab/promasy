package com.github.andriilab.promasy.app.components.dialogs;

import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.components.panes.HtmlViewerPane;

import javax.swing.*;

public class HtmlViewerDialog extends JDialog {
    public HtmlViewerDialog(MainFrame parent, String label, String url, boolean isModal) {
        super(parent, label, isModal);
        setSize(600, 500);
        setLocationRelativeTo(parent);

        add(new JScrollPane(new HtmlViewerPane(url)));
    }
}
