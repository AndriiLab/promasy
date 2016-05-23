package gui;

import java.net.URL;

import javax.swing.*;

public class Utils {
	public static ImageIcon createIcon(String path){
		URL url = System.class.getResource(path);
		if(url == null){
			System.err.println("Unable to load icon: "+path);
		}
		return new ImageIcon(url);
	}

    public static void setBoxFromModel(JComboBox box, String req){
        if(req != null){
            for(int i = 0; i<=box.getItemCount(); i++){
                if(box.getItemAt(i).toString().equals(req)){
                    box.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}
