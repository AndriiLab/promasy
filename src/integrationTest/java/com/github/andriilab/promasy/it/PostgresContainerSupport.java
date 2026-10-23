package com.github.andriilab.promasy.it;

import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.data.storage.ConnectionSettings;
import com.github.andriilab.promasy.data.storage.DbConnector;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * One PostgreSQL container shared by every integration test in the JVM (Ryuk removes it when the JVM exits).
 * <p>
 * {@code sql/db_clean.sql} uses {@code COPY ... FROM stdin}, so it can't run through JDBC; the image's entrypoint runs it
 * with psql instead. The dump assigns ownership to {@code postgres}, so that must be the superuser's name. The
 * application connects as {@code promasy_user}, which the dump creates with the bundle's default password and only
 * SELECT/INSERT/UPDATE grants, exactly like a real installation.
 */
public final class PostgresContainerSupport {

    /** Id of the employee {@code it-seed.sql} inserts; negative so it never collides with hilo-generated ids. */
    public static final long ADMIN_ID = -1L;

    private static final String SUPERUSER = "postgres";
    private static final String SUPERUSER_PASSWORD = "postgres";

    private static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:17-alpine"))
                    .withUsername(SUPERUSER)
                    .withPassword(SUPERUSER_PASSWORD)
                    .withDatabaseName("promasy")
                    .withCopyFileToContainer(MountableFile.forHostPath("sql/db_clean.sql"),
                            "/docker-entrypoint-initdb.d/10-schema.sql")
                    .withCopyFileToContainer(MountableFile.forClasspathResource("it-seed.sql"),
                            "/docker-entrypoint-initdb.d/20-it-seed.sql");

    static {
        POSTGRES.start();
        try {
            DbConnector.INSTANCE.connect(new ConnectionSettings(POSTGRES.getHost(), POSTGRES.getDatabaseName(),
                    Labels.getProperty("connectionSettings.schema"), POSTGRES.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                    Labels.getProperty("connectionSettings.user"), Labels.getProperty("connectionSettings.password")));
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private PostgresContainerSupport() {
    }

    /** The application's shared {@link EntityManager}, connected as {@code promasy_user}. */
    public static EntityManager entityManager() {
        return DbConnector.INSTANCE.getEntityManager();
    }

    /** A fresh superuser connection, for setup and checks the application user isn't allowed to do. */
    public static Connection superuserConnection() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), SUPERUSER, SUPERUSER_PASSWORD);
    }
}
