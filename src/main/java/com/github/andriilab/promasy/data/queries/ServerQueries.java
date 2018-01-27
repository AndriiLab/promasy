package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.data.storage.Database;

import javax.persistence.EntityManager;
import java.sql.Timestamp;

/**
 * Various queries to the server
 */
public class ServerQueries {

    public static Timestamp getServerTimestamp() {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        Timestamp serverTime = (Timestamp) em.createNativeQuery("SELECT current_timestamp").getSingleResult();
        em.getTransaction().commit();

        return serverTime;
    }

}
