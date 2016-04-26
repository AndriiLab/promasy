package model;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by laban on 26.04.2016.
 */
public class ProducerQueries implements SQLQueries<ProducerModel> {

    private List<ProducerModel> prodList;
    private final String id = "brand_id";
    private final String table = "producers";

    public ProducerQueries() {
        prodList = new LinkedList<>();
    }

    @Override
    public void create(ProducerModel object) throws SQLException {
        String query = "INSERT INTO producers(brand_name, created_by, created_date) VALUES (?, ?, ?)";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setString(1, object.getBrandName());
        prepStmt.setLong(2, object.getCreatedBy());
        prepStmt.setTimestamp(3, object.getCreatedDate());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void retrieve() throws SQLException {
        prodList.clear();
        String query = "SELECT brand_id, brand_name, created_by, created_date, modified_by,modified_date, active FROM producers WHERE active = TRUE";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);

        while (results.next()) {
            long prodId = results.getLong("brand_id");
            String prodDesc = results.getString("brand_name");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");

           ProducerModel model = new ProducerModel(createdBy, createdDate, modifiedBy, modifiedDate, active,prodId, prodDesc);
            prodList.add(model);
        }
        results.close();
        selectStmt.close();
    }

    @Override
    public void update(ProducerModel object) throws SQLException {
        String query = "UPDATE producers SET brand_name=?, modified_by=?, modified_date=?, active=? WHERE brand_id=?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setString(1, object.getBrandName());
        prepStmt.setLong(2, object.getModifiedBy());
        prepStmt.setTimestamp(3, object.getModifiedDate());
        prepStmt.setBoolean(4, object.isActive());
        prepStmt.setLong(5, object.getBrandId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void delete(ProducerModel object) throws SQLException {
        String query = "UPDATE producers SET modified_by=?, modified_date=?, active=? WHERE brand_id=?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getModifiedBy());
        prepStmt.setTimestamp(2, object.getModifiedDate());
        prepStmt.setBoolean(3, object.isActive());
        prepStmt.setLong(4, object.getBrandId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public List<ProducerModel> getList()  {
        return Collections.unmodifiableList(prodList);
    }

    @Override
    public boolean isChanged(LastChangesModel cacheModel) throws SQLException {
        return checkChanges(cacheModel, table, id);
    }

    @Override
    public LastChangesModel getChangedModel() throws SQLException {
        return getChanged(table, id);
    }
}
