package model.models.report;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Model for report table with information about procurements by CPV code
 */
public class CpvAmountReportModel {
    private String bidType;
    private String cpvNumber;
    private String cpvName;
    private String kpkvk;
    private BigDecimal totalPrice;
    private String procurementProcedure;
    private Date startDate;
    private String notation;

    public CpvAmountReportModel(String bidType, String cpvNumber, String cpvName, String kpkvk, BigDecimal totalPrice, String procurementProcedure, Date startDate, String notation) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
