package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EmployeeQueries implements SQLQueries<EmployeeModel>{
	
	private List<EmployeeModel> empList;
	private final String id = "emp_id";
	private final String table = "employees";
	
	public EmployeeQueries() {
		empList = new LinkedList<EmployeeModel>();
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
		empList.clear();
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
			empList.add(empModel);
		}
		results.close();
		prepStmt.close();
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
		prepStmt.setLong(12, object.getEmpId());
		System.out.println(prepStmt);
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void delete(EmployeeModel object) throws SQLException {
		String query = "UPDATE employees SET active = false, modified_by=?, modified_date=? WHERE emp_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setLong(1, object.getModifiedBy());
		prepStmt.setTimestamp(2, object.getModifiedDate());
		prepStmt.setLong(3, object.getEmpId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}
	
	public boolean checkLogin(LoginData logindata) throws SQLException{
		
		String query = "select emp_id, emp_fname, emp_mname, emp_lname, dep_id, subdep_id, roles_id, login, password, created_by, created_date, modified_by, modified_date from employees where login = ? and password = ? and active = true";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, logindata.getLogin());
		prepStmt.setString(2, logindata.getPassword());
		ResultSet results = prepStmt.executeQuery();
		if (results.next()) {
			logindata.setEmpId(results.getLong("emp_id"));
			logindata.setEmpFName(results.getString("emp_fname"));
			logindata.setEmpMName(results.getString("emp_mname"));
			logindata.setEmpLName(results.getString("emp_lname"));
			logindata.setDepId(results.getLong("dep_id"));
			logindata.setSubdepId(results.getLong("subdep_id"));
			logindata.setRoleId(results.getLong("roles_id"));
			logindata.setCreatedBy(results.getLong("created_by"));
			logindata.setCreatedDate(results.getTimestamp("created_date"));
			logindata.setModifiedBy(results.getLong("modified_by"));
			logindata.setModifiedDate(results.getTimestamp("modified_date"));
			results.close();
			prepStmt.close();
			return true;
		}
		results.close();
		prepStmt.close();
		return false;
	}

	public List<EmployeeModel> getList() {
		return Collections.unmodifiableList(empList);
	}

	public boolean isChanged(LastChangesModel cacheModel) throws SQLException {
		return checkChanges(cacheModel, table, id);
	}

	public LastChangesModel getChangedModel() throws SQLException {
		return getChanged(table, id);
	}
}
