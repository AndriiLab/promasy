package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.bid.enums.Status;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BidTest {

    private Bid bid;

    @BeforeEach
    void setUp() {
        bid = TestEntities.bid(1, BidType.MATERIALS, "12.50", 3);
    }

    private BidStatus status(Status status, int dayOfJanuary) {
        BidStatus bidStatus = new BidStatus(status, bid);
        bidStatus.setCreatedDate(TestEntities.timestamp(dayOfJanuary));
        return bidStatus;
    }

    // total price

    @Test
    void getTotalPrice_multipliesOnePriceByAmount() {
        assertThat(bid.getTotalPrice()).isEqualByComparingTo(new BigDecimal("37.50"));
    }

    @Test
    void getTotalPrice_zeroAmount_isZero() {
        bid.setAmount(0);

        assertThat(bid.getTotalPrice()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    // KEKV

    @ParameterizedTest
    @EnumSource(BidType.class)
    void getKEKV_noCustomValue_returnsDefaultOfBidType(BidType type) {
        bid.setType(type);

        assertThat(bid.getKEKV()).isEqualTo(type.getKekv());
    }

    @Test
    void setKEKV_customValue_overridesDefault() {
        bid.setKEKV(9999);

        assertThat(bid.getKEKV()).isEqualTo(9999);
    }

    @Test
    void setKEKV_defaultOfCurrentType_isStoredAsNullAndFollowsTypeChange() {
        bid.setKEKV(BidType.MATERIALS.getKekv());

        bid.setType(BidType.EQUIPMENT);

        assertThat(bid.getKEKV()).isEqualTo(BidType.EQUIPMENT.getKekv());
    }

    @Test
    void setKEKV_customValue_survivesTypeChange() {
        bid.setKEKV(9999);

        bid.setType(BidType.EQUIPMENT);

        assertThat(bid.getKEKV()).isEqualTo(9999);
    }

    @Test
    void setKEKV_defaultAfterCustomValue_resetsToDefault() {
        bid.setKEKV(9999);

        bid.setKEKV(BidType.MATERIALS.getKekv());

        assertThat(bid.getKEKV()).isEqualTo(BidType.MATERIALS.getKekv());
    }

    // transient amount

    @Test
    void getTransientAmount_notSet_returnsAmount() {
        assertThat(bid.getTransientAmount()).isEqualTo(3);
    }

    @Test
    void getTransientAmount_afterSet_returnsSetValue() {
        bid.setTransientAmount(2);

        assertThat(bid.getTransientAmount()).isEqualTo(2);
    }

    @Test
    void getTransientAmount_afterAmountChange_keepsFirstReadValueUntilCleared() {
        assertThat(bid.getTransientAmount()).isEqualTo(3);

        bid.setAmount(7);

        assertThat(bid.getTransientAmount()).isEqualTo(3);
    }

    @Test
    void clearTransientAmount_resetsToCurrentAmount() {
        bid.setTransientAmount(2);
        bid.setAmount(7);

        bid.clearTransientAmount();

        assertThat(bid.getTransientAmount()).isEqualTo(7);
    }

    // statuses

    @Test
    void addStatus_addsToListAndSetsBackReference() {
        BidStatus bidStatus = new BidStatus();

        bid.addStatus(bidStatus);

        assertThat(bid.getStatuses()).containsExactly(bidStatus);
        assertThat(bidStatus.getBid()).isSameAs(bid);
    }

    @Test
    void getLastBidStatusModel_singleStatus_returnsIt() {
        BidStatus only = status(Status.CREATED, 1);
        bid.getStatuses().add(only);

        assertThat(bid.getLastBidStatusModel()).isSameAs(only);
    }

    @Test
    void getLastBidStatusModel_unorderedStatuses_returnsLatestByCreatedDate() {
        BidStatus latest = status(Status.RECEIVED, 5);
        bid.getStatuses().add(status(Status.CREATED, 1));
        bid.getStatuses().add(latest);
        bid.getStatuses().add(status(Status.SUBMITTED, 3));

        assertThat(bid.getLastBidStatusModel()).isSameAs(latest);
    }

    // deactivation

    @Test
    void setActive_false_deactivatesStatusesWithAuditFields() {
        BidStatus first = status(Status.CREATED, 1);
        BidStatus second = status(Status.SUBMITTED, 2);
        bid.getStatuses().add(first);
        bid.getStatuses().add(second);
        Employee employee = TestEntities.employee("Smith", "John", "Michael");

        bid.setActive(false, employee, TestEntities.timestamp(9));

        assertThat(bid.isActive()).isFalse();
        for (BidStatus s : bid.getStatuses()) {
            assertThat(s.isActive()).isFalse();
            assertThat(s.getModifiedEmployee()).isSameAs(employee);
            assertThat(s.getModifiedDate()).isEqualTo(TestEntities.timestamp(9));
        }
    }

    @Test
    void setActive_true_doesNotTouchStatuses() {
        BidStatus bidStatus = status(Status.CREATED, 1);
        bid.getStatuses().add(bidStatus);
        bid.setActive(false, null, TestEntities.timestamp(8));

        bid.setActive(true, null, TestEntities.timestamp(9));

        assertThat(bid.isActive()).isTrue();
        assertThat(bidStatus.isActive()).isFalse();
    }

    // misc

    @Test
    void toString_returnsDescription() {
        bid.setDescription("Pipette");

        assertThat(bid).hasToString("Pipette");
    }
}
