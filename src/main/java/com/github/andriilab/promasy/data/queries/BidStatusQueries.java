package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.domain.bid.entities.BidStatus;

import java.util.List;

/**
 * Queries for {@link BidStatus}
 */
public class BidStatusQueries extends SQLQueries<BidStatus> {

    public BidStatusQueries() {
        super(BidStatus.class);
    }

    @Override
    public List<BidStatus> getResults() {
        return null;
    }
}
