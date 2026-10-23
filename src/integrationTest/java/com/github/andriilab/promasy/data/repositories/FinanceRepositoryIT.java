package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.finance.GetFinanceAssignedAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceSpentAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinancesQuery;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.it.AbstractIntegrationTest;
import com.github.andriilab.promasy.it.ItFixtures;
import com.github.andriilab.promasy.it.Sql;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The finance queries on real SQL: which finances cover a year, the per-type amounts, and the active filters.
 */
class FinanceRepositoryIT extends AbstractIntegrationTest {

    private static List<String> numbers(List<Finance> finances) {
        return finances.stream().map(Finance::getFinanceNumber).collect(Collectors.toList());
    }

    private Subdepartment newSubdepartment(String name) {
        Institute institute = ItFixtures.institute(storage, "Institute " + name);
        Department department = ItFixtures.department(storage, institute, "Department " + name);
        return ItFixtures.subdepartment(storage, department, "Subdepartment " + name);
    }

    @Test
    void get_byYear_returnsFinancesWhoseYearsCoverTheYearIncludingBoundariesOrderedByNumber() {
        ItFixtures.finance(storage, "5-ends-2023", "2022-06-01", "2023-12-31", "1", "1", "1");
        ItFixtures.finance(storage, "3-spans", "2023-01-01", "2025-12-31", "1", "1", "1");
        ItFixtures.finance(storage, "4-starts-2024", "2024-01-01", "2024-01-02", "1", "1", "1");
        ItFixtures.finance(storage, "1-before", "2020-01-01", "2022-12-31", "1", "1", "1");
        ItFixtures.finance(storage, "2-after", "2025-01-01", "2026-12-31", "1", "1", "1");

        assertThat(numbers(storage.FINANCES.get(new GetFinancesQuery(2023))))
                .containsExactly("3-spans", "5-ends-2023");
        assertThat(numbers(storage.FINANCES.get(new GetFinancesQuery(2024))))
                .containsExactly("3-spans", "4-starts-2024");
        assertThat(numbers(storage.FINANCES.get(new GetFinancesQuery(2022))))
                .containsExactly("1-before", "5-ends-2023");
        assertThat(numbers(storage.FINANCES.get(new GetFinancesQuery(2019)))).isEmpty();
        assertThat(numbers(storage.FINANCES.get(new GetFinancesQuery(2027)))).isEmpty();
    }

    @Test
    void get_byYear_leavesOutSoftDeletedFinances() {
        ItFixtures.finance(storage, "A", "2024-01-01", "2024-12-31", "1", "1", "1");
        Finance removed = ItFixtures.finance(storage, "B", "2024-01-01", "2024-12-31", "1", "1", "1");
        storage.FINANCES.delete(removed);

        assertThat(numbers(storage.FINANCES.get(new GetFinancesQuery(2024)))).containsExactly("A");
        assertThat(numbers(storage.FINANCES.getResults())).containsExactly("A");
    }

    @Test
    void get_byDepartmentAndYear_returnsOnlyFinancesAssignedToThatDepartment() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Department department = ItFixtures.department(storage, institute, "Department");
        Department other = ItFixtures.department(storage, institute, "Other department");
        Subdepartment first = ItFixtures.subdepartment(storage, department, "First");
        Subdepartment second = ItFixtures.subdepartment(storage, department, "Second");
        Subdepartment foreign = ItFixtures.subdepartment(storage, other, "Foreign");
        Finance both = ItFixtures.finance(storage, "2-both", "2024-01-01", "2024-12-31", "10", "10", "10");
        Finance single = ItFixtures.finance(storage, "1-single", "2024-01-01", "2024-12-31", "10", "10", "10");
        Finance otherYear = ItFixtures.finance(storage, "3-other-year", "2020-01-01", "2020-12-31", "10", "10", "10");
        Finance foreignOnly = ItFixtures.finance(storage, "4-foreign", "2024-01-01", "2024-12-31", "10", "10", "10");
        Finance removedAssignment = ItFixtures.finance(storage, "5-removed", "2024-01-01", "2024-12-31", "10", "10", "10");
        ItFixtures.financeDepartment(storage, both, first, "1", "1", "1");
        ItFixtures.financeDepartment(storage, both, second, "1", "1", "1");
        ItFixtures.financeDepartment(storage, single, first, "1", "1", "1");
        ItFixtures.financeDepartment(storage, otherYear, first, "1", "1", "1");
        ItFixtures.financeDepartment(storage, foreignOnly, foreign, "1", "1", "1");
        FinanceDepartment removed = ItFixtures.financeDepartment(storage, removedAssignment, first, "1", "1", "1");
        storage.DEPARTMENT_FINANCES.delete(removed);

