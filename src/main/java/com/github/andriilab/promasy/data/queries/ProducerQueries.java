package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.domain.item.entities.Producer_;
import org.hibernate.JDBCException;

import java.util.List;

/**
 * CRUD class for {@link Producer}
 */
public class ProducerQueries extends SQLQueries<Producer> {

    public ProducerQueries() {
        super(Producer.class);
    }

    @Override
    public List<Producer> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Producer_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Producer_.brandName)));
        return super.getList();
    }
}
