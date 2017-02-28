package model.models;

import gui.commons.Labels;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BidsReportModel {
	private String department;
    private String subdepartment;
    private String financeName;
    private String financeType;
    private String cpv;
    private String cpvUkr;
    private String orderDescription;
    private String orderDate;
    private String producer;
	private String catNum;
	private String supplier;
    private String reasonForSupplierChoice;
    private String packType;
	private BigDecimal onePrice;
	private int amount;

    public BidsReportModel(String department, String subdepartment, String financeName, String financeType, String cpv, String cpvUkr, String orderDescription, String orderDate, String producer, String catNum, String supplier, String reasonForSupplierChoice, String packType, BigDecimal onePrice, int amount) {
        this.department = department;
        this.subdepartment = subdepartment;
        this.financeName = financeName;
        this.financeType = financeType;
        this.cpv = cpv;
        this.cpvUkr = cpvUkr;
        this.orderDescription = orderDescription;
        this.orderDate = orderDate;
        if (producer == null || producer.equals(EmptyModel.STRING)) {
            this.producer = Labels.getProperty("any");
        } else {
            this.producer = producer;
        }
        this.catNum = catNum;
        if (supplier == null || supplier.equals(EmptyModel.STRING)) {
            this.supplier = Labels.getProperty("any");
        } else {
            this.supplier = supplier;
        }
        this.packType = packType;
        this.onePrice = onePrice;
        this.amount = amount;
        if (reasonForSupplierChoice == null) {
            this.reasonForSupplierChoice = EmptyModel.STRING;
        } else {
            this.reasonForSupplierChoice = reasonForSupplierChoice;
        }
    }

    public BidsReportModel() {
    }

    public String getDepartment() {
        return department;
    }

    public String getSubdepartment() {
        return subdepartment;
    }

    public String getFinanceName() {
        return financeName;
    }

    public String getFinanceType() {
        return financeType;
    }

    public String getCpv() {
        return cpv;
    }

    public String getCpvUkr() {
        return cpvUkr;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getProducer() {
        return producer;
    }

    public String getCatNum() {
        return catNum;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getReasonForSupplierChoice() {
        return reasonForSupplierChoice;
    }

    public String getPackType() {
        return packType;
    }

    public BigDecimal getOnePrice() {
        return onePrice;
    }

    public int getAmount() {
        return amount;
    }

    public BigDecimal getTotalPrice() {
        return (onePrice.multiply(BigDecimal.valueOf(amount))).setScale(2, RoundingMode.CEILING);
	}
}
