package com.github.andriilab.promasy.app.components.dialogs;

import com.github.andriilab.promasy.app.controller.Logger;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.app.components.DrawSplashScreen;
import com.github.andriilab.promasy.app.components.panes.HtmlViewerPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/splash.png")));
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
