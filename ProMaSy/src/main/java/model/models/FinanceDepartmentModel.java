package model.models;

import model.enums.BidType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for data of related to department finances
 */

@Entity
@Table(name = "finance_dep")
public class FinanceDepartmentModel extends AbstractModel {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "finance_id")
    private FinanceModel finances;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subdep_id")
    private SubdepartmentModel subdepartment;

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private List<BidMaterialModel> materials = new ArrayList<>();

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private List<BidEquipmentModel> equipment = new ArrayList<>();

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private List<BidServiceModel> services = new ArrayList<>();

    @Column(name = "total_materials")
    private BigDecimal totalMaterialsAmount;

    @Column(name = "total_eqipment")
    private BigDecimal totalEqupmentAmount;

    @Column(name = "total_services")
    private BigDecimal totalServicesAmount;

    public FinanceDepartmentModel(long modelId, EmployeeModel createdEmployee, Timestamp createdDate, EmployeeModel modifiedEmployee, Timestamp modifiedDate, boolean active, FinanceModel finances, SubdepartmentModel subdepartment, List<BidMaterialModel> materials, List<BidEquipmentModel> equipment, List<BidServiceModel> services, BigDecimal totalMaterialsAmount, BigDecimal totalEqupmentAmount, BigDecimal totalServicesAmount) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.finances = finances;
        this.subdepartment = subdepartment;
        this.materials = materials;
        this.equipment = equipment;
        this.services = services;
        this.totalMaterialsAmount = totalMaterialsAmount;
        this.totalEqupmentAmount = totalEqupmentAmount;
        this.totalServicesAmount = totalServicesAmount;
    }

    public FinanceDepartmentModel(FinanceModel model, SubdepartmentModel subdepartment, BigDecimal totalMaterialsAmount, BigDecimal totalEqupmentAmount, BigDecimal totalServicesAmount) {
        this.finances = model;
        this.subdepartment = subdepartment;
        this.totalMaterialsAmount = totalMaterialsAmount;
        this.totalEqupmentAmount = totalEqupmentAmount;
        this.totalServicesAmount = totalServicesAmount;
    }

    public FinanceDepartmentModel() {
    }

    public List<BidMaterialModel> getMaterials() {
        return materials;
    }

    public void setMaterials(List<BidMaterialModel> materials) {
        this.materials = materials;
    }

    public List<BidEquipmentModel> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<BidEquipmentModel> equipment) {
        this.equipment = equipment;
    }

    public List<BidServiceModel> getServices() {
        return services;
    }

    public void setServices(List<BidServiceModel> services) {
        this.services = services;
    }

    public FinanceModel getFinances() {
        return finances;
    }

    public void setFinances(FinanceModel finances) {
        this.finances = finances;
    }

    public List<? extends BidModel> getBids(BidType bidType) {
        switch (bidType) {
            case MATERIALS:
                return materials;
            case SERVICES:
                return services;
            case EQUIPMENT:
                return equipment;
            default:
                return null;
        }
    }

    public SubdepartmentModel getSubdepartment() {
        return subdepartment;
    }

    public void setSubdepartment(SubdepartmentModel subdepartment) {
        this.subdepartment = subdepartment;
    }

    public void addMaterialBid(BidMaterialModel model) {
        model.setFinances(this);
        int indexOfModel = materials.indexOf(model);
        // if model does exist, replace it with modified model (this is possible with overridden equals() and hashcode() in model)
        if (indexOfModel != -1) {
            materials.set(indexOfModel, model);
        } else {
            materials.add(model);
        }
    }

    public void addEqupmentBid(BidEquipmentModel model) {
        model.setFinances(this);
        int indexOfModel = equipment.indexOf(model);
        // if model does exist, replace it with modified model (this is possible with overridden equals() and hashcode() in model)
        if (indexOfModel != -1) {
            equipment.set(indexOfModel, model);
        } else {
            equipment.add(model);
        }
    }

    public void addServiceBid(BidServiceModel model) {
        model.setFinances(this);
        int indexOfModel = services.indexOf(model);
        // if model does exist, replace it with modified model (this is possible with overridden equals() and hashcode() in model)
        if (indexOfModel != -1) {
            services.set(indexOfModel, model);
        } else {
            services.add(model);
        }
    }

    public BigDecimal getTotalMaterialsAmount() {
        return totalMaterialsAmount;
    }

    public void setTotalMaterialsAmount(BigDecimal totalMaterialsAmount) {
        this.totalMaterialsAmount = totalMaterialsAmount;
    }

    public BigDecimal getTotalEqupmentAmount() {
        return totalEqupmentAmount;
    }

    public void setTotalEqupmentAmount(BigDecimal totalEqupmentAmount) {
        this.totalEqupmentAmount = totalEqupmentAmount;
    }

    public BigDecimal getTotalServicesAmount() {
        return totalServicesAmount;
    }

    public void setTotalServicesAmount(BigDecimal totalServicesAmount) {
        this.totalServicesAmount = totalServicesAmount;
    }

    public BigDecimal getTotalAmount(BidType bidType) {
        switch (bidType) {
            case MATERIALS:
                return totalMaterialsAmount;
            case SERVICES:
                return totalServicesAmount;
            case EQUIPMENT:
                return totalEqupmentAmount;
            default:
                return null;
        }
    }

    public BigDecimal getLeftMaterialsAmount() {
        BigDecimal leftAmount = totalMaterialsAmount;
        for (BidMaterialModel bid : materials) {
            if (bid.isActive()) {
                leftAmount = leftAmount.subtract(bid.getTotalPrice());
            }
        }
        return leftAmount;
    }

    public BigDecimal getLeftEqupmentAmount() {
        BigDecimal leftAmount = totalEqupmentAmount;
        for (BidEquipmentModel bid : equipment) {
            if (bid.isActive()) {
                leftAmount = leftAmount.subtract(bid.getTotalPrice());
            }
        }
        return leftAmount;
    }

    public BigDecimal getLeftServicesAmount() {
        BigDecimal leftAmount = totalServicesAmount;
        for (BidServiceModel bid : services) {
            if (bid.isActive()) {
                leftAmount = leftAmount.subtract(bid.getTotalPrice());
            }
        }
        return leftAmount;
    }

    public BigDecimal getLeftAmount(BidType bidType) {
        switch (bidType) {
            case MATERIALS:
                return getLeftMaterialsAmount();
            case SERVICES:
                return getLeftServicesAmount();
            case EQUIPMENT:
                return getLeftEqupmentAmount();
            default:
                return null;
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
        for (BidMaterialModel model : materials) {
            model.setDeleted();
        }
        for (BidEquipmentModel model : equipment) {
            model.setDeleted();
        }
        for (BidServiceModel model : services) {
            model.setDeleted();
        }
        super.setDeleted();
    }
}
