package com.github.andriilab.promasy.model.models;

import com.github.andriilab.promasy.model.dao.Database;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "departments")
public class DepartmentModel extends AbstractModel {

    @Column(name = "dep_name")
    private String depName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "inst_id")
    private InstituteModel institute;

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    private List<SubdepartmentModel> subdepartments = new ArrayList<>();

    public DepartmentModel() {
        this.depName = EmptyModel.STRING;
    }

    public DepartmentModel(String depName) {
        this.depName = depName;
    }

    public DepartmentModel(long depId, String depName, InstituteModel institute, EmployeeModel createdBy, Timestamp createdDate,
                           EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active,
                           List<SubdepartmentModel> subdepartments) {
        super(depId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.depName = depName;
        this.institute = institute;
        this.subdepartments = subdepartments;
    }

    @Override
    public void setDescription(String description) {
        this.depName = description;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public InstituteModel getInstitute() {
        return institute;
    }

    public void setInstitute(InstituteModel institute) {
        this.institute = institute;
    }

    public List<SubdepartmentModel> getSubdepartments() {
        subdepartments.sort(Comparator.comparing(SubdepartmentModel::getSubdepName));
        return subdepartments;
    }

    public String toString() {
        return depName;
    }

    public void addSubdepartment(SubdepartmentModel model) {
        model.setDepartment(this);
        int indexOfModel = subdepartments.indexOf(model);
        // if com.github.andriilab.promasy.model does exist, replace it with modified com.github.andriilab.promasy.model (this is possible with overridden equals() and hashcode() in com.github.andriilab.promasy.model)
        if (indexOfModel != -1) {
            subdepartments.set(indexOfModel, model);
        } else {
            subdepartments.add(model);
        }
    }

    @Override
    public void setDeleted() {
        subdepartments.forEach(Model::setDeleted);
        super.setDeleted();
    }

    @Override
    public void createOrUpdate() throws SQLException {
        Database.DEPARTMENTS.createOrUpdate(this);
    }

    @Override
    public void refresh() {
        Database.DEPARTMENTS.refresh(this);
    }

    @Override
    public String getMessage() {
        return "addOrUpdateDepartment";
    }

}