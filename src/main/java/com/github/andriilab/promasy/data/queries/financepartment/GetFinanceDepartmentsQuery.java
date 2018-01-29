package com.github.andriilab.promasy.data.queries.financepartment;

import com.github.andriilab.promasy.data.queries.IQuery;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;

public class GetFinanceDepartmentsQuery implements IQuery {
    private int year;
    private long departmentId;
    private long financeId;

    public GetFinanceDepartmentsQuery(int year, Subdepartment subdepartment) {
        this.year = year;
        this.departmentId = subdepartment.getDepartment().getModelId();
    }

//    public GetFinanceDepartmentsQuery(int year, long departmentId, long financeId) {
//        this.year = year;
//        this.departmentId = departmentId;
//        this.financeId = financeId;
//    }

    public int getYear() {
        return year;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public long getFinanceId() {
        return financeId;
    }
}
