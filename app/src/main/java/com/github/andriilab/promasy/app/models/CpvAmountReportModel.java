package com.github.andriilab.promasy.app.models;

import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.domain.bid.enums.ProcurementProcedure;
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

    public CpvAmountReportModel() {
    }

    public CpvAmountReportModel(CpvAmount entity) {
        // todo
        Set<Integer> kpkvkSet = new HashSet<>();
        StringJoiner kpkvk = new StringJoiner(", ");
        for (Bid bid : entity.getBids()) {
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
        return new CpvAmountReportModel(this, entity.getType().getBidTypeName(), entity.getCpv().getCpvId(), entity.getCpv().getCpvUkr(), kpkvk.toString(), entity.getCpv()totalAmount, procProcedure, Labels.getProperty("duringYear"), null);
    }
}
