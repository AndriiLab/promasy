package main.java.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Model for data of related to department finances
 */
public class FinanceDepartmentModel extends AbstractModel {
    private int orderNumber;
    private String orderName;
    private long depId;
    private String depName;
    private long empId;
    private String empName;
    private BigDecimal totalAmount;
    private BigDecimal leftAmount;

    public FinanceDepartmentModel(long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate, boolean active, long orderId, String orderName, int orderNumber, long depId, String depName, long empId, String empName, BigDecimal totalAmount, BigDecimal leftAmount) {
        super(orderId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.orderName = orderName;
        this.orderNumber = orderNumber;
        this.depId = depId;
        this.depName = depName;
        this.empId = empId;
        this.empName = empName;
        this.totalAmount = totalAmount;
        this.leftAmount = leftAmount;
    }

    public FinanceDepartmentModel(long orderId, long depId, long empId, BigDecimal totalAmount) {
        setModelId(orderId);
        this.depId = depId;
        this.empId = empId;
        this.totalAmount = totalAmount;
    }

    public FinanceDepartmentModel() {

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

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        if (orderNumber == 0 && orderName == null) {
            return "";
        }
        return "(" + orderNumber + ") " + orderName;
    }
}
