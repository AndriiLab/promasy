package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment_;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class SubdepartmentQueries extends SQLQueries<Subdepartment> {

    public SubdepartmentQueries() {
        super(Subdepartment.class);
    }

    public List<Subdepartment> retrieve(long departmentId) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<Subdepartment> query = em.createQuery("select sdm from Subdepartment sdm join sdm.department where sdm.department.modelId = :departmentId and sdm.active = true order by sdm.subdepName", Subdepartment.class);
        query.setParameter("departmentId", departmentId);
        List<Subdepartment> list = query.getResultList();
        em.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Subdepartment> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Subdepartment_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Subdepartment_.subdepName)));
        return super.getList();
    }
}
