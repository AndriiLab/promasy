package com.github.andriilab.promasy.data.storage;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectionSettingsTest {

    private final ConnectionSettings settings =
            new ConnectionSettings("db.example.org", "promasy_db", "promasy", 5433, "alice", "s3cret");

    @Test
    void getters_returnConstructorValues() {
        assertThat(settings.getServer()).isEqualTo("db.example.org");
        assertThat(settings.getDatabase()).isEqualTo("promasy_db");
        assertThat(settings.getSchema()).isEqualTo("promasy");
        assertThat(settings.getPortNumber()).isEqualTo(5433);
        assertThat(settings.getUser()).isEqualTo("alice");
        assertThat(settings.getPassword()).isEqualTo("s3cret");
    }

    @Test
    void getUrl_buildsPostgresJdbcUrl() {
        assertThat(settings.getUrl()).isEqualTo("jdbc:postgresql://db.example.org:5433/promasy_db");
    }

    @Test
    void getProperties_containsUrlAndPersistenceCredentials() {
        Map<String, String> properties = settings.getProperties();

        assertThat(properties)
                .containsEntry("javax.persistence.jdbc.url", "jdbc:postgresql://db.example.org:5433/promasy_db")
                .containsEntry("javax.persistence.jdbc.user", "alice")
                .containsEntry("javax.persistence.jdbc.password", "s3cret")
                .containsEntry("hibernate.default_schema", "promasy");
    }
}
