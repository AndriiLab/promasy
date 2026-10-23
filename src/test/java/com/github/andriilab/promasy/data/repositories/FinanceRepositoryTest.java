package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.finance.GetFinanceAssignedAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceSpentAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinancesQuery;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinanceRepositoryTest {

    private static final int YEAR = 2020;

    @Mock
    private EntityManager entityManager;
    @Mock(answer = Answers.RETURNS_SELF)
    private TypedQuery<Finance> typedQuery;
    @Mock(answer = Answers.RETURNS_SELF)
    private Query scalarQuery;

    private FinanceRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FinanceRepository(entityManager);
    }

    // ---- get(GetFinancesQuery)

    @Test
    void get_noDepartment_filtersByYearOnly() {
        List<Finance> rows = Arrays.asList(TestEntities.finance("1", "a", "1", "1", "1"));
        when(entityManager.createQuery(anyString(), eq(Finance.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(rows);

        List<Finance> result = repository.get(new GetFinancesQuery(YEAR));

        assertThat(result).containsExactlyElementsOf(rows);
        verify(typedQuery).setParameter("year", YEAR);
        verify(typedQuery, never()).setParameter(eq("departmentId"), any());
        assertThat(capturedJpql()).doesNotContain(":departmentId");
    }

    @Test
    void get_withDepartment_filtersByDepartmentAndYear() {
        Department department = new Department("Physics");
        department.setModelId(7);
        when(entityManager.createQuery(anyString(), eq(Finance.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.<Finance>asList());

        repository.get(new GetFinancesQuery(YEAR, department));

        verify(typedQuery).setParameter("departmentId", 7L);
        verify(typedQuery).setParameter("year", YEAR);
        assertThat(capturedJpql()).contains(":departmentId");
    }

    @ParameterizedTest(name = "department id {0}")
    @CsvSource({"0", "7"})
    void get_anyDepartment_yearMustFallInsideFinancePeriod(long departmentId) {
        when(entityManager.createQuery(anyString(), eq(Finance.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.<Finance>asList());
        Department department = new Department("Physics");
        department.setModelId(departmentId);

        repository.get(new GetFinancesQuery(YEAR, department));

        assertThat(capturedJpql())
                .contains("EXTRACT(YEAR from fm.startDate) <= :year")
                .contains("EXTRACT(YEAR from fm.endDate) >= :year");
    }

    @Test
    void get_queryFails_rethrowsAndTheNextCallStillWorks() {
        when(entityManager.createQuery(anyString(), eq(Finance.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList())
                .thenThrow(new PersistenceException("boom"))
                .thenReturn(Arrays.<Finance>asList());

        assertThatThrownBy(() -> repository.get(new GetFinancesQuery(YEAR))).isInstanceOf(PersistenceException.class);
        List<Finance> second = repository.get(new GetFinancesQuery(YEAR));

        assertThat(second).isEmpty();
        verify(entityManager, never()).getTransaction();
    }

    // ---- assigned / unassigned amount

    @ParameterizedTest(name = "{0}")
    @CsvSource({"MATERIALS,totalMaterialsAmount", "EQUIPMENT,totalEquipmentAmount", "SERVICES,totalServicesAmount"})
    void retrieveAssignedAmount_bidType_sumsMatchingDepartmentColumn(BidType type, String column) {
        Finance finance = TestEntities.finance("1", "a", "1", "1", "1");
        when(entityManager.createQuery(anyString())).thenReturn(scalarQuery);
        when(scalarQuery.getSingleResult()).thenReturn(new BigDecimal("125.50"));

        BigDecimal result = repository.retrieveAssignedAmount(new GetFinanceAssignedAmountQuery(finance, type));

        assertThat(result).isEqualByComparingTo("125.50");
        ArgumentCaptor<String> jpql = ArgumentCaptor.forClass(String.class);
        verify(entityManager).createQuery(jpql.capture());
        assertThat(jpql.getValue()).contains("sum(dfm." + column + ")").contains("dfm.active = true");
        verify(scalarQuery).setParameter("finances", finance);
        verify(entityManager, never()).getTransaction();
    }

    @ParameterizedTest
    @EnumSource(BidType.class)
    void retrieveAssignedAmount_nothingAssigned_returnsZero(BidType type) {
        when(entityManager.createQuery(anyString())).thenReturn(scalarQuery);
        when(scalarQuery.getSingleResult()).thenReturn(null);

        BigDecimal result = repository.retrieveAssignedAmount(
                new GetFinanceAssignedAmountQuery(TestEntities.finance("1", "a", "1", "1", "1"), type));

        assertThat(result).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void retrieveAssignedAmount_queryFails_rethrowsWithoutTransaction() {
        when(entityManager.createQuery(anyString())).thenReturn(scalarQuery);
        when(scalarQuery.getSingleResult()).thenThrow(new PersistenceException("boom"));

        assertThatThrownBy(() -> repository.retrieveAssignedAmount(
                new GetFinanceAssignedAmountQuery(TestEntities.finance("1", "a", "1", "1", "1"), BidType.MATERIALS)))
                .isInstanceOf(PersistenceException.class);

        verify(entityManager, never()).getTransaction();
    }

    @ParameterizedTest(name = "{0}: total 1000, assigned {1} -> {2}")
    @CsvSource({"MATERIALS,300.25,699.75", "EQUIPMENT,1000,0", "SERVICES,1200,-200"})
    void retrieveUnassignedAmount_assigned_isTotalMinusAssigned(BidType type, String assigned, String expected) {
        Finance finance = TestEntities.finance("1", "a", "1000", "1000", "1000");
        when(entityManager.createQuery(anyString())).thenReturn(scalarQuery);
        when(scalarQuery.getSingleResult()).thenReturn(new BigDecimal(assigned));

        BigDecimal result = repository.retrieveUnassignedAmount(new GetFinanceUnassignedAmountQuery(finance, type));

        assertThat(result).isEqualByComparingTo(expected);
    }

    @Test
    void retrieveUnassignedAmount_nothingAssigned_isWholeTotal() {
        Finance finance = TestEntities.finance("1", "a", "1000", "2000", "3000");
        when(entityManager.createQuery(anyString())).thenReturn(scalarQuery);
        when(scalarQuery.getSingleResult()).thenReturn(null);

        BigDecimal result = repository.retrieveUnassignedAmount(
                new GetFinanceUnassignedAmountQuery(finance, BidType.EQUIPMENT));

        assertThat(result).isEqualByComparingTo("2000");
    }

    // ---- spent / left amount

    @Test
    void retrieveSpentAmount_hasBids_returnsSumAndBindsFinanceAndType() {
        Finance finance = TestEntities.finance("1", "a", "1", "1", "1");
        when(entityManager.createQuery(anyString())).thenReturn(scalarQuery);
        when(scalarQuery.getSingleResult()).thenReturn(new BigDecimal("42.10"));

        BigDecimal result = repository.retrieveSpentAmount(new GetFinanceSpentAmountQuery(finance, BidType.SERVICES));

        assertThat(result).isEqualByComparingTo("42.10");
        verify(scalarQuery).setParameter("finance", finance);
        verify(scalarQuery).setParameter("bidType", BidType.SERVICES);
        verify(entityManager, never()).getTransaction();
    }

    @Test
    void retrieveSpentAmount_noBids_returnsZero() {
        when(entityManager.createQuery(anyString())).thenReturn(scalarQuery);
        when(scalarQuery.getSingleResult()).thenReturn(null);

        BigDecimal result = repository.retrieveSpentAmount(
                new GetFinanceSpentAmountQuery(TestEntities.finance("1", "a", "1", "1", "1"), BidType.MATERIALS));

        assertThat(result).isEqualTo(BigDecimal.ZERO);
    }

    @ParameterizedTest(name = "{0}: total 500, spent {1} -> {2}")
    @CsvSource({"MATERIALS,120.40,379.60", "EQUIPMENT,500,0", "SERVICES,600,-100"})
    void retrieveLeftAmount_spent_isTotalMinusSpent(BidType type, String spent, String expected) {
        Finance finance = TestEntities.finance("1", "a", "500", "500", "500");
        when(entityManager.createQuery(anyString())).thenReturn(scalarQuery);
        when(scalarQuery.getSingleResult()).thenReturn(new BigDecimal(spent));

        BigDecimal result = repository.retrieveLeftAmount(new GetFinanceLeftAmountQuery(finance, type));

        assertThat(result).isEqualByComparingTo(expected);
    }

    private String capturedJpql() {
        ArgumentCaptor<String> jpql = ArgumentCaptor.forClass(String.class);
        verify(entityManager).createQuery(jpql.capture(), eq(Finance.class));
        return jpql.getValue();
    }
}
