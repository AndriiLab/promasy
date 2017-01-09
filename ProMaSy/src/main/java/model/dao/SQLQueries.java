package model.dao;

import model.models.AbstractModel;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class SQLQueries<T extends AbstractModel> {

    private final Class<T> entityClass;
    CriteriaBuilder criteriaBuilder;
    CriteriaQuery<T> criteriaQuery;
    Root<T> root;
    private List<T> list = new ArrayList<>();
    private EntityManager entityManager;

    SQLQueries(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void createOrUpdate(T object) throws SQLException {
        entityManager = Database.DB.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(object);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    void retrieve() throws SQLException {
        entityManager = Database.DB.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(entityClass);
        root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
    }

	public List<T> getList() {
        list.clear();

        list = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

        return Collections.unmodifiableList(list);
    }

    public abstract List<T> getResults() throws SQLException;
}
