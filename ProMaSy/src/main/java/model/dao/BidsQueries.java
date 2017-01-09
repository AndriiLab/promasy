package model.dao;

import model.models.BidModel;
import model.models.BidModel_;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD for bid data
 */
public class BidsQueries extends SQLQueries<BidModel>{

    BidsQueries() {
        super(BidModel.class);
    }

    public List<BidModel> retrieve(long departmentId) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get("department.modelId"), departmentId));
        return super.getList();
    }

    public List<BidModel> retrieve(long departmentId, long orderId) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get("department.modelId"), departmentId));
        criteriaQuery.where(criteriaBuilder.equal(root.get("finances.modelId"), orderId));
        return super.getList();
    }

    @Override
    public List<BidModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.active), true));
        return super.getList();
    }
}
