package model.enums;

import gui.Labels;

/**
 * This Enum holds data about available bid statuses
 */
public enum Status {
    CREATED(Labels.getProperty("created")),
    SUBMITTED(Labels.getProperty("submitted")),
    POSTED_IN_PROZORRO(Labels.getProperty("postedInProzorro")),
    RECEIVED(Labels.getProperty("received")),
    NOT_RECEIVED(Labels.getProperty("notReceived")),
    DECLINED(Labels.getProperty("declined"));

    private final String statusDesc;

    Status(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    @Override
    public String toString() {
        return statusDesc;
    }
}
