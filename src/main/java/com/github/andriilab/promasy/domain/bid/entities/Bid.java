package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.domain.item.entities.Supplier;
import com.github.andriilab.promasy.domain.organization.entities.Employee;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * IEntity for storing data related to the bid
 */
@javax.persistence.Entity
@Table(name = "bids")
public class Bid extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "am_unit_id")
    private AmountUnit amountUnit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cpv_code")
    private Cpv cpv;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "finance_dep_id")
    private FinanceDepartment finances;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reason_id")
    private ReasonForSupplierChoice reasonForSupplierChoice;

    @OneToMany(mappedBy = "bid", cascade = CascadeType.PERSIST)
    private List<BidStatus> statuses = new ArrayList<>();

    @Column(name = "cat_num")
    private String catNum;

    @Column(name = "bid_desc")
    private String bidDesc;

    @Column(name = "one_price")
    private BigDecimal onePrice;

    @Column(name = "amount")
    private int amount;

    @Transient
    private int transientAmount;

    @Enumerated(EnumType.STRING)
    private BidType type;

    @Column(name = "kekv")
    private Integer kekv;

    @Column(name = "proc_start_date")
    private Date procurementStartDate;


    public Bid(long modelId, Employee createdBy, Timestamp createdDate, Employee modifiedBy, Timestamp modifiedDate, boolean active, Producer producer, String catNum, String bidDesc, Cpv cpv, BigDecimal onePrice, int amount, AmountUnit amountUnit, FinanceDepartment finances, Supplier supplier, List<BidStatus> statuses, ReasonForSupplierChoice reasonForSupplierChoice, BidType type, Date procurementStartDate) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.producer = producer;
        this.catNum = catNum;
        this.bidDesc = bidDesc;
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

    public Bid(Producer producer, String catNum, String bidDesc, Cpv cpv, BigDecimal onePrice, int amount, AmountUnit amountUnit, FinanceDepartment finances, Supplier supplier, List<BidStatus> statuses, ReasonForSupplierChoice reasonForSupplierChoice, BidType type, Date procurementStartDate) {
        this.producer = producer;
        this.catNum = catNum;
        this.bidDesc = bidDesc;
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
    public void setDescription(String description) {
        this.bidDesc = description;
    }

    public BidType getType() {
        return type;
    }

    public void setType(BidType type) {
        this.type = type;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String getCatNum() {
        return catNum;
    }

    public void setCatNum(String catNum) {
        this.catNum = catNum;
    }

    public String getBidDesc() {
        return bidDesc;
    }

    public void setBidDesc(String bidDesc) {
        this.bidDesc = bidDesc;
    }

    public Cpv getCpv() {
        return cpv;
    }

    public void setCpv(Cpv cpv) {
        this.cpv = cpv;
    }

    public BigDecimal getOnePrice() {
        return onePrice;
    }

    public void setOnePrice(BigDecimal onePrice) {
        this.onePrice = onePrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AmountUnit getAmountUnit() {
        return amountUnit;
    }

    public void setAmountUnit(AmountUnit amountUnit) {
        this.amountUnit = amountUnit;
    }

    public FinanceDepartment getFinances() {
        return finances;
    }

    public void setFinances(FinanceDepartment finances) {
        this.finances = finances;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<BidStatus> getStatuses() {
        return statuses;
    }

    public Date getProcurementStartDate() {
        return procurementStartDate;
    }

    public void setProcurementStartDate(Date procurementStartDate) {
        this.procurementStartDate = procurementStartDate;
    }

    public int getKEKV() {
        if (kekv == null) {
            return type.getKEKV();
        } else {
            return kekv;
        }
    }

    public void setKEKV(Integer kekv) {
        if (!type.getKEKV().equals(kekv)) {
            this.kekv = kekv;
        } else {
            this.kekv = null;
        }
    }

    public void addStatus(BidStatus bidStatusModel) {
        statuses.add(bidStatusModel);
        bidStatusModel.setBid(this);
    }

    public ReasonForSupplierChoice getReasonForSupplierChoice() {
        return reasonForSupplierChoice;
    }

    public void setReasonForSupplierChoice(ReasonForSupplierChoice reasonForSupplierChoice) {
        this.reasonForSupplierChoice = reasonForSupplierChoice;
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

    public void setTransientAmount(int transientAmount) {
        this.transientAmount = transientAmount;
    }

    public void clearTransientAmount() {
        this.transientAmount = amount;
    }

    @Override
    public String toString() {
        return bidDesc;
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
    public void setDeleted() {
        statuses.forEach(IEntity::setDeleted);
        super.setDeleted();
    }

    @Override
    public String getMessage() {
        return "createOrUpdateUserBid";
    }
}
