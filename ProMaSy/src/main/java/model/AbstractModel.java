package model;

import java.sql.Timestamp;

public abstract class AbstractModel {

    private long modelId;
    private EmployeeModel createdEmployee;
    private Timestamp createdDate;
    private EmployeeModel modifiedEmployee;
    private Timestamp modifiedDate;
	private boolean active;
	
	AbstractModel() {
        this.createdEmployee = new EmployeeModel();
        this.createdDate = null;
        this.modifiedEmployee = new EmployeeModel();
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

    void setModelId(long modelId) {
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
}