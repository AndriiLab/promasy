package model.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Model for financial data
 */
@Entity
@Table(name = "finances")
public class FinanceModel extends AbstractModel {

    @Column(name = "number")
    private int financeNumber;

    @Column(name = "name")
    private String financeName;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "starts_on")
    private Date startDate;

    @Column(name = "due_to")
    private Date endDate;

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private List<FinanceDepartmentModel> departmentModels;

    public FinanceModel(EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active, long orderId, int financeNumber, String financeName, BigDecimal totalAmount, Date startDate, Date endDate, List<FinanceDepartmentModel> departmentModels) {
        super(orderId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.financeNumber = financeNumber;
        this.financeName = financeName;
        this.totalAmount = totalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.departmentModels = departmentModels;
    }

    public FinanceModel(int financeNumber, String financeName, BigDecimal totalAmount, Date startDate, Date endDate) {
        this.financeNumber = financeNumber;
        this.financeName = financeName;
        this.totalAmount = totalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public FinanceModel() {

    }

    public List<FinanceDepartmentModel> getDepartmentModels() {
        return departmentModels;
    }

    public void setDepartmentModels(List<FinanceDepartmentModel> departmentModels) {
        this.departmentModels = departmentModels;
    }

    public int getFinanceNumber() {
        return financeNumber;
    }

    public void setFinanceNumber(int financeNumber) {
        this.financeNumber = financeNumber;
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getLeftAmount() {
        BigDecimal leftAmount = totalAmount;
        for (FinanceDepartmentModel model : departmentModels) {
            leftAmount = leftAmount.subtract(model.getLeftAmount());
        }
        return leftAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        if (financeNumber == 0 && financeName == null) {
            return "";
        }
        return "(" + financeNumber + ") " + financeName;
    }
}
