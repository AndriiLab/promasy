package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.authorization.LoginData;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.testutil.FakeTransaction;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepositoryTest {

    private static final Timestamp SERVER_TIME = TestEntities.timestamp(15);

    @Mock
    private EntityManager entityManager;
    @Mock
    private EntityTransaction mockTransaction;

    private final Employee currentUser = TestEntities.employee("Smith", "John", "Michael");

    private MockedStatic<ServerRepository> serverRepository;
    private MockedStatic<LoginData> loginData;
    private AmountUnitRepository repository;

    @BeforeEach
    void setUp() {
        serverRepository = mockStatic(ServerRepository.class);
        serverRepository.when(ServerRepository::getServerTimestamp).thenReturn(SERVER_TIME);
        loginData = mockStatic(LoginData.class);
        loginData.when(LoginData::getInstance).thenReturn(currentUser);

        lenient().when(entityManager.getTransaction()).thenReturn(mockTransaction);
        repository = new AmountUnitRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        loginData.close();
        serverRepository.close();
    }

    @Test
    void create_newEntity_fillsCreatedAuditFieldsAndPersistsInsideTransaction() {
        AmountUnit unit = new AmountUnit("pcs");

        repository.create(unit);

        assertThat(unit.getCreatedDate()).isEqualTo(SERVER_TIME);
        assertThat(unit.getCreatedEmployee()).isSameAs(currentUser);
        assertThat(unit.getModifiedDate()).isNull();
        assertThat(unit.getModifiedEmployee()).isNull();
        InOrder order = inOrder(mockTransaction, entityManager);
        order.verify(mockTransaction).begin();
        order.verify(entityManager).persist(unit);
        order.verify(mockTransaction).commit();
    }

    @Test
    void update_existingEntity_fillsModifiedAuditFieldsAndKeepsCreatedOnes() {
        AmountUnit unit = new AmountUnit("pcs");
        unit.setCreatedDate(TestEntities.timestamp(1));

        repository.update(unit);

        assertThat(unit.getModifiedDate()).isEqualTo(SERVER_TIME);
        assertThat(unit.getModifiedEmployee()).isSameAs(currentUser);
        assertThat(unit.getCreatedDate()).isEqualTo(TestEntities.timestamp(1));
        assertThat(unit.getCreatedEmployee()).isNull();
        InOrder order = inOrder(mockTransaction, entityManager);
        order.verify(mockTransaction).begin();
        order.verify(entityManager).persist(unit);
        order.verify(mockTransaction).commit();
    }

    @Test
    void delete_activeEntity_isSoftDeletedAndPersisted() {
        AmountUnit unit = new AmountUnit("pcs");

        repository.delete(unit);

        assertThat(unit.isActive()).isFalse();
        assertThat(unit.getModifiedDate()).isEqualTo(SERVER_TIME);
        assertThat(unit.getModifiedEmployee()).isSameAs(currentUser);
        verify(entityManager).persist(unit);
        verify(entityManager, never()).remove(any());
        verify(mockTransaction).commit();
    }

    @Test
    void refresh_entity_refreshesInsideTransaction() {
        AmountUnit unit = new AmountUnit("pcs");

        repository.refresh(unit);

        InOrder order = inOrder(mockTransaction, entityManager);
        order.verify(mockTransaction).begin();
        order.verify(entityManager).refresh(unit);
        order.verify(mockTransaction).commit();
    }

    @Test
    void create_persistFails_rollsBackActiveTransactionAndRethrows() {
        AmountUnit unit = new AmountUnit("pcs");
        PersistenceException failure = new PersistenceException("constraint violated");
        doThrow(failure).when(entityManager).persist(unit);
        when(mockTransaction.isActive()).thenReturn(true);

        assertThatThrownBy(() -> repository.create(unit)).isSameAs(failure);

        verify(mockTransaction).rollback();
        verify(mockTransaction, never()).commit();
    }

    @Test
    void create_persistFailsAndTransactionAlreadyInactive_doesNotRollBack() {
        AmountUnit unit = new AmountUnit("pcs");
        doThrow(new PersistenceException("boom")).when(entityManager).persist(unit);
        when(mockTransaction.isActive()).thenReturn(false);

        assertThatThrownBy(() -> repository.create(unit)).isInstanceOf(PersistenceException.class);

        verify(mockTransaction, never()).rollback();
    }

    @Test
    void create_rollbackAlsoFails_keepsOriginalExceptionWithRollbackFailureSuppressed() {
        AmountUnit unit = new AmountUnit("pcs");
        PersistenceException failure = new PersistenceException("constraint violated");
        IllegalStateException rollbackFailure = new IllegalStateException("connection lost");
        doThrow(failure).when(entityManager).persist(unit);
        when(mockTransaction.isActive()).thenReturn(true);
        doThrow(rollbackFailure).when(mockTransaction).rollback();

        assertThatThrownBy(() -> repository.create(unit))
                .isSameAs(failure)
                .hasSuppressedException(rollbackFailure);
    }

    @Test
    void create_afterFailedCreate_nextCallStillWorksOnSharedEntityManager() {
        FakeTransaction transaction = new FakeTransaction();
        when(entityManager.getTransaction()).thenReturn(transaction);
        AmountUnit broken = new AmountUnit("broken");
        AmountUnit fine = new AmountUnit("fine");
        // entities are equal by modelId, so Mockito would treat two id-0 units as the same argument
        broken.setModelId(1);
        fine.setModelId(2);
        doThrow(new PersistenceException("boom")).when(entityManager).persist(broken);

        assertThatThrownBy(() -> repository.create(broken)).isInstanceOf(PersistenceException.class);
        repository.create(fine);

        assertThat(transaction.isActive()).isFalse();
        assertThat(transaction.getRollbacks()).isEqualTo(1);
        assertThat(transaction.getCommits()).isEqualTo(1);
        verify(entityManager).persist(fine);
    }

    @Test
    void getResults_activeEntities_returnsUnmodifiableListAndCommits() {
        List<AmountUnit> rows = Arrays.asList(new AmountUnit("kg"), new AmountUnit("pcs"));
        stubCriteriaQuery(rows);

        List<AmountUnit> result = repository.getResults();

        assertThat(result).containsExactlyElementsOf(rows);
        assertThatThrownBy(() -> result.add(new AmountUnit("l"))).isInstanceOf(UnsupportedOperationException.class);
        InOrder order = inOrder(mockTransaction, entityManager);
        order.verify(mockTransaction).begin();
        order.verify(entityManager).createQuery(any(CriteriaQuery.class));
        order.verify(mockTransaction).commit();
    }

    @Test
    void getResults_calledTwice_doesNotEmptyTheFirstResult() {
        List<AmountUnit> firstRows = new ArrayList<>(Arrays.asList(new AmountUnit("kg"), new AmountUnit("pcs")));
        List<AmountUnit> secondRows = new ArrayList<>(Arrays.asList(new AmountUnit("l")));
        stubCriteriaQuery(firstRows, secondRows);

        List<AmountUnit> first = repository.getResults();
        List<AmountUnit> second = repository.getResults();

        assertThat(first).hasSize(2);
        assertThat(second).hasSize(1);
    }

    @Test
    void getResults_queryFails_rollsBackAndRethrows() {
        PersistenceException failure = new PersistenceException("query failed");
        stubCriteriaQueryFailing(failure);
        when(mockTransaction.isActive()).thenReturn(true);

        assertThatThrownBy(() -> repository.getResults()).isSameAs(failure);

        verify(mockTransaction).rollback();
        verify(mockTransaction, never()).commit();
    }

    @SafeVarargs
    private final void stubCriteriaQuery(List<AmountUnit>... results) {
        TypedQuery<AmountUnit> typedQuery = prepareCriteria();
        when(typedQuery.getResultList()).thenReturn(results[0], Arrays.copyOfRange(results, 1, results.length));
    }

    private void stubCriteriaQueryFailing(RuntimeException failure) {
        TypedQuery<AmountUnit> typedQuery = prepareCriteria();
        when(typedQuery.getResultList()).thenThrow(failure);
    }

    @SuppressWarnings("unchecked")
    private TypedQuery<AmountUnit> prepareCriteria() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<AmountUnit> criteriaQuery = mock(CriteriaQuery.class);
        Root<AmountUnit> root = mock(Root.class);
        TypedQuery<AmountUnit> typedQuery = mock(TypedQuery.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(AmountUnit.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(AmountUnit.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        return typedQuery;
    }
}
