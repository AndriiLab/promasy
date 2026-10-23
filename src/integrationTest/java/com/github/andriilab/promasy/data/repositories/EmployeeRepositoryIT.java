package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.authorization.PasswordUtils;
import com.github.andriilab.promasy.data.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.it.AbstractIntegrationTest;
import com.github.andriilab.promasy.it.ItFixtures;
import com.github.andriilab.promasy.it.PostgresContainerSupport;
import com.github.andriilab.promasy.it.Sql;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Login checks, salts and the filtered employee lists against real SQL, with real {@link PasswordUtils} hashes.
 */
class EmployeeRepositoryIT extends AbstractIntegrationTest {

    private static final long SALT = 42L;

    private static String hash(String password) {
        return PasswordUtils.makePass(password.toCharArray(), SALT);
    }

    private Employee employee(String login, Role role, Subdepartment subdepartment) {
        return ItFixtures.employee(storage, login, role, subdepartment, SALT);
    }

    private static List<String> logins(List<Employee> employees) {
        return employees.stream().map(Employee::getLogin).collect(Collectors.toList());
    }

    // checkLogin(username, password)

    @Test
    void checkLogin_correctCredentials_returnsTrue() {
        employee("anna", Role.USER, null);

        assertThat(storage.EMPLOYEES.checkLogin("anna", hash(ItFixtures.PASSWORD))).isTrue();
    }

    @Test
    void checkLogin_wrongUsername_returnsFalse() {
        employee("anna", Role.USER, null);

        assertThat(storage.EMPLOYEES.checkLogin("nobody", hash(ItFixtures.PASSWORD))).isFalse();
    }

    @Test
    void checkLogin_wrongPassword_returnsFalse() {
        employee("anna", Role.USER, null);

        assertThat(storage.EMPLOYEES.checkLogin("anna", hash("wrong"))).isFalse();
    }

    @Test
    void checkLogin_passwordOfAnotherEmployee_returnsFalse() {
        employee("anna", Role.USER, null);
        Employee boris = employee("boris", Role.USER, null);
        boris.setPassword(PasswordUtils.makePass("borisSecret".toCharArray(), SALT));
        storage.EMPLOYEES.update(boris);

        assertThat(storage.EMPLOYEES.checkLogin("boris", hash(ItFixtures.PASSWORD))).isFalse();
        assertThat(storage.EMPLOYEES.checkLogin("boris", hash("borisSecret"))).isTrue();
    }

    @Test
    void checkLogin_inactiveEmployee_returnsFalse() {
        Employee anna = employee("anna", Role.USER, null);
        storage.EMPLOYEES.delete(anna);

        assertThat(storage.EMPLOYEES.checkLogin("anna", hash(ItFixtures.PASSWORD))).isFalse();
    }

    // checkLogin(username): "is this username free?"

    @Test
    void checkLogin_unknownUsername_returnsTrueMeaningFree() {
        assertThat(storage.EMPLOYEES.checkLogin("nobody")).isTrue();
    }

    @Test
    void checkLogin_existingUsername_returnsFalse() {
        employee("anna", Role.USER, null);

        assertThat(storage.EMPLOYEES.checkLogin("anna")).isFalse();
    }

    @Test
    void checkLogin_inactiveEmployeeUsername_isStillTaken() {
        Employee anna = employee("anna", Role.USER, null);
        storage.EMPLOYEES.delete(anna);

        assertThat(storage.EMPLOYEES.checkLogin("anna")).isFalse();
    }

    // getSalt

    @Test
    void getSalt_activeEmployee_returnsStoredSalt() {
        employee("anna", Role.USER, null);

        assertThat(storage.EMPLOYEES.getSalt("anna")).isEqualTo(SALT);
    }

    @Test
    void getSalt_inactiveEmployee_returnsZero() {
        Employee anna = employee("anna", Role.USER, null);
        storage.EMPLOYEES.delete(anna);

        assertThat(storage.EMPLOYEES.getSalt("anna")).isZero();
    }

    @Test
    void getSalt_unknownUsername_returnsZero() {
        assertThat(storage.EMPLOYEES.getSalt("nobody")).isZero();
    }

