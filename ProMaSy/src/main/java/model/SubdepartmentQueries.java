package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SubdepartmentQueries extends SQLQueries<SubdepartmentModel>{

	public SubdepartmentQueries() {
		super("subdep_id", "subdepartments");
	}

	@Override
	public void create(SubdepartmentModel object) throws SQLException {
		String query = "INSERT INTO subdepartments(subdep_name, dep_id, created_by, created_date) VALUES (?, ?, ?, ?)";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getSubdepName());
		prepStmt.setLong(2, object.getDepartment());
		prepStmt.setLong(3, object.getCreatedBy());
		prepStmt.setTimestamp(4, object.getCreatedDate());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	@Override
	public void retrieve() throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public void retrieve(long reqDepId) throws SQLException {
		list.clear();
		
		String query = "SELECT subdep_id, subdep_name, dep_id, created_by, created_date, modified_by, modified_date, active FROM subdepartments WHERE dep_id = ? AND active = TRUE ORDER BY subdep_name ASC";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setLong(1, reqDepId);
		ResultSet results = prepStmt.executeQuery();

		while (results.next()) {
			long subdepId = results.getLong("subdep_id");
			String subdepName = results.getString("subdep_name");
			long depId = results.getLong("dep_id");
			long createdBy = results.getLong("created_by");
			Timestamp createdDate = results.getTimestamp("created_date");
			long modifiedBy = results.getLong("modified_by");
			Timestamp modifiedDate = results.getTimestamp("modified_date");
			boolean active = results.getBoolean("active");

			SubdepartmentModel subdepModel = new SubdepartmentModel(subdepId, subdepName, 
					depId, createdBy, createdDate, modifiedBy, modifiedDate, active);
			list.add(subdepModel);
		}
		results.close();
		prepStmt.close();
	}

	public void update(SubdepartmentModel object) throws SQLException {
		String query = "UPDATE subdepartments SET subdep_name=?, modified_by=?, modified_date=? WHERE subdep_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getSubdepName());
		prepStmt.setLong(2, object.getModifiedBy());
		prepStmt.setTimestamp(3, object.getModifiedDate());
		prepStmt.setLong(4, object.getModelId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void delete(SubdepartmentModel object) throws SQLException {
		String query = "UPDATE subdepartments SET active=false, modified_by=?, modified_date=? WHERE subdep_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setLong(1, object.getModifiedBy());
		prepStmt.setTimestamp(2, object.getModifiedDate());
		prepStmt.setLong(3, object.getModelId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}
}
