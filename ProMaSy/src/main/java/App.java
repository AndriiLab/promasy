/*
 * This is main Promasy class. It implements general method
 * for launching applications based on Swing interface. 
 */

import controller.Controller;
import gui.MainFrame;

import javax.swing.*;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> runApp(args));
	}

	private static void runApp(String[] args) {
		MainFrame mainFrame = new MainFrame();
		new Controller(args, mainFrame);
	}
}
