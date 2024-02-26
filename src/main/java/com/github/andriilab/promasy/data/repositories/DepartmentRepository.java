package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Department_;
import org.hibernate.JDBCException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class DepartmentRepository extends Repository<Department> {

    public DepartmentRepository(EntityManager entityManager) {
        super(Department.class, entityManager);
    }

    public List<Department> get(long instituteId) {
        entityManager.getTransaction().begin();
        TypedQuery<Department> query = entityManager.createQuery("select dm from Department dm join dm.institute where dm.institute.modelId = :instituteId and dm.active = true order by dm.depName",
                Department.class);
        query.setParameter("instituteId", instituteId);
        List<Department> list = query.getResultList();
        entityManager.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Department> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Department_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Department_.depName)));
        return super.getList();
    }
}
