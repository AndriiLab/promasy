package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Institute_;
import org.hibernate.JDBCException;

import java.util.List;

public class InstituteQueries extends SQLQueries<Institute> {

    public InstituteQueries() {
        super(Institute.class);
    }

    @Override
    public List<Institute> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Institute_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Institute_.instName)));
        return super.getList();
    }
}
