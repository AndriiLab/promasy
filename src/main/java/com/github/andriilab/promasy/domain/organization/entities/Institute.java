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

@Entity
@Table(name = "institutes")
public class Institute extends AbstractEntity {

    @Column(name = "inst_name")
    private @Getter
    @Setter
    String instName;

    @Column(name = "phone_number")
    private @Getter
    @Setter
    String phoneNumber;

    @Column(name = "fax_number")
    private @Getter
    @Setter
    String faxNumber;

    @Column(name = "email")
    private @Getter
    @Setter
    String eMail;

    @Column(name = "edrpou")
    private @Getter
    @Setter
    Integer edrpou;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private @Getter
    @Setter
    Address address;

    @OneToMany(mappedBy = "institute", cascade = CascadeType.PERSIST)
    private List<Department> departments = new ArrayList<>();

    public Institute() {
        this.instName = "";
    }

    public Institute(String instName, String phoneNumber, String faxNumber, String eMail, Integer edrpou, Address address) {
        this.instName = instName;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.eMail = eMail;
        this.edrpou = edrpou;
        this.address = address;
    }

    public Institute(long modelId, Employee createdEmployee, Timestamp createdDate, Employee modifiedEmployee, Timestamp modifiedDate, boolean active, String instName, String phoneNumber, String faxNumber, String eMail, Integer edrpou, Address address, List<Department> departments) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.instName = instName;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.eMail = eMail;
        this.edrpou = edrpou;
        this.address = address;
        this.departments = departments;
    }

    @Override
    public void setDescription(String instName) {
        this.instName = instName;
    }

    @Override
    public String getDescription() {
        return this.instName;
    }

    public List<Department> getDepartments() {
        departments.sort(Comparator.comparing(Department::getDepName));
        return departments;
    }

    public void addDepartment(Department model) {
        model.setInstitute(this);
        int indexOfModel = departments.indexOf(model);
        // if com.github.andriilab.promasy.domain.model does exist, replace it with modified com.github.andriilab.promasy.domain.model (this is possible with overridden equals() and hashcode() in com.github.andriilab.promasy.domain.model)
        if (indexOfModel != -1) {
            departments.set(indexOfModel, model);
        } else {
            departments.add(model);
        }
    }

    @Override
    public void setDeleted() {
        departments.forEach(IEntity::setDeleted);
        super.setDeleted();
    }

    @Override
    public String getMessage() {
        return "addOrUpdateInstitute";
    }
}
