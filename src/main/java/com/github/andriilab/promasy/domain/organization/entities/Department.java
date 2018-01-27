package com.github.andriilab.promasy.domain.organization.entities;

import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.IEntity;
import org.hibernate.JDBCException;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@javax.persistence.Entity
@Table(name = "departments")
public class Department extends AbstractEntity {

    @Column(name = "dep_name")
    private String depName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "inst_id")
    private Institute institute;

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    private List<Subdepartment> subdepartments = new ArrayList<>();

    public Department() {
        this.depName = EmptyModel.STRING;
    }

    public Department(String depName) {
        this.depName = depName;
    }

    public Department(long depId, String depName, Institute institute, Employee createdBy, Timestamp createdDate,
                      Employee modifiedBy, Timestamp modifiedDate, boolean active,
                      List<Subdepartment> subdepartments) {
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

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public List<Subdepartment> getSubdepartments() {
        subdepartments.sort(Comparator.comparing(Subdepartment::getSubdepName));
        return subdepartments;
    }

    public String toString() {
        return depName;
    }

    public void addSubdepartment(Subdepartment model) {
        model.setDepartment(this);
        int indexOfModel = subdepartments.indexOf(model);
        // if com.github.andriilab.promasy.domain.model does exist, replace it with modified com.github.andriilab.promasy.domain.model (this is possible with overridden equals() and hashcode() in com.github.andriilab.promasy.domain.model)
        if (indexOfModel != -1) {
            subdepartments.set(indexOfModel, model);
        } else {
            subdepartments.add(model);
        }
    }

    @Override
    public void setDeleted() {
        subdepartments.forEach(IEntity::setDeleted);
        super.setDeleted();
    }

    @Override
    public void createOrUpdate() throws JDBCException {
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
