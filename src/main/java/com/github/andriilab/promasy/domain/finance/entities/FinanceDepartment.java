package com.github.andriilab.promasy.domain.finance.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IEntity for data of related to department finances
 */

@Entity
@Table(name = "finance_dep")
public class FinanceDepartment extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "finance_id")
    private @Getter
    @Setter
    Finance finances;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subdep_id")
    private @Getter
    @Setter
    Subdepartment subdepartment;

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private @Getter
    @Setter
    List<Bid> bids = new ArrayList<>();

    @Column(name = "total_materials")
    private @Getter
    @Setter
    BigDecimal totalMaterialsAmount;

    @Column(name = "total_eqipment")
    private @Getter
    @Setter
    BigDecimal totalEquipmentAmount;

    @Column(name = "total_services")
    private @Getter
    @Setter
    BigDecimal totalServicesAmount;

    public FinanceDepartment(long modelId, Employee createdEmployee, Timestamp createdDate, Employee modifiedEmployee, Timestamp modifiedDate, boolean active, Finance finances, Subdepartment subdepartment, List<Bid> bids, BigDecimal totalMaterialsAmount, BigDecimal totalEquipmentAmount, BigDecimal totalServicesAmount) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.finances = finances;
        this.subdepartment = subdepartment;
        this.bids = bids;
        this.totalMaterialsAmount = totalMaterialsAmount;
        this.totalEquipmentAmount = totalEquipmentAmount;
        this.totalServicesAmount = totalServicesAmount;
    }

    public FinanceDepartment(Finance model, Subdepartment subdepartment, BigDecimal totalMaterialsAmount, BigDecimal totalEquipmentAmount, BigDecimal totalServicesAmount) {
        this.finances = model;
        this.subdepartment = subdepartment;
        this.totalMaterialsAmount = totalMaterialsAmount;
        this.totalEquipmentAmount = totalEquipmentAmount;
        this.totalServicesAmount = totalServicesAmount;
    }

    public FinanceDepartment() {
    }

    @Override
    public void setDescription(String description) {
    }

    @Override
    public String getDescription() {
        return "";
    }

    public List<Bid> getActiveBids() {
        return bids.stream().filter(Bid::isActive).collect(Collectors.toList());
    }

    public List<Bid> getBids(BidType bidType) {
        return bids.stream().filter(b -> b.getType().equals(bidType)).collect(Collectors.toList());
    }

    public void addBid(Bid model) {
        if (model.getFinances() != null) {
            model.getFinances().getBids().remove(model);
        }
        model.setFinances(this);
        int indexOfModel = bids.indexOf(model);
        // if com.github.andriilab.promasy.domain.model does exist, replace it with modified com.github.andriilab.promasy.domain.model (this is possible with overridden equals() and hashcode() in com.github.andriilab.promasy.domain.model)
        if (indexOfModel != -1) {
            bids.set(indexOfModel, model);
        } else {
            bids.add(model);
        }
    }

    public BigDecimal getTotalAmount(BidType bidType) {
        switch (bidType) {
            case MATERIALS:
                return totalMaterialsAmount;
            case SERVICES:
                return totalServicesAmount;
            case EQUIPMENT:
                return totalEquipmentAmount;
            default:
                return BigDecimal.ZERO;
        }
    }

    @Override
    public String toString() {
        if (finances == null) {
            return "";
        }
        return finances.toString();
    }

    @Override
    public void setDeleted() {
        bids.forEach(IEntity::setDeleted);
        super.setDeleted();
    }

    @Override
    public String getMessage() {
        return "addOrUpdateDepOrder";
    }
}
