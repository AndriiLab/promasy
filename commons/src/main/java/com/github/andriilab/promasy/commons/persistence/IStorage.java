package com.github.andriilab.promasy.commons.persistence;

public interface IStorage {
    <E extends IEntity> IRepository<E> getByClass(Class<E> entityClass);
}
