package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.authorization.LoginData;
import com.github.andriilab.promasy.domain.IEntity;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public abstract class Repository<T extends IEntity> implements IRepository<T> {

    private final Class<T> entityClass;
    CriteriaBuilder criteriaBuilder;
    CriteriaQuery<T> criteriaQuery;
    Root<T> root;
    protected final EntityManager entityManager;

    public Repository(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    /**
     * Runs the work between begin and commit of the given manager's transaction. If the work (or the commit) fails
     * with a {@link RuntimeException}, an active transaction is rolled back before the exception is rethrown, so the
     * shared {@link EntityManager} stays usable for the next call.
     */
    static <R> R inTransaction(EntityManager entityManager, Supplier<R> work) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            R result = work.get();
            transaction.commit();
            return result;
        } catch (RuntimeException e) {
            rollbackAfterFailure(transaction, e);
            throw e;
        }
    }

    static void inTransaction(EntityManager entityManager, Runnable work) {
        inTransaction(entityManager, () -> {
            work.run();
            return null;
        });
    }

    static void rollbackAfterFailure(EntityTransaction transaction, RuntimeException cause) {
        try {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } catch (RuntimeException rollbackFailure) {
            cause.addSuppressed(rollbackFailure);
        }
    }

    protected <R> R inTransaction(Supplier<R> work) {
        return inTransaction(entityManager, work);
    }

    protected void inTransaction(Runnable work) {
        inTransaction(entityManager, work);
    }

    @Override
    public void create(T object) throws JDBCException {
        object.setCreatedDate(ServerRepository.getServerTimestamp());
        object.setCreatedEmployee(LoginData.getInstance());
        createOrUpdate(object);
    }

    @Override
    public void update(T object) throws JDBCException {
        object.setModifiedDate(ServerRepository.getServerTimestamp());
        object.setModifiedEmployee(LoginData.getInstance());
        createOrUpdate(object);
    }

    @Override
    public void delete(T object) throws JDBCException {
        object.setActive(false, LoginData.getInstance(), ServerRepository.getServerTimestamp());
        createOrUpdate(object);
    }

    @Override
    public void refresh(T object) throws JDBCException {
        inTransaction(() -> entityManager.refresh(object));
    }

    /**
     * Opens the transaction and prepares the criteria query. The transaction is closed by {@link #getList()}.
     */
    void retrieve() throws JDBCException {
        entityManager.getTransaction().begin();

        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(entityClass);
        root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
    }

    public List<T> getList() throws JDBCException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            List<T> list = entityManager.createQuery(criteriaQuery).getResultList();
            transaction.commit();
            return Collections.unmodifiableList(list);
        } catch (RuntimeException e) {
            rollbackAfterFailure(transaction, e);
            throw e;
        }
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    private void createOrUpdate(T object) throws JDBCException {
        inTransaction(() -> entityManager.persist(object));
    }
}
