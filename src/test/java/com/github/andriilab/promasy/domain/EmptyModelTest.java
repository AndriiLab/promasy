package com.github.andriilab.promasy.domain;

import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.BidStatus;
import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.domain.item.entities.Supplier;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class EmptyModelTest {

    static Stream<Arguments> placeholders() {
        return Stream.of(
                arguments(AmountUnit.class, EmptyModel.AMOUNT_UNITS),
                arguments(Bid.class, EmptyModel.BID),
                arguments(BidStatus.class, EmptyModel.BID_STATUS),
                arguments(Department.class, EmptyModel.DEPARTMENT),
                arguments(Employee.class, EmptyModel.EMPLOYEE),
                arguments(FinanceDepartment.class, EmptyModel.FINANCE_DEPARTMENT),
                arguments(Finance.class, EmptyModel.FINANCE),
                arguments(Institute.class, EmptyModel.INSTITUTE),
                arguments(Producer.class, EmptyModel.PRODUCER),
                arguments(ReasonForSupplierChoice.class, EmptyModel.REASON_FOR_SUPPLIER_CHOICE),
                arguments(Subdepartment.class, EmptyModel.SUBDEPARTMENT),
                arguments(Supplier.class, EmptyModel.SUPPLIER)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("placeholders")
    void getByClass_registeredEntity_returnsItsPlaceholder(Class<IEntity> entityClass, IEntity placeholder) {
        assertThat(EmptyModel.getByClass(entityClass)).isSameAs(placeholder);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("placeholders")
    void placeholder_isEqualToNewInstanceOfSameClassButNotToPersistedOne(Class<?> entityClass, IEntity placeholder)
            throws Exception {
        IEntity fresh = (IEntity) entityClass.getDeclaredConstructor().newInstance();
        IEntity persisted = (IEntity) entityClass.getDeclaredConstructor().newInstance();
        persisted.setModelId(5);

        assertThat(placeholder).isEqualTo(fresh);
        assertThat(placeholder).isNotEqualTo(persisted);
    }

    @Test
    void getByClass_unregisteredClass_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> EmptyModel.getByClass(FakeEntity.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(FakeEntity.class.getName());
    }

    private abstract static class FakeEntity extends AbstractEntity {
    }
}
