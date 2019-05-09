package com.github.andriilab.promasy.persistence.repositories;

import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment_;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class SubdepartmentRepository extends Repository<Subdepartment> {

    public SubdepartmentRepository(EntityManager entityManager) {
        super(Subdepartment.class, entityManager);
    }

    public List<Subdepartment> get(long departmentId) {
        entityManager.getTransaction().begin();
        TypedQuery<Subdepartment> query = entityManager.createQuery("select sdm from Subdepartment sdm join sdm.department where sdm.department.modelId = :departmentId and sdm.active = true order by sdm.subdepName", Subdepartment.class);
        query.setParameter("departmentId", departmentId);
        List<Subdepartment> list = query.getResultList();
        entityManager.getTransaction().commit();

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
