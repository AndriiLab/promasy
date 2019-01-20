package com.github.andriilab.promasy.data.models;

import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
}
