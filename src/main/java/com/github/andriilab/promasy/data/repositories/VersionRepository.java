package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.versioning.entities.Version;
import com.github.andriilab.promasy.app.commons.Labels;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;

/**
 * DB access com.github.andriilab.promasy.domain.model for handling program version
 */
public class VersionRepository {

    private final EntityManager entityManager;

    public VersionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String retrieve() throws JDBCException {
        Version minimumVersion = Repository.readOnly(entityManager, () -> entityManager.find(Version.class, 1));

        //trying to get min version
        if (minimumVersion != null) {
            return minimumVersion.getVersion();
        }

        //if entry doesn't exist (0 rows returned) creating new entry equal to software version
        String version = Labels.getVersion();
        Repository.inTransaction(entityManager, () -> entityManager.persist(new Version(version)));

        return version;
    }


    public void updateVersion() {
        Repository.inTransaction(entityManager, () -> {
            Version minimumVersion = entityManager.find(Version.class, 1);
            minimumVersion.setVersion(Labels.getVersion());
        });
    }
}
