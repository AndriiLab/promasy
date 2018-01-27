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

@Entity
@Table(name = "institutes")
public class Institute extends AbstractEntity {

    @Column(name = "inst_name")
    private String instName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "fax_number")
    private String faxNumber;

    @Column(name = "email")
    private String eMail;

    @Column(name = "edrpou")
    private Integer edrpou;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "institute", cascade = CascadeType.PERSIST)
    private List<Department> departments = new ArrayList<>();

    public Institute() {
        this.instName = EmptyModel.STRING;
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

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public Integer getEDRPOU() {
        return edrpou;
    }

    public void setEDRPOU(Integer edrpou) {
        this.edrpou = edrpou;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String toString() {
        return instName;
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
    public void createOrUpdate() throws JDBCException {
        Database.INSTITUTES.createOrUpdate(this);
    }

    @Override
    public void refresh() {
        Database.INSTITUTES.refresh(this);
    }

    @Override
    public String getMessage() {
        return "addOrUpdateInstitute";
    }
}
