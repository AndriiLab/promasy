package gui;

import model.AbstractModel;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;

public class Utils {
	public static ImageIcon createIcon(String path){
		URL url = System.class.getResource(path);
		if(url == null){
            return createIcon("images/Applet16.gif");
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

    public static long makeSalt(){
        return new SecureRandom().nextLong();
    }

    public static String makePass(char[] pass, long salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(new String(pass).getBytes(StandardCharsets.UTF_8));
            md.update(ByteBuffer.allocate(Long.BYTES).putLong(salt).array());
            return DatatypeConverter.printHexBinary(md.digest());
        } catch (NoSuchAlgorithmException e) {
            //Bad practice
            e.printStackTrace();
            return "";
        }
    }
}
