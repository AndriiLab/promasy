package com.github.andriilab.promasy.it;

import com.github.andriilab.promasy.data.authorization.PasswordUtils;
import com.github.andriilab.promasy.data.storage.Storage;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.finance.enums.Fund;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.domain.organization.enums.Role;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Persists small entity graphs through the repositories, like the application does, and returns the managed entities.
 */
public final class ItFixtures {

    public static final String PASSWORD = "secret";

    private ItFixtures() {
    }

    public static Institute institute(Storage storage, String name) {
        Institute institute = new Institute(name, "", "", "", null, null);
        storage.INSTITUTES.create(institute);
        return institute;
    }

    public static Department department(Storage storage, Institute institute, String name) {
        Department department = new Department(name);
        institute.addDepartment(department);
        storage.DEPARTMENTS.create(department);
        return department;
    }

    public static Subdepartment subdepartment(Storage storage, Department department, String name) {
        Subdepartment subdepartment = new Subdepartment(name);
        department.addSubdepartment(subdepartment);
        storage.SUBDEPARTMENS.create(subdepartment);
        return subdepartment;
    }

    /** A finance valid from {@code startsOn} to {@code dueTo} ({@code yyyy-mm-dd}) with the given totals. */
    public static Finance finance(Storage storage, String number, String startsOn, String dueTo,
                                  String materials, String equipment, String services) {
        Finance finance = new Finance(number, "Finance " + number, new BigDecimal(materials), new BigDecimal(equipment),
                new BigDecimal(services), Date.valueOf(startsOn), Date.valueOf(dueTo), Fund.COMMON_FUND, 1);
        storage.FINANCES.create(finance);
        return finance;
    }

    /** The part of a finance assigned to a subdepartment. */
    public static FinanceDepartment financeDepartment(Storage storage, Finance finance, Subdepartment subdepartment,
                                                      String materials, String equipment, String services) {
        FinanceDepartment financeDepartment = new FinanceDepartment(finance, subdepartment, new BigDecimal(materials),
                new BigDecimal(equipment), new BigDecimal(services));
        finance.addFinanceDepartmentModel(financeDepartment);
        storage.DEPARTMENT_FINANCES.create(financeDepartment);
        return financeDepartment;
    }

    /** A bid without producer, supplier, CPV and statuses: enough for the amount and filter queries. */
    public static Bid bid(Storage storage, FinanceDepartment financeDepartment, BidType type, String onePrice, int amount) {
        return bid(storage, financeDepartment, type, onePrice, amount, "Bid", null);
    }

    /** A bid with a description and a CPV (which must already be managed, like every row of the {@code cpv} table). */
    public static Bid bid(Storage storage, FinanceDepartment financeDepartment, BidType type, String onePrice, int amount,
                          String description, Cpv cpv) {
        Bid bid = new Bid(null, null, description, cpv, new BigDecimal(onePrice), amount, null, financeDepartment, null,
                new ArrayList<>(), null, type, null);
        financeDepartment.addBid(bid);
        storage.BIDS.create(bid);
        return bid;
    }

    /** An employee who can log in with {@link #PASSWORD}, hashed with the given salt like the registration dialog does. */
    public static Employee employee(Storage storage, String login, Role role, Subdepartment subdepartment, long salt) {
        Employee employee = new Employee("First", "Middle", "Last-" + login, login + "@example.com", "", "",
                subdepartment, role, login, PasswordUtils.makePass(PASSWORD.toCharArray(), salt), salt);
        if (subdepartment != null) {
            subdepartment.addEmployee(employee);
        }
        storage.EMPLOYEES.create(employee);
        return employee;
    }
}
