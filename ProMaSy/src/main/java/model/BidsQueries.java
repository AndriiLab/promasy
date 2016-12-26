package main.java.model;

import java.math.BigDecimal;
import java.sql.*;

/**
 * CRUD for bid data
 */
public class BidsQueries extends SQLQueries<BidModel>{

    public BidsQueries (){
        super("bid_id", "bids");
    }

    @Override
    public void create(BidModel object) throws SQLException {
        Database.DB.getConnection().setAutoCommit(false); // enabling transactions

        //generating id for new bid and setting to Bid object
        String generateBidId = "SELECT id_gen()";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(generateBidId);
        ResultSet results = prepStmt.executeQuery();
        results.next();
        object.setModelId(results.getLong("id_gen"));

        String query1 = "INSERT INTO bids(bid_id, dep_id, brand_id, cat_num, bid_desc, cpv_code, one_price, amount, am_unit_id, order_id, supplier_id, created_by, created_date, active, status_id, reason_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStmt1 = Database.DB.getConnection().prepareStatement(query1);
        prepStmt1.setLong(1, object.getModelId());
        prepStmt1.setLong(2, object.getDepId());
        long producerId = object.getProducerId();
        if (producerId == 0L) {
            prepStmt1.setNull(3, Types.BIGINT);
        } else {
            prepStmt1.setLong(3, producerId);
        }
        prepStmt1.setString(4, object.getCatNum());
        prepStmt1.setString(5, object.getBidDesc());
        prepStmt1.setString(6, object.getCpvCode());
        prepStmt1.setBigDecimal(7, object.getOnePrice());
        prepStmt1.setInt(8, object.getAmount());
        prepStmt1.setLong(9, object.getAmUnitId());
        prepStmt1.setLong(10, object.getFinanceId());
        long supplierId = object.getSupplierId();
        if (supplierId == 0L) {
            prepStmt1.setNull(11, Types.BIGINT);
        } else {
            prepStmt1.setLong(11, supplierId);
        }
        prepStmt1.setLong(12, object.getCreatedBy());
        prepStmt1.setTimestamp(13, object.getCreatedDate());
        prepStmt1.setBoolean(14, object.isActive());
        prepStmt1.setInt(15, object.getStatusId());
        long reasonId = object.getReasonId();
        if (reasonId == 0L) {
            prepStmt1.setNull(16, Types.BIGINT);
        } else {
            prepStmt1.setLong(16, reasonId);
        }
        prepStmt1.executeUpdate();

        String query2 = "INSERT INTO bid_status(bid_id, status_id, created_by, created_date) VALUES (?, ?, ?, ?)";
        PreparedStatement prepStmt2 = Database.DB.getConnection().prepareStatement(query2);
        prepStmt2.setLong(1, object.getModelId());
        prepStmt2.setInt(2, object.getStatusId());
        prepStmt2.setLong(3, object.getCreatedBy());
        prepStmt2.setTimestamp(4, object.getCreatedDate());
        prepStmt2.executeUpdate();

        Database.DB.getConnection().commit();// if all operations was successful committing changes
        results.close();
        prepStmt1.close();
        prepStmt2.close();

        Database.DB.getConnection().setAutoCommit(true); // disabling transactions
    }

    @Override
    public void retrieve() throws SQLException {
        list.clear();
        String query = "SELECT bids.bid_id, bids.dep_id, departments.dep_name AS depName, " +
                "bids.brand_id, producers.brand_name, bids.cat_num, bids.bid_desc, bids.cpv_code, " +
                "cpv.cpv_ukr, bids.one_price, bids.amount, bids.am_unit_id, amountunits.am_unit_desc, " +
                "bids.order_id, finance.order_name, bids.supplier_id, suppliers.supplier_name, " +
                "bids.created_by, bids.created_date, bids.modified_by, bids.modified_date, bids.active, " +
                "creator.emp_fname AS c_fname, creator.emp_mname AS c_mname, creator.emp_lname AS c_lname, " +
                "modifier.emp_fname AS m_fname, modifier.emp_mname AS m_mname, modifier.emp_lname AS m_lname, " +
                "bids.status_id, statuses.status_desc, bids.reason_id, reasons_for_suppl.reason_name, " +
                "CAST(CASE " +
                "WHEN bids.modified_date=NULL THEN bids.modified_date " +
                "ELSE bids.created_date " +
                "END AS TIMESTAMP) AS mod_date " +
                "FROM bids " +
                "INNER JOIN departments ON bids.dep_id = departments.dep_id " +
                "LEFT JOIN producers ON producers.brand_id = bids.brand_id " +
                "INNER JOIN cpv ON cpv.cpv_code = bids.cpv_code " +
                "INNER JOIN amountunits ON amountunits.am_unit_id = bids.am_unit_id " +
                "LEFT JOIN suppliers ON bids.supplier_id = suppliers.supplier_id " +
                "INNER JOIN finance ON finance.order_id = bids.order_id " +
                "INNER JOIN statuses ON bids.status_id = statuses.status_id " +
                "INNER JOIN employees AS creator ON bids.created_by = creator.emp_id " +
                "LEFT JOIN employees AS modifier ON bids.modified_by = modifier.emp_id " +
                "LEFT JOIN reasons_for_suppl ON bids.reason_id = reasons_for_suppl.reason_id " +
                "WHERE bids.active = TRUE ORDER BY mod_date DESC";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        ResultSet results = prepStmt.executeQuery();

        getResults(results);

        results.close();
        prepStmt.close();
    }

