package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit_;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import java.util.List;

public class AmountUnitRepository extends Repository<AmountUnit> {

    public AmountUnitRepository(EntityManager entityManager) {
        super(AmountUnit.class, entityManager);
    }

    @Override
    public List<AmountUnit> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(AmountUnit_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(AmountUnit_.description)));
        return super.getList();
    }
}
