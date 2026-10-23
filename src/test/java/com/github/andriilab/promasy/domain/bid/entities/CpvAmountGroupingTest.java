package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CpvAmountGroupingTest {

    private final List<String> lookups = new ArrayList<>();

    private final Function<String, Cpv> lookup = code -> {
        lookups.add(code);
        return new Cpv(code, "ukr " + code, "eng " + code, 2, false);
    };

    private static Bid bid(long id, BidType type, String cpvId, String onePrice, int amount) {
        Bid bid = TestEntities.bid(id, type, onePrice, amount);
        bid.setCpv(new Cpv(cpvId, "ukr " + cpvId, "eng " + cpvId, 8, true));
        return bid;
    }

    @Test
    void groupByCpvAndType_noBids_returnsEmptyListWithoutLookups() {
        assertThat(CpvAmount.groupByCpvAndType(Collections.<Bid>emptyList(), lookup)).isEmpty();
        assertThat(lookups).isEmpty();
    }

    @Test
    void groupByCpvAndType_bidsInSameFourDigitGroupAndType_areSummedIntoOneEntry() {
        Bid first = bid(1, BidType.MATERIALS, "03111100-2", "10.00", 2);
        Bid second = bid(2, BidType.MATERIALS, "03111900-8", "5.50", 4);

        List<CpvAmount> result = CpvAmount.groupByCpvAndType(Arrays.asList(first, second), lookup);

        assertThat(result).hasSize(1);
        CpvAmount group = result.get(0);
        assertThat(group.getTotalAmount()).isEqualByComparingTo(new BigDecimal("42.00"));
        assertThat(group.getBids()).containsExactly(first, second);
        assertThat(group.getType()).isEqualTo(BidType.MATERIALS);
    }

    @Test
    void groupByCpvAndType_lookup_receivesEightCharacterGroupCodeOncePerGroup() {
        Bid first = bid(1, BidType.MATERIALS, "03111100-2", "1", 1);
        Bid second = bid(2, BidType.MATERIALS, "03111900-8", "1", 1);
        Bid other = bid(3, BidType.MATERIALS, "09000000-3", "1", 1);

        List<CpvAmount> result = CpvAmount.groupByCpvAndType(Arrays.asList(first, second, other), lookup);

        assertThat(lookups).containsExactlyInAnyOrder("03110000", "09000000");
        assertThat(result).extracting(m -> m.getCpv().getCpvId()).containsExactly("03110000", "09000000");
    }

    @Test
    void groupByCpvAndType_sameCpvDifferentTypes_areKeptSeparate() {
        Bid materials = bid(1, BidType.MATERIALS, "03111000-2", "10", 1);
        Bid equipment = bid(2, BidType.EQUIPMENT, "03111000-2", "20", 1);

        List<CpvAmount> result = CpvAmount.groupByCpvAndType(Arrays.asList(materials, equipment), lookup);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(CpvAmount::getTotalAmount)
                .usingElementComparator(BigDecimal::compareTo)
                .containsExactlyInAnyOrder(new BigDecimal("10"), new BigDecimal("20"));
    }

    @Test
    void groupByCpvAndType_result_isSortedByTypeThenCpvCode() {
        Bid servicesA = bid(1, BidType.SERVICES, "03111000-2", "1", 1);
        Bid equipmentB = bid(2, BidType.EQUIPMENT, "09000000-3", "1", 1);
        Bid equipmentA = bid(3, BidType.EQUIPMENT, "03111000-2", "1", 1);
        Bid materialsB = bid(4, BidType.MATERIALS, "09000000-3", "1", 1);

        List<CpvAmount> result = CpvAmount.groupByCpvAndType(
                Arrays.asList(servicesA, equipmentB, equipmentA, materialsB), lookup);

        assertThat(result).extracting(m -> m.getType() + "/" + m.getCpv().getCpvId()).containsExactly(
                BidType.MATERIALS + "/09000000",
                BidType.EQUIPMENT + "/03110000",
                BidType.EQUIPMENT + "/09000000",
                BidType.SERVICES + "/03110000");
    }

    @Test
    void groupByCpvAndType_result_isUnmodifiable() {
        List<CpvAmount> result = CpvAmount.groupByCpvAndType(
                Collections.singletonList(bid(1, BidType.MATERIALS, "03111000-2", "1", 1)), lookup);

        assertThatThrownBy(() -> result.add(new CpvAmount())).isInstanceOf(UnsupportedOperationException.class);
    }
}
