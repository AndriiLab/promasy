package com.github.andriilab.promasy.data.reports.models;

import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.domain.bid.enums.ProcurementProcedure;
import com.github.andriilab.promasy.app.commons.Labels;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * IEntity for report table with information about procurements by CPV code
 */
public class CpvAmountReportModel {
    @Getter @Setter private String bidType;
    @Getter @Setter private String cpvNumber;
    @Getter @Setter private String cpvName;
    @Getter @Setter private String kpkvk;
    @Getter @Setter private BigDecimal totalPrice;
    @Getter @Setter private String procurementProcedure;
    @Getter @Setter private String startDate;
    @Getter @Setter private String notation;
    @Getter @Setter private CpvAmount parentModel;

    public CpvAmountReportModel(CpvAmount parentModel, String bidType, String cpvNumber, String cpvName, String kpkvk, BigDecimal totalPrice, String procurementProcedure, String startDate, String notation) {
        this.parentModel = parentModel;
        this.bidType = bidType;
        this.cpvNumber = cpvNumber;
        this.cpvName = cpvName;
        this.kpkvk = kpkvk;
        this.totalPrice = totalPrice;
        this.procurementProcedure = procurementProcedure;
        this.startDate = startDate;
        this.notation = notation;
    }

    public CpvAmountReportModel(CpvAmount amount) {
        Set<Integer> kpkvkSet = new HashSet<>();
        StringJoiner kpkvkBuilder = new StringJoiner(", ");
        for (Bid bid : amount.getBids()) {
            Integer kpkvkInt = bid.getFinances().getFinances().getKpkvk();
            if (kpkvkInt != null) {
                kpkvkSet.add(kpkvkInt);
            }
        }
        if (kpkvkSet.isEmpty()) {
            kpkvkBuilder.add(Labels.getProperty("default.kpkvk"));
        } else if (kpkvkSet.size() == 1) {
            kpkvkBuilder.add(kpkvkSet.iterator().next().toString());
        } else {
            while (kpkvkSet.iterator().hasNext()) {
                kpkvkBuilder.add(kpkvkSet.iterator().next().toString());
            }
        }

        String procProcedure;
        if (amount.getTotalAmount().compareTo(BigDecimal.valueOf(50000)) > 0) {
            procProcedure = ProcurementProcedure.OPEN_AUCTION.toString();
        } else {
            procProcedure = ProcurementProcedure.BELOW_THRESHOLD.toString();
        }

        this.parentModel = amount;
        this.bidType = amount.getType().getBidTypeName();
        this.cpvNumber = amount.getCpv().getCpvId();
        this.cpvName = amount.getCpv().getCpvUkr();
        this.kpkvk =  kpkvkBuilder.toString();
        this.totalPrice =  amount.getTotalAmount();
        this.procurementProcedure = procProcedure;
        this.startDate = Labels.getProperty("duringYear");
        this.notation = null;
    }
}
