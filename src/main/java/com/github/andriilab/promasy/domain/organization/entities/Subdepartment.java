package com.github.andriilab.promasy.domain.organization.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "subdepartments")
public class Subdepartment extends AbstractEntity {

    @Column(name = "subdep_name")
    private String subdepName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dep_id")
    private Department department;

    @OneToMany(mappedBy = "subdepartment", cascade = CascadeType.PERSIST)
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "subdepartment", cascade = CascadeType.PERSIST)
    private List<FinanceDepartment> financeDepartments = new ArrayList<>();

    public Subdepartment() {
    }

    public Subdepartment(String subdepName) {
        this.subdepName = subdepName;
    }

    public Subdepartment(long modelId, Employee createdEmployee, Timestamp createdDate, Employee modifiedEmployee, Timestamp modifiedDate, boolean active, String subdepName, Department department, List<Employee> employees, List<FinanceDepartment> financeDepartments) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.subdepName = subdepName;
        this.department = department;
        this.employees = employees;
        this.financeDepartments = financeDepartments;
    }

    @Override
    public void setDescription(String subdepName) {
        this.subdepName = subdepName;
    }

    public String getSubdepName() {
        return subdepName;
    }

    public void setSubdepName(String subdepName) {
        this.subdepName = subdepName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<FinanceDepartment> getFinanceDepartments() {
        return financeDepartments;
    }

    public void addEmployee(Employee model) {
        model.setSubdepartment(this);
        int indexOfModel = employees.indexOf(model);
        // if com.github.andriilab.promasy.domain.model does exist, replace it with modified com.github.andriilab.promasy.domain.model (this is possible with overridden equals() and hashcode() in com.github.andriilab.promasy.domain.model)
        if (indexOfModel != -1) {
            employees.set(indexOfModel, model);
        } else {
            employees.add(model);
        }
    }

    public void addFinanceDepartmentModel(FinanceDepartment model) {
        model.setSubdepartment(this);
        int indexOfModel = financeDepartments.indexOf(model);
        // if com.github.andriilab.promasy.domain.model does exist, replace it with modified com.github.andriilab.promasy.domain.model (this is possible with overridden equals() and hashcode() in com.github.andriilab.promasy.domain.model)
        if (indexOfModel != -1) {
            financeDepartments.set(indexOfModel, model);
        } else {
            financeDepartments.add(model);
        }
    }

    @Override
    public String toString() {
        return subdepName;
    }

    @Override
    public void setDeleted() {
        employees.forEach(IEntity::setDeleted);
        financeDepartments.forEach(IEntity::setDeleted);
        super.setDeleted();
    }

    @Override
    public String getMessage() {
        return "addOrUpdateSubdepartment";
    }

}
