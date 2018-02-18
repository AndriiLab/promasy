package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.finance.*;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.Finance_;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * CRUD class for finance data
 */
public class FinanceRepository extends Repository<Finance> {

    public FinanceRepository(EntityManager entityManager) {
        super(Finance.class, entityManager);
    }

    @Override
    public List<Finance> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Finance_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Finance_.financeNumber)));
        return super.getList();
    }

    public List<Finance> get(GetFinancesQuery query) {
        if (query.getDepartmentId() != 0)
            return retrieve(query.getDepartmentId(), query.getYear());
        return retrieve(query.getYear());
    }

    private List<Finance> retrieve(long departmentId, int year) {
        entityManager.getTransaction().begin();
        TypedQuery<Finance> query = entityManager.createQuery("select fm from Finance fm join fm.financeDepartmentModels fdm where fdm.subdepartment.department.modelId = :departmentId and fm.active = true and EXTRACT(YEAR from fm.startDate) <= :year and EXTRACT(YEAR from fm.endDate) >= :year order by fm.financeNumber",
                Finance.class);
        query.setParameter("departmentId", departmentId);
        query.setParameter("year", year);
        List<Finance> list = query.getResultList();
        entityManager.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    private List<Finance> retrieve(int year) {
        entityManager.getTransaction().begin();
        TypedQuery<Finance> query = entityManager.createQuery("select fm from Finance fm where fm.active = true and EXTRACT(YEAR from fm.startDate) >= :year and EXTRACT(YEAR from fm.endDate) <= :year order by fm.financeNumber",
                Finance.class);
        query.setParameter("year", year);
        List<Finance> list = query.getResultList();
        entityManager.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    public BigDecimal retrieveAssignedAmount(GetFinanceAssignedAmountQuery query) {
        entityManager.getTransaction().begin();
        Query q;

        switch (query.getType()) {
            case MATERIALS:
                q = entityManager.createQuery("select sum(dfm.totalMaterialsAmount) from FinanceDepartment dfm where dfm.active = true and dfm.finances = :finances");
                break;
            case EQUIPMENT:
                q = entityManager.createQuery("select sum(dfm.totalEquipmentAmount) from FinanceDepartment dfm where dfm.active = true and dfm.finances = :finances");
                break;
            case SERVICES:
                q = entityManager.createQuery("select sum(dfm.totalServicesAmount) from FinanceDepartment dfm where dfm.active = true and dfm.finances = :finances");
                break;
            default:
                return BigDecimal.ZERO;
        }

        q.setParameter("finances", query.getModel());
        BigDecimal assignedAmount = (BigDecimal) q.getSingleResult();
        entityManager.getTransaction().commit();

        return assignedAmount != null ? assignedAmount : BigDecimal.ZERO;
    }

    public BigDecimal retrieveUnassignedAmount(GetFinanceUnassignedAmountQuery query) {
        BigDecimal totalAmount = query.getModel().getTotalAmount(query.getType());
        BigDecimal assignedAmount = retrieveAssignedAmount(new GetFinanceAssignedAmountQuery(query.getModel(), query.getType()));

        return totalAmount.subtract(assignedAmount);
    }

    public BigDecimal retrieveSpentAmount(GetFinanceSpentAmountQuery query) {
        entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("select sum(bd.onePrice * bd.amount) from Bid bd inner join FinanceDepartment fd on bd.finances = fd where bd.active = true and bd.type=:bidType and fd.finances=:finance");

        q.setParameter("finance", query.getModel())
                .setParameter("bidType", query.getType());
        BigDecimal assignedAmount = (BigDecimal) q.getSingleResult();
        entityManager.getTransaction().commit();

        return assignedAmount != null ? assignedAmount : BigDecimal.ZERO;
    }

    public BigDecimal retrieveLeftAmount(GetFinanceLeftAmountQuery query) {
        BigDecimal totalAmount = query.getModel().getTotalAmount(query.getType());
        BigDecimal spentAmount = retrieveSpentAmount(new GetFinanceSpentAmountQuery(query.getModel(), query.getType()));

        return totalAmount.subtract(spentAmount);
    }
}
