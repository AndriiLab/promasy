package com.github.andriilab.promasy.app.queries.financepartment;

import com.github.andriilab.promasy.commons.cq.IQuery;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import lombok.Getter;

public class GetFinanceDepartmentsQuery implements IQuery {
    @Getter private int year;
    @Getter private Subdepartment subdepartment;
    @Getter private Finance finance;

    public GetFinanceDepartmentsQuery(int year, Subdepartment subdepartment) {
        this.year = year;
        this.subdepartment = subdepartment;
    }

    public GetFinanceDepartmentsQuery(int year, Finance finance) {
        this.year = year;
        this.finance = finance;
    }
}
