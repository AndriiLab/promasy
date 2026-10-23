package com.github.andriilab.promasy.domain;

import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractEntityTest {

    private static AmountUnit unit(long modelId) {
        AmountUnit unit = new AmountUnit();
        unit.setModelId(modelId);
        return unit;
    }

    // equals / hashCode

    @Test
    void equals_sameClassAndSameId_isTrue() {
        assertThat(unit(7)).isEqualTo(unit(7));
    }

    @Test
    void equals_sameClassAndDifferentId_isFalse() {
        assertThat(unit(7)).isNotEqualTo(unit(8));
    }

    @Test
    void equals_sameInstance_isTrue() {
        AmountUnit unit = unit(7);

        assertThat(unit.equals(unit)).isTrue();
    }

    @Test
    void equals_null_isFalse() {
        assertThat(unit(7).equals(null)).isFalse();
    }

    @Test
    void equals_objectThatIsNotAnEntity_isFalse() {
        assertThat(unit(7).equals("7")).isFalse();
    }

    @Test
    void equals_newEntitiesOfSameClassWithDefaultId_areEqual() {
        // combo-box placeholders and Validator rely on this
        assertThat(new AmountUnit()).isEqualTo(EmptyModel.AMOUNT_UNITS);
    }

    @Test
    void equals_differentClassesWithSameId_isFalse() {
        assertThat(EmptyModel.BID.equals(EmptyModel.EMPLOYEE)).isFalse();
        assertThat(unit(7).equals(TestEntities.bid(7, null, "1", 1))).isFalse();
    }

    @Test
    void equals_isSymmetricForDifferentClasses() {
        AmountUnit unit = unit(7);
        Bid bid = TestEntities.bid(7, null, "1", 1);

        assertThat(unit.equals(bid)).isEqualTo(bid.equals(unit));
    }

    @Test
    void equals_proxyOfSameEntityClass_isTrue() {
        AmountUnit proxy = mock(AmountUnit.class, withSettings().extraInterfaces(HibernateProxy.class));
        LazyInitializer initializer = mock(LazyInitializer.class);
        when(((HibernateProxy) proxy).getHibernateLazyInitializer()).thenReturn(initializer);
        when(initializer.getImplementation()).thenReturn(unit(7));
        when(proxy.getModelId()).thenReturn(7L);

        assertThat(unit(7).equals(proxy)).isTrue();
    }

    @Test
    void hashCode_sameId_isSame() {
        assertThat(unit(7).hashCode()).isEqualTo(unit(7).hashCode());
    }

    // getLastEditPersonName

    @Test
    void getLastEditPersonName_modifiedEmployeeSet_returnsModifiedEmployee() {
        AmountUnit unit = new AmountUnit();
        unit.setCreatedEmployee(TestEntities.employee("Smith", "John", "Michael"));
        unit.setModifiedEmployee(TestEntities.employee("Brown", "Anna", "Maria"));

        assertThat(unit.getLastEditPersonName()).isEqualTo("Brown A.M.");
    }

    @Test
    void getLastEditPersonName_onlyCreatedEmployeeSet_returnsCreatedEmployee() {
        AmountUnit unit = new AmountUnit();
        unit.setCreatedEmployee(TestEntities.employee("Smith", "John", "Michael"));

        assertThat(unit.getLastEditPersonName()).isEqualTo("Smith J.M.");
    }

    @Test
    void getLastEditPersonName_modifiedEmployeeHasNoShortName_fallsBackToCreatedEmployee() {
        AmountUnit unit = new AmountUnit();
        unit.setCreatedEmployee(TestEntities.employee("Smith", "John", "Michael"));
        unit.setModifiedEmployee(new Employee());

        assertThat(unit.getLastEditPersonName()).isEqualTo("Smith J.M.");
    }

    @Test
    void getLastEditPersonName_noEmployees_returnsEmptyString() {
        assertThat(new AmountUnit().getLastEditPersonName()).isEmpty();
    }

    // getLastEditDate

    @Test
    void getLastEditDate_modifiedDateSet_returnsModifiedDate() {
        AmountUnit unit = new AmountUnit();
        unit.setCreatedDate(TestEntities.timestamp(1));
        unit.setModifiedDate(TestEntities.timestamp(2));

        assertThat(unit.getLastEditDate()).isEqualTo(TestEntities.timestamp(2));
    }

    @Test
    void getLastEditDate_onlyCreatedDateSet_returnsCreatedDate() {
        AmountUnit unit = new AmountUnit();
        unit.setCreatedDate(TestEntities.timestamp(1));

        assertThat(unit.getLastEditDate()).isEqualTo(TestEntities.timestamp(1));
    }

    @Test
    void getLastEditDate_noDates_returnsCurrentTime() {
        long before = Instant.now().toEpochMilli();

        Timestamp result = new AmountUnit().getLastEditDate();

        // compare epoch millis: Date.after/before ignore the sub-second part of a Timestamp
        assertThat(result.getTime()).isBetween(before, Instant.now().toEpochMilli());
    }

    // active / audit

    @Test
    void new_entity_isActive() {
        assertThat(new AmountUnit().isActive()).isTrue();
    }

    @Test
    void setActive_false_storesFlagAndAuditFields() {
        AmountUnit unit = new AmountUnit();
        Employee employee = TestEntities.employee("Smith", "John", "Michael");

        unit.setActive(false, employee, TestEntities.timestamp(3));

        assertThat(unit.isActive()).isFalse();
        assertThat(unit.getModifiedEmployee()).isSameAs(employee);
        assertThat(unit.getModifiedDate()).isEqualTo(TestEntities.timestamp(3));
    }

    @Test
    void setActive_true_reactivatesEntity() {
        AmountUnit unit = new AmountUnit();
        unit.setActive(false, null, TestEntities.timestamp(3));

        unit.setActive(true, null, TestEntities.timestamp(4));

        assertThat(unit.isActive()).isTrue();
        assertThat(unit.getModifiedDate()).isEqualTo(TestEntities.timestamp(4));
    }
}
