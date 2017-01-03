package model;

import gui.Labels;

import java.sql.Timestamp;

/**
 *  Model for producer data
 */
public class ProducerModel extends AbstractModel{
    private String brandName;

    public ProducerModel(EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy,
                         Timestamp modifiedDate, boolean active, long brandId,
                         String brandName) {
        super(brandId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.brandName = brandName;
    }

    public ProducerModel(String brandName){
        this.brandName = brandName;
    }

    public ProducerModel(){
        this.brandName = Labels.getProperty("any");
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String toString(){
        return brandName;
    }
}
