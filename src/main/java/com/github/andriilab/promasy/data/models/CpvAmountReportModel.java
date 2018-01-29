package com.github.andriilab.promasy.data.models;

import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;

import java.math.BigDecimal;

/**
 * IEntity for report table with information about procurements by CPV code
 */
public class CpvAmountReportModel {
    private String bidType;
    private String cpvNumber;
    private String cpvName;
    private String kpkvk;
    private BigDecimal totalPrice;
    private String procurementProcedure;
    private String startDate;
    private String notation;
    private CpvAmount parentModel;

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

    public String getBidType() {
        return bidType;
    }

    public void setBidType(String bidType) {
        this.bidType = bidType;
    }

    public String getCpvNumber() {
        return cpvNumber;
    }

    public void setCpvNumber(String cpvNumber) {
        this.cpvNumber = cpvNumber;
    }

    public String getCpvName() {
        return cpvName;
    }

    public void setCpvName(String cpvName) {
        this.cpvName = cpvName;
    }

    public String getKpkvk() {
        return kpkvk;
    }

    public void setKpkvk(String kpkvk) {
        this.kpkvk = kpkvk;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProcurementProcedure() {
        return procurementProcedure;
    }

    public void setProcurementProcedure(String procurementProcedure) {
        this.procurementProcedure = procurementProcedure;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public CpvAmount getParentModel() {
        return parentModel;
    }

    public void setParentModel(CpvAmount parentModel) {
        this.parentModel = parentModel;
    }
}
