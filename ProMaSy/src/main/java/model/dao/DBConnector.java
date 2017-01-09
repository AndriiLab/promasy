package model.dao;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public enum DBConnector {
    INSTANCE;

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Statistics stat;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void connect(Properties prefs) throws Exception {
        // TODO: 03.01.2017
//		String url = "jdbc:postgresql://" + conSet.getProperty("host") +
//				":" + conSet.getProperty("port") + "/" + conSet.getProperty("database");
//		Map<String, String> connectionProperties = new HashMap<>();
//		connectionProperties.put("javax.persistence.jdbc.url", url);
//		connectionProperties.put("javax.persistence.jdbc.user", conSet.getProperty("user"));
//		connectionProperties.put("javax.persistence.jdbc.password", conSet.getProperty("password"));
//		connectionProperties.put("hibernate.default_schema", conSet.getProperty("currentSchema"));
//
//		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres-test", connectionProperties);
        entityManagerFactory = Persistence.createEntityManagerFactory("postgres-connect");
        entityManager = entityManagerFactory.createEntityManager();
        System.out.println(">>>>>>>>>>>>>>> Connected on schema '" + entityManagerFactory.getProperties().get("hibernate.default_schema") + "' on '" + entityManagerFactory.getProperties().get("javax.persistence.jdbc.url") +
                "'");
        stat = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        stat.setStatisticsEnabled(true);
    }

    public void disconnect() {
        if (entityManager != null) {
            entityManager.close();
            entityManager = null;
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            System.out.println(">>>>>>>>>>>>>>> Disconnected successfully\n" + stat.toString().replace(",", ",\n"));
        }
        entityManagerFactory = null;
    }
}
