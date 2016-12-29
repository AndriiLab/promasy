package model.enums;

import gui.Labels;

/**
 * This Enum holds data about available bid statuses
 */
public enum Status {
    CREATED(10, Labels.getProperty("created")),
    SUBMITTED(20, Labels.getProperty("submitted")),
    POSTED_IN_PROZORRO(50, Labels.getProperty("postedInProzorro")),
    RECEIVED(60, Labels.getProperty("received")),
    NOT_RECEIVED(80, Labels.getProperty("notReceived")),
    DECLINED(90, Labels.getProperty("declined"));

    private final int statusId;
    private final String statusDesc;

    Status(int statusId, String statusDesc) {
        this.statusId = statusId;
        this.statusDesc = statusDesc;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    @Override
    public String toString() {
        return statusDesc;
    }
}
