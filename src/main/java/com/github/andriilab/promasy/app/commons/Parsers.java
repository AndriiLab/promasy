package com.github.andriilab.promasy.app.commons;

import com.github.andriilab.promasy.app.controller.Logger;
import com.github.andriilab.promasy.app.components.panes.ErrorOptionPane;

import javax.swing.*;
import java.math.BigDecimal;

public final class Parsers {

    private Parsers() {
    }

    public static Integer parseInteger(JFrame parent, JTextField jTextField, String fieldName) {
        String targetIntegerText = jTextField.getText();
        if (targetIntegerText.isEmpty()) {
            ErrorOptionPane.emptyField(parent, fieldName);
            jTextField.requestFocusInWindow();
            return null;
        }
        int integer;
        try {
            targetIntegerText = Formatters.formatFinanceString(targetIntegerText);
            integer = Integer.parseInt(targetIntegerText);
        } catch (NumberFormatException ex) {
            ErrorOptionPane.wrongFormat(parent, fieldName, Labels.getProperty("wrongIntegerFormat"));
            jTextField.requestFocusInWindow();
            return null;
        }
        return integer;
    }

    /**
     * Converts user-entered finance text (comma or dot separator, optional spaces) to a number.
     *
     * @throws NumberFormatException if the text is not a number or has more than 2 decimal places
     */
    static BigDecimal toFinanceAmount(String text) {
        return new BigDecimal(Formatters.formatFinanceString(text));
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
            targetBigDecimal = toFinanceAmount(targetBigDecimalText);
        } catch (NumberFormatException ex) {
            Logger.warnEvent(Utils.class, ex);
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

    public static BigDecimal parseSubdepartmentBigDecimal(JFrame parent,
                                                          BigDecimal unassignedAmount,
                                                          JTextField jTextField,
                                                          String fieldName) {
        BigDecimal targetBigDecimal = parseBigDecimal(parent, jTextField, fieldName);

        if (targetBigDecimal == null) {
            return null;
        }

        if (targetBigDecimal.compareTo(unassignedAmount) > 0) {
            JOptionPane.showMessageDialog(parent,
                    Labels.getProperty("depFinanceAmountGreaterThanAvailableFinanceAmount") + ".\n"
                            + Labels.withColon("unassignedFinanceAmount")
                            + " " + unassignedAmount + Labels.withSpaceBefore("uah"),
                    Labels.getProperty("fieldErr"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            jTextField.setText(unassignedAmount.toString());
            jTextField.requestFocusInWindow();
            return null;
        }

        return targetBigDecimal;
    }
}
