package com.github.andriilab.promasy.domain.finance.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.enums.Fund;
import com.github.andriilab.promasy.domain.organization.entities.Employee;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * IEntity for financial data
 */

@javax.persistence.Entity
@Table(name = "finances")
public class Finance extends AbstractEntity {

    @Column(name = "number")
    private String financeNumber;

    @Column(name = "name")
    private String financeName;

    @Column(name = "total_materials")
    private BigDecimal totalMaterials;

    @Column(name = "total_equpment")
    private BigDecimal totalEquipment;

    @Column(name = "total_services")
    private BigDecimal totalServices;

    @Column(name = "starts_on")
    private Date startDate;

    @Column(name = "due_to")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private Fund fundType;

    @Column(name = "kpkvk")
    private Integer kpkvk;

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private List<FinanceDepartment> financeDepartmentModels = new ArrayList<>();

    @Transient
    private Map<BidType, BigDecimal> leftAmount;

    public Finance(long modelId, Employee createdEmployee, Timestamp createdDate, Employee modifiedEmployee, Timestamp modifiedDate, boolean active, String financeNumber, String financeName, BigDecimal totalMaterials, BigDecimal totalEquipment, BigDecimal totalServices, Date startDate, Date endDate, List<FinanceDepartment> financeDepartmentModels, Fund fundType, Integer kpkvk) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.financeNumber = financeNumber;
        this.financeName = financeName;
        this.totalMaterials = totalMaterials;
        this.totalEquipment = totalEquipment;
        this.totalServices = totalServices;
        this.startDate = startDate;
        this.endDate = endDate;
        this.financeDepartmentModels = financeDepartmentModels;
        this.fundType = fundType;
        this.kpkvk = kpkvk;
    }

    public Finance(String financeNumber, String financeName, BigDecimal totalMaterials, BigDecimal totalEquipment, BigDecimal totalServices, Date startDate, Date endDate, Fund fundType, Integer kpkvk) {
        this.financeNumber = financeNumber;
        this.financeName = financeName;
        this.totalMaterials = totalMaterials;
        this.totalEquipment = totalEquipment;
        this.totalServices = totalServices;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fundType = fundType;
        this.kpkvk = kpkvk;
    }

    public Finance() {
    }

    @Override
    public void setDescription(String financeName) {
        this.financeName = financeName;
    }

    public List<FinanceDepartment> getFinanceDepartmentModelsSorted() {
        //two stage sort, first by department, then by subdepartment
        financeDepartmentModels.sort(Comparator
                .comparing((FinanceDepartment sm) -> sm.getSubdepartment().getDepartment().getDepName())
                .thenComparing(sm -> sm.getSubdepartment().getSubdepName()));
        return financeDepartmentModels;
    }

    public List<FinanceDepartment> getFinanceDepartmentModels() {
        return financeDepartmentModels;
    }

    public String getFinanceNumber() {
        return financeNumber;
    }

    public void setFinanceNumber(String financeNumber) {
        this.financeNumber = financeNumber;
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName;
    }

    public BigDecimal getTotalMaterials() {
        return totalMaterials;
    }

    public void setTotalMaterials(BigDecimal totalMaterials) {
        this.totalMaterials = totalMaterials;
    }

    public BigDecimal getTotalEquipment() {
        return totalEquipment;
    }

    public void setTotalEquipment(BigDecimal totalEquipment) {
        this.totalEquipment = totalEquipment;
    }

    public BigDecimal getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(BigDecimal totalServices) {
        this.totalServices = totalServices;
    }

    public BigDecimal getTotalAmount(BidType type) {
        switch (type) {
            case EQUIPMENT:
                return totalEquipment;
            case MATERIALS:
                return totalMaterials;
            case SERVICES:
                return totalServices;
            default:
                return null;
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Fund getFundType() {
        return fundType;
    }

    public void setFundType(Fund fundType) {
        this.fundType = fundType;
    }

    public Integer getKPKVK() {
        return kpkvk;
    }

    public void setKPKVK(Integer kpkvk) {
        this.kpkvk = kpkvk;
    }

    @Override
    public String toString() {
        if (financeNumber == null && financeName == null) {
            return EmptyModel.STRING;
        }
        return "(" + financeNumber + ") " + financeName;
    }

    public void addFinanceDepartmentModel(FinanceDepartment model) {
        model.setFinances(this);
        int indexOfModel = financeDepartmentModels.indexOf(model);
        // if com.github.andriilab.promasy.domain.model does exist, replace it with modified com.github.andriilab.promasy.domain.model (this is possible with overridden equals() and hashcode() in com.github.andriilab.promasy.domain.model)
        if (indexOfModel != -1) {
            financeDepartmentModels.set(indexOfModel, model);
        } else {
            financeDepartmentModels.add(model);
        }
    }

    @Override
    public void setDeleted() {
        financeDepartmentModels.forEach(IEntity::setDeleted);
        super.setDeleted();
    }

    @Override
    public String getMessage() {
        return "createOrUpdateOrder";
    }
}