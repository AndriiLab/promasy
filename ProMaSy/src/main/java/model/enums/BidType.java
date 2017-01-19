package model.enums;

import gui.Labels;
import model.models.BidEquipmentModel;
import model.models.BidMaterialModel;
import model.models.BidModel;

/**
 * Enum holds data about types of bids
 */
public enum BidType {
    MATERIALS(Labels.getProperty("bidType.material"), BidMaterialModel.class),
    EQUIPMENT(Labels.getProperty("bidType.equipment"), BidEquipmentModel.class),
    SERVICES(Labels.getProperty("bidType.services"), BidEquipmentModel.class);

    private String bidTypeName;
    private Class typeClass;

    <T extends BidModel> BidType(String bidTypeName, Class<T> type) {
        this.bidTypeName = bidTypeName;
        this.typeClass = type;
    }

    public String getBidTypeName() {
        return bidTypeName;
    }

    public <T extends BidModel> Class<T> getTypeClass() {
        return typeClass;
    }

    @Override
    public String toString() {
        return bidTypeName;
    }
}
