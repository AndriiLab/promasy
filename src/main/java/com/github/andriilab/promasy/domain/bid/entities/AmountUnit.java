package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.entities.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "amount_units")
public class AmountUnit extends AbstractEntity {

    @Column(name = "amount_unit_desc")
    private String description;

    public AmountUnit(long amUnitId, String description, Employee createdBy,
                      Timestamp createdDate, Employee modifiedBy, Timestamp modifiedDate,
                      boolean active) {
        super(amUnitId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.description = description;
    }

    public AmountUnit(String description) {
        this.description = description;
    }

    public AmountUnit() {
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getMessage() {
        return "addOrUpdateAmUnit";
    }

    public String toString() {
        return description;
    }

}
