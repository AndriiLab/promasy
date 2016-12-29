package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Queries associated with registration of a new user
 */
public class RegistrationQueries {

    public int getRegistrationNumber() throws SQLException {
        String sql = "SELECT nextval('registrations_left')";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(sql);

        results.next();
        int registrationNumber = results.getInt("nextval");

        results.close();
        selectStmt.close();

        return registrationNumber;
    }

}
