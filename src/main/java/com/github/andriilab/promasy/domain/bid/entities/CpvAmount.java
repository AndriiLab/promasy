package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.data.models.CpvAmountReportModel;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.bid.enums.ProcurementProcedure;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.presentation.commons.Labels;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

/**
 * POJO for data of finance amount spent on each cpv code
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

    public CpvAmountReportModel generateCpvAmountReportModel() {
        Set<Integer> kpkvkSet = new HashSet<>();
        StringJoiner kpkvk = new StringJoiner(", ");
        for (Bid bid : bids) {
            Integer kpkvkInt = bid.getFinances().getFinances().getKpkvk();
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
