package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DepartmentQueries implements SQLQueries<DepartmentModel>{
	
	private List<DepartmentModel> depList;
	private final String id = "dep_id";
	private final String table = "departments";
	
	public DepartmentQueries() {
		depList = new LinkedList<>();
	}

	public void create(DepartmentModel object) throws SQLException {
		String query = "INSERT INTO departments(dep_name, inst_id, created_by, created_date) VALUES (?, ?, ?, ?)";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getDepName());
		prepStmt.setLong(2, object.getInstId());
		prepStmt.setLong(3, object.getCreatedBy());
		prepStmt.setTimestamp(4, object.getCreatedDate());
		prepStmt.executeUpdate();
		prepStmt.close();
	}
	
	@Override
	public void retrieve() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void retrieve(long reqInstId) throws SQLException {
		depList.clear();
		String query = "select dep_id, dep_name, inst_id, created_by, created_date, modified_by, modified_date, active from departments where inst_id = ? and active = true";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setLong(1, reqInstId);
		ResultSet results = prepStmt.executeQuery();

		while (results.next()) {
			long depId = results.getLong("dep_id");
			String depName = results.getString("dep_name");
			long instId = results.getLong("inst_id");
			long createdBy = results.getLong("created_by");
			Timestamp createdDate = results.getTimestamp("created_date");
			long modifiedBy = results.getLong("modified_by");
			Timestamp modifiedDate = results.getTimestamp("modified_date");
			boolean active = results.getBoolean("active");

			DepartmentModel depModel = new DepartmentModel(depId, depName, instId, createdBy, 
					createdDate, modifiedBy, modifiedDate, active);
			depList.add(depModel);
		}
		results.close();
		prepStmt.close();
	}

	public void update(DepartmentModel object) throws SQLException {
		String query = "UPDATE departments SET dep_name=?, modified_by=?, modified_date=? WHERE dep_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getDepName());
		prepStmt.setLong(2, object.getModifiedBy());
		prepStmt.setTimestamp(3, object.getModifiedDate());
		prepStmt.setLong(4, object.getDepId());
		prepStmt.executeUpdate();
		prepStmt.close();
		
	}

	public void delete(DepartmentModel object) throws SQLException {
		String query = "UPDATE departments SET active=false, modified_by=?, modified_date=? WHERE dep_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setLong(1, object.getModifiedBy());
		prepStmt.setTimestamp(2, object.getModifiedDate());
		prepStmt.setLong(3, object.getDepId());
		prepStmt.executeUpdate();
		prepStmt.close();
		
	}

	public List<DepartmentModel> getList() {
		return Collections.unmodifiableList(depList);
	}
	
	public boolean isChanged(LastChangesModel cacheModel) throws SQLException {
		return checkChanges(cacheModel, table, id);
	}

	public LastChangesModel getChangedModel() throws SQLException {
		return getChanged(table, id);
	}

}
