package com.github.andriilab.promasy.app.commons;

import com.github.andriilab.promasy.app.controller.Logger;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class SystemCommands {
    public static void copyToClipboard(String string) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(string), null);
    }

    public static void showInExplorer(String path) {
        try {
            Runtime.getRuntime().exec("explorer.exe /select, " + path);
        } catch (IOException e) {
            Logger.errorEvent(Utils.class, null, e);
        }
    }
}
