package com.github.andriilab.promasy.app.queries.employees;

import com.github.andriilab.promasy.commons.cq.IQuery;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import lombok.Getter;

public class GetEmployeesQuery implements IQuery {
    @Getter private Role role;
    @Getter private long departmentId;

    public GetEmployeesQuery() {
    }

    public GetEmployeesQuery(Role role) {
        this.role = role;
    }

    public GetEmployeesQuery(Role role, long departmentId) {
        this.role = role;
        this.departmentId = departmentId;
    }
}
