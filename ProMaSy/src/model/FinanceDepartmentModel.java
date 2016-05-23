package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by laban on 04.05.2016.
 */
public class FinanceDepartmentModel extends AbstractModel {
    private long orderId;
    private long depId;
    private String depName;
    private long empId;
    private String empName;
    private BigDecimal totalAmount;
    private BigDecimal leftAmount;

    public FinanceDepartmentModel(long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate, boolean active, long orderId, long depId, String depName, long empId, String empName, BigDecimal totalAmount, BigDecimal leftAmount) {
        super(createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.orderId = orderId;
        this.depId = depId;
        this.depName = depName;
        this.empId = empId;
        this.empName = empName;
        this.totalAmount = totalAmount;
        this.leftAmount = leftAmount;
    }

    public FinanceDepartmentModel(long orderId, long depId, long empId, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.depId = depId;
        this.empId = empId;
        this.totalAmount = totalAmount;
    }

    public FinanceDepartmentModel() {

    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getDepId() {
        return depId;
    }

    public void setDepId(long depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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
        return depName;
    }
}
