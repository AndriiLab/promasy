package com.github.andriilab.promasy.testutil;

import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Small factory methods for building domain objects in tests. Only the fields a test cares about are set.
 */
public final class TestEntities {

    private TestEntities() {
    }

    public static Employee employee(String lastName, String firstName, String middleName) {
        Employee employee = new Employee();
        employee.setEmpLName(lastName);
        employee.setEmpFName(firstName);
        employee.setEmpMName(middleName);
        return employee;
    }

    public static Bid bid(long modelId, BidType type, String onePrice, int amount) {
        Bid bid = new Bid();
        bid.setModelId(modelId);
        bid.setType(type);
        bid.setOnePrice(new BigDecimal(onePrice));
        bid.setAmount(amount);
        return bid;
    }

    public static Subdepartment subdepartment(String departmentName, String subdepartmentName) {
        Department department = new Department(departmentName);
        Subdepartment subdepartment = new Subdepartment(subdepartmentName);
        department.addSubdepartment(subdepartment);
        return subdepartment;
    }

    public static FinanceDepartment financeDepartment(long modelId, Subdepartment subdepartment) {
        FinanceDepartment financeDepartment = new FinanceDepartment();
        financeDepartment.setModelId(modelId);
        financeDepartment.setSubdepartment(subdepartment);
        return financeDepartment;
    }

    public static Finance finance(String number, String name, String materials, String equipment, String services) {
        return new Finance(number, name, new BigDecimal(materials), new BigDecimal(equipment),
                new BigDecimal(services), null, null, null, null);
    }

    /**
     * A time-zone independent timestamp on the given day of January 2020.
     */
    public static Timestamp timestamp(int dayOfJanuary) {
        return Timestamp.valueOf(LocalDateTime.of(2020, 1, dayOfJanuary, 12, 0));
    }
}
