package gui.commons;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Getter class for LabelsBundle.properties
 */
public class Labels {
    private static final String LABELS_BUNDLE = "LabelsBundle";
    private static final ResourceBundle LABELS = ResourceBundle.getBundle(LABELS_BUNDLE);
    private static final String VERSION_BUNDLE = "PromasyVersion";
    private static final ResourceBundle VERSION = ResourceBundle.getBundle(VERSION_BUNDLE);

    static {
        UIManager.put("OptionPane.yesButtonText", getProperty("yes"));
        UIManager.put("OptionPane.noButtonText", getProperty("no"));
        UIManager.put("OptionPane.cancelButtonText", getProperty("cancel"));
    }

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
    public static String withDot(final String key) {
        return getProperty(key) + ".";
    }
    public static String withThreeDots(final String key) {
        return getProperty(key) + "...";
    }

    public static String quoted(final String key) {
        return "'" + getProperty(key) + "'";
    }

    public static String getVersion() {
        return VERSION.getString("versionNumber");
    }
    public static String getBuildDate() {
        return VERSION.getString("buildDate");
    }

    public static int getBuildYear() {
        return LocalDateTime.parse(getBuildDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).getYear();
    }
}


