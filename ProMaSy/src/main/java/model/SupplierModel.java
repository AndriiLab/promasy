package main.java.model;

import java.sql.Timestamp;

/**
 * Created by laban on 26.04.2016.
 */
public class SupplierModel extends AbstractModel {
    private String supplierName;
    private String supplierTel;
    private String supplierComments;

    public SupplierModel(long createdBy, Timestamp createdDate, long modifiedBy, Timestamp modifiedDate,
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
