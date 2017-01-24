package gui;

import model.enums.BidType;
import model.enums.Role;
import model.models.AbstractModel;
import model.models.ConnectionSettingsModel;
import model.models.FinanceModel;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utils {

    public static final Dimension COMBOBOX_DIMENSION = new Dimension(225, 20);

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

    public static void emptyFieldError(JFrame parent, String fieldName) {
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("enterDataIntoField") + " \"" + fieldName + "\"",
                Labels.getProperty("fieldCannotBeEmpty") + " \"" + fieldName + "\"",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void wrongFormatError(JFrame parent, String fieldName, String hints) {
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("wrongFormat") + " \"" + fieldName + "\"\n" + Labels.getProperty("checkInput") + "\n" + hints,
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

    public static Timestamp getSystemTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static void saveConnectionSettings(ConnectionSettingsModel model) throws IOException {
        FileOutputStream fos = new FileOutputStream("promasy_settings.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.close();
        fos.close();
    }

    public static ConnectionSettingsModel loadConnectionSettings() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("promasy_settings.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ConnectionSettingsModel model = (ConnectionSettingsModel) ois.readObject();
        ois.close();
        fis.close();
        return model;
    }

    public static String saveLog(String log) throws IOException {
        String fileName = "log_" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(Utils.getSystemTime()) + ".txt";
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(log);
        bw.close();
        fw.close();
        return fileName;
    }

    private static String formatBigDecimal(String bigDecimal) {
        if (bigDecimal.contains(",")) {
            bigDecimal = bigDecimal.replace(",", ".");
        }
        if (bigDecimal.contains(" ")) {
            bigDecimal = bigDecimal.replace(" ", "");
        }
        return bigDecimal;
    }

    public static BigDecimal parseBigDecimal(JFrame parent, JTextField jTextField, String fieldName) {
        String targetBigDecimalText = Utils.formatBigDecimal(jTextField.getText());
        if (targetBigDecimalText.isEmpty()) {
            Utils.emptyFieldError(parent, fieldName);
            jTextField.requestFocusInWindow();
            return null;
        }

        jTextField.setText(targetBigDecimalText);

        BigDecimal targetBigDecimal;
        try {
            targetBigDecimal = new BigDecimal(targetBigDecimalText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("financeNumberFormatException"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE);

            jTextField.requestFocusInWindow();
            return null;
        }
        if (targetBigDecimal.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("financeNumberCannotBeLessZero"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE);
            jTextField.requestFocusInWindow();
            return null;
        }
        return targetBigDecimal;
    }

    public static BigDecimal parseSubdepartmentBigDecimal(boolean isCreateMode, JFrame parent, FinanceModel selectedFinanceModel, JTextField jTextField, String fieldName, BidType bidType) {
        BigDecimal targetBigDecimal = parseBigDecimal(parent, jTextField, fieldName);
        if (targetBigDecimal == null) {
            return null;
        } else if (targetBigDecimal.compareTo(selectedFinanceModel.getTotalAmount(bidType)) == 1) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("depFinanceAmountGreaterThanFinanceAmount"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE);
            jTextField.requestFocusInWindow();
            return null;
        } else if (isCreateMode && targetBigDecimal.compareTo(selectedFinanceModel.getUnassignedAmount(bidType)) == 1) {
            BigDecimal unassignedAmount = selectedFinanceModel.getUnassignedAmount(bidType);
            JOptionPane.showMessageDialog(parent, Labels.getProperty("depFinanceAmountGreaterThanAvailableFinanceAmount") + ".\n" + Labels.withColon("unassignedFinanceAmount") + " " + unassignedAmount + Labels.withSpaceBefore("uah"), Labels.getProperty("fieldErr"), JOptionPane.ERROR_MESSAGE);
            jTextField.setText(unassignedAmount.toString());
            jTextField.requestFocusInWindow();
            return null;
        } else
            return targetBigDecimal;
    }

    public static void setPreferredButtonSizes(JButton button1, JButton button2) {
        Dimension button1Size = button1.getPreferredSize();
        Dimension button2Size = button2.getPreferredSize();
        if (button1Size.getWidth() > button2Size.getWidth()) {
            button2.setPreferredSize(button1Size);
        } else {
            button1.setPreferredSize(button2Size);
        }
    }

    public static <T extends AbstractModel> void setBoxData(JComboBox<T> comboBox, java.util.List<T> db, T emptyModel, boolean isFirstEmpty) {
        comboBox.removeAllItems();
        if (isFirstEmpty) {
            comboBox.addItem(emptyModel);
        }
        if (!isFirstEmpty && (db == null || db.isEmpty())) {
            comboBox.addItem(emptyModel);
        } else if (db != null && !db.isEmpty()) {
            for (T model : db) {
                if (model.isActive()) {
                    comboBox.addItem(model);
                }
            }
        }
        comboBox.repaint();
    }
}
