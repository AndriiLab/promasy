package com.github.andriilab.promasy.model.dao;

import com.github.andriilab.promasy.model.models.AmountUnitsModel;
import com.github.andriilab.promasy.model.models.AmountUnitsModel_;

import java.sql.SQLException;
import java.util.List;

public class AmountUnitsQueries extends SQLQueries<AmountUnitsModel>{

    AmountUnitsQueries() {
        super(AmountUnitsModel.class);
    }

    @Override
    public List<AmountUnitsModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(AmountUnitsModel_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(AmountUnitsModel_.amUnitDesc)));
        return super.getList();
    }
}
