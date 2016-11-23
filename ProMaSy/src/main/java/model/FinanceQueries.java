package main.java.model;

import java.math.BigDecimal;
import java.sql.*;

/**
 * CRUD class for finance data
 */
public class FinanceQueries extends SQLQueries<FinanceModel> {

    public FinanceQueries(){
        super("order_id", "finance");
    }

    private static BigDecimal financeLeft(long orderId, BigDecimal totalAmount) throws SQLException {
        String query = "SELECT ? - sum(one_price*amount) AS finance_left FROM bids WHERE active = TRUE AND order_id = ?";
        try (PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query)) {
            prepStmt.setBigDecimal(1, totalAmount);
            prepStmt.setLong(2, orderId);
            return getFinanceLeftResults(prepStmt, totalAmount);
        }
    }

    static BigDecimal financeLeft(long orderId, long depId, BigDecimal totalAmount) throws SQLException {
        String query = "SELECT ? - sum(one_price*amount) AS finance_left FROM bids WHERE active = TRUE AND order_id = ? AND dep_id = ?";
        try (PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query)) {
            prepStmt.setBigDecimal(1, totalAmount);
            prepStmt.setLong(2, orderId);
            prepStmt.setLong(3, depId);
            return getFinanceLeftResults(prepStmt, totalAmount);
        }
    }

    private static BigDecimal getFinanceLeftResults(PreparedStatement prepStmt, BigDecimal totalAmount) throws SQLException {
        try (ResultSet results = prepStmt.executeQuery()) {
            results.next();
            BigDecimal leftAmount = results.getBigDecimal("finance_left");
            if (leftAmount == null) {
                return totalAmount;
            }
            return results.getBigDecimal("finance_left");
        }
    }

    @Override
    public void create(FinanceModel object) throws SQLException {
        String query = "INSERT INTO finance(\n" +
                "            order_number, order_name, order_amount, starts_on, \n" +
                "            due_to, created_by, created_date)\n" +
                "    VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setInt(1, object.getOrderNumber());
        prepStmt.setString(2, object.getOrderName());
        prepStmt.setBigDecimal(3, object.getTotalAmount());
        prepStmt.setDate(4, object.getStartDate());
        prepStmt.setDate(5, object.getEndDate());
        prepStmt.setLong(6, object.getCreatedBy());
        prepStmt.setTimestamp(7, object.getCreatedDate());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void retrieve() throws SQLException {
        list.clear();
        String query = "SELECT order_id, order_number, order_name, order_amount, starts_on, due_to, created_by, created_date, modified_by, modified_date, active FROM finance WHERE active = TRUE ORDER BY order_number ASC";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);
        fillList(results);
        results.close();
        selectStmt.close();
    }

    public void retrieve(long departmentId) throws SQLException {
        list.clear();
        String query = "SELECT finance.order_id, finance.order_number, finance.order_name, finance.order_amount, " +
                "finance.starts_on, finance.due_to, finance.created_by, finance.created_date, finance.modified_by, " +
                "finance.modified_date, finance.order_id, finance.active " +
                "FROM finance " +
                "INNER JOIN finance_dep ON finance.order_id = finance_dep.order_id " +
                "WHERE finance_dep.dep_id = ? AND finance.active = TRUE ORDER BY finance.order_number ASC";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, departmentId);
        ResultSet results = prepStmt.executeQuery();
        fillList(results);
        results.close();
        prepStmt.close();
    }

    private void fillList(ResultSet results) throws SQLException {
        while (results.next()) {
            long orderId = results.getLong("order_id");
            int orderNumber = results.getInt("order_number");
            String orderName = results.getString("order_name");
            BigDecimal totalAmount = results.getBigDecimal("order_amount");
            BigDecimal leftAmount = financeLeft(orderId, totalAmount);
            Date startDate = results.getDate("starts_on");
            Date endDate = results.getDate("due_to");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");

            FinanceModel financeModel = new FinanceModel(createdBy, createdDate, modifiedBy, modifiedDate, active, orderId, orderNumber, orderName, totalAmount, leftAmount, startDate, endDate);
            list.add(financeModel);
        }
    }

    @Override
    public void update(FinanceModel object) throws SQLException {
        String query = "UPDATE finance\n" +
                "   SET order_number=?, order_name=?, order_amount=?, starts_on=?, \n" +
                "       due_to=?, modified_by=?, modified_date=?\n" +
                " WHERE order_id=?\n";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setInt(1, object.getOrderNumber());
        prepStmt.setString(2, object.getOrderName());
        prepStmt.setBigDecimal(3, object.getTotalAmount());
        prepStmt.setDate(4, object.getStartDate());
        prepStmt.setDate(5, object.getEndDate());
        prepStmt.setLong(6, object.getModifiedBy());
        prepStmt.setTimestamp(7, object.getModifiedDate());
        prepStmt.setLong(8, object.getModelId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void delete(FinanceModel object) throws SQLException {
        String query = "UPDATE finance\n" +
                "   SET modified_by=?, modified_date=?\n, active = FALSE" +
                " WHERE order_id=?\n";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getModifiedBy());
        prepStmt.setTimestamp(2, object.getModifiedDate());
        prepStmt.setLong(3, object.getModelId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }
}
