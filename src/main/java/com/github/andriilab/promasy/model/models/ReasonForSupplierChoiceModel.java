package com.github.andriilab.promasy.model.models;

import com.github.andriilab.promasy.model.dao.Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Model for storing reasons why buyer choose selected supplier
 */
@Entity
@Table(name = "reasons_for_suppl")
public class ReasonForSupplierChoiceModel extends AbstractModel {

    @Column(name = "reason_name")
    private String reason;

    public ReasonForSupplierChoiceModel(long modelId, EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active, String reason) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.reason = reason;
    }

    public ReasonForSupplierChoiceModel(String reason) {
        this.reason = reason;
    }

    public ReasonForSupplierChoiceModel() {
        this.reason = EmptyModel.STRING;
    }

    @Override
    public void setDescription(String reason) {
        this.reason = reason;
    }

    @Override
    public void createOrUpdate() throws SQLException {
        Database.REASONS.createOrUpdate(this);
    }

    @Override
    public void refresh() {
        Database.REASONS.refresh(this);
    }

    @Override
    public String getMessage() {
        return "addOrUpdateReasonForSupplierChoice";
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
