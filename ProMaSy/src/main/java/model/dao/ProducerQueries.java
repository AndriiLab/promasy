package model.dao;

import model.models.ProducerModel;
import model.models.ProducerModel_;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD class for {@link ProducerModel}
 */
public class ProducerQueries extends SQLQueries<ProducerModel> {

    ProducerQueries() {
        super(ProducerModel.class);
    }

    @Override
    public List<ProducerModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(ProducerModel_.active), true));
        return super.getList();
    }
}
