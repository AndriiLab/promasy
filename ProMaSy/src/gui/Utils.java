package gui;

import java.net.URL;

import javax.swing.ImageIcon;

public class Utils {
	public static ImageIcon createIcon(String path){
		URL url = System.class.getResource(path);
		if(url == null){
			System.err.println("Unable to load icon: "+path);
		}
		
		ImageIcon icon =  new ImageIcon(url);
		
		return icon;
	}
}
