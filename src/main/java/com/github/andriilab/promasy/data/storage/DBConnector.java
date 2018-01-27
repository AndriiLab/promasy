package com.github.andriilab.promasy.data.storage;

import com.github.andriilab.promasy.data.controller.Logger;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.Utils;
import com.github.andriilab.promasy.presentation.commons.Labels;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DBConnector {
    INSTANCE;

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Statistics stat;
    private MainFrame mainFrame;
    private ConnectionSettings connectionSettings;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    // sets connection settings to Properties object
    public void loadConnectionSettings(boolean tableUpdateMode) {
        ConnectionSettings model = null;
        try {
            model = Utils.loadConnectionSettings();
            Logger.infoEvent(null, "Connection Settings loaded from settings file");
        } catch (FileNotFoundException e) {
            Logger.infoEvent(null, "Connection Settings used from defaults");
            model = new ConnectionSettings(Labels.getProperty("connectionSettings.server"),
                    Labels.getProperty("connectionSettings.database"), Labels.getProperty("connectionSettings.schema"),
                    Labels.getInt("connectionSettings.port"), Labels.getProperty("connectionSettings.user"),
                    Labels.getProperty("connectionSettings.password"));
        } catch (IOException | ClassNotFoundException e) {
            Logger.errorEvent(mainFrame, e);
        }

        if (tableUpdateMode) {
            model.getProperties().put("hibernate.hbm2ddl.auto", "update");
        }

        this.connectionSettings = model;
    }

    public ConnectionSettings getConnectionSettings() {
        return connectionSettings;
    }

    public void connect() throws SQLException {
        if (connectionSettings == null)
            loadConnectionSettings(false);
        //checking connection settings. this statement throws SQLException
        DriverManager.getConnection(connectionSettings.getUrl(), connectionSettings.getUser(),
                connectionSettings.getPassword()).close();

        //if no exception caught connecting with Hibernate
        entityManagerFactory = Persistence.createEntityManagerFactory("postgres-connect",
                connectionSettings.getProperties());
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
            if (mainFrame != null && stat != null) {
                Logger.infoEvent(mainFrame, stat.toString().replace(",", ",\n\t\t\t\t"));
            }
        }
    }

    public void showConnectionStats(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
