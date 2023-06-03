package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.IEntity;
import org.hibernate.JDBCException;

import java.util.Collection;
import java.util.List;

/**
 * Interface for queries
 */
public interface IRepository<T extends IEntity> {
    void create(T object) throws JDBCException;
    void update(T object) throws JDBCException;
    void delete(T object) throws JDBCException;

    void refresh(T object)throws JDBCException;

    default void refresh(Collection<T> objects) throws JDBCException {
        objects.forEach(this::refresh);
    }

    List<T> getResults() throws JDBCException;

    Class<T> getEntityClass();
}
