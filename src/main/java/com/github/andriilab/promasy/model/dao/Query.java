package com.github.andriilab.promasy.model.dao;

import com.github.andriilab.promasy.model.models.Model;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for queries
 */
interface Query<T extends Model> {
    void createOrUpdate(T object) throws SQLException;

    void refresh(T object);

    List<T> getList();

    List<T> getResults() throws SQLException;
}
