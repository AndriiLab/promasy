package com.github.andriilab.promasy.commons.persistence;

import org.hibernate.JDBCException;

import java.util.Collection;
import java.util.List;

/**
 * Interface for queries
 */
public interface IRepository<T extends IEntity> {
    void createOrUpdate(T object) throws JDBCException;

    default void createOrUpdate(Collection<T> objects) throws JDBCException {
        objects.forEach(this::createOrUpdate);
    }

    void refresh(T object);

    default void refresh(Collection<T> objects) throws JDBCException {
        objects.forEach(this::refresh);
    }

    List<T> getResults() throws JDBCException;

    Class<T> getEntityClass();
}
