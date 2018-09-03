package com.github.andriilab.promasy.domain;

import com.github.andriilab.promasy.domain.organization.entities.Employee;

import java.sql.Timestamp;

/**
 * Interface for all models
 */
public interface IEntity {
    Employee getCreatedEmployee();

    Employee getModifiedEmployee();

    long getModelId();

    void setModelId(long modelId);

    Timestamp getCreatedDate();

    void setCreatedDate(Timestamp createdDate);

    Timestamp getModifiedDate();

    void setModifiedDate(Timestamp modifiedDate);

    boolean isActive();

    void setActive(boolean active);

    String getLastEditPersonName();

    Timestamp getLastEditDate();

    void setDescription(String description);

    String getDescription();

    // methods for setting created/modified employee and created/modified date
    void setCreated();

    void setUpdated();

    void setDeleted();

    String getMessage();
}
