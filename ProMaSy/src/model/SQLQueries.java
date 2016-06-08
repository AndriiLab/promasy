package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

abstract class SQLQueries<T> {

    List<T> list = new LinkedList<>();
    private String id;
    private String table;

    SQLQueries(String id, String table) {
        this.id = id;
        this.table = table;
    }

    abstract void create(T object) throws SQLException;
    abstract void retrieve() throws SQLException;
    abstract void update(T object) throws SQLException;
    abstract void delete(T object) throws SQLException;
	public List<T> getList() {
        return Collections.unmodifiableList(list);
    }

    //TODO implement cache system
	boolean checkChanges(LastChangesModel cacheModel, String table, String id) throws SQLException {
		String query = "SELECT MAX(created_date), MAX(modified_date), COUNT(" + id + ") FROM " + table;
		Statement selectStmt = DBConnector.INSTANCE.getConnection().createStatement();
		ResultSet results = selectStmt.executeQuery(query);
		results.next();
		int numElements = results.getInt("created_date");
		Timestamp lastCreated = results.getTimestamp("created_date");
		Timestamp lastModified = results.getTimestamp("modified_date");
		results.close();
		selectStmt.close();
		return cacheModel.getLastModified() == lastModified &&
				cacheModel.getLastCreated() == lastCreated &&
				cacheModel.getNumElements() == numElements;
	}

    boolean isChanged(LastChangesModel cacheModel) throws SQLException {
        return checkChanges(cacheModel, table, id);
    }

    LastChangesModel getChangedModel() throws SQLException{
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
}
