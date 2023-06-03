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

    public CpvAmountReportModel(CpvAmount amount) {
        Set<String> kpkvkSet = new HashSet<>();
        for (Bid bid : amount.getBids()) {
            Integer kpkvkInt = bid.getFinances().getFinances().getKpkvk();
            kpkvkSet.add(kpkvkInt != null ? kpkvkInt.toString() : Labels.getProperty("default.kpkvk"));
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
        this.kpkvk = String.join(" ,", kpkvkSet);
        this.totalPrice =  amount.getTotalAmount();
        this.procurementProcedure = procProcedure;
        this.startDate = Labels.getProperty("duringYear");
        this.notation = null;
    }
}
