package model;

import java.sql.Timestamp;

/**
 * Created by laban on 26.04.2016.
 */
public class ProducerModel extends AbstractModel{

    private long brandId;
    private String brandName;

    public ProducerModel(long createdBy, Timestamp createdDate, long modifiedBy,
                         Timestamp modifiedDate, boolean active, long brandId,
                         String brandName) {
        super(createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
