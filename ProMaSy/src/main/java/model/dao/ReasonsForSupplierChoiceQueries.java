package model.dao;

import model.models.ReasonForSupplierChoiceModel;
import model.models.ReasonForSupplierChoiceModel_;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD for {@link ReasonForSupplierChoiceModel}
 */
public class ReasonsForSupplierChoiceQueries extends SQLQueries<ReasonForSupplierChoiceModel> {

    ReasonsForSupplierChoiceQueries() {
        super(ReasonForSupplierChoiceModel.class);
    }

    @Override
    public List<ReasonForSupplierChoiceModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(ReasonForSupplierChoiceModel_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(ReasonForSupplierChoiceModel_.reason)));
        return super.getList();
    }
}
