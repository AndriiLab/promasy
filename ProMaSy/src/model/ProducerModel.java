package model;

import java.sql.Timestamp;

/**
 * Created by laban on 26.04.2016.
 */
public class ProducerModel extends AbstractModel{
    private String brandName;

    public ProducerModel(long createdBy, Timestamp createdDate, long modifiedBy,
                         Timestamp modifiedDate, boolean active, long brandId,
                         String brandName) {
        super(brandId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.brandName = brandName;
    }

    public ProducerModel(String brandName){
        this.brandName = brandName;
    }

    public ProducerModel(){
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