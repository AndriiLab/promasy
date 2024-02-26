package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Institute_;
import org.hibernate.JDBCException;

import jakarta.persistence.EntityManager;
import java.util.List;

public class InstituteRepository extends Repository<Institute> {

    public InstituteRepository(EntityManager entityManager) {
        super(Institute.class, entityManager);
    }

    @Override
    public List<Institute> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Institute_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Institute_.instName)));
        return super.getList();
    }
}
