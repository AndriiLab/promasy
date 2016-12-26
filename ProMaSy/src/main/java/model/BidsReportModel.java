package main.java.model;

import main.java.gui.Labels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

public class BidsReportModel {
	private String department;
	private String financeName;
	private String cpvCode;
	private String cpvUkr;
	private String orderDescription;
	private Timestamp orderDate;
	private String producer;
	private String catNum;
	private String supplier;
    private String reasonForSupplierChoice;
    private String packType;
	private BigDecimal onePrice;
	private int amount;

	public BidsReportModel(String department, String financeName, String cpvCode,
                           String cpvUkr, String orderDescription, Timestamp orderDate, String producer, String catNum,
                           String supplier, String packType, BigDecimal onePrice, int amount, String reasonForSupplierChoice) {
        super();
        this.department = department;
        this.financeName = financeName;
        this.cpvCode = cpvCode;
        this.cpvUkr = cpvUkr;
        this.orderDescription = orderDescription;
        this.orderDate = orderDate;
        if (producer == null) {
            this.producer = Labels.getProperty("any");
        } else {
            this.producer = producer;
        }
        this.catNum = catNum;
        if (supplier == null) {
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

    public BidsReportModel() {

    }

    public String getReasonForSupplierChoice() {
        return reasonForSupplierChoice;
    }

	public String getDepartment() {
		return department;
	}

	public String getFinanceName() {
		return financeName;
	}

	public String getCpvCode() {
		return cpvCode;
	}

	public String getCpvUkr() {
		return cpvUkr;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public Timestamp getOrderDate() {
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

	public String getPackType() {
		return packType;
	}

	public BigDecimal getOnePrice() {
		return onePrice.setScale(2, RoundingMode.CEILING);
	}

	public int getAmount() {
		return amount;
	}
	
	public BigDecimal getTotalPrice(){
		return (onePrice.multiply(BigDecimal.valueOf(amount))).setScale(2, RoundingMode.CEILING);
	}
}
