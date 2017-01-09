package model.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Version;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractModel {

    @Column(name = "active")
    protected boolean active;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_seq_gen")
    @GenericGenerator(
            name = "hilo_seq_gen",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "hilo_seqeunce"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "3"),
                    @Parameter(name = "optimizer", value = "hilo")
            })
    @Column(name = "id", nullable = false)
    private long modelId;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private EmployeeModel createdEmployee;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    private EmployeeModel modifiedEmployee;
    @Column(name = "modified_date")
    private Timestamp modifiedDate;
    @Version
    private int version = 0;

    protected AbstractModel() {
        this.createdEmployee = null;
        this.createdDate = null;
        this.modifiedEmployee = null;
        this.modifiedDate = null;
        this.active = true;
    }

    protected AbstractModel(long modelId, EmployeeModel createdEmployee, Timestamp createdDate, EmployeeModel modifiedEmployee, Timestamp modifiedDate,
                            boolean active) {
        this.modelId = modelId;
        this.createdEmployee = createdEmployee;
        this.createdDate = createdDate;
        this.modifiedEmployee = modifiedEmployee;
        this.modifiedDate = modifiedDate;
        this.active = active;
    }

    public EmployeeModel getCreatedEmployee() {
        return createdEmployee;
    }

    public void setCreatedEmployee(EmployeeModel createdEmployee) {
        this.createdEmployee = createdEmployee;
    }

    public EmployeeModel getModifiedEmployee() {
        return modifiedEmployee;
    }

    public void setModifiedEmployee(EmployeeModel modifiedEmployee) {
        this.modifiedEmployee = modifiedEmployee;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLastEditPersonName() {
        if (!modifiedEmployee.getShortName().isEmpty()) {
            return modifiedEmployee.getShortName();
        } else if (!createdEmployee.getShortName().isEmpty()) {
            return createdEmployee.getShortName();
        }
        return null;
    }

    public Timestamp getLastEditDate() {
        if (modifiedDate != null) {
            return modifiedDate;
        } else if (createdDate != null) {
            return createdDate;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractModel)) return false;
        AbstractModel otherModel = (AbstractModel) obj;
        return new EqualsBuilder().append(modelId, otherModel.getModelId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(modelId).hashCode();
    }
}