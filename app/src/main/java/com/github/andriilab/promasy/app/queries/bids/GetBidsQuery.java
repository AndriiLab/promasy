package com.github.andriilab.promasy.app.queries.bids;

import com.github.andriilab.promasy.commons.cq.IQuery;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import lombok.Getter;

public class GetBidsQuery implements IQuery {
    @Getter private BidType type;
    @Getter private int year;
    @Getter private long departmentId;
    @Getter private long subdepartmentId;
    @Getter private long financeDepartmentId;

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
}
