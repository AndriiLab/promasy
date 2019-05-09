package com.github.andriilab.promasy.gui.commons;

public class Formatters {
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
}
