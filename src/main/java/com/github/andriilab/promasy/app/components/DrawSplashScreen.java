package com.github.andriilab.promasy.app.components;

import com.github.andriilab.promasy.app.commons.Labels;

import java.awt.*;

/**
 * Splash screen drawer class
 */
public class DrawSplashScreen extends Thread {

    private volatile SplashScreen splash;

    private static void drawVersionAndBuild(Graphics2D g) {
        g.setComposite(AlphaComposite.Clear);
        g.setPaintMode();
        g.setColor(Color.WHITE);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        g.setFont(newFont);
        g.drawString(Labels.withColon("softwareVersion") + Labels.getVersion(), 100, 300);
        g.drawString(Labels.withColon("build") + Labels.getBuildDate(), 100, 340);
    }

    public static void drawFullInfo(Graphics2D g) {
        g.setComposite(AlphaComposite.Clear);
        g.setPaintMode();
        g.setColor(Color.WHITE);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        g.setFont(newFont);
        g.drawString(Labels.withColon("softwareVersion") + Labels.getVersion() + "  " + Labels.withColon("build") + Labels.getBuildDate(), 50, 180);
        g.drawString(Labels.withColon("javaVersion") + System.getProperty("java.version"), 50, 200);
        g.drawString(Labels.getProperty("freeSoftwareDistributedUnder1"), 50, 240);
        g.drawString(Labels.getProperty("freeSoftwareDistributedUnder2"), 50, 260);
        g.drawString(Labels.getProperty("madeWithOpenSourceSoftware"), 50, 320);
        g.drawString(Labels.getProperty("copyright1") + Labels.getBuildYear() + Labels.withSpaceBefore("copyright2"), 200, 380);
    }

    public void run() {
        splash = SplashScreen.getSplashScreen();

        Graphics2D g = splash.createGraphics();
        drawVersionAndBuild(g);

        splash.update();
    }

    public void close() {
        splash.close();
    }

    public boolean isVisible() {
        return splash.isVisible();
    }
}
