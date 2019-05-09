package com.github.andriilab.promasy.domain.item.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * IEntity for producer persistence
 */

@Entity
@Table(name = "producers")
public class Producer extends AbstractEntity {

    @Column(name = "brand_name")
    @Getter @Setter private String brandName;

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
        this.brandName = ""; // todo
    }

    @Override
    public void setDescription(String producerName) {
        this.brandName = producerName;
    }

    @Override
    public String getDescription() {
        return this.brandName;
    }

    @Override
    public String getMessage() {
        return "addOrUpdateProd";
    }

    public String toString() {
        return brandName;
    }
}
