package model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by AL on 25.05.2016.
 */
public class BidsQueries implements SQLQueries<BidModel>{

    private List<BidModel> bidsList;
    private static final String id = "bid_id";
    private static final String table = "bids";

    public BidsQueries (){
        bidsList = new LinkedList<>();
    }


    @Override
    public void create(BidModel object) throws SQLException {
        String query = "INSERT INTO bids(emp_id, brand_id, cat_num, bid_desc, cpv_code, one_price, amount, am_unit_id, order_id, supplier_id, received, date_received, created_by, created_date, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getEmpId());
        prepStmt.setLong(2, object.getBrandId());
        prepStmt.setString(3, object.getCatNum());
        prepStmt.setString(4, object.getBidDesc());
        prepStmt.setString(5, object.getCpvCode());
        prepStmt.setBigDecimal(6, object.getOnePrice());
        prepStmt.setInt(7, object.getAmount());
        prepStmt.setLong(8, object.getAmUnitId());
        prepStmt.setLong(9, object.getOrderId());
        prepStmt.setLong(10, object.getSupplierId());
        prepStmt.setBoolean(11, object.isReceived());
        prepStmt.setTimestamp(12, object.getDateReceived());
        prepStmt.setLong(13, object.getCreatedBy());
        prepStmt.setTimestamp(14, object.getCreatedDate());
        prepStmt.setBoolean(15, object.isActive());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void retrieve() throws SQLException {
        bidsList.clear();
        String query = "SELECT bid_id, emp_id, brand_id, cat_num, bid_desc, cpv_code, one_price, amount, am_unit_id, order_id, supplier_id, received, date_received, created_by, created_date, modified_by, modified_date FROM bids WHERE active = TRUE";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        ResultSet results = prepStmt.executeQuery();

        getResults(results);

        results.close();
        prepStmt.close();
    }

    public void retrieve(long departmentId, long orderId) throws SQLException {
        bidsList.clear();
        String query = "SELECT bids.bid_id, bids.emp_id, bids.brand_id, bids.cat_num, bids.bid_desc, bids.cpv_code, bids.one_price, bids.amount, bids.am_unit_id, bids.order_id, bids.supplier_id, bids.received, bids.date_received, bids.created_by, bids.created_date, bids.modified_by, bids.modified_date FROM bids INNER JOIN employees ON employees.emp_id = bids.emp_id WHERE bids.active = TRUE AND bids.order_id = ? AND employees.dep_id = ?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, orderId);
        prepStmt.setLong(2, departmentId);
        ResultSet results = prepStmt.executeQuery();

        getResults(results);

        results.close();
        prepStmt.close();
    }

    @Override
    public void update(BidModel object) throws SQLException {
        String query = "UPDATE bids SET emp_id=?, brand_id=?, cat_num=?, bid_desc=?, cpv_code=?,  one_price=?, amount=?, am_unit_id=?, order_id=?, supplier_id=?, received=?, date_received=?, modified_by=?,  modified_date=? WHERE bid_id = ? AND active = TRUE";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getEmpId());
        prepStmt.setLong(2, object.getBrandId());
        prepStmt.setString(3, object.getCatNum());
        prepStmt.setString(4, object.getBidDesc());
        prepStmt.setString(5, object.getCpvCode());
        prepStmt.setBigDecimal(6, object.getOnePrice());
        prepStmt.setInt(7, object.getAmount());
        prepStmt.setLong(8, object.getAmUnitId());
        prepStmt.setLong(9, object.getOrderId());
        prepStmt.setLong(10, object.getSupplierId());
        prepStmt.setBoolean(11, object.isReceived());
        prepStmt.setTimestamp(12, object.getDateReceived());
        prepStmt.setLong(13, object.getModifiedBy());
        prepStmt.setTimestamp(14, object.getModifiedDate());
        prepStmt.setLong(15, object.getBidId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void delete(BidModel object) throws SQLException {
        String query = "UPDATE bids SET modified_by=?, modified_date=?, active = FALSE WHERE bid_id = ?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getModifiedBy());
        prepStmt.setTimestamp(2, object.getModifiedDate());
        prepStmt.setLong(3, object.getBidId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public List<BidModel> getList() {
        return Collections.unmodifiableList(bidsList);
    }

    @Override
    public boolean isChanged(LastChangesModel cacheModel) throws SQLException {
        return checkChanges(cacheModel, table, id);
    }

    @Override
    public LastChangesModel getChangedModel() throws SQLException {
        return getChanged(table, id);
    }

    private void getResults(ResultSet results) throws SQLException {
        while (results.next()) {
            long bidId = results.getLong("bid_id");
            long empId = results.getLong("emp_id");
            long brandId = results.getLong("brand_id");
            String catNum = results.getString("cat_num");
            String bidDesc = results.getString("bid_desc");
            String cpvCode = results.getString("cpv_code");
            BigDecimal onePrice = results.getBigDecimal("one_price");
            int amount = results.getInt("amount");
            long amUnitId = results.getLong("am_unit_id");
            long orderId = results.getLong("order_id");
            long supplierId = results.getLong("supplier_id");
            boolean received = results.getBoolean("received");
            Timestamp dateReceived = results.getTimestamp("date_received");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");

            BidModel model = new BidModel(createdBy, createdDate, modifiedBy, modifiedDate, active, bidId, empId, brandId, catNum, bidDesc, cpvCode, onePrice, amount, amUnitId, orderId, supplierId, received, dateReceived);
            bidsList.add(model);
        }
    }

}
