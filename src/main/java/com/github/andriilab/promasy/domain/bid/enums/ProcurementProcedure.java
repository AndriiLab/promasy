package com.github.andriilab.promasy.domain.bid.enums;

import com.github.andriilab.promasy.presentation.commons.Labels;
import lombok.Getter;

/**
 * Types of procurement procedures
 */
public enum ProcurementProcedure {
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

    private final @Getter
    String description;

    ProcurementProcedure(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
