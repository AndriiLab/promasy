package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * POJO for persistence of finance amount spent on each cpv code
 */
public class CpvAmount {

    @Getter private final List<Bid> bids = new LinkedList<>();
    @Getter @Setter private BidType type;
    @Getter @Setter private Cpv cpv;
    private BigDecimal totalAmount;

    private CpvAmount(Cpv cpv, BidType type, BigDecimal totalAmount) {
        this.cpv = cpv;
        this.type = type;
        this.totalAmount = totalAmount;
    }

    public CpvAmount(Cpv cpv, BidType type, BigDecimal totalAmount, Bid model) {
        this(cpv, type, totalAmount);
        this.addBidModel(model);
    }

    public CpvAmount() {
    }

    public void addToTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = this.totalAmount.add(totalAmount);
    }

    public void addBidModel(Bid model) {
        int indexOfModel = bids.indexOf(model);
        if (indexOfModel != -1) {
            bids.set(indexOfModel, model);
        } else {
            bids.add(model);
        }
    }

    @Override
    public String toString() {
        return "CpvAmount{" +
                "cpv=" + cpv.getCpvId() +
                ", type=" + type +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
