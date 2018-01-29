package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentSpentAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentsQuery;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;

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
        if (query.getDepartmentId() != 0)
            return retrieveByDepartmentId(query.getDepartmentId(), query.getYear());
        else if (query.getFinanceId() != 0)
            return retrieveByFinanceId(query.getFinanceId(), query.getYear());
        return null;
    }

    private List<FinanceDepartment> retrieveByFinanceId(long financeId, int year) {
        entityManager.getTransaction().begin();
        TypedQuery<FinanceDepartment> query = entityManager.createQuery("select fdm from FinanceDepartment fdm join fdm.finances where fdm.finances.modelId = :financeId and fdm.active = true and EXTRACT(YEAR from fdm.finances.startDate) <= :year and EXTRACT(YEAR from fdm.finances.endDate) >= :year order by fdm.subdepartment.department.depName, fdm.subdepartment.subdepName",
                FinanceDepartment.class);
        query.setParameter("financeId", financeId);
        query.setParameter("year", year);
        List<FinanceDepartment> list = query.getResultList();
        entityManager.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    private List<FinanceDepartment> retrieveByDepartmentId(long departmentId, int year) {
        entityManager.getTransaction().begin();
        TypedQuery<FinanceDepartment> query = entityManager.createQuery("select fdm from FinanceDepartment fdm join fdm.subdepartment.department where fdm.subdepartment.department.modelId = :departmentId and fdm.active = true and EXTRACT(YEAR from fdm.finances.startDate) <= :year and EXTRACT(YEAR from fdm.finances.endDate) >= :year order by fdm.finances.financeNumber, fdm.subdepartment.subdepName",
                FinanceDepartment.class);
        query.setParameter("departmentId", departmentId);
        query.setParameter("year", year);
        List<FinanceDepartment> list = query.getResultList();
        entityManager.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<FinanceDepartment> getResults() {
        entityManager.getTransaction().begin();
        TypedQuery<FinanceDepartment> query = entityManager.createQuery("select fdm from FinanceDepartment fdm join fdm.subdepartment.department where fdm.active = true order by fdm.finances.financeNumber, fdm.subdepartment.department.depName, fdm.subdepartment.subdepName",
                FinanceDepartment.class);
        List<FinanceDepartment> list = query.getResultList();
        entityManager.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    public BigDecimal retrieveSpentAmount(GetFinanceDepartmentSpentAmountQuery query) {
        entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("select sum(b.onePrice * b.amount) from Bid b where b.active = true and b.finances = :financeDepartment and b.type = :bidType");

        q.setParameter("financeDepartment", query.getModel())
                .setParameter("bidType", query.getType());
        BigDecimal assignedAmount = (BigDecimal) q.getSingleResult();
        entityManager.getTransaction().commit();

        if (assignedAmount == null) {
            assignedAmount = BigDecimal.ZERO;
        }

        return assignedAmount;
    }

    public BigDecimal retrieveLeftAmount(GetFinanceDepartmentLeftAmountQuery query) {
        BigDecimal totalAmount = query.getModel().getTotalAmount(query.getType());
        BigDecimal assignedAmount = retrieveSpentAmount(new GetFinanceDepartmentSpentAmountQuery(query.getModel(), query.getType()));

        return totalAmount.subtract(assignedAmount);
    }
}
