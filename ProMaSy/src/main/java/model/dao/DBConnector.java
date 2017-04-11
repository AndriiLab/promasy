package model.dao;

import model.models.ConnectionSettingsModel;
import org.hibernate.stat.Statistics;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public enum DBConnector {
    INSTANCE;

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Statistics stat;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void connect(ConnectionSettingsModel conSet) throws SQLException {
        DriverManager.getConnection(conSet.getUrl(), conSet.getUser(), conSet.getPassword()).close();

        Map<String, String> connectionProperties = new HashMap<>();
        connectionProperties.put("javax.persistence.jdbc.url", conSet.getUrl());
        connectionProperties.put("javax.persistence.jdbc.user", conSet.getUser());
        connectionProperties.put("javax.persistence.jdbc.password", conSet.getPassword());
        connectionProperties.put("hibernate.default_schema", conSet.getSchema());
        entityManagerFactory = Persistence.createEntityManagerFactory("postgres-connect", connectionProperties);
        entityManager = entityManagerFactory.createEntityManager();

        //Logging connection stats
//        stat = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
//        stat.setStatisticsEnabled(true);
    }

    public void disconnect() {
        if (entityManager != null) {
            entityManager.close();
            entityManager = null;
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory = null;
//            Logger.infoEvent(null, stat.toString().replace(",", ",\n"));
        }
    }
}
