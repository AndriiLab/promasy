/**
 * This is main Promasy class. It implements general method
 * for launching applications based on Swing interface. 
 */

import javax.swing.SwingUtilities;

import gui.MainFrame;


public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
			
		});
	}
}
