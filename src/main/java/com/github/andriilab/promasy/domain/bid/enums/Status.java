package com.github.andriilab.promasy.domain.bid.enums;

import com.github.andriilab.promasy.presentation.commons.Labels;
import lombok.Getter;

import java.util.Comparator;

/**
 * This Enum holds data about available bid statuses
 */
public enum Status {
    CREATED(Labels.getProperty("status.created"), 0),
    SUBMITTED(Labels.getProperty("status.submitted"), 1),
    POSTED_IN_PROZORRO(Labels.getProperty("status.postedInProzorro"), 2),
    RECEIVED(Labels.getProperty("status.received"), 3),
    NOT_RECEIVED(Labels.getProperty("status.notReceived"), 4),
    DECLINED(Labels.getProperty("status.declined"), 5);

    private final @Getter
    String statusDesc;
    private final @Getter
    int idNum;

    Status(String statusDesc, int idNum) {
        this.statusDesc = statusDesc;
        this.idNum = idNum;
    }

    @Override
    public String toString() {
        return statusDesc;
    }

    class StatusComparator implements Comparator<Status> {
        @Override
        public int compare(Status o1, Status o2) {
            Integer int1 = o1.getIdNum();
            Integer int2 = o2.getIdNum();
            return int1.compareTo(int2);
        }
    }


}
