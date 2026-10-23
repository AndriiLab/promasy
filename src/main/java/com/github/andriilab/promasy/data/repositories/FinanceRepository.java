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
        return inTransaction(() -> {
            TypedQuery<Finance> query = entityManager.createQuery("select distinct fm from Finance fm join fm.financeDepartmentModels fdm where fdm.subdepartment.department.modelId = :departmentId and fdm.active = true and fm.active = true and EXTRACT(YEAR from fm.startDate) <= :year and EXTRACT(YEAR from fm.endDate) >= :year order by fm.financeNumber",
                    Finance.class);
            query.setParameter("departmentId", departmentId);
            query.setParameter("year", year);
            return Collections.unmodifiableList(query.getResultList());
        });
    }

    private List<Finance> retrieve(int year) {
        return inTransaction(() -> {
            TypedQuery<Finance> query = entityManager.createQuery("select fm from Finance fm where fm.active = true and EXTRACT(YEAR from fm.startDate) <= :year and EXTRACT(YEAR from fm.endDate) >= :year order by fm.financeNumber",
                    Finance.class);
            query.setParameter("year", year);
            return Collections.unmodifiableList(query.getResultList());
        });
    }

    public BigDecimal retrieveAssignedAmount(GetFinanceAssignedAmountQuery query) {
        String field;

        switch (query.getType()) {
            case MATERIALS:
                field = "totalMaterialsAmount";
                break;
            case EQUIPMENT:
                field = "totalEquipmentAmount";
                break;
            case SERVICES:
                field = "totalServicesAmount";
                break;
            default:
                return BigDecimal.ZERO;
        }

        return inTransaction(() -> {
            Query q = entityManager.createQuery("select sum(dfm." + field + ") from FinanceDepartment dfm where dfm.active = true and dfm.finances = :finances");
            q.setParameter("finances", query.getModel());
            return amountOrZero((BigDecimal) q.getSingleResult());
        });
    }

    public BigDecimal retrieveUnassignedAmount(GetFinanceUnassignedAmountQuery query) {
        BigDecimal totalAmount = query.getModel().getTotalAmount(query.getType());
        BigDecimal assignedAmount = retrieveAssignedAmount(new GetFinanceAssignedAmountQuery(query.getModel(), query.getType()));

        return totalAmount.subtract(assignedAmount);
    }

    public BigDecimal retrieveSpentAmount(GetFinanceSpentAmountQuery query) {
        return inTransaction(() -> {
            Query q = entityManager.createQuery("select sum(bd.onePrice * bd.amount) from Bid bd inner join FinanceDepartment fd on bd.finances = fd where bd.active = true and bd.type=:bidType and fd.finances=:finance");

            q.setParameter("finance", query.getModel())
                    .setParameter("bidType", query.getType());
            return amountOrZero((BigDecimal) q.getSingleResult());
        });
    }

    public BigDecimal retrieveLeftAmount(GetFinanceLeftAmountQuery query) {
        BigDecimal totalAmount = query.getModel().getTotalAmount(query.getType());
        BigDecimal spentAmount = retrieveSpentAmount(new GetFinanceSpentAmountQuery(query.getModel(), query.getType()));

        return totalAmount.subtract(spentAmount);
    }

    private static BigDecimal amountOrZero(BigDecimal amount) {
        return amount != null ? amount : BigDecimal.ZERO;
    }
}
