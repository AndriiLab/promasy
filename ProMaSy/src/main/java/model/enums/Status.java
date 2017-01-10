package model.enums;

import gui.Labels;

/**
 * This Enum holds data about available bid statuses
 */
public enum Status {
    CREATED(Labels.getProperty("status.created")),
    SUBMITTED(Labels.getProperty("status.submitted")),
    POSTED_IN_PROZORRO(Labels.getProperty("status.postedInProzorro")),
    RECEIVED(Labels.getProperty("status.received")),
    NOT_RECEIVED(Labels.getProperty("status.notReceived")),
    DECLINED(Labels.getProperty("status.declined"));

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
