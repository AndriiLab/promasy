package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import org.hibernate.JDBCException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "amount_units")
public class AmountUnit extends AbstractEntity {

    @Column(name = "amount_unit_desc")
    private String amUnitDesc;

    public AmountUnit(long amUnitId, String amUnitDesc, Employee createdBy,
                      Timestamp createdDate, Employee modifiedBy, Timestamp modifiedDate,
                      boolean active) {
        super(amUnitId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.amUnitDesc = amUnitDesc;
    }

    public AmountUnit(String amUnitDesc) {
        this.amUnitDesc = amUnitDesc;
    }

    public AmountUnit() {

    }

    @Override
    public void setDescription(String description) {
        this.amUnitDesc = description;
    }

    @Override
    public void createOrUpdate() throws JDBCException {
        Database.AMOUNTUNITS.createOrUpdate(this);
    }

    @Override
    public void refresh() {
        Database.AMOUNTUNITS.refresh(this);
    }

    @Override
    public String getMessage() {
        return "addOrUpdateAmUnit";
    }

    public String getAmUnitDesc() {
        return amUnitDesc;
    }

    public void setAmUnitDesc(String amUnitDesc) {
        this.amUnitDesc = amUnitDesc;
    }

    public String toString() {
        return amUnitDesc;
    }

}
