package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class SQLQueries<T> {

    List<T> list = new ArrayList<>();
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
}
