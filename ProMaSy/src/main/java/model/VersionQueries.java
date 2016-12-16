package main.java.model;

import main.java.gui.Labels;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DB access model for handling program version
 */
public class VersionQueries {

    public String retrieve() throws SQLException {
        String query = "SELECT version_allowed FROM version";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);
        String version;
        //trying to get min version
        if (results.next()){
            version = results.getString("version_allowed");
        } else {
            //if entry doesn't exist (0 rows returned) creating new entry equal to software version
            setVersion("INSERT INTO version (version_allowed) VALUES (?)");
            version = Labels.getProperty("versionNumber");
        }
        results.close();
        selectStmt.close();

        return version;
    }

    private void setVersion(String query) throws SQLException {
        PreparedStatement prpStmt = Database.DB.getConnection().prepareStatement(query);
        prpStmt.setString(1, Labels.getProperty("versionNumber"));
        prpStmt.execute();
        prpStmt.close();
    }

    public void updateVersion() throws SQLException {
        setVersion("UPDATE version SET version_allowed=?");
    }
}
