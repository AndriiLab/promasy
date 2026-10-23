package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentSpentAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentsQuery;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * CRUD for {@link FinanceDepartment}
 */
public class FinanceDepartmentRepository extends Repository<FinanceDepartment> {

    public FinanceDepartmentRepository(EntityManager entityManager) {
        super(FinanceDepartment.class, entityManager);
    }

    public List<FinanceDepartment> get(GetFinanceDepartmentsQuery query) {
        if (query.getSubdepartment().getModelId() != 0)
            return retrieveBySubdepartment(query.getSubdepartment(), query.getYear());
        else if (query.getFinance().getModelId() != 0)
            return retrieveByFinance(query.getFinance(), query.getYear());
        return Collections.emptyList();
    }

    private List<FinanceDepartment> retrieveByFinance(Finance finance, int year) {
        return inTransaction(() -> {
            TypedQuery<FinanceDepartment> query = entityManager.createQuery("select fdm from FinanceDepartment fdm join fdm.finances where fdm.finances = :finance and fdm.active = true and EXTRACT(YEAR from fdm.finances.startDate) <= :year and EXTRACT(YEAR from fdm.finances.endDate) >= :year order by fdm.subdepartment.department.depName, fdm.subdepartment.subdepName",
                    FinanceDepartment.class);
            query.setParameter("finance", finance);
            query.setParameter("year", year);
            return Collections.unmodifiableList(query.getResultList());
        });
    }

    private List<FinanceDepartment> retrieveBySubdepartment(Subdepartment subdepartment, int year) {
        return inTransaction(() -> {
            TypedQuery<FinanceDepartment> query = entityManager.createQuery("select fdm from FinanceDepartment fdm join fdm.subdepartment.department where fdm.subdepartment = :subdepartment and fdm.active = true and EXTRACT(YEAR from fdm.finances.startDate) <= :year and EXTRACT(YEAR from fdm.finances.endDate) >= :year order by fdm.finances.financeNumber, fdm.subdepartment.subdepName",
                    FinanceDepartment.class);
            query.setParameter("subdepartment", subdepartment);
            query.setParameter("year", year);
            return Collections.unmodifiableList(query.getResultList());
        });
    }

    @Override
    public List<FinanceDepartment> getResults() {
        return inTransaction(() -> {
            TypedQuery<FinanceDepartment> query = entityManager.createQuery("select fdm from FinanceDepartment fdm join fdm.subdepartment.department where fdm.active = true order by fdm.finances.financeNumber, fdm.subdepartment.department.depName, fdm.subdepartment.subdepName",
                    FinanceDepartment.class);
            return Collections.unmodifiableList(query.getResultList());
        });
    }

    public BigDecimal retrieveSpentAmount(GetFinanceDepartmentSpentAmountQuery query) {
        BigDecimal spentAmount = inTransaction(() -> {
            Query q = entityManager.createQuery("select sum(b.onePrice * b.amount) from Bid b where b.active = true and b.finances = :financeDepartment and b.type = :bidType");

            q.setParameter("financeDepartment", query.getModel())
                    .setParameter("bidType", query.getType());
            return (BigDecimal) q.getSingleResult();
        });

        return spentAmount != null ? spentAmount : BigDecimal.ZERO;
    }

    public BigDecimal retrieveLeftAmount(GetFinanceDepartmentLeftAmountQuery query) {
        BigDecimal totalAmount = query.getModel().getTotalAmount(query.getType());
        BigDecimal assignedAmount = retrieveSpentAmount(new GetFinanceDepartmentSpentAmountQuery(query.getModel(), query.getType()));

        return totalAmount.subtract(assignedAmount);
    }
}
