package com.github.andriilab.promasy.persistence.repositories;

import com.github.andriilab.promasy.domain.bid.entities.BidStatus;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

/**
 * Queries for {@link BidStatus}
 */
public class BidStatusRepository extends Repository<BidStatus> {

    public BidStatusRepository(EntityManager entityManager) {
        super(BidStatus.class, entityManager);
    }

    @Override
    public List<BidStatus> getResults() {
        return Collections.emptyList();
    }
}
