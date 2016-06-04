package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by laban on 23.05.2016.
 */
public class BidModel extends AbstractModel{

    private long bidId;
    private long empId;
    private long brandId;
    private String catNum;
    private String bidDesc;
    private String cpvCode;
    private BigDecimal onePrice;
    private int amount;
    private long amUnitId;
    private long orderId;
    private long supplierId;
    private boolean received;
    private Timestamp dateReceived;

    public BidModel(long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate, boolean active, long bidId, long empId, long brandId, String catNum, String bidDesc, String cpvCode, BigDecimal onePrice, int amount, long amUnitId, long orderId, long supplierId, boolean received, Timestamp dateReceived) {
        super(createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.bidId = bidId;
        this.empId = empId;
        this.brandId = brandId;
        this.catNum = catNum;
        this.bidDesc = bidDesc;
        this.cpvCode = cpvCode;
        this.onePrice = onePrice;
        this.amount = amount;
        this.amUnitId = amUnitId;
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.received = received;
        this.dateReceived = dateReceived;
    }

    public BidModel() {

    }

    public long getBidId() {
        return bidId;
    }

    public void setBidId(long bidId) {
        this.bidId = bidId;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
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

    public String getCpvCode() {
        return cpvCode;
    }

    public void setCpvCode(String cpvCode) {
        this.cpvCode = cpvCode;
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

    public long getAmUnitId() {
        return amUnitId;
    }

    public void setAmUnitId(long amUnitId) {
        this.amUnitId = amUnitId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public Timestamp getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Timestamp dateReceived) {
        this.dateReceived = dateReceived;
    }
}
