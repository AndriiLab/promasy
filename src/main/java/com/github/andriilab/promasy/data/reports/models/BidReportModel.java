package com.github.andriilab.promasy.data.reports.models;

import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.app.commons.Labels;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

public class BidReportModel {
    @Getter private final String department;
    @Getter private final String subdepartment;
    @Getter private final String financeNumber;
    @Getter private final String financeName;
    @Getter private final String financeType;
    @Getter private final String cpv;
    @Getter private final String cpvUkr;
    @Getter private final String orderDescription;
    @Getter private final String orderDate;
    @Getter private final String producer;
    @Getter private final String catNum;
    @Getter private final String supplier;
    @Getter private final String reasonForSupplierChoice;
    @Getter private final String packType;
    @Getter private final BigDecimal onePrice;
    @Getter private final int amount;

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
