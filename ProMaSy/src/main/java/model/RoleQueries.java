package main.java.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RoleQueries extends SQLQueries<RoleModel>{

	public RoleQueries()  {
		super("roles_id", "roles");
	}

	@Override
	public void create(RoleModel object) {
		// TODO Auto-generated method stub
		
	}

	public void retrieve() throws SQLException {
		list.clear();
		String query = "select roles_id, roles_name from roles where active = true";
		Statement selectStmt = Database.DB.getConnection().createStatement();
		ResultSet results = selectStmt.executeQuery(query);

		while (results.next()) {
			int rolesId = results.getInt("roles_id");
			String rolesName = results.getString("roles_name");

			RoleModel rolesModel = new RoleModel(rolesId, rolesName);
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
