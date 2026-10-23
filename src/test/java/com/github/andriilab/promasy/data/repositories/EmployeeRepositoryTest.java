package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.authorization.LoginData;
import com.github.andriilab.promasy.data.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The criteria API replaces the previous restriction on every {@code CriteriaQuery.where} call, so the conditions of
 * one query must be combined into a single {@code and(...)} predicate.
 */
@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryTest {

    @Mock
    private EntityManager entityManager;
    @Mock
    private EntityTransaction transaction;
    @Mock
    private CriteriaBuilder criteriaBuilder;
    @Mock
    private CriteriaQuery<Employee> criteriaQuery;
    @Mock
    private Root<Employee> root;
    @Mock
    private TypedQuery<Employee> typedQuery;

    private MockedStatic<LoginData> loginData;
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        loginData = mockStatic(LoginData.class);
        lenient().when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Employee.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Employee.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        repository = new EmployeeRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        loginData.close();
    }

    private Predicate equalTo(Object value) {
        Predicate predicate = mock(Predicate.class);
        when(criteriaBuilder.equal(any(), eq(value))).thenReturn(predicate);
        return predicate;
    }

    private void returning(Employee... employees) {
        List<Employee> rows = employees.length == 0 ? Collections.<Employee>emptyList() : Arrays.asList(employees);
        when(typedQuery.getResultList()).thenReturn(rows);
    }

    @Test
    void checkLogin_matchingActiveUser_requiresActiveLoginAndPasswordInOneRestrictionAndLogsIn() {
        Predicate active = equalTo(true);
        Predicate login = equalTo("jsmith");
        Predicate password = equalTo("HASH");
        Predicate combined = mock(Predicate.class);
        when(criteriaBuilder.and(active, login, password)).thenReturn(combined);
        Employee employee = TestEntities.employee("Smith", "John", "M");
        returning(employee);

        boolean result = repository.checkLogin("jsmith", "HASH");

        assertThat(result).isTrue();
        verify(criteriaQuery).where(combined);
        loginData.verify(() -> LoginData.getInstance(employee));
    }

    @Test
    void checkLogin_noMatchingEmployee_returnsFalseAndDoesNotLogIn() {
        Predicate combined = mock(Predicate.class);
        when(criteriaBuilder.and(equalTo(true), equalTo("jsmith"), equalTo("WRONG"))).thenReturn(combined);
        returning();

        boolean result = repository.checkLogin("jsmith", "WRONG");

        assertThat(result).isFalse();
        verify(criteriaQuery).where(combined);
        loginData.verifyNoInteractions();
    }

    @Test
    void getSalt_existingUser_restrictsToActiveEmployeeWithThatLogin() {
        Predicate combined = mock(Predicate.class);
        when(criteriaBuilder.and(equalTo(true), equalTo("jsmith"))).thenReturn(combined);
        Employee employee = TestEntities.employee("Smith", "John", "M");
        employee.setSalt(123456789L);
        returning(employee);

        long salt = repository.getSalt("jsmith");

        assertThat(salt).isEqualTo(123456789L);
        verify(criteriaQuery).where(combined);
    }

    @Test
    void getSalt_unknownUser_returnsZero() {
        when(criteriaBuilder.and(equalTo(true), equalTo("ghost"))).thenReturn(mock(Predicate.class));
        returning();

        assertThat(repository.getSalt("ghost")).isZero();
    }

    @Test
    void retrieve_roleOnly_restrictsToActiveEmployeesWithThatRole() {
        Predicate combined = mock(Predicate.class);
        when(criteriaBuilder.and(equalTo(true), equalTo(Role.ECONOMIST))).thenReturn(combined);
        Employee employee = TestEntities.employee("Smith", "John", "M");
        returning(employee);

        List<Employee> result = repository.retrieve(new GetEmployeesQuery(Role.ECONOMIST));

        assertThat(result).containsExactly(employee);
        verify(criteriaQuery).where(combined);
    }
}
