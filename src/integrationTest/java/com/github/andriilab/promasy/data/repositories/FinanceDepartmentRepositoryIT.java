package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentSpentAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentsQuery;
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
 * The finance-per-subdepartment queries on real SQL: lookups by finance and by subdepartment for a year, the ordering,
 * the active filter and the spent and left amounts.
 */
class FinanceDepartmentRepositoryIT extends AbstractIntegrationTest {

    private static List<String> financeNumbers(List<FinanceDepartment> financeDepartments) {
        return financeDepartments.stream().map(fd -> fd.getFinances().getFinanceNumber()).collect(Collectors.toList());
    }

    private static List<String> subdepartmentNames(List<FinanceDepartment> financeDepartments) {
        return financeDepartments.stream().map(fd -> fd.getSubdepartment().getSubdepName()).collect(Collectors.toList());
    }

    @Test
    void get_bySubdepartment_returnsActiveAssignmentsCoveringTheYearOrderedByFinanceNumber() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Department department = ItFixtures.department(storage, institute, "Department");
        Subdepartment subdepartment = ItFixtures.subdepartment(storage, department, "Subdepartment");
        Subdepartment other = ItFixtures.subdepartment(storage, department, "Other");
        Finance b = ItFixtures.finance(storage, "B", "2024-01-01", "2024-12-31", "1", "1", "1");
        Finance a = ItFixtures.finance(storage, "A", "2023-12-31", "2024-01-01", "1", "1", "1");
        Finance past = ItFixtures.finance(storage, "Past", "2020-01-01", "2023-12-31", "1", "1", "1");
        Finance removed = ItFixtures.finance(storage, "Removed", "2024-01-01", "2024-12-31", "1", "1", "1");
        Finance foreign = ItFixtures.finance(storage, "Foreign", "2024-01-01", "2024-12-31", "1", "1", "1");
        ItFixtures.financeDepartment(storage, b, subdepartment, "1", "1", "1");
        ItFixtures.financeDepartment(storage, a, subdepartment, "1", "1", "1");
        ItFixtures.financeDepartment(storage, past, subdepartment, "1", "1", "1");
        storage.DEPARTMENT_FINANCES.delete(ItFixtures.financeDepartment(storage, removed, subdepartment, "1", "1", "1"));
        ItFixtures.financeDepartment(storage, foreign, other, "1", "1", "1");

        List<FinanceDepartment> result = storage.DEPARTMENT_FINANCES.get(new GetFinanceDepartmentsQuery(2024, subdepartment));

