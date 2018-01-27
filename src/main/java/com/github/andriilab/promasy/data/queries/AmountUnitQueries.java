package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit_;
import org.hibernate.JDBCException;

import java.util.List;

public class AmountUnitQueries extends SQLQueries<AmountUnit> {

    public AmountUnitQueries() {
        super(AmountUnit.class);
    }

    @Override
    public List<AmountUnit> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(AmountUnit_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(AmountUnit_.amUnitDesc)));
        return super.getList();
    }
}
