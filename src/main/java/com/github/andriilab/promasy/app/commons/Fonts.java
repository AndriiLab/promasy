package com.github.andriilab.promasy.app.commons;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class Fonts {

    private Fonts() {
    }

    public static void setInterfaceFont() {
        Map<Integer, Font> fonts = new HashMap<>();
        fonts.put(Font.PLAIN, getFont("", 13f));
        fonts.put(Font.BOLD, getFont("", 13.5f));
        fonts.put(Font.ITALIC, getFont("", 13f));

        changeFont(fonts);
    }

    private static void changeFont(Map<Integer, Font> fonts) {
        for (Map.Entry<Object, Object> entry : UIManager.getDefaults().entrySet()) {
            Object key = entry.getKey();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource oldFont) {
                FontUIResource newFont = new FontUIResource(fonts.get(oldFont.getStyle()));
                UIManager.put(key, newFont);
            }
        }
    }

    private static Font getFont(String path, float size) {
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            assert stream != null;
            return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return Font.getFont(Font.SANS_SERIF);
        }
    }
}
