package com.github.andriilab.promasy.data.queries.bids;

import com.github.andriilab.promasy.data.queries.IQuery;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import lombok.Getter;

public class GetBidsQuery implements IQuery {
    private @Getter
    BidType type;
    private @Getter
    int year;
    private @Getter
    long departmentId;
    private @Getter
    long subdepartmentId;
    private @Getter
    long financeDepartmentId;

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
