package com.github.andriilab.promasy.domain;

import com.github.andriilab.promasy.domain.organization.entities.Employee;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractEntity implements IEntity {

    @Column(name = "active")
    @Getter private boolean active;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_seq_gen")
    @GenericGenerator(
            name = "hilo_seq_gen",
            type = org.hibernate.id.enhanced.SequenceStyleGenerator.class,
            parameters = {
                    @Parameter(name = "sequence_name", value = "hilo_seqeunce"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "3"),
                    @Parameter(name = "optimizer", value = "hilo")
            })
    @Column(name = "id", nullable = false)
    @Getter @Setter private long modelId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @Getter @Setter private Employee createdEmployee;

    @Column(name = "created_date")
    @Getter @Setter private Timestamp createdDate;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    @Getter @Setter private Employee modifiedEmployee;

    @Column(name = "modified_date")
    @Getter @Setter private Timestamp modifiedDate;

    @Version
    private final long version = 0;

    protected AbstractEntity() {
        this.createdEmployee = null;
        this.createdDate = null;
        this.modifiedEmployee = null;
        this.modifiedDate = null;
        this.active = true;
    }

    protected AbstractEntity(long modelId, Employee createdEmployee, Timestamp createdDate, Employee modifiedEmployee, Timestamp modifiedDate,
                             boolean active) {
        this.modelId = modelId;
        this.createdEmployee = createdEmployee;
        this.createdDate = createdDate;
        this.modifiedEmployee = modifiedEmployee;
        this.modifiedDate = modifiedDate;
        this.active = active;
    }

    @Override
    public String getLastEditPersonName() {
        if (modifiedEmployee != null && !modifiedEmployee.getShortName().isEmpty()) {
            return modifiedEmployee.getShortName();
        } else if (createdEmployee != null && !createdEmployee.getShortName().isEmpty()) {
            return createdEmployee.getShortName();
        }
        return "";
    }

    @Override
    public Timestamp getLastEditDate() {
        if (modifiedDate != null) {
            return modifiedDate;
        } else if (createdDate != null) {
            return createdDate;
        }
        return Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IEntity otherEntity) {
            return new EqualsBuilder().append(modelId, otherEntity.getModelId()).isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(modelId).hashCode();
    }

    @Override
    public void setActive(boolean active, Employee employee, Timestamp modifiedDate) {
        this.active = active;
        this.modifiedEmployee = employee;
        this.modifiedDate = modifiedDate;
    }
}