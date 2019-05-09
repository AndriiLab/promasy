package com.github.andriilab.promasy.persistence.repositories;

import com.github.andriilab.promasy.persistence.storage.DbConnector;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Various queries to the server
 */
public class ServerRepository {

    public static Timestamp getServerTimestamp() {
        EntityManager em = DbConnector.INSTANCE.getEntityManager();
        em.getTransaction().begin();
        Timestamp serverTime = (Timestamp) em.createNativeQuery("SELECT current_timestamp").getSingleResult();
        em.getTransaction().commit();

        return serverTime != null ? serverTime : Timestamp.from(Instant.now());
    }
}
