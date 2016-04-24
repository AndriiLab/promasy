package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class InstituteQueries implements SQLQueries<InstituteModel>{
	
	private List<InstituteModel> instList;
	private final String id = "inst_id";
	private final String table = "institute";
	
	
	public InstituteQueries() {
		instList = new LinkedList<InstituteModel>();
	}

	public void create(InstituteModel object) throws SQLException {
		String query = "INSERT INTO institute (inst_name, created_by, created_date) VALUES (?, ?, ?)";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getInstName());
		prepStmt.setLong(2, object.getCreatedBy());
		prepStmt.setTimestamp(3, object.getCreatedDate());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void retrieve() throws SQLException {
		instList.clear();
		String query = "select inst_id, inst_name, created_by, created_date, modified_by, modified_date, active from institute where active = true";
		Statement selectStmt = Database.DB.getConnection().createStatement();
		ResultSet results = selectStmt.executeQuery(query);

		while (results.next()) {
			long instId = results.getLong("inst_id");
			String instName = results.getString("inst_name");
			long createdBy = results.getLong("created_by");
			Timestamp createdDate = results.getTimestamp("created_date");
			long modifiedBy = results.getLong("modified_by");
			Timestamp modifiedDate = results.getTimestamp("modified_date");
			boolean active = results.getBoolean("active");

			InstituteModel instModel = new InstituteModel(instId, instName, createdBy, createdDate, modifiedBy,
					modifiedDate, active);
			instList.add(instModel);
		}
		results.close();
		selectStmt.close();
	}

	public void update(InstituteModel object) throws SQLException {
		String query = "UPDATE institute SET inst_name=?, modified_by=?, modified_date=? WHERE inst_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getInstName());
		prepStmt.setLong(2, object.getModifiedBy());
		prepStmt.setTimestamp(3, object.getModifiedDate());
		prepStmt.setLong(4, object.getInstId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void delete(InstituteModel object) throws SQLException {
		String query = "UPDATE institute SET active=false, modified_by=?, modified_date=? WHERE inst_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setLong(1, object.getModifiedBy());
		prepStmt.setTimestamp(2, object.getModifiedDate());
		prepStmt.setLong(3, object.getInstId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public List<InstituteModel> getList() {
		return Collections.unmodifiableList(instList);
	}
	
	public boolean isChanged(LastChangesModel cacheModel) throws SQLException {
		return checkChanges(cacheModel, table, id);
	}

	public LastChangesModel getChangedModel() throws SQLException {
		return getChanged(table, id);
	}

}
