package gui;

import controller.Logger;
import gui.commons.Icons;
import gui.commons.Labels;
import gui.components.PJOptionPane;
import model.enums.BidType;
import model.models.ConnectionSettingsModel;
import model.models.FinanceModel;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Utils {
    /**
     * Function determines row with searchObject in given table
     *
     * @param table             {@link JTable} with objects
     * @param columnWithObjects number of coumn, where searchObjects stored
     * @param searchObject      object, which row to be determined
     * @return number of column with object in table or -1 if searchObject doesn't exist in table
     */

    public static int getRowWithObject(JTable table, int columnWithObjects, Object searchObject) {
        for (int i = 0; i < table.getRowCount(); i++) {
            Object tableObject = table.getValueAt(i, columnWithObjects);
            if (tableObject == searchObject) {
                return i;
            }
        }
        return -1;
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
            Logger.warnEvent(e);
            return null;
        }
    }

    public static void saveConnectionSettings(ConnectionSettingsModel model) throws IOException {
        FileOutputStream fos = new FileOutputStream("settings.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.close();
        fos.close();
    }

    public static ConnectionSettingsModel loadConnectionSettings() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("settings.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ConnectionSettingsModel model = (ConnectionSettingsModel) ois.readObject();
        ois.close();
        fis.close();
        return model;
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

    public static Integer parseInteger(JFrame parent, JTextField jTextField, String fieldName) {
        String targetIntegerText = Utils.formatBigDecimal(jTextField.getText());
        if (targetIntegerText.isEmpty()) {
            PJOptionPane.emptyField(parent, fieldName);
            jTextField.requestFocusInWindow();
            return null;
        }
        Integer integer;
        try {
            integer = Integer.parseInt(targetIntegerText);
        } catch (NumberFormatException ex) {
            PJOptionPane.wrongFormat(parent, fieldName, Labels.getProperty("wrongFloatFormat"));
            jTextField.requestFocusInWindow();
            return null;
        }
        return integer;
    }

    public static BigDecimal parseBigDecimal(JFrame parent, JTextField jTextField, String fieldName) {
        String targetBigDecimalText = Utils.formatBigDecimal(jTextField.getText());
        if (targetBigDecimalText.isEmpty()) {
            PJOptionPane.emptyField(parent, fieldName);
            jTextField.requestFocusInWindow();
            return null;
        }

        jTextField.setText(targetBigDecimalText);

        BigDecimal targetBigDecimal;
        try {
            targetBigDecimal = new BigDecimal(targetBigDecimalText);
        } catch (NumberFormatException ex) {
            Logger.warnEvent(ex);
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("financeNumberFormatException"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE, Icons.ERROR);

            jTextField.requestFocusInWindow();
            return null;
        }
        if (targetBigDecimal.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("financeNumberCannotBeLessZero"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE, Icons.ERROR);
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
                    JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            jTextField.requestFocusInWindow();
            return null;
        } else if (isCreateMode && targetBigDecimal.compareTo(selectedFinanceModel.getUnassignedAmount(bidType)) == 1) {
            BigDecimal unassignedAmount = selectedFinanceModel.getUnassignedAmount(bidType);
            JOptionPane.showMessageDialog(parent, Labels.getProperty("depFinanceAmountGreaterThanAvailableFinanceAmount") + ".\n" + Labels.withColon("unassignedFinanceAmount") + " " + unassignedAmount + Labels.withSpaceBefore("uah"), Labels.getProperty("fieldErr"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            jTextField.setText(unassignedAmount.toString());
            jTextField.requestFocusInWindow();
            return null;
        } else {
            return targetBigDecimal;
        }
    }
}
