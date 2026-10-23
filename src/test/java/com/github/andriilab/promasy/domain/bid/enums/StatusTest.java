package com.github.andriilab.promasy.domain.bid.enums;

import com.github.andriilab.promasy.app.commons.Labels;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class StatusTest {

    @ParameterizedTest
    @CsvSource({
            "CREATED,status.created,0",
            "SUBMITTED,status.submitted,1",
            "POSTED_IN_PROZORRO,status.postedInProzorro,2",
            "RECEIVED,status.received,3",
            "NOT_RECEIVED,status.notReceived,4",
            "DECLINED,status.declined,5"
    })
    void values_takeDescriptionFromLabelsBundleAndHaveFixedId(Status status, String key, int idNum) {
        assertThat(status.getStatusDesc()).isEqualTo(Labels.getProperty(key));
        assertThat(status.getIdNum()).isEqualTo(idNum);
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    void toString_isLocalizedDescription(Status status) {
        assertThat(status).hasToString(status.getStatusDesc());
        assertThat(status.getStatusDesc()).isNotBlank();
    }

    @Test
    void idNums_areUniqueAndFollowDeclarationOrder() {
        Status[] values = Status.values();

        for (int i = 0; i < values.length; i++) {
            assertThat(values[i].getIdNum()).isEqualTo(i);
        }
    }
}
