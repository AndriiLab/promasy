package com.github.andriilab.promasy.model.models;

import com.github.andriilab.promasy.gui.commons.Labels;
import com.github.andriilab.promasy.model.dao.Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *  Model for producer data
 */

@Entity
@Table(name = "producers")
public class ProducerModel extends AbstractModel{

    @Column(name = "brand_name")
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

    @Override
    public void setDescription(String producerName) {
        this.brandName = producerName;
    }

    @Override
    public void createOrUpdate() throws SQLException {
        Database.PRODUCERS.createOrUpdate(this);
    }

    @Override
    public void refresh() {
        Database.PRODUCERS.refresh(this);
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

    public String toString(){
        return brandName;
    }
}
