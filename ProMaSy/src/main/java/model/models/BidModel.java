package model.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Model for storing data related to the bid
 */
@Entity
@Table(name = "bids")
public class BidModel extends AbstractModel{

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dep_id")
    private DepartmentModel department;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private ProducerModel producer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "am_unit_id")
    private AmountUnitsModel amountUnit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cpv_code")
    private CPVModel cpv;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "finance_dep_id")
    private FinanceDepartmentModel finances;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id")
    private SupplierModel supplier;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reason_id")
    private ReasonForSupplierChoiceModel reasonForSupplierChoice;

    @OneToMany(mappedBy = "bid", cascade = CascadeType.PERSIST)
    private List<BidStatusModel> statuses;

    @Column(name = "cat_num")
    private String catNum;

    @Column(name = "bid_desc")
    private String bidDesc;

    @Column(name = "one_price")
    private BigDecimal onePrice;

    @Column(name = "amount")
    private int amount;

    public BidModel(long modelId, EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active, DepartmentModel department, ProducerModel producer, String catNum, String bidDesc, CPVModel cpv, BigDecimal onePrice, int amount, AmountUnitsModel amountUnit, FinanceDepartmentModel finances, SupplierModel supplier, List<BidStatusModel> statuses, ReasonForSupplierChoiceModel reasonForSupplierChoice) {
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
        this.statuses = statuses;
        this.reasonForSupplierChoice = reasonForSupplierChoice;
    }

    public BidModel(DepartmentModel department, ProducerModel producer, String catNum, String bidDesc, CPVModel cpv, BigDecimal onePrice, int amount, AmountUnitsModel amountUnit, FinanceDepartmentModel finances, SupplierModel supplier, List<BidStatusModel> statuses, ReasonForSupplierChoiceModel reasonForSupplierChoice) {
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
        this.statuses = statuses;
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

    public FinanceDepartmentModel getDepartmrntFinances() {
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

    public List<BidStatusModel> getStatuses() {
        return statuses;
    }

    public void addStatus(BidStatusModel bidStatusModel) {
        statuses.add(bidStatusModel);
        bidStatusModel.setBid(this);
    }

    public ReasonForSupplierChoiceModel getReasonForSupplierChoice() {
        return reasonForSupplierChoice;
    }

    public void setReasonForSupplierChoice(ReasonForSupplierChoiceModel reasonForSupplierChoice) {
        this.reasonForSupplierChoice = reasonForSupplierChoice;
    }

    public BigDecimal getTotalPrice() {
        return onePrice.multiply(new BigDecimal(amount));
    }

    @Override
    public String toString() {
        return bidDesc;
    }

    public BidStatusModel getLastBidStatusModel() {
        BidStatusModel bidStatusModel = statuses.get(0);
        for (BidStatusModel model : statuses) {
            if (model.getCreatedDate().after(bidStatusModel.getCreatedDate())) {
                bidStatusModel = model;
            }
        }
        return bidStatusModel;
    }
}