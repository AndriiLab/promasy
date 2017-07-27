package com.github.andriilab.promasy.model.dao;

import com.github.andriilab.promasy.model.models.BidStatusModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Queries for {@link BidStatusModel}
 */
public class BidStatusQueries extends SQLQueries<BidStatusModel> {

    BidStatusQueries() {
        super(BidStatusModel.class);
    }

    @Override
    public List<BidStatusModel> getResults() throws SQLException {
        return null;
    }
}
