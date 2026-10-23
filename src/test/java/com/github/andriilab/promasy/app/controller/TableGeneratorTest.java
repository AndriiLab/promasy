package com.github.andriilab.promasy.app.controller;

import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TableGeneratorTest {

    private static final String CPV_A = "03111000";
    private static final String CPV_B = "09000000";

    private static AmountUnit unit(long id, String description) {
        AmountUnit unit = new AmountUnit(description);
        unit.setModelId(id);
        return unit;
    }

    private static Bid bid(long id, BidType type, String cpvId, AmountUnit unit, String onePrice, int amount) {
        Bid bid = TestEntities.bid(id, type, onePrice, amount);
        bid.setCpv(new Cpv(cpvId, "ukr " + cpvId, "eng " + cpvId, 1, false));
        bid.setAmountUnit(unit);
        return bid;
    }

    @Test
    void preSort_emptyList_returnsEmptyList() {
        assertThat(TableGenerator.preSort(Collections.<Bid>emptyList())).isEmpty();
    }

    @Test
    void preSort_sameCpvKekvUnitAndPrice_mergesIntoFirstBidWithSummedAmount() {
        AmountUnit pcs = unit(1, "pcs");
        Bid first = bid(1, BidType.MATERIALS, CPV_A, pcs, "10.00", 2);
        Bid second = bid(2, BidType.MATERIALS, CPV_A, pcs, "10.00", 3);

        List<Bid> result = TableGenerator.preSort(Arrays.asList(first, second));

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isSameAs(first);
        assertThat(result.get(0).getTransientAmount()).isEqualTo(5);
    }

    @Test
    void preSort_differentPrice_keepsBidsSeparate() {
        AmountUnit pcs = unit(1, "pcs");
        Bid cheap = bid(1, BidType.MATERIALS, CPV_A, pcs, "10.00", 2);
        Bid expensive = bid(2, BidType.MATERIALS, CPV_A, pcs, "11.00", 3);

        assertThat(TableGenerator.preSort(Arrays.asList(cheap, expensive))).hasSize(2);
    }

    @Test
    void preSort_differentCpv_keepsBidsSeparate() {
        AmountUnit pcs = unit(1, "pcs");
        Bid a = bid(1, BidType.MATERIALS, CPV_A, pcs, "10.00", 2);
        Bid b = bid(2, BidType.MATERIALS, CPV_B, pcs, "10.00", 3);

        assertThat(TableGenerator.preSort(Arrays.asList(a, b))).hasSize(2);
    }

    @Test
    void preSort_differentKekv_keepsBidsSeparate() {
        AmountUnit pcs = unit(1, "pcs");
        Bid materials = bid(1, BidType.MATERIALS, CPV_A, pcs, "10.00", 2);
        Bid equipment = bid(2, BidType.EQUIPMENT, CPV_A, pcs, "10.00", 3);

        assertThat(TableGenerator.preSort(Arrays.asList(materials, equipment))).hasSize(2);
    }

    @Test
    void preSort_unitsWithCollidingStringHashCodes_keepsBidsSeparate() {
        // "Aa" and "BB" share the same String.hashCode()
        Bid aa = bid(1, BidType.MATERIALS, CPV_A, unit(1, "Aa"), "10.00", 2);
        Bid bb = bid(2, BidType.MATERIALS, CPV_A, unit(2, "BB"), "10.00", 3);
        assertThat("Aa".hashCode()).isEqualTo("BB".hashCode());

        List<Bid> result = TableGenerator.preSort(Arrays.asList(aa, bb));

        assertThat(result).containsExactlyInAnyOrder(aa, bb);
        assertThat(aa.getTransientAmount()).isEqualTo(2);
        assertThat(bb.getTransientAmount()).isEqualTo(3);
    }

    @Test
    void preSort_unitAndPriceThatConcatenateToSameText_keepsBidsSeparate() {
        // unit "m2" + price "10" and unit "m" + price "210" both read "m210" without a separator
        Bid first = bid(1, BidType.MATERIALS, CPV_A, unit(1, "m2"), "10", 1);
        Bid second = bid(2, BidType.MATERIALS, CPV_A, unit(2, "m"), "210", 1);

        assertThat(TableGenerator.preSort(Arrays.asList(first, second))).hasSize(2);
    }

    @Test
    void preSort_mixedBids_sortsByKekvThenCpvThenUnitId() {
        AmountUnit unit1 = unit(1, "pcs");
        AmountUnit unit2 = unit(2, "kg");
        Bid services = bid(1, BidType.SERVICES, CPV_A, unit1, "5", 1);
        Bid equipmentB = bid(2, BidType.EQUIPMENT, CPV_B, unit1, "5", 1);
        Bid equipmentAUnit2 = bid(3, BidType.EQUIPMENT, CPV_A, unit2, "5", 1);
        Bid equipmentAUnit1 = bid(4, BidType.EQUIPMENT, CPV_A, unit1, "5", 1);
        Bid materials = bid(5, BidType.MATERIALS, CPV_B, unit2, "5", 1);

        List<Bid> result = TableGenerator.preSort(Arrays.asList(services, equipmentB, equipmentAUnit2, equipmentAUnit1, materials));

        assertThat(result).extracting(Bid::getKEKV).isSorted();
        assertThat(result.stream().filter(b -> b.getType() == BidType.EQUIPMENT)).containsExactly(equipmentAUnit1, equipmentAUnit2, equipmentB);
        assertThat(result).hasSize(5);
    }
}
