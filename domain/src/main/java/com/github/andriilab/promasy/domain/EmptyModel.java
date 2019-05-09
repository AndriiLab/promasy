package com.github.andriilab.promasy.domain;

import com.github.andriilab.promasy.commons.persistence.IEntity;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.BidStatus;
import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.domain.item.entities.Supplier;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.domain.versioning.entities.Version;

/**
 * Instances of empty models
 */
@SuppressWarnings("unchecked")
public class EmptyModel {
    public static final AmountUnit AMOUNT_UNITS = new AmountUnit();
    public static final Bid BID = new Bid();
    public static final BidStatus BID_STATUS = new BidStatus();
    public static final Cpv CPV = new Cpv();
    public static final Department DEPARTMENT = new Department();
    public static final Employee EMPLOYEE = new Employee();
    public static final FinanceDepartment FINANCE_DEPARTMENT = new FinanceDepartment();
    public static final Finance FINANCE = new Finance();
    public static final Institute INSTITUTE = new Institute();
    public static final Producer PRODUCER = new Producer();
    public static final ReasonForSupplierChoice REASON_FOR_SUPPLIER_CHOICE = new ReasonForSupplierChoice();
    public static final Subdepartment SUBDEPARTMENT = new Subdepartment();
    public static final Supplier SUPPLIER = new Supplier();
    public static final Version VERSION = new Version();
    public static final Object OBJECT = new Object();

    private static IEntity[] entities = new IEntity[]{
            AMOUNT_UNITS, BID, BID_STATUS,
            DEPARTMENT, EMPLOYEE, FINANCE_DEPARTMENT, FINANCE,
            INSTITUTE, PRODUCER, REASON_FOR_SUPPLIER_CHOICE,
            SUBDEPARTMENT, SUPPLIER};

    public static <E extends IEntity> E getByClass(Class<E> entityClass) {
        for (IEntity entity : entities) {
            if (entity.getClass().equals(entityClass)) {
                return (E) entity;
            }
        }

        throw new IllegalArgumentException("No entity class found for name " + entityClass.getName());
    }
}
