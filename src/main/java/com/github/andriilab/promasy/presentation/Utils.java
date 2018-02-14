package com.github.andriilab.promasy.presentation;

import com.github.andriilab.promasy.data.controller.Logger;
import com.github.andriilab.promasy.data.storage.ConnectionSettings;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import com.github.andriilab.promasy.presentation.components.ErrorOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
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

    public static void copyToClipboard(String string) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(string), null);
    }

    public static void showInExplorer(String path) {
        try {
            Runtime.getRuntime().exec("explorer.exe /select, " + path);
        } catch (IOException e) {
            Logger.errorEvent(null, e);
        }
    }

    public static long makeSalt() {
        return new SecureRandom().nextLong();
    }

    public static String makePass(char[] pass, long salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(new String(pass).getBytes(StandardCharsets.UTF_8));
            md.update(ByteBuffer.allocate(Long.BYTES).putLong(salt).array());
            StringBuilder sb = new StringBuilder();
            for (byte b : md.digest()) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Logger.warnEvent(e);
            return null;
        }
    }

    public static void saveConnectionSettings(ConnectionSettings model) throws IOException {
        FileOutputStream fos = new FileOutputStream("settings.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.close();
        fos.close();
    }

    public static ConnectionSettings loadConnectionSettings() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("settings.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ConnectionSettings model = (ConnectionSettings) ois.readObject();
        ois.close();
        fis.close();
        return model;
    }

    public static String formatFinanceString(String bigDecimal) throws NumberFormatException {
        if (bigDecimal.contains(",")) {
            bigDecimal = bigDecimal.replace(",", ".");
            //if has more than 2 digits after '.' + dot (3) throw exception
            if (bigDecimal.substring(bigDecimal.indexOf(".")).length() > 3) {
                throw new NumberFormatException();
            }
        }
        if (bigDecimal.contains(" ")) {
            bigDecimal = bigDecimal.replace(" ", "");
        }
        return bigDecimal;
    }

    public static Integer parseInteger(JFrame parent, JTextField jTextField, String fieldName) {
        String targetIntegerText = jTextField.getText();
        if (targetIntegerText.isEmpty()) {
            ErrorOptionPane.emptyField(parent, fieldName);
            jTextField.requestFocusInWindow();
            return null;
        }
        Integer integer;
        try {
            targetIntegerText = formatFinanceString(targetIntegerText);
            integer = Integer.parseInt(targetIntegerText);
        } catch (NumberFormatException ex) {
            ErrorOptionPane.wrongFormat(parent, fieldName, Labels.getProperty("wrongIntegerFormat"));
            jTextField.requestFocusInWindow();
            return null;
        }
        return integer;
    }

    public static BigDecimal parseBigDecimal(JFrame parent, JTextField jTextField, String fieldName) {
        String targetBigDecimalText = jTextField.getText();

        if (targetBigDecimalText.isEmpty()) {
            ErrorOptionPane.emptyField(parent, fieldName);
            jTextField.requestFocusInWindow();
            return null;
        }

        BigDecimal targetBigDecimal;

        try {
            targetBigDecimalText = formatFinanceString(targetBigDecimalText);

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

    public static BigDecimal parseSubdepartmentBigDecimal(boolean isCreateMode, JFrame parent, Finance selectedFinanceModel, BigDecimal unassignedAmount, JTextField jTextField, String fieldName, BidType bidType) {
        BigDecimal targetBigDecimal = parseBigDecimal(parent, jTextField, fieldName);
        if (targetBigDecimal == null) {
            return null;
        } else if (targetBigDecimal.compareTo(selectedFinanceModel.getTotalAmount(bidType)) > 0) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("depFinanceAmountGreaterThanFinanceAmount"),
                    Labels.getProperty("fieldErr"),
                    JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            jTextField.requestFocusInWindow();
            return null;
        } else if (isCreateMode && targetBigDecimal.compareTo(unassignedAmount) > 0) {
            JOptionPane.showMessageDialog(parent, Labels.getProperty("depFinanceAmountGreaterThanAvailableFinanceAmount") + ".\n" + Labels.withColon("unassignedFinanceAmount") + " " + unassignedAmount + Labels.withSpaceBefore("uah"), Labels.getProperty("fieldErr"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            jTextField.setText(unassignedAmount.toString());
            jTextField.requestFocusInWindow();
            return null;
        } else {
            return targetBigDecimal;
        }
    }
}
