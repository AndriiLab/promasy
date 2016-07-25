package main.java.gui.bids.reports;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

public class BidsReportModel {
	private String headPosition;
	private String head;
	private String department;
	private String financeName;
	private String cpvCode;
	private String cpvUkr;
	private String orderDescription;
	private Timestamp orderDate;
	private String producer;
	private String catNum;
	private String supplier;
	private String packType;
	private BigDecimal onePrice;
	private int amount;
	private String departmentHead;
	private String personallyLiableEmpl;
	private String accountant;
	private String economist;
	
	

	public BidsReportModel(String headPosition, String head, String department, String financeName, String cpvCode,
			String cpvUkr, String orderDescription, Timestamp orderDate, String producer, String catNum,
			String supplier, String packType, BigDecimal onePrice, int amount, String departmentHead,
			String personallyLiableEmpl, String accountant, String economist) {
		super();
		this.headPosition = headPosition;
		this.head = head;
		this.department = department;
		this.financeName = financeName;
		this.cpvCode = cpvCode;
		this.cpvUkr = cpvUkr;
		this.orderDescription = orderDescription;
		this.orderDate = orderDate;
		this.producer = producer;
		this.catNum = catNum;
		this.supplier = supplier;
		this.packType = packType;
		this.onePrice = onePrice;
		this.amount = amount;
		this.departmentHead = departmentHead;
		this.personallyLiableEmpl = personallyLiableEmpl;
		this.accountant = accountant;
		this.economist = economist;
	}

	public String getHeadPosition() {
		return headPosition;
	}

	public String getHead() {
		return head;
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
		return onePrice;
	}

	public int getAmount() {
		return amount;
	}
	
	public String getDepartmentHead() {
		return departmentHead;
	}

	public String getPersonallyLiableEmpl() {
		return personallyLiableEmpl;
	}

	public String getAccountant() {
		return accountant;
	}

	public String getEconomist() {
		return economist;
	}

	public BigDecimal getTotalPrice(){
		return (onePrice.multiply(BigDecimal.valueOf(amount))).setScale(2, RoundingMode.CEILING);
	}

}
