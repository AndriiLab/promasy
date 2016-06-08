package model;

import java.sql.*;

/**
 * Created by laban on 26.04.2016.
 */
public class SupplierQueries extends SQLQueries<SupplierModel> {

    public SupplierQueries() {
        super("supplier_id", "suppliers");
    }

    @Override
    public void create(SupplierModel object) throws SQLException {
        String query = "INSERT INTO suppliers (supplier_name, supplier_tel, supplier_comments, created_by, created_date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setString(1, object.getSupplierName());
        prepStmt.setString(2, object.getSupplierTel());
        prepStmt.setString(3, object.getSupplierComments());
        prepStmt.setLong(4, object.getCreatedBy());
        prepStmt.setTimestamp(5, object.getCreatedDate());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void retrieve() throws SQLException {
        list.clear();
        String query = "SELECT supplier_id, supplier_name, supplier_tel, supplier_comments, created_by, created_date, modified_by, modified_date, active FROM suppliers WHERE active = TRUE";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);

        while (results.next()) {
            long suplId = results.getLong("supplier_id");
            String suplName = results.getString("supplier_name");
            String suplTel = results.getString("supplier_tel");
            String suplDesc = results.getString("supplier_comments");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");

            SupplierModel model = new SupplierModel(createdBy, createdDate, modifiedBy, modifiedDate, active,
                    suplId, suplName, suplTel, suplDesc);
            list.add(model);
        }
        results.close();
        selectStmt.close();
    }

    @Override
    public void update(SupplierModel object) throws SQLException {
        String query = "UPDATE suppliers SET supplier_name=?, supplier_tel=?, supplier_comments=?, modified_by=?, modified_date=?, active=? WHERE supplier_id=?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setString(1, object.getSupplierName());
        prepStmt.setString(2, object.getSupplierTel());
        prepStmt.setString(3, object.getSupplierComments());
        prepStmt.setLong(4, object.getModifiedBy());
        prepStmt.setTimestamp(5, object.getModifiedDate());
        prepStmt.setBoolean(6, object.isActive());
        prepStmt.setLong(7, object.getModelId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void delete(SupplierModel object) throws SQLException {
        String query = "UPDATE suppliers SET modified_by=?, modified_date=?, active=? WHERE supplier_id=?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getModifiedBy());
        prepStmt.setTimestamp(2, object.getModifiedDate());
        prepStmt.setBoolean(3, object.isActive());
        prepStmt.setLong(4, object.getModelId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }
}