    public void retrieve(long departmentId) throws SQLException {
        list.clear();
        String query = "SELECT bids.bid_id, bids.dep_id, departments.dep_name AS depName, " +
                "bids.brand_id, producers.brand_name, bids.cat_num, bids.bid_desc, bids.cpv_code, " +
                "cpv.cpv_ukr, bids.one_price, bids.amount, bids.am_unit_id, amountunits.am_unit_desc, " +
                "bids.order_id, finance.order_name, bids.supplier_id, suppliers.supplier_name, " +
                "bids.created_by, bids.created_date, bids.modified_by, bids.modified_date, bids.active, " +
                "creator.emp_fname AS c_fname, creator.emp_mname AS c_mname, creator.emp_lname AS c_lname, " +
                "modifier.emp_fname AS m_fname, modifier.emp_mname AS m_mname, modifier.emp_lname AS m_lname, " +
                "bids.status_id, statuses.status_desc, bids.reason_id, reasons_for_suppl.reason_name, " +
                "CAST(CASE " +
                "WHEN bids.modified_date=NULL THEN bids.modified_date " +
                "ELSE bids.created_date " +
                "END AS TIMESTAMP) AS mod_date " +
                "FROM bids " +
                "INNER JOIN departments ON bids.dep_id = departments.dep_id " +
                "LEFT JOIN producers ON producers.brand_id = bids.brand_id " +
                "INNER JOIN cpv ON cpv.cpv_code = bids.cpv_code " +
                "INNER JOIN amountunits ON amountunits.am_unit_id = bids.am_unit_id " +
                "LEFT JOIN suppliers ON bids.supplier_id = suppliers.supplier_id " +
                "INNER JOIN finance ON finance.order_id = bids.order_id " +
                "INNER JOIN statuses ON bids.status_id = statuses.status_id " +
                "INNER JOIN employees AS creator ON bids.created_by = creator.emp_id " +
                "LEFT JOIN employees AS modifier ON bids.modified_by = modifier.emp_id " +
                "LEFT JOIN reasons_for_suppl ON bids.reason_id = reasons_for_suppl.reason_id " +
                "WHERE bids.active = TRUE AND bids.dep_id = ? " +
                "ORDER BY mod_date DESC";

        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, departmentId);
        ResultSet results = prepStmt.executeQuery();

        getResults(results);

