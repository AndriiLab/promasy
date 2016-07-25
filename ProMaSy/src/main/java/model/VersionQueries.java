package main.java.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by laban on 25.07.2016.
 */
public class VersionQueries {

    public String retrive() throws SQLException {
        String query = "SELECT version_allowed FROM version";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);

        if (results.next()){
            return results.getString("version_allowed");
        } else return "0.1";
    }
}
