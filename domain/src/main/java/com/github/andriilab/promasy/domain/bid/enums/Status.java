package com.github.andriilab.promasy.domain.bid.enums;

import lombok.Getter;

import java.util.Comparator;

/**
 * This Enum holds persistence about available bid statuses
 */
public enum Status {
    CREATED(0),
    SUBMITTED(1),
    POSTED_IN_PROZORRO(2),
    RECEIVED(3),
    NOT_RECEIVED(4),
    DECLINED(5);

    @Getter    private final int idNum;

    Status(int idNum) {
        this.idNum = idNum;
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
