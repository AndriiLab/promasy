package model.enums;

import gui.commons.Labels;

/**
 * Enum holds data about types of bids
 */
public enum BidType {
    MATERIALS(Labels.getProperty("bidType.material")),
    EQUIPMENT(Labels.getProperty("bidType.equipment")),
    SERVICES(Labels.getProperty("bidType.services"));

    private String bidTypeName;

    BidType(String bidTypeName) {
        this.bidTypeName = bidTypeName;
    }

    public String getBidTypeName() {
        return bidTypeName;
    }

    @Override
    public String toString() {
        return bidTypeName;
    }
}
