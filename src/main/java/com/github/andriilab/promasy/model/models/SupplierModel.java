package com.github.andriilab.promasy.model.models;

import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.model.dao.Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Model for supplier data
 */

@Entity
@Table(name = "suppliers")
public class SupplierModel extends AbstractModel {

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_tel")
    private String supplierTel;

    @Column(name = "supplier_comments")
    private String supplierComments;

    public SupplierModel(EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate,
                         boolean active, long supplierId, String supplierName, String supplierTel,
                         String supplierComments) {
        super(supplierId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.supplierName = supplierName;
        this.supplierTel = supplierTel;
        this.supplierComments = supplierComments;
    }

    public SupplierModel(String supplierName, String supplierTel,
                         String supplierComments){
        this.supplierName = supplierName;
        this.supplierTel = supplierTel;
        this.supplierComments = supplierComments;
    }

    public SupplierModel(){
        this.supplierName = Labels.getProperty("any");
    }

    @Override
    public void setDescription(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public void createOrUpdate() throws SQLException {
        Database.SUPPLIERS.createOrUpdate(this);
    }

    @Override
    public void refresh() {
        Database.SUPPLIERS.refresh(this);
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
