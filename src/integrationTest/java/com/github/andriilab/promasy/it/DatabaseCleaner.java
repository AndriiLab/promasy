package com.github.andriilab.promasy.it;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Brings the database back to the state {@code db_clean.sql} + {@code it-seed.sql} left it in: CPV data, the version
 * row and the seeded admin stay, everything else goes.
 * <p>
 * The foreign keys are circular ({@code subdepartments.created_by -> employees.subdep_id -> subdepartments}), so FK
 * triggers are switched off for the cleanup transaction via {@code session_replication_role}, which needs a superuser.
 */
final class DatabaseCleaner {

    private static final String[] DATA_TABLES = {
            "addresses", "amount_units", "bid_statuses", "bids", "departments", "finance_dep", "finances",
            "institutes", "producers", "reasons_for_suppl", "registrations", "subdepartments", "suppliers", "version"
    };

    private DatabaseCleaner() {
    }

    static void clean() throws SQLException {
        try (Connection connection = PostgresContainerSupport.superuserConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute("SET LOCAL session_replication_role = replica");
            for (String table : DATA_TABLES) {
                statement.execute("DELETE FROM promasy." + table);
            }
            statement.execute("DELETE FROM promasy.employees WHERE id <> " + PostgresContainerSupport.ADMIN_ID);
            statement.execute("INSERT INTO promasy.version (id, version_allowed) VALUES (1, '0.8.0')");
            connection.commit();
        }
    }
}
