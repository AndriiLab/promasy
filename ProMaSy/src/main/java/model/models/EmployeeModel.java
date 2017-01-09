package model.models;

import model.enums.Role;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "employees")
public class EmployeeModel extends AbstractModel {

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
    private SubdepartmentModel subdepartment;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public EmployeeModel(long modelId, EmployeeModel createdBy, Timestamp createdDate, EmployeeModel modifiedBy, Timestamp modifiedDate, boolean active, String empFName, String empMName, String empLName, String email, String phoneMain, String phoneReserve, SubdepartmentModel subdepartment, Role role, String login, String password, long salt) {
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

    public EmployeeModel() {
    }

    public EmployeeModel(Role role) {
        this.role = role;
    }

    public EmployeeModel(long modelId, Role role) {
        super.setModelId(modelId);
        this.role = role;
    }

    public EmployeeModel(String empFName, String empMName, String email, String phoneMain, String phoneReserve,
                         String empLName, SubdepartmentModel subdepartment, Role role, String login, String password, long salt) {
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

    public SubdepartmentModel getSubdepartment() {
        return subdepartment;
    }

    public void setSubdepartment(SubdepartmentModel subdepartment) {
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
        return "";
    }

    public String toString() {
        return getShortName();
    }
}
