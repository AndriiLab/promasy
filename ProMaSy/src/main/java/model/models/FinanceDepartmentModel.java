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
    @JoinColumn(name = "dep_id")
    private DepartmentModel department;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "emp_id")
    private EmployeeModel responsibleEmployee;

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private List<BidModel> bids = new ArrayList<>();

    @Column(name = "finance_amount")
    private BigDecimal totalAmount;

    public FinanceDepartmentModel(EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active, long orderId, FinanceModel finances, DepartmentModel department, EmployeeModel responsibleEmployee, BigDecimal totalAmount, List<BidModel> bids) {
        super(orderId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.finances = finances;
        this.department = department;
        this.responsibleEmployee = responsibleEmployee;
        this.totalAmount = totalAmount;
        this.bids = bids;
    }

    public FinanceDepartmentModel(long orderId, DepartmentModel department, EmployeeModel responsibleEmployee, BigDecimal totalAmount) {
        setModelId(orderId);
        this.department = department;
        this.responsibleEmployee = responsibleEmployee;
        this.totalAmount = totalAmount;
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

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }

    public EmployeeModel getResponsibleEmployee() {
        return responsibleEmployee;
    }

    public void setResponsibleEmployee(EmployeeModel responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