    @Test
    void getSalt_negativeSalt_roundTripsThroughDatabase() {
        ItFixtures.employee(storage, "anna", Role.USER, null, Long.MIN_VALUE);

        assertThat(storage.EMPLOYEES.getSalt("anna")).isEqualTo(Long.MIN_VALUE);
    }

    // isFirstRun / getUserWithId

    @Test
    void isFirstRun_onlyEmployeeWithoutLogin_returnsTrue() {
        Sql.execute("UPDATE promasy.employees SET login = NULL WHERE id = ?", PostgresContainerSupport.ADMIN_ID);
        try {
            assertThat(storage.EMPLOYEES.isFirstRun()).isTrue();
        } finally {
            Sql.execute("UPDATE promasy.employees SET login = 'it-admin' WHERE id = ?",
                    PostgresContainerSupport.ADMIN_ID);
        }
    }

    @Test
    void isFirstRun_someEmployeeHasLogin_returnsFalse() {
        assertThat(storage.EMPLOYEES.isFirstRun()).isFalse();
    }

    @Test
    void getUserWithId_existingAndUnknownId_returnsEmployeeOrNull() {
        Employee anna = employee("anna", Role.USER, null);

        assertThat(storage.EMPLOYEES.getUserWithId(anna.getModelId())).isEqualTo(anna);
        assertThat(storage.EMPLOYEES.getUserWithId(Long.MAX_VALUE)).isNull();
    }

    // retrieve(GetEmployeesQuery)

    private Subdepartment subdepartmentOf(Department department, String name) {
        return ItFixtures.subdepartment(storage, department, name);
    }

    @Test
    void retrieve_roleAndDepartmentFilters_returnOnlyMatchingActiveEmployeesOrderedByName() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Department cardiology = ItFixtures.department(storage, institute, "Cardiology");
        Department surgery = ItFixtures.department(storage, institute, "Surgery");
        Subdepartment cardiologyA = subdepartmentOf(cardiology, "Cardiology A");
        Subdepartment cardiologyB = subdepartmentOf(cardiology, "Cardiology B");
        Subdepartment surgeryA = subdepartmentOf(surgery, "Surgery A");
        employee("c-user-b", Role.USER, cardiologyB);
        employee("c-user-a", Role.USER, cardiologyA);
        employee("c-head", Role.HEAD_OF_DEPARTMENT, cardiologyA);
        employee("s-user", Role.USER, surgeryA);
        employee("s-head", Role.HEAD_OF_DEPARTMENT, surgeryA);
        storage.EMPLOYEES.delete(employee("c-gone", Role.USER, cardiologyA));
        employee("no-department", Role.USER, null);

        long cardiologyId = cardiology.getModelId();

        assertThat(logins(storage.EMPLOYEES.retrieve(new GetEmployeesQuery(Role.USER, cardiologyId))))
                .containsExactly("c-user-a", "c-user-b");
        assertThat(logins(storage.EMPLOYEES.retrieve(new GetEmployeesQuery(Role.HEAD_OF_DEPARTMENT, cardiologyId))))
                .containsExactly("c-head");
        assertThat(logins(storage.EMPLOYEES.retrieve(new GetEmployeesQuery(null, cardiologyId))))
                .containsExactly("c-head", "c-user-a", "c-user-b");
        assertThat(logins(storage.EMPLOYEES.retrieve(new GetEmployeesQuery(Role.HEAD_OF_DEPARTMENT))))
                .containsExactly("c-head", "s-head");
    }

    @Test
    void retrieve_noFilter_returnsAllEmployeesIncludingInactiveOnesOrderedByName() {
        employee("zed", Role.USER, null);
        Employee gone = employee("amy", Role.USER, null);
        storage.EMPLOYEES.delete(gone);

        List<String> all = logins(storage.EMPLOYEES.retrieve(new GetEmployeesQuery()));

        assertThat(all).contains("zed", "amy", "it-admin");
        assertThat(all.indexOf("amy")).isLessThan(all.indexOf("zed"));
    }

    @Test
    void retrieve_roleWithoutMatches_returnsEmptyList() {
        employee("anna", Role.USER, null);

        assertThat(storage.EMPLOYEES.retrieve(new GetEmployeesQuery(Role.DIRECTOR))).isEmpty();
    }
}
