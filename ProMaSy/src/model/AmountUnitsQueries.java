package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AmountUnitsQueries implements SQLQueries<AmountUnitsModel>{
	
	private List<AmountUnitsModel> amUnitList;
	private Connection con;
	
	public AmountUnitsQueries() {
		con = Database.INSTANCE.getConnection();
		amUnitList = new LinkedList<AmountUnitsModel>();
	}

	public void create(AmountUnitsModel object) throws SQLException {
		String query = "INSERT INTO amountunits(am_unit_desc, created_by, created_date) VALUES (?, ?, ?)";
		PreparedStatement prepStmt = con.prepareStatement(query);
		prepStmt.setString(1, object.getAmUnitDesc());
		prepStmt.setLong(2, object.getCreatedBy());
		prepStmt.setTimestamp(3, object.getCreatedDate());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void retrieve() throws SQLException {
		amUnitList.clear();
		String query = "SELECT am_unit_id, am_unit_desc, created_by, created_date, modified_by, modified_date, active FROM amountunits";
		Statement selectStmt = con.createStatement();
		ResultSet results = selectStmt.executeQuery(query);

		while (results.next()) {
			long amUnitId = results.getLong("am_unit_id");
			String amUnitDesc = results.getString("am_unit_desc");
			long createdBy = results.getLong("created_by");
			Timestamp createdDate = results.getTimestamp("created_date");
			long modifiedBy = results.getLong("modified_by");
			Timestamp modifiedDate = results.getTimestamp("modified_date");
			boolean active = results.getBoolean("active");
			
			AmountUnitsModel amUnintsModel = new AmountUnitsModel(amUnitId, amUnitDesc, 
					createdBy, createdDate, modifiedBy, modifiedDate, active);
			amUnitList.add(amUnintsModel);
		}
		results.close();
		selectStmt.close();
	}

	public void update(AmountUnitsModel object) throws SQLException {
		String query = "UPDATE amountunits SET am_unit_desc=?, modified_by=?, modified_date=?, active=? WHERE am_unit_id=?";
		PreparedStatement prepStmt = con.prepareStatement(query);
		prepStmt.setString(1, object.getAmUnitDesc());
		prepStmt.setLong(2, object.getModifiedBy());
		prepStmt.setTimestamp(3, object.getModifiedDate());
		prepStmt.setBoolean(4, object.isActive());
		prepStmt.setLong(5, object.getAmUnitId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void delete(AmountUnitsModel object) throws SQLException {
		String query = "UPDATE amountunits SET modified_by=?, modified_date=?, active=? WHERE am_unit_id=?";
		PreparedStatement prepStmt = con.prepareStatement(query);
		prepStmt.setLong(1, object.getModifiedBy());
		prepStmt.setTimestamp(2, object.getModifiedDate());
		prepStmt.setBoolean(3, object.isActive());
		prepStmt.setLong(4, object.getAmUnitId());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public List<AmountUnitsModel> getList() {
		return Collections.unmodifiableList(amUnitList);
	}
}
