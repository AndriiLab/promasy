package gui;

import controller.Logger;
import gui.commons.Labels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class InfoDialog extends JDialog {

    private final MainFrame parent;

    InfoDialog(MainFrame parent) {
        super(parent, Labels.getProperty("aboutSoftware"), true);
        this.parent = parent;
        setResizable(false);
        setSize(610, 455);
        setLocationRelativeTo(parent);


        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab(Labels.getProperty("info"), createLabel());
        tabPane.addTab(Labels.getProperty("authors"), new JScrollPane(createTextArea("/AUTHORS.txt")));
        tabPane.addTab(Labels.getProperty("license"), new JScrollPane(createTextArea("/LICENSE.txt")));
        tabPane.addTab(Labels.getProperty("libraries"), new JScrollPane(createTextArea("/PACKAGES.txt")));

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
            Logger.errorEvent(parent, e);
            return new JLabel();
        }
    }

    private JTextArea createTextArea(String textFile) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        try {
            InputStream in = getClass().getResourceAsStream(textFile);
            BufferedReader input = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            textArea.read(input, textFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textArea;
    }
}
