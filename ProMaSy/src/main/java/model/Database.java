package main.java.model;

public class Database {
	public static final DBConnector DB = DBConnector.INSTANCE;
	public static final CPVQueries CPV = new CPVQueries();
	public static final RoleQueries ROLES = new RoleQueries();
	public static final AmountUnitsQueries AMOUNTUNITS = new AmountUnitsQueries();
	public static final InstituteQueries INSTITUTES = new InstituteQueries();
	public static final DepartmentQueries DEPARTMENTS = new DepartmentQueries();
	public static final SubdepartmentQueries SUBDEPARTMENS = new SubdepartmentQueries();
	public static final EmployeeQueries EMPLOYEES = new EmployeeQueries();
    public static final ProducerQueries PRODUCERS = new ProducerQueries();
    public static final SupplierQueries SUPPLIERS = new SupplierQueries();
    public static final StatusQueries STATUSES = new StatusQueries();
    public static final FinanceQueries FINANCES = new FinanceQueries();
    public static final FinanceDepartmentQueries DEPARTMENT_FINANCES = new FinanceDepartmentQueries();
    public static final BidsQueries BIDS = new BidsQueries();
    public static final VersionQueries VERSIONS = new VersionQueries();
    public static final ReasonsForSupplierChoiceQueries REASONS = new ReasonsForSupplierChoiceQueries();
}
