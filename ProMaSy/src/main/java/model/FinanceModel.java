package main.java.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Model for financial data
 */
public class FinanceModel extends AbstractModel {
    private int orderNumber;
    private String orderName;
    private BigDecimal totalAmount;
    private BigDecimal leftAmount;
    private Date startDate;
    private Date endDate;

    public FinanceModel(long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate, boolean active, long orderId, int orderNumber, String orderName, BigDecimal totalAmount, BigDecimal leftAmount, Date startDate, Date endDate) {
        super(orderId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.orderNumber = orderNumber;
        this.orderName = orderName;
        this.totalAmount = totalAmount;
        this.leftAmount = leftAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public FinanceModel(int orderNumber, String orderName, BigDecimal totalAmount, Date startDate, Date endDate) {
        this.orderNumber = orderNumber;
        this.orderName = orderName;
        this.totalAmount = totalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public FinanceModel() {

    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
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
        if (orderNumber == 0 && orderName == null) {
            return "";
        }
        return "(" + orderNumber + ") " + orderName;
    }
}
