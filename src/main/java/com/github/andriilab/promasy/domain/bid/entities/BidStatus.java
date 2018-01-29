package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.bid.enums.Status;
import com.github.andriilab.promasy.domain.organization.entities.Employee;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * POJO for storing relation between {@link Status} and {@link Bid}
 */

@Entity
@Table(name = "bid_statuses")
public class BidStatus extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bid_id")
    private Bid bid;

    public BidStatus() {
    }

    public BidStatus(long modelId, Employee createdEmployee, Timestamp createdDate, Employee modifiedEmployee,
                     Timestamp modifiedDate, boolean active, Status status, Bid bid) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.status = status;
        this.bid = bid;
    }

    public BidStatus(Status status, Bid bid) {
        this.status = status;
        this.bid = bid;
    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public String getMessage() {
        return "statusWasSet";
    }

    @Override
    public String toString() {
        return status.getStatusDesc();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }
}
