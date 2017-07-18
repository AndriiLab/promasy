package model.dao;

import model.models.Model;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for queries
 */
public interface Query<T extends Model> {
    void createOrUpdate(T object) throws SQLException;

    void refresh(T object);

    List<T> getList();

    List<T> getResults() throws SQLException;
}
