package com.github.andriilab.promasy.domain.item.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.presentation.commons.Labels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * IEntity for producer data
 */

@Entity
@Table(name = "producers")
public class Producer extends AbstractEntity {

    @Column(name = "brand_name")
    private String brandName;

    public Producer(Employee createdBy, Timestamp createdDate, Employee modifiedBy,
                    Timestamp modifiedDate, boolean active, long brandId,
                    String brandName) {
        super(brandId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.brandName = brandName;
    }

    public Producer(String brandName) {
        this.brandName = brandName;
    }

    public Producer() {
        this.brandName = Labels.getProperty("any");
    }

    @Override
    public void setDescription(String producerName) {
        this.brandName = producerName;
    }

    @Override
    public String getMessage() {
        return "addOrUpdateProd";
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String toString() {
        return brandName;
    }
}
