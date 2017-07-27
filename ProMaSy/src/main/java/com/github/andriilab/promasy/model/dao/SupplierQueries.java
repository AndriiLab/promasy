package com.github.andriilab.promasy.model.dao;

import com.github.andriilab.promasy.model.models.SupplierModel;
import com.github.andriilab.promasy.model.models.SupplierModel_;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD for {@link SupplierModel}
 */
public class SupplierQueries extends SQLQueries<SupplierModel> {

    SupplierQueries() {
        super(SupplierModel.class);
    }

    @Override
    public List<SupplierModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(SupplierModel_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(SupplierModel_.supplierName)));
        return super.getList();
    }
}
