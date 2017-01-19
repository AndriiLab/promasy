package model.models;

import model.enums.Status;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * POJO for storing relation between {@link Status} and {@link BidModel}
 */

@Entity
@Table(name = "bid_statuses")
public class BidStatusModel<T extends BidModel> extends AbstractModel {

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = BidModel.class)
    @JoinColumn(name = "bid_id")
    private T bid;

    public BidStatusModel() {
    }

    public BidStatusModel(long modelId, EmployeeModel createdEmployee, Timestamp createdDate, EmployeeModel modifiedEmployee,
                          Timestamp modifiedDate, boolean active, Status status, T bid) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.status = status;
        this.bid = bid;
    }

    public BidStatusModel(Status status, T bid) {
        this.status = status;
        this.bid = bid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getBid() {
        return bid;
    }

    public void setBid(T bid) {
        this.bid = bid;
    }
}
