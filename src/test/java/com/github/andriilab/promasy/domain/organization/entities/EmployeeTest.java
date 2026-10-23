package com.github.andriilab.promasy.domain.organization.entities;

import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeTest {

    @Test
    void getShortName_allNamesPresent_isLastNameWithInitials() {
        Employee employee = TestEntities.employee("Smith", "John", "Michael");

        assertThat(employee.getShortName()).isEqualTo("Smith J.M.");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Smith,'',Michael,Smith M.",
            "Smith,John,'',Smith J.",
            "Smith,'','',Smith"
    })
    void getShortName_emptyFirstOrMiddleName_skipsMissingInitial(
            String last, String first, String middle, String expected) {
        Employee employee = TestEntities.employee(last, first, middle);

        assertThat(employee.getShortName()).isEqualTo(expected);
    }

    @Test
    void getShortName_anyNameIsNull_isEmpty() {
        assertThat(TestEntities.employee(null, "John", "Michael").getShortName()).isEmpty();
        assertThat(TestEntities.employee("Smith", null, "Michael").getShortName()).isEmpty();
        assertThat(TestEntities.employee("Smith", "John", null).getShortName()).isEmpty();
        assertThat(new Employee().getShortName()).isEmpty();
    }

    @Test
    void toString_isShortName() {
        assertThat(TestEntities.employee("Smith", "John", "Michael")).hasToString("Smith J.M.");
    }

    @Test
    void description_isLastName() {
        Employee employee = new Employee();

        employee.setDescription("Smith");

        assertThat(employee.getEmpLName()).isEqualTo("Smith");
        assertThat(employee.getDescription()).isEqualTo("Smith");
    }

    @Test
    void new_withRole_setsRole() {
        assertThat(new Employee(Role.ADMIN).getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    void new_withAllFields_setsThem() {
        Subdepartment subdepartment = TestEntities.subdepartment("D", "S");

        Employee employee = new Employee("John", "Michael", "Smith", "j@example.com", "111", "222",
                subdepartment, Role.USER, "jsmith", "hash", 42L);

        assertThat(employee.getEmpFName()).isEqualTo("John");
        assertThat(employee.getEmpMName()).isEqualTo("Michael");
        assertThat(employee.getEmpLName()).isEqualTo("Smith");
        assertThat(employee.getEmail()).isEqualTo("j@example.com");
        assertThat(employee.getPhoneMain()).isEqualTo("111");
        assertThat(employee.getPhoneReserve()).isEqualTo("222");
        assertThat(employee.getSubdepartment()).isSameAs(subdepartment);
        assertThat(employee.getRole()).isEqualTo(Role.USER);
        assertThat(employee.getLogin()).isEqualTo("jsmith");
        assertThat(employee.getPassword()).isEqualTo("hash");
        assertThat(employee.getSalt()).isEqualTo(42L);
    }
}
