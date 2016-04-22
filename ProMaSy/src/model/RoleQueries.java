package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RoleQueries implements SQLQueries<RoleModel>{
	
	private List<RoleModel> rolesList;
	private Connection con;
	
	public RoleQueries()  {
		con = Database.INSTANCE.getConnection();
		rolesList = new LinkedList<RoleModel>();
	}

	@Override
	public void create(RoleModel object) {
		// TODO Auto-generated method stub
		
	}
	

	public void retrieve() throws SQLException {
		rolesList.clear();
		String query = "select roles_id, roles_name, created_by, created_date, modified_by, modified_date, active from roles where active = true";
		Statement selectStmt = con.createStatement();
		ResultSet results = selectStmt.executeQuery(query);

		while (results.next()) {
			long rolesId = results.getLong("roles_id");
			String rolesName = results.getString("roles_name");
			long createdBy = results.getLong("created_by");
			Timestamp createdDate = results.getTimestamp("created_date");
			long modifiedBy = results.getLong("modified_by");
			Timestamp modifiedDate = results.getTimestamp("modified_date");
			boolean active = results.getBoolean("active");

			RoleModel rolesModel = new RoleModel(rolesId, rolesName, createdBy, 
					createdDate, modifiedBy, modifiedDate, active);
			rolesList.add(rolesModel);
		}
		results.close();
		selectStmt.close();
	}
		

	@Override
	public void update(RoleModel object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(RoleModel object) {
		// TODO Auto-generated method stub
		
	}

	public List<RoleModel> getList() {
		return Collections.unmodifiableList(rolesList);
	}

}
