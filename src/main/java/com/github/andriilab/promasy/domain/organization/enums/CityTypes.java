package com.github.andriilab.promasy.domain.organization.enums;

import com.github.andriilab.promasy.presentation.commons.Labels;

/**
 * Types of cities with full and short names. According to http://zakon3.rada.gov.ua/laws/show/v0048359-09
 */
public enum CityTypes {
    CITY(Labels.getProperty("cityTypes.city"), Labels.withDot("cityTypes.cityShort")),
    URBAN_VILLAGE(Labels.getProperty("cityTypes.urbanVillage"), Labels.getProperty("cityTypes.urbanVillageShort")),
    SETTLEMENT(Labels.getProperty("cityTypes.settlement"), Labels.getProperty("cityTypes.settlementShort")),
    VILLAGE(Labels.getProperty("cityTypes.village"), Labels.withDot("cityTypes.villageShort"));

    private final String name;
    private final String shortName;

    CityTypes(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return name;
    }
}
