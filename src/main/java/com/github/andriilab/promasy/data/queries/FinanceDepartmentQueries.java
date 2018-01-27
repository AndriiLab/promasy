package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
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
public class FinanceDepartmentQueries extends SQLQueries<FinanceDepartment> {

    public FinanceDepartmentQueries() {
        super(FinanceDepartment.class);
    }

    public List<FinanceDepartment> retrieveByFinanceId(long financeID) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<FinanceDepartment> query = em.createQuery("select fdm from FinanceDepartment fdm join fdm.finances where fdm.finances.modelId = :financeId and fdm.active = true order by fdm.subdepartment.department.depName, fdm.subdepartment.subdepName",
                FinanceDepartment.class);
        query.setParameter("financeId", financeID);
        List<FinanceDepartment> list = query.getResultList();
        em.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    public List<FinanceDepartment> retrieveByDepartmentId(long departmentId) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<FinanceDepartment> query = em.createQuery("select fdm from FinanceDepartment fdm join fdm.subdepartment.department where fdm.subdepartment.department.modelId = :departmentId and fdm.active = true order by fdm.finances.financeNumber, fdm.subdepartment.subdepName",
                FinanceDepartment.class);
        query.setParameter("departmentId", departmentId);
        List<FinanceDepartment> list = query.getResultList();
        em.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<FinanceDepartment> getResults() {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<FinanceDepartment> query = em.createQuery("select fdm from FinanceDepartment fdm join fdm.subdepartment.department where fdm.active = true order by fdm.finances.financeNumber, fdm.subdepartment.department.depName, fdm.subdepartment.subdepName",
                FinanceDepartment.class);
        List<FinanceDepartment> list = query.getResultList();
        em.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    public BigDecimal getTotalAmount(Finance model, BidType type) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        Query query;
        switch (type) {
            case MATERIALS:
                query = em.createQuery("select sum(dfm.totalMaterialsAmount) from FinanceDepartment dfm where dfm.active = true and dfm.finances = :finances");
                break;
            case EQUIPMENT:
                query = em.createQuery("select sum(dfm.totalEquipmentAmount) from FinanceDepartment dfm where dfm.active = true and dfm.finances = :finances");
                break;
            case SERVICES:
                query = em.createQuery("select sum(dfm.totalServicesAmount) from FinanceDepartment dfm where dfm.active = true and dfm.finances = :finances");
                break;
            default:
                return BigDecimal.ZERO;
        }
        query.setParameter("finances", model);
        BigDecimal totalAmount = (BigDecimal) query.getSingleResult();
        em.getTransaction().commit();

        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        return totalAmount;
    }
}
