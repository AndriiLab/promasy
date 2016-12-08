package main.java.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * CRUD for bid status
 */
public class StatusQueries extends SQLQueries<StatusModel> {

    public StatusQueries() {
        super("id", "bid_status");
    }

    @Override
    public void create(StatusModel object) throws SQLException {
        Database.DB.getConnection().setAutoCommit(false);

        String query1 = "UPDATE bids SET status_id=?, modified_by=?, modified_date=? WHERE bid_id=?";
        PreparedStatement prepStmt1 = Database.DB.getConnection().prepareStatement(query1);
        prepStmt1.setInt(1, object.getStatusId());
        prepStmt1.setLong(2, object.getCreatedBy());
        prepStmt1.setTimestamp(3, object.getCreatedDate());
        prepStmt1.setLong(4, object.getBidId());
        prepStmt1.executeUpdate();

        String query2 = "INSERT INTO bid_status(bid_id, status_id, created_by, created_date) VALUES (?, ?, ?, ?)";
        PreparedStatement prepStmt2 = Database.DB.getConnection().prepareStatement(query2);
        prepStmt2.setLong(1, object.getBidId());
        prepStmt2.setInt(2, object.getStatusId());
        prepStmt2.setLong(3, object.getCreatedBy());
        prepStmt2.setTimestamp(4, object.getCreatedDate());
        prepStmt2.executeUpdate();

        Database.DB.getConnection().commit();
        prepStmt1.close();
        prepStmt2.close();

        Database.DB.getConnection().setAutoCommit(true);
    }

    @Override
    void retrieve() throws SQLException {

    }

    public void retrieve(long bidId) throws SQLException {
        list.clear();
        String query = "SELECT bid_status.id, bid_status.bid_id, bid_status.status_id, bid_status.created_by, " +
                "bid_status.created_date, bid_status.active, " +
                "statuses.status_desc, employees.emp_fname, employees.emp_lname, employees.emp_mname " +
                "FROM bid_status " +
                "INNER JOIN statuses ON bid_status.status_id = statuses.status_id " +
                "INNER JOIN employees ON bid_status.created_by = employees.emp_id " +
                "WHERE bid_status.active=TRUE AND bid_id = ? " +
                "ORDER BY created_date DESC";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, bidId);
        ResultSet results = prepStmt.executeQuery();

        while (results.next()) {
            long modelId = results.getLong("id");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            bidId = results.getLong("bid_id");
            int statusId = results.getInt("status_id");
            boolean active = results.getBoolean("active");
            String statusDesc = results.getString("status_desc");
            String createdFName = results.getString("emp_fname");
            String createdMName = results.getString("emp_mname");
            String createdLName = results.getString("emp_lname");

            StatusModel model = new StatusModel(modelId, createdBy, createdDate, 0, null, active, bidId, statusId, statusDesc, createdFName, createdMName, createdLName);
            list.add(model);
        }

        results.close();
        prepStmt.close();
    }

    @Override
    void update(StatusModel object) throws SQLException {

    }

    @Override
    void delete(StatusModel object) throws SQLException {

    }
}
