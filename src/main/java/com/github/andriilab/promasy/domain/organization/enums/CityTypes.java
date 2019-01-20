package com.github.andriilab.promasy.domain.organization.enums;

import com.github.andriilab.promasy.presentation.commons.Labels;
import lombok.Getter;

/**
 * Types of cities with full and short names. According to http://zakon3.rada.gov.ua/laws/show/v0048359-09
 */
public enum CityTypes {
    CITY(Labels.getProperty("cityTypes.city"), Labels.withDot("cityTypes.cityShort")),
    URBAN_VILLAGE(Labels.getProperty("cityTypes.urbanVillage"), Labels.getProperty("cityTypes.urbanVillageShort")),
    SETTLEMENT(Labels.getProperty("cityTypes.settlement"), Labels.getProperty("cityTypes.settlementShort")),
    VILLAGE(Labels.getProperty("cityTypes.village"), Labels.withDot("cityTypes.villageShort"));

    @Getter private final String name;
    @Getter private final String shortName;

    CityTypes(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return name;
    }
}
