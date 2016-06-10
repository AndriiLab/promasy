package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class EmployeeQueries extends SQLQueries<EmployeeModel>{

	EmployeeQueries() {
		super("emp_id", "employees");
	}
	
	public void create(EmployeeModel object) throws SQLException {
		String query = "INSERT INTO employees (emp_fname, emp_mname, emp_lname, dep_id, subdep_id, roles_id, login, password, created_by, created_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getEmpFName());
		prepStmt.setString(2, object.getEmpMName());
		prepStmt.setString(3, object.getEmpLName());
		prepStmt.setLong(4, object.getDepId());
		prepStmt.setLong(5, object.getSubdepId());
		prepStmt.setLong(6, object.getRoleId());
		prepStmt.setString(7, object.getLogin());
		prepStmt.setString(8, object.getPassword());
		prepStmt.setLong(9, object.getCreatedBy());
		prepStmt.setTimestamp(10, object.getCreatedDate());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void retrieve() throws SQLException {
		list.clear();
		String query = "SELECT employees.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, "+ 
				"departments.inst_id, institute.inst_name, "+
				"employees.dep_id, departments.dep_name, "+
				"employees.subdep_id, subdepartments.subdep_name, "+
				"employees.roles_id, roles.roles_name, "+
				"employees.login, employees.password, "+
				"employees.created_by, employees.created_date, "+
				"employees.modified_by, employees.modified_date, "+
				"employees.active "+
			"FROM employees "+
			"INNER JOIN departments ON employees.dep_id = departments.dep_id "+
			"LEFT OUTER JOIN subdepartments ON employees.subdep_id = subdepartments.subdep_id "+
			"INNER JOIN roles ON employees.roles_id = roles.roles_id "+
			"INNER JOIN institute ON departments.inst_id = institute.inst_id";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		ResultSet results = prepStmt.executeQuery();

        loadResultsToList(results);

		results.close();
		prepStmt.close();
	}

    public void retrieve(long departmentId) throws SQLException {
        list.clear();
        String query = "SELECT employees.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, "+
                "departments.inst_id, institute.inst_name, "+
                "employees.dep_id, departments.dep_name, "+
                "employees.subdep_id, subdepartments.subdep_name, "+
                "employees.roles_id, roles.roles_name, "+
                "employees.login, employees.password, "+
                "employees.created_by, employees.created_date, "+
                "employees.modified_by, employees.modified_date, "+
                "employees.active "+
                "FROM employees "+
                "INNER JOIN departments ON employees.dep_id = departments.dep_id "+
                "LEFT OUTER JOIN subdepartments ON employees.subdep_id = subdepartments.subdep_id "+
                "INNER JOIN roles ON employees.roles_id = roles.roles_id "+
                "INNER JOIN institute ON departments.inst_id = institute.inst_id " +
                "WHERE employees.dep_id = ?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, departmentId);
        ResultSet results = prepStmt.executeQuery();

        loadResultsToList(results);

        results.close();
        prepStmt.close();
    }

    private void loadResultsToList(ResultSet results) throws SQLException {
        while (results.next()) {
            long empId = results.getLong("emp_id");
            String empFName = results.getString("emp_fname");
            String empMName = results.getString("emp_mname");
            String empLName = results.getString("emp_lname");
            long instId = results.getLong("inst_id");
            String instName = results.getString("inst_name");
            long depId = results.getLong("dep_id");
            String depName = results.getString("dep_name");
            long subdepId = results.getLong("subdep_id");
            String subdepName = results.getString("subdep_name");
            long roleId = results.getLong("roles_id");
            String roleName = results.getString("roles_name");
            String login = results.getString("login");
            String password = results.getString("password");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");

            EmployeeModel empModel = new EmployeeModel(empId, empFName, empMName, empLName,
                    instId, instName, depId, depName, subdepId, subdepName, roleId,
                    roleName, login, password, createdBy, createdDate, modifiedBy,
                    modifiedDate, active);
            list.add(empModel);
        }
    }

	public void update(EmployeeModel object) throws SQLException {
		String query = "UPDATE employees SET emp_fname=?, emp_mname=?, emp_lname=?, dep_id=?, subdep_id=?, roles_id=?, login=?, password=?, modified_by=?, modified_date=?, active=? WHERE emp_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getEmpFName());
		prepStmt.setString(2, object.getEmpMName());
		prepStmt.setString(3, object.getEmpLName());
		prepStmt.setLong(4, object.getDepId());
		prepStmt.setLong(5, object.getSubdepId());
		prepStmt.setLong(6, object.getRoleId());
		prepStmt.setString(7, object.getLogin());
		prepStmt.setString(8, object.getPassword());
		prepStmt.setLong(9, object.getModifiedBy());
		prepStmt.setTimestamp(10, object.getModifiedDate());
		prepStmt.setBoolean(11, object.isActive());
		prepStmt.setLong(12, object.getModelId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void delete(EmployeeModel object) throws SQLException {
		String query = "UPDATE employees SET active = false, modified_by=?, modified_date=? WHERE emp_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setLong(1, object.getModifiedBy());
		prepStmt.setTimestamp(2, object.getModifiedDate());
		prepStmt.setLong(3, object.getModelId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}
	
	public boolean checkLogin(String username, String password) throws SQLException{
		
		String query = "SELECT employees.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, " +
				"departments.inst_id, employees.dep_id, employees.subdep_id, employees.roles_id, employees.login, " +
                "employees.password, employees.created_by, employees.created_date, employees.modified_by, " +
                "employees.modified_date " +
                "FROM employees " +
                "INNER JOIN departments ON employees.dep_id = departments.dep_id " +
                "WHERE employees.login = ? AND employees.password = ? AND employees.active = true";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, username);
		prepStmt.setString(2, password);
		ResultSet results = prepStmt.executeQuery();
		if (results.next()) {
			LoginData.getInstance(results.getLong("emp_id"), results.getString("emp_fname"), results.getString("emp_mname"), results.getString("emp_lname"), results.getLong("inst_id"), results.getLong("dep_id"), results.getLong("subdep_id"), results.getLong("roles_id"), username, password, results.getLong("created_by"),results.getTimestamp("created_date"),results.getLong("modified_by"), results.getTimestamp("modified_date"));
			results.close();
			prepStmt.close();
			return true;
		}
		results.close();
		prepStmt.close();
		return false;
	}
}
