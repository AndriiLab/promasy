package model.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

/**
 * Wrapper class for {@link BidModel}. Used for storing bids with equipment
 */

@Entity
@Table(name = "bids_equipment")
public class BidEquipmentModel extends BidModel {

    public BidEquipmentModel(DepartmentModel department, ProducerModel producer, String catNum, String bidDesc, CPVModel cpv, BigDecimal onePrice, int amount, AmountUnitsModel amountUnit, FinanceDepartmentModel finances, SupplierModel supplier, List statuses, ReasonForSupplierChoiceModel reasonForSupplierChoice) {
        super(department, producer, catNum, bidDesc, cpv, onePrice, amount, amountUnit, finances, supplier, statuses, reasonForSupplierChoice);
    }

    public BidEquipmentModel() {
    }
}
