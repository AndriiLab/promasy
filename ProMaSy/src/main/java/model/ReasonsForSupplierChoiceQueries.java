package model;

import java.sql.*;

/**
 * CRUD for {@link ReasonForSupplierChoiceModel}
 */
public class ReasonsForSupplierChoiceQueries extends SQLQueries<ReasonForSupplierChoiceModel> {

    ReasonsForSupplierChoiceQueries() {
        super("reason_id", "reasons_for_suppl");
    }

    @Override
    public void create(ReasonForSupplierChoiceModel object) throws SQLException {
        String query = "INSERT INTO reasons_for_suppl(reason_name, created_by, created_date) VALUES (?, ?, ?)";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setString(1, object.getReason());
        prepStmt.setLong(2, object.getCreatedBy());
        prepStmt.setTimestamp(3, object.getCreatedDate());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void retrieve() throws SQLException {
        list.clear();
        String query = "SELECT reason_id, reason_name, created_by, created_date, modified_by,modified_date, active FROM reasons_for_suppl WHERE active = TRUE ORDER BY reason_name ASC";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);

        while (results.next()) {
            long reasonId = results.getLong("reason_id");
            String reasonName = results.getString("reason_name");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");

            ReasonForSupplierChoiceModel model = new ReasonForSupplierChoiceModel(reasonId, createdBy, createdDate, modifiedBy, modifiedDate, active, reasonName);
            list.add(model);
        }
        results.close();
        selectStmt.close();
    }

    @Override
    public void update(ReasonForSupplierChoiceModel object) throws SQLException {
        String query = "UPDATE reasons_for_suppl SET reason_name=?, modified_by=?, modified_date=?, active=? WHERE reason_id=?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setString(1, object.getReason());
        prepStmt.setLong(2, object.getModifiedBy());
        prepStmt.setTimestamp(3, object.getModifiedDate());
        prepStmt.setBoolean(4, object.isActive());
        prepStmt.setLong(5, object.getModelId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void delete(ReasonForSupplierChoiceModel object) throws SQLException {
        String query = "UPDATE reasons_for_suppl SET modified_by=?, modified_date=?, active=? WHERE reason_id=?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getModifiedBy());
        prepStmt.setTimestamp(2, object.getModifiedDate());
        prepStmt.setBoolean(3, object.isActive());
        prepStmt.setLong(4, object.getModelId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }
}
