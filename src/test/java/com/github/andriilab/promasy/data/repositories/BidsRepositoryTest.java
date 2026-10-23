package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.testutil.FakeTransaction;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BidsRepositoryTest {

    private static final int YEAR = 2021;

    @Mock
    private EntityManager entityManager;
    @Mock
    private EntityTransaction transaction;
    @Mock(answer = Answers.RETURNS_SELF)
    private TypedQuery<Bid> typedQuery;
    @Mock(answer = Answers.RETURNS_SELF)
    private Query scalarQuery;

    private BidsRepository repository;
    private final List<Bid> bids = Arrays.asList(
            TestEntities.bid(1, BidType.MATERIALS, "10", 1),
            TestEntities.bid(2, BidType.MATERIALS, "20", 2));

    @BeforeEach
    void setUp() {
        lenient().when(entityManager.getTransaction()).thenReturn(transaction);
        repository = new BidsRepository(entityManager);
    }

    private void stubBidQuery(List<Bid> rows) {
        when(entityManager.createQuery(anyString(), eq(Bid.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(rows);
    }

    private String capturedJpql() {
        ArgumentCaptor<String> jpql = ArgumentCaptor.forClass(String.class);
        verify(entityManager).createQuery(jpql.capture(), eq(Bid.class));
        return jpql.getValue();
    }

    @Test
    void retrieve_financeDepartment_filtersByFinanceDepartmentIdTypeAndYear() {
        FinanceDepartment financeDepartment = TestEntities.financeDepartment(31, TestEntities.subdepartment("D", "S"));
        stubBidQuery(bids);

        List<Bid> result = repository.retrieve(new GetBidsQuery(BidType.EQUIPMENT, YEAR, financeDepartment));

        assertThat(result).isSameAs(bids);
        assertThat(capturedJpql()).contains("bm.finances.id = :financeDepartmentId");
        verify(typedQuery).setParameter("financeDepartmentId", 31L);
        verify(typedQuery).setParameter("bidType", BidType.EQUIPMENT);
        verify(typedQuery).setParameter("requestedYear", YEAR);
    }

    @Test
    void retrieve_subdepartment_filtersBySubdepartmentIdTypeAndYear() {
        Subdepartment subdepartment = TestEntities.subdepartment("D", "S");
        subdepartment.setModelId(21);
        stubBidQuery(bids);

        repository.retrieve(new GetBidsQuery(BidType.SERVICES, YEAR, subdepartment));

        assertThat(capturedJpql()).contains("fd.subdepartment.id = :subdepartmentId")
                .doesNotContain("fd.subdepartment.department.id");
        verify(typedQuery).setParameter("subdepartmentId", 21L);
        verify(typedQuery).setParameter("bidType", BidType.SERVICES);
        verify(typedQuery).setParameter("requestedYear", YEAR);
    }

    @Test
    void retrieve_department_filtersByDepartmentIdTypeAndYear() {
        Department department = new Department("D");
        department.setModelId(11);
        stubBidQuery(bids);

        repository.retrieve(new GetBidsQuery(BidType.MATERIALS, YEAR, department));

        assertThat(capturedJpql()).contains("fd.subdepartment.department.id = :departmentId");
        verify(typedQuery).setParameter("departmentId", 11L);
        verify(typedQuery).setParameter("bidType", BidType.MATERIALS);
        verify(typedQuery).setParameter("requestedYear", YEAR);
    }

    @Test
    void retrieve_typeOnly_filtersByTypeAndYear() {
        stubBidQuery(bids);

        repository.retrieve(new GetBidsQuery(BidType.MATERIALS, YEAR));

        assertThat(capturedJpql()).contains(":bidType")
                .doesNotContain(":departmentId").doesNotContain(":subdepartmentId").doesNotContain(":financeDepartmentId");
        verify(typedQuery).setParameter("bidType", BidType.MATERIALS);
        verify(typedQuery).setParameter("requestedYear", YEAR);
    }

    @Test
    void retrieve_yearOnly_filtersByYearWithoutTypeCondition() {
        stubBidQuery(bids);

        repository.retrieve(new GetBidsQuery(YEAR));

        assertThat(capturedJpql()).doesNotContain(":bidType").contains("bm.active = true");
        verify(typedQuery).setParameter("requestedYear", YEAR);
        verify(typedQuery, never()).setParameter(eq("bidType"), any());
    }

    @Test
    void retrieve_anyRoute_yearMustFallInsideFinancePeriodAndOnlyActiveBidsAreRead() {
        stubBidQuery(bids);

        repository.retrieve(new GetBidsQuery(BidType.MATERIALS, YEAR));

        assertThat(capturedJpql())
                .contains("EXTRACT(YEAR from fm.startDate) <= :requestedYear")
                .contains("EXTRACT(YEAR from fm.endDate) >= :requestedYear")
                .contains("bm.active = true");
    }

    @Test
    void retrieve_bidsFound_refreshesEveryBidAfterCommit() {
        stubBidQuery(bids);

        repository.retrieve(new GetBidsQuery(BidType.MATERIALS, YEAR));

        InOrder order = inOrder(transaction, entityManager);
        order.verify(transaction).begin();
        order.verify(transaction).commit();
        order.verify(entityManager).refresh(bids.get(0));
        order.verify(entityManager).refresh(bids.get(1));
    }

    @Test
    void retrieve_queryFails_rollsBackSoTheNextCallCanBegin() {
        FakeTransaction fakeTransaction = new FakeTransaction();
        when(entityManager.getTransaction()).thenReturn(fakeTransaction);
        when(entityManager.createQuery(anyString(), eq(Bid.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenThrow(new PersistenceException("boom")).thenReturn(bids);

        assertThatThrownBy(() -> repository.retrieve(new GetBidsQuery(YEAR))).isInstanceOf(PersistenceException.class);
        List<Bid> second = repository.retrieve(new GetBidsQuery(YEAR));

        assertThat(second).isEqualTo(bids);
        assertThat(fakeTransaction.getRollbacks()).isEqualTo(1);
        assertThat(fakeTransaction.getCommits()).isEqualTo(1);
    }

    @Test
    void getTotalAmount_hasBids_returnsSumAndBindsTypeAndFinanceDepartment() {
        FinanceDepartment financeDepartment = TestEntities.financeDepartment(5, TestEntities.subdepartment("D", "S"));
        when(entityManager.createQuery(anyString())).thenReturn(scalarQuery);
        when(scalarQuery.getSingleResult()).thenReturn(new BigDecimal("99.99"));

        BigDecimal result = repository.getTotalAmount(financeDepartment, BidType.SERVICES);

        assertThat(result).isEqualByComparingTo("99.99");
        verify(scalarQuery).setParameter("bidType", BidType.SERVICES);
        verify(scalarQuery).setParameter("finances", financeDepartment);
        verify(transaction).commit();
    }

    @Test
    void getTotalAmount_noBids_returnsZero() {
        FinanceDepartment financeDepartment = TestEntities.financeDepartment(5, TestEntities.subdepartment("D", "S"));
        when(entityManager.createQuery(anyString())).thenReturn(scalarQuery);
        when(scalarQuery.getSingleResult()).thenReturn(null);

        BigDecimal result = repository.getTotalAmount(financeDepartment, BidType.MATERIALS);

        assertThat(result).isEqualTo(BigDecimal.ZERO);
    }
}
