package model;

import java.sql.*;

public class EmployeeQueries extends SQLQueries<EmployeeModel>{

	EmployeeQueries() {
		super("emp_id", "employees");
	}
	
	public void create(EmployeeModel object) throws SQLException {
        String query = "INSERT INTO employees (emp_fname, emp_mname, emp_lname, dep_id, subdep_id, roles_id, login, password, created_by, created_date, salt, email, phone_main, phone_reserve) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getEmpFName());
		prepStmt.setString(2, object.getEmpMName());
		prepStmt.setString(3, object.getEmpLName());
		prepStmt.setLong(4, object.getDepId());
        long subdepId = object.getSubdepId();
        if (subdepId == 0L) {
            prepStmt.setNull(5, Types.BIGINT);
        } else {
            prepStmt.setLong(5, subdepId);
        }
        prepStmt.setLong(6, object.getRoleId());
        prepStmt.setString(7, object.getLogin());
		prepStmt.setString(8, object.getPassword());
		prepStmt.setLong(9, object.getCreatedBy());
		prepStmt.setTimestamp(10, object.getCreatedDate());
		prepStmt.setLong(11, object.getSalt());
        prepStmt.setString(12, object.getEmail());
        prepStmt.setString(13, object.getPhoneMain());
        prepStmt.setString(14, object.getPhoneReserve());
        prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void retrieve() throws SQLException {
		list.clear();
        String query = "SELECT employees.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, " +
                "employees.email, employees.phone_main, employees.phone_reserve, " +
                "departments.inst_id, institute.inst_name, "+
				"employees.dep_id, departments.dep_name, "+
				"employees.subdep_id, subdepartments.subdep_name, "+
				"employees.roles_id, roles.roles_name, "+
				"employees.login, employees.password, "+
				"employees.created_by, employees.created_date, "+
				"employees.modified_by, employees.modified_date, "+
				"employees.active, employees.salt "+
			"FROM employees "+
			"INNER JOIN departments ON employees.dep_id = departments.dep_id "+
			"LEFT OUTER JOIN subdepartments ON employees.subdep_id = subdepartments.subdep_id "+
			"INNER JOIN roles ON employees.roles_id = roles.roles_id "+
			"INNER JOIN institute ON departments.inst_id = institute.inst_id ORDER BY emp_lname ASC";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		ResultSet results = prepStmt.executeQuery();

        loadResultsToList(results);

		results.close();
		prepStmt.close();
	}

    public void retrieve(int roleId) throws SQLException {
        list.clear();
        String query = "SELECT employees.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, "+
                "employees.email, employees.phone_main, employees.phone_reserve, " +
                "departments.inst_id, institute.inst_name, "+
                "employees.dep_id, departments.dep_name, "+
                "employees.subdep_id, subdepartments.subdep_name, "+
                "employees.roles_id, roles.roles_name, "+
                "employees.login, employees.password, "+
                "employees.created_by, employees.created_date, "+
                "employees.modified_by, employees.modified_date, "+
                "employees.active, employees.salt "+
                "FROM employees "+
                "INNER JOIN departments ON employees.dep_id = departments.dep_id "+
                "LEFT OUTER JOIN subdepartments ON employees.subdep_id = subdepartments.subdep_id "+
                "INNER JOIN roles ON employees.roles_id = roles.roles_id "+
                "INNER JOIN institute ON departments.inst_id = institute.inst_id " +
                "WHERE employees.roles_id = ? ORDER BY emp_lname ASC";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setInt(1, roleId);
        ResultSet results = prepStmt.executeQuery();

        loadResultsToList(results);

        results.close();
        prepStmt.close();
    }

    public void retrieve(long departmentId) throws SQLException {
        list.clear();
        String query = "SELECT employees.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, "+
                "employees.email, employees.phone_main, employees.phone_reserve, " +
                "departments.inst_id, institute.inst_name, "+
                "employees.dep_id, departments.dep_name, "+
                "employees.subdep_id, subdepartments.subdep_name, "+
                "employees.roles_id, roles.roles_name, "+
                "employees.login, employees.password, "+
                "employees.created_by, employees.created_date, "+
                "employees.modified_by, employees.modified_date, "+
                "employees.active, employees.salt "+
                "FROM employees "+
                "INNER JOIN departments ON employees.dep_id = departments.dep_id "+
                "LEFT OUTER JOIN subdepartments ON employees.subdep_id = subdepartments.subdep_id "+
                "INNER JOIN roles ON employees.roles_id = roles.roles_id "+
                "INNER JOIN institute ON departments.inst_id = institute.inst_id " +
                "WHERE employees.dep_id = ? ORDER BY emp_lname ASC";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setLong(1, departmentId);
        ResultSet results = prepStmt.executeQuery();

        loadResultsToList(results);

        results.close();
        prepStmt.close();
    }

    public void retrieve(int roleId, long departmentId) throws SQLException {
        list.clear();
        String query = "SELECT employees.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, "+
                "employees.email, employees.phone_main, employees.phone_reserve, " +
                "departments.inst_id, institute.inst_name, "+
                "employees.dep_id, departments.dep_name, "+
                "employees.subdep_id, subdepartments.subdep_name, "+
                "employees.roles_id, roles.roles_name, "+
                "employees.login, employees.password, "+
                "employees.created_by, employees.created_date, "+
                "employees.modified_by, employees.modified_date, "+
                "employees.active, employees.salt "+
                "FROM employees "+
                "INNER JOIN departments ON employees.dep_id = departments.dep_id "+
                "LEFT OUTER JOIN subdepartments ON employees.subdep_id = subdepartments.subdep_id "+
                "INNER JOIN roles ON employees.roles_id = roles.roles_id "+
                "INNER JOIN institute ON departments.inst_id = institute.inst_id " +
                "WHERE employees.roles_id = ? AND employees.dep_id = ? ORDER BY emp_lname ASC";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setInt(1, roleId);
        prepStmt.setLong(2, departmentId);
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
            String email = results.getString("email");
            String phoneMain = results.getString("phone_main");
            String phoneReserve = results.getString("phone_reserve");
            long instId = results.getLong("inst_id");
            String instName = results.getString("inst_name");
            long depId = results.getLong("dep_id");
            String depName = results.getString("dep_name");
            long subdepId = results.getLong("subdep_id");
            String subdepName = results.getString("subdep_name");
            int roleId = results.getInt("roles_id");
            String roleName = results.getString("roles_name");
            String login = results.getString("login");
            String password = results.getString("password");
			long salt = results.getLong("salt");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");

            EmployeeModel empModel = new EmployeeModel(empId, createdBy, createdDate, modifiedBy, modifiedDate, active, empFName, empMName, empLName, email, phoneMain, phoneReserve, instId, instName, depId, depName, subdepId, subdepName, roleId, roleName, login, password, salt);
            list.add(empModel);
        }
    }

