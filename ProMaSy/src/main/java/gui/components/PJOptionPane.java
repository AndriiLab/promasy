package gui.components;

import gui.MainFrame;
import gui.commons.Labels;
import model.enums.BidType;
import model.models.EmptyModel;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * Dialogs with various errors
 */
public class PJOptionPane {

    public static void emptyField(JFrame parent, String fieldName) {
        String field = fieldName == null ? EmptyModel.STRING : " \"" + fieldName + "\"";
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("enterDataIntoField") + field,
                Labels.getProperty("fieldCannotBeEmpty") + field,
                JOptionPane.ERROR_MESSAGE);
    }

    public static int renameEntry(JFrame parent, String oldFieldName, String newFieldName) {
        return JOptionPane.showConfirmDialog(parent,
                Labels.getProperty("renameField") +
                        "\n\t" + Labels.withColon("newName") + " \"" + newFieldName + "\"" +
                        "\n\t" + Labels.withColon("oldName") + " \"" + oldFieldName + "\"",
                Labels.getProperty("confirmAction"),
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

    public static boolean deleteEntry(JFrame parent, String propertyName, String entryName) {
        return JOptionPane.showConfirmDialog(parent, Labels.withSpaceAfter("confirmDeleteLong") + propertyName + " '" + entryName + "'?", Labels.withSpaceAfter("delete") + propertyName, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static void wrongFormat(JFrame parent, String fieldName, String hints) {
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("wrongFormat") + " \"" + fieldName + "\"\n" + Labels.getProperty("checkInput") + "\n" + hints,
                Labels.getProperty("fieldErr"),
                JOptionPane.ERROR_MESSAGE);
    }

    public static void criticalError(JFrame parent) {
        JOptionPane.showMessageDialog(parent,
                Labels.getProperty("criticalErrorHappened"),
                Labels.getProperty("criticalError"),
                JOptionPane.ERROR_MESSAGE);
    }

    public static void emptyModelSelected(JFrame parent, String name) {
        JOptionPane.showMessageDialog(parent,
                Labels.withSpaceAfter("youDidNotChooseModel") + name,
                Labels.getProperty("fieldErr"),
                JOptionPane.ERROR_MESSAGE);
    }

    public static void insufficientFunds(MainFrame parent, BigDecimal financeLeft, BidType currentBid) {
        JOptionPane.showMessageDialog(parent,
                Labels.withSpaceAfter("insufficientFundsMessage") + currentBid.toString() + ": " + financeLeft.toString() + Labels.withSpaceBefore("uah"),
                Labels.getProperty("insufficientFunds"), JOptionPane.ERROR_MESSAGE);
    }
}
