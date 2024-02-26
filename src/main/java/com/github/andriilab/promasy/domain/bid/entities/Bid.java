package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.domain.item.entities.Supplier;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * IEntity for storing data related to the bid
 */
@Entity
@Table(name = "bids")
public class Bid extends AbstractEntity {

    @Column(name = "bid_desc")
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    @Getter @Setter private Producer producer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "am_unit_id")
    @Getter @Setter private AmountUnit amountUnit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cpv_code")
    @Getter @Setter private Cpv cpv;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "finance_dep_id")
    @Getter @Setter private FinanceDepartment finances;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id")
    @Getter @Setter private Supplier supplier;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reason_id")
    @Getter @Setter private ReasonForSupplierChoice reasonForSupplierChoice;

    @OneToMany(mappedBy = "bid", cascade = CascadeType.PERSIST)
    @Getter @Setter private List<BidStatus> statuses = new ArrayList<>();

    @Column(name = "cat_num")
    @Getter @Setter private String catNum;

    @Column(name = "one_price")
    @Getter @Setter private BigDecimal onePrice;

    @Column(name = "amount")
    @Getter @Setter private int amount;

    @Transient
    @Setter private int transientAmount;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private BidType type;

    @Column(name = "kekv")
    private Integer kekv;

    @Column(name = "proc_start_date")
    @Getter @Setter private Date procurementStartDate;


    public Bid(long modelId, Employee createdBy, Timestamp createdDate, Employee modifiedBy, Timestamp modifiedDate,
               boolean active, Producer producer, String catNum, String description, Cpv cpv, BigDecimal onePrice,
               int amount, AmountUnit amountUnit, FinanceDepartment finances, Supplier supplier,
               List<BidStatus> statuses, ReasonForSupplierChoice reasonForSupplierChoice, BidType type,
               Date procurementStartDate) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.producer = producer;
        this.catNum = catNum;
        this.description = description;
        this.cpv = cpv;
        this.onePrice = onePrice;
        this.amount = amount;
        this.amountUnit = amountUnit;
        this.finances = finances;
        this.supplier = supplier;
        this.statuses = statuses;
        this.reasonForSupplierChoice = reasonForSupplierChoice;
        this.type = type;
        this.procurementStartDate = procurementStartDate;
    }

    public Bid(Producer producer, String catNum, String description, Cpv cpv, BigDecimal onePrice, int amount,
               AmountUnit amountUnit, FinanceDepartment finances, Supplier supplier, List<BidStatus> statuses,
               ReasonForSupplierChoice reasonForSupplierChoice, BidType type, Date procurementStartDate) {
        this.producer = producer;
        this.catNum = catNum;
        this.description = description;
        this.cpv = cpv;
        this.onePrice = onePrice;
        this.amount = amount;
        this.amountUnit = amountUnit;
        this.finances = finances;
        this.supplier = supplier;
        this.statuses = statuses;
        this.reasonForSupplierChoice = reasonForSupplierChoice;
        this.type = type;
        this.procurementStartDate = procurementStartDate;
    }

    public Bid() {
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public int getKEKV() {
        if (kekv == null) {
            return type.getKekv();
        } else {
            return kekv;
        }
    }

    public void setKEKV(Integer kekv) {
        if (!type.getKekv().equals(kekv)) {
            this.kekv = kekv;
        } else {
            this.kekv = null;
        }
    }

    public void addStatus(BidStatus bidStatusModel) {
        statuses.add(bidStatusModel);
        bidStatusModel.setBid(this);
    }

    public BigDecimal getTotalPrice() {
        return onePrice.multiply(new BigDecimal(amount));
    }

    public int getTransientAmount() {
        if (transientAmount == 0) {
            transientAmount = amount;
        }

        return transientAmount;
    }

    public void clearTransientAmount() {
        this.transientAmount = amount;
    }

    @Override
    public String toString() {
        return description;
    }

    public BidStatus getLastBidStatusModel() {
        return Collections.max(statuses, (thisModel, thatModel) -> {
            if (thisModel.getCreatedDate().after(thatModel.getCreatedDate())) {
                return 1;
            } else if (thisModel.getCreatedDate().before(thatModel.getCreatedDate())) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    @Override
    public String getMessage() {
        return "createOrUpdateUserBid";
    }

    @Override
    public void setActive(boolean active, Employee employee, Timestamp modifiedDate) {
        if(!active){
            statuses.forEach(s -> s.setActive(false, employee, modifiedDate));
        }
        super.setActive(active, employee, modifiedDate);
    }
}
