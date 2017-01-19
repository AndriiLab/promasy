package model.models;

import model.enums.BidType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for financial data
 */

@Entity
@Table(name = "finances")
public class FinanceModel extends AbstractModel {

    @Column(name = "number")
    private int financeNumber;

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

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private List<FinanceDepartmentModel> financeDepartmentModels = new ArrayList<>();

    public FinanceModel(long modelId, EmployeeModel createdEmployee, Timestamp createdDate, EmployeeModel modifiedEmployee, Timestamp modifiedDate, boolean active, int financeNumber, String financeName, BigDecimal totalMaterials, BigDecimal totalEquipment, BigDecimal totalServices, Date startDate, Date endDate, List<FinanceDepartmentModel> financeDepartmentModels) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.financeNumber = financeNumber;
        this.financeName = financeName;
        this.totalMaterials = totalMaterials;
        this.totalEquipment = totalEquipment;
        this.totalServices = totalServices;
        this.startDate = startDate;
        this.endDate = endDate;
        this.financeDepartmentModels = financeDepartmentModels;
    }

    public FinanceModel(int financeNumber, String financeName, BigDecimal totalMaterials, BigDecimal totalEquipment, BigDecimal totalServices, Date startDate, Date endDate) {
        this.financeNumber = financeNumber;
        this.financeName = financeName;
        this.totalMaterials = totalMaterials;
        this.totalEquipment = totalEquipment;
        this.totalServices = totalServices;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public FinanceModel() {
    }

    public List<FinanceDepartmentModel> getFinanceDepartmentModels() {
        return financeDepartmentModels;
    }

    public void setFinanceDepartmentModels(List<FinanceDepartmentModel> departmentModels) {
        this.financeDepartmentModels = departmentModels;
    }

    public int getFinanceNumber() {
        return financeNumber;
    }

    public void setFinanceNumber(int financeNumber) {
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

    public BigDecimal getLeftAmount(BidType type) {
        BigDecimal leftAmount = getTotalAmount(type);

        for (FinanceDepartmentModel model : financeDepartmentModels) {
            if (model.isActive()) {
                leftAmount = leftAmount.subtract(model.getTotalAmount(type).subtract(model.getLeftAmount(type)));
            }
        }
        return leftAmount;
    }

    public BigDecimal getUnassignedAmount(BidType type) {
        BigDecimal unassignedAmount = getTotalAmount(type);
        for (FinanceDepartmentModel model : financeDepartmentModels) {
            if (model.isActive()) {
                unassignedAmount = unassignedAmount.subtract(model.getTotalAmount(type));
            }
        }
        return unassignedAmount;
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

    @Override
    public String toString() {
        if (financeNumber == 0 && financeName == null) {
            return "";
        }
        return "(" + financeNumber + ") " + financeName;
    }

    public void addFinanceDepartmentModel(FinanceDepartmentModel model) {
        model.setFinances(this);
        int indexOfModel = financeDepartmentModels.indexOf(model);
        // if model does exist, replace it with modified model (this is possible with overridden equals() and hashcode() in model)
        if (indexOfModel != -1) {
            financeDepartmentModels.set(indexOfModel, model);
        } else {
            financeDepartmentModels.add(model);
        }
    }

    @Override
    public void setDeleted() {
        for (FinanceDepartmentModel model : financeDepartmentModels) {
            model.setDeleted();
        }
        super.setDeleted();
    }
}
