package com.github.andriilab.promasy.app.components.panes;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Icons;
import com.github.andriilab.promasy.app.commons.Labels;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * Dialogs with various errors
 */
public class ErrorOptionPane {

    public static void emptyField(JFrame parent, String fieldName) {
        String field = fieldName == null ? "" : " \"" + fieldName + "\"";
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("enterDataIntoField") + field,
                Labels.getProperty("fieldCannotBeEmpty") + field,
                JOptionPane.ERROR_MESSAGE, Icons.ERROR);
    }

    public static void longField(JFrame parent, String fieldName, int size) {
        String field = fieldName == null ? "" : " \"" + fieldName + "\" ";
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("maxFieldSize") + field + size + Labels.withSpaceBefore("chars"),
                Labels.getProperty("tooLongField") + field,
                JOptionPane.ERROR_MESSAGE, Icons.ERROR);
    }

    public static int renameEntry(JFrame parent, String oldFieldName, String newFieldName) {
        return JOptionPane.showConfirmDialog(parent,
                Labels.getProperty("renameField") +
                        "\n\t" + Labels.withColon("newName") + " \"" + newFieldName + "\"" +
                        "\n\t" + Labels.withColon("oldName") + " \"" + oldFieldName + "\"",
                Labels.getProperty("confirmAction"),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, Icons.QUESTION);
    }

    public static boolean deleteEntry(JFrame parent, String propertyName, String entryName) {
        return JOptionPane.showConfirmDialog(parent, Labels.withSpaceAfter("confirmDeleteLong") + propertyName + " '" + entryName + "'?", Labels.withSpaceAfter("delete") + propertyName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, Icons.QUESTION) == JOptionPane.YES_OPTION;
    }

    public static void wrongFormat(JFrame parent, String fieldName, String hints) {
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("wrongFormat") + " \"" + fieldName + "\"\n" + Labels.getProperty("checkInput") + "\n" + hints,
                Labels.getProperty("fieldErr"),
                JOptionPane.ERROR_MESSAGE, Icons.ERROR);
    }

    public static void criticalError(JFrame parent) {
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("criticalErrorHappened"),
                Labels.getProperty("criticalError"),
                JOptionPane.ERROR_MESSAGE, Icons.ERROR);
    }

    public static void emptyModelSelected(JFrame parent, String name) {
        JOptionPane.showMessageDialog(parent,
                Labels.withSpaceAfter("youDidNotChooseModel") + name,
                Labels.getProperty("fieldErr"),
                JOptionPane.ERROR_MESSAGE, Icons.ERROR);
    }

    public static void insufficientFunds(MainFrame parent, BigDecimal financeLeft, BidType currentBid) {
        JOptionPane.showMessageDialog(parent,
                Labels.withSpaceAfter("insufficientFundsMessage") + currentBid.toString() + ": " + financeLeft.toString() + Labels.withSpaceBefore("uah"),
                Labels.getProperty("insufficientFunds"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
    }
}
