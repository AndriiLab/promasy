package main.java.gui.bids.status;

/**
 * This Enum holds data about available bid statuses
 */
public enum Status {
    CREATED(10, "Створено"),
    SUBMITTED(20, "Подано"),
    POSTED_IN_PROZORRO(50, "Розміщено на Prozorro"),
    RECEIVED(60, "Отримано"),
    NOT_RECEIVED(80, "Не отримано"),
    DECLINED(90, "Відхилено");

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
