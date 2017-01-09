package model.dao;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public enum DBConnector {
    INSTANCE;

    private EntityManagerFactory entityManagerFactory;
    private Statistics stat;

    public EntityManagerFactory getEntityManagerFactory() {
        System.out.println(">>>>>>>>>>>>>>> Transaction created on schema '" + entityManagerFactory.getProperties().get("hibernate.default_schema") + "' on '" + entityManagerFactory.getProperties().get("javax.persistence.jdbc.url") +
                "'");
        return entityManagerFactory;
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
        stat = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        stat.setStatisticsEnabled(true);
    }

    public void disconnect() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            System.out.println(">>>>>>>>>>>>>>> Disconnected successfully\n" + stat.toString().replace(",", ",\n"));
        }
        entityManagerFactory = null;
    }
}
