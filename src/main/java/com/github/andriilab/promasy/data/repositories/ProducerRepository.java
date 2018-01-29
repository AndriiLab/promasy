package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.domain.item.entities.Producer_;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * CRUD class for {@link Producer}
 */
public class ProducerRepository extends Repository<Producer> {

    public ProducerRepository(EntityManager entityManager) {
        super(Producer.class, entityManager);
    }

    @Override
    public List<Producer> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Producer_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Producer_.brandName)));
        return super.getList();
    }
}
