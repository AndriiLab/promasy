package com.github.andriilab.promasy.presentation;

import com.github.andriilab.promasy.presentation.components.HtmlViewerPane;

import javax.swing.*;

class HtmlViewerDialog extends JDialog {
    HtmlViewerDialog(MainFrame parent, String label, String url, boolean isModal) {
        super(parent, label, isModal);
        setSize(600, 500);
        setLocationRelativeTo(parent);

        add(new JScrollPane(new HtmlViewerPane(url)));
    }
}
