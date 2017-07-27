package com.github.andriilab.promasy;
/*
 * This is main Promasy class. It implements general method
 * for launching applications based on Swing interface. 
 */

import com.github.andriilab.promasy.controller.Controller;
import com.github.andriilab.promasy.gui.MainFrame;

import javax.swing.*;

class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> runApp(args));
    }

    private static void runApp(String[] args) {
        MainFrame mainFrame = new MainFrame();
        new Controller(args, mainFrame);
    }
}
