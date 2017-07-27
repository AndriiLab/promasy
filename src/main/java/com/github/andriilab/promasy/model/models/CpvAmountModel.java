package com.github.andriilab.promasy.model.models;

import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.model.enums.BidType;
import com.github.andriilab.promasy.model.enums.ProcurementProcedure;
import com.github.andriilab.promasy.model.models.report.CpvAmountReportModel;

import java.math.BigDecimal;
import java.util.*;

/**
 * POJO for data of finance amount spent on each cpv code
 */
public class CpvAmountModel {

    private CPVModel cpv;
    private BidType type;
    private BigDecimal totalAmount;
    private final List<BidModel> bids = new LinkedList<>();

    private CpvAmountModel(CPVModel cpv, BidType type, BigDecimal totalAmount) {
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

    public CpvAmountReportModel generateCpvAmountReportModel() {
        Set<Integer> kpkvkSet = new HashSet<>();
        StringJoiner kpkvk = new StringJoiner(", ");
        for (BidModel bid : bids) {
            Integer kpkvkInt = bid.getFinances().getFinances().getKPKVK();
            if (kpkvkInt != null) {
                kpkvkSet.add(kpkvkInt);
            }
        }
        if (kpkvkSet.isEmpty()) {
            kpkvk.add(Labels.getProperty("default.kpkvk"));
        } else if (kpkvkSet.size() == 1) {
            kpkvk.add(kpkvkSet.iterator().next().toString());
        } else {
            while (kpkvkSet.iterator().hasNext()) {
                kpkvk.add(kpkvkSet.iterator().next().toString());
            }
        }

        String procProcedure;
        if (totalAmount.compareTo(BigDecimal.valueOf(50000)) > 0) {
            procProcedure = ProcurementProcedure.OPEN_AUCTION.toString();
        } else {
            procProcedure = ProcurementProcedure.BELOW_THRESHOLD.toString();
        }
        return new CpvAmountReportModel(this, type.getBidTypeName(), cpv.getCpvId(), cpv.getCpvUkr(), kpkvk.toString(), totalAmount, procProcedure, Labels.getProperty("duringYear"), null);
    }
}
