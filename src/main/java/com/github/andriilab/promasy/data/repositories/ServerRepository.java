package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.storage.DbConnector;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Various queries to the server
 */
public class ServerRepository {

    public static Timestamp getServerTimestamp() {
        EntityManager em = DbConnector.INSTANCE.getEntityManager();
        Timestamp serverTime = Repository.inTransaction(em, () -> (Timestamp) em.createNativeQuery("SELECT current_timestamp").getSingleResult());

        return serverTime != null ? serverTime : Timestamp.from(Instant.now());
    }
}
