package model.models;

import model.dao.Database;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subdepartments")
public class SubdepartmentModel extends AbstractModel {

    @Column(name = "subdep_name")
    private String subdepName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dep_id")
    private DepartmentModel department;

    @OneToMany(mappedBy = "subdepartment", cascade = CascadeType.PERSIST)
    private List<EmployeeModel> employees = new ArrayList<>();

    @OneToMany(mappedBy = "subdepartment", cascade = CascadeType.PERSIST)
    private List<FinanceDepartmentModel> financeDepartments = new ArrayList<>();

    public SubdepartmentModel() {
    }

    public SubdepartmentModel(String subdepName) {
        this.subdepName = subdepName;
    }

    public SubdepartmentModel(long modelId, EmployeeModel createdEmployee, Timestamp createdDate, EmployeeModel modifiedEmployee, Timestamp modifiedDate, boolean active, String subdepName, DepartmentModel department, List<EmployeeModel> employees, List<FinanceDepartmentModel> financeDepartments) {
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

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }

    public List<EmployeeModel> getEmployees() {
        return employees;
    }

    public List<FinanceDepartmentModel> getFinanceDepartments() {
        return financeDepartments;
    }

    public void addEmployee(EmployeeModel model) {
        model.setSubdepartment(this);
        int indexOfModel = employees.indexOf(model);
        // if model does exist, replace it with modified model (this is possible with overridden equals() and hashcode() in model)
        if (indexOfModel != -1) {
            employees.set(indexOfModel, model);
        } else {
            employees.add(model);
        }
    }

    public void addFinanceDepartmentModel(FinanceDepartmentModel model) {
        model.setSubdepartment(this);
        int indexOfModel = financeDepartments.indexOf(model);
        // if model does exist, replace it with modified model (this is possible with overridden equals() and hashcode() in model)
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
        employees.forEach(Model::setDeleted);
        financeDepartments.forEach(Model::setDeleted);
        super.setDeleted();
    }

    @Override
    public void createOrUpdate() throws SQLException {
        Database.SUBDEPARTMENS.createOrUpdate(this);
    }

    @Override
    public void refresh() {
        Database.SUBDEPARTMENS.refresh(this);
    }

    @Override
    public String getMessage() {
        return "addOrUpdateSubdepartment";
    }

}
