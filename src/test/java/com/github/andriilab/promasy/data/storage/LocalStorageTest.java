package com.github.andriilab.promasy.data.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalStorageTest {

    @TempDir
    File tempDir;

    @Test
    void saveAndLoad_roundTrip_restoresAllSettings() throws Exception {
        File file = new File(tempDir, "settings.ser");
        ConnectionSettings original =
                new ConnectionSettings("db.example.org", "promasy_db", "promasy", 5433, "alice", "s3cret");

        LocalStorage.saveConnectionSettings(original, file);
        ConnectionSettings loaded = LocalStorage.loadConnectionSettings(file);

        assertThat(loaded).isNotSameAs(original);
        assertThat(loaded.getServer()).isEqualTo("db.example.org");
        assertThat(loaded.getDatabase()).isEqualTo("promasy_db");
        assertThat(loaded.getSchema()).isEqualTo("promasy");
        assertThat(loaded.getPortNumber()).isEqualTo(5433);
        assertThat(loaded.getUser()).isEqualTo("alice");
        assertThat(loaded.getPassword()).isEqualTo("s3cret");
        assertThat(loaded.getProperties()).isEqualTo(original.getProperties());
    }

    @Test
    void save_existingFile_overwritesPreviousSettings() throws Exception {
        File file = new File(tempDir, "settings.ser");
        LocalStorage.saveConnectionSettings(new ConnectionSettings("old", "db", "s", 1, "u", "p"), file);

        LocalStorage.saveConnectionSettings(new ConnectionSettings("new", "db", "s", 2, "u", "p"), file);

        ConnectionSettings loaded = LocalStorage.loadConnectionSettings(file);
        assertThat(loaded.getServer()).isEqualTo("new");
        assertThat(loaded.getPortNumber()).isEqualTo(2);
    }

    @Test
    void load_missingFile_throwsFileNotFoundException() {
        File missing = new File(tempDir, "missing.ser");

        assertThatThrownBy(() -> LocalStorage.loadConnectionSettings(missing))
                .isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void load_fileWithoutSerializedObject_throwsIOException() throws IOException {
        File garbage = new File(tempDir, "garbage.ser");
        assertThat(garbage.createNewFile()).isTrue();

        assertThatThrownBy(() -> LocalStorage.loadConnectionSettings(garbage))
                .isInstanceOf(IOException.class);
    }
}
