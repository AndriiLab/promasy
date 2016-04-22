package model;

import java.sql.SQLException;
import java.util.List;

public abstract interface SQLQueries<T> {
	
	public abstract void create(T object) throws SQLException;

	public abstract void retrieve() throws SQLException;
	
	public abstract void update(T object) throws SQLException;

	public abstract void delete (T object) throws SQLException;
	
	public abstract List<T> getList();

}
