package model;

import model.enums.Status;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Model for storing data related to the bid
 */
public class BidModel extends AbstractModel{

    private DepartmentModel department;
    private ProducerModel producer;
    private AmountUnitsModel amountUnit;
    private CPVModel cpv;
    private FinanceDepartmentModel finances;
    private SupplierModel supplier;
    private Status status;
    private ReasonForSupplierChoiceModel reasonForSupplierChoice;

    private String catNum;
    private String bidDesc;
    private BigDecimal onePrice;
    private int amount;

    public BidModel(long modelId, EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active, DepartmentModel department, ProducerModel producer, String catNum, String bidDesc, CPVModel cpv, BigDecimal onePrice, int amount, AmountUnitsModel amountUnit, FinanceDepartmentModel finances, SupplierModel supplier, Status status, ReasonForSupplierChoiceModel reasonForSupplierChoice) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.department = department;
        this.producer = producer;
        this.catNum = catNum;
        this.bidDesc = bidDesc;
        this.cpv = cpv;
        this.onePrice = onePrice;
        this.amount = amount;
        this.amountUnit = amountUnit;
        this.finances = finances;
        this.supplier = supplier;
        this.status = status;
        this.reasonForSupplierChoice = reasonForSupplierChoice;
    }

    public BidModel(DepartmentModel department, ProducerModel producer, String catNum, String bidDesc, CPVModel cpv, BigDecimal onePrice, int amount, AmountUnitsModel amountUnit, FinanceDepartmentModel finances, SupplierModel supplier, Status status, ReasonForSupplierChoiceModel reasonForSupplierChoice) {
        this.department = department;
        this.producer = producer;
        this.catNum = catNum;
        this.bidDesc = bidDesc;
        this.cpv = cpv;
        this.onePrice = onePrice;
        this.amount = amount;
        this.amountUnit = amountUnit;
        this.finances = finances;
        this.supplier = supplier;
        this.status = status;
        this.reasonForSupplierChoice = reasonForSupplierChoice;
    }

    public BidModel() {

    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }

    public ProducerModel getProducer() {
        return producer;
    }

    public void setProducer(ProducerModel producer) {
        this.producer = producer;
    }

    public String getCatNum() {
        return catNum;
    }

    public void setCatNum(String catNum) {
        this.catNum = catNum;
    }

    public String getBidDesc() {
        return bidDesc;
    }

    public void setBidDesc(String bidDesc) {
        this.bidDesc = bidDesc;
    }

    public CPVModel getCpv() {
        return cpv;
    }

    public void setCpv(CPVModel cpv) {
        this.cpv = cpv;
    }

    public BigDecimal getOnePrice() {
        return onePrice;
    }

    public void setOnePrice(BigDecimal onePrice) {
        this.onePrice = onePrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AmountUnitsModel getAmountUnit() {
        return amountUnit;
    }

    public void setAmountUnit(AmountUnitsModel amountUnit) {
        this.amountUnit = amountUnit;
    }

    public FinanceDepartmentModel getFinances() {
        return finances;
    }

    public void setFinances(FinanceDepartmentModel finances) {
        this.finances = finances;
    }

    public SupplierModel getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierModel supplier) {
        this.supplier = supplier;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ReasonForSupplierChoiceModel getReasonForSupplierChoice() {
        return reasonForSupplierChoice;
    }

    public void setReasonForSupplierChoice(ReasonForSupplierChoiceModel reasonForSupplierChoice) {
        this.reasonForSupplierChoice = reasonForSupplierChoice;
    }

    @Override
    public String toString() {
        return bidDesc;
    }

}
