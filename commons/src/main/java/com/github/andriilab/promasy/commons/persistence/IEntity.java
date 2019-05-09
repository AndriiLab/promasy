package com.github.andriilab.promasy.commons.persistence;

import java.sql.Timestamp;

/**
 * Interface for all models
 */
public interface IEntity {
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

    String getMessage();
}
