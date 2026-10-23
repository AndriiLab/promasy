package com.github.andriilab.promasy.domain.finance.entities;

import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class FinanceDepartmentTest {

    private FinanceDepartment department;

    @BeforeEach
    void setUp() {
        department = TestEntities.financeDepartment(1, TestEntities.subdepartment("D", "S"));
    }

    private static Bid bid(long modelId, BidType type) {
        return TestEntities.bid(modelId, type, "1", 1);
    }

    // bids

    @Test
    void getActiveBids_excludesDeactivatedBids() {
        Bid active = bid(1, BidType.MATERIALS);
        Bid inactive = bid(2, BidType.MATERIALS);
        department.addBid(active);
        department.addBid(inactive);
        inactive.setActive(false, null, TestEntities.timestamp(1));

        assertThat(department.getActiveBids()).containsExactly(active);
    }

    @Test
    void getBids_byType_returnsOnlyBidsOfThatType() {
        Bid material = bid(1, BidType.MATERIALS);
        Bid service = bid(2, BidType.SERVICES);
        department.addBid(material);
        department.addBid(service);

        assertThat(department.getBids(BidType.SERVICES)).containsExactly(service);
        assertThat(department.getBids(BidType.EQUIPMENT)).isEmpty();
    }

    @Test
    void addBid_newBid_isAddedWithBackReference() {
        Bid bid = bid(1, BidType.MATERIALS);

        department.addBid(bid);

        assertThat(department.getBids()).containsExactly(bid);
        assertThat(bid.getFinances()).isSameAs(department);
    }

    @Test
    void addBid_bidOfAnotherDepartment_isMovedHere() {
        FinanceDepartment other = TestEntities.financeDepartment(2, TestEntities.subdepartment("D", "S2"));
        Bid bid = bid(1, BidType.MATERIALS);
        other.addBid(bid);

        department.addBid(bid);

        assertThat(other.getBids()).isEmpty();
        assertThat(department.getBids()).containsExactly(bid);
        assertThat(bid.getFinances()).isSameAs(department);
    }

    @Test
    void addBid_sameBidTwice_keepsSingleEntry() {
        Bid bid = bid(1, BidType.MATERIALS);

        department.addBid(bid);
        department.addBid(bid);

        assertThat(department.getBids()).containsExactly(bid);
    }

    @Test
    void addBid_sameIdAsExistingBid_replacesExistingInstance() {
        Bid original = bid(1, BidType.MATERIALS);
        Bid reloaded = bid(1, BidType.MATERIALS);
        department.addBid(original);
        original.setFinances(null);

        department.addBid(reloaded);

        assertThat(department.getBids()).hasSize(1);
        assertThat(department.getBids().get(0)).isSameAs(reloaded);
    }

    // amounts

    @ParameterizedTest
    @CsvSource({"MATERIALS,10.00", "EQUIPMENT,20.00", "SERVICES,30.00"})
    void getTotalAmount_returnsAmountOfRequestedType(BidType type, String expected) {
        department.setTotalMaterialsAmount(new BigDecimal("10.00"));
        department.setTotalEquipmentAmount(new BigDecimal("20.00"));
        department.setTotalServicesAmount(new BigDecimal("30.00"));

        assertThat(department.getTotalAmount(type)).isEqualByComparingTo(new BigDecimal(expected));
    }

    // misc

    @Test
    void toString_withoutFinance_isEmpty() {
        assertThat(department).hasToString("");
    }

    @Test
    void toString_withFinance_delegatesToFinance() {
        department.setFinances(TestEntities.finance("F-1", "Grant", "1", "1", "1"));

        assertThat(department).hasToString("(F-1) Grant");
    }

    @Test
    void description_isAlwaysEmpty() {
        department.setDescription("ignored");

        assertThat(department.getDescription()).isEmpty();
    }

    @Test
    void setActive_false_deactivatesBidsWithAuditFields() {
        Bid bid = bid(1, BidType.MATERIALS);
        department.addBid(bid);
        Employee employee = TestEntities.employee("Smith", "John", "Michael");

        department.setActive(false, employee, TestEntities.timestamp(9));

        assertThat(department.isActive()).isFalse();
        assertThat(bid.isActive()).isFalse();
        assertThat(bid.getModifiedEmployee()).isSameAs(employee);
        assertThat(bid.getModifiedDate()).isEqualTo(TestEntities.timestamp(9));
    }
}
