package model.dao;

import gui.Labels;
import model.models.Version;

import javax.persistence.EntityManager;
import java.sql.SQLException;

/**
 * DB access model for handling program version
 */
public class VersionQueries {

    public String retrieve() throws SQLException {
        String version;

        EntityManager em = Database.DB.getEntityManager();

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


    public void updateVersion() throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();

        Version minimumVersion = em.find(Version.class, 1);
        minimumVersion.set(Labels.getVersion());

        em.getTransaction().commit();
    }
}
