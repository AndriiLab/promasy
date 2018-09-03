package com.github.andriilab.promasy.domain.organization.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "employees")
public class Employee extends AbstractEntity {

    @Column(name = "emp_fname")
    private @Getter
    @Setter
    String empFName;

    @Column(name = "emp_mname")
    private @Getter
    @Setter
    String empMName;

    @Column(name = "emp_lname")
    private @Getter
    @Setter
    String empLName;

    @Column(name = "email")
    private @Getter
    @Setter
    String email;

    @Column(name = "phone_main")
    private @Getter
    @Setter
    String phoneMain;

    @Column(name = "phone_reserve")
    private @Getter
    @Setter
    String phoneReserve;

    @Column(name = "login")
    private @Getter
    @Setter
    String login;

    @Column(name = "password")
    private @Getter
    @Setter
    String password;

    @Column(name = "salt")
    private @Getter
    @Setter
    long salt;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subdep_id")
    private @Getter
    @Setter
    Subdepartment subdepartment;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private @Getter
    @Setter
    Role role;

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
    public String getDescription() {
        return this.empLName;
    }

    @Override
    public String getMessage() {
        return "createOrUpdateUser";
    }

    public String getShortName() {
        if (empFName != null && empLName != null && empMName != null) {
            return empLName + " " + empFName.substring(0, 1) + "."
                    + empMName.substring(0, 1) + ".";
        }
        return "";
    }

    public String toString() {
        return getShortName();
    }
}
