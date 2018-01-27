package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.domain.IEntity;
import org.hibernate.JDBCException;

import java.util.List;

/**
 * Interface for queries
 */
interface Query<T extends IEntity> {
    void createOrUpdate(T object) throws JDBCException;

    void refresh(T object);

    List<T> getList();

    List<T> getResults() throws JDBCException;
}
