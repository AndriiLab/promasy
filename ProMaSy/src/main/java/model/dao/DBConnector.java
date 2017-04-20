package model.dao;

import controller.Logger;
import gui.MainFrame;
import model.models.ConnectionSettingsModel;
import org.hibernate.SessionFactory;
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
    private MainFrame mainFrame;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void connect(ConnectionSettingsModel conSet) throws SQLException {
        //checking connection settings. this statement throws SQLException
        DriverManager.getConnection(conSet.getUrl(), conSet.getUser(), conSet.getPassword()).close();

        //if no exception caught connecting with Hibernate
        Map<String, String> connectionProperties = new HashMap<>();
        connectionProperties.put("javax.persistence.jdbc.url", conSet.getUrl());
        connectionProperties.put("javax.persistence.jdbc.user", conSet.getUser());
        connectionProperties.put("javax.persistence.jdbc.password", conSet.getPassword());
        connectionProperties.put("hibernate.default_schema", conSet.getSchema());
        entityManagerFactory = Persistence.createEntityManagerFactory("postgres-connect", connectionProperties);
        entityManager = entityManagerFactory.createEntityManager();

        //Logging connection stats
        if (mainFrame != null) {
            stat = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
            stat.setStatisticsEnabled(true);
        }
    }

    public void disconnect() {
        if (entityManager != null) {
            entityManager.close();
            entityManager = null;
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory = null;
            if (mainFrame != null) {
                Logger.infoEvent(mainFrame, stat.toString().replace(",", ",\n\t\t\t\t"));
            }
        }
    }

    public void showConnectionStats(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
