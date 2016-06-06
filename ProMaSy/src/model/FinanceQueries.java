package model;

import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by laban on 05.05.2016.
 */
public class FinanceQueries extends SQLQueries<FinanceModel> {

    public FinanceQueries(){
        id = "order_id";
        table = "finance";
    }

    @Override
    public void create(FinanceModel object) throws SQLException {
        String query = "INSERT INTO inst_db.finance(\n" +
                "            order_number, order_name, order_amount, starts_on, \n" +
                "            due_to, created_by, created_date)\n" +
                "    VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setString(1, object.getOrderNumber());
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
        String query = "SELECT order_id, order_number, order_name, order_amount, starts_on, due_to, created_by, created_date, modified_by, modified_date, active FROM finance WHERE active = TRUE";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);

        while (results.next()) {
            long orderId = results.getLong("order_id");
            String orderNumber = results.getString("order_number");
            String orderName = results.getString("order_name");
            BigDecimal totalAmount = results.getBigDecimal("order_amount");
//            TODO
//            BigDecimal leftAmount = results.getBigDecimal("");
            Date startDate = results.getDate("starts_on");
            Date endDate = results.getDate("due_to");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");

            FinanceModel financeModel = new FinanceModel(createdBy, createdDate, modifiedBy, modifiedDate, active, orderId, orderNumber, orderName, totalAmount, null, startDate, endDate);
            list.add(financeModel);
        }
        results.close();
        selectStmt.close();
    }


    @Override
    public void update(FinanceModel object) throws SQLException {
        String query = "UPDATE finance\n" +
                "   SET order_number=?, order_name=?, order_amount=?, starts_on=?, \n" +
                "       due_to=?, modified_by=?, modified_date=?\n" +
                " WHERE order_id=?\n";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setString(1, object.getOrderNumber());
        prepStmt.setString(2, object.getOrderName());
        prepStmt.setBigDecimal(3, object.getTotalAmount());
        prepStmt.setDate(4, object.getStartDate());
        prepStmt.setDate(5, object.getEndDate());
        prepStmt.setLong(6, object.getModifiedBy());
        prepStmt.setTimestamp(7, object.getModifiedDate());
        prepStmt.setLong(8, object.getOrderId());
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
        prepStmt.setLong(3, object.getOrderId());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

}
