package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.storage.DBConnector;

import javax.persistence.EntityManager;
import java.sql.Timestamp;

/**
 * Various queries to the server
 */
public class ServerRepository {

    public static Timestamp getServerTimestamp() {
        EntityManager em = DBConnector.INSTANCE.getEntityManager();
        em.getTransaction().begin();
        Timestamp serverTime = (Timestamp) em.createNativeQuery("SELECT current_timestamp").getSingleResult();
        em.getTransaction().commit();

        return serverTime;
    }
}
