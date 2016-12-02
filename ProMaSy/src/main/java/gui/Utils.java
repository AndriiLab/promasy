package main.java.gui;

import main.java.model.AbstractModel;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;

public class Utils {
    public static final Color GREEN = new Color(0, 153, 51);
    public static final Color RED = new Color(204, 0, 0);
    public static final Color BLUE = new Color(25, 61, 160);

    public static Timestamp getCurrentTime(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static void setBoxFromID(JComboBox<? extends AbstractModel> box, long requestedId){
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

    public static void wrongFormatError(JFrame parent, String fieldName, String hints) {
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("wrongFormat")+ " \"" + fieldName + "\"\n" + Labels.getProperty("checkInput") + "\"\n" + hints,
                Labels.getProperty("fieldErr"),
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