package com.github.andriilab.promasy.domain.finance.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.enums.Fund;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import lombok.Getter;
import lombok.Setter;

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
    private @Getter
    @Setter
    String financeNumber;

    @Column(name = "name")
    private @Getter
    @Setter
    String financeName;

    @Column(name = "total_materials")
    private @Getter
    @Setter
    BigDecimal totalMaterials;

    @Column(name = "total_equpment")
    private @Getter
    @Setter
    BigDecimal totalEquipment;

    @Column(name = "total_services")
    private @Getter
    @Setter
    BigDecimal totalServices;

    @Column(name = "starts_on")
    private @Getter
    @Setter
    Date startDate;

    @Column(name = "due_to")
    private @Getter
    @Setter
    Date endDate;

    @Enumerated(EnumType.STRING)
    private @Getter
    @Setter
    Fund fundType;

    @Column(name = "kpkvk")
    private @Getter
    @Setter
    Integer kpkvk;

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private @Getter
    List<FinanceDepartment> financeDepartmentModels = new ArrayList<>();

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

    @Override
    public String getDescription() {
        return financeName;
    }

    public List<FinanceDepartment> getFinanceDepartmentModelsSorted() {
        //two stage sort, first by department, then by subdepartment
        financeDepartmentModels.sort(Comparator
                .comparing((FinanceDepartment sm) -> sm.getSubdepartment().getDepartment().getDepName())
                .thenComparing(sm -> sm.getSubdepartment().getSubdepName()));
        return financeDepartmentModels;
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
                return BigDecimal.ZERO;
        }
    }

    @Override
    public String toString() {
        if (financeNumber == null && financeName == null) {
            return "";
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
