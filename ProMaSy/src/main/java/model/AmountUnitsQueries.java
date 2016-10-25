package main.java.model;

import java.sql.*;

public class AmountUnitsQueries extends SQLQueries<AmountUnitsModel>{

	public AmountUnitsQueries() {
        super("am_unit_id", "amountunits");
	}

	public void create(AmountUnitsModel object) throws SQLException {
        String query = "INSERT INTO amountunits(am_unit_desc, created_by, created_date) VALUES (?, ?, ?)";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getAmUnitDesc());
		prepStmt.setLong(2, object.getCreatedBy());
		prepStmt.setTimestamp(3, object.getCreatedDate());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void retrieve() throws SQLException {
		list.clear();
		String query = "SELECT am_unit_id, am_unit_desc, created_by, created_date, modified_by, modified_date, active FROM amountunits WHERE active = TRUE ORDER BY am_unit_desc ASC";
		Statement selectStmt = Database.DB.getConnection().createStatement();
		ResultSet results = selectStmt.executeQuery(query);

		while (results.next()) {
			long amUnitId = results.getLong("am_unit_id");
			String amUnitDesc = results.getString("am_unit_desc");
			long createdBy = results.getLong("created_by");
			Timestamp createdDate = results.getTimestamp("created_date");
			long modifiedBy = results.getLong("modified_by");
			Timestamp modifiedDate = results.getTimestamp("modified_date");
			boolean active = results.getBoolean("active");
			
			AmountUnitsModel amUnitsModel = new AmountUnitsModel(amUnitId, amUnitDesc,
					createdBy, createdDate, modifiedBy, modifiedDate, active);
			list.add(amUnitsModel);
		}
		results.close();
		selectStmt.close();
	}

	public void update(AmountUnitsModel object) throws SQLException {
		String query = "UPDATE amountunits SET am_unit_desc=?, modified_by=?, modified_date=?, active=? WHERE am_unit_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setString(1, object.getAmUnitDesc());
		prepStmt.setLong(2, object.getModifiedBy());
		prepStmt.setTimestamp(3, object.getModifiedDate());
		prepStmt.setBoolean(4, object.isActive());
		prepStmt.setLong(5, object.getModelId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void delete(AmountUnitsModel object) throws SQLException {
		String query = "UPDATE amountunits SET modified_by=?, modified_date=?, active=? WHERE am_unit_id=?";
		PreparedStatement prepStmt = Database.DB.getConnection().prepareStatement(query);
		prepStmt.setLong(1, object.getModifiedBy());
		prepStmt.setTimestamp(2, object.getModifiedDate());
		prepStmt.setBoolean(3, object.isActive());
		prepStmt.setLong(4, object.getModelId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

}
