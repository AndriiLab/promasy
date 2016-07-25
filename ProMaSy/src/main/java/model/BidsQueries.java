package main.java.model;

import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by AL on 25.05.2016.
 */
public class BidsQueries extends SQLQueries<BidModel>{

    public BidsQueries (){
        super("bid_id", "bids");
    }

    @Override
    public void create(BidModel object) throws SQLException {
        String query = "INSERT INTO bids(dep_id, brand_id, cat_num, bid_desc, cpv_code, one_price, amount, am_unit_id, order_id, supplier_id, received, date_received, created_by, created_date, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getDepId());
        prepStmt.setLong(2, object.getProducerId());
        prepStmt.setString(3, object.getCatNum());
        prepStmt.setString(4, object.getBidDesc());
        prepStmt.setString(5, object.getCpvCode());
        prepStmt.setBigDecimal(6, object.getOnePrice());
        prepStmt.setInt(7, object.getAmount());
        prepStmt.setLong(8, object.getAmUnitId());
        prepStmt.setLong(9, object.getFinanceId());
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
        list.clear();
        String query = "SELECT bids.bid_id, bids.dep_id, departments.dep_name AS depName, bids.brand_id, producers.brand_name, bids.cat_num, bids.bid_desc, bids.cpv_code, cpv.cpv_ukr, bids.one_price, bids.amount, bids.am_unit_id, amountunits.am_unit_desc, bids.order_id, finance.order_name, bids.supplier_id, suppliers.supplier_name, bids.received, bids.date_received, bids.created_by, bids.created_date, bids.modified_by, bids.modified_date, bids.active, employees.emp_fname, employees.emp_mname, employees.emp_lname FROM bids INNER JOIN departments ON bids.dep_id = departments.dep_id INNER JOIN producers ON producers.brand_id = bids.brand_id INNER JOIN cpv ON cpv.cpv_code = bids.cpv_code INNER JOIN amountunits ON amountunits.am_unit_id = bids.am_unit_id INNER JOIN employees ON bids.created_by =  employees.emp_id INNER JOIN suppliers ON bids.supplier_id = suppliers.supplier_id INNER JOIN finance ON finance.order_id = bids.order_id WHERE bids.active = TRUE";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        ResultSet results = prepStmt.executeQuery();

        getResults(results);

        results.close();
        prepStmt.close();
    }

    public void retrieve(long departmentId) throws SQLException {
        list.clear();
        String query = "SELECT bids.bid_id, bids.dep_id, departments.dep_name AS depName, bids.brand_id, producers.brand_name, bids.cat_num, bids.bid_desc, bids.cpv_code, cpv.cpv_ukr, bids.one_price, bids.amount, bids.am_unit_id, amountunits.am_unit_desc, bids.order_id, finance.order_name, bids.supplier_id, suppliers.supplier_name, bids.received, bids.date_received, bids.created_by, bids.created_date, bids.modified_by, bids.modified_date, bids.active, employees.emp_fname, employees.emp_mname, employees.emp_lname FROM bids INNER JOIN departments ON bids.dep_id = departments.dep_id INNER JOIN producers ON producers.brand_id = bids.brand_id INNER JOIN cpv ON cpv.cpv_code = bids.cpv_code INNER JOIN amountunits ON amountunits.am_unit_id = bids.am_unit_id INNER JOIN employees ON bids.created_by =  employees.emp_id INNER JOIN suppliers ON bids.supplier_id = suppliers.supplier_id INNER JOIN finance ON finance.order_id = bids.order_id WHERE bids.active = TRUE AND bids.dep_id = ?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, departmentId);
        ResultSet results = prepStmt.executeQuery();

        getResults(results);

        results.close();
        prepStmt.close();
    }

    public void retrieve(long departmentId, long orderId) throws SQLException {
        list.clear();
        String query = "SELECT bids.bid_id, bids.dep_id, departments.dep_name AS depName, bids.brand_id, producers.brand_name, bids.cat_num, bids.bid_desc, bids.cpv_code, cpv.cpv_ukr, bids.one_price, bids.amount, bids.am_unit_id, amountunits.am_unit_desc, bids.order_id, finance.order_name, bids.supplier_id, suppliers.supplier_name, bids.received, bids.date_received, bids.created_by, bids.created_date, bids.modified_by, bids.modified_date, bids.active, employees.emp_fname, employees.emp_mname, employees.emp_lname FROM bids INNER JOIN departments ON bids.dep_id = departments.dep_id INNER JOIN producers ON producers.brand_id = bids.brand_id INNER JOIN cpv ON cpv.cpv_code = bids.cpv_code INNER JOIN amountunits ON amountunits.am_unit_id = bids.am_unit_id INNER JOIN employees ON bids.created_by =  employees.emp_id INNER JOIN suppliers ON bids.supplier_id = suppliers.supplier_id INNER JOIN finance ON finance.order_id = bids.order_id WHERE bids.active = TRUE AND bids.dep_id = ? AND bids.order_id = ?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, departmentId);
        prepStmt.setLong(2, orderId);
        ResultSet results = prepStmt.executeQuery();

        getResults(results);

        results.close();
        prepStmt.close();
    }

