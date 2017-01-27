package gui;

import java.awt.*;

/**
 * Splash screen drawer class
 */
public class DrawSplashScreen extends Thread {

    private volatile SplashScreen splash;

    public void run() {
        splash = SplashScreen.getSplashScreen();

        Graphics2D g = splash.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(100, 320, 200, 100);
        g.setPaintMode();
        g.setColor(Color.WHITE);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        g.setFont(newFont);
        g.drawString(Labels.withColon("softwareVersion") + Labels.getVersion(), 100, 300);
        g.drawString(Labels.withColon("build") + Labels.getBuildDate(), 100, 340);

        splash.update();
    }

    public void close() {
        splash.close();
    }
}
