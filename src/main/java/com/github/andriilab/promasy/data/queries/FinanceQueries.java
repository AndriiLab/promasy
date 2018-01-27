package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.Finance_;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

/**
 * CRUD class for finance data
 */
public class FinanceQueries extends SQLQueries<Finance> {

    public FinanceQueries() {
        super(Finance.class);
    }

    public List<Finance> retrieve(long departmentId) throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Finance_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get("department.modelId"), departmentId));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Finance_.financeNumber)));
        return super.getList();
    }

    public List<Finance> retrieveByDepartmentId(long departmentId) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select fm from Finance fm join fm.financeDepartmentModels fdm where fdm.subdepartment.department.modelId = :departmentId and fm.active = true order by fm.financeNumber");
        query.setParameter("departmentId", departmentId);
        List<Finance> list = query.getResultList();
        em.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Finance> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Finance_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Finance_.financeNumber)));
        return super.getList();
    }
}
