package model.models;

import model.dao.Database;
import model.enums.BidType;
import model.enums.Fund;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Enumerated(EnumType.STRING)
    private Fund fundType;

    @Column(name = "kpkvk")
    private Integer kpkvk;

    @OneToMany(mappedBy = "finances", cascade = CascadeType.PERSIST)
    private List<FinanceDepartmentModel> financeDepartmentModels = new ArrayList<>();

    public FinanceModel(long modelId, EmployeeModel createdEmployee, Timestamp createdDate, EmployeeModel modifiedEmployee, Timestamp modifiedDate, boolean active, int financeNumber, String financeName, BigDecimal totalMaterials, BigDecimal totalEquipment, BigDecimal totalServices, Date startDate, Date endDate, List<FinanceDepartmentModel> financeDepartmentModels, Fund fundType, Integer kpkvk) {
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

    public FinanceModel(int financeNumber, String financeName, BigDecimal totalMaterials, BigDecimal totalEquipment, BigDecimal totalServices, Date startDate, Date endDate, Fund fundType, Integer kpkvk) {
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

    public FinanceModel() {
    }

    @Override
    public void setDescription(String financeName) {
        this.financeName = financeName;
    }

    public List<FinanceDepartmentModel> getFinanceDepartmentModelsSorted() {
        //two stage sort, first by department, then by subdepartment
        financeDepartmentModels.sort(Comparator
                .comparing((FinanceDepartmentModel sm) -> sm.getSubdepartment().getDepartment().getDepName())
                .thenComparing(sm -> sm.getSubdepartment().getSubdepName()));
        return financeDepartmentModels;
    }

    public List<FinanceDepartmentModel> getFinanceDepartmentModels() {
        return financeDepartmentModels;
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
        // TODO make a query from this function
        for (FinanceDepartmentModel model : financeDepartmentModels) {
            if (model.isActive()) {
                leftAmount = leftAmount.subtract(model.getUsedAmount(type));
            }
        }
        return leftAmount;
    }

    public BigDecimal getUnassignedAmount(BidType type) {
        return getTotalAmount(type).subtract(Database.DEPARTMENT_FINANCES.getTotalAmount(this, type));
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
        if (financeNumber == 0 && financeName == null) {
            return EmptyModel.STRING;
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
        financeDepartmentModels.forEach(AbstractModel::setDeleted);
        super.setDeleted();
    }
}
