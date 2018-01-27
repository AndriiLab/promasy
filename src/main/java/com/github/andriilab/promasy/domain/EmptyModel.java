package com.github.andriilab.promasy.domain;

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

/**
 * Instances of empty models
 */
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

    public static final String STRING = "";
}
