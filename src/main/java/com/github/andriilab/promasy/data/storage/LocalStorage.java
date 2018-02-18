package com.github.andriilab.promasy.data.storage;

import java.io.*;

public class LocalStorage {

    public static void saveConnectionSettings(ConnectionSettings model) throws IOException {
        FileOutputStream fos = new FileOutputStream("settings.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.close();
        fos.close();
    }

    public static ConnectionSettings loadConnectionSettings() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("settings.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ConnectionSettings model = (ConnectionSettings) ois.readObject();
        ois.close();
        fis.close();
        return model;
    }
}
