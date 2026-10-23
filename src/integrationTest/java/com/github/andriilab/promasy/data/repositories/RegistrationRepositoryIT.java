package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.it.AbstractIntegrationTest;
import com.github.andriilab.promasy.it.Sql;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The registration ticket counter (the single {@code registrations} row) against real SQL.
 */
class RegistrationRepositoryIT extends AbstractIntegrationTest {

    private static Object storedTickets() {
        return Sql.scalar("SELECT registrations_left FROM promasy.registrations WHERE id = 1");
    }

    @Test
    void getRegistrationsLeft_noRow_returnsZero() {
        assertThat(storage.REGISTRATION.getRegistrationsLeft()).isZero();
    }

    @Test
    void getRegistrationsLeft_rowPresent_returnsStoredNumber() {
        Sql.execute("INSERT INTO promasy.registrations (id, registrations_left) VALUES (1, 7)");

        assertThat(storage.REGISTRATION.getRegistrationsLeft()).isEqualTo(7);
    }

    @Test
    void useRegistration_ticketsLeft_decrementsStoredNumberAndReturnsNumberBeforeUse() {
        Sql.execute("INSERT INTO promasy.registrations (id, registrations_left) VALUES (1, 3)");

        int result = storage.REGISTRATION.useRegistration();

        assertThat(result).isEqualTo(3);
        assertThat(storedTickets()).isEqualTo(2);
        assertThat(storage.REGISTRATION.getRegistrationsLeft()).isEqualTo(2);
    }

    /** The controller allows the registration when the result is positive, so the last ticket must count. */
    @Test
    void useRegistration_lastTicket_returnsPositiveAndLeavesZero() {
        Sql.execute("INSERT INTO promasy.registrations (id, registrations_left) VALUES (1, 1)");

        assertThat(storage.REGISTRATION.useRegistration()).isPositive();
        assertThat(storedTickets()).isEqualTo(0);
        assertThat(storage.REGISTRATION.useRegistration()).isZero();
        assertThat(storedTickets()).isEqualTo(0);
    }

    @Test
    void useRegistration_calledRepeatedly_allowsExactlyAsManyRegistrationsAsTickets() {
        Sql.execute("INSERT INTO promasy.registrations (id, registrations_left) VALUES (1, 2)");

        int allowed = 0;
        for (int i = 0; i < 4; i++) {
            if (storage.REGISTRATION.useRegistration() > 0) {
                allowed++;
            }
        }

        assertThat(allowed).isEqualTo(2);
        assertThat(storedTickets()).isEqualTo(0);
        assertThat(storage.REGISTRATION.getRegistrationsLeft()).isZero();
    }

    @Test
    void useRegistration_noRow_returnsZeroAndCreatesRowWithZeroTickets() {
        assertThat(storage.REGISTRATION.useRegistration()).isZero();

        assertThat(storedTickets()).isEqualTo(0);
    }

    @Test
    void changeNumberOfRegistrationTickets_rowPresent_replacesNumber() {
        Sql.execute("INSERT INTO promasy.registrations (id, registrations_left) VALUES (1, 3)");

        storage.REGISTRATION.changeNumberOfRegistrationTickets(10);

        assertThat(storedTickets()).isEqualTo(10);
        assertThat(storage.REGISTRATION.getRegistrationsLeft()).isEqualTo(10);
    }

    /** db_clean.sql ships the table empty, so this is the state of every fresh installation. */
    @Test
    void changeNumberOfRegistrationTickets_noRow_createsRow() {
        storage.REGISTRATION.changeNumberOfRegistrationTickets(5);

        assertThat(storedTickets()).isEqualTo(5);
        assertThat(storage.REGISTRATION.getRegistrationsLeft()).isEqualTo(5);
    }

    @Test
    void changeNumberOfRegistrationTickets_negativeNumber_storesZero() {
        Sql.execute("INSERT INTO promasy.registrations (id, registrations_left) VALUES (1, 3)");

        storage.REGISTRATION.changeNumberOfRegistrationTickets(-4);

        assertThat(storedTickets()).isEqualTo(0);
    }
}
