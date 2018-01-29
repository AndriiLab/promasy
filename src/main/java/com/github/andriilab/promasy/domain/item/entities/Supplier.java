package com.github.andriilab.promasy.domain.item.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.presentation.commons.Labels;

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
    private String supplierName;

    @Column(name = "supplier_tel")
    private String supplierTel;

    @Column(name = "supplier_comments")
    private String supplierComments;

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
    public String getMessage() {
        return "addOrUpdateSupl";
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierTel() {
        return supplierTel;
    }

    public void setSupplierTel(String supplierTel) {
        this.supplierTel = supplierTel;
    }

    public String getSupplierComments() {
        return supplierComments;
    }

    public void setSupplierComments(String supplierComments) {
        this.supplierComments = supplierComments;
    }

    @Override
    public String toString() {
        return supplierName;
    }
}
