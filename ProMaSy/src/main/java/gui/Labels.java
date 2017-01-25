package gui;

import java.util.ResourceBundle;

/**
 * Getter class for LabelsBundle.properties
 */
public class Labels {
    private static final String LABELS_BUNDLE = "LabelsBundle";
    private static final ResourceBundle LABELS = ResourceBundle.getBundle(LABELS_BUNDLE);
    private static final String VERSION_BUNDLE = "PromasyVersion";
    private static final ResourceBundle VERSION = ResourceBundle.getBundle(VERSION_BUNDLE);

    public static String getProperty(final String key) {
        return LABELS.getString(key);
    }

    public static int getInt(final String key) {
        return Integer.parseInt(LABELS.getString(key));
    }

    public static String withColon(final String key) {
        return getProperty(key) + ": ";
    }
    public static String withSpaceBefore(final String key) {
        return " "+getProperty(key);
    }

    public static String withSpaceAfter(final String key) {
        return getProperty(key) + " ";
    }
    public static String withThreeDots(final String key) {
        return getProperty(key) + "...";
    }

    public static String getVersion() {
        return VERSION.getString("versionNumber");
    }

    public static String getBuildDate() {
        return VERSION.getString("buildDate");
    }
}


