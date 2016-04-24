package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public abstract interface SQLQueries<T> {
	
	public abstract void create(T object) throws SQLException;

	public abstract void retrieve() throws SQLException;
	
	public abstract void update(T object) throws SQLException;

	public abstract void delete (T object) throws SQLException;
	
	public abstract List<T> getList();
	
	public default boolean checkChanges(LastChangesModel cacheModel, String table, String id) throws SQLException {
		String query = "SELECT MAX(created_date), MAX(modified_date), COUNT(" + id + ") FROM " + table;
		Statement selectStmt = DBConnector.INSTANCE.getConnection().createStatement();
		ResultSet results = selectStmt.executeQuery(query);
		results.next();
		int numElements = results.getInt("created_date");
		Timestamp lastCreated = results.getTimestamp("created_date");
		Timestamp lastModified = results.getTimestamp("modified_date");
		results.close();
		selectStmt.close();
		if (cacheModel.getLastModified() == lastModified &&
			cacheModel.getLastCreated() == lastCreated &&
			cacheModel.getNumElements() == numElements){
			return true;
		} else return false;
	}

	public boolean isChanged(LastChangesModel cacheModel) throws SQLException;
	
	public default LastChangesModel getChanged(String table, String id) throws SQLException{
		String query = "SELECT MAX(created_date), MAX(modified_date), COUNT(" + id + ") FROM " + table;
		Statement selectStmt = DBConnector.INSTANCE.getConnection().createStatement();
		ResultSet results = selectStmt.executeQuery(query);
		results.next();
		int numElements = results.getInt("created_date");
		Timestamp lastCreated = results.getTimestamp("created_date");
		Timestamp lastModified = results.getTimestamp("modified_date");
		LastChangesModel model = new LastChangesModel(numElements, lastCreated, lastModified);
		results.close();
		selectStmt.close();
		
		return model;
	}
	
	public LastChangesModel getChangedModel() throws SQLException;
}