    @Override
    public void update(BidModel object) throws SQLException {
        String query = "UPDATE bids SET dep_id=?, brand_id=?, cat_num=?, bid_desc=?, cpv_code=?,  one_price=?, amount=?, am_unit_id=?, order_id=?, supplier_id=?, received=?, date_received=?, modified_by=?,  modified_date=? WHERE bid_id = ? AND active = TRUE";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getDepId());
        prepStmt.setLong(2, object.getProducerId());
        prepStmt.setString(3, object.getCatNum());
        prepStmt.setString(4, object.getBidDesc());
        prepStmt.setString(5, object.getCpvCode());
        prepStmt.setBigDecimal(6, object.getOnePrice());
        prepStmt.setInt(7, object.getAmount());
        prepStmt.setLong(8, object.getAmUnitId());
        prepStmt.setLong(9, object.getFinanceId());
        prepStmt.setLong(10, object.getSupplierId());
        prepStmt.setBoolean(11, object.isReceived());
        prepStmt.setTimestamp(12, object.getDateReceived());
        prepStmt.setLong(13, object.getModifiedBy());
        prepStmt.setTimestamp(14, object.getModifiedDate());
        prepStmt.setLong(15, object.getModelId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void delete(BidModel object) throws SQLException {
        String query = "UPDATE bids SET modified_by=?, modified_date=?, active = FALSE WHERE bid_id = ?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getModifiedBy());
        prepStmt.setTimestamp(2, object.getModifiedDate());
        prepStmt.setLong(3, object.getModelId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    private void getResults(ResultSet results) throws SQLException {
        while (results.next()) {
            long bidId = results.getLong("bid_id");
            long depId = results.getLong("dep_id");
            String depName = results.getString("depName");
            long producerId = results.getLong("brand_id");
            String producerName = results.getString("brand_name");
            String catNum = results.getString("cat_num");
            String bidDesc = results.getString("bid_desc");
            String cpvCode = results.getString("cpv_code");
            String cpvUkr = results.getString("cpv_ukr");
            BigDecimal onePrice = results.getBigDecimal("one_price");
            int amount = results.getInt("amount");
            long amUnitId = results.getLong("am_unit_id");
            String amUnitName = results.getString("am_unit_desc");
            long financeId = results.getLong("order_id");
            String financeName = results.getString("order_name");
            String createdFName = results.getString("emp_fname");
            String createdMName = results.getString("emp_mname");
            String createdLName = results.getString("emp_lname");
            long supplierId = results.getLong("supplier_id");
            String supplierName = results.getString("supplier_name");
            boolean received = results.getBoolean("received");
            Timestamp dateReceived = results.getTimestamp("date_received");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");

            BidModel model = new BidModel(bidId, createdBy, createdDate, modifiedBy, modifiedDate, active, depId, depName, producerId, producerName, catNum, bidDesc, cpvCode, cpvUkr, onePrice, amount, amUnitId, amUnitName, financeId, financeName, supplierId, supplierName, received, dateReceived, createdFName, createdMName, createdLName);
            list.add(model);
        }
    }

    public BigDecimal getSum() throws SQLException {
        String query = "SELECT sum(one_price*amount) FROM bids WHERE active = TRUE";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);
        results.next();
        BigDecimal sum = results.getBigDecimal("sum");
        if (sum == null){
            return BigDecimal.ZERO;
        }
        return sum;
    }

    public BigDecimal getSum(long departmentId) throws SQLException {
        String query = "SELECT sum(one_price*amount) FROM bids WHERE active = TRUE AND dep_id = ?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, departmentId);
        ResultSet results = prepStmt.executeQuery();
        results.next();
        BigDecimal sum = results.getBigDecimal("sum");
        if (sum == null){
            return BigDecimal.ZERO;
        }
        return sum;
    }

    public BigDecimal getSum(long departmentId, long orderId) throws SQLException {
        String query = "SELECT sum(one_price*amount) FROM bids WHERE active = TRUE AND dep_id = ? AND order_id = ?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, departmentId);
        prepStmt.setLong(2, orderId);
        ResultSet results = prepStmt.executeQuery();
        results.next();
        BigDecimal sum = results.getBigDecimal("sum");
        if (sum == null){
            return BigDecimal.ZERO;
        }
        return sum;
    }
}
