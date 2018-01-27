package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice;
import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice_;
import org.hibernate.JDBCException;

import java.util.List;

/**
 * CRUD for {@link ReasonForSupplierChoice}
 */
public class ReasonsForSupplierChoiceQueries extends SQLQueries<ReasonForSupplierChoice> {

    public ReasonsForSupplierChoiceQueries() {
        super(ReasonForSupplierChoice.class);
    }

    @Override
    public List<ReasonForSupplierChoice> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(ReasonForSupplierChoice_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(ReasonForSupplierChoice_.reason)));
        return super.getList();
    }
}
