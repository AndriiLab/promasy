package model.dao;

import model.enums.BidType;
import model.models.BidModel;
import model.models.BidModel_;
import model.models.DepartmentModel;
import model.models.FinanceDepartmentModel;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD for bid data
 */
public class BidsQueries extends SQLQueries<BidModel> {

    BidsQueries() {
        super(BidModel.class);
    }

    public List<BidModel> retrieve(BidType type, FinanceDepartmentModel financeDepartment) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.type), type));
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.finances), financeDepartment));
        return super.getList();
    }

    public List<BidModel> retrieve(BidType type, DepartmentModel department) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.type), type));
        //todo
//        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.department), department));
        return super.getList();
    }

    public List<BidModel> getResults(BidType type) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.type), type));
        return super.getList();
    }

    @Override
    public List<BidModel> getResults() throws SQLException {
        return null;
    }
}
