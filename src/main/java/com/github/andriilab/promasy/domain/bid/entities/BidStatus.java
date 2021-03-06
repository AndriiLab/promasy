package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.bid.enums.Status;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * POJO for storing relation between {@link Status} and {@link Bid}
 */

@Entity
@Table(name = "bid_statuses")
public class BidStatus extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Getter @Setter private Status status;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bid_id")
    @Getter @Setter private Bid bid;

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
    public String getDescription() {
        return null;
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
}