        assertThat(financeNumbers(result)).containsExactly("A", "B");
    }

    @Test
    void get_byFinance_returnsActiveAssignmentsOrderedByDepartmentThenSubdepartmentName() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Department departmentB = ItFixtures.department(storage, institute, "B-department");
        Department departmentA = ItFixtures.department(storage, institute, "A-department");
        Subdepartment b2 = ItFixtures.subdepartment(storage, departmentB, "2");
        Subdepartment b1 = ItFixtures.subdepartment(storage, departmentB, "1");
        Subdepartment a9 = ItFixtures.subdepartment(storage, departmentA, "9");
        Subdepartment removed = ItFixtures.subdepartment(storage, departmentA, "0-removed");
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "10", "10", "10");
        Finance other = ItFixtures.finance(storage, "Other", "2024-01-01", "2024-12-31", "10", "10", "10");
        ItFixtures.financeDepartment(storage, finance, b2, "1", "1", "1");
        ItFixtures.financeDepartment(storage, finance, b1, "1", "1", "1");
        ItFixtures.financeDepartment(storage, finance, a9, "1", "1", "1");
        storage.DEPARTMENT_FINANCES.delete(ItFixtures.financeDepartment(storage, finance, removed, "1", "1", "1"));
        ItFixtures.financeDepartment(storage, other, a9, "1", "1", "1");

        List<FinanceDepartment> result = storage.DEPARTMENT_FINANCES.get(new GetFinanceDepartmentsQuery(2024, finance));

        assertThat(subdepartmentNames(result)).containsExactly("9", "1", "2");
    }

    @Test
    void get_byFinance_yearOutsideTheFinance_returnsEmptyList() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Subdepartment subdepartment = ItFixtures.subdepartment(storage,
                ItFixtures.department(storage, institute, "Department"), "Subdepartment");
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "10", "10", "10");
        ItFixtures.financeDepartment(storage, finance, subdepartment, "1", "1", "1");

        assertThat(storage.DEPARTMENT_FINANCES.get(new GetFinanceDepartmentsQuery(2023, finance))).isEmpty();
        assertThat(storage.DEPARTMENT_FINANCES.get(new GetFinanceDepartmentsQuery(2025, finance))).isEmpty();
        assertThat(storage.DEPARTMENT_FINANCES.get(new GetFinanceDepartmentsQuery(2024, finance))).hasSize(1);
    }

    @Test
    void getResults_returnsActiveAssignmentsOrderedByFinanceDepartmentAndSubdepartment() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Department departmentB = ItFixtures.department(storage, institute, "B-department");
        Department departmentA = ItFixtures.department(storage, institute, "A-department");
        Subdepartment b = ItFixtures.subdepartment(storage, departmentB, "b");
        Subdepartment a2 = ItFixtures.subdepartment(storage, departmentA, "a2");
        Subdepartment a1 = ItFixtures.subdepartment(storage, departmentA, "a1");
        Finance first = ItFixtures.finance(storage, "1", "2024-01-01", "2024-12-31", "10", "10", "10");
        Finance second = ItFixtures.finance(storage, "2", "2024-01-01", "2024-12-31", "10", "10", "10");
        ItFixtures.financeDepartment(storage, second, a1, "1", "1", "1");
        ItFixtures.financeDepartment(storage, first, b, "1", "1", "1");
        ItFixtures.financeDepartment(storage, first, a2, "1", "1", "1");
        ItFixtures.financeDepartment(storage, first, a1, "1", "1", "1");
        storage.DEPARTMENT_FINANCES.delete(ItFixtures.financeDepartment(storage, second, b, "1", "1", "1"));

        List<FinanceDepartment> result = storage.DEPARTMENT_FINANCES.getResults();

        assertThat(financeNumbers(result)).containsExactly("1", "1", "1", "2");
        assertThat(subdepartmentNames(result)).containsExactly("a1", "a2", "b", "a1");
    }

    @Test
    void get_neitherSubdepartmentNorFinanceSelected_returnsEmptyList() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Subdepartment subdepartment = ItFixtures.subdepartment(storage,
                ItFixtures.department(storage, institute, "Department"), "Subdepartment");
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "10", "10", "10");
        ItFixtures.financeDepartment(storage, finance, subdepartment, "1", "1", "1");

        assertThat(storage.DEPARTMENT_FINANCES.get(new GetFinanceDepartmentsQuery(2024, new Subdepartment())))
                .isEmpty();
    }

    @ParameterizedTest
    @EnumSource(BidType.class)
    void retrieveSpentAmount_sumsActiveBidsOfThatTypeAndOfThatAssignmentOnly(BidType type) {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Department department = ItFixtures.department(storage, institute, "Department");
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "2000", "3000");
        FinanceDepartment mine = ItFixtures.financeDepartment(storage, finance,
                ItFixtures.subdepartment(storage, department, "Mine"), "500", "500", "500");
        FinanceDepartment neighbour = ItFixtures.financeDepartment(storage, finance,
                ItFixtures.subdepartment(storage, department, "Neighbour"), "500", "500", "500");
        for (BidType bidType : BidType.values()) {
            ItFixtures.bid(storage, mine, bidType, "10.50", 4);
            ItFixtures.bid(storage, mine, bidType, "1", 8);
            ItFixtures.bid(storage, neighbour, bidType, "1000", 1);
            storage.BIDS.delete(ItFixtures.bid(storage, mine, bidType, "5000", 1));
        }

        BigDecimal spent = storage.DEPARTMENT_FINANCES.retrieveSpentAmount(new GetFinanceDepartmentSpentAmountQuery(mine, type));

        assertThat(spent).isEqualByComparingTo("50.00");
    }

    @ParameterizedTest
    @EnumSource(BidType.class)
    void retrieveSpentAmount_noBids_returnsZeroInsteadOfNull(BidType type) {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "2000", "3000");
        FinanceDepartment financeDepartment = ItFixtures.financeDepartment(storage, finance,
                ItFixtures.subdepartment(storage, ItFixtures.department(storage, institute, "Department"), "S"),
                "500", "500", "500");

        assertThat(storage.DEPARTMENT_FINANCES.retrieveSpentAmount(
                new GetFinanceDepartmentSpentAmountQuery(financeDepartment, type))).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @ParameterizedTest
    @EnumSource(BidType.class)
    void retrieveLeftAmount_isTheAssignedAmountMinusWhatIsSpent(BidType type) {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "2000", "3000");
        FinanceDepartment financeDepartment = ItFixtures.financeDepartment(storage, finance,
                ItFixtures.subdepartment(storage, ItFixtures.department(storage, institute, "Department"), "S"),
                "500", "600", "700");
        ItFixtures.bid(storage, financeDepartment, type, "25.25", 4);

        BigDecimal left = storage.DEPARTMENT_FINANCES.retrieveLeftAmount(
                new GetFinanceDepartmentLeftAmountQuery(financeDepartment, type));

        assertThat(left).isEqualByComparingTo(type == BidType.MATERIALS ? "399.00" : type == BidType.EQUIPMENT ? "499.00" : "599.00");
    }

    @Test
    void create_financeDepartment_persistsForeignKeysAndAmounts() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Subdepartment subdepartment = ItFixtures.subdepartment(storage,
                ItFixtures.department(storage, institute, "Department"), "Subdepartment");
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "10", "10", "10");

        FinanceDepartment financeDepartment = ItFixtures.financeDepartment(storage, finance, subdepartment, "1.10", "2.20", "3.30");

        long id = financeDepartment.getModelId();
        assertThat(Sql.scalar("SELECT finance_id FROM promasy.finance_dep WHERE id = ?", id)).isEqualTo(finance.getModelId());
        assertThat(Sql.scalar("SELECT subdep_id FROM promasy.finance_dep WHERE id = ?", id)).isEqualTo(subdepartment.getModelId());
        assertThat((BigDecimal) Sql.scalar("SELECT total_materials FROM promasy.finance_dep WHERE id = ?", id))
                .isEqualByComparingTo("1.10");
        assertThat((BigDecimal) Sql.scalar("SELECT total_eqipment FROM promasy.finance_dep WHERE id = ?", id))
                .isEqualByComparingTo("2.20");
        assertThat((BigDecimal) Sql.scalar("SELECT total_services FROM promasy.finance_dep WHERE id = ?", id))
                .isEqualByComparingTo("3.30");
    }
}
