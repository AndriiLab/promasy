package main.java.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Model for storing data related to the bid
 */
public class BidModel extends AbstractModel{

    private long depId;
    private String depName;
    private long producerId;
    private String producerName;
    private String catNum;
    private String bidDesc;
    private String cpvCode;
    private String cpvUkr;
    private BigDecimal onePrice;
    private int amount;
    private long amUnitId;
    private String amUnitName;
    private long financeId;
    private String financeName;
    private long supplierId;
    private String supplierName;
    private int statusId;
    private String statusDesc;
    private String createdFName;
    private String createdMName;
    private String createdLName;
    private String editedFName;
    private String editedMName;
    private String editedLName;
    private long reasonId;
    private String reasonName;

    public BidModel(long modelId, long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate, boolean active, long depId, String depName, long producerId, String producerName, String catNum, String bidDesc, String cpvCode, String cpvUkr, BigDecimal onePrice, int amount, long amUnitId, String amUnitName, long financeId, String financeName, long supplierId, String supplierName, int statusId, String statusDesc, String createdFName, String createdMName, String createdLName, String editedFName, String editedMName, String editedLName, long reasonId, String reasonName) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.depId = depId;
        this.depName = depName;
        this.producerId = producerId;
        this.producerName = producerName;
        this.catNum = catNum;
        this.bidDesc = bidDesc;
        this.cpvCode = cpvCode;
        this.cpvUkr = cpvUkr;
        this.onePrice = onePrice;
        this.amount = amount;
        this.amUnitId = amUnitId;
        this.amUnitName = amUnitName;
        this.financeId = financeId;
        this.financeName = financeName;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.createdFName = createdFName;
        this.createdMName = createdMName;
        this.createdLName = createdLName;
        this.editedFName = editedFName;
        this.editedMName = editedMName;
        this.editedLName = editedLName;
        this.reasonId = reasonId;
        this.reasonName = reasonName;
    }

    public BidModel(long depId, long producerId, String catNum, String bidDesc, String cpvCode, BigDecimal onePrice, int amount, long amUnitId, long financeId, long supplierId, int statusId, long reasonId, String reasonName) {
        this.depId = depId;
        this.producerId = producerId;
        this.catNum = catNum;
        this.bidDesc = bidDesc;
        this.cpvCode = cpvCode;
        this.onePrice = onePrice;
        this.amount = amount;
        this.amUnitId = amUnitId;
        this.financeId = financeId;
        this.supplierId = supplierId;
        this.statusId = statusId;
        this.reasonId = reasonId;
        this.reasonName = reasonName;
    }

    public BidModel() {

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

    public long getProducerId() {
        return producerId;
    }

    public void setProducerId(long producerId) {
        this.producerId = producerId;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
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

    public String getCpvUkr() {
        return cpvUkr;
    }

    public void setCpvUkr(String cpvUkr) {
        this.cpvUkr = cpvUkr;
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

    public String getAmUnitName() {
        return amUnitName;
    }

    public void setAmUnitName(String amUnitName) {
        this.amUnitName = amUnitName;
    }

    public long getFinanceId() {
        return financeId;
    }

    public void setFinanceId(long financeId) {
        this.financeId = financeId;
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getCreatedFName() {
        return createdFName;
    }

    public void setCreatedFName(String createdFName) {
        this.createdFName = createdFName;
    }

    public String getCreatedMName() {
        return createdMName;
    }

    public void setCreatedMName(String createdMName) {
        this.createdMName = createdMName;
    }

    public String getCreatedLName() {
        return createdLName;
    }

    public void setCreatedLName(String createdLName) {
        this.createdLName = createdLName;
    }

    public String getEditedFName() {
        return editedFName;
    }

    public void setEditedFName(String editedFName) {
        this.editedFName = editedFName;
    }

    public String getEditedMName() {
        return editedMName;
    }

    public void setEditedMName(String editedMName) {
        this.editedMName = editedMName;
    }

    public String getEditedLName() {
        return editedLName;
    }

    public void setEditedLName(String editedLName) {
        this.editedLName = editedLName;
    }

    public long getReasonId() {
        return reasonId;
    }

    public void setReasonId(long reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    @Override
    public String toString() {
        return bidDesc;
    }

    public Object getLastEditPersonName() {
        if (editedFName != null && editedLName != null && editedMName != null) {
            return editedLName + " " + editedFName.substring(0, 1) + "."
                    + editedMName.substring(0, 1) + ".";
        } else if (createdFName != null && createdLName != null && createdMName != null) {
            return createdLName + " " + createdFName.substring(0, 1) + "."
                    + createdMName.substring(0, 1)+".";
        }
        return null;
    }

    public Object getLastEditDate() {
        if (super.getModifiedDate() != null) {
            return super.getModifiedDate();
        } else if (super.getCreatedDate() != null) {
            return super.getCreatedDate();
        }
        return null;
    }
}
