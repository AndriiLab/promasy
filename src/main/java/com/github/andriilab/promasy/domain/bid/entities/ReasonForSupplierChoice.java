package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.entities.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.sql.Timestamp;

/**
 * IEntity for storing reasons why buyer choose selected supplier
 */
@Entity
@Table(name = "reasons_for_suppl")
public class ReasonForSupplierChoice extends AbstractEntity {

    @Column(name = "reason_name")
    private String description;

    public ReasonForSupplierChoice(long modelId, Employee createdBy, Timestamp createdDate, Employee modifiedBy,
                                   Timestamp modifiedDate, boolean active, String description) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.description = description;
    }

    public ReasonForSupplierChoice(String description) {
        this.description = description;
    }

    public ReasonForSupplierChoice() {
        this.description = "";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getMessage() {
        return "addOrUpdateReasonForSupplierChoice";
    }

    @Override
    public String toString() {
        return description;
    }
}
