package main.java; /**
 * This is main Promasy class. It implements general method
 * for launching applications based on Swing interface. 
 */

import main.java.controller.Controller;
import main.java.gui.MainFrame;

import javax.swing.*;


public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(App::runApp);
	}

	private static void runApp() {

		MainFrame mainFrame = new MainFrame();
		
		new Controller(mainFrame);
		
	}
}
