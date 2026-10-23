package com.github.andriilab.promasy.app.commons;

public final class Formatters {

    private Formatters() {
    }

    public static String formatFinanceString(String bigDecimal) throws NumberFormatException {
        bigDecimal = bigDecimal.replace(",", ".").replace(" ", "");
        //if has more than 2 digits after '.' + dot (3) throw exception
        if (bigDecimal.contains(".") && bigDecimal.substring(bigDecimal.indexOf(".")).length() > 3) {
            throw new NumberFormatException();
        }
        return bigDecimal;
    }
}
