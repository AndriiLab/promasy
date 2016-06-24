package gui;

import java.util.ResourceBundle;

/**
 * Created by A on 26.04.2016.
 */
public class Labels {
    private static final String FILENAME = "resources/LabelsBundle";
    private static final ResourceBundle LABELS = ResourceBundle.getBundle(FILENAME);

    public static String getProperty(final String key) {
        return LABELS.getString(key);
    }

    public static String withColon(final String key) {
        return getProperty(key) + ": ";
    }

    public static String withSpaceBefore(final String key) {
        return " "+getProperty(key);
    }

    public static String withThreeDots(final String key) {
        return getProperty(key) + "...";
    }
}


