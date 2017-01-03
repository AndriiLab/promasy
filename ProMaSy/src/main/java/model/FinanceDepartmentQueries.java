package model;

import java.math.BigDecimal;
import java.sql.*;

/**
 * CRUD for {@link FinanceDepartmentModel}
 */
public class FinanceDepartmentQueries extends SQLQueries<FinanceDepartmentModel> {

    public FinanceDepartmentQueries(){
        super("order_id", "finance_dep");
    }

    @Override
    public void create(FinanceDepartmentModel object) throws SQLException {
        String query = "INSERT INTO finance_dep(order_id, dep_id, emp_id, order_amount, created_by, created_date, active)\n" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, object.getModelId());
        prepStmt.setLong(2, object.getDepartment());
        prepStmt.setLong(3, object.getResponsibleEmployee());
        prepStmt.setBigDecimal(4, object.getTotalAmount());
        prepStmt.setLong(5, object.getCreatedBy());
        prepStmt.setTimestamp(6, object.getCreatedDate());
        prepStmt.setBoolean(7, object.isActive());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void retrieve() throws SQLException {
        list.clear();
        String query = "SELECT finance_dep.order_id, finance.order_name, finance.order_number, " +
                "finance_dep.dep_id, departments.dep_name, " +
                    "finance_dep.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, " +
                    "finance_dep.order_amount, finance_dep.created_by, " +
                    "finance_dep.created_date, finance_dep.modified_by, " +
                    "finance_dep.modified_date, finance_dep.active " +
                "FROM finance_dep " +
                "INNER JOIN finance ON finance.order_id = finance_dep.order_id " +
                "INNER JOIN departments ON finance_dep.dep_id = departments.dep_id " +
                "INNER JOIN employees ON finance_dep.emp_id = employees.emp_id WHERE finance_dep.active = TRUE " +
                "ORDER BY finance_dep.order_amount DESC";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);

        getResults (results);

        results.close();
        selectStmt.close();
    }

    public void retrieveByOrderID(long orderID) throws SQLException {
        list.clear();
        String query = "SELECT finance_dep.order_id, finance.order_name, finance.order_number, " +
                "finance_dep.dep_id, departments.dep_name, " +
                "finance_dep.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, " +
                "finance_dep.order_amount, finance_dep.created_by, " +
                "finance_dep.created_date, finance_dep.modified_by, " +
                "finance_dep.modified_date, finance_dep.active " +
                "FROM finance_dep " +
                "INNER JOIN finance ON finance.order_id = finance_dep.order_id " +
                "INNER JOIN departments ON finance_dep.dep_id = departments.dep_id " +
                "INNER JOIN employees ON finance_dep.emp_id = employees.emp_id WHERE finance_dep.order_id = ? " +
                "AND finance_dep.active = TRUE ORDER BY finance_dep.order_amount DESC";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, orderID);
        ResultSet results = prepStmt.executeQuery();

        getResults (results);

        results.close();
        prepStmt.close();
    }

    public void retrieveByDepartmentID(long departmentId) throws SQLException {
        list.clear();
        String query = "SELECT finance_dep.order_id, finance.order_name, finance.order_number, " +
                "finance_dep.dep_id, departments.dep_name, " +
                "finance_dep.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, " +
                "finance_dep.order_amount, finance_dep.created_by, " +
                "finance_dep.created_date, finance_dep.modified_by, " +
                "finance_dep.modified_date, finance_dep.active " +
                "FROM finance_dep " +
                "INNER JOIN finance ON finance.order_id = finance_dep.order_id " +
                "INNER JOIN departments ON finance_dep.dep_id = departments.dep_id " +
                "INNER JOIN employees ON finance_dep.emp_id = employees.emp_id WHERE finance_dep.dep_id = ? " +
                "AND finance_dep.active = TRUE ORDER BY finance_dep.order_amount DESC";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, departmentId);
        ResultSet results = prepStmt.executeQuery();

        getResults (results);

        results.close();
        prepStmt.close();
    }

    @Override
    public void update(FinanceDepartmentModel object) throws SQLException {
        String query = "UPDATE finance_dep SET emp_id=?, order_amount=?, modified_by=?, modified_date=?, active=?" +
                " WHERE order_id=? AND dep_id=?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);

        prepStmt.setLong(1, object.getResponsibleEmployee());
        prepStmt.setBigDecimal(2, object.getTotalAmount());
        prepStmt.setLong(3, object.getModifiedBy());
        prepStmt.setTimestamp(4, object.getModifiedDate());
        prepStmt.setBoolean(5, object.isActive());
        prepStmt.setLong(6, object.getModelId());
        prepStmt.setLong(7, object.getDepartment());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    @Override
    public void delete(FinanceDepartmentModel object) throws SQLException {
        String query = "UPDATE finance_dep SET modified_by=?, modified_date=?, active=? WHERE order_id=? AND dep_id=?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);

        prepStmt.setLong(1, object.getModifiedBy());
        prepStmt.setTimestamp(2, object.getModifiedDate());
        prepStmt.setBoolean(3, object.isActive());
        prepStmt.setLong(4, object.getModelId());
        prepStmt.setLong(5, object.getDepartment());
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    private void getResults(ResultSet results) throws SQLException {
        while (results.next()) {
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");
            long orderId = results.getLong("order_id");
            String orderName =  results.getString("order_name");
            int orderNumber = results.getInt("order_number");
            long depId = results.getLong("dep_id");
            String depName = results.getString("dep_name");
            long empId = results.getLong("emp_id");
            String empFName = results.getString("emp_fname");
            String empMName = results.getString("emp_mname");
            String empLName = results.getString("emp_lname");
            String empName = getName(empFName, empMName, empLName);
            BigDecimal totalAmount = results.getBigDecimal("order_amount");
            BigDecimal leftAmount = FinanceQueries.financeLeft(orderId, depId);

            FinanceDepartmentModel financeDepartmentModel = new FinanceDepartmentModel(createdBy, createdDate, modifiedBy, modifiedDate, active, orderId, orderName, orderNumber, depId, depName, empId, empName, totalAmount, leftAmount);
            list.add(financeDepartmentModel);
        }
    }

    private String getName(String empFName, String empMName, String empLName){
        if(empFName != null && empLName != null && empMName != null){
            return empLName + " " + empFName.substring(0, 1) + "."
                    + empMName.substring(0, 1)+".";
        }
        else if (empFName != null && empLName != null){
            return empLName + " " + empFName.substring(0, 1) + ".";
        }
        else if (empLName != null){
            return empLName;
        }
        return null;
    }
}
