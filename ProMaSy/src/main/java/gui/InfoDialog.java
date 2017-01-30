package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InfoDialog extends JDialog {

    public InfoDialog(JFrame parent) {
        super(parent, Labels.getProperty("aboutSoftware"), true);
        setResizable(false);
        setSize(610, 430);
        setLocationRelativeTo(parent);

        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource("/splash.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graphics2D g = (Graphics2D) img.getGraphics();
        DrawSplashScreen.drawVersionAndBuild(g);

        ImageIcon icon = new ImageIcon(img);

        JLabel label = new JLabel(icon);

		setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }
}
