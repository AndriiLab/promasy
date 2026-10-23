package com.github.andriilab.promasy.data.storage;

import java.io.*;

public class LocalStorage {

    private static final File SETTINGS_FILE = new File("settings.ser");

    public static void saveConnectionSettings(ConnectionSettings model) throws IOException {
        saveConnectionSettings(model, SETTINGS_FILE);
    }

    public static void saveConnectionSettings(ConnectionSettings model, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(model);
            }
        }
    }

    public static ConnectionSettings loadConnectionSettings() throws IOException, ClassNotFoundException {
        return loadConnectionSettings(SETTINGS_FILE);
    }

    public static ConnectionSettings loadConnectionSettings(File file) throws IOException, ClassNotFoundException {
        ConnectionSettings model;
        try (FileInputStream fis = new FileInputStream(file)) {
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                model = (ConnectionSettings) ois.readObject();
            }
        }
        return model;
    }
}
