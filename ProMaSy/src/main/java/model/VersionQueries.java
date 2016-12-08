package main.java.model;

import main.java.gui.Labels;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by laban on 25.07.2016.
 */
public class VersionQueries {

    public String retrieve() throws SQLException {
        String query = "SELECT version_allowed FROM version";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);
        String version = "0.1";
        if (results.next()){
            version = results.getString("version_allowed");
        }
        results.close();
        selectStmt.close();

        return version;
    }

    public void set() throws SQLException {
        String query = "UPDATE version SET version_allowed=?";
        PreparedStatement prpStmt = Database.DB.getConnection().prepareStatement(query);
        prpStmt.setString(1, Labels.getProperty("versionNumber"));
        prpStmt.execute();

        prpStmt.close();
    }
}
