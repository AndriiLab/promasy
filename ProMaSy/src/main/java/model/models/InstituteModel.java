package model.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "institutes")
public class InstituteModel extends AbstractModel {

    @Column(name = "inst_name")
    private String instName;

    @OneToMany(mappedBy = "institute", cascade = CascadeType.PERSIST)
    private List<DepartmentModel> departments = new ArrayList<>();

    public InstituteModel() {
        this.instName = EmptyModel.STRING;
    }

    public InstituteModel(String instName) {
        this.instName = instName;
    }

    public InstituteModel(long instId, String instName, EmployeeModel createdBy,
                          Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate,
                          boolean active, List<DepartmentModel> departments) {
        super(instId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.instName = instName;
        this.departments = departments;
    }

    @Override
    public void setDescription(String instName) {
        this.instName = instName;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String toString(){
        return instName;
    }

    public List<DepartmentModel> getDepartments() {
        departments.sort(Comparator.comparing(DepartmentModel::getDepName));
        return departments;
    }

    public void addDepartment(DepartmentModel model) {
        model.setInstitute(this);
        int indexOfModel = departments.indexOf(model);
        // if model does exist, replace it with modified model (this is possible with overridden equals() and hashcode() in model)
        if (indexOfModel != -1) {
            departments.set(indexOfModel, model);
        } else {
            departments.add(model);
        }
    }

    @Override
    public void setDeleted() {
        for (DepartmentModel model : departments) {
            model.setDeleted();
        }
        super.setDeleted();
    }
}
