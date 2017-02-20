package model.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "amount_units")
public class AmountUnitsModel extends AbstractModel{

    @Column(name = "amount_unit_desc")
    private String amUnitDesc;

    public AmountUnitsModel(long amUnitId, String amUnitDesc, EmployeeModel createdBy,
                            Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate,
                            boolean active) {
        super(amUnitId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.amUnitDesc = amUnitDesc;
    }

    public AmountUnitsModel(String amUnitDesc) {
        this.amUnitDesc = amUnitDesc;
    }

    public AmountUnitsModel() {

    }

    @Override
    public void setDescription(String description) {
        this.amUnitDesc = description;
    }

    public String getAmUnitDesc() {
        return amUnitDesc;
    }

    public void setAmUnitDesc(String amUnitDesc) {
        this.amUnitDesc = amUnitDesc;
    }

    public String toString(){
        return amUnitDesc;
    }

}
