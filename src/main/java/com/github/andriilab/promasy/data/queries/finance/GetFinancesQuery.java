package com.github.andriilab.promasy.data.queries.finance;

import com.github.andriilab.promasy.data.queries.IQuery;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import lombok.Getter;

public class GetFinancesQuery implements IQuery {
    @Getter private final int year;
    @Getter private long departmentId;

    public GetFinancesQuery(int year) {
        this.year = year;
    }

    public GetFinancesQuery(int year, Department department) {
        this.year = year;
        this.departmentId = department.getModelId();
    }
}
