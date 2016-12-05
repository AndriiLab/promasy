package main.java.model;

import java.sql.Timestamp;

/**
 * Model for storing reasons why buyer choose selected supplier
 */
public class ReasonForSupplierChoiceModel extends AbstractModel {
    private String reason;

    public ReasonForSupplierChoiceModel(long modelId, long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate, boolean active, String reason) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.reason = reason;
    }

    public ReasonForSupplierChoiceModel(String reason) {
        this.reason = reason;
    }

    public ReasonForSupplierChoiceModel() {
        this.reason = "";
        this.setModelId(0L);

    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return reason;
    }
}
