package model.dao;

import model.models.FinanceModel;
import model.models.FinanceModel_;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD class for finance data
 */
public class FinanceQueries extends SQLQueries<FinanceModel> {

    FinanceQueries() {
        super(FinanceModel.class);
    }

    public List<FinanceModel> retrieve(long departmentId) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(FinanceModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get("department.modelId"), departmentId));
        return super.getList();
    }

    @Override
    public List<FinanceModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(FinanceModel_.active), true));
        return super.getList();
    }
}
