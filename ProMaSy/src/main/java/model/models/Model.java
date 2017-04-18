package model.models;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Interface for all models
 */
public interface Model {
    EmployeeModel getCreatedEmployee();

    EmployeeModel getModifiedEmployee();

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

    // methods for setting created/modified employee and created/modified date
    void setCreated();

    void setUpdated();

    void setDeleted();

    void createOrUpdate() throws SQLException;

    String getMessage();
}
