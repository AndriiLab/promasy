package com.github.andriilab.promasy.domain.bid.enums;

import com.github.andriilab.promasy.presentation.commons.Labels;

/**
 * Enum holds data about types of bids
 */
public enum BidType {
    MATERIALS(Labels.getProperty("bidType.material"), Labels.getInt("default.bidType.materialKEKV")),
    EQUIPMENT(Labels.getProperty("bidType.equipment"), Labels.getInt("default.bidType.equipmentKEKV")),
    SERVICES(Labels.getProperty("bidType.services"), Labels.getInt("default.bidType.servicesKEKV"));

    private final String bidTypeName;
    private final Integer kekv;

    BidType(String bidTypeName, Integer kekv) {
        this.bidTypeName = bidTypeName;
        this.kekv = kekv;
    }

    public String getBidTypeName() {
        return bidTypeName;
    }

    public Integer getKEKV() {
        return kekv;
    }

    @Override
    public String toString() {
        return bidTypeName;
    }
}
