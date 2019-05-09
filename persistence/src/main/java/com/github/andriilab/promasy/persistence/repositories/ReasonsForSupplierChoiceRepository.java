package com.github.andriilab.promasy.persistence.repositories;

import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice;
import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice_;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * CRUD for {@link ReasonForSupplierChoice}
 */
public class ReasonsForSupplierChoiceRepository extends Repository<ReasonForSupplierChoice> {

    public ReasonsForSupplierChoiceRepository(EntityManager entityManager) {
        super(ReasonForSupplierChoice.class, entityManager);
    }

    @Override
    public List<ReasonForSupplierChoice> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(ReasonForSupplierChoice_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(ReasonForSupplierChoice_.description)));
        return super.getList();
    }
}