        results.close();
        prepStmt.close();
    }

    public void retrieve(long departmentId, long orderId) throws SQLException {
        list.clear();
        String query = "SELECT bids.bid_id, bids.dep_id, departments.dep_name AS depName, " +
                "bids.brand_id, producers.brand_name, bids.cat_num, bids.bid_desc, bids.cpv_code, " +
                "cpv.cpv_ukr, bids.one_price, bids.amount, bids.am_unit_id, amountunits.am_unit_desc, " +
                "bids.order_id, finance.order_name, bids.supplier_id, suppliers.supplier_name, " +
                "bids.created_by, bids.created_date, bids.modified_by, bids.modified_date, bids.active, " +
                "creator.emp_fname AS c_fname, creator.emp_mname AS c_mname, creator.emp_lname AS c_lname, " +
                "modifier.emp_fname AS m_fname, modifier.emp_mname AS m_mname, modifier.emp_lname AS m_lname, " +
                "bids.status_id, statuses.status_desc, bids.reason_id, reasons_for_suppl.reason_name, " +
                "CAST(CASE " +
                "WHEN bids.modified_date=NULL THEN bids.modified_date " +
                "ELSE bids.created_date " +
                "END AS TIMESTAMP) AS mod_date " +
                "FROM bids " +
                "INNER JOIN departments ON bids.dep_id = departments.dep_id " +
                "LEFT JOIN producers ON producers.brand_id = bids.brand_id " +
                "INNER JOIN cpv ON cpv.cpv_code = bids.cpv_code " +
                "INNER JOIN amountunits ON amountunits.am_unit_id = bids.am_unit_id " +
                "LEFT JOIN suppliers ON bids.supplier_id = suppliers.supplier_id " +
                "INNER JOIN finance ON finance.order_id = bids.order_id " +
                "INNER JOIN statuses ON bids.status_id = statuses.status_id " +
                "INNER JOIN employees AS creator ON bids.created_by = creator.emp_id " +
                "LEFT JOIN employees AS modifier ON bids.modified_by = modifier.emp_id " +
                "LEFT JOIN reasons_for_suppl ON bids.reason_id = reasons_for_suppl.reason_id " +
                "WHERE bids.active = TRUE AND bids.dep_id = ? AND bids.order_id = ?" +
                "ORDER BY mod_date DESC";

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
        Database.DB.getConnection().setAutoCommit(false); // enabling transactions

        String query1 = "UPDATE bids SET dep_id=?, brand_id=?, cat_num=?, bid_desc=?, cpv_code=?,  one_price=?, amount=?, am_unit_id=?, order_id=?, supplier_id=?, modified_by=?, modified_date=?, status_id=?, reason_id=? WHERE bid_id = ? AND active = TRUE";
        PreparedStatement prepStmt1 = Database.DB.getConnection().prepareStatement(query1);
        prepStmt1.setLong(1, object.getDepId());
        long producerId = object.getProducerId();
        if (producerId == 0L) {
            prepStmt1.setNull(2, Types.BIGINT);
        } else {
            prepStmt1.setLong(2, producerId);
        }
        prepStmt1.setString(3, object.getCatNum());
        prepStmt1.setString(4, object.getBidDesc());
        prepStmt1.setString(5, object.getCpvCode());
        prepStmt1.setBigDecimal(6, object.getOnePrice());
        prepStmt1.setInt(7, object.getAmount());
        prepStmt1.setLong(8, object.getAmUnitId());
        prepStmt1.setLong(9, object.getFinanceId());
        long supplierId = object.getSupplierId();
        if (supplierId == 0L) {
            prepStmt1.setNull(10, Types.BIGINT);
        } else {
            prepStmt1.setLong(10, supplierId);
        }
        prepStmt1.setLong(11, object.getModifiedBy());
        prepStmt1.setTimestamp(12, object.getModifiedDate());
        prepStmt1.setInt(13, object.getStatusId());
        long reasonId = object.getReasonId();
        if (reasonId == 0L) {
            prepStmt1.setNull(14, Types.BIGINT);
        } else {
            prepStmt1.setLong(14, reasonId);
        }
        prepStmt1.setLong(15, object.getModelId());
        prepStmt1.executeUpdate();

        String query2 = "INSERT INTO bid_status(bid_id, status_id, created_by, created_date) VALUES (?, ?, ?, ?)";
        PreparedStatement prepStmt2 = Database.DB.getConnection().prepareStatement(query2);
        prepStmt2.setLong(1, object.getModelId());
        prepStmt2.setInt(2, object.getStatusId());
        prepStmt2.setLong(3, object.getModifiedBy());
        prepStmt2.setTimestamp(4, object.getModifiedDate());
        prepStmt2.executeUpdate();

        Database.DB.getConnection().commit();// if all operations was successful committing changes
        prepStmt1.close();
        prepStmt2.close();

        Database.DB.getConnection().setAutoCommit(true); // disabling transactions
    }

    @Override
    public void delete(BidModel object) throws SQLException {
        Database.DB.getConnection().setAutoCommit(false); // enabling transactions

        String query1 = "UPDATE bids SET modified_by=?, modified_date=?, active = FALSE WHERE bid_id = ?";
        PreparedStatement prepStmt1 = Database.DB.getConnection().prepareStatement(query1);
        prepStmt1.setLong(1, object.getModifiedBy());
        prepStmt1.setTimestamp(2, object.getModifiedDate());
        prepStmt1.setLong(3, object.getModelId());
        prepStmt1.executeUpdate();

        String query2 = "UPDATE bid_status SET modified_by=?, modified_date=?, active = FALSE WHERE bid_id = ?";
        PreparedStatement prepStmt2 = Database.DB.getConnection().prepareStatement(query2);
        prepStmt2.setLong(1, object.getModifiedBy());
        prepStmt2.setTimestamp(2, object.getModifiedDate());
        prepStmt2.setLong(3, object.getModelId());
        prepStmt2.executeUpdate();

        Database.DB.getConnection().commit();// if all operations was successful committing changes
        prepStmt1.close();
        prepStmt2.close();
        Database.DB.getConnection().setAutoCommit(true); // disabling transactions
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
            String createdFName = results.getString("c_fname");
            String createdMName = results.getString("c_mname");
            String createdLName = results.getString("c_lname");
            String editedFName = results.getString("m_fname");
            String editedMName = results.getString("m_mname");
            String editedLName = results.getString("m_lname");
            long supplierId = results.getLong("supplier_id");
            String supplierName = results.getString("supplier_name");
            int statusId = results.getInt("status_id");
            String statusDesc = results.getString("status_desc");
            long createdBy = results.getLong("created_by");
            long modifiedBy = results.getLong("modified_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");
            long reasonId = results.getLong("reason_id");
            String reasonName = results.getString("reason_name");

            BidModel model = new BidModel(bidId, createdBy, createdDate, modifiedBy, modifiedDate, active, depId, depName, producerId, producerName, catNum, bidDesc, cpvCode, cpvUkr, onePrice, amount, amUnitId, amUnitName, financeId, financeName, supplierId, supplierName, statusId, statusDesc, createdFName, createdMName, createdLName, editedFName, editedMName, editedLName, reasonId, reasonName);
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
