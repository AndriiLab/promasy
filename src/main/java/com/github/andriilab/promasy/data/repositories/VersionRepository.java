package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.versioning.entities.Version;
import com.github.andriilab.promasy.presentation.commons.Labels;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;

/**
 * DB access com.github.andriilab.promasy.domain.model for handling program version
 */
public class VersionRepository {

    private EntityManager entityManager;

    public VersionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String retrieve() throws JDBCException {
        String version;

        Version minimumVersion = entityManager.find(Version.class, 1);

        //trying to get min version
        if (minimumVersion != null) {
            version = minimumVersion.getVersion();
        } else {
            //if entry doesn't exist (0 rows returned) creating new entry equal to software version
            version = Labels.getVersion();

            entityManager.getTransaction().begin();

            Version currentVersion = new Version(version);
            entityManager.persist(currentVersion);

            entityManager.getTransaction().commit();
        }

        return version;
    }


    public void updateVersion() {
        entityManager.getTransaction().begin();

        Version minimumVersion = entityManager.find(Version.class, 1);
        minimumVersion.setVersion(Labels.getVersion());

        entityManager.getTransaction().commit();
    }
}
