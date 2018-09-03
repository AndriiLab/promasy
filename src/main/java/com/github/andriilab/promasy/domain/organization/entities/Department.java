package com.github.andriilab.promasy.domain.organization.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.IEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@javax.persistence.Entity
@Table(name = "departments")
public class Department extends AbstractEntity {

    @Column(name = "dep_name")
    private @Getter
    @Setter
    String depName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "inst_id")
    private @Getter
    @Setter
    Institute institute;

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    private List<Subdepartment> subdepartments = new ArrayList<>();

    public Department() {
        this.depName = "";
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

    @Override
    public String getDescription() {
        return this.depName;
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
    public String getMessage() {
        return "addOrUpdateDepartment";
    }

}
