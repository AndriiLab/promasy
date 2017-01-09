package gui;

import model.dao.LoginData;
import model.dao.ServerQueries;
import model.enums.Role;
import model.models.AbstractModel;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Utils {

    public static void setBoxFromModel(JComboBox<? extends AbstractModel> box, AbstractModel model) {
        if (model.getModelId() != 0L) {
            for(int i = 0; i<=box.getItemCount(); i++){
                if (box.getItemAt(i).equals(model)) {
                    box.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    public static void setRoleBox(JComboBox<Role> box, Role role) {
        for (int i = 0; i <= box.getItemCount(); i++) {
            if (box.getItemAt(i).equals(role)) {
                box.setSelectedIndex(i);
                break;
            }
        }
    }

    // methods for setting created/modified employee and created/modified date
    public static <T extends AbstractModel> void setCreated(T model) {
        model.setCreatedEmployee(LoginData.getInstance().getCreatedEmployee());
        model.setCreatedDate(ServerQueries.getServerTimestamp());
    }

    public static <T extends AbstractModel> void setUpdated(T model) {
        model.setModifiedEmployee(LoginData.getInstance().getModifiedEmployee());
        model.setModifiedDate(ServerQueries.getServerTimestamp());
    }

    public static <T extends AbstractModel> void setDeleted(T model) {
        model.setActive(false);
        setUpdated(model);
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
