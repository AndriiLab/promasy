package com.github.andriilab.promasy.persistence.repositories;

import com.github.andriilab.promasy.domain.versioning.entities.Version;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;

/**
 * DB access com.github.andriilab.promasy.domain.model for handling program version
 */
public class VersionRepository {

    private static String defaultVersion = "0.8.0";
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
            version = defaultVersion;

            entityManager.getTransaction().begin();

            Version currentVersion = new Version(version);
            entityManager.persist(currentVersion);

            entityManager.getTransaction().commit();
        }

        return version;
    }

    public void updateVersion(String version) {
        entityManager.getTransaction().begin();

        Version minimumVersion = entityManager.find(Version.class, 1);
        minimumVersion.setVersion(version);

        entityManager.getTransaction().commit();
    }
}
