/**
 * This class connects MainFrame and Database
 */
package controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import model.CPVQueries;
import model.AbstractModel;
import model.CPVModel;
import model.Database;
import model.DepartmentQueries;
import model.EmployeeModel;
import model.EmployeeQueries;
import model.DepartmentModel;
import model.InstituteModel;
import model.InstituteQueries;
import model.LoginData;
import model.RoleQueries;
import model.RoleModel;
import model.SubdepartmentQueries;
import model.SubdepartmentModel;

public class Controller {
	
	Properties conSet;
	CPVQueries cpv;
	RoleQueries roles;
	InstituteQueries institutes;
	DepartmentQueries departments;
	SubdepartmentQueries subdepartmens;
	EmployeeQueries employees;
	
	// sets connection settings to Properties object 
	public void setConnectionSettings(String host, String database, String schema, 
			String port, String user, String password) {
		if (conSet == null){
			conSet = new Properties();
		}
		conSet.setProperty("host", host);
		conSet.setProperty("port", port);
		conSet.setProperty("database", database);
		conSet.setProperty("user", user);
		conSet.setProperty("password", password);
		conSet.setProperty("currentSchema", schema);
	}
	
	//connecting to DB
	public void connect() throws Exception {
		Database.INSTANCE.connect(conSet);
		cpv = new CPVQueries();
		roles = new RoleQueries();
		institutes = new InstituteQueries();
		departments = new DepartmentQueries();
		subdepartmens = new SubdepartmentQueries();
		employees = new EmployeeQueries();
		
	}
	//disconnecting from DB
	public void disconnect() {
		Database.INSTANCE.disconnect();
	}
	
	//general methods for loging modifications in DB entries
	public <T extends AbstractModel> void setCreated(T model){
		model.setCreatedBy(LoginData.INSTANCE.getEmpId());
		model.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	}
	
	public <T extends AbstractModel> void setModified(T model){
		model.setModifiedBy(LoginData.INSTANCE.getEmpId());
		model.setModifiedDate(new Timestamp(System.currentTimeMillis()));
	}
	
	public <T extends AbstractModel> void setInactive(T model){
		setModified(model);
		model.setActive(false);
	}

	// further methods are using for sending queries and get data (to) from DB
	//TODO factory. if possible
	public void loadCpv(String request, boolean sameLvlShow) throws SQLException {
		cpv.retrieve(request, sameLvlShow);
	}
	
	public List<CPVModel> getCpvList() {
		return cpv.getList();
	}
	
	public void loadRoles() throws SQLException{
		roles.retrieve();
	}
	
	public List<RoleModel> getRolesList() {
		return roles.getList();
	}
	
	public void loadInstitute() throws SQLException{
		institutes.retrieve();
	}
	
	public List<InstituteModel> getInstList() {
		return institutes.getList();
	}
	
	public void loadDepartment(long instId) throws SQLException{
		departments.retrieve(instId);
	}
	
	public List<DepartmentModel> getDepList() {
		return departments.getList();
	}
	
	public void loadSubdepartment(long depId) throws SQLException{
		subdepartmens.retrieve(depId);
	}
	
	public List<SubdepartmentModel> getSubdepList() {
		return subdepartmens.getList();
	} 
	
	public void loadEmployees() throws SQLException{
		employees.retrieve();
	}
	
	public List<EmployeeModel> getEmpList(){
		return employees.getList();
	}
	
	public void createEmployee(EmployeeModel empModel) throws SQLException{
		setCreated(empModel);
		employees.create(empModel);
	}
	
	public void editEmployee(EmployeeModel empModel) throws SQLException{
		setModified(empModel);
		employees.update(empModel);
	}
	
	public boolean checkLogin(LoginData loginData) throws SQLException{
		return employees.checkLogin(loginData);
	}
	
	public void createInstitute(InstituteModel instModel) throws SQLException{
		setCreated(instModel);
		institutes.create(instModel);
	}

	public void editInstitute(InstituteModel instModel) throws SQLException {
		setModified(instModel);
		institutes.update(instModel);
	}

	public void deleteInstitute(InstituteModel instModel) throws SQLException {
		setInactive(instModel);
		institutes.delete(instModel);
	}

	public void createDepartment(DepartmentModel model) throws SQLException {
		setCreated(model);
		departments.create(model);
	}

	public void editDepartment(DepartmentModel model) throws SQLException {
		setModified(model);
		departments.update(model);
	}
	
	public void deleteDepartment(DepartmentModel model) throws SQLException {
		setInactive(model);
		departments.delete(model);
	}

	public void createSubdepartment(SubdepartmentModel model) throws SQLException {
		setCreated(model);
		subdepartmens.create(model);
	}

	public void editSubdepartment(SubdepartmentModel model) throws SQLException {
		setModified(model);
		subdepartmens.update(model);
	}

	public void deleteSubdepartment(SubdepartmentModel model) throws SQLException {
		setInactive(model);
		subdepartmens.delete(model);
	}
		
}
