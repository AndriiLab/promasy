package gui.empedit;

import java.util.EventObject;

import model.EmployeeModel;

public class EmployeeEvent extends EventObject{
	
	private EmployeeModel empModel = null;

	public EmployeeEvent(Object source, EmployeeModel empModel) {
		super(source);
		this.empModel = empModel;
	}
	
	public EmployeeModel getEmployeeModel(){
		return empModel;
	}

}
