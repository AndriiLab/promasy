package model;

import java.sql.Timestamp;

/**
 * Model for storing status-bid relations
 */
public class StatusModel extends AbstractModel {
    private long bidId;
    private int statusId;
    private String statusDesc;
    private String createdFName;
    private String createdMName;
    private String createdLName;

    public StatusModel(long modelId, long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate, boolean active, long bidId, int statusId, String statusDesc, String createdFName, String createdMName, String createdLName) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.bidId = bidId;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.createdFName = createdFName;
        this.createdMName = createdMName;
        this.createdLName = createdLName;
    }

    public StatusModel(long bidId, int statusId, String statusDesc) {
        this.bidId = bidId;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
    }

    public StatusModel() {

    }

    public long getBidId() {
        return bidId;
    }

    public void setBidId(long bidId) {
        this.bidId = bidId;
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

    public Object getLastEditPersonName() {
        if (createdFName != null && createdLName != null && createdMName != null) {
            return createdLName + " " + createdFName.substring(0, 1) + "."
                    + createdMName.substring(0, 1) + ".";
        }
        return null;
    }

    @Override
    public String toString() {
        return super.getCreatedDate().toString() + " " + statusDesc + " " + getLastEditPersonName();
    }
}
