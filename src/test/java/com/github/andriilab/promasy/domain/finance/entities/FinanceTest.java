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

class FinanceTest {

    private Finance finance;

    @BeforeEach
    void setUp() {
        finance = TestEntities.finance("F-1", "Grant", "100.00", "200.00", "300.00");
    }

    @ParameterizedTest
    @CsvSource({"MATERIALS,100.00", "EQUIPMENT,200.00", "SERVICES,300.00"})
    void getTotalAmount_returnsTotalOfRequestedType(BidType type, String expected) {
        assertThat(finance.getTotalAmount(type)).isEqualByComparingTo(new BigDecimal(expected));
    }

    @Test
    void toString_numberAndName_formatsBoth() {
        assertThat(finance).hasToString("(F-1) Grant");
    }

    @Test
    void toString_noNumberAndNoName_isEmpty() {
        assertThat(new Finance()).hasToString("");
    }

    @Test
    void description_isFinanceName() {
        finance.setDescription("Other");

        assertThat(finance.getFinanceName()).isEqualTo("Other");
        assertThat(finance.getDescription()).isEqualTo("Other");
    }

    // finance departments

    @Test
    void addFinanceDepartmentModel_newDepartment_isAddedWithBackReference() {
        FinanceDepartment department = TestEntities.financeDepartment(1, TestEntities.subdepartment("D", "S"));

        finance.addFinanceDepartmentModel(department);

        assertThat(finance.getFinanceDepartmentModels()).containsExactly(department);
        assertThat(department.getFinances()).isSameAs(finance);
    }

    @Test
    void addFinanceDepartmentModel_sameId_replacesExistingInstance() {
        FinanceDepartment original = TestEntities.financeDepartment(1, TestEntities.subdepartment("D", "S"));
        FinanceDepartment modified = TestEntities.financeDepartment(1, TestEntities.subdepartment("D", "S"));
        finance.addFinanceDepartmentModel(original);

        finance.addFinanceDepartmentModel(modified);

        assertThat(finance.getFinanceDepartmentModels()).hasSize(1);
        assertThat(finance.getFinanceDepartmentModels().get(0)).isSameAs(modified);
    }

    @Test
    void addFinanceDepartmentModel_differentId_keepsBoth() {
        FinanceDepartment first = TestEntities.financeDepartment(1, TestEntities.subdepartment("D", "S"));
        FinanceDepartment second = TestEntities.financeDepartment(2, TestEntities.subdepartment("D", "S"));

        finance.addFinanceDepartmentModel(first);
        finance.addFinanceDepartmentModel(second);

        assertThat(finance.getFinanceDepartmentModels()).containsExactly(first, second);
    }

    @Test
    void getFinanceDepartmentModelsSorted_ordersByDepartmentThenSubdepartment() {
        FinanceDepartment bA = TestEntities.financeDepartment(1, TestEntities.subdepartment("B", "A"));
        FinanceDepartment aZ = TestEntities.financeDepartment(2, TestEntities.subdepartment("A", "Z"));
        FinanceDepartment aB = TestEntities.financeDepartment(3, TestEntities.subdepartment("A", "B"));
        finance.addFinanceDepartmentModel(bA);
        finance.addFinanceDepartmentModel(aZ);
        finance.addFinanceDepartmentModel(aB);

        assertThat(finance.getFinanceDepartmentModelsSorted()).containsExactly(aB, aZ, bA);
    }

    // deactivation

    @Test
    void setActive_false_cascadesToDepartmentsAndTheirBids() {
        FinanceDepartment department = TestEntities.financeDepartment(1, TestEntities.subdepartment("D", "S"));
        Bid bid = TestEntities.bid(1, BidType.MATERIALS, "1", 1);
        department.addBid(bid);
        finance.addFinanceDepartmentModel(department);
        Employee employee = TestEntities.employee("Smith", "John", "Michael");

        finance.setActive(false, employee, TestEntities.timestamp(9));

        assertThat(finance.isActive()).isFalse();
        assertThat(department.isActive()).isFalse();
        assertThat(department.getModifiedEmployee()).isSameAs(employee);
        assertThat(bid.isActive()).isFalse();
        assertThat(bid.getModifiedDate()).isEqualTo(TestEntities.timestamp(9));
    }

    @Test
    void setActive_true_doesNotReactivateDepartments() {
        FinanceDepartment department = TestEntities.financeDepartment(1, TestEntities.subdepartment("D", "S"));
        finance.addFinanceDepartmentModel(department);
        finance.setActive(false, null, TestEntities.timestamp(8));

        finance.setActive(true, null, TestEntities.timestamp(9));

        assertThat(finance.isActive()).isTrue();
        assertThat(department.isActive()).isFalse();
    }
}
