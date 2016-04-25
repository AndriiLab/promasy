package gui;

import java.util.ResourceBundle;

/**
 * Created by A on 26.04.2016.
 */
public class LabelsLocale {
    private static final String FILENAME = "LabelsBundle";
    private static final ResourceBundle LABELS = ResourceBundle.getBundle(FILENAME);

    public static String getProperty(final String key) {
        return LABELS.getString(key);
    }

}
