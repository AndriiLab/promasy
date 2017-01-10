package model.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for data of related to department finances
 */

@Entity
@Table(name = "finance_dep")
public class FinanceDepartmentModel extends AbstractModel {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "finance_id")
    private FinanceModel finances;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subdep_id")
    private SubdepartmentModel subdepartment;

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private List<BidModel> bids = new ArrayList<>();

    @Column(name = "finance_amount")
    private BigDecimal totalAmount;


    public FinanceDepartmentModel(long modelId, EmployeeModel createdEmployee, Timestamp createdDate, EmployeeModel modifiedEmployee, Timestamp modifiedDate, boolean active, FinanceModel finances, SubdepartmentModel subdepartment, List<BidModel> bids, BigDecimal totalAmount) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.finances = finances;
        this.subdepartment = subdepartment;
        this.bids = bids;
        this.totalAmount = totalAmount;
    }

    public FinanceDepartmentModel(FinanceModel model, SubdepartmentModel subdepartment, BigDecimal totalAmount) {
        this.finances = model;
        this.totalAmount = totalAmount;
        this.subdepartment = subdepartment;
    }

    public FinanceDepartmentModel() {

    }

    public List<BidModel> getBids() {
        return bids;
    }

    public void setBids(List<BidModel> bids) {
        this.bids = bids;
    }

    public FinanceModel getFinances() {
        return finances;
    }

    public void setFinances(FinanceModel finances) {
        this.finances = finances;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SubdepartmentModel getSubdepartment() {
        return subdepartment;
    }

    public void setSubdepartment(SubdepartmentModel subdepartment) {
        this.subdepartment = subdepartment;
    }

    public void addBid(BidModel model) {
        model.setFinances(this);
        int indexOfModel = bids.indexOf(model);
        // if model does exist, replace it with modified model (this is possible with overridden equals() and hashcode() in model)
        if (indexOfModel != -1) {
            bids.set(indexOfModel, model);
        } else {
            bids.add(model);
        }
    }

    public BigDecimal getLeftAmount() {
        BigDecimal leftAmount = totalAmount;
        for (BidModel bid : bids) {
            leftAmount = leftAmount.subtract(bid.getTotalPrice());
        }
        return leftAmount;
    }

    @Override
    public String toString() {
        if (finances == null) {
            return "";
        }
        return finances.toString();
    }
}