	public void update(EmployeeModel object) throws SQLException {
        String query = "UPDATE employees SET emp_fname=?, emp_mname=?, emp_lname=?, dep_id=?, subdep_id=?, roles_id=?, login=?, password=?, modified_by=?, modified_date=?, active=?, salt=?, email=?, phone_main=?, phone_reserve=? WHERE emp_id=?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getEmpFName());
		prepStmt.setString(2, object.getEmpMName());
		prepStmt.setString(3, object.getEmpLName());
		prepStmt.setLong(4, object.getDepId());
        long subdepId = object.getSubdepId();
        if (subdepId == 0) {
            prepStmt.setNull(5, Types.BIGINT);
        } else {
            prepStmt.setLong(5, subdepId);
        }
        prepStmt.setLong(6, object.getRoleId());
        prepStmt.setString(7, object.getLogin());
		prepStmt.setString(8, object.getPassword());
		prepStmt.setLong(9, object.getModifiedBy());
		prepStmt.setTimestamp(10, object.getModifiedDate());
		prepStmt.setBoolean(11, object.isActive());
        prepStmt.setLong(12, object.getSalt());
        prepStmt.setString(13, object.getEmail());
        prepStmt.setString(14, object.getPhoneMain());
        prepStmt.setString(15, object.getPhoneReserve());
        prepStmt.setLong(16, object.getModelId());
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

    public long getSalt(String username) throws SQLException {
        String query = "SELECT employees.salt FROM employees WHERE employees.login = ? AND employees.active = true";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setString(1, username);
        ResultSet results = prepStmt.executeQuery();
        if (results.next()) {
            return results.getLong("salt");
        }
        return 0;
    }
	
	public boolean checkLogin(String username, String password) throws SQLException{
		String query = "SELECT employees.emp_id, employees.emp_fname, employees.emp_mname, employees.emp_lname, " +
                "employees.email, employees.phone_main, employees.phone_reserve, " +
                "departments.inst_id, institute.inst_name, " +
                "employees.dep_id, departments.dep_name, " +
                "employees.subdep_id, subdepartments.subdep_name, " +
                "employees.roles_id, roles.roles_name, " +
                "employees.login, employees.password, " +
                "employees.created_by, employees.created_date, " +
                "employees.modified_by, employees.modified_date, " +
                "employees.active, employees.salt " +
                "FROM employees " +
                "INNER JOIN departments ON employees.dep_id = departments.dep_id " +
                "LEFT OUTER JOIN subdepartments ON employees.subdep_id = subdepartments.subdep_id " +
                "INNER JOIN roles ON employees.roles_id = roles.roles_id " +
                "INNER JOIN institute ON departments.inst_id = institute.inst_id " +
                "WHERE employees.login = ? AND employees.password = ? AND employees.active = TRUE";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, username);
		prepStmt.setString(2, password);
		ResultSet results = prepStmt.executeQuery();
		if (results.next()) {
            long empId = results.getLong("emp_id");
            String empFName = results.getString("emp_fname");
            String empMName = results.getString("emp_mname");
            String empLName = results.getString("emp_lname");
            String email = results.getString("email");
            String phoneMain = results.getString("phone_main");
            String phoneReserve = results.getString("phone_reserve");
            long instId = results.getLong("inst_id");
            String instName = results.getString("inst_name");
            long depId = results.getLong("dep_id");
            String depName = results.getString("dep_name");
            long subdepId = results.getLong("subdep_id");
            String subdepName = results.getString("subdep_name");
            int roleId = results.getInt("roles_id");
            String roleName = results.getString("roles_name");
            String login = results.getString("login");
            String pass = results.getString("password");
            long salt = results.getLong("salt");
            long createdBy = results.getLong("created_by");
            Timestamp createdDate = results.getTimestamp("created_date");
            long modifiedBy = results.getLong("modified_by");
            Timestamp modifiedDate = results.getTimestamp("modified_date");
            boolean active = results.getBoolean("active");

            LoginData.getInstance(new EmployeeModel(empId, createdBy, createdDate, modifiedBy, modifiedDate, active, empFName, empMName, empLName, email, phoneMain, phoneReserve, instId, instName, depId, depName, subdepId, subdepName, roleId, roleName, login, pass, salt));
            results.close();
            prepStmt.close();
			return true;
		}
		results.close();
		prepStmt.close();
		return false;
	}

    public boolean checkLogin(String username) throws SQLException {
        String query = "SELECT login FROM employees WHERE login = ?";
        PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
        prepStmt.setString(1, username);
        ResultSet results = prepStmt.executeQuery();
        if (results.next()) {
            results.close();
            prepStmt.close();
            return false;
        }
        results.close();
        prepStmt.close();
        return true;
    }

    public boolean isFirstRun() throws SQLException {
        String query = "SELECT active FROM employees";
        Statement selectStmt = Database.DB.getConnection().createStatement();
        ResultSet results = selectStmt.executeQuery(query);
        if (results.next()) {
            results.close();
            selectStmt.close();
            return false;
        }
        results.close();
        selectStmt.close();
        return true;
    }
}
