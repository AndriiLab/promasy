package gui;

import model.AbstractModel;

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

    public static void setBoxFromModel( JComboBox<? extends AbstractModel> box, long requestedId){
        if(requestedId != 0){
            for(int i = 0; i<=box.getItemCount(); i++){
                if(box.getItemAt(i).getModelId() == requestedId){
                    box.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    public static void emptyFieldError(JFrame parent, String fieldName) {
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("enterDataIntoField") + " \"" + fieldName + "\"",
                Labels.getProperty("fieldCannotBeEmpty") + " \"" + fieldName + "\"",
                JOptionPane.ERROR_MESSAGE);
    }
}
