package com.github.andriilab.promasy.app.commons;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Getter class for LabelsBundle.properties
 */
public final class Labels {
    private static final String LABELS_BUNDLE_NAME = "LabelsBundle";
    private static final ResourceBundle LABELS_BUNDLE = ResourceBundle.getBundle(LABELS_BUNDLE_NAME);
    private static final String VERSION_BUNDLE_NAME = "PromasyVersion";
    private static final ResourceBundle VERSION_BUNDLE = ResourceBundle.getBundle(VERSION_BUNDLE_NAME);

    static {
        UIManager.put("OptionPane.yesButtonText", getProperty("yes"));
        UIManager.put("OptionPane.noButtonText", getProperty("no"));
        UIManager.put("OptionPane.cancelButtonText", getProperty("cancel"));
    }

    private Labels() {
    }

    public static String getProperty(final String key) {
        return LABELS_BUNDLE.getString(key);
    }

    public static int getInt(final String key) {
        return Integer.parseInt(LABELS_BUNDLE.getString(key));
    }

    public static String withColon(final String key) {
        return getProperty(key) + ": ";
    }

    public static String withSpaceBefore(final String key) {
        return " " + getProperty(key);
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
        return VERSION_BUNDLE.getString("versionNumber");
    }

    public static String getBuildDate() {
        return VERSION_BUNDLE.getString("buildDate");
    }

    public static int getBuildYear() {
        return LocalDateTime.parse(getBuildDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).getYear();
    }
}


