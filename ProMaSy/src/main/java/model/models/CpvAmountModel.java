package model.models;

import model.enums.BidType;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * POJO for data of finance amount spent on each cpv code
 */
public class CpvAmountModel {

    private CPVModel cpv;
    private BidType type;
    private BigDecimal totalAmount;
    private List<BidModel> bids = new LinkedList<>();

    public CpvAmountModel(CPVModel cpv, BidType type, BigDecimal totalAmount) {
        this.cpv = cpv;
        this.type = type;
        this.totalAmount = totalAmount;
    }

    public CpvAmountModel(CPVModel cpv, BidType type, BigDecimal totalAmount, BidModel model) {
        this(cpv, type, totalAmount);
        this.addBidModel(model);
    }

    public CpvAmountModel() {
    }

    public void addToTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = this.totalAmount.add(totalAmount);
    }

    public CPVModel getCpv() {
        return cpv;
    }

    public void setCpv(CPVModel cpv) {
        this.cpv = cpv;
    }

    public BidType getType() {
        return type;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<BidModel> getBids() {
        return bids;
    }

    public void addBidModel(BidModel model) {
        int indexOfModel = bids.indexOf(model);
        if (indexOfModel != -1) {
            bids.set(indexOfModel, model);
        } else {
            bids.add(model);
        }
    }

    @Override
    public String toString() {
        return "CpvAmountModel{" +
                "cpv=" + cpv.getCpvId() +
                ", type=" + type +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
