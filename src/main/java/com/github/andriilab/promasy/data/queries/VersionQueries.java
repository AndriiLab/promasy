package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.versioning.entities.Version;
import com.github.andriilab.promasy.presentation.commons.Labels;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;

/**
 * DB access com.github.andriilab.promasy.domain.model for handling program version
 */
public class VersionQueries {

    public String retrieve() throws JDBCException {
        String version;

        EntityManager em = Database.CONNECTOR.getEntityManager();

        Version minimumVersion = em.find(Version.class, 1);

        //trying to get min version
        if (minimumVersion != null) {
            version = minimumVersion.get();
        } else {
            //if entry doesn't exist (0 rows returned) creating new entry equal to software version
            version = Labels.getVersion();

            em.getTransaction().begin();

            Version currentVersion = new Version(version);
            em.persist(currentVersion);

            em.getTransaction().commit();
        }

        return version;
    }


    public void updateVersion() {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();

        Version minimumVersion = em.find(Version.class, 1);
        minimumVersion.set(Labels.getVersion());

        em.getTransaction().commit();
    }
}
