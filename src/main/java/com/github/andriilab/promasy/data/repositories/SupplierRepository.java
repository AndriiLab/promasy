package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.item.entities.Supplier;
import com.github.andriilab.promasy.domain.item.entities.Supplier_;
import org.hibernate.JDBCException;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * CRUD for {@link Supplier}
 */
public class SupplierRepository extends Repository<Supplier> {

    public SupplierRepository(EntityManager entityManager) {
        super(Supplier.class, entityManager);
    }

    @Override
    public List<Supplier> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Supplier_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Supplier_.supplierName)));
        return super.getList();
    }
}
