package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.IEntity;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Repository<T extends IEntity> implements IRepository<T> {

    private final Class<T> entityClass;
    CriteriaBuilder criteriaBuilder;
    CriteriaQuery<T> criteriaQuery;
    Root<T> root;
    private List<T> list = new LinkedList<>();
    protected final EntityManager entityManager;

    public Repository(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    @Override
    public void createOrUpdate(T object) throws JDBCException {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    @Override
    public void refresh(T object) throws JDBCException {
        entityManager.getTransaction().begin();
        entityManager.refresh(object);
        entityManager.getTransaction().commit();
    }

    void retrieve() throws JDBCException {
        entityManager.getTransaction().begin();

        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(entityClass);
        root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
    }

    public List<T> getList() throws JDBCException {
        list.clear();

        list = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }
}
