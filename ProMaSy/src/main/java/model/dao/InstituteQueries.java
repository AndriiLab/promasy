package model.dao;

import model.models.InstituteModel;
import model.models.InstituteModel_;

import java.sql.SQLException;
import java.util.List;

public class InstituteQueries extends SQLQueries<InstituteModel>{

    InstituteQueries() {
        super(InstituteModel.class);
    }

    @Override
    public List<InstituteModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(InstituteModel_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(InstituteModel_.instName)));
        return super.getList();
    }
}
