package model;

import java.sql.Timestamp;

/**
 * Model for storing reasons why buyer choose selected supplier
 */
public class ReasonForSupplierChoiceModel extends AbstractModel {
    private String reason;

    public ReasonForSupplierChoiceModel(long modelId, EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active, String reason) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.reason = reason;
    }

    public ReasonForSupplierChoiceModel(String reason) {
        this.reason = reason;
    }

    public ReasonForSupplierChoiceModel() {
        this.reason = "";
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
