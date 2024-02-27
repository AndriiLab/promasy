package com.github.andriilab.promasy.data.queries.financepartment;

import com.github.andriilab.promasy.data.queries.IQuery;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import lombok.Getter;

@Getter
public class GetFinanceDepartmentsQuery implements IQuery {
    private final int year;
    private Subdepartment subdepartment;
    private Finance finance;

    public GetFinanceDepartmentsQuery(int year, Subdepartment subdepartment) {
        this.year = year;
        this.subdepartment = subdepartment;
    }

    public GetFinanceDepartmentsQuery(int year, Finance finance) {
        this.year = year;
        this.finance = finance;
    }
}
