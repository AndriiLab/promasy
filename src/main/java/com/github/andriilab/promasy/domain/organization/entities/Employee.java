package com.github.andriilab.promasy.domain.organization.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.organization.enums.Role;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "employees")
public class Employee extends AbstractEntity {

    @Column(name = "emp_fname")
    private String empFName;

    @Column(name = "emp_mname")
    private String empMName;

    @Column(name = "emp_lname")
    private String empLName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_main")
    private String phoneMain;

    @Column(name = "phone_reserve")
    private String phoneReserve;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private long salt;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subdep_id")
    private Subdepartment subdepartment;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public Employee(long modelId, Employee createdBy, Timestamp createdDate, Employee modifiedBy, Timestamp modifiedDate, boolean active, String empFName, String empMName, String empLName, String email, String phoneMain, String phoneReserve, Subdepartment subdepartment, Role role, String login, String password, long salt) {
        super(modelId, createdBy, createdDate, modifiedBy, modifiedDate, active);
        this.empFName = empFName;
        this.empMName = empMName;
        this.empLName = empLName;
        this.email = email;
        this.phoneMain = phoneMain;
        this.phoneReserve = phoneReserve;
        this.subdepartment = subdepartment;
        this.role = role;
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    public Employee() {
    }

    public Employee(Role role) {
        this.role = role;
    }

    public Employee(String empFName, String empMName, String empLName, String email, String phoneMain, String phoneReserve,
                    Subdepartment subdepartment, Role role, String login, String password, long salt) {
        this.empFName = empFName;
        this.empMName = empMName;
        this.empLName = empLName;
        this.email = email;
        this.phoneMain = phoneMain;
        this.phoneReserve = phoneReserve;
        this.subdepartment = subdepartment;
        this.role = role;
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    @Override
    public void setDescription(String lastName) {
        this.empLName = lastName;
    }

    @Override
    public String getMessage() {
        return "createOrUpdateUser";
    }

    public String getEmpFName() {
        return empFName;
    }

    public void setEmpFName(String empFName) {
        this.empFName = empFName;
    }

    public String getEmpMName() {
        return empMName;
    }

    public void setEmpMName(String empMName) {
        this.empMName = empMName;
    }

    public String getEmpLName() {
        return empLName;
    }

    public void setEmpLName(String empLName) {
        this.empLName = empLName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneMain() {
        return phoneMain;
    }

    public void setPhoneMain(String phoneMain) {
        this.phoneMain = phoneMain;
    }

    public String getPhoneReserve() {
        return phoneReserve;
    }

    public void setPhoneReserve(String phoneReserve) {
        this.phoneReserve = phoneReserve;
    }

    public Subdepartment getSubdepartment() {
        return subdepartment;
    }

    public void setSubdepartment(Subdepartment subdepartment) {
        this.subdepartment = subdepartment;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getSalt() {
        return salt;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }

    public String getShortName() {
        if (empFName != null && empLName != null && empMName != null) {
            return empLName + " " + empFName.substring(0, 1) + "."
                    + empMName.substring(0, 1) + ".";
        }
        return EmptyModel.STRING;
    }

    public String toString() {
        return getShortName();
    }
}
