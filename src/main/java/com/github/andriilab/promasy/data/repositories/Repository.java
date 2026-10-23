package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.authorization.LoginData;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import org.hibernate.JDBCException;
import org.hibernate.LockOptions;
import org.hibernate.Session;

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
            reattachLoggedInEmployee(entityManager, e);
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

    /**
     * A rollback detaches every entity of the shared {@link EntityManager}, including the logged-in employee that
     * {@code create}/{@code update} put into the audit fields. Those fields cascade PERSIST, so a detached employee
     * would make every later write fail with "detached entity passed to persist"; attach it again.
     */
    static void reattachLoggedInEmployee(EntityManager entityManager, RuntimeException cause) {
        try {
            Employee employee = LoginData.getInstance();
            if (employee != null && !entityManager.contains(employee)) {
                entityManager.unwrap(Session.class).buildLockRequest(LockOptions.NONE).lock(employee);
            }
        } catch (RuntimeException reattachFailure) {
            cause.addSuppressed(reattachFailure);
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
        save(object);
    }

    @Override
    public void delete(T object) throws JDBCException {
        object.setActive(false, LoginData.getInstance(), ServerRepository.getServerTimestamp());
        save(object);
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
            reattachLoggedInEmployee(entityManager, e);
            throw e;
        }
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    private void createOrUpdate(T object) throws JDBCException {
        inTransaction(() -> entityManager.persist(object));
    }

    /**
     * Writes the changes of an entity that already has a row. A rollback detaches every entity the UI still holds,
     * and {@code persist} rejects a detached entity (and, through the PERSIST cascades, any detached entity it
     * references), so such an entity is merged instead. The merge works on the managed copy, so the new version is
     * copied back: the caller keeps using its own instance and the next write must not look stale.
     */
    private void save(T object) throws JDBCException {
        if (object.getModelId() == 0 || entityManager.contains(object)) {
            createOrUpdate(object);
            return;
        }
        T merged = inTransaction(() -> entityManager.merge(object));
        object.setVersion(merged.getVersion());
    }
}
