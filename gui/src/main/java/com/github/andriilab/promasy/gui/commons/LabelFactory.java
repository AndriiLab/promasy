package com.github.andriilab.promasy.gui.commons;

public class LabelFactory {
    public static String toLabel(Role role){
        ADMIN(Labels.getProperty("role.admin")),
                DIRECTOR(Labels.getProperty("role.director")),
                DEPUTY_DIRECTOR(Labels.getProperty("role.deputyDirector")),
                HEAD_OF_TENDER_COMMITTEE(Labels.getProperty("role.headOfTenderCommittee")),
                SECRETARY_OF_TENDER_COMMITTEE(Labels.getProperty("role.secretaryOfTenderCommittee")),
                ECONOMIST(Labels.getProperty("role.economist")),
                ACCOUNTANT(Labels.getProperty("role.accountant")),
                HEAD_OF_DEPARTMENT(Labels.getProperty("role.headOfDepartment")),
                PERSONALLY_LIABLE_EMPLOYEE(Labels.getProperty("role.personallyLiableEmployee")),
                USER(Labels.getProperty("role.user"));
        // todo
    }

    public static String toLabel(BidType bidType){
        MATERIALS(Labels.getProperty("bidType.material"), Labels.getInt("default.bidType.materialKEKV")),
                EQUIPMENT(Labels.getProperty("bidType.equipment"), Labels.getInt("default.bidType.equipmentKEKV")),
                SERVICES(Labels.getProperty("bidType.services"), Labels.getInt("default.bidType.servicesKEKV"));
        private final String bidTypeName;
        private final Integer kekv;
        public int getKEKV() {
            if (kekv == null) {
                return type.getKekv();
            } else {
                return kekv;
            }
        }
        // todo
    }

    public static String toLabel(Fund fund){
        COMMON_FUND(Labels.getProperty("fund.commonFund")),
                SPECIAL_FUND(Labels.getProperty("fund.specialFund"));
        // todo
    }

    public static String toLabel(CityTypes type){
        CITY(Labels.getProperty("cityTypes.city"), Labels.withDot("cityTypes.cityShort")),
                URBAN_VILLAGE(Labels.getProperty("cityTypes.urbanVillage"), Labels.getProperty("cityTypes.urbanVillageShort")),
                SETTLEMENT(Labels.getProperty("cityTypes.settlement"), Labels.getProperty("cityTypes.settlementShort")),
                VILLAGE(Labels.getProperty("cityTypes.village"), Labels.withDot("cityTypes.villageShort"));

        private final String name;
        private final String shortName;
        // todo
    }

    public static String toLabel(StreetTypes type){
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

        // todo
    }

    public static String toLabel(Status type){
        CREATED(Labels.getProperty("status.created"), 0),
                SUBMITTED(Labels.getProperty("status.submitted"), 1),
                POSTED_IN_PROZORRO(Labels.getProperty("status.postedInProzorro"), 2),
                RECEIVED(Labels.getProperty("status.received"), 3),
                NOT_RECEIVED(Labels.getProperty("status.notReceived"), 4),
                DECLINED(Labels.getProperty("status.declined"), 5);
        private final String statusDesc;

        // todo
    }

    public static String toLabel(ProcurementProcedure type){
        BELOW_THRESHOLD(Labels.getProperty("procProc.belowThreshold")),
                OPEN_AUCTION(Labels.getProperty("procProc.openAuction")),
                OPEN_AUCTION_ENG(Labels.getProperty("procProc.openAuctionWEng")),
                CLOSED_CONTRACT_REPORT(Labels.getProperty("procProc.closedContractReport")),
                NEGOTIATING_PROCEDURE(Labels.getProperty("procProc.negotiatingProcedure")),
                NEGOTIATING_PROCEDURE_SHORT(Labels.getProperty("procProc.negotiatingProcedureShort")),
                NEGOTIATING_PROCEDURE_DEFENCE(Labels.getProperty("procProc.negotiatingProcedureDefence")),
                WITHOUT_ETS(Labels.getProperty("procProc.withoutETS")),
                COMPETITIVE_DIALOGUE(Labels.getProperty("procProc.competitiveDialogue")),
                COMPETITIVE_DIALOGUE_ENG(Labels.getProperty("procProc.competitiveDialogueWEng"));

        // todo
    }
}