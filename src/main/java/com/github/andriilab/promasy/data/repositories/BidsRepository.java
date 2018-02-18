package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.Bid_;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

/**
 * CRUD for bid data
 */
public class BidsRepository extends Repository<Bid> {

    public BidsRepository(EntityManager entityManager) {
        super(Bid.class, entityManager);
    }

    public List<Bid> retrieve(GetBidsQuery query) throws JDBCException {

        if (query.getFinanceDepartmentId() != 0)
            return retrieveByFinanceDepartment(query.getType(), query.getYear(), query.getFinanceDepartmentId());
        else if (query.getSubdepartmentId() != 0)
            return retrieveBySubdepartment(query.getType(), query.getYear(), query.getSubdepartmentId());
        else if (query.getDepartmentId() != 0)
            return retrieveByDepartment(query.getType(), query.getYear(), query.getDepartmentId());
        else if (query.getType() != null)
            return retrieveByType(query.getType(), query.getYear());
        else
            return retrieveByYear(query.getYear());
    }

    private List<Bid> retrieveByFinanceDepartment(BidType type, int year, long financeDepartmentId) throws JDBCException {
        entityManager.getTransaction().begin();
        TypedQuery<Bid> query = entityManager.createQuery("select bm from Bid bm join bm.finances fd join fd.finances fm where EXTRACT(YEAR from fm.startDate) <= :requestedYear and EXTRACT(YEAR from fm.endDate) >= :requestedYear and bm.active = true and bm.type = :bidType and bm.finances.id = :financeDepartmentId order by coalesce(bm.modifiedDate, bm.createdDate) desc",
                Bid.class)
                .setParameter("requestedYear", year)
                .setParameter("bidType", type)
                .setParameter("financeDepartmentId", financeDepartmentId);
        List<Bid> list = query.getResultList();
        entityManager.getTransaction().commit();

        list.forEach(entityManager::refresh);

        return list;
    }

    private List<Bid> retrieveBySubdepartment(BidType type, int year, long subdepartmentId) throws JDBCException {
        entityManager.getTransaction().begin();
        TypedQuery<Bid> query = entityManager.createQuery("select bm from Bid bm join bm.finances fd join fd.finances fm where EXTRACT(YEAR from fm.startDate) <= :requestedYear and EXTRACT(YEAR from fm.endDate) >= :requestedYear and bm.active = true and bm.type = :bidType and fd.subdepartment.id = :subdepartmentId order by coalesce(bm.modifiedDate, bm.createdDate) desc",
                Bid.class)
                .setParameter("requestedYear", year)
                .setParameter("bidType", type)
                .setParameter("subdepartmentId", subdepartmentId);
        List<Bid> list = query.getResultList();
        entityManager.getTransaction().commit();

        list.forEach(entityManager::refresh);

        return list;
    }

    private List<Bid> retrieveByDepartment(BidType type, int year, long departmentId) throws JDBCException {
        entityManager.getTransaction().begin();
        TypedQuery<Bid> query = entityManager.createQuery("select bm from Bid bm join bm.finances fd join fd.finances fm where EXTRACT(YEAR from fm.startDate) <= :requestedYear and EXTRACT(YEAR from fm.endDate) >= :requestedYear and bm.active = true and bm.type = :bidType and fd.subdepartment.department.id = :departmentId order by coalesce(bm.modifiedDate, bm.createdDate) desc",
                Bid.class)
                .setParameter("requestedYear", year)
                .setParameter("bidType", type)
                .setParameter("departmentId", departmentId);
        List<Bid> list = query.getResultList();
        entityManager.getTransaction().commit();

        list.forEach(entityManager::refresh);

        return list;
    }

    private List<Bid> retrieveByType(BidType type, int year) throws JDBCException {
        entityManager.getTransaction().begin();
        TypedQuery<Bid> query = entityManager.createQuery("select bm from Bid bm join bm.finances fd join fd.finances fm where EXTRACT(YEAR from fm.startDate) <= :requestedYear and EXTRACT(YEAR from fm.endDate) >= :requestedYear and bm.active = true and bm.type = :bidType order by coalesce(bm.modifiedDate, bm.createdDate) desc",
                Bid.class)
                .setParameter("requestedYear", year)
                .setParameter("bidType", type);
        List<Bid> list = query.getResultList();
        entityManager.getTransaction().commit();

        list.forEach(entityManager::refresh);

        return list;
    }

    private List<Bid> retrieveByYear(int year) throws JDBCException {
        entityManager.getTransaction().begin();
        TypedQuery<Bid> query = entityManager.createQuery("select bm from Bid bm join bm.finances fd join fd.finances fm where EXTRACT(YEAR from fm.startDate) <= :requestedYear and EXTRACT(YEAR from fm.endDate) >= :requestedYear and bm.active = true order by coalesce(bm.modifiedDate, bm.createdDate) desc",
                Bid.class)
                .setParameter("requestedYear", year);
        List<Bid> list = query.getResultList();
        entityManager.getTransaction().commit();

        list.forEach(entityManager::refresh);

        return list;
    }

    @Override
    @Deprecated
    public List<Bid> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Bid_.active), true));
        return super.getList();
    }

    public BigDecimal getTotalAmount(FinanceDepartment financeDepartmentModel, BidType bidType) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select sum(bm.amount * bm.onePrice) from Bid bm where bm.active = true and bm.type = :bidType and bm.finances = :finances")
                .setParameter("bidType", bidType)
                .setParameter("finances", financeDepartmentModel);
        BigDecimal totalAmount = (BigDecimal) query.getSingleResult();
        entityManager.getTransaction().commit();

        return totalAmount;
    }
}
