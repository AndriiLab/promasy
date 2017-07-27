package model.enums;

import gui.commons.Labels;

/**
 * Types of streets with full and short names. According to http://zakon3.rada.gov.ua/laws/show/v0048359-09
 */
public enum StreetTypes {
    STREET(Labels.getProperty("streetTypes.street"), Labels.withDot("streetTypes.streetShort")),
    AVENUE(Labels.getProperty("streetTypes.avenue"), Labels.withDot("streetTypes.avenueShort")),
    QUAY(Labels.getProperty("streetTypes.quay"), Labels.getProperty("streetTypes.quay")),
    BOULEVARD(Labels.getProperty("streetTypes.boulevard"), Labels.withDot("streetTypes.boulevardShort")),
    ALLEY(Labels.getProperty("streetTypes.alley"), Labels.getProperty("streetTypes.alley")),
    BLIND_ALLEY(Labels.getProperty("streetTypes.blindAlley"), Labels.getProperty("streetTypes.blindAlley")),
    DESCENT(Labels.getProperty("streetTypes.descent"), Labels.getProperty("streetTypes.descent")),
    HIGHWAY(Labels.getProperty("streetTypes.highway"), Labels.getProperty("streetTypes.highway")),
    SQUARE(Labels.getProperty("streetTypes.square"), Labels.withDot("streetTypes.squareShort")),
    LANE(Labels.getProperty("streetTypes.lane"), Labels.withDot("streetTypes.laneShort")),
    LINE(Labels.getProperty("streetTypes.line"), Labels.getProperty("streetTypes.line")),
    BACK_ALLEY(Labels.getProperty("streetTypes.backAlley"), Labels.getProperty("streetTypes.backAlley")),
    ENTRY(Labels.getProperty("streetTypes.entry"), Labels.getProperty("streetTypes.entry")),
    ENTRY2(Labels.getProperty("streetTypes.entry2"), Labels.getProperty("streetTypes.entry2")),
    PASSAGE(Labels.getProperty("streetTypes.passage"), Labels.getProperty("streetTypes.passage")),
    CROSSING(Labels.getProperty("streetTypes.crossing"), Labels.getProperty("streetTypes.crossing")),
    GLADE(Labels.getProperty("streetTypes.glade"), Labels.getProperty("streetTypes.glade")),
    SQUARE2(Labels.getProperty("streetTypes.square2"), Labels.getProperty("streetTypes.square2Short")),
    MICRODISTRICT(Labels.getProperty("streetTypes.microdistrict"), Labels.getProperty("streetTypes.microdistrictShort"));


    private final String name;
    private final String shortName;

    StreetTypes(String name, String shortName) {
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
