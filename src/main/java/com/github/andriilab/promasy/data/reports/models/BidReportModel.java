package com.github.andriilab.promasy.data.reports.models;

import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.app.commons.Labels;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

public class BidReportModel {
    @Getter private String department;
    @Getter private String subdepartment;
    @Getter private String financeNumber;
    @Getter private String financeName;
    @Getter private String financeType;
    @Getter private String cpv;
    @Getter private String cpvUkr;
    @Getter private String orderDescription;
    @Getter private String orderDate;
    @Getter private String producer;
    @Getter private String catNum;
    @Getter private String supplier;
    @Getter private String reasonForSupplierChoice;
    @Getter private String packType;
    @Getter private BigDecimal onePrice;
    @Getter private int amount;

    public BidReportModel(Bid bid) {
        this(
                bid.getFinances().getSubdepartment().getDepartment().getDepName(),
                bid.getFinances().getSubdepartment().getSubdepName(),
                bid.getFinances().getFinances().getFinanceNumber(),
                bid.getFinances().getFinances().getFinanceName(),
                bid.getType().getBidTypeName(),
                bid.getCpv().getCpvId(),
                bid.getCpv().getCpvUkr(),
                bid.getDescription(),
                bid.getLastEditDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd\nHH:mm")),
                (bid.getProducer() != null ? bid.getProducer().getBrandName() : ""),
                (bid.getCatNum() != null ? bid.getCatNum() : ""),
                (bid.getSupplier() != null ? bid.getSupplier().getSupplierName() : ""),
                (bid.getReasonForSupplierChoice() != null ? bid.getReasonForSupplierChoice().getDescription() : ""),
                bid.getAmountUnit().getDescription(),
                bid.getOnePrice(),
                bid.getAmount()
        );
    }

    public BidReportModel(String department, String subdepartment, String financeNumber, String financeName, String financeType, String cpv, String cpvUkr, String orderDescription, String orderDate, String producer, String catNum, String supplier, String reasonForSupplierChoice, String packType, BigDecimal onePrice, int amount) {
        this.department = department;
        this.subdepartment = subdepartment;
        this.financeNumber = financeNumber;
        this.financeName = financeName;
        this.financeType = financeType;
        this.cpv = cpv;
        this.cpvUkr = cpvUkr;
        this.orderDescription = orderDescription;
        this.orderDate = orderDate;
        if (producer == null || producer.equals("")) {
            this.producer = Labels.getProperty("any");
        } else {
            this.producer = producer;
        }
        this.catNum = catNum;
        if (supplier == null || supplier.equals("")) {
            this.supplier = Labels.getProperty("any");
        } else {
            this.supplier = supplier;
        }
        this.packType = packType;
        this.onePrice = onePrice;
        this.amount = amount;
        if (reasonForSupplierChoice == null) {
            this.reasonForSupplierChoice = "";
        } else {
            this.reasonForSupplierChoice = reasonForSupplierChoice;
        }
    }

    public BigDecimal getTotalPrice() {
        return (onePrice.multiply(BigDecimal.valueOf(amount))).setScale(2, RoundingMode.CEILING);
    }
}
