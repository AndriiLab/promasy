package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.it.AbstractIntegrationTest;
import com.github.andriilab.promasy.it.Sql;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The minimum-client-version row that the startup version gate reads.
 */
class VersionRepositoryIT extends AbstractIntegrationTest {

    private static Object storedVersion() {
        return Sql.scalar("SELECT version_allowed FROM promasy.version WHERE id = 1");
    }

    @Test
    void retrieve_seededRow_returnsStoredVersion() {
        assertThat(storage.VERSIONS.retrieve()).isEqualTo("0.8.0");
    }

    @Test
    void retrieve_rowChangedOutsideApplication_returnsNewVersionOnceEntityManagerIsCleared() {
        Sql.execute("UPDATE promasy.version SET version_allowed = '0.9.1' WHERE id = 1");
        em.clear();

        assertThat(storage.VERSIONS.retrieve()).isEqualTo("0.9.1");
    }

    @Test
    void retrieve_noRow_insertsClientVersionAndReturnsIt() {
        Sql.execute("DELETE FROM promasy.version");

        String version = storage.VERSIONS.retrieve();

        assertThat(version).isEqualTo(Labels.getVersion());
        assertThat(storedVersion()).isEqualTo(Labels.getVersion());
    }

    @Test
    void updateVersion_olderMinimumStored_raisesItToClientVersion() {
        storage.VERSIONS.updateVersion();

        assertThat(storedVersion()).isEqualTo(Labels.getVersion());
        assertThat(storage.VERSIONS.retrieve()).isEqualTo(Labels.getVersion());
    }
}
