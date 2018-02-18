package com.github.andriilab.promasy.presentation.components.dialogs;

import com.github.andriilab.promasy.data.controller.Logger;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.DrawSplashScreen;
import com.github.andriilab.promasy.presentation.components.panes.HtmlViewerPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InfoDialog extends JDialog {

    private final MainFrame parent;

    public InfoDialog(MainFrame parent) {
        super(parent, Labels.getProperty("aboutSoftware"), true);
        this.parent = parent;
        setResizable(false);
        setSize(610, 455);
        setLocationRelativeTo(parent);


        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab(Labels.getProperty("info"), createLabel());
        tabPane.addTab(Labels.getProperty("authors"), new JScrollPane(new HtmlViewerPane("/AUTHORS.html")));
        tabPane.addTab(Labels.getProperty("license"), new JScrollPane(new HtmlViewerPane("/LICENSE.html")));
        tabPane.addTab(Labels.getProperty("libraries"), new JScrollPane(new HtmlViewerPane("/PACKAGES.html")));

        setLayout(new BorderLayout());
        add(tabPane, BorderLayout.CENTER);
    }

    private JLabel createLabel() {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource("/splash.png"));
            Graphics2D g = (Graphics2D) img.getGraphics();
            DrawSplashScreen.drawFullInfo(g);
            ImageIcon icon = new ImageIcon(img);

            return new JLabel(icon);
        } catch (IOException e) {
            Logger.errorEvent(this.getClass(), parent, e);
            return new JLabel();
        }
    }
}
