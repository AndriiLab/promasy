package com.github.andriilab.promasy.it;

import com.github.andriilab.promasy.data.authorization.LoginData;
import com.github.andriilab.promasy.data.storage.Storage;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import java.sql.SQLException;

/**
 * Base class for integration tests: the shared container, the application's {@link EntityManager} and
 * {@link Storage}, and the seeded admin logged in through {@link LoginData}. Every test starts from a clean database.
 */
public abstract class AbstractIntegrationTest {

    protected static EntityManager em;
    protected static Storage storage;
    protected static Employee admin;

    @BeforeAll
    static void connect() {
        em = PostgresContainerSupport.entityManager();
        storage = new Storage(em);
        // LoginData is write-once per JVM, so every test class gets the same admin instance
        admin = LoginData.getInstance(em.find(Employee.class, PostgresContainerSupport.ADMIN_ID));
    }

    @BeforeEach
    void cleanDatabase() throws SQLException {
        DatabaseCleaner.clean();
        em.clear();
        // clear() detached the admin; audit fields cascade PERSIST, so a detached admin would fail every create
        em.unwrap(Session.class).buildLockRequest(LockOptions.NONE).lock(admin);
    }
}
