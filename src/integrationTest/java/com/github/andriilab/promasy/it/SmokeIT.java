package com.github.andriilab.promasy.it;

import com.github.andriilab.promasy.data.commands.CreateCommand;
import com.github.andriilab.promasy.data.commands.CommandsHandler;
import com.github.andriilab.promasy.data.repositories.ServerRepository;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Proves the setup: container, schema from {@code db_clean.sql}, application user, JDK 8 toolchain.
 */
class SmokeIT extends AbstractIntegrationTest {

    @Test
    void testJvm_runsOnToolchain_isJava8() {
        assertThat(System.getProperty("java.version")).startsWith("1.8");
    }

    @Test
    void connection_applicationSettings_runsAsPromasyUser() {
        Object currentUser = em.createNativeQuery("SELECT current_user").getSingleResult();

        assertThat(currentUser).isEqualTo("promasy_user");
    }

    @Test
    void versionRetrieve_dumpLoaded_returnsDumpVersion() {
        assertThat(storage.VERSIONS.retrieve()).isEqualTo("0.8.0");
    }

    @Test
    void cpvValidateCode_dumpLoaded_findsKnownCode() {
        Cpv cpv = storage.CPV.validateCode("03000000-1");

        assertThat(cpv.getCpvId()).isEqualTo("03000000-1");
    }

    @Test
    void getServerTimestamp_connected_isCloseToNow() {
        Timestamp serverTime = ServerRepository.getServerTimestamp();

        assertThat(Math.abs(serverTime.getTime() - System.currentTimeMillis()))
                .isLessThan(TimeUnit.MINUTES.toMillis(5));
    }

    @Test
    void createCommand_amountUnit_persistsWithAuditFields() {
        AmountUnit unit = new AmountUnit("kg");

        new CommandsHandler(storage).handle(new CreateCommand<>(unit));

        AmountUnit stored = em.find(AmountUnit.class, unit.getModelId());
        assertThat(unit.getModelId()).isPositive();
        assertThat(stored.getCreatedEmployee()).isEqualTo(admin);
        assertThat(stored.getCreatedDate()).isNotNull();
    }
}
