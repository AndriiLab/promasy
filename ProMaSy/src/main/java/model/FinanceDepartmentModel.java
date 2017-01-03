package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Model for data of related to department finances
 */
public class FinanceDepartmentModel extends AbstractModel {
    private FinanceModel finances;
    private DepartmentModel department;
    private EmployeeModel responsibleEmployee;

    private BigDecimal totalAmount;
    private BigDecimal leftAmount;

    public FinanceDepartmentModel(EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active, long orderId, FinanceModel finances, DepartmentModel department, EmployeeModel responsibleEmployee, BigDecimal totalAmount, BigDecimal leftAmount) {
        super(orderId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.finances = finances;
        this.department = department;
        this.responsibleEmployee = responsibleEmployee;
        this.totalAmount = totalAmount;
        this.leftAmount = leftAmount;
    }

    public FinanceDepartmentModel(long orderId, DepartmentModel department, EmployeeModel responsibleEmployee, BigDecimal totalAmount) {
        setModelId(orderId);
        this.department = department;
        this.responsibleEmployee = responsibleEmployee;
        this.totalAmount = totalAmount;
    }

    public FinanceDepartmentModel() {

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
        return leftAmount;
    }

    public void setLeftAmount(BigDecimal leftAmount) {
        this.leftAmount = leftAmount;
    }

    @Override
    public String toString() {
        return finances.toString();
    }
}
