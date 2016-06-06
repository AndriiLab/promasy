package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class RoleQueries extends SQLQueries<RoleModel>{

	public RoleQueries()  {
		id = "roles_id";
		table = "roles";
	}

	@Override
	public void create(RoleModel object) {
		// TODO Auto-generated method stub
		
	}

	public void retrieve() throws SQLException {
		list.clear();
		String query = "select roles_id, roles_name, created_by, created_date, modified_by, modified_date, active from roles where active = true";
		Statement selectStmt = Database.DB.getConnection().createStatement();
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
			list.add(rolesModel);
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

}
