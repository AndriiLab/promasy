package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.IEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

abstract class SQLQueries<T extends IEntity> implements Query<T> {

    private final Class<T> entityClass;
    CriteriaBuilder criteriaBuilder;
    CriteriaQuery<T> criteriaQuery;
    Root<T> root;
    private List<T> list = new LinkedList<>();
    private EntityManager entityManager;

    SQLQueries(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void createOrUpdate(T object) {
        entityManager = Database.CONNECTOR.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    @Override
    public void refresh(T object) {
        entityManager = Database.CONNECTOR.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.refresh(object);
        entityManager.getTransaction().commit();
    }

    void retrieve() {
        entityManager = Database.CONNECTOR.getEntityManager();
        entityManager.getTransaction().begin();

        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(entityClass);
        root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
    }

    @Override
    public List<T> getList() {
        list.clear();

        list = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

}
