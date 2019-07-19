package com.github.andriilab.promasy.domain.item.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.app.commons.Labels;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * IEntity for supplier data
 */

@Entity
@Table(name = "suppliers")
public class Supplier extends AbstractEntity {

    @Column(name = "supplier_name")
    @Getter @Setter private String supplierName;

    @Column(name = "supplier_tel")
    @Getter @Setter private String supplierTel;

    @Column(name = "supplier_comments")
    @Getter @Setter private String supplierComments;

    public Supplier(Employee createdBy, Timestamp createdDate, Employee modifiedBy, Timestamp modifiedDate,
                    boolean active, long supplierId, String supplierName, String supplierTel,
                    String supplierComments) {
        super(supplierId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.supplierName = supplierName;
        this.supplierTel = supplierTel;
        this.supplierComments = supplierComments;
    }

    public Supplier(String supplierName, String supplierTel,
                    String supplierComments) {
        this.supplierName = supplierName;
        this.supplierTel = supplierTel;
        this.supplierComments = supplierComments;
    }

    public Supplier() {
        this.supplierName = Labels.getProperty("any");
    }

    @Override
    public void setDescription(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String getDescription() {
        return this.supplierName;
    }

    @Override
    public String getMessage() {
        return "addOrUpdateSupl";
    }

    @Override
    public String toString() {
        return supplierName;
    }
}
