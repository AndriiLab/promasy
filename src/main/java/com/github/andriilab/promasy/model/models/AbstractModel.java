package com.github.andriilab.promasy.model.models;

import com.github.andriilab.promasy.model.dao.LoginData;
import com.github.andriilab.promasy.model.dao.ServerQueries;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Version;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractModel implements Model {

    @Column(name = "active")
    private boolean active;
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
    private long version = 0;

    protected AbstractModel() {
        this.createdEmployee = null;
        this.createdDate = null;
        this.modifiedEmployee = null;
        this.modifiedDate = null;
        this.active = true;
    }

    AbstractModel(long modelId, EmployeeModel createdEmployee, Timestamp createdDate, EmployeeModel modifiedEmployee, Timestamp modifiedDate,
                  boolean active) {
        this.modelId = modelId;
        this.createdEmployee = createdEmployee;
        this.createdDate = createdDate;
        this.modifiedEmployee = modifiedEmployee;
        this.modifiedDate = modifiedDate;
        this.active = active;
    }

    @Override
    public EmployeeModel getCreatedEmployee() {
        return createdEmployee;
    }

    private void setCreatedEmployee(EmployeeModel createdEmployee) {
        this.createdEmployee = createdEmployee;
    }

    @Override
    public EmployeeModel getModifiedEmployee() {
        return modifiedEmployee;
    }

    private void setModifiedEmployee(EmployeeModel modifiedEmployee) {
        this.modifiedEmployee = modifiedEmployee;
    }

    @Override
    public long getModelId() {
        return modelId;
    }

    @Override
    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    @Override
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getLastEditPersonName() {
        if (modifiedEmployee != null && !modifiedEmployee.getShortName().isEmpty()) {
            return modifiedEmployee.getShortName();
        } else if (createdEmployee != null && !createdEmployee.getShortName().isEmpty()) {
            return createdEmployee.getShortName();
        }
        return null;
    }

    @Override
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
        if (!(obj instanceof Model)) {
            return false;
        }
        Model otherModel = (Model) obj;
        return new EqualsBuilder().append(modelId, otherModel.getModelId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(modelId).hashCode();
    }

    // methods for setting created/modified employee and created/modified date
    @Override
    public void setCreated() {
        this.setCreatedEmployee(LoginData.getInstance());
        this.setCreatedDate(ServerQueries.getServerTimestamp());
    }

    @Override
    public void setUpdated() {
        this.setModifiedEmployee(LoginData.getInstance());
        this.setModifiedDate(ServerQueries.getServerTimestamp());
    }

    @Override
    public void setDeleted() {
        this.setActive(false);
        setUpdated();
    }
}