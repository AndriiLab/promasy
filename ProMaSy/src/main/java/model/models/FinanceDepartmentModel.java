package model.models;

import model.enums.BidType;

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

    @Column(name = "total_materials")
    private BigDecimal totalMaterialsAmount;

    @Column(name = "total_eqipment")
    private BigDecimal totalEqupmentAmount;

    @Column(name = "total_services")
    private BigDecimal totalServicesAmount;

    public FinanceDepartmentModel(long modelId, EmployeeModel createdEmployee, Timestamp createdDate, EmployeeModel modifiedEmployee, Timestamp modifiedDate, boolean active, FinanceModel finances, SubdepartmentModel subdepartment, List<BidModel> bids, BigDecimal totalMaterialsAmount, BigDecimal totalEqupmentAmount, BigDecimal totalServicesAmount) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.finances = finances;
        this.subdepartment = subdepartment;
        this.bids = bids;
        this.totalMaterialsAmount = totalMaterialsAmount;
        this.totalEqupmentAmount = totalEqupmentAmount;
        this.totalServicesAmount = totalServicesAmount;
    }

    public FinanceDepartmentModel(FinanceModel model, SubdepartmentModel subdepartment, BigDecimal totalMaterialsAmount, BigDecimal totalEqupmentAmount, BigDecimal totalServicesAmount) {
        this.finances = model;
        this.subdepartment = subdepartment;
        this.totalMaterialsAmount = totalMaterialsAmount;
        this.totalEqupmentAmount = totalEqupmentAmount;
        this.totalServicesAmount = totalServicesAmount;
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

    public List<BidModel> getBids(BidType bidType) {
        List<BidModel> bidsByType = new ArrayList<>();
        for (BidModel bid : bids) {
            if (bid.getType().equals(bidType)) {
                bidsByType.add(bid);
            }
        }
        return bidsByType;
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

    public BigDecimal getTotalMaterialsAmount() {
        return totalMaterialsAmount;
    }

    public void setTotalMaterialsAmount(BigDecimal totalMaterialsAmount) {
        this.totalMaterialsAmount = totalMaterialsAmount;
    }

    public BigDecimal getTotalEqupmentAmount() {
        return totalEqupmentAmount;
    }

    public void setTotalEqupmentAmount(BigDecimal totalEqupmentAmount) {
        this.totalEqupmentAmount = totalEqupmentAmount;
    }

    public BigDecimal getTotalServicesAmount() {
        return totalServicesAmount;
    }

    public void setTotalServicesAmount(BigDecimal totalServicesAmount) {
        this.totalServicesAmount = totalServicesAmount;
    }

    public BigDecimal getTotalAmount(BidType bidType) {
        switch (bidType) {
            case MATERIALS:
                return totalMaterialsAmount;
            case SERVICES:
                return totalServicesAmount;
            case EQUIPMENT:
                return totalEqupmentAmount;
            default:
                return null;
        }
    }

    public BigDecimal getLeftAmount(BidType bidType) {
        BigDecimal leftAmount = getTotalAmount(bidType);
        for (BidModel bid : bids) {
            if (bid.getType().equals(bidType) && bid.isActive()) {
                leftAmount = leftAmount.subtract(bid.getTotalPrice());
            }
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

    @Override
    public void setDeleted() {
        for (BidModel model : bids) {
            model.setDeleted();
        }
        super.setDeleted();
    }
}