        List<Finance> finances = storage.FINANCES.get(new GetFinancesQuery(2024, department));

        // "2-both" is assigned to two subdepartments of the department but is one finance; "5-removed" lost its only
        // assignment to that department
        assertThat(numbers(finances)).containsExactly("1-single", "2-both");
    }

    @ParameterizedTest
    @EnumSource(BidType.class)
    void retrieveAssignedAmount_sumsActiveFinanceDepartmentsOfThatTypeOnly(BidType type) {
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "2000", "3000");
        Finance other = ItFixtures.finance(storage, "Other", "2024-01-01", "2024-12-31", "1000", "2000", "3000");
        Subdepartment first = newSubdepartment("1");
        Subdepartment second = newSubdepartment("2");
        Subdepartment third = newSubdepartment("3");
        ItFixtures.financeDepartment(storage, finance, first, "100", "200", "300");
        ItFixtures.financeDepartment(storage, finance, second, "10", "20", "30");
        FinanceDepartment removed = ItFixtures.financeDepartment(storage, finance, third, "1", "2", "3");
        ItFixtures.financeDepartment(storage, other, first, "7", "7", "7");
        storage.DEPARTMENT_FINANCES.delete(removed);

        BigDecimal assigned = storage.FINANCES.retrieveAssignedAmount(new GetFinanceAssignedAmountQuery(finance, type));

        assertThat(assigned).isEqualByComparingTo(expectedForType(type, "110", "220", "330"));
    }

    @ParameterizedTest
    @EnumSource(BidType.class)
    void retrieveAssignedAmount_nothingAssigned_returnsZeroInsteadOfNull(BidType type) {
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "2000", "3000");

        assertThat(storage.FINANCES.retrieveAssignedAmount(new GetFinanceAssignedAmountQuery(finance, type)))
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @ParameterizedTest
    @EnumSource(BidType.class)
    void retrieveUnassignedAmount_isTheFinanceTotalMinusWhatIsAssigned(BidType type) {
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "2000", "3000");
        ItFixtures.financeDepartment(storage, finance, newSubdepartment("1"), "100", "200", "300");
        ItFixtures.financeDepartment(storage, finance, newSubdepartment("2"), "10", "20", "30");

        BigDecimal unassigned = storage.FINANCES.retrieveUnassignedAmount(new GetFinanceUnassignedAmountQuery(finance, type));

        assertThat(unassigned).isEqualByComparingTo(expectedForType(type, "890", "1780", "2670"));
    }

    @ParameterizedTest
    @EnumSource(BidType.class)
    void retrieveSpentAmount_sumsPriceTimesAmountOfActiveBidsOfThatTypeAcrossDepartments(BidType type) {
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "2000", "3000");
        Finance other = ItFixtures.finance(storage, "Other", "2024-01-01", "2024-12-31", "1000", "2000", "3000");
        FinanceDepartment first = ItFixtures.financeDepartment(storage, finance, newSubdepartment("1"), "500", "500", "500");
        FinanceDepartment second = ItFixtures.financeDepartment(storage, finance, newSubdepartment("2"), "500", "500", "500");
        FinanceDepartment foreign = ItFixtures.financeDepartment(storage, other, newSubdepartment("3"), "500", "500", "500");
        for (BidType bidType : BidType.values()) {
            ItFixtures.bid(storage, first, bidType, "10.50", 4);
            ItFixtures.bid(storage, second, bidType, "100", 2);
            ItFixtures.bid(storage, foreign, bidType, "1000", 1);
            storage.BIDS.delete(ItFixtures.bid(storage, first, bidType, "5000", 1));
        }

        BigDecimal spent = storage.FINANCES.retrieveSpentAmount(new GetFinanceSpentAmountQuery(finance, type));

        assertThat(spent).isEqualByComparingTo("242.00");
    }

    @ParameterizedTest
    @EnumSource(BidType.class)
    void retrieveSpentAmount_noBids_returnsZeroInsteadOfNull(BidType type) {
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "2000", "3000");

        assertThat(storage.FINANCES.retrieveSpentAmount(new GetFinanceSpentAmountQuery(finance, type)))
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @ParameterizedTest
    @EnumSource(BidType.class)
    void retrieveLeftAmount_isTheFinanceTotalMinusWhatIsSpent(BidType type) {
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "2000", "3000");
        FinanceDepartment department = ItFixtures.financeDepartment(storage, finance, newSubdepartment("1"), "500", "500", "500");
        Bid bid = ItFixtures.bid(storage, department, type, "25.25", 4);

        BigDecimal left = storage.FINANCES.retrieveLeftAmount(new GetFinanceLeftAmountQuery(finance, type));

        assertThat(bid.getTotalPrice()).isEqualByComparingTo("101.00");
        assertThat(left).isEqualByComparingTo(expectedForType(type, "899", "1899", "2899"));
    }

    @Test
    void deleteFinance_withDepartmentsAndBids_deactivatesTheWholeBranchAndFreesItsAmounts() {
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "2000", "3000");
        FinanceDepartment department = ItFixtures.financeDepartment(storage, finance, newSubdepartment("1"), "500", "500", "500");
        Bid bid = ItFixtures.bid(storage, department, BidType.MATERIALS, "10", 5);

        storage.FINANCES.delete(finance);

        assertThat(Sql.scalar("SELECT active FROM promasy.finances WHERE id = ?", finance.getModelId())).isEqualTo(false);
        assertThat(Sql.scalar("SELECT active FROM promasy.finance_dep WHERE id = ?", department.getModelId()))
                .isEqualTo(false);
        assertThat(Sql.scalar("SELECT active FROM promasy.bids WHERE id = ?", bid.getModelId())).isEqualTo(false);
        assertThat(storage.FINANCES.retrieveAssignedAmount(new GetFinanceAssignedAmountQuery(finance, BidType.MATERIALS)))
                .isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(storage.FINANCES.retrieveSpentAmount(new GetFinanceSpentAmountQuery(finance, BidType.MATERIALS)))
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void create_finance_persistsAllColumnsIncludingEnumAndDates() {
        Finance finance = ItFixtures.finance(storage, "F-1", "2024-02-03", "2025-04-05", "1.10", "2.20", "3.30");

        assertThat(Sql.scalar("SELECT number FROM promasy.finances WHERE id = ?", finance.getModelId())).isEqualTo("F-1");
        assertThat(Sql.scalar("SELECT fundtype FROM promasy.finances WHERE id = ?", finance.getModelId()))
                .isEqualTo("COMMON_FUND");
        assertThat(Sql.scalar("SELECT starts_on::text FROM promasy.finances WHERE id = ?", finance.getModelId()))
                .isEqualTo("2024-02-03");
        assertThat(Sql.scalar("SELECT due_to::text FROM promasy.finances WHERE id = ?", finance.getModelId()))
                .isEqualTo("2025-04-05");
        assertThat((BigDecimal) Sql.scalar("SELECT total_equpment FROM promasy.finances WHERE id = ?", finance.getModelId()))
                .isEqualByComparingTo("2.20");
    }

    private static BigDecimal expectedForType(BidType type, String materials, String equipment, String services) {
        switch (type) {
            case MATERIALS:
                return new BigDecimal(materials);
            case EQUIPMENT:
                return new BigDecimal(equipment);
            default:
                return new BigDecimal(services);
        }
    }
}
