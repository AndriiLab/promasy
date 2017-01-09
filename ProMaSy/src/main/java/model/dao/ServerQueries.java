package model.dao;

import javax.persistence.EntityManager;
import java.sql.Timestamp;

/**
 * Various queries to the server
 */
public class ServerQueries {

    public static Timestamp getServerTimestamp() {
        EntityManager em = Database.DB.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Timestamp serverTime = (Timestamp) em.createNativeQuery("SELECT current_timestamp").getSingleResult();
        em.getTransaction().commit();
        em.close();

        return serverTime;
    }
}
