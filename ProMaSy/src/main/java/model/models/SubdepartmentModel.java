package model.models;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private List<EmployeeModel> employees;

    public SubdepartmentModel() {

    }

    public SubdepartmentModel(String subdepName, DepartmentModel department) {
        this.subdepName = subdepName;
        this.department = department;
    }

    public SubdepartmentModel(long subdepId, String subdepName, DepartmentModel department, EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active, List<EmployeeModel> employees) {
        super(subdepId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.subdepName = subdepName;
        this.department = department;
        this.employees = employees;
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

    public void setEmployees(List<EmployeeModel> employees) {
        this.employees = employees;
    }

    public String toString() {
        return subdepName;
    }

}
