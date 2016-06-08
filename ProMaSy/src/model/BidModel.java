package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by laban on 23.05.2016.
 */
public class BidModel extends AbstractModel{

    private long depId;
    private String depName;
    private long brandId;
    private String brandName;
    private String catNum;
    private String bidDesc;
    private String cpvCode;
    private BigDecimal onePrice;
    private int amount;
    private long amUnitId;
    private String amUnitName;
    private long orderId;
    private String orderName;
    private long supplierId;
    private String supplierName;
    private boolean received;
    private Timestamp dateReceived;

    public BidModel(long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate, boolean active, long bidId, long depId, String depName, long brandId, String brandName, String catNum, String bidDesc, String cpvCode, BigDecimal onePrice, int amount, long amUnitId, String amUnitName, long orderId, String orderName, long supplierId, String supplierName, boolean received, Timestamp dateReceived) {
        super(bidId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.depId = depId;
        this.depName = depName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.catNum = catNum;
        this.bidDesc = bidDesc;
        this.cpvCode = cpvCode;
        this.onePrice = onePrice;
        this.amount = amount;
        this.amUnitId = amUnitId;
        this.amUnitName = amUnitName;
        this.orderId = orderId;
        this.orderName = orderName;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.received = received;
        this.dateReceived = dateReceived;
    }

    public BidModel(long depId, long brandId, String catNum, String bidDesc, String cpvCode, BigDecimal onePrice, int amount, long amUnitId, long orderId, long supplierId) {
        this.depId = depId;
        this.brandId = brandId;
        this.catNum = catNum;
        this.bidDesc = bidDesc;
        this.cpvCode = cpvCode;
        this.onePrice = onePrice;
        this.amount = amount;
        this.amUnitId = amUnitId;
        this.orderId = orderId;
        this.supplierId = supplierId;
    }

    public BidModel() {

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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getAmUnitName() {
        return amUnitName;
    }

    public void setAmUnitName(String amUnitName) {
        this.amUnitName = amUnitName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public String toString() {
        return bidDesc;
    }
}
