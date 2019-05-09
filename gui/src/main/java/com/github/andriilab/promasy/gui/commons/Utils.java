package com.github.andriilab.promasy.gui.commons;

import javax.swing.*;

public class Utils {
    /**
     * Function determines row with searchObject in given table
     *
     * @param table             {@link JTable} with objects
     * @param columnWithObjects index of column, where searchObjects stored
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
}
