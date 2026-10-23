package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CpvAmountTest {

    private final Cpv cpv = new Cpv("33100000-1", "Medical equipment", "Medical equipment", 2, false);

    @Test
    void constructor_withBid_storesFieldsAndAddsBid() {
        Bid bid = TestEntities.bid(1, BidType.MATERIALS, "10", 1);

        CpvAmount amount = new CpvAmount(cpv, BidType.MATERIALS, new BigDecimal("10"), bid);

        assertThat(amount.getCpv()).isSameAs(cpv);
        assertThat(amount.getType()).isEqualTo(BidType.MATERIALS);
        assertThat(amount.getTotalAmount()).isEqualByComparingTo("10");
        assertThat(amount.getBids()).containsExactly(bid);
    }

    @Test
    void constructor_noArgs_startsEmptyWithZeroTotal() {
        CpvAmount amount = new CpvAmount();

        assertThat(amount.getBids()).isEmpty();
        assertThat(amount.getTotalAmount()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void addToTotalAmount_noArgsInstance_addsToZero() {
        CpvAmount amount = new CpvAmount();

        amount.addToTotalAmount(new BigDecimal("2.50"));

        assertThat(amount.getTotalAmount()).isEqualByComparingTo("2.50");
    }

    @Test
    void addToTotalAmount_calledTwice_accumulates() {
        CpvAmount amount = new CpvAmount(cpv, BidType.SERVICES, new BigDecimal("10"), TestEntities.bid(1, BidType.SERVICES, "10", 1));

        amount.addToTotalAmount(new BigDecimal("5"));
        amount.addToTotalAmount(new BigDecimal("0.25"));

        assertThat(amount.getTotalAmount()).isEqualByComparingTo("15.25");
    }

    @Test
    void addBidModel_newBid_isAppended() {
        Bid first = TestEntities.bid(1, BidType.MATERIALS, "1", 1);
        Bid second = TestEntities.bid(2, BidType.MATERIALS, "1", 1);
        CpvAmount amount = new CpvAmount(cpv, BidType.MATERIALS, BigDecimal.ONE, first);

        amount.addBidModel(second);

        assertThat(amount.getBids()).containsExactly(first, second);
    }

    @Test
    void addBidModel_bidWithSameId_replacesExistingInstance() {
        Bid original = TestEntities.bid(1, BidType.MATERIALS, "1", 1);
        Bid replacement = TestEntities.bid(1, BidType.MATERIALS, "2", 1);
        CpvAmount amount = new CpvAmount(cpv, BidType.MATERIALS, BigDecimal.ONE, original);

        amount.addBidModel(replacement);

        assertThat(amount.getBids()).hasSize(1);
        assertThat(amount.getBids().get(0)).isSameAs(replacement);
    }

    @Test
    void toString_containsCpvCodeTypeAndTotal() {
        CpvAmount amount = new CpvAmount(cpv, BidType.EQUIPMENT, new BigDecimal("7"), TestEntities.bid(1, BidType.EQUIPMENT, "7", 1));

        assertThat(amount.toString()).contains("33100000-1", BidType.EQUIPMENT.toString(), "7");
    }
}
