package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.organization.entities.Employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * IEntity for storing reasons why buyer choose selected supplier
 */
@Entity
@Table(name = "reasons_for_suppl")
public class ReasonForSupplierChoice extends AbstractEntity {

    @Column(name = "reason_name")
    private String reason;

    public ReasonForSupplierChoice(long modelId, Employee createdBy, Timestamp createdDate, Employee modifiedBy, Timestamp modifiedDate, boolean active, String reason) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.reason = reason;
    }

    public ReasonForSupplierChoice(String reason) {
        this.reason = reason;
    }

    public ReasonForSupplierChoice() {
        this.reason = EmptyModel.STRING;
    }

    @Override
    public void setDescription(String reason) {
        this.reason = reason;
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
