package com.github.andriilab.promasy.data.models;

import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * IEntity for report table with information about procurements by CPV code
 */
public class CpvAmountReportModel {
    private @Getter
    @Setter
    String bidType;
    private @Getter
    @Setter
    String cpvNumber;
    private @Getter
    @Setter
    String cpvName;
    private @Getter
    @Setter
    String kpkvk;
    private @Getter
    @Setter
    BigDecimal totalPrice;
    private @Getter
    @Setter
    String procurementProcedure;
    private @Getter
    @Setter
    String startDate;
    private @Getter
    @Setter
    String notation;
    private @Getter
    @Setter
    CpvAmount parentModel;

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
