package com.github.andriilab.promasy.model.models;

import com.github.andriilab.promasy.model.dao.Database;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "institutes")
public class InstituteModel extends AbstractModel {

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
    private List<DepartmentModel> departments = new ArrayList<>();

    public InstituteModel() {
        this.instName = EmptyModel.STRING;
    }

    public InstituteModel(String instName, String phoneNumber, String faxNumber, String eMail, Integer edrpou, Address address) {
        this.instName = instName;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.eMail = eMail;
        this.edrpou = edrpou;
        this.address = address;
    }

    public InstituteModel(long modelId, EmployeeModel createdEmployee, Timestamp createdDate, EmployeeModel modifiedEmployee, Timestamp modifiedDate, boolean active, String instName, String phoneNumber, String faxNumber, String eMail, Integer edrpou, Address address, List<DepartmentModel> departments) {
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
        // if com.github.andriilab.promasy.model does exist, replace it with modified com.github.andriilab.promasy.model (this is possible with overridden equals() and hashcode() in com.github.andriilab.promasy.model)
        if (indexOfModel != -1) {
            departments.set(indexOfModel, model);
        } else {
            departments.add(model);
        }
    }

    @Override
    public void setDeleted() {
        departments.forEach(Model::setDeleted);
        super.setDeleted();
    }

    @Override
    public void createOrUpdate() throws SQLException {
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