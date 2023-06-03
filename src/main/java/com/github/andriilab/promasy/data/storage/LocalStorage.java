package com.github.andriilab.promasy.data.storage;

import java.io.*;

public class LocalStorage {

    public static void saveConnectionSettings(ConnectionSettings model) throws IOException {
        try (FileOutputStream fos = new FileOutputStream("settings.ser")) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(model);
            }
        }
    }

    public static ConnectionSettings loadConnectionSettings() throws IOException, ClassNotFoundException {
        ConnectionSettings model;
        try (FileInputStream fis = new FileInputStream("settings.ser")) {
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                model = (ConnectionSettings) ois.readObject();
            }
        }
        return model;
    }
}
