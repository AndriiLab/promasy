package com.github.andriilab.promasy.domain;

import com.github.andriilab.promasy.domain.organization.entities.Employee;

import java.sql.Timestamp;

/**
 * Interface for all models
 */
public interface IEntity {
    void setCreatedEmployee(Employee employee);
    Employee getCreatedEmployee();

    void setModifiedEmployee(Employee employee);
    Employee getModifiedEmployee();

    long getModelId();

    void setModelId(long modelId);

    Timestamp getCreatedDate();

    void setCreatedDate(Timestamp createdDate);

    Timestamp getModifiedDate();

    void setModifiedDate(Timestamp modifiedDate);

    boolean isActive();

    void setActive(boolean active, Employee employee, Timestamp modifiedDate);

    String getLastEditPersonName();

    Timestamp getLastEditDate();

    void setDescription(String description);

    String getDescription();

    String getMessage();
}
