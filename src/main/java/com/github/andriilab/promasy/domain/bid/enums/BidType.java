package com.github.andriilab.promasy.domain.bid.enums;

import com.github.andriilab.promasy.presentation.commons.Labels;
import lombok.Getter;

/**
 * Enum holds data about types of bids
 */
public enum BidType {
    MATERIALS(Labels.getProperty("bidType.material"), Labels.getInt("default.bidType.materialKEKV")),
    EQUIPMENT(Labels.getProperty("bidType.equipment"), Labels.getInt("default.bidType.equipmentKEKV")),
    SERVICES(Labels.getProperty("bidType.services"), Labels.getInt("default.bidType.servicesKEKV"));

    @Getter private final String bidTypeName;
    @Getter private final Integer kekv;

    BidType(String bidTypeName, Integer kekv) {
        this.bidTypeName = bidTypeName;
        this.kekv = kekv;
    }

    @Override
    public String toString() {
        return bidTypeName;
    }
}
