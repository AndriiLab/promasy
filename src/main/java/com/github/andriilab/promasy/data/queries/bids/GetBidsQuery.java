package com.github.andriilab.promasy.data.queries.bids;

import com.github.andriilab.promasy.data.queries.IQuery;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;

public class GetBidsQuery implements IQuery {
    private BidType type;
    private int year;
    private long departmentId;
    private long subdepartmentId;
    private long financeDepartmentId;

    public GetBidsQuery(int year) {
        this.year = year;
    }

    public GetBidsQuery(BidType type, int year) {
        this.type = type;
        this.year = year;
    }

    public GetBidsQuery(BidType type, int year, Department department) {
        this.type = type;
        this.year = year;
        this.departmentId = department.getModelId();
    }

    public GetBidsQuery(BidType type, int year, Subdepartment subdepartment) {
        this.type = type;
        this.year = year;
        this.subdepartmentId = subdepartment.getModelId();
    }

    public GetBidsQuery(BidType type, int year, FinanceDepartment financeDepartment) {
        this.type = type;
        this.year = year;
        this.financeDepartmentId = financeDepartment.getModelId();
    }

    public BidType getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public long getSubdepartmentId() {
        return subdepartmentId;
    }

    public long getFinanceDepartmentId() {
        return financeDepartmentId;
    }
}